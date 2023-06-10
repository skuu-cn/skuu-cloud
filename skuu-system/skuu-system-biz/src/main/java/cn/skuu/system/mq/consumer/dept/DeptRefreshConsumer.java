package cn.skuu.system.mq.consumer.dept;

import cn.skuu.system.service.dept.DeptService;
import cn.skuu.system.mq.message.dept.DeptRefreshMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link DeptRefreshMessage} 的消费者
 *
 * @author dcx
 */
@Component
@Slf4j
public class DeptRefreshConsumer {

    @Resource
    private DeptService deptService;

    @EventListener
    public void execute(DeptRefreshMessage message) {
        log.info("[execute][收到 Dept 刷新消息]");
        deptService.initLocalCache();
    }

}
