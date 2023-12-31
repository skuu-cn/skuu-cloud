package cn.skuu.system.mq.consumer.permission;

import cn.skuu.system.mq.message.permission.MenuRefreshMessage;
import cn.skuu.system.service.permission.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link MenuRefreshMessage} 的消费者
 *
 * @author dcx
 */
@Component
@Slf4j
public class MenuRefreshConsumer {

    @Resource
    private MenuService menuService;

    @EventListener
    public void execute(MenuRefreshMessage menuRefreshMessage) {
        log.info("[execute][收到 Menu 刷新消息]");
        menuService.initLocalCache();
    }
}
