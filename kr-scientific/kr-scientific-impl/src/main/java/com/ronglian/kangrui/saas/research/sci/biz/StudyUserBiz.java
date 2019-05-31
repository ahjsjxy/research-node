package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.entity.StudyUser;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyRoleUserMapper;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyUserMapper;
import com.ronglian.kangrui.saas.research.sci.vo.StudyUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 15:37
 **/
@Service
public class StudyUserBiz extends BaseBiz<StudyUserMapper,StudyUser> {

    @Autowired
    private StudyUserMapper studyUserMapper ;


    /**
     * 查询项目下的 用户列表
     * @param studyId
     * @return
     */
    public List<StudyUserVo> getUserListByStudyId(Long studyId) {
        return studyUserMapper.getUserListByStudyId(studyId) ;
    }


    /**
     * 查询项目下的 未选择用户列表
     * @param studyId
     * @return
     */
    public List<StudyUserVo> getValidUserListByStudyId(Long studyId) {
        return studyUserMapper.getValidUserListByStudyId(studyId) ;
    }


    /**
     * 项目下- 用户 保存
     * @param studyId
     * @param selectUserList
     * @return
     */
    public boolean saveUserInfoByStudyId(Long studyId, List<StudyUserVo> selectUserList) {
        boolean flag = Boolean.FALSE ;

        // 1 : 根据study id 删除 study_user
        StudyUser studyUser = new StudyUser() ;
        studyUser.setStudyId(studyId);
        this.delete(studyUser);

        // 2 ：根据study id 插入 study_user
        if (selectUserList!=null && selectUserList.size()>0) {
            for (StudyUserVo studyUserVo: selectUserList) {
                StudyUser temp = new StudyUser() ;
                temp.setStudyId(studyId);
                temp.setUserId(studyUserVo.getId());
                this.insertSelective(studyUser) ;
            }
        }

        flag = Boolean.TRUE ;
        return flag ;
    }

}
