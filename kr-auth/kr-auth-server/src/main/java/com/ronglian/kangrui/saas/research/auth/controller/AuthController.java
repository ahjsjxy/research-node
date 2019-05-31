package com.ronglian.kangrui.saas.research.auth.controller;

import com.ronglian.kangrui.saas.research.auth.service.AuthService;
import com.ronglian.kangrui.saas.research.auth.util.user.JwtAuthenticationRequest;
import com.ronglian.kangrui.saas.research.auth.util.user.JwtAuthenticationResponse;
import com.ronglian.kangrui.saas.research.admin.vo.user.UserInfo;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("jwt")
public class AuthController {
    @Value("${jwt.token-header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "token", method = RequestMethod.POST)
    public ObjectRestResponse<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws Exception {
        final String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        // return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        if (StringUtils.isNotBlank(token)) {
            return new ObjectRestResponse().rel(Boolean.TRUE).data(new JwtAuthenticationResponse(token)) ;
        }else {
            return new ObjectRestResponse().rel(Boolean.FALSE).data("accountError") ;
        }
    }

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }

    @RequestMapping(value = "verify", method = RequestMethod.GET)
    public ResponseEntity<?> verify(String token) throws Exception {
        authService.validate(token);
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "invalid", method = RequestMethod.POST)
    public ResponseEntity<?> invalid(String token){
        authService.invalid(token);
        return ResponseEntity.ok(true);
    }
}
