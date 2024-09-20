package cn.skuu.bpm.framework.flowable.core.behavior.script.impl;

import cn.skuu.bpm.enums.definition.BpmTaskRuleScriptEnum;
import cn.skuu.bpm.framework.flowable.core.behavior.script.BpmTaskAssignScript;
import cn.skuu.bpm.service.task.BpmProcessInstanceService;
import cn.skuu.framework.common.util.collection.SetUtils;
import cn.skuu.framework.common.util.number.NumberUtils;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 分配给发起人审批的 Script 实现类
 *
 * @author skuu
 */
@Component
public class BpmTaskAssignStartUserScript implements BpmTaskAssignScript {

    @Resource
    @Lazy // 解决循环依赖
    private BpmProcessInstanceService bpmProcessInstanceService;

    @Override
    public Set<Long> calculateTaskCandidateUsers(DelegateExecution execution) {
        ProcessInstance processInstance = bpmProcessInstanceService.getProcessInstance(execution.getProcessInstanceId());
        Long startUserId = NumberUtils.parseLong(processInstance.getStartUserId());
        return SetUtils.asSet(startUserId);
    }

    @Override
    public BpmTaskRuleScriptEnum getEnum() {
        return BpmTaskRuleScriptEnum.START_USER;
    }

}
