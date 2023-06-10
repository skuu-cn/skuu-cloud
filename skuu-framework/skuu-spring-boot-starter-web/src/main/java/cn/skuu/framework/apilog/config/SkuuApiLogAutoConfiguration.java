//package cn.skuu.framework.apilog.config;
//
//import cn.skuu.framework.apilog.core.filter.ApiAccessLogFilter;
//import cn.skuu.framework.apilog.core.service.ApiAccessLogFrameworkService;
//import cn.skuu.framework.apilog.core.service.ApiAccessLogFrameworkServiceImpl;
//import cn.skuu.framework.apilog.core.service.ApiErrorLogFrameworkService;
//import cn.skuu.framework.apilog.core.service.ApiErrorLogFrameworkServiceImpl;
//import cn.skuu.framework.common.enums.WebFilterOrderEnum;
//import cn.skuu.framework.web.config.WebProperties;
//import cn.skuu.framework.web.config.SkuuWebAutoConfiguration;
//import cn.skuu.infra.api.logger.ApiAccessLogApi;
//import cn.skuu.infra.api.logger.ApiErrorLogApi;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.AutoConfiguration;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//
//import javax.servlet.Filter;
//
//@AutoConfiguration
//@AutoConfigureAfter(SkuuWebAutoConfiguration.class)
//public class SkuuApiLogAutoConfiguration {
//
//    @Bean
//    public ApiAccessLogFrameworkService apiAccessLogFrameworkService(ApiAccessLogApi apiAccessLogApi) {
//        return new ApiAccessLogFrameworkServiceImpl(apiAccessLogApi);
//    }
//
//    @Bean
//    public ApiErrorLogFrameworkService apiErrorLogFrameworkService(ApiErrorLogApi apiErrorLogApi) {
//        return new ApiErrorLogFrameworkServiceImpl(apiErrorLogApi);
//    }
//
//    /**
//     * 创建 ApiAccessLogFilter Bean，记录 API 请求日志
//     */
//    @Bean
//    @ConditionalOnProperty(prefix = "yudao.access-log", value = "enable", matchIfMissing = true) // 允许使用 yudao.access-log.enable=false 禁用访问日志
//    public FilterRegistrationBean<ApiAccessLogFilter> apiAccessLogFilter(WebProperties webProperties,
//                                                                         @Value("${spring.application.name}") String applicationName,
//                                                                         ApiAccessLogFrameworkService apiAccessLogFrameworkService) {
//        ApiAccessLogFilter filter = new ApiAccessLogFilter(webProperties, applicationName, apiAccessLogFrameworkService);
//        return createFilterBean(filter, WebFilterOrderEnum.API_ACCESS_LOG_FILTER);
//    }
//
//    private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
//        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
//        bean.setOrder(order);
//        return bean;
//    }
//
//}
