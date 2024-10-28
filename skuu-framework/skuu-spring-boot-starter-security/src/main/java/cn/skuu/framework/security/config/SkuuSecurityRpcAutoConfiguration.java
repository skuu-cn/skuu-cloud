package cn.skuu.framework.security.config;

import cn.skuu.framework.security.core.rpc.LoginUserRequestInterceptor;
import cn.skuu.framework.security.core.rpc.OAuth2TokenClient;
import cn.skuu.framework.security.core.rpc.PermissionClient;
import cn.skuu.framework.security.core.rpc.TenantClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * Security 使用到 Feign 的配置项
 *
 * @author dcx
 */
@AutoConfiguration
@EnableFeignClients(clients = {OAuth2TokenClient.class, // 主要是引入相关的 API 服务
        PermissionClient.class, TenantClient.class})
public class SkuuSecurityRpcAutoConfiguration {

    @Bean
    public LoginUserRequestInterceptor loginUserRequestInterceptor() {
        return new LoginUserRequestInterceptor();
    }

}
