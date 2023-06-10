package cn.skuu.system.mq.message.permission;

import lombok.Data;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 角色数据刷新 Message
 *
 * @author dcx
 */
@Data
public class RoleRefreshMessage extends RemoteApplicationEvent {

    public RoleRefreshMessage() {
    }

    public RoleRefreshMessage(Object source, String originService, String destinationService) {
        super(source, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
    }

}
