package cn.skuu.member.framework.rpc.config;

import cn.skuu.system.api.logger.LoginLogApi;
import cn.skuu.system.api.sms.SmsCodeApi;
import cn.skuu.system.api.social.SocialClientApi;
import cn.skuu.system.api.social.SocialUserApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {SmsCodeApi.class, LoginLogApi.class, SocialUserApi.class, SocialClientApi.class})
public class RpcConfiguration {
}
