package cn.skuu.system.mq.producer.dept;

import cn.skuu.framework.mq.core.bus.AbstractBusProducer;
import cn.skuu.system.mq.message.dept.DeptRefreshMessage;
import org.springframework.stereotype.Component;

/**
 * Dept 部门相关消息的 Producer
 */
@Component
public class DeptProducer extends AbstractBusProducer {

    /**
     * 发送 {@link DeptRefreshMessage} 消息
     */
    public void sendDeptRefreshMessage() {
        publishEvent(new DeptRefreshMessage(this, getBusId(), selfDestinationService()));
    }

}
