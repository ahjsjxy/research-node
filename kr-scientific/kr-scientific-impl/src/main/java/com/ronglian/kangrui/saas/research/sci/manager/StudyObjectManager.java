package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.sci.biz.CrfBiz;
import com.ronglian.kangrui.saas.research.sci.biz.CrfDictFieldBiz;
import com.ronglian.kangrui.saas.research.sci.biz.StudyObjectBiz;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Crf;
import com.ronglian.kangrui.saas.research.sci.entity.StudyGroup;
import com.ronglian.kangrui.saas.research.sci.entity.StudyObject;
import com.ronglian.kangrui.saas.research.sci.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-07 17:21
 **/
@Service
public class StudyObjectManager {

    @Autowired
    private StudyObjectBiz studyObjectBiz ;

    @Autowired
    private CrfBiz crfBiz ;

    @Autowired
    private CrfDictFieldBiz crfDictFieldBiz ;

    @Autowired
    private FormConfigManager formConfigManager ;

    @Autowired
    private FormDataManager formDataManager ;

    @Autowired
    private CrfDictFieldManager crfDictFieldManager ;

    @Autowired
    private CrfFormConfigManager crfFormConfigManager ;




    /**
     * 根据id 查询研究对象
     * @param objectId
     * @return
     */
    public StudyObject selectById(Long objectId) {
        return studyObjectBiz.selectById(objectId) ;
    }



    /**
     * 插入一个研究对象
     * @param studyId
     * @param studyGroupId
     * @return
     */
    public StudyObject insertStudyObject(Long studyId, Long studyGroupId) {
        return studyObjectBiz.insertStudyObject(studyId, studyGroupId) ;
    }



    /**
     * 根据 study object id 更新下 更新时间
     * @param objectId
     * @return
     */
    public StudyObject updateStudyObjectById(Long objectId) {
        return  studyObjectBiz.updateStudyObjectById(objectId) ;
    }




    /**
     *  查询研究对象列表
     * @param studyId
     * @param studyGroupId
     * @return
     */
    public List<StudyObjectVo> queryStudyObjectList(Long studyId, Long studyGroupId) {
        List<StudyObjectVo> studyObjectList = studyObjectBiz.queryStudyObjectList(studyId, studyGroupId) ;
        if (studyObjectList!=null && studyObjectList.size()>0) {
            for(StudyObjectVo studyObjectVo : studyObjectList) {

                //某行转换为key,value
                Map<String, Object> map = this.getOneRowMap(studyId, studyObjectVo) ;
                studyObjectVo.setFieldMap(map);

                studyObjectVo.setId(null);
                studyObjectVo.setCreateTime(null);
                studyObjectVo.setUpdateTime(null);
            }
        }

        return studyObjectList ;
    }




    /**
     * 转换map
     * @param studyId
     * @param studyObjectVo
     * @return
     */
    private Map<String, Object> getOneRowMap(Long studyId, StudyObjectVo studyObjectVo) {
        Map<String, Object> map = new HashMap<>() ;
        map.put("id", studyObjectVo.getId()) ;
        map.put("createTime", studyObjectVo.getCreateTime()) ;
        map.put("updateTime", studyObjectVo.getUpdateTime()) ;

        Map<String, Object> fieldMap = this.getFieldList(studyId, studyObjectVo.getId()) ;
        map.putAll(fieldMap);

        Map<String, StudyObjectFormVo> formMap = this.getCrfFormList(studyId, studyObjectVo.getId()) ;
        map.putAll(formMap);

        return map ;
    }




