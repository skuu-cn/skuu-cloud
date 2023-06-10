package cn.skuu.system.mq.consumer.sensitiveword;

import cn.skuu.system.mq.message.sensitiveword.SensitiveWordRefreshMessage;
import cn.skuu.system.service.sensitiveword.SensitiveWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link SensitiveWordRefreshMessage} 的消费者
 *
 * @author dcx
 */
@Component
@Slf4j
public class SensitiveWordRefreshConsumer {

    @Resource
    private SensitiveWordService sensitiveWordService;

    @EventListener
    public void execute(SensitiveWordRefreshMessage message) {
        log.info("[execute][收到 SensitiveWord 刷新消息]");
        sensitiveWordService.initLocalCache();
    }

}
