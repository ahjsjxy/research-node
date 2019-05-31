package com.ronglian.kangrui.saas.research.sci.mapper;


import com.ronglian.kangrui.saas.research.common.mapper.MyBaseMapper;
import com.ronglian.kangrui.saas.research.sci.entity.StudyUser;
import com.ronglian.kangrui.saas.research.sci.vo.StudyUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudyUserMapper extends MyBaseMapper<StudyUser> {

    public List<StudyUserVo> getUserListByStudyId(@Param(value = "studyId") Long studyId) ;


    public List<StudyUserVo> getValidUserListByStudyId(@Param(value = "studyId") Long studyId) ;

}