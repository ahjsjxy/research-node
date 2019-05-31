package com.ronglian.kangrui.saas.research.sci.rest;

import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.sci.entity.Field;
import com.ronglian.kangrui.saas.research.sci.manager.StudyUserManager;
import com.ronglian.kangrui.saas.research.sci.vo.FieldConvertVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyUserVo;
import com.ronglian.kangrui.saas.research.sci.vo.UserConvertVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 15:04
 **/
@RestController
@RequestMapping("studyUser")
@Slf4j
public class StudyUserRest {

    @Autowired
    private StudyUserManager studyUserManager ;


    @ApiOperation("项目或角色下-用户列表")
    @RequestMapping(value = "getUserListByObjectId",method = RequestMethod.GET)
    public List<StudyUserVo> getUserListByObjectId(@RequestParam Long objectId,
                                                   @RequestParam(defaultValue = "1") int treeStudyFlag) {
        return studyUserManager.getUserListByObjectId(objectId, treeStudyFlag) ;
    }



    @ApiOperation("项目或角色下-未选择用户")
    @RequestMapping(value = "getValidUserListByObjectId",method = RequestMethod.GET)
    public List<StudyUserVo> getValidUserListByObjectId(@RequestParam Long objectId,
                                                   @RequestParam(defaultValue = "1") int treeStudyFlag){
        return studyUserManager.getValidUserListByObjectId(objectId, treeStudyFlag) ;
    }



    @ApiOperation("项目或角色下-保存用户")
    @RequestMapping(value = "saveUserInfo",method = RequestMethod.POST)
    public ObjectRestResponse saveUserInfo(@RequestBody UserConvertVo userConvertVo) {
        if (userConvertVo==null || userConvertVo.getObjectId()==null) {
            return new ObjectRestResponse().rel(Boolean.FALSE)  ;
        }

        // 保存用户
        boolean flag = studyUserManager.saveUserInfo(userConvertVo.getObjectId(),
                        userConvertVo.getTreeStudyFlag(), userConvertVo.getSelectUserList()) ;
        return new ObjectRestResponse().rel(flag)  ;
    }


}
