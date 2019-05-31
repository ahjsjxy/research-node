package com.ronglian.kangrui.saas.research.admin.manager;

import com.ronglian.kangrui.saas.research.admin.biz.UserBiz;
import com.ronglian.kangrui.saas.research.admin.entity.User;
import com.ronglian.kangrui.saas.research.admin.mapper.UserMapper;
import com.ronglian.kangrui.saas.research.admin.vo.user.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-04-12 10:00
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserManager {

    @Autowired
    private UserBiz userBiz ;



    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    //@Cache(key="user{1}")
    public User getUserByUsername(String username){
        User user = new User();
        user.setUsername(username);
        return userBiz.selectOne(user) ;
    }


    /**
     * 获取全平台  未删除、启用的、审核通过的用户列表
     * @return
     */
    public List<UserVo> getUserList(){
        return userBiz.getUserList() ;
    }
}
