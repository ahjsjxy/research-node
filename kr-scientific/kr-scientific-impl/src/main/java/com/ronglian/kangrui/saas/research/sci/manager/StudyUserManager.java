package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.admin.api.IUserService;
import com.ronglian.kangrui.saas.research.admin.vo.user.UserVo;
import com.ronglian.kangrui.saas.research.sci.biz.StudyRoleUserBiz;
import com.ronglian.kangrui.saas.research.sci.biz.StudyUserBiz;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRoleUser;
import com.ronglian.kangrui.saas.research.sci.entity.StudyUser;
import com.ronglian.kangrui.saas.research.sci.vo.StudyUserVo;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 15:05
 **/
@Service
public class StudyUserManager {

    @Autowired
    private StudyUserBiz studyUserBiz ;

    @Autowired
    private StudyRoleUserBiz studyRoleUserBiz ;


    /**
     *  查询项目或角色下-用户列表
     * @param objectId
     * @param treeStudyFlag
     * @return
     */
    public List<StudyUserVo> getUserListByObjectId(Long objectId, int treeStudyFlag) {
        List<StudyUserVo> studyUserVoList = null ;

        if (treeStudyFlag == ResearchConsts.TREE_STUDY_FLAG_STUDY) {
            // 查询项目下的 用户列表
            studyUserVoList = studyUserBiz.getUserListByStudyId(objectId) ;

        } else if (treeStudyFlag == ResearchConsts.TREE_STUDY_FLAG_ROLE){
            // 查询项目角色下的 用户列表
            studyUserVoList = studyRoleUserBiz.getUserListByRoleId(objectId) ;
        }

        return studyUserVoList ;
    }



    /**
     * 查询项目或角色下 - 未选择用户列表
     * @param objectId
     * @param treeStudyFlag
     * @return
     */
    public List<StudyUserVo> getValidUserListByObjectId(Long objectId, int treeStudyFlag) {
        List<StudyUserVo> validUserList = null ;

        if (treeStudyFlag == ResearchConsts.TREE_STUDY_FLAG_STUDY) {
            // 查询项目下的 未选择用户列表
            validUserList = studyUserBiz.getValidUserListByStudyId(objectId) ;

        } else if (treeStudyFlag == ResearchConsts.TREE_STUDY_FLAG_ROLE){
            // 查询项目角色下的 未选择用户列表
            validUserList = studyRoleUserBiz.getValidUserListByRoleId(objectId) ;
        }

        return validUserList ;
    }



    /**
     * 保存  项目或角色下-用户列表
     * @param objectId
     * @param treeStudyFlag
     * @param selectUserList
     * @return
     */
    @Transactional
    public boolean saveUserInfo(Long objectId, int treeStudyFlag, List<StudyUserVo> selectUserList) {
        boolean flag = Boolean.FALSE ;

        if (treeStudyFlag == ResearchConsts.TREE_STUDY_FLAG_STUDY) {
            // 项目下- 用户 保存
            flag = studyUserBiz.saveUserInfoByStudyId(objectId, selectUserList) ;

        } else if (treeStudyFlag == ResearchConsts.TREE_STUDY_FLAG_ROLE){
            // 项目角色下-用户 保存
            flag = studyRoleUserBiz.saveUserInfoByRoleId(objectId, selectUserList) ;

        }

        return flag ;
    }

}
