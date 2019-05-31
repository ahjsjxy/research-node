package com.ronglian.kangrui.saas.research.auth.client.runner;

import com.ronglian.kangrui.saas.research.auth.client.config.UserAuthConfig;
import com.ronglian.kangrui.saas.research.common.msg.BaseResponse;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import com.ronglian.kangrui.saas.research.auth.client.feign.ServiceAuthFeign;

/**
 * 监听完成时触发
 *
 * @author kr
 * @create 2017/11/29.
 */
@Configuration
@Slf4j
public class AuthClientRunner implements CommandLineRunner {

    @Autowired
    private UserAuthConfig    userAuthConfig;

    @Autowired
    private ServiceAuthFeign  serviceAuthFeign;

    @Override
    public void run(String... args) throws Exception {
        log.info("初始化加载用户pubKey");
        BaseResponse resp = serviceAuthFeign.getUserPublicKey();
        if (resp.getStatus() == 200) {
            ObjectRestResponse<byte[]> userResponse = (ObjectRestResponse<byte[]>) resp;
            this.userAuthConfig.setPubKeyByte(userResponse.getData());
        }
    }
}