package cn.skuu.system.mq.message.permission;

import lombok.Data;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 角色与菜单数据刷新 Message
 *
 * @author dcx
 */
@Data
public class RoleMenuRefreshMessage extends RemoteApplicationEvent {

    public RoleMenuRefreshMessage() {
    }

    public RoleMenuRefreshMessage(Object source, String originService, String destinationService) {
        super(source, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
    }

}
