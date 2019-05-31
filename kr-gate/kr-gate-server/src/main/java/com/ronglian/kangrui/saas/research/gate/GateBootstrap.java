package com.ronglian.kangrui.saas.research.gate;


import com.ronglian.kangrui.saas.research.gate.ratelimit.config.IUserPrincipal;
import com.ronglian.kangrui.saas.research.gate.utils.DBLog;
import com.ronglian.kangrui.saas.research.auth.client.EnableAceAuthClient;
import com.ronglian.kangrui.saas.research.gate.config.UserPrincipal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * Created by Ace on 2017/6/2.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(
        {
                "com.ronglian.kangrui.saas.research.auth.client.feign",
                "com.ronglian.kangrui.saas.research.admin.api"
        }
)
@EnableZuulProxy
//@EnableScheduling
@EnableAceAuthClient
//@EnableAceGateRateLimit
public class GateBootstrap {
    public static void main(String[] args) {
        DBLog.getInstance().start();
        SpringApplication.run(GateBootstrap.class, args);
    }

    @Bean
    @Primary
    IUserPrincipal userPrincipal(){
        return new UserPrincipal();
    }
}
