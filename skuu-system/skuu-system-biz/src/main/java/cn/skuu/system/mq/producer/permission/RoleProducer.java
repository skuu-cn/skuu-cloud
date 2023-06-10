package cn.skuu.system.mq.producer.permission;

import cn.skuu.framework.mq.core.bus.AbstractBusProducer;
import cn.skuu.system.mq.message.permission.RoleRefreshMessage;
import org.springframework.stereotype.Component;

/**
 * Role 角色相关消息的 Producer
 *
 * @author dcx
 */
@Component
public class RoleProducer extends AbstractBusProducer {

    /**
     * 发送 {@link RoleRefreshMessage} 消息
     */
    public void sendRoleRefreshMessage() {
        publishEvent(new RoleRefreshMessage(this, getBusId(), selfDestinationService()));
    }

}
