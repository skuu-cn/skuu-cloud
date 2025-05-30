package cn.skuu.bpm.framework.flowable.core.behavior;

import cn.hutool.core.collection.CollUtil;
import cn.skuu.bpm.framework.flowable.core.candidate.BpmTaskCandidateInvoker;
import cn.skuu.bpm.framework.flowable.core.util.FlowableUtils;
import cn.skuu.framework.common.util.collection.SetUtils;
import lombok.Setter;
import org.flowable.bpmn.model.Activity;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.flowable.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;

import java.util.Set;

/**
 * 自定义的【串行】的【多个】流程任务的 assignee 负责人的分配
 *
 * 本质上，实现和 {@link BpmParallelMultiInstanceBehavior} 一样，只是继承的类不一样
 *
 * @author skuu
 */
@Setter
public class BpmSequentialMultiInstanceBehavior extends SequentialMultiInstanceBehavior {

    private BpmTaskCandidateInvoker taskCandidateInvoker;

    public BpmSequentialMultiInstanceBehavior(Activity activity, AbstractBpmnActivityBehavior innerActivityBehavior) {
        super(activity, innerActivityBehavior);
    }

    /**
     * 逻辑和 {@link BpmParallelMultiInstanceBehavior#resolveNrOfInstances(DelegateExecution)} 类似
     *
     * 差异的点：是在【第二步】的时候，需要返回 LinkedHashSet 集合！因为它需要有序！
     */
    @Override
    protected int resolveNrOfInstances(DelegateExecution execution) {
        // 第一步，设置 collectionVariable 和 CollectionVariable
        // 从  execution.getVariable() 读取所有任务处理人的 key
        super.collectionExpression = null; // collectionExpression 和 collectionVariable 是互斥的
        super.collectionVariable = FlowableUtils.formatExecutionCollectionVariable(execution.getCurrentActivityId());
        // 从 execution.getVariable() 读取当前所有任务处理的人的 key
        super.collectionElementVariable = FlowableUtils.formatExecutionCollectionElementVariable(execution.getCurrentActivityId());

        // 第二步，获取任务的所有处理人
        @SuppressWarnings("unchecked")
        Set<Long> assigneeUserIds = (Set<Long>) execution.getVariable(super.collectionVariable, Set.class);
        if (assigneeUserIds == null) {
            assigneeUserIds = taskCandidateInvoker.calculateUsers(execution);
            execution.setVariable(super.collectionVariable, assigneeUserIds);
            if (CollUtil.isEmpty(assigneeUserIds)) {
                // 特殊：如果没有处理人的情况下，至少有一个 null 空元素，避免自动通过！
                // 这样，保证在 BpmUserTaskActivityBehavior 至少创建出一个 Task 任务
                // 用途：1）审批人为空时；2）审批类型为自动通过、自动拒绝时
                assigneeUserIds = SetUtils.asSet((Long) null);
            }
        }
        return assigneeUserIds.size();
    }

}
