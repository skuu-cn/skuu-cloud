package cn.skuu.framework.file.config;

import cn.skuu.framework.file.core.client.FileClientFactory;
import cn.skuu.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 文件配置类
 *
 * @author dcx
 */
@AutoConfiguration
public class SkuuFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
