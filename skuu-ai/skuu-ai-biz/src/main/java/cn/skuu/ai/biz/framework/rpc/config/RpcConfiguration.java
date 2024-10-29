package cn.skuu.ai.biz.biz.framework.rpc.config;

import cn.skuu.infra.api.file.FileApi;
import cn.skuu.system.api.dict.DictDataApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {DictDataApi.class, FileApi.class})
public class RpcConfiguration {
}
