package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import com.ronglian.kangrui.saas.research.common.context.BaseContextHandler;
import com.ronglian.kangrui.saas.research.sci.biz.*;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Study;
import com.ronglian.kangrui.saas.research.sci.entity.StudyCenter;
import com.ronglian.kangrui.saas.research.sci.entity.StudyGroup;
import com.ronglian.kangrui.saas.research.sci.vo.StudyAddVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyGroupVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-11 12:28
 **/
@Service
public class StudyManager {

    @Autowired
    private StudyBiz studyBiz ;

    @Autowired
    private StudyObjectBiz studyObjectBiz ;

    @Autowired
    private StudyGroupBiz studyGroupBiz ;

    @Autowired
    private StudyCenterBiz studyCenterBiz ;

    @Autowired
    private StudyRoleBiz studyRoleBiz ;


    /**
     * 查询项目列表
     * @return
     */
    public List<StudyVo> getStudyList(StudyVo studyVo) {
        return studyBiz.getStudyList(studyVo) ;
    }



    /**
     * 批量删除study根据study id
     * @param studyIdStr
     * @return
     */
    public boolean deleteStudyById(String studyIdStr){
        boolean flag = Boolean.FALSE ;

        if (StringUtils.isNotBlank(studyIdStr)) {
            List<Long> studyIdList = Arrays.asList(studyIdStr.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            for (Long studyId : studyIdList) {
                Study study = studyBiz.selectById(studyId) ;
                study.setDeleted(ResearchConsts.DELETED_YES);
                study.setUpdateTime(new Date());
                studyBiz.updateSelectiveById(study); ;
            }

        }

        return flag ;
    }


    /**
     *  项目-第一步保存
     *  编辑-保存
     *  新建项目基本信息-保存(1, 基本信息保存  2，项目状态为创建中)
     *
     * @return
     */

    public StudyAddVo saveStudyStep1(StudyAddVo studyAddVo) {
        Study study = new Study() ;
        BeanUtils.copyProperties(studyAddVo, study);

        if (studyAddVo!=null && studyAddVo.getId()!=null) {
            // 编辑项目-第一步基本信息
            study.setUpdateTime(new Date());
            study.setUpdateUser(BaseContextHandler.getUserID());
            studyBiz.updateSelectiveById(study);

        } else {
            study = this.saveStudyStep1(study) ;
        }

        BeanUtils.copyProperties(study, studyAddVo);
        return studyAddVo ;
    }



    /**
     * 新建项目基本信息-保存(1, 基本信息保存  2，项目状态为创建中)
     * @param study
     * @return
     */
    @Transactional
    public Study saveStudyStep1(Study study) {
        // 1, 新建项目-第一步基本信息
        study.setStatus(ResearchConsts.STUDY_STATUS_CREATING);//1：创建中
        study.setDeleted(ResearchConsts.DELETED_NO);
        study.setCreateTime(new Date());
        study.setCreateUser(BaseContextHandler.getUserID());
        study.setUpdateTime(new Date());
        study = studyBiz.insertSelective(study) ;


        // 2, 给项目创建角色(每个项目默认创建4个角色)  3, 每个角色赋值按钮权限  4, 项目创建者为默认为项目管理员
        studyRoleBiz.addStudyRoleInfo(study.getId());

        return study ;
    }



    /**
     * 项目-第二步保存(编辑和新建队列-保存)
     * @param studyAddVo
     * @return
     */
    public StudyAddVo saveStudyStep2(StudyAddVo studyAddVo) {
        if (studyAddVo == null || studyAddVo.getStudyGroupVoList() == null) {
            return null;
        }


        List<StudyGroupVo> studyGroupVoList = studyAddVo.getStudyGroupVoList();
        for (StudyGroupVo studyGroupVo : studyGroupVoList) {
            StudyGroup studyGroup = new StudyGroup();
            BeanUtils.copyProperties(studyGroupVo, studyGroup);

            if (studyGroupVo!=null && studyGroupVo.getId()!=null) {
                // 编辑队列
                studyGroup.setUpdateTime(new Date());
                studyGroupBiz.updateSelectiveById(studyGroup); ;
            }else {
                // 新建队列
                studyGroup.setStudyId(studyAddVo.getId());
                studyGroup.setDeleted(ResearchConsts.DELETED_NO);
                studyGroup.setCreateTime(new Date());
                studyGroup.setCreateUser(BaseContextHandler.getUserID());
                studyGroup.setUpdateTime(new Date());
                studyGroupBiz.insertSelective(studyGroup);
            }
        }

        return studyAddVo ;
    }



    /**
     * 查询项目分组根据项目ID
     * @param studyId
     * @return
     */
    public StudyAddVo getStudyGroupByStudyId(Long studyId) {
        StudyAddVo studyAddVo = new StudyAddVo() ;

        Study study = studyBiz.selectById(studyId) ;
        BeanUtils.copyProperties(study, studyAddVo);

        List<StudyGroupVo> studyGroupVoList = new ArrayList<StudyGroupVo>() ;
        StudyGroup studyGroup = new StudyGroup() ;
        studyGroup.setStudyId(studyId);
        studyGroup.setDeleted(ResearchConsts.DELETED_NO);
        List<StudyGroup> studyGroupList = studyGroupBiz.selectList(studyGroup) ;
        if (studyGroupList!=null && studyGroupList.size()>0) {
            for (StudyGroup temp : studyGroupList){
                StudyGroupVo studyGroupVo = new StudyGroupVo() ;
                BeanUtils.copyProperties(temp, studyGroupVo);
                studyGroupVoList.add(studyGroupVo) ;
            }
            studyAddVo.setStudyGroupVoList(studyGroupVoList);
        }
        return studyAddVo ;
    }



    /**
     * 根据id查询对象
     * @param studyId
     * @return
     */
    public Study selectById(Long studyId) {
        return studyBiz.selectById(studyId) ;
    }



    /**
     * 校验项目名称不重复（未删除）
     * @param name
     * @param studyId
     * @return
     */
    public boolean checkStudyNameExists(String name, Long studyId) {
        boolean isRepeat = Boolean.FALSE ;

        List<StudyVo> studyList = studyBiz.getStudyList(null) ;
        List<StudyVo> repeatList = null ;
        if (studyList!=null && studyList.size()>0) {
            if (studyId!=null) {
                StudyVo temp = new StudyVo() ;
                temp.setId(studyId);
                temp.setName(name);
                // 编辑的时候判断name是否重复
                repeatList = studyBiz.checkStudyNameExists(temp) ;
            } else {
                repeatList = studyList.stream().filter(studyVo -> name.equals(studyVo.getName())).collect(toList());
            }
        }

        isRepeat = (repeatList!=null && repeatList.size()>0) ? Boolean.TRUE : Boolean.FALSE ;
        return isRepeat ;
    }



    /**
     * 校验项目-是否有研究对象，有研究对象，不允许修改项目的基本信息
     * @param studyId
     * @return
     */
    public boolean checkHasStudyObjectData(Long studyId) {
        return studyObjectBiz.checkHasStudyObjectData(studyId) ;
    }


    /**
     * 项目-第三步保存，修改项目的状态为待发布
     * @param studyId
     * @return
     */
    public StudyVo saveStudyStep3(Long studyId) {
        Study study = new Study() ;
        study.setId(studyId);
        study.setStatus(ResearchConsts.STUDY_STATUS_NOT_PUBLISHED);//项目状态(2：待发布)
        study.setUpdateTime(new Date());
        study.setUpdateUser(BaseContextHandler.getUserID());
        studyBiz.updateSelectiveById(study);

        StudyVo studyVo = new StudyVo() ;
        BeanUtils.copyProperties(study, studyVo);

        return studyVo ;
    }



    /**
     * 获取当前用户默认选中的中心
     * @return
     */
    public List<HospCenterVo> getUserDefCenterList() {
        return studyCenterBiz.getUserDefCenterList(BaseContextHandler.getUserID()) ;
    }



    /**
     * 项目-获取已选择的中心
     * @param studyId
     * @return
     */
    public List<HospCenterVo> getSelectedCenterList(Long studyId) {
        return studyCenterBiz.getSelectedCenterList(studyId) ;
    }



    /**
     * 项目-获取未选择的中心
     * @param studyId
     * @return
     */
    public List<HospCenterVo> geValidCenterList(Long studyId) {
        return studyCenterBiz.getValidCenterList(studyId) ;
    }


    /**
     * 项目-第四步保存
     * 1, 保存项目下的中心信息
     * 2, 设置项目状态为“已发布”
     *
     * @param studyAddVo
     * @return
     */
    public StudyAddVo saveStudyStep4(StudyAddVo studyAddVo) {
        // 保存项目下的中心信息
        if (studyAddVo == null || studyAddVo.getCenterList() == null) {
            return null;
        }

        List<HospCenterVo> centerList = studyAddVo.getCenterList() ;
        //先删除项目下的原来中心
        studyCenterBiz.deleteByStudyId(studyAddVo.getId());

        // 新建-项目关联的中心
        for (HospCenterVo hospCenterVo : centerList) {
            StudyCenter studyCenter = new StudyCenter() ;
            studyCenter.setCenterId(hospCenterVo.getId());
            studyCenter.setStudyId(studyAddVo.getId());
            studyCenterBiz.insertSelective(studyCenter) ;
        }


        //修改项目的状态-已发布
        Study study = studyBiz.selectById(studyAddVo.getId()) ;
        if (study!=null && study.getStatus()!=null &&
                study.getStatus()==ResearchConsts.STUDY_STATUS_NOT_PUBLISHED){
            study.setStatus(ResearchConsts.STUDY_STATUS_PUBLISHED);//项目状态(3：已发布)
        }
        study.setId(studyAddVo.getId());
        study.setUpdateTime(new Date());
        study.setUpdateUser(BaseContextHandler.getUserID());
        studyBiz.updateSelectiveById(study);

        return studyAddVo ;
    }
}
