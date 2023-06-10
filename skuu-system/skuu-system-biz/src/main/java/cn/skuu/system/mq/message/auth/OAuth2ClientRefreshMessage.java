package cn.skuu.system.mq.message.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * OAuth 2.0 客户端的数据刷新 Message
 *
 * @author dcx
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OAuth2ClientRefreshMessage extends RemoteApplicationEvent {

    public OAuth2ClientRefreshMessage() {
    }

    public OAuth2ClientRefreshMessage(Object source, String originService, String destinationService) {
        super(source, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
    }

}
