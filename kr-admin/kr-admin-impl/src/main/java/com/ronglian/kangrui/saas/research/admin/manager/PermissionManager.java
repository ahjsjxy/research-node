package com.ronglian.kangrui.saas.research.admin.manager;

import com.ronglian.kangrui.saas.research.admin.entity.User;
import com.ronglian.kangrui.saas.research.admin.vo.user.UserInfo;
import com.ronglian.kangrui.saas.research.common.constant.UserConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * Created by ace on 2017/9/12.
 */
@Service
public class PermissionManager {
    @Autowired
    private UserManager     userManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT);


    public UserInfo validate(String username, String password){
        UserInfo info = new UserInfo();
        User user = userManager.getUserByUsername(username);
        if (user!=null && encoder.matches(password, user.getPassword())) {
            BeanUtils.copyProperties(user, info);
            info.setId(user.getId().toString());
        }
        return info;
    }


}