    /**
     * 根据object id判断当前的form 表单是否填写完整
     * @return
     */
    private Map<String, StudyObjectFormVo> getCrfFormList(Long studyId, Long objectId) {
        Map<String, StudyObjectFormVo> map = new HashMap<String, StudyObjectFormVo>() ;

        List<FormConfigVo> formConfigVoList = crfFormConfigManager.getNormalFormConfigVoList(studyId) ;
        if (formConfigVoList!=null && formConfigVoList.size()>0) {
            for (FormConfigVo formConfigVo : formConfigVoList) {
                StudyObjectFormVo temp = new StudyObjectFormVo() ;
                temp.setValue(formConfigVo.getId());

                // 获取采集是否完成逻辑
                Integer collectFlag = this.getCollectFlagInfo(formConfigVo, objectId, studyId) ;
                temp.setCollectFlag(collectFlag);//采集是否完成  0: 已填完；1：未填完  2：未开始填

                // crf 是否有新的列未生成字段到DB
                boolean hasNewFields = crfDictFieldManager.checkHasFieldsGenerateToDb(formConfigVo.getId()) ;
                temp.setHasNewFields(hasNewFields); //【true：有新列  false：无】

                // 如果crf 下面没有字段，则设置editFlag
                boolean editFlag = (formConfigVo.getFieldVoList()!=null
                        && formConfigVo.getFieldVoList().size()>0) ;
                temp.setEditFlag(editFlag);

                // map.put(formConfigVo.getTableName(), temp) ;
                map.put("formflag_"+formConfigVo.getId(), temp) ;

            }
        }

        return map ;
    }




    /**
     * 获取采集是否完成逻辑(0: 已填完；1：未填完  2：未开始填)
     * @param formConfigVo
     * @param objectId
     * @return
     */
    private Integer getCollectFlagInfo(FormConfigVo formConfigVo, Long objectId, Long studyId) {
        Integer collectFlag = ResearchConsts.COLLECT_FLAG_NOT_BEGIN; // 采集是否完成  (2：未开始填)

        CrfDictFieldVo crfDictFieldVo = new CrfDictFieldVo() ;
        crfDictFieldVo.setCrfFormId(formConfigVo.getId());
        crfDictFieldVo.setStudyId(studyId);
        List<CrfFieldVo> crfFieldVoList = crfDictFieldBiz.getCrfAndFieldGenerateToDbList(crfDictFieldVo) ;
        if (crfFieldVoList!=null && crfFieldVoList.size()>0) {
            //采集是否完成  0: 已填完；1：未填完  2：未开始填
            List<RecordVo> recordVoList = formDataManager.getOneFormData(formConfigVo, objectId);
            if (recordVoList!=null && recordVoList.size()>0) {
                RecordVo recordVo = (RecordVo) recordVoList.get(0);
                Map<String,Object> fieldsMap = formDataManager.getFieldsIntoDB(recordVo) ;
                collectFlag = (fieldsMap.containsValue(null) || fieldsMap.containsValue("") ? ResearchConsts.COLLECT_FLAG_NOT_FINISHED :
                        ResearchConsts.COLLECT_FLAG_FINISHED ) ;
            }
        }

        return collectFlag ;
    }






    /***
     * 获取 object id 每一列的值拼成集合
     * @param studyId
     * @param objectId
     * @return
     */
    private Map<String, Object> getFieldList(Long studyId, Long objectId) {
        Map<String, Object> map = new HashMap<>() ;

        List<FormConfigVo> formConfigVoList = formConfigManager.getDisplayFormConfigVoList(studyId) ;

        if (formConfigVoList!=null && formConfigVoList.size()>0) {
            for (FormConfigVo formConfigVo : formConfigVoList) {
                List<RecordVo> recordVoList = formDataManager.getOneFormData(formConfigVo, objectId);
                if (recordVoList!=null && recordVoList.size()>0) {
                    RecordVo recordVo = (RecordVo) recordVoList.get(0) ;
                    Map<String,Object> fieldsMap = formDataManager.getFieldsIntoDB(recordVo) ;
                    if (formConfigVo.getFieldVoList()!=null && formConfigVo.getFieldVoList().size()>0) {
                        for (CrfFieldVo crfFieldVo : formConfigVo.getFieldVoList()) {
                            if (fieldsMap.get(crfFieldVo.getFieldName())!=null) {
                                String value = fieldsMap.get(crfFieldVo.getFieldName()).toString() ;
                                map.put(crfFieldVo.getFieldName(), value) ;

                            }
                        }
                    }
                } else {
                    // do sth
                    if (formConfigVo.getFieldVoList()!=null && formConfigVo.getFieldVoList().size()>0) {
                        for (CrfFieldVo crfFieldVo : formConfigVo.getFieldVoList()) {
                            map.put(crfFieldVo.getFieldName(), "") ;
                        }
                    }
                }
            }
        }

        return map ;
    }


