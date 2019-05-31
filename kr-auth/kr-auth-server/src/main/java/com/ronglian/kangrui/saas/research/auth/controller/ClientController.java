package com.ronglian.kangrui.saas.research.auth.controller;

import com.ronglian.kangrui.saas.research.auth.configuration.KeyConfiguration;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ace on 2017/9/10.
 */
@RestController
@RequestMapping("client")
public class ClientController{
    @Autowired
    private KeyConfiguration  keyConfiguration;

    @RequestMapping(value = "/userPubKey",method = RequestMethod.POST)
    public ObjectRestResponse<byte[]> getUserPublicKey() throws Exception {
        return new ObjectRestResponse<byte[]>().data(keyConfiguration.getUserPubKey());
    }


}
