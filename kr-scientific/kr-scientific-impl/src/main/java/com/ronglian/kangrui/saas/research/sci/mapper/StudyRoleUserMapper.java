package com.ronglian.kangrui.saas.research.sci.mapper;


import com.ronglian.kangrui.saas.research.common.mapper.MyBaseMapper;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRoleUser;
import com.ronglian.kangrui.saas.research.sci.vo.StudyUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudyRoleUserMapper extends MyBaseMapper<StudyRoleUser> {

    public List<StudyUserVo> getUserListByRoleId(@Param(value = "roleId") Long roleId) ;


    public List<StudyUserVo> getValidUserListByRoleId(@Param(value = "roleId") Long roleId) ;


}