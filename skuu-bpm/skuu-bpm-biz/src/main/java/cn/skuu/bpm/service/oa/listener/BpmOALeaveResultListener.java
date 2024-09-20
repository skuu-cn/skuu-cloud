package cn.skuu.bpm.service.oa.listener;

import cn.skuu.bpm.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import cn.skuu.bpm.framework.bpm.core.event.BpmProcessInstanceResultEventListener;
import cn.skuu.bpm.service.oa.BpmOALeaveService;
import cn.skuu.bpm.service.oa.BpmOALeaveServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * OA 请假单的结果的监听器实现类
 *
 * @author skuu
 */
@Component
public class BpmOALeaveResultListener extends BpmProcessInstanceResultEventListener {

    @Resource
    private BpmOALeaveService leaveService;

    @Override
    protected String getProcessDefinitionKey() {
        return BpmOALeaveServiceImpl.PROCESS_KEY;
    }

    @Override
    protected void onEvent(BpmProcessInstanceResultEvent event) {
        leaveService.updateLeaveResult(Long.parseLong(event.getBusinessKey()), event.getResult());
    }

}
