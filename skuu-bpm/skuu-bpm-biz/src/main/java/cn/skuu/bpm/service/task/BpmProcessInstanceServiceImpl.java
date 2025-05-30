package cn.skuu.bpm.service.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.skuu.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.skuu.bpm.controller.admin.definition.vo.model.simple.BpmSimpleModelNodeVO;
import cn.skuu.bpm.controller.admin.task.vo.instance.*;
import cn.skuu.bpm.controller.admin.task.vo.instance.BpmApprovalDetailRespVO.ApprovalTaskInfo;
import cn.skuu.bpm.convert.task.BpmProcessInstanceConvert;
import cn.skuu.bpm.dal.dataobject.definition.BpmProcessDefinitionInfoDO;
import cn.skuu.bpm.enums.ErrorCodeConstants;
import cn.skuu.bpm.enums.definition.BpmModelTypeEnum;
import cn.skuu.bpm.enums.definition.BpmSimpleModelNodeType;
import cn.skuu.bpm.enums.task.BpmProcessInstanceStatusEnum;
import cn.skuu.bpm.enums.task.BpmReasonEnum;
import cn.skuu.bpm.enums.task.BpmTaskStatusEnum;
import cn.skuu.bpm.framework.flowable.core.candidate.BpmTaskCandidateInvoker;
import cn.skuu.bpm.framework.flowable.core.candidate.BpmTaskCandidateStrategy;
import cn.skuu.bpm.framework.flowable.core.candidate.strategy.BpmTaskCandidateStartUserSelectStrategy;
import cn.skuu.bpm.framework.flowable.core.enums.BpmnVariableConstants;
import cn.skuu.bpm.framework.flowable.core.event.BpmProcessInstanceEventPublisher;
import cn.skuu.bpm.framework.flowable.core.util.BpmnModelUtils;
import cn.skuu.bpm.framework.flowable.core.util.FlowableUtils;
import cn.skuu.bpm.framework.flowable.core.util.SimpleModelUtils;
import cn.skuu.bpm.service.definition.BpmProcessDefinitionService;
import cn.skuu.bpm.service.message.BpmMessageService;
import cn.skuu.bpm.service.task.bo.AlreadyRunApproveNodeRespBO;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.collection.CollectionUtils;
import cn.skuu.framework.common.util.date.DateUtils;
import cn.skuu.framework.common.util.json.JsonUtils;
import cn.skuu.framework.common.util.object.BeanUtils;
import cn.skuu.framework.common.util.object.PageUtils;
import cn.skuu.system.api.user.AdminUserApi;
import cn.skuu.system.api.user.dto.AdminUserRespDTO;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.variable.VariableContainer;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Stream;

import static cn.skuu.bpm.controller.admin.task.vo.instance.BpmApprovalDetailRespVO.ApprovalNodeInfo;
import static cn.skuu.bpm.controller.admin.task.vo.instance.BpmApprovalDetailRespVO.User;
import static cn.skuu.bpm.enums.ErrorCodeConstants.*;
import static cn.skuu.bpm.enums.definition.BpmSimpleModelNodeType.*;
import static cn.skuu.bpm.enums.definition.BpmUserTaskApproveTypeEnum.USER;
import static cn.skuu.bpm.enums.task.BpmTaskStatusEnum.NOT_START;
import static cn.skuu.bpm.enums.task.BpmTaskStatusEnum.RUNNING;
import static cn.skuu.bpm.framework.flowable.core.enums.BpmTaskCandidateStrategyEnum.START_USER;
import static cn.skuu.bpm.framework.flowable.core.enums.BpmnModelConstants.START_USER_NODE_ID;
import static cn.skuu.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.skuu.framework.common.util.collection.CollectionUtils.*;

/**
 * 流程实例 Service 实现类
 * <p>
 * ProcessDefinition & ProcessInstance & Execution & Task 的关系：
 * 1. <a href="https://blog.csdn.net/bobozai86/article/details/105210414" />
 * <p>
 * HistoricProcessInstance & ProcessInstance 的关系：
 * 1. <a href=" https://my.oschina.net/843294669/blog/71902" />
 * <p>
 * 简单来说，前者 = 历史 + 运行中的流程实例，后者仅是运行中的流程实例
 *
 * @author skuu
 */
@Service
@Validated
@Slf4j
public class BpmProcessInstanceServiceImpl implements BpmProcessInstanceService {

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private HistoryService historyService;
    @Resource
    private ManagementService managementService;
    @Resource
    private BpmActivityService activityService;
    @Resource
    private BpmProcessDefinitionService processDefinitionService;
    @Resource
    @Lazy // 避免循环依赖
    private BpmTaskService taskService;
    @Resource
    private BpmMessageService messageService;
    @Resource
    private BpmTaskCandidateInvoker bpmTaskCandidateInvoker;

    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private BpmProcessInstanceEventPublisher processInstanceEventPublisher;

