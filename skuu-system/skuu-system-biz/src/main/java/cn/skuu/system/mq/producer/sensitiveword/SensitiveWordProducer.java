package cn.skuu.system.mq.producer.sensitiveword;

import cn.skuu.framework.mq.core.bus.AbstractBusProducer;
import cn.skuu.system.mq.message.sensitiveword.SensitiveWordRefreshMessage;
import org.springframework.stereotype.Component;

/**
 * 敏感词相关的 Producer
 */
@Component
public class SensitiveWordProducer extends AbstractBusProducer {{

}
    /**
     * 发送 {@link SensitiveWordRefreshMessage} 消息
     */
    public void sendSensitiveWordRefreshMessage() {
        publishEvent(new SensitiveWordRefreshMessage(this, getBusId(), selfDestinationService()));
    }

}
