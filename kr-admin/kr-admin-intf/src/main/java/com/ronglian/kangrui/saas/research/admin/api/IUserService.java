package com.ronglian.kangrui.saas.research.admin.api;

import com.ronglian.kangrui.saas.research.admin.vo.authority.PermissionInfo;
import com.ronglian.kangrui.saas.research.admin.vo.user.UserVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author kr
 * @create 2017-06-21 8:11
 */
@FeignClient(value = "kr-admin")
public interface IUserService {

    @RequestMapping(value="/api/user/un/{username}/permissions",method = RequestMethod.GET)
    public List<PermissionInfo> getPermissionByUsername(@PathVariable("username") String username);


    @RequestMapping(value="/api/permissions",method = RequestMethod.GET)
    public List<PermissionInfo> getAllPermissionInfo();


    @RequestMapping(value="/api/user/getUserList",method = RequestMethod.GET)
    public List<UserVo> getUserList();
}