    /**
     * 研究对象列头
     * @param studyId
     * @return
     */
    public List<HeaderVo> getHeaderList(Long studyId) {
        List<HeaderVo> headerList = new ArrayList<HeaderVo>() ;

        HeaderVo headerVo = new HeaderVo() ;
        headerVo.setName("createTime");
        headerVo.setTitle("录入时间");
        headerList.add(headerVo);


        // 研究对象的展示列头
        List<FormConfigVo> formConfigVoList = formConfigManager.getDisplayFormConfigVoList(studyId) ;
        if (formConfigVoList!=null  && formConfigVoList.size()>0) {
            List<FormConfigVo> formValidList = formConfigVoList.stream().filter(formConfigVo -> (formConfigVo.getFieldVoList()!=null && formConfigVo.getFieldVoList().size()>0)).distinct().collect(Collectors.toList());
            for (FormConfigVo temp : formValidList) {
                List<CrfFieldVo> fieldVoList = temp.getFieldVoList() ;
                if (fieldVoList!=null && fieldVoList.size()>0) {
                    for (CrfFieldVo crfFieldVo : fieldVoList) {
                        headerVo = new HeaderVo() ;
                        headerVo.setName(crfFieldVo.getFieldName());
                        headerVo.setTitle(crfFieldVo.getLabel());
                        headerList.add(headerVo);

                    }
                }

            }
        }


        headerVo = new HeaderVo() ;
        headerVo.setName("updateTime");
        headerVo.setTitle("更新时间");
        headerList.add(headerVo);


        // 研究对象的CRF
        List<CrfParentVo> crfParentVoList = formConfigManager.getCrfFormListByStudyId(studyId) ;
        if (crfParentVoList!=null && crfParentVoList.size()>0) {
            for (CrfParentVo crfParentVo : crfParentVoList) {
                Crf crf = crfBiz.selectById(crfParentVo.getId()) ;

                headerVo = new HeaderVo() ;

                // headerVo.setName(crf.getTableName());
                headerVo.setName("formflag_"+crf.getId());
                headerVo.setTitle(crfParentVo.getName());
                headerList.add(headerVo);

            }
        }

        return headerList ;
    }



    /**
     *  查询某课题下的 队列-列表
     * @param studyId
     * @return
     */
    public List<StudyGroup> getStudyGroupList(Long studyId) {
        return studyObjectBiz.getStudyGroupList(studyId) ;
    }




    /**
     * 根据主键ID更新  研究对象删除
     * @param objectId
     */
    public void delStudyObject(Long objectId) {
        studyObjectBiz.delStudyObject(objectId);
    }




    /**
     * 删除研究对象以及相关的form
     * @param objectIdStr
     * @return
     */
    public boolean delStudyObjectForm(String objectIdStr) {
        boolean flag = Boolean.FALSE ;

        if (StringUtils.isNotBlank(objectIdStr)) {
            List<Long> objectList = Arrays.asList(objectIdStr.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            if (objectList!=null && objectList.size()>0) {
                int num = 0 ;
                for (Long objectId : objectList) {
                    // 更新 研究对象主表的状态为已删除
                    this.delStudyObject(objectId);

                    // 更新 相关的动态表单数据为已删除
                    formDataManager.delFormData(objectId);
                    num++ ;
                }

                if (num>0) {
                    flag = Boolean.TRUE ;
                }
            }
        }

       return flag ;
    }


}
