package com.ronglian.kangrui.saas.research.auth.configuration;

import com.ronglian.kangrui.saas.research.auth.interceptor.UserAuthRestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ace on 2017/9/12.
 */
@Configuration
public class FeignConfiguration {
    @Bean
    UserAuthRestInterceptor getUserTokenInterceptor(){
        return new UserAuthRestInterceptor();
    }
}
