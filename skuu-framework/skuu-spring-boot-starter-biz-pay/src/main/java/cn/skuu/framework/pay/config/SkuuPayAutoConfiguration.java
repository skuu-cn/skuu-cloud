package cn.skuu.framework.pay.config;

import cn.skuu.framework.pay.core.client.PayClientFactory;
import cn.skuu.framework.pay.core.client.impl.PayClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 支付配置类
 *
 * @author dcx
 */
@AutoConfiguration
@EnableConfigurationProperties(PayProperties.class)
public class SkuuPayAutoConfiguration {

    @Bean
    public PayClientFactory payClientFactory() {
        return new PayClientFactoryImpl();
    }

}
