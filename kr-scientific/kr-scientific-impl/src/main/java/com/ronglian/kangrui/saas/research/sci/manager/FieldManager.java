package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.sci.biz.CrfBiz;
import com.ronglian.kangrui.saas.research.sci.biz.FieldBiz;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Crf;
import com.ronglian.kangrui.saas.research.sci.entity.Field;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.entity.CrfDictField;
import com.ronglian.kangrui.saas.research.sci.vo.FieldVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-13 14:16
 **/
@Service
@Slf4j
public class FieldManager {

    @Autowired
    private FieldBiz fieldBiz ;

    @Autowired
    private CrfBiz crfBiz ;



    /**
     * 根据二级字典ID查询字段列表
     * @param dictionaryId
     * @param name
     * @return
     */
    public List<FieldVo> getFieldListByDictId(Long dictionaryId, String name) {
        return fieldBiz.getFieldListByDictId(dictionaryId, name) ;
    }


    /**
     * 查询当前字段的所有子节点id
     * @param fieldId
     * @return
     */
    public List<FieldVo> getChildListById(Long fieldId) {
        return fieldBiz.getChildListById(fieldId) ;
    }



    /**
     * 根据字段ID 查出所有的父节点id
     * @param fieldId
     * @return
     */
    public List<FieldVo> getParentListById(Long fieldId) {
        return fieldBiz.getParentListById(fieldId) ;
    }



    /**
     * 获取有效的下拉列
     * @param dictionaryId
     * @param fieldId
     * @param name
     * @return
     */
    public List<FieldVo> getValidFieldList(Long dictionaryId, Long fieldId, String name) {
        return fieldBiz.getValidFieldList(dictionaryId, fieldId, name) ;
    }




    /**
     * 新增字段
     * @param dictionaryId
     * @param fieldVo
     * @return
     */
    @Transactional
    public Field saveFieldInfo(Long dictionaryId, FieldVo fieldVo) {
        return fieldBiz.saveFieldInfo(dictionaryId, fieldVo) ;
    }




    /**
     * 编辑 field
     * @param fieldVo
     * @return
     */
    public Field updateField(FieldVo fieldVo) {
        return fieldBiz.updateField(fieldVo) ;
    }



    /**
     * 删除字段
     * @param objectIdStr
     * @return
     */
    public Msg deleteFieldInfo(String objectIdStr) {
        Msg msg = new Msg() ;

        if (StringUtils.isNotBlank(objectIdStr)) {
            List<Long> objectList = Arrays.asList(objectIdStr.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(toList());
            if (objectList!=null && objectList.size()>0) {
                StringBuffer sb = new StringBuffer("") ;
                int num = 0 ;
                for (Long fieldId : objectList) {
                    // 判断该字段是否有项目引用，有的话不能删除
                    List<CrfDictField> crfDictFieldList = crfBiz.checkReferenceListByFieldId(fieldId) ;
                    if (crfDictFieldList!=null && crfDictFieldList.size()>0) {
                        Field field = fieldBiz.selectById(fieldId) ;
                        if (num > 0) {
                            sb.append(",") ;
                        }
                        sb.append(field.getLabel()) ;

                        sb.append("被Crf form引用 【") ;
                        StringBuffer formInfoStr = this.getFormListStrByFieldId(crfDictFieldList) ;
                        sb.append(formInfoStr) ;
                        sb.append("】") ;

                    } else {
                        // 修改 某个字段以及所有的子级字段 为删除
                        this.updateFieldDeleted(fieldId);
                        sb = null ;
                    }
                }


                boolean flag = (sb!=null && sb.length()>0) ? Boolean.FALSE : Boolean.TRUE ;
                msg.setSucFlag(flag);
                msg.setDesc((sb!=null && sb.length()>0) ? sb.toString() : "");
            }
        }

        return msg ;
    }



    /**
     *  某个字段被 crf form引用详情
     * @param crfDictFieldList
     * @return
     */
    private StringBuffer getFormListStrByFieldId(List<CrfDictField> crfDictFieldList) {
        StringBuffer sb = new StringBuffer("") ;

        int i = 0 ;
        for (CrfDictField crfDictField : crfDictFieldList) {
            Crf crf = crfBiz.selectById(crfDictField.getCrfFormId()) ;
            if (crf!=null && crf.getDeleted()== ResearchConsts.DELETED_NO) {

                if (i>0) {
                    sb.append(",") ;
                }
                sb.append(crf.getName()) ;
            }
        }

        return sb  ;
    }



    /**
     *  修改某个字段以及所有的子级字段 为删除
     * @param fieldId
     */
    private void  updateFieldDeleted(Long fieldId) {
        // 1, 置field表的deleted状态为 已删除
        fieldBiz.updateSingleFieldDeleted(fieldId);

        // 2,查询当前字段的所有子节点id，并置 所有子节点的字段状态为  已删除
        List<FieldVo> childFieldList = this.getChildListById(fieldId) ;
        if (childFieldList!=null && childFieldList.size()>0) {
            for (FieldVo fieldVo : childFieldList) {
                fieldBiz.updateSingleFieldDeleted(fieldVo.getId()) ;
            }
        }
    }



    /**
     * 把某个字段置为 开启/关闭
     * @param fieldId
     * @return
     */
    public Boolean updateFieldStatus(Long fieldId, Integer status) {
        return fieldBiz.updateFieldStatus(fieldId, status) ;
    }



    /**
     * 校验英文名称，在该字典下不重复（未删除）
     * @param dictionaryId
     * @param name
     * @param fieldId
     * @return
     */
    public boolean checkFieldNameExists(Long dictionaryId, String name, Long fieldId) {
        return fieldBiz.checkFieldNameExists(dictionaryId, name, fieldId) ;
    }



    /**
     * 校验中文名称，在该字典下不重复（未删除）
     * @param dictionaryId
     * @param label
     * @param fieldId
     * @return
     */
    public boolean checkFieldLabelExists(Long dictionaryId, String label, Long fieldId) {
        return fieldBiz.checkFieldLabelExists(dictionaryId, label, fieldId);
    }



    /**
     * 根据field id 编辑field
     * @param fieldId
     * @return
     */
    public FieldVo getFieldById(Long fieldId) {
        return fieldBiz.getFieldById(fieldId) ;
    }

}