    // ========== Query 查询相关方法 ==========

    @Override
    public ProcessInstance getProcessInstance(String id) {
        return runtimeService.createProcessInstanceQuery()
                .includeProcessVariables()
                .processInstanceId(id)
                .singleResult();
    }

    @Override
    public List<ProcessInstance> getProcessInstances(Set<String> ids) {
        return runtimeService.createProcessInstanceQuery().processInstanceIds(ids).list();
    }

    @Override
    public HistoricProcessInstance getHistoricProcessInstance(String id) {
        return historyService.createHistoricProcessInstanceQuery().processInstanceId(id).includeProcessVariables().singleResult();
    }

    @Override
    public List<HistoricProcessInstance> getHistoricProcessInstances(Set<String> ids) {
        return historyService.createHistoricProcessInstanceQuery().processInstanceIds(ids).list();
    }

    @Override
    public PageResult<HistoricProcessInstance> getProcessInstancePage(Long userId,
                                                                      BpmProcessInstancePageReqVO pageReqVO) {
        // 通过 BpmProcessInstanceExtDO 表，先查询到对应的分页
        HistoricProcessInstanceQuery processInstanceQuery = historyService.createHistoricProcessInstanceQuery()
                .includeProcessVariables()
                .processInstanceTenantId(FlowableUtils.getTenantId())
                .orderByProcessInstanceStartTime().desc();
        if (userId != null) { // 【我的流程】菜单时，需要传递该字段
            processInstanceQuery.startedBy(String.valueOf(userId));
        } else if (pageReqVO.getStartUserId() != null) { // 【管理流程】菜单时，才会传递该字段
            processInstanceQuery.startedBy(String.valueOf(pageReqVO.getStartUserId()));
        }
        if (StrUtil.isNotEmpty(pageReqVO.getName())) {
            processInstanceQuery.processInstanceNameLike("%" + pageReqVO.getName() + "%");
        }
        if (StrUtil.isNotEmpty(pageReqVO.getProcessDefinitionKey())) {
            processInstanceQuery.processDefinitionKey(pageReqVO.getProcessDefinitionKey());
        }
        if (StrUtil.isNotEmpty(pageReqVO.getCategory())) {
            processInstanceQuery.processDefinitionCategory(pageReqVO.getCategory());
        }
        if (pageReqVO.getStatus() != null) {
            processInstanceQuery.variableValueEquals(BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_STATUS, pageReqVO.getStatus());
        }
        if (ArrayUtil.isNotEmpty(pageReqVO.getCreateTime())) {
            processInstanceQuery.startedAfter(DateUtils.of(pageReqVO.getCreateTime()[0]));
            processInstanceQuery.startedBefore(DateUtils.of(pageReqVO.getCreateTime()[1]));
        }
        // 查询数量
        long processInstanceCount = processInstanceQuery.count();
        if (processInstanceCount == 0) {
            return PageResult.empty(processInstanceCount);
        }
        // 查询列表
        List<HistoricProcessInstance> processInstanceList = processInstanceQuery.listPage(PageUtils.getStart(pageReqVO), pageReqVO.getPageSize());
        return new PageResult<>(processInstanceList, processInstanceCount);
    }

    @Override
    public Map<String, String> getFormFieldsPermission(BpmFormFieldsPermissionReqVO reqVO) {
        // 1.1  获取流程定义 Id
        String processDefinitionId = reqVO.getProcessDefinitionId();
        if (StrUtil.isEmpty(processDefinitionId) && StrUtil.isNotEmpty(reqVO.getProcessInstanceId())) {
            HistoricProcessInstance processInstance = getHistoricProcessInstance(reqVO.getProcessInstanceId());
            if (processInstance == null) {
                throw exception(ErrorCodeConstants.PROCESS_INSTANCE_NOT_EXISTS);
            }
            processDefinitionId = processInstance.getProcessDefinitionId();
        }

        // 1.2 获取流程活动编号
        String activityId = reqVO.getActivityId();
        if (StrUtil.isEmpty(activityId) && StrUtil.isNotEmpty(reqVO.getTaskId())) { // 流程活动 Id 为空。从流程任务中获取流程活动 Id
            activityId = Optional.ofNullable(taskService.getHistoricTask(reqVO.getTaskId()))
                    .map(HistoricTaskInstance::getTaskDefinitionKey).orElse(null);
        }
        if (StrUtil.isEmpty(activityId)) {
            return null;
        }

        // 2. 从 BpmnModel 中解析表单字段权限
        return BpmnModelUtils.parseFormFieldsPermission(
                processDefinitionService.getProcessDefinitionBpmnModel(processDefinitionId), activityId);
    }

