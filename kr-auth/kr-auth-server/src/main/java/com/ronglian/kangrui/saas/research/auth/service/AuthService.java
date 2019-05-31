package com.ronglian.kangrui.saas.research.auth.service;


import com.ronglian.kangrui.saas.research.admin.vo.user.UserInfo;

public interface AuthService {
    String login(String username, String password) throws Exception;
    String refresh(String oldToken);
    void validate(String token) throws Exception;
    Boolean invalid(String token);

    /**
     * 获取当个用户
     * @param username
     * @param password
     * @return
     */
    public UserInfo getOneUserInfo(String username, String password) ;
}
