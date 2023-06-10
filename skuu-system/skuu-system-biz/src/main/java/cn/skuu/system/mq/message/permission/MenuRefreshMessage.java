package cn.skuu.system.mq.message.permission;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 菜单数据刷新 Message
 *
 * @author dcx
 */
public class MenuRefreshMessage extends RemoteApplicationEvent {

    public MenuRefreshMessage() {
    }

    public MenuRefreshMessage(Object source, String originService, String destinationService) {
        super(source, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
    }

}