    @Override
    public BpmApprovalDetailRespVO getApprovalDetail(Long startUserId, BpmApprovalDetailReqVO reqVO) {
        // 1. 审批详情
        BpmApprovalDetailRespVO respVO = new BpmApprovalDetailRespVO();
        String processDefinitionId = reqVO.getProcessDefinitionId();
        ProcessInstance runProcessInstance = null;  // 正在运行的流程实例
        Set<String> runNodeIds = new HashSet<>(); // 已经运行的节点 Ids (BPMN XML 节点 Id)
        Map<String, ApprovalNodeInfo> runningApprovalNodes = new HashMap<>(); // 正在运行的节点的审批信息
        List<ApprovalNodeInfo> approvalNodes = new ArrayList<>();
        // 1.1 情况一：流程未发起
        if (reqVO.getProcessInstanceId() == null) {
            respVO.setStatus(BpmProcessInstanceStatusEnum.NOT_START.getStatus());
            // 1.2 情况二：流程已发起
        } else {
            // 1.2.1 获取流程实例状态
            HistoricProcessInstance processInstance = getHistoricProcessInstance(reqVO.getProcessInstanceId());
            if (processInstance == null) {
                throw exception(ErrorCodeConstants.PROCESS_INSTANCE_NOT_EXISTS);
            }
            Integer processInstanceStatus = FlowableUtils.getProcessInstanceStatus(processInstance);
            respVO.setStatus(processInstanceStatus);
            // 1.2.2 构建已运行节点的审批信息
            List<HistoricActivityInstance> historicActivityList = activityService.getActivityListByProcessInstanceId(processInstance.getId());
            AlreadyRunApproveNodeRespBO respBO = buildAlreadyRunApproveNodes(processInstance.getId(), processInstanceStatus, historicActivityList);
            approvalNodes = respBO.getApproveNodes();
            runNodeIds = respBO.getRunNodeIds();
            // 1.2.3 特殊：流程已经结束，直接 return，无需预测
            if (BpmProcessInstanceStatusEnum.isProcessEndStatus(processInstanceStatus)) {
                respVO.setApproveNodes(approvalNodes);
                return respVO;
            }
            runningApprovalNodes = respBO.getRunningApprovalNodes();
            processDefinitionId = processInstance.getProcessDefinitionId();
            runProcessInstance = getProcessInstance(processInstance.getId());
            startUserId = Long.valueOf(runProcessInstance.getStartUserId());
        }

        // 2. 流程未结束，预测未运行节点的审批信息。需要区分 BPMN 设计器 和 SIMPLE 设计器
        BpmProcessDefinitionInfoDO processDefinitionInfo = processDefinitionService.getProcessDefinitionInfo(processDefinitionId);
        // 2.1 情况一：仿钉钉流程设计器
        if (Objects.equals(BpmModelTypeEnum.SIMPLE.getType(), processDefinitionInfo.getModelType())) {
            BpmSimpleModelNodeVO simpleModel = JsonUtils.parseObject(processDefinitionInfo.getSimpleModel(), BpmSimpleModelNodeVO.class);
            List<ApprovalNodeInfo> notRunApproveNodes = new ArrayList<>();
            traverseSimpleModelNodeToBuildNotRunApproveNodes(
                    startUserId, runProcessInstance, simpleModel, runNodeIds, runningApprovalNodes, notRunApproveNodes);
            approvalNodes.addAll(notRunApproveNodes);
            respVO.setApproveNodes(approvalNodes);
            //  会不会有极端的情况：对于依次审批来说，它是已经 running，但是当前节点也要计算其它审批人？(已修改)
            // 2.2 情况二：BPMN 流程设计器
        } else if (Objects.equals(BpmModelTypeEnum.BPMN.getType(), processDefinitionInfo.getModelType())) {
            // TODO 芋艿：需要把 start 节点加出来
            // TODO Bpmn 设计器，构建未运行节点的审批信息；未完全实现
            respVO.setApproveNodes(approvalNodes);
        }
        return respVO;
    }

