package com.ronglian.kangrui.saas.research.auth.client.interceptor;

import com.ronglian.kangrui.saas.research.auth.client.config.UserAuthConfig;
import com.ronglian.kangrui.saas.research.common.context.BaseContextHandler;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author kr
 * @date 2017/9/15
 */
@Configuration
public class ServiceFeignInterceptor implements RequestInterceptor {
    private Logger logger = LoggerFactory.getLogger(ServiceFeignInterceptor.class);
    @Autowired
    private UserAuthConfig userAuthConfig;

    public ServiceFeignInterceptor() {
    }


    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(userAuthConfig.getTokenHeader(), BaseContextHandler.getToken());
    }


    public void setUserAuthConfig(UserAuthConfig userAuthConfig) {
        this.userAuthConfig = userAuthConfig;
    }

}