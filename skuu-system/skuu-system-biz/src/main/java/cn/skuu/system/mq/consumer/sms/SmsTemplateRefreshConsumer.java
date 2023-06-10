package cn.skuu.system.mq.consumer.sms;

import cn.skuu.system.service.sms.SmsTemplateService;
import cn.skuu.system.mq.message.sms.SmsTemplateRefreshMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link SmsTemplateRefreshMessage} 的消费者
 *
 * @author dcx
 */
@Component
@Slf4j
public class SmsTemplateRefreshConsumer {

    @Resource
    private SmsTemplateService smsTemplateService;

    @EventListener
    public void execute(SmsTemplateRefreshMessage message) {
        log.info("[execute][收到 SmsTemplate 刷新消息]");
        smsTemplateService.initLocalCache();
    }

}