    /**
     * 遍历 SIMPLE 设计器模型 构建未运行节点的审批信息
     *
     * @param startUserId          流程发起人编号
     * @param processInstance      流程实例
     * @param simpleModelNode      SIMPLE 设计器模型
     * @param runNodeIds           已经运行节点的 Ids
     * @param runningApprovalNodes 正在运行的节点的审批信息
     * @param approveNodeList      未运行节点的审批信息列表
     */
    private void traverseSimpleModelNodeToBuildNotRunApproveNodes(Long startUserId,
                                                                  ProcessInstance processInstance,
                                                                  BpmSimpleModelNodeVO simpleModelNode,
                                                                  Set<String> runNodeIds,
                                                                  Map<String, ApprovalNodeInfo> runningApprovalNodes,
                                                                  List<ApprovalNodeInfo> approveNodeList) {
        if (!SimpleModelUtils.isValidNode(simpleModelNode)) {
            return;
        }
        buildNotRunApproveNodes(startUserId, processInstance, simpleModelNode, runNodeIds, runningApprovalNodes, approveNodeList);
        // 如果有子节点递归遍历子节点
        traverseSimpleModelNodeToBuildNotRunApproveNodes(
                startUserId, processInstance, simpleModelNode.getChildNode(), runNodeIds, runningApprovalNodes, approveNodeList);
    }

    private void buildNotRunApproveNodes(Long startUserId, ProcessInstance processInstance,
                                         BpmSimpleModelNodeVO node, Set<String> runNodeIds,
                                         Map<String, ApprovalNodeInfo> runningApprovalNodes,
                                         List<ApprovalNodeInfo> approveNodeList) {
        // 情况一：节点未运行：需要进行预测
        if (!runNodeIds.contains(node.getId())) {
            // 1. 对需要人工审批的审批节点，进行预测
            if (APPROVE_NODE.getType().equals(node.getType()) && USER.getType().equals(node.getApproveType())
                    || START_USER_NODE.getType().equals(node.getType())) {
                ApprovalNodeInfo approvalNodeInfo = new ApprovalNodeInfo().setNodeType(node.getType())
                        .setName(node.getName()).setStatus(NOT_START.getStatus());
                Integer candidateStrategy = START_USER_NODE.getType().equals(node.getType()) ?
                        START_USER.getStrategy() : node.getCandidateStrategy();
                approvalNodeInfo.setCandidateUserList(
                        getNotRunTaskCandidateUserList(startUserId, processInstance, node.getId(), candidateStrategy, node.getCandidateParam()));
                approveNodeList.add(approvalNodeInfo);
                // 2. 对分支节点，进行预测
            } else if (BpmSimpleModelNodeType.isBranchNode(node.getType())) {
                //  并行分支，不用预测条件。所有分支都需要遍历
                if (PARALLEL_BRANCH_NODE.getType().equals(node.getType())) {
                    node.getConditionNodes().forEach(conditionNode ->
                            traverseSimpleModelNodeToBuildNotRunApproveNodes(startUserId, processInstance, conditionNode.getChildNode()
                                    , runNodeIds, runningApprovalNodes, approveNodeList));
                } else if (CONDITION_BRANCH_NODE.getType().equals(node.getType())) {
                    for (BpmSimpleModelNodeVO conditionNode : node.getConditionNodes()) {
                        // 满足一个条件, 遍历该分支并
                        if ((processInstance != null && evalConditionExpress(processInstance, SimpleModelUtils.buildConditionExpression(conditionNode))) // 预测条件表达式的值
                                || BooleanUtil.isTrue(conditionNode.getDefaultFlow())) { // 是否默认的序列
                            traverseSimpleModelNodeToBuildNotRunApproveNodes(startUserId, processInstance, conditionNode.getChildNode(),
                                    runNodeIds, runningApprovalNodes, approveNodeList);
                            break;
                        }
                    }
                }
                // TODO 包容分支待实现
                // 3. 结束节点
            } else if (END_NODE.getType().equals(node.getType())) {
                ApprovalNodeInfo nodeProgress = new ApprovalNodeInfo();
                nodeProgress.setNodeType(node.getType());
                nodeProgress.setName(node.getName());
                nodeProgress.setStatus(NOT_START.getStatus());
                approveNodeList.add(nodeProgress);
            }
        } else {
            // 情况二：节点已经运行
            // 如果是分支节点，需要检查分支节点的运行情况
            if (BpmSimpleModelNodeType.isBranchNode(node.getType()) && ArrayUtil.isNotEmpty(node.getConditionNodes())) {
                node.getConditionNodes().forEach(conditionNode -> {
                    // 只有运行的条件，才需要遍历
                    if (runNodeIds.contains(conditionNode.getId())) {
                        traverseSimpleModelNodeToBuildNotRunApproveNodes(startUserId, processInstance, conditionNode.getChildNode()
                                , runNodeIds, runningApprovalNodes, approveNodeList);
                    }
                });
                // 如果是依次审批, 需要加其它未审批候选人
            } else if (SimpleModelUtils.isSequentialApproveNode(node) && runningApprovalNodes.containsKey(node.getId())) {
                ApprovalNodeInfo approvalNodeInfo = runningApprovalNodes.get(node.getId());
                List<User> candidateUserList = getNotRunTaskCandidateUserList(
                        startUserId, processInstance, node.getId(), node.getCandidateStrategy(), node.getCandidateParam());
                // TODO @jason：这里的逻辑，可能可以简化成，直接拿已经审批过的人的 userId 集合，从 candidateUserList remove 下。一方面简单一点，方面 calculateUsers 返回的是 set，目前不是很有序。
                ApprovalTaskInfo approvalTaskInfo = CollUtil.getFirst(approvalNodeInfo.getTasks());
                Long currentAssignedUserId = null;
                if (approvalTaskInfo != null && approvalTaskInfo.getAssigneeUser() != null) {
                    currentAssignedUserId = approvalTaskInfo.getAssigneeUser().getId();
                }
                // 找到当前审批人在候选人列表的位置
                int index = 0;
                for (User user : candidateUserList) {
                    if (user.getId().equals(currentAssignedUserId)) {
                        break;
                    }
                    index++;
                }
                // 截取当前审批人位置后面的候选人, 不包含当前审批人
                approvalNodeInfo.setCandidateUserList(CollUtil.sub(candidateUserList, ++index, candidateUserList.size()));
            }
        }
    }

