package cn.skuu.framework.security.config;

import cn.skuu.framework.security.core.rpc.LoginUserRequestInterceptor;
import cn.skuu.system.api.oauth2.OAuth2TokenApi;
import cn.skuu.system.api.permission.PermissionApi;
import cn.skuu.system.api.tenant.TenantApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * Security 使用到 Feign 的配置项
 *
 * @author dcx
 */
@AutoConfiguration
@EnableFeignClients(clients = {OAuth2TokenApi.class, // 主要是引入相关的 API 服务
        PermissionApi.class, TenantApi.class})
public class SkuuSecurityRpcAutoConfiguration {

    @Bean
    public LoginUserRequestInterceptor loginUserRequestInterceptor() {
        return new LoginUserRequestInterceptor();
    }

}
