package cn.skuu.bpm.service.task;

import cn.hutool.core.util.ObjectUtil;
import cn.skuu.bpm.controller.admin.task.vo.instance.BpmProcessInstanceCopyPageReqVO;
import cn.skuu.bpm.dal.dataobject.task.BpmProcessInstanceCopyDO;
import cn.skuu.bpm.dal.mysql.task.BpmProcessInstanceCopyMapper;
import cn.skuu.bpm.enums.ErrorCodeConstants;
import cn.skuu.bpm.service.definition.BpmProcessDefinitionService;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.collection.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static cn.skuu.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.skuu.framework.common.util.collection.CollectionUtils.convertList;

/**
 * 流程抄送 Service 实现类
 *
 * @author kyle
 */
@Service
@Validated
@Slf4j
public class BpmProcessInstanceCopyServiceImpl implements BpmProcessInstanceCopyService {

    @Resource
    private BpmProcessInstanceCopyMapper processInstanceCopyMapper;

    @Resource
    @Lazy // 延迟加载，避免循环依赖
    private BpmTaskService taskService;

    @Resource
    @Lazy // 延迟加载，避免循环依赖
    private BpmProcessInstanceService processInstanceService;
    @Resource
    @Lazy // 延迟加载，避免循环依赖
    private BpmProcessDefinitionService processDefinitionService;

    @Override
    public void createProcessInstanceCopy(Collection<Long> userIds, String taskId) {
        Task task = taskService.getTask(taskId);
        if (ObjectUtil.isNull(task)) {
            throw exception(ErrorCodeConstants.TASK_NOT_EXISTS);
        }
        String processInstanceId = task.getProcessInstanceId();
        createProcessInstanceCopy(userIds, processInstanceId, task.getTaskDefinitionKey(), task.getId(), task.getName());
    }

    @Override
    public void createProcessInstanceCopy(Collection<Long> userIds, String processInstanceId, String activityId, String taskId, String taskName) {
        // 1.1 校验流程实例存在
        ProcessInstance processInstance = processInstanceService.getProcessInstance(processInstanceId);
        if (processInstance == null) {
            throw exception(ErrorCodeConstants.PROCESS_INSTANCE_NOT_EXISTS);
        }
        // 1.2 校验流程定义存在
        ProcessDefinition processDefinition = processDefinitionService.getProcessDefinition(
                processInstance.getProcessDefinitionId());
        if (processDefinition == null) {
            throw exception(ErrorCodeConstants.PROCESS_DEFINITION_NOT_EXISTS);
        }

        // 2. 创建抄送流程
        List<BpmProcessInstanceCopyDO> copyList = convertList(userIds, userId -> new BpmProcessInstanceCopyDO()
                .setUserId(userId).setStartUserId(Long.valueOf(processInstance.getStartUserId()))
                .setProcessInstanceId(processInstanceId).setProcessInstanceName(processInstance.getName())
                .setCategory(processDefinition.getCategory()).setActivityId(activityId)
                .setTaskId(taskId).setTaskName(taskName));
        processInstanceCopyMapper.insertBatch(copyList);
    }

    @Override
    public PageResult<BpmProcessInstanceCopyDO> getProcessInstanceCopyPage(Long userId,
                                                                           BpmProcessInstanceCopyPageReqVO pageReqVO) {
        return processInstanceCopyMapper.selectPage(userId, pageReqVO);
    }

    @Override
    public Set<Long> getCopyUserIds(String processInstanceId, String activityId) {
        return CollectionUtils.convertSet(processInstanceCopyMapper.selectListByProcessInstanceIdAndActivityId(processInstanceId, activityId),
                BpmProcessInstanceCopyDO::getUserId);
    }

}