package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.sci.biz.CrfBiz;
import com.ronglian.kangrui.saas.research.sci.biz.CrfDictFieldBiz;
import com.ronglian.kangrui.saas.research.sci.biz.StudyBiz;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Crf;
import com.ronglian.kangrui.saas.research.sci.entity.CrfDictField;
import com.ronglian.kangrui.saas.research.sci.entity.Study;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.vo.CrfFormVo;
import com.ronglian.kangrui.saas.research.sci.vo.DiseaseFieldVo;
import com.ronglian.kangrui.saas.research.sci.vo.FieldVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.reducing;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-08 14:43
 **/
@Service
public class CrfManager {

    @Autowired
    private CrfBiz crfBiz ;

    @Autowired
    private StudyBiz studyBiz;

    @Autowired
    private CrfDictFieldBiz crfDictFieldBiz ;

    @Autowired
    private DiseaseManager diseaseManager ;

    @Autowired
    private CrfDictFieldManager crfDictFieldManager ;



    /**
     * 新建CRF form 保存
     * @param crfFormVo
     * @return
     */
    public Crf saveCrfForm(CrfFormVo crfFormVo) {
        return crfBiz.saveCrfForm(crfFormVo) ;
    }



    /**
     * 新建题组保存
     * @param crfFormVo
     * @return
     */
    public Crf saveCrfTestlets(CrfFormVo crfFormVo) {
        return crfBiz.saveCrfTestlets(crfFormVo) ;
    }


    /**
     * 项目下字典-字段列表
     * @param studyId
     * @param dictionaryId
     * @return
     */
    public List<DiseaseFieldVo> getFieldListByStudyId(Long studyId, Long crfId, Long dictionaryId) {
        List<DiseaseFieldVo> diseaseFieldVoList = new ArrayList<DiseaseFieldVo>() ;

        // 根据项目ID查询项目
        Study study = studyBiz.selectById(studyId) ;

        // 判断项目下是否关联疾病，如果关联疾病，则只显示疾病选中的字典下的字段。并通过题组属性筛选字典属性
        if (study!=null && study.getDiseaseId()!=null) {
            diseaseFieldVoList = diseaseManager.getFieldListByStudyId(study.getDiseaseId(), dictionaryId) ;

        } else {
            //若项目没有选择疾病，则显示所有的字典下的字段。并通过题组属性过滤字典属性
            diseaseFieldVoList = diseaseManager.getFieldListByDictId(dictionaryId) ;
        }

        // 筛选 把已选择的field 改为 checked:true
        if (diseaseFieldVoList!=null && diseaseFieldVoList.size()>0){
            for (DiseaseFieldVo diseaseFieldVo : diseaseFieldVoList){
                // 判断该字段是否被crf id 选中
                boolean isChecked = this.checkFieldSelectedByCrfId(crfId, dictionaryId, diseaseFieldVo.getId()) ;
                diseaseFieldVo.setChecked(isChecked);
            }
        }

        return diseaseFieldVoList ;
    }



    /**
     * 判断该字段是否crf id 选中
     * @param crfId
     * @param dictionaryId
     * @param fieldId
     * @return
     */
    private boolean checkFieldSelectedByCrfId(Long crfId, Long dictionaryId, Long fieldId) {
        boolean isChecked = Boolean.FALSE ;

        List<FieldVo> selectFieldList = crfDictFieldManager.getSelectFieldByCrfId(crfId, dictionaryId, fieldId) ;
        isChecked = (selectFieldList!=null && selectFieldList.size()>0) ? Boolean.TRUE : Boolean.FALSE ;

        return isChecked ;
    }



    /**
     * 保存题组关联的字段信息
     * 1: 先查询这个题组对应的字典下，老的已勾选字段
     * 2: 页面上选中的字段集合
     * 3：找出库中已有字段和页面勾选字段对比：删除的字段和新增的字段。编辑的字段保持不动
     *
     * @param crfId
     * @param dictionaryId
     * @param diseaseFieldVos
     * @return
     */
    @Transactional
    public boolean saveCrfFieldsInfo(Long crfId, Long dictionaryId, List<DiseaseFieldVo> diseaseFieldVos) {
        boolean flag = Boolean.FALSE ;

        // 1: 先查询这个题组对应的字典下，老的已勾选字段  2: 页面上选中的字段集合 3：找出库中已有字段和页面勾选字段对比：删除的字段和新增的字段。编辑的字段保持不动
        crfDictFieldManager.handleUpdateCrfFieldsInfo(crfId, dictionaryId, diseaseFieldVos) ;
        flag = Boolean.TRUE ;
        return flag ;
    }


    /**
     * 根据id查询对象
     * @param crfId
     * @return
     */
    public Crf selectById(Long crfId) {
        return crfBiz.selectById(crfId) ;
    }



    /**
     * 校验项目下Crf表单重复（1：项目下的crf表单不重复  2：未删除的表单）
     * @param name
     * @param studyId
     * @param crfFormId
     * @return
     */
    public boolean checkCrfFormNameExists(String name, Long studyId, Long crfFormId) {
        return crfBiz.checkCrfFormNameExists(name, studyId, crfFormId) ;
    }



