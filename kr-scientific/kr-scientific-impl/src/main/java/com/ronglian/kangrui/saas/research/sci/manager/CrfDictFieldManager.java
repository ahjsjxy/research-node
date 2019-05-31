package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.sci.util.ListDifferentUtil;
import com.ronglian.kangrui.saas.research.sci.biz.CrfDictFieldBiz;
import com.ronglian.kangrui.saas.research.sci.vo.DiseaseFieldVo;
import com.ronglian.kangrui.saas.research.sci.vo.FieldVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-04-19 14:12
 **/
@Service
public class CrfDictFieldManager {

    @Autowired
    private CrfDictFieldBiz crfDictFieldBiz ;



    /**
     * 查询crf 题组 关联的字段列表
     * @param crfId
     * @param dictionaryId
     * @param fieldId
     * @return
     */
    public List<FieldVo> getSelectFieldByCrfId(Long crfId, Long dictionaryId, Long fieldId) {
        return crfDictFieldBiz.getSelectFieldByCrfId(crfId, dictionaryId, fieldId) ;
    }



    /**
     * 项目-校验  1：存在未删除的CRF且还没创建表 2：CRF存在字段未创建到数据库列的
     * @param studyId
     * @return
     */
    public boolean checkHasCrfFormGenerateToDb(Long studyId) {
        return crfDictFieldBiz.checkHasCrfFormGenerateToDb(studyId) ;
    }



    /**
     * Crf表单-校验存在未删除的字段  1: CRF 已经创建表到DB  2：CRF存在字段未创建到数据库列的
     * @param crfFormId
     * @return
     */
    public boolean checkHasFieldsGenerateToDb(Long crfFormId) {
        return crfDictFieldBiz.checkHasFieldsGenerateToDb(crfFormId) ;
    }



    /**
     * 1: 先查询这个题组对应的字典下，老的已勾选字段
     * 2: 页面上选中的字段集合
     * 3：找出库中已有字段和页面勾选字段对比：删除的字段和新增的字段。编辑的字段保持不动
     *
     * @param crfId
     * @param dictionaryId
     * @param diseaseFieldVos
     * @return
     */
    public void handleUpdateCrfFieldsInfo(Long crfId, Long dictionaryId, List<DiseaseFieldVo> diseaseFieldVos) {
        // 先查询这个题组对应的字典下，数据库之前老的已勾选字段
        List<FieldVo> oldSelectFieldList = crfDictFieldBiz.getSelectFieldByCrfId(crfId, dictionaryId,null) ;
        List<Long> oldFieldIdList = null ;
        if (oldSelectFieldList!=null && oldSelectFieldList.size()>0) {
            oldFieldIdList = oldSelectFieldList.stream().map(FieldVo::getId).collect(toList());
        }

        List<Long> newFieldIdList = null ;
        if (diseaseFieldVos!=null && diseaseFieldVos.size()>0) {
            // 页面传参过来判断选中的字段列
            List<DiseaseFieldVo> selectFieldList = diseaseFieldVos.stream().filter(diseaseFieldVo -> (diseaseFieldVo.isChecked()==Boolean.TRUE) ).collect(toList());
            if (selectFieldList!=null && selectFieldList.size()>0) {
                newFieldIdList = selectFieldList.stream().map(DiseaseFieldVo::getId).collect(toList());
            }
        }

        // 获取删除的集合
        List<Long> deleteList = ListDifferentUtil.getDeleteList(oldFieldIdList, newFieldIdList) ;
        if (deleteList!=null && deleteList.size()>0) {
            crfDictFieldBiz.updateByCrfId(crfId, dictionaryId, deleteList);
        }


        // 获取新增的集合
        List<Long> insertList = ListDifferentUtil.getInsertList(oldFieldIdList, newFieldIdList) ;
        if (insertList!=null && insertList.size()>0) {
            for (Long fieldId : insertList) {
                crfDictFieldBiz.addCrfFieldByCrfId(crfId, dictionaryId, fieldId);
            }
        }

    }


}