    /**
     * 从已经运行活动节点构建的审批信息列表
     *
     * @param processInstanceId    流程实例 Id
     * @param processInstanceStatus 流程实例状态
     * @param historicActivityList 已经运行活动
     */
    private AlreadyRunApproveNodeRespBO buildAlreadyRunApproveNodes(String processInstanceId,
                                                                    Integer processInstanceStatus,
                                                                    List<HistoricActivityInstance> historicActivityList) {
        // 1.1 获取待处理活动：只有 "userTask" 和 "endEvent" 需要处理
        List<HistoricActivityInstance> pendingActivityNodes = filterList(historicActivityList,
                item -> BpmSimpleModelNodeType.isRecordNode(item.getActivityType()));
        // 1.2 已运行节点的 activityId
        Set<String> runNodeIds = convertSet(historicActivityList, HistoricActivityInstance::getActivityId);

        // 2.1 获取已运行的任务(包括运行中的任务)
        List<HistoricTaskInstance> taskList = taskService.getTaskListByProcessInstanceId(processInstanceId);
        Map<String, HistoricTaskInstance> taskMap = convertMap(taskList, HistoricTaskInstance::getId);

        // 2.2 获取加签的任务
        Map<String, List<HistoricTaskInstance>> addSignTaskMap = convertMultiMap(
                filterList(taskList, task -> StrUtil.isNotEmpty(task.getParentTaskId())), HistoricTaskInstance::getParentTaskId);
        // 3.1 获取节点的用户信息
        Set<Long> userIds = CollectionUtils.convertSetByFlatMap(pendingActivityNodes, activity -> {
            if (BPMN_USER_TASK_TYPE.equals(activity.getActivityType())) {
                HistoricTaskInstance task = taskMap.get(activity.getTaskId());
                Set<Long> taskUsers = CollUtil.newHashSet();
                CollUtil.addIfAbsent(taskUsers, NumberUtil.parseLong(task.getAssignee(), null));
                CollUtil.addIfAbsent(taskUsers, NumberUtil.parseLong(task.getOwner(), null));
                List<HistoricTaskInstance> addSignTasks = addSignTaskMap.get(activity.getTaskId());
                if (CollUtil.isNotEmpty(addSignTasks)) {
                    addSignTasks.forEach(item -> {
                        CollUtil.addIfAbsent(taskUsers, NumberUtil.parseLong(item.getAssignee(), null));
                        CollUtil.addIfAbsent(taskUsers, NumberUtil.parseLong(item.getOwner(), null));
                    });
                }
                return taskUsers.stream();
            } else {
                return Stream.empty();
            }
        });
        Map<Long, AdminUserRespDTO> userMap = convertMap(adminUserApi.getUserList(userIds).getCheckedData(), AdminUserRespDTO::getId);

        // 3.2 已经结束的任务转换为审批信息
        final Multimap<String, HistoricActivityInstance> runningTask = ArrayListMultimap.create(); // 运行中的任务
        List<ApprovalNodeInfo> approvalNodeList = convertList(pendingActivityNodes, activity -> {
            ApprovalNodeInfo approvalNodeInfo = new ApprovalNodeInfo().setId(activity.getId()).setName(activity.getActivityName())
                    .setStartTime(DateUtils.of(activity.getStartTime())).setEndTime(DateUtils.of(activity.getEndTime()));
            if (BPMN_USER_TASK_TYPE.equals(activity.getActivityType())) { // 用户任务
                // nodeType
                approvalNodeInfo.setNodeType(START_USER_NODE_ID.equals(activity.getActivityId()) ?
                        START_USER_NODE.getType() : APPROVE_NODE.getType());
                // status
                HistoricTaskInstance task = taskMap.get(activity.getTaskId());
                Integer taskStatus = FlowableUtils.getTaskStatus(task);
                // 运行中的任务, 会签，或签任务聚合在一起。
                if (!BpmTaskStatusEnum.isEndStatus(taskStatus)) {
                    runningTask.put(activity.getActivityId(), activity);
                    return null;
                }
                // tasks
                ApprovalTaskInfo approveTask = convertApproveTaskInfo(task, userMap);
                List<ApprovalTaskInfo> approveTasks = CollUtil.newArrayList(approveTask);
                List<HistoricTaskInstance> addSignTasks = addSignTaskMap.get(activity.getTaskId());
                if (CollUtil.isNotEmpty(addSignTasks)) { // 处理加签任务
                    approveTasks.addAll(convertList(addSignTasks, item -> convertApproveTaskInfo(item, userMap)));
                }

                approvalNodeInfo.setStatus(taskStatus);
                approvalNodeInfo.setTasks(approveTasks);
            } else if (END_NODE.getBpmnType().equals(activity.getActivityType())) {
                approvalNodeInfo.setNodeType(END_NODE.getType());
                approvalNodeInfo.setStatus(processInstanceStatus);
            }
            return approvalNodeInfo;
        });
        // 3.3 运行中的任务转换为审批信息。
        final Map<String, ApprovalNodeInfo> runningApprovalNodes = new HashMap<>(); // 正在运行节点的审批信息
        runningTask.asMap().forEach((activityId, activities) -> {
            if (CollUtil.isNotEmpty(activities)) {
                ApprovalNodeInfo approvalNodeInfo = new ApprovalNodeInfo();
                approvalNodeInfo.setNodeType(APPROVE_NODE.getType());
                approvalNodeInfo.setStatus(RUNNING.getStatus());
                List<ApprovalTaskInfo> approveTasks = CollUtil.newArrayList();
                int i = 0;
                for (HistoricActivityInstance activity : activities) {
                    HistoricTaskInstance task = taskMap.get(activity.getTaskId());
                    // 取第一个任务， 会签/或签的任务。开始时间相同的
                    if (i == 0) {
                        approvalNodeInfo.setId(activity.getId()).setName(activity.getActivityName()).
                                setStartTime(DateUtils.of(activity.getStartTime()));
                    }
                    // tasks
                    ApprovalTaskInfo approveTask = convertApproveTaskInfo(task, userMap);
                    approveTasks.add(approveTask);
                    List<HistoricTaskInstance> addSignTasks = addSignTaskMap.get(activity.getTaskId());
                    if (CollUtil.isNotEmpty(addSignTasks)) { // 处理加签任务
                        approveTasks.addAll(convertList(addSignTasks, item -> convertApproveTaskInfo(item, userMap)));
                    }
                    i++;
                }
                approvalNodeInfo.setTasks(approveTasks);
                approvalNodeList.add(approvalNodeInfo);
                runningApprovalNodes.put(activityId, approvalNodeInfo);
            }
        });
        return new AlreadyRunApproveNodeRespBO().setApproveNodes(approvalNodeList).setRunNodeIds(runNodeIds)
                .setRunningApprovalNodes(runningApprovalNodes);
    }

