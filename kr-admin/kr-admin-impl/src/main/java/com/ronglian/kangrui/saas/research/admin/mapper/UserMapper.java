package com.ronglian.kangrui.saas.research.admin.mapper;

import com.ronglian.kangrui.saas.research.admin.entity.User;
import com.ronglian.kangrui.saas.research.admin.vo.user.UserVo;
import com.ronglian.kangrui.saas.research.common.mapper.MyBaseMapper;

import java.util.List;

public interface UserMapper extends MyBaseMapper<User> {

    public List<UserVo> getUserList() ;

}