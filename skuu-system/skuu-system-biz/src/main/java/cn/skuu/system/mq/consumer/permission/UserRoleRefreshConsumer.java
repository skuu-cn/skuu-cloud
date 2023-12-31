package cn.skuu.system.mq.consumer.permission;

import cn.skuu.system.mq.message.permission.UserRoleRefreshMessage;
import cn.skuu.system.service.permission.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link UserRoleRefreshMessage} 的消费者
 *
 * @author dcx
 */
@Component
@Slf4j
public class UserRoleRefreshConsumer  {

    @Resource
    private PermissionService permissionService;

    @EventListener
    public void execute(UserRoleRefreshMessage userRoleRefreshMessage) {
        log.info("[execute][收到 User 与 Role 的关联刷新消息]");
        permissionService.initLocalCache();
    }
}
