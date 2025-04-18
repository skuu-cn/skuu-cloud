package cn.skuu.bpm.framework.flowable.core.listener;

import cn.skuu.bpm.service.task.BpmProcessInstanceService;
import com.google.common.collect.ImmutableSet;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEntityEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.engine.delegate.event.AbstractFlowableEngineEventListener;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 监听 {@link ProcessInstance} 的状态变更，更新其对应的 status 状态
 *
 * @author jason
 */
@Component
public class BpmProcessInstanceEventListener extends AbstractFlowableEngineEventListener {

    public static final Set<FlowableEngineEventType> PROCESS_INSTANCE_EVENTS = ImmutableSet.<FlowableEngineEventType>builder()
            .add(FlowableEngineEventType.PROCESS_COMPLETED)
            .build();

    @Resource
    @Lazy // 延迟加载，避免循环依赖
    private BpmProcessInstanceService processInstanceService;

    public BpmProcessInstanceEventListener(){
        super(PROCESS_INSTANCE_EVENTS);
    }

    @Override
    protected void processCompleted(FlowableEngineEntityEvent event) {
        processInstanceService.processProcessInstanceCompleted((ProcessInstance)event.getEntity());
    }

}
