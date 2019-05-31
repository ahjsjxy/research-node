package com.ronglian.kangrui.saas.research.admin.rpc;

import com.ronglian.kangrui.saas.research.admin.manager.PermissionManager;
import com.ronglian.kangrui.saas.research.admin.manager.UserManager;
import com.ronglian.kangrui.saas.research.admin.vo.user.UserInfo;
import com.ronglian.kangrui.saas.research.admin.vo.user.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author kr
 * @create 2017-06-21 8:15
 */
@RestController
@RequestMapping("api")
@Slf4j
public class UserService{
    @Autowired
    private PermissionManager permissionManager;

    @Autowired
    private UserManager userManager ;

    @RequestMapping(value = "/user/validate", method = RequestMethod.POST)
    public @ResponseBody
    UserInfo validate(String username, String password){
        return permissionManager.validate(username,password);
    }


    @RequestMapping(value="/user/getUserList",method = RequestMethod.GET)
    public List<UserVo> getUserList(){
        return userManager.getUserList();
    }


}