    /**
     * 校验Crf表单下的题组不重复（1：Crf表单下的题组不重复  2：未删除的题组）
     * @param name
     * @param crfFormId
     * @param crfId
     * @return
     */
    public boolean checkTestletsNameExists(String name, Long crfFormId, Long crfId) {
        return crfBiz.checkTestletsNameExists(name, crfFormId, crfId) ;
    }



    /**
     * 删除Crf表单
     * 1:先判断Crf是否已创建表单到数据库
     * 2:判断Crf表单是否已关联数据
     * 3:CRF下的所有字段置为无效
     * 4: 删除 study_crf
     *
     * @param studyId
     * @param crfFormId
     * @return
     */
    public Msg deleteCrfFormInfo(Long studyId, Long crfFormId) {
        Msg msg = new Msg() ;

        boolean flag = Boolean.FALSE ;
        String desc = null ;

        // 1:先判断Crf是否已创建表单到数据库
        Crf crf = crfBiz.selectById(crfFormId) ;
        if (crf!=null && crf.getTableName()!=null){

            //2:判断Crf表单是否已关联数据
            CrfFormVo crfFormVo = new CrfFormVo() ;
            crfFormVo.setTableName(crf.getTableName());
            List<Map> formData = crfBiz.getFormData(crfFormVo);
            flag = (formData!=null && formData.size()>0)  ;
            desc = flag ? ResearchConsts.CRF_FORM_NOT_DELETED_MSG: null ;//CRF已经存在指标值不允许删除
        }


        if (!flag) {
            // crf 表单无数据，1:把CRF表单置为无效  2: crf 下的所有题组置为无效  3:CRF下的所有字段置为无效 4: 删除 study_crf
            desc = this.updateCrfFormDeleted(studyId, crfFormId) ;
        }

        msg.setSucFlag(flag);
        msg.setDesc(desc);

        return msg ;
    }





    /**
     *  crf 表单无数据，1:把CRF表单置为无效  2: crf 下的所有题组置为无效  3:CRF下的所有字段置为无效 4: 删除 study_crf
     * @param studyId
     * @param crfFormId
     * @return
     */
    @Transactional
    public String updateCrfFormDeleted(Long studyId, Long crfFormId) {
        crfBiz.updateCrfFormDeleted(studyId, crfFormId);
        return ResearchConsts.CRF_FORM_DEL_SUC_MSG;
    }




    /**
     * 删除题组
     * 1: 先判断题组的上级CRF表单是否已创建表
     * 2：再判断题组下是否有字段
     * 3: 题组下的字段是否已创建字段到库
     * 4: 判断题组下已创建字段的列，是否已存储数据
     * @param crfSecondId
     * @return
     */
    public Msg deleteCrfTestlets(Long crfSecondId) {
        Msg msg = new Msg() ;

        boolean flag = Boolean.FALSE ;
        String desc = null ;

        // 1: 先判断题组的上级CRF表单是否已创建表
        Crf crf = crfBiz.selectById(crfSecondId) ;
        if (crf!=null && crf.getParentId()!=null) {
            Crf crfForm = crfBiz.selectById(crf.getParentId()) ;
            if (crfForm!=null && crfForm.getTableName()!=null) {

                // 2：再判断题组下是否有字段
                List<CrfDictField> crfDictFieldList = crfDictFieldBiz.getCrfDictFieldInfoByCrfSecondId(crfSecondId) ;
                if (crfDictFieldList!=null && crfDictFieldList.size()>0) {

                    // 3: 题组下的字段是否已创建字段到库
                    List<CrfDictField> crfValidFieldList = crfDictFieldList.stream() .filter(crfDictField -> (StringUtils.isNotBlank(crfDictField.getFieldName())
                                    && crfDictField.getGenerateToDb()==ResearchConsts.GENERATE_TO_DB_YES)) .collect(Collectors.toList());

                    if (crfValidFieldList!=null && crfValidFieldList.size()>0) {
                        List<String> fieldNameList = crfValidFieldList.stream().map(CrfDictField::getFieldName).collect(Collectors.toList());

                        // 4: 判断题组下已创建字段的列，是否已存储数据
                        CrfFormVo temp = new CrfFormVo() ;
                        temp.setTableName(crfForm.getTableName());
                        temp.setFieldNameList(fieldNameList);
                        List<Map> formData = crfBiz.getFieldsInfoData(temp) ;
                        flag = (formData!=null && formData.size()>0) ;
                        desc = flag ? ResearchConsts.CRF_TESTLETS_NOT_DELETED_MSG: null ;//题组已经存在指标值不允许删除
                    }
                }
            }
        }


        if (!flag) {
            // 题组无数据，1:把题组置为无效  2: 题组下的所有字段置为无效
            desc = this.updateCrfTestletsDeleted(crfSecondId) ;
        }

        msg.setSucFlag(flag);
        msg.setDesc(desc);

        return msg ;
    }



    /**
     *  题组无数据，1:把题组置为无效  2: 题组下的所有字段置为无效
     * @param crfSecondId
     * @return
     */
    @Transactional
    public String updateCrfTestletsDeleted(Long crfSecondId) {
        crfBiz.updateCrfTestletsDeleted(crfSecondId);
        return ResearchConsts.CRF_TESTLETS_DEL_SUC_MSG;
    }



}
