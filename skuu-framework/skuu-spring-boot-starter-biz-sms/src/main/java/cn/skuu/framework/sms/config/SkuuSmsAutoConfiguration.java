package cn.skuu.framework.sms.config;

import cn.skuu.framework.sms.core.client.SmsClientFactory;
import cn.skuu.framework.sms.core.client.impl.SmsClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 短信配置类
 *
 * @author dcx
 */
@AutoConfiguration
public class SkuuSmsAutoConfiguration {

    @Bean
    public SmsClientFactory smsClientFactory() {
        return new SmsClientFactoryImpl();
    }

}
