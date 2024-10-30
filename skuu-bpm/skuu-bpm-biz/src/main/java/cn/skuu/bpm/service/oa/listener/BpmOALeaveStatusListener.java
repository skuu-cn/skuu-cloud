package cn.skuu.bpm.service.oa.listener;

import cn.skuu.bpm.event.BpmProcessInstanceStatusEvent;
import cn.skuu.bpm.event.BpmProcessInstanceStatusEventListener;
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
public class BpmOALeaveStatusListener extends BpmProcessInstanceStatusEventListener {

    @Resource
    private BpmOALeaveService leaveService;

    @Override
    protected String getProcessDefinitionKey() {
        return BpmOALeaveServiceImpl.PROCESS_KEY;
    }

    @Override
    protected void onEvent(BpmProcessInstanceStatusEvent event) {
        leaveService.updateLeaveStatus(Long.parseLong(event.getBusinessKey()), event.getStatus());
    }

}
