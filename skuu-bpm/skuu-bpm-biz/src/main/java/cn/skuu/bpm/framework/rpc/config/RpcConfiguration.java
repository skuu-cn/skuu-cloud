package cn.skuu.bpm.framework.rpc.config;

import cn.skuu.system.api.dept.DeptApi;
import cn.skuu.system.api.dept.PostApi;
import cn.skuu.system.api.dict.DictDataApi;
import cn.skuu.system.api.permission.RoleApi;
import cn.skuu.system.api.sms.SmsSendApi;
import cn.skuu.system.api.user.AdminUserApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {RoleApi.class, DeptApi.class, PostApi.class, AdminUserApi.class, SmsSendApi.class, DictDataApi.class})
public class RpcConfiguration {
}
