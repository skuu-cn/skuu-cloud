package cn.skuu.framework.env.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 环境配置
 *
 * @author dcx
 */
@ConfigurationProperties(prefix = "skuu.env")
@Data
public class EnvProperties {

    public static final String TAG_KEY = "skuu.env.tag";

    /**
     * 环境标签
     */
    private String tag;

}
