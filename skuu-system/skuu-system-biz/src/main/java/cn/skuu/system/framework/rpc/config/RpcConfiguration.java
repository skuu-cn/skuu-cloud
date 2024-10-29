package cn.skuu.system.framework.rpc.config;

import cn.skuu.infra.api.config.ConfigApi;
import cn.skuu.infra.api.file.FileApi;
import cn.skuu.infra.api.websocket.WebSocketSenderApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {FileApi.class, WebSocketSenderApi.class, ConfigApi.class})
public class RpcConfiguration {
}
