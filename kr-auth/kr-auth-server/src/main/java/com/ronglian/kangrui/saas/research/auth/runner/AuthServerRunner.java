package com.ronglian.kangrui.saas.research.auth.runner;

import com.ronglian.kangrui.saas.research.auth.common.util.jwt.RsaKeyHelper;
import com.ronglian.kangrui.saas.research.auth.configuration.KeyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

/**
 * @author ace
 * @create 2017/12/17.
 */
@Configuration
public class AuthServerRunner implements CommandLineRunner {
    private static final String REDIS_USER_PRI_KEY = "KR:AUTH:JWT:PRI";
    private static final String REDIS_USER_PUB_KEY = "KR:AUTH:JWT:PUB";
    @Autowired
    private KeyConfiguration keyConfiguration;

    @Override
    public void run(String... args) throws Exception {
            Map<String, byte[]> keyMap = RsaKeyHelper.generateKey(keyConfiguration.getUserSecret());
            keyConfiguration.setUserPriKey(keyMap.get("pri"));
            keyConfiguration.setUserPubKey(keyMap.get("pub"));
    }
}
