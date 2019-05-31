package com.ronglian.kangrui.saas.research.auth.client.configuration;

import com.ronglian.kangrui.saas.research.auth.client.config.UserAuthConfig;
//import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kr on 2017/9/15.
 */
@Configuration
@ComponentScan({"com.ronglian.kangrui.saas.research.auth.client", "com.ronglian.kangrui.saas.research.auth.common.event"})
//@RemoteApplicationEventScan(basePackages = "com.ronglian.kangrui.auth.common.event")
public class AutoConfiguration {

    @Bean
    UserAuthConfig getUserAuthConfig(){
        return new UserAuthConfig();
    }

}