    private ApprovalTaskInfo convertApproveTaskInfo(HistoricTaskInstance task, Map<Long, AdminUserRespDTO> userMap) {
        if (task == null) {
            return null;
        }
        ApprovalTaskInfo approveTask = BeanUtils.toBean(task, ApprovalTaskInfo.class);
        approveTask.setStatus(FlowableUtils.getTaskStatus(task)).setReason(FlowableUtils.getTaskReason(task));
        Long taskAssignee = NumberUtil.parseLong(task.getAssignee(), null);
        if (taskAssignee != null) {
            approveTask.setAssigneeUser(BeanUtils.toBean(userMap.get(taskAssignee), User.class));
        }
        Long taskOwner = NumberUtil.parseLong(task.getOwner(), null);
        if (taskOwner != null) {
            approveTask.setOwnerUser(BeanUtils.toBean(userMap.get(taskOwner), User.class));
        }
        return approveTask;
    }

    private List<User> getNotRunTaskCandidateUserList(Long startUserId, ProcessInstance processInstance, String activityId,
                                                      Integer candidateStrategy, String candidateParam) {
        BpmTaskCandidateStrategy taskCandidateStrategy = bpmTaskCandidateInvoker.getCandidateStrategy(candidateStrategy);
        Set<Long> userIds = taskCandidateStrategy.calculateUsers(startUserId, processInstance, activityId, candidateParam);
        Map<Long, AdminUserRespDTO> adminUserMap = adminUserApi.getUserMap(userIds);
        // 需要按照候选人的顺序返回。原因是，依次审批需要按顺序展示用户
        return convertList(userIds, userId -> BeanUtils.toBean(adminUserMap.get(userId), User.class));
    }

