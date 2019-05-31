package com.ronglian.kangrui.saas.research.auth.service.impl;

import com.ronglian.kangrui.saas.research.auth.feign.IUserService;
import com.ronglian.kangrui.saas.research.auth.service.AuthService;
import com.ronglian.kangrui.saas.research.auth.util.user.JwtTokenUtil;
import com.ronglian.kangrui.saas.research.admin.vo.user.UserInfo;
import com.ronglian.kangrui.saas.research.auth.common.util.jwt.JWTInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthServiceImpl implements AuthService {

    private JwtTokenUtil jwtTokenUtil;
    private IUserService userService;

    @Autowired
    public AuthServiceImpl(
            JwtTokenUtil jwtTokenUtil,
            IUserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @Override
    public String login(String username, String password) throws Exception {
        UserInfo info = userService.validate(username,password);
        String token = "";
        if (!StringUtils.isEmpty(info.getId())) {
            token = jwtTokenUtil.generateToken(new JWTInfo(info.getUsername(), info.getId() + "", info.getName()));
        }
        return token;
    }


    /**
     * 获取当个用户
     * @param username
     * @param password
     * @return
     */
    @Override
    public UserInfo getOneUserInfo(String username, String password) {
        UserInfo info = userService.validate(username,password);
        return  info ;
    }


    @Override
    public void validate(String token) throws Exception {
        jwtTokenUtil.getInfoFromToken(token);
    }

    @Override
    public Boolean invalid(String token) {
        // TODO: 2017/9/11 注销token
        return null;
    }

    @Override
    public String refresh(String oldToken) {
        // TODO: 2017/9/11 刷新token
        return null;
    }
}
