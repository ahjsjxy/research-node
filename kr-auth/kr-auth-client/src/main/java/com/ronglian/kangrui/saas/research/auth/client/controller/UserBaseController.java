package com.ronglian.kangrui.saas.research.auth.client.controller;


import com.ronglian.kangrui.saas.research.auth.client.config.UserAuthConfig;
import com.ronglian.kangrui.saas.research.auth.client.jwt.UserAuthUtil;
import com.ronglian.kangrui.saas.research.auth.common.util.jwt.IJWTInfo;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.common.rest.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserBaseController<Biz extends BaseBiz,Entity> extends BaseController {

    @Autowired
    private UserAuthUtil userAuthUtil;
    @Autowired
    private UserAuthConfig userAuthConfig;

    protected IJWTInfo getCurrentUserInfo(){
        IJWTInfo user = null;
        try {
            user = userAuthUtil.getInfoFromToken(request.getHeader(userAuthConfig.getTokenHeader()));
        }catch (Exception e){
            // log.error("获取用户信息异常",e);
        }
        return user;
    }
}
