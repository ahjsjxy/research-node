package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.common.context.BaseContextHandler;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.consts.RoleTypeConsts;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRole;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRoleButton;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRoleUser;
import com.ronglian.kangrui.saas.research.sci.entity.StudyUser;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyRoleButtonMapper;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyRoleMapper;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyRoleUserMapper;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyUserMapper;
import com.ronglian.kangrui.saas.research.sci.vo.StudyRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-28 14:23
 **/
@Service
public class StudyRoleBiz extends BaseBiz<StudyRoleMapper, StudyRole> {

    @Autowired
    private StudyRoleMapper studyRoleMapper ;

    @Autowired
    private StudyRoleButtonMapper studyRoleButtonMapper ;

    @Autowired
    private StudyRoleUserMapper studyRoleUserMapper ;

    @Autowired
    private StudyUserMapper studyUserMapper ;


    /**
     *   给项目创建角色(每个项目默认创建4个角色)
     *   每个角色赋值按钮权限
     *   项目创建者为默认为项目管理员
     *
     * @param studyId
     */
    public void addStudyRoleInfo(Long studyId){
        // 新建项目-角色
        for (RoleTypeConsts.RoleTypeEnum roleTypeEnum : RoleTypeConsts.RoleTypeEnum.values()) {
            StudyRole studyRole = new StudyRole() ;
            studyRole.setName(roleTypeEnum.getName());
            studyRole.setAllowOperate(ResearchConsts.ALLOW_OPERATE_NO);//1：禁止修改删除
            studyRole.setDeleted(ResearchConsts.DELETED_NO);//0：未删除
            studyRole.setCreateUser(BaseContextHandler.getUserID());
            studyRole.setUpdateTime(new Date());
            studyRole.setStudyId(studyId);
            studyRole = this.insertSelective(studyRole) ;


            // 角色赋值按钮权限
            List<String> buttonCodeList = Arrays.asList(roleTypeEnum.getButtonArray());
            for (String buttonCode: buttonCodeList) {
                StudyRoleButton studyRoleButton = new StudyRoleButton() ;
                studyRoleButton.setButtonCode(buttonCode);
                studyRoleButton.setRoleId(studyRole.getId());
                studyRoleButtonMapper.insertSelective(studyRoleButton) ;
            }

            // 项目创建者为默认为项目管理员
            if (roleTypeEnum.getName().equals(RoleTypeConsts.STUDY_ADMIN)) {
                StudyRoleUser studyRoleUser = new StudyRoleUser() ;
                studyRoleUser.setRoleId(studyRole.getId());
                studyRoleUser.setUserId(BaseContextHandler.getUserID());
                studyRoleUserMapper.insertSelective(studyRoleUser) ;
            }

        }

        // 项目创建用户
        StudyUser studyUser = new StudyUser() ;
        studyUser.setStudyId(studyId);
        studyUser.setUserId(BaseContextHandler.getUserID());
        studyUserMapper.insertSelective(studyUser) ;

    }


    /**
     * 获取某项目下的角色列表
     * @param studyId
     * @param roleName
     * @return
     */
    public List<StudyRoleVo> getStudyRoleList(Long studyId, String roleName) {
        StudyRoleVo studyRoleVo = new StudyRoleVo() ;
        studyRoleVo.setStudyId(studyId);
        studyRoleVo.setName(roleName);
        return studyRoleMapper.getStudyRoleList(studyRoleVo) ;
    }


    /**
     * 校验 项目下的角色名称是否重复
     * @param studyRoleVo
     * @return
     */
    public List<StudyRoleVo> checkNameExists(StudyRoleVo studyRoleVo) {
        return studyRoleMapper.checkNameExists(studyRoleVo) ;
    }
}
