package com.ronglian.kangrui.saas.research.admin.biz;


import com.ronglian.kangrui.saas.research.admin.entity.User;
import com.ronglian.kangrui.saas.research.admin.mapper.UserMapper;
import com.ronglian.kangrui.saas.research.admin.vo.user.UserVo;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-24 11:21
 **/
@Service
@Slf4j
public class UserBiz extends BaseBiz<UserMapper,User> {

    @Autowired
    private UserMapper userMapper ;


    /**
     * 获取全平台  未删除、启用的、审核通过的用户列表
     * @return
     */
    public List<UserVo> getUserList(){
        return userMapper.getUserList() ;
    }

}
