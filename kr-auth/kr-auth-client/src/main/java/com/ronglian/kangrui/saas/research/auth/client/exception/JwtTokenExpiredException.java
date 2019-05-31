package com.ronglian.kangrui.saas.research.auth.client.exception;

/**
 * Created by ace on 2017/9/15.
 */
public class JwtTokenExpiredException extends Exception {
    public JwtTokenExpiredException(String s) {
        super(s);
    }
}
