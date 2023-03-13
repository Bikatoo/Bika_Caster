package com.bikatoo.lancer.starter.config;

import com.bikatoo.lancer.common.json.JsonReturnHandler;
import com.bikatoo.lancer.starter.interceptor.LoginInterceptor;
import com.bikatoo.lancer.starter.interceptor.RequestLogInterceptor;
import com.bikatoo.lancer.starter.interceptor.SystemLoginInterceptor;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LancerConfig implements WebMvcConfigurer, WebMvcRegistrations {

    @Resource
    private LoginInterceptor loginInterceptor;
    @Resource
    private SystemLoginInterceptor systemLoginInterceptor;

    @Resource
    private RequestLogInterceptor requestLogInterceptor;

    @Bean
    public JsonReturnHandler jsonReturnHandler() {
        return new JsonReturnHandler();
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(jsonReturnHandler());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        // SYSTEM
        List<String> systemLoginExcludeMapping = Lists.newArrayList();
        systemLoginExcludeMapping.add("/system/account/login");
        systemLoginExcludeMapping.add("/system/admin/**");
        registry.addInterceptor(systemLoginInterceptor).addPathPatterns("/system/**").excludePathPatterns(systemLoginExcludeMapping);
        registry.addInterceptor(requestLogInterceptor).addPathPatterns("/**")
                .excludePathPatterns(Lists.newArrayList("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs"));


        // APP
        List<String> loginExcludeMapping = Lists.newArrayList();
        loginExcludeMapping.add("/app/user/login");
        loginExcludeMapping.add("/app/user/captcha");
        loginExcludeMapping.add("/app/waterfall_flow");
        loginExcludeMapping.add("/app/banner");
        loginExcludeMapping.add("/app/test");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/app/**").excludePathPatterns(loginExcludeMapping);

    }
}
