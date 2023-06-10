package cn.skuu.system.mq.consumer.sms;

import cn.skuu.system.service.sms.SmsChannelService;
import cn.skuu.system.mq.message.sms.SmsChannelRefreshMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link SmsChannelRefreshMessage} 的消费者
 *
 * @author dcx
 */
@Component
@Slf4j
public class SmsChannelRefreshConsumer {

    @Resource
    private SmsChannelService smsChannelService;

    @EventListener
    public void execute(SmsChannelRefreshMessage message) {
        log.info("[execute][收到 SmsChannel 刷新消息]");
        smsChannelService.initLocalCache();
    }

}
