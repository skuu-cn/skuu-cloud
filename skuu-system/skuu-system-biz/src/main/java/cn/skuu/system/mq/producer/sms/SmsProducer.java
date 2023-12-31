package cn.skuu.system.mq.producer.sms;

import cn.skuu.framework.common.core.KeyValue;
import cn.skuu.framework.mq.core.bus.AbstractBusProducer;
import cn.skuu.system.mq.message.sms.SmsChannelRefreshMessage;
import cn.skuu.system.mq.message.sms.SmsSendMessage;
import cn.skuu.system.mq.message.sms.SmsTemplateRefreshMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Sms 短信相关消息的 Producer
 *
 * @author zzf
 * @date 2021/3/9 16:35
 */
@Slf4j
@Component
public class SmsProducer extends AbstractBusProducer {

    @Resource
    private StreamBridge streamBridge;

    /**
     * 发送 {@link SmsChannelRefreshMessage} 消息
     */
    public void sendSmsChannelRefreshMessage() {
        publishEvent(new SmsChannelRefreshMessage(this, getBusId(), selfDestinationService()));
    }

    /**
     * 发送 {@link SmsTemplateRefreshMessage} 消息
     */
    public void sendSmsTemplateRefreshMessage() {
        publishEvent(new SmsTemplateRefreshMessage(this, getBusId(), selfDestinationService()));
    }

    /**
     * 发送 {@link SmsSendMessage} 消息
     *
     * @param logId 短信日志编号
     * @param mobile 手机号
     * @param channelId 渠道编号
     * @param apiTemplateId 短信模板编号
     * @param templateParams 短信模板参数
     */
    public void sendSmsSendMessage(Long logId, String mobile,
                                   Long channelId, String apiTemplateId, List<KeyValue<String, Object>> templateParams) {
        SmsSendMessage message = new SmsSendMessage().setLogId(logId).setMobile(mobile);
        message.setChannelId(channelId).setApiTemplateId(apiTemplateId).setTemplateParams(templateParams);
        streamBridge.send("smsSend-out-0", message);
    }

}
