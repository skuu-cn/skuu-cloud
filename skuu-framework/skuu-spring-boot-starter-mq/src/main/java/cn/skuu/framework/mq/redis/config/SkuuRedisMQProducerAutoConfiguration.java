package cn.skuu.framework.mq.redis.config;

import cn.skuu.framework.mq.redis.core.RedisMQTemplate;
import cn.skuu.framework.mq.redis.core.interceptor.RedisMessageInterceptor;
import cn.skuu.framework.redis.config.SkuuRedisAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * Redis 消息队列 Producer 配置类
 *
 * @author skuu
 */
@Slf4j
@AutoConfiguration(after = SkuuRedisAutoConfiguration.class)
public class SkuuRedisMQProducerAutoConfiguration {

    @Bean
    public RedisMQTemplate redisMQTemplate(StringRedisTemplate redisTemplate,
                                           List<RedisMessageInterceptor> interceptors) {
        RedisMQTemplate redisMQTemplate = new RedisMQTemplate(redisTemplate);
        // 添加拦截器
        interceptors.forEach(redisMQTemplate::addInterceptor);
        return redisMQTemplate;
    }

}