    /**
     * 计算条件表达式的值
     *
     * @param processInstance 流程实例
     * @param express         条件表达式
     */
    private Boolean evalConditionExpress(ProcessInstance processInstance, String express) {
        if (express == null) {
            return Boolean.FALSE;
        }
        Object result = managementService.executeCommand(context -> {
            try {
                return FlowableUtils.getExpressionValue((VariableContainer) processInstance, express);
            } catch (FlowableException ex) {
                log.error("[evalConditionExpress][条件表达式({}) 解析报错", express, ex);
                return Boolean.FALSE;
            }
        });
        return Boolean.TRUE.equals(result);
    }

    // ========== Update 写入相关方法 ==========

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createProcessInstance(Long userId, @Valid BpmProcessInstanceCreateReqVO createReqVO) {
        // 获得流程定义
        ProcessDefinition definition = processDefinitionService.getProcessDefinition(createReqVO.getProcessDefinitionId());
        // 发起流程
        return createProcessInstance0(userId, definition, createReqVO.getVariables(), null,
                createReqVO.getStartUserSelectAssignees());
    }

    @Override
    public String createProcessInstance(Long userId, @Valid BpmProcessInstanceCreateReqDTO createReqDTO) {
        // 获得流程定义
        ProcessDefinition definition = processDefinitionService.getActiveProcessDefinition(createReqDTO.getProcessDefinitionKey());
        // 发起流程
        return createProcessInstance0(userId, definition, createReqDTO.getVariables(), createReqDTO.getBusinessKey(),
                createReqDTO.getStartUserSelectAssignees());
    }

    private String createProcessInstance0(Long userId, ProcessDefinition definition,
                                          Map<String, Object> variables, String businessKey,
                                          Map<String, List<Long>> startUserSelectAssignees) {
        // 1.1 校验流程定义
        if (definition == null) {
            throw exception(PROCESS_DEFINITION_NOT_EXISTS);
        }
        if (definition.isSuspended()) {
            throw exception(PROCESS_DEFINITION_IS_SUSPENDED);
        }
        BpmProcessDefinitionInfoDO processDefinitionInfo = processDefinitionService.getProcessDefinitionInfo(definition.getId());
        if (processDefinitionInfo == null) {
            throw exception(PROCESS_DEFINITION_NOT_EXISTS);
        }
        // 1.2 校验是否能够发起
        if (!processDefinitionService.canUserStartProcessDefinition(processDefinitionInfo, userId)) {
            throw exception(PROCESS_INSTANCE_START_USER_CAN_START);
        }
        // 1.3 校验发起人自选审批人
        validateStartUserSelectAssignees(definition, startUserSelectAssignees);

        // 2. 创建流程实例
        if (variables == null) {
            variables = new HashMap<>();
        }
        FlowableUtils.filterProcessInstanceFormVariable(variables); // 过滤一下，避免 ProcessInstance 系统级的变量被占用
        variables.put(BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_STATUS, // 流程实例状态：审批中
                BpmProcessInstanceStatusEnum.RUNNING.getStatus());
        if (CollUtil.isNotEmpty(startUserSelectAssignees)) {
            variables.put(BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_START_USER_SELECT_ASSIGNEES, startUserSelectAssignees);
        }
        ProcessInstance instance = runtimeService.createProcessInstanceBuilder()
                .processDefinitionId(definition.getId())
                .businessKey(businessKey)
                .name(definition.getName().trim())
                .variables(variables)
                .start();
        return instance.getId();
    }

    private void validateStartUserSelectAssignees(ProcessDefinition definition, Map<String, List<Long>> startUserSelectAssignees) {
        // 1. 获得发起人自选审批人的 UserTask 列表
        BpmnModel bpmnModel = processDefinitionService.getProcessDefinitionBpmnModel(definition.getId());
        List<UserTask> userTaskList = BpmTaskCandidateStartUserSelectStrategy.getStartUserSelectUserTaskList(bpmnModel);
        if (CollUtil.isEmpty(userTaskList)) {
            return;
        }

        // 2. 校验发起人自选审批人的 UserTask 是否都配置了
        userTaskList.forEach(userTask -> {
            List<Long> assignees = startUserSelectAssignees != null ? startUserSelectAssignees.get(userTask.getId()) : null;
            if (CollUtil.isEmpty(assignees)) {
                throw exception(PROCESS_INSTANCE_START_USER_SELECT_ASSIGNEES_NOT_CONFIG, userTask.getName());
            }
            Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(assignees);
            assignees.forEach(assignee -> {
                if (userMap.get(assignee) == null) {
                    throw exception(PROCESS_INSTANCE_START_USER_SELECT_ASSIGNEES_NOT_EXISTS, userTask.getName(), assignee);
                }
            });
        });
    }

    @Override
    public void cancelProcessInstanceByStartUser(Long userId, @Valid BpmProcessInstanceCancelReqVO cancelReqVO) {
        // 1.1 校验流程实例存在
        ProcessInstance instance = getProcessInstance(cancelReqVO.getId());
        if (instance == null) {
            throw exception(PROCESS_INSTANCE_CANCEL_FAIL_NOT_EXISTS);
        }
        // 1.2 只能取消自己的
        if (!Objects.equals(instance.getStartUserId(), String.valueOf(userId))) {
            throw exception(PROCESS_INSTANCE_CANCEL_FAIL_NOT_SELF);
        }

        // 2. 取消流程
        updateProcessInstanceCancel(cancelReqVO.getId(),
                BpmReasonEnum.CANCEL_PROCESS_INSTANCE_BY_START_USER.format(cancelReqVO.getReason()));
    }

    @Override
    public void cancelProcessInstanceByAdmin(Long userId, BpmProcessInstanceCancelReqVO cancelReqVO) {
        // 1.1 校验流程实例存在
        ProcessInstance instance = getProcessInstance(cancelReqVO.getId());
        if (instance == null) {
            throw exception(PROCESS_INSTANCE_CANCEL_FAIL_NOT_EXISTS);
        }

        // 2. 取消流程
        AdminUserRespDTO user = adminUserApi.getUser(userId).getCheckedData();
        updateProcessInstanceCancel(cancelReqVO.getId(),
                BpmReasonEnum.CANCEL_PROCESS_INSTANCE_BY_ADMIN.format(user.getNickname(), cancelReqVO.getReason()));
    }

    private void updateProcessInstanceCancel(String id, String reason) {
        // 1. 更新流程实例 status
        runtimeService.setVariable(id, BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_STATUS,
                BpmProcessInstanceStatusEnum.CANCEL.getStatus());
        runtimeService.setVariable(id, BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_REASON, reason);

        // 2. 结束流程
        taskService.moveTaskToEnd(id);
    }

    @Override
    public void updateProcessInstanceReject(ProcessInstance processInstance, String reason) {
        runtimeService.setVariable(processInstance.getProcessInstanceId(), BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_STATUS,
                BpmProcessInstanceStatusEnum.REJECT.getStatus());
        runtimeService.setVariable(processInstance.getProcessInstanceId(), BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_REASON,
                BpmReasonEnum.REJECT_TASK.format(reason));
    }

    // ========== Event 事件相关方法 ==========

    @Override
    public void processProcessInstanceCompleted(ProcessInstance instance) {
        // 注意：需要基于 instance 设置租户编号，避免 Flowable 内部异步时，丢失租户编号
        FlowableUtils.execute(instance.getTenantId(), () -> {
            // 1.1 获取当前状态
            Integer status = (Integer) instance.getProcessVariables().get(BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_STATUS);
            String reason = (String) instance.getProcessVariables().get(BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_REASON);
            // 1.2 当流程状态还是审批状态中，说明审批通过了，则变更下它的状态
            // 为什么这么处理？因为流程完成，并且完成了，说明审批通过了
            if (Objects.equals(status, BpmProcessInstanceStatusEnum.RUNNING.getStatus())) {
                status = BpmProcessInstanceStatusEnum.APPROVE.getStatus();
                runtimeService.setVariable(instance.getId(), BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_STATUS, status);
            }

            // 2. 发送对应的消息通知
            if (Objects.equals(status, BpmProcessInstanceStatusEnum.APPROVE.getStatus())) {
                messageService.sendMessageWhenProcessInstanceApprove(BpmProcessInstanceConvert.INSTANCE.buildProcessInstanceApproveMessage(instance));
            } else if (Objects.equals(status, BpmProcessInstanceStatusEnum.REJECT.getStatus())) {
                messageService.sendMessageWhenProcessInstanceReject(
                        BpmProcessInstanceConvert.INSTANCE.buildProcessInstanceRejectMessage(instance, reason));
            }

            // 3. 发送流程实例的状态事件
            processInstanceEventPublisher.sendProcessInstanceResultEvent(
                    BpmProcessInstanceConvert.INSTANCE.buildProcessInstanceStatusEvent(this, instance, status));
        });
    }

}
