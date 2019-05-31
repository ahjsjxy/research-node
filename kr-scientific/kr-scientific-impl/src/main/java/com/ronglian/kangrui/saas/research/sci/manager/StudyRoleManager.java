package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.common.context.BaseContextHandler;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.biz.StudyBiz;
import com.ronglian.kangrui.saas.research.sci.biz.StudyRoleBiz;
import com.ronglian.kangrui.saas.research.sci.biz.StudyRoleButtonBiz;
import com.ronglian.kangrui.saas.research.sci.biz.StudyRoleUserBiz;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Study;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRole;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRoleButton;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRoleUser;
import com.ronglian.kangrui.saas.research.sci.vo.StudyRoleTreeVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-28 16:51
 **/
@Service
@Slf4j
public class StudyRoleManager {

    @Autowired
    private StudyBiz studyBiz ;

    @Autowired
    private StudyRoleBiz studyRoleBiz ;

    @Autowired
    private StudyRoleUserBiz studyRoleUserBiz ;

    @Autowired
    private StudyRoleButtonBiz studyRoleButtonBiz ;



    /**
     * 获取某项目下的角色列表
     * @param studyId
     * @param roleName
     * @return
     */
    public StudyRoleTreeVo getStudyRoleList(Long studyId, String roleName) {
        StudyRoleTreeVo studyRoleTreeVo = new StudyRoleTreeVo() ;

        Study study = studyBiz.selectById(studyId) ;
        BeanUtils.copyProperties(study, studyRoleTreeVo);
        studyRoleTreeVo.setTreeStudyFlag(ResearchConsts.TREE_STUDY_FLAG_STUDY);//是否项目 1：是

        List<StudyRoleVo> studyRoleVoList = studyRoleBiz.getStudyRoleList(studyId, roleName) ;
        studyRoleTreeVo.setStudyRoleVoList(studyRoleVoList);

        return studyRoleTreeVo ;
    }



    /**
     * 保存 项目-角色信息
     * @param studyRoleVo
     * @return
     */
    public StudyRoleVo saveStudyRoleInfo(StudyRoleVo studyRoleVo) {
        StudyRole studyRole = new StudyRole() ;
        BeanUtils.copyProperties(studyRoleVo, studyRole);

        if (studyRoleVo.getId()!=null) {
            //编辑
            studyRole.setUpdateTime(new Date());
            studyRole.setUpdateUser(BaseContextHandler.getUserID());
            studyRoleBiz.updateSelectiveById(studyRole);
        } else {
            //新增
            studyRole.setAllowOperate(ResearchConsts.ALLOW_OPERATE_YES);//0：允许修改删除
            studyRole.setDeleted(ResearchConsts.DELETED_NO);
            studyRole.setCreateUser(BaseContextHandler.getUserID());
            studyRole.setUpdateTime(new Date());
            studyRoleBiz.insertSelective(studyRole) ;
        }

        return studyRoleVo ;
    }


    /**
     * 根据角色ID 获取 项目-角色
     * @param roleId
     * @return
     */
    public StudyRoleVo getStudyRoleById(Long roleId) {
        StudyRole studyRole = studyRoleBiz.selectById(roleId) ;

        StudyRoleVo studyRoleVo = new StudyRoleVo() ;
        BeanUtils.copyProperties(studyRole, studyRoleVo);

        return studyRoleVo ;
    }



    /**
     *  校验角色名称，角色下不重复（未删除）
     * @param studyId
     * @param name
     * @param roleId
     * @return
     */
    public boolean checkNameExists(Long studyId, String name, Long roleId) {
        boolean isRepeat = Boolean.FALSE ;

        // 查询系统所有有效的角色名称
        List<StudyRoleVo> studyRoleVoList = studyRoleBiz.getStudyRoleList(studyId, null) ;
        List<StudyRoleVo> repeatList = null ;
        if (studyRoleVoList!=null && studyRoleVoList.size()>0) {
            if (roleId!=null) {
                StudyRoleVo temp = new StudyRoleVo() ;
                temp.setId(roleId);
                temp.setName(name);
                // 编辑的时候判断name是否重复
                repeatList = studyRoleBiz.checkNameExists(temp) ;
            } else {
                repeatList = studyRoleVoList.stream().filter(studyRoleVo -> name.equals(studyRoleVo.getName())) .collect(toList());
            }
        }

        isRepeat = (repeatList!=null && repeatList.size()>0) ? Boolean.TRUE : Boolean.FALSE ;
        return isRepeat ;
    }



    /**
     *  删除角色
     *  1, 把角色置为无效
     *  2, 删除角色下关联的用户
     *  3, 删除角色下关联的按钮
     *
     * @param roleId
     * @return
     */
    @Transactional
    public Msg deleteRoleInfo(Long roleId) {
        Msg msg = new Msg() ;

        boolean flag = Boolean.FALSE ;
        try {
            // 1, 把角色置为无效
            StudyRole studyRole = studyRoleBiz.selectById(roleId) ;
            studyRole.setDeleted(ResearchConsts.DELETED_YES);
            studyRole.setUpdateTime(new Date());
            studyRole.setUpdateUser(BaseContextHandler.getUserID());
            studyRoleBiz.updateSelectiveById(studyRole);

            // 2, 删除角色下关联的用户
            StudyRoleUser studyRoleUser = new StudyRoleUser() ;
            studyRoleUser.setRoleId(roleId);
            studyRoleUserBiz.delete(studyRoleUser);


            // 3, 删除角色下关联的按钮
            StudyRoleButton studyRoleButton = new StudyRoleButton() ;
            studyRoleButton.setRoleId(roleId);
            studyRoleButtonBiz.delete(studyRoleButton);


            flag = Boolean.TRUE ;
        } catch (Exception e) {
            log.error(e.getMessage());
            flag = Boolean.FALSE ;
        }

        msg.setSucFlag(flag);
        return msg ;
    }




}
