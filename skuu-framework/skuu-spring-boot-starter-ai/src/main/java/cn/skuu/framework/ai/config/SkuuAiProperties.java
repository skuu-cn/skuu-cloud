package cn.skuu.framework.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * skuu AI 配置类
 *
 * @author fansili
 * @since 1.0
 */
@ConfigurationProperties(prefix = "skuu.ai")
@Data
public class SkuuAiProperties {

    /**
     * DeepSeek
     */
    private DeepSeekProperties deepSeek;

    /**
     * 讯飞星火
     */
    private XingHuoProperties xinghuo;

    /**
     * Midjourney 绘图
     */
    private MidjourneyProperties midjourney;

    /**
     * Suno 音乐
     */
    private SunoProperties suno;

    @Data
    public static class XingHuoProperties {

        private String enable;
        private String appId;
        private String appKey;
        private String secretKey;

        private String model;
        private Float temperature;
        private Integer maxTokens;
        private Integer topK;

    }

    @Data
    public static class DeepSeekProperties {

        private String enable;
        private String apiKey;

        private String model;
        private Float temperature;
        private Integer maxTokens;
        private Float topP;

    }

    @Data
    public static class MidjourneyProperties {

        private String enable;
        private String baseUrl;

        private String apiKey;
        private String notifyUrl;

    }

    @Data
    public static class SunoProperties {

        private boolean enable = false;

        private String baseUrl;

    }

}