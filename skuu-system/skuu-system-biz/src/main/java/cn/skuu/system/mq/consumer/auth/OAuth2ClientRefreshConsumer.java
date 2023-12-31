package cn.skuu.system.mq.consumer.auth;

import cn.skuu.system.mq.message.auth.OAuth2ClientRefreshMessage;
import cn.skuu.system.service.oauth2.OAuth2ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link OAuth2ClientRefreshMessage} 的消费者
 *
 * @author dcx
 */
@Component
@Slf4j
public class OAuth2ClientRefreshConsumer {

    @Resource
    private OAuth2ClientService oauth2ClientService;

    @EventListener
    public void execute(OAuth2ClientRefreshMessage message) {
        log.info("[execute][收到 OAuth2Client 刷新消息]");
        oauth2ClientService.initLocalCache();
    }

}
