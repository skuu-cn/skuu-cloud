package cn.skuu.framework.operatelog.config;

import cn.skuu.framework.operatelog.core.aop.OperateLogAspect;
import cn.skuu.framework.operatelog.core.service.OperateLogFrameworkService;
import cn.skuu.framework.operatelog.core.service.OperateLogFrameworkServiceImpl;
import cn.skuu.system.api.logger.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class SkuuOperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService(OperateLogApi operateLogApi) {
        return new OperateLogFrameworkServiceImpl(operateLogApi);
    }

}
