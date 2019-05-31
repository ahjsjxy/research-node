package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.sci.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * crf form处理
 *
 * @author lanyan
 * @create 2019-03-05 11:33
 **/
@Service
@Slf4j
public class FormsManager {

    @Autowired
    private FormDataManager formDataManager ;

    @Autowired
    private StudyObjectManager studyObjectManager ;

    @Autowired
    private FormConfigManager formConfigManager ;



    /**
     * 根据study id 查询项目的crf配置
     * @param studyId
     * @return
     */
    public StudyTreeVo getDisplayFieldList(Long studyId) {
        return formConfigManager.getDisplayFieldList(studyId) ;
    }


    /**
     * 保存设置显示字段
     * @param studyTreeVo
     * @return
     */
    public int saveDisplayFieldInfo(StudyTreeVo studyTreeVo) {
        return formConfigManager.saveDisplayFieldInfo(studyTreeVo) ;
    }



    /**
     * 获取动态form的结构及数据
     * @param studyId
     * @return
     */
    public FormsVo getFormConfigAndDataInfo(Long studyId) {
        FormsVo formsVo = new FormsVo() ;

        //查询form config节点
        List<FormConfigVo> formConfigVoList = formConfigManager.getDisplayFormConfigVoList(studyId) ;
        formsVo.setFormConfigs(formConfigVoList);

        //查询form data
        FormDataVo formData = formDataManager.getFormData(formConfigVoList, null);
        formsVo.setData(formData);

        return formsVo ;
    }



    /**
     * 保存-设置显示列的那几个字段
     * @param formsVo
     * @return
     */
    public boolean saveDisplayForm(FormsVo formsVo, Long studyId, Long studyGroupId) {
        return formDataManager.saveDisplayFormData(formsVo, studyId, studyGroupId) ;
    }



    /**
     * 根据study object id和 form id 查询某个单节点的form的配置和数据
     * @param objectId
     * @param crfFormId
     * @return
     */
    public FormsVo getOneFormConfigAndDataInfo(Long objectId, Long crfFormId, Long crfSecondId) {
        FormsVo formsVo = new FormsVo() ;
        formsVo.setObjectId(objectId);

        //查询form config节点
        List<FormConfigVo> formConfigVoList = formConfigManager.getOneFormConfig(objectId, crfFormId, crfSecondId) ;
        formsVo.setFormConfigs(formConfigVoList);


        List<FormConfigVo> tempList = formConfigManager.getOneFormConfig(objectId, crfFormId, null) ;

        //查询form data
        FormDataVo formData = formDataManager.getFormData(tempList, objectId);
        formsVo.setData(formData);

        return formsVo ;

    }


    /**
     * 根据object id 保存单个form信息
     * @param formsVo
     * @param objectId
     * @return
     */
    public boolean saveOneFormInfo(FormsVo formsVo, Long objectId) {
        return formDataManager.saveOneFormInfo(formsVo, objectId) ;
    }


    /**
     * 删除动态form
     * @param objectId
     * @return
     */
    @Transactional
    public  boolean delFormData(Long objectId) {
        // 更新 研究对象主表的状态为已删除
        studyObjectManager.delStudyObject(objectId);

        // 更新 相关的动态表单数据为已删除
        return formDataManager.delFormData(objectId);
    }



    /**
     * 研究对象-点击单个Crf对应的题组树形
     * @param crfFormId
     * @return
     */
    public CrfParentVo getOneFormInfoTree(Long crfFormId) {
        return formConfigManager.getOneFormInfoTree(crfFormId) ;
    }


    /**
     * 根据study id获取动态列头
     * @param studyId
     * @return
     */
    public DynamicFieldsVo getDynamicFieldsInfo(Long studyId) {
        DynamicFieldsVo dynamicFieldsVo = new DynamicFieldsVo() ;

        List<FormConfigVo> formConfigVoList = formConfigManager.getDisplayFormConfigVoList(studyId) ;
        dynamicFieldsVo.setFormConfigVoList(formConfigVoList);

        List<CrfParentVo> crfParentVoList = formConfigManager.getCrfFormListByStudyId(studyId) ;
        dynamicFieldsVo.setCrfList(crfParentVoList);

        return dynamicFieldsVo ;
    }

}
