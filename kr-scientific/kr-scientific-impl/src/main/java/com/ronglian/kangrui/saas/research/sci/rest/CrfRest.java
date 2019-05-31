package com.ronglian.kangrui.saas.research.sci.rest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ronglian.kangrui.saas.research.sci.entity.Crf;
import com.ronglian.kangrui.saas.research.sci.entity.Study;
import com.ronglian.kangrui.saas.research.sci.manager.CrfConfigManager;
import com.ronglian.kangrui.saas.research.sci.manager.CrfManager;
import com.ronglian.kangrui.saas.research.sci.manager.DictionaryManager;
import com.ronglian.kangrui.saas.research.sci.manager.StudyManager;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.common.msg.TableResultResponse;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-04-18 10:24
 **/
@RestController
@RequestMapping("crf")
@Slf4j
public class CrfRest {

    @Autowired
    private CrfManager crfManager ;

    @Autowired
    private StudyManager studyManager ;

    @Autowired
    private DictionaryManager dictionaryManager ;

    @Autowired
    private CrfConfigManager crfConfigManager ;



    @ApiOperation("保存Crf")
    @RequestMapping(value = "/saveCrfForm",method = RequestMethod.POST)
    public ObjectRestResponse saveCrfForm(@RequestBody CrfFormVo crfFormVo){
        CrfParentVo crfParentVo = crfConfigManager.initCrfParentVo(crfFormVo) ;
        return new ObjectRestResponse().data(crfParentVo) ;
    }



    @ApiOperation("保存题组")
    @RequestMapping(value = "/saveCrfTestlets",method = RequestMethod.POST)
    public ObjectRestResponse saveCrfTestlets(@RequestBody CrfFormVo crfFormVo) {
        CrfVo crfVo = crfConfigManager.initCrfVo(crfFormVo) ;
        return new ObjectRestResponse().data(crfVo) ;
    }



    @ApiOperation("项目下-字典列表")
    @RequestMapping(value = "/pageDictListByStudyId",method = RequestMethod.GET)
    public TableResultResponse<DictionaryVo> pageDictListByStudyId(@RequestParam(defaultValue = "10") int limit,
                                                                   @RequestParam(defaultValue = "1") int page,
                                                                   @RequestParam Long studyId,
                                                                   @RequestParam Long crfId){
        // 查询项目
        Study study = studyManager.selectById(studyId) ;
        // 查询 crf 题组
        Crf crf = crfManager.selectById(crfId) ;

        Page<DictionaryVo> result = PageHelper.startPage(page, limit,true);
        List<DictionaryVo> dictionaryVoList = dictionaryManager.getDictListByStudyId(study, crf) ;
        return new TableResultResponse<DictionaryVo>(result.getTotal(), dictionaryVoList);
    }



    @ApiOperation("项目下题组-字段列表")
    @RequestMapping(value = "/getFieldListByStudyId",method = RequestMethod.GET)
    public List<DiseaseFieldVo> getFieldListByStudyId(@RequestParam Long studyId,
                                                      @RequestParam Long crfId,
                                                      @RequestParam Long dictionaryId) {
        return crfManager.getFieldListByStudyId(studyId, crfId, dictionaryId) ;
    }



    @ApiOperation("保存题组关联的字段信息")
    @RequestMapping(value = "/saveCrfFieldsInfo",method = RequestMethod.POST)
    public ObjectRestResponse saveCrfFieldsInfo(@RequestBody CrfFieldConvertVo crfFieldConvertVo){
        boolean flag = crfManager.saveCrfFieldsInfo(crfFieldConvertVo.getCrfId(), crfFieldConvertVo.getDictionaryId(),
                crfFieldConvertVo.getFieldVoList()) ;
        return new ObjectRestResponse().rel(flag) ;
    }



    @ApiOperation("校验项目下Crf表单重复")
    @RequestMapping(value = "checkCrfFormNameExists",method = RequestMethod.GET)
    public ObjectRestResponse checkCrfFormNameExists(@RequestParam Long studyId,
                                                 @RequestParam String name,
                                                 Long crfFormId) {
        boolean flag = crfManager.checkCrfFormNameExists(name.trim(), studyId, crfFormId) ;
        return new ObjectRestResponse().rel(flag) ;
    }


    @ApiOperation("校验Crf表单下的题组重复")
    @RequestMapping(value = "checkTestletsNameExists",method = RequestMethod.GET)
    public ObjectRestResponse checkTestletsNameExists(@RequestParam Long crfFormId,
                                                     @RequestParam String name,
                                                     Long crfId) {
        boolean flag = crfManager.checkTestletsNameExists(name.trim(), crfFormId, crfId) ;
        return new ObjectRestResponse().rel(flag) ;
    }



    @ApiOperation("删除Crf表单")
    @RequestMapping(value = "deleteCrfFormInfo",method = RequestMethod.POST)
    public ObjectRestResponse deleteCrfFormInfo(@RequestParam Long studyId, @RequestParam Long crfFormId) {
        Msg msg = crfManager.deleteCrfFormInfo(studyId, crfFormId) ;
        return new ObjectRestResponse().rel(msg.getSucFlag()).data(msg.getDesc()) ;
    }



    @ApiOperation("删除题组")
    @RequestMapping(value = "deleteCrfTestlets",method = RequestMethod.POST)
    public ObjectRestResponse deleteCrfTestlets(@RequestParam Long studyId, @RequestParam Long crfSecondId) {
        Msg msg = crfManager.deleteCrfTestlets(crfSecondId) ;
        return new ObjectRestResponse().rel(msg.getSucFlag()).data(msg.getDesc()) ;
    }

}
