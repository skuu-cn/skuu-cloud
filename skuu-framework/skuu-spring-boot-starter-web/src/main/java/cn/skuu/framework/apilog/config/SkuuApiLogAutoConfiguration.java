package cn.skuu.framework.apilog.config;

import cn.skuu.framework.apilog.core.filter.ApiAccessLogFilter;
import cn.skuu.framework.apilog.core.interceptor.ApiAccessLogInterceptor;
import cn.skuu.framework.common.enums.WebFilterOrderEnum;
import cn.skuu.framework.web.config.SkuuWebAutoConfiguration;
import cn.skuu.framework.web.config.WebProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@AutoConfiguration
@AutoConfigureAfter(SkuuWebAutoConfiguration.class)
public class SkuuApiLogAutoConfiguration implements WebMvcConfigurer {
    /**
     * 创建 ApiAccessLogFilter Bean，记录 API 请求日志
     */
    @Bean
    @ConditionalOnProperty(prefix = "skuu.access-log", value = "enable", matchIfMissing = true)
    // 允许使用 skuu.access-log.enable=false 禁用访问日志
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public FilterRegistrationBean<ApiAccessLogFilter> apiAccessLogFilter(WebProperties webProperties,
                                                                         @Value("${spring.application.name}") String applicationName) {
        ApiAccessLogFilter filter = new ApiAccessLogFilter(webProperties, applicationName);
        return createFilterBean(filter, WebFilterOrderEnum.API_ACCESS_LOG_FILTER);
    }

    private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(order);
        return bean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiAccessLogInterceptor());
    }

}
