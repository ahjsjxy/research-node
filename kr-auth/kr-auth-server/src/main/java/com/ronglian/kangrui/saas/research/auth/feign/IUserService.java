package com.ronglian.kangrui.saas.research.auth.feign;

import com.ronglian.kangrui.saas.research.auth.configuration.FeignConfiguration;
import com.ronglian.kangrui.saas.research.admin.vo.user.UserInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * ${DESCRIPTION}
 *
 * @author kr
 * @create 2017-06-21 8:11
 */
@FeignClient(value = "kr-admin",configuration = FeignConfiguration.class)
public interface IUserService {
  @RequestMapping(value = "/api/user/validate", method = RequestMethod.POST)
  public UserInfo validate(@RequestParam("username") String username, @RequestParam("password") String password);
}
