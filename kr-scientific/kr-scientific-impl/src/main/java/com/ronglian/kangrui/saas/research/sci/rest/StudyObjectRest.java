package com.ronglian.kangrui.saas.research.sci.rest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ronglian.kangrui.saas.research.sci.entity.StudyGroup;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.common.msg.TableResultResponse;
import com.ronglian.kangrui.saas.research.sci.manager.StudyObjectManager;
import com.ronglian.kangrui.saas.research.sci.vo.HeaderVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyObjectVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-08 17:31
 **/
@RestController
@RequestMapping("studyObject")
@Slf4j
public class StudyObjectRest {

    @Autowired
    private StudyObjectManager studyObjectManager ;


    @ApiOperation("项目的队列列表")
    @RequestMapping(value = "/getStudyGroupList",method = RequestMethod.GET)
    public List<StudyGroup> getStudyGroupList(@RequestParam Long studyId) {
        return studyObjectManager.getStudyGroupList(studyId) ;
    }



    @ApiOperation("研究对象列头")
    @RequestMapping(value = "getHeaderList",method = RequestMethod.GET)
    public List<HeaderVo> getHeaderList(@RequestParam Long studyId) {
        return studyObjectManager.getHeaderList(studyId) ;
    }



    @ApiOperation("研究对象列表")
    @RequestMapping(value = "/pageList",method = RequestMethod.GET)
    public TableResultResponse<StudyObjectVo> pageList(@RequestParam(defaultValue = "10") int limit,
                                                       @RequestParam(defaultValue = "1") int page,
                                                       @RequestParam Long studyId,
                                                       @RequestParam Long studyGroupId){
        Page<StudyObjectVo> result = PageHelper.startPage(page, limit,true);
        List<StudyObjectVo> studyObjectVoList = studyObjectManager.queryStudyObjectList(studyId, studyGroupId) ;
        return new TableResultResponse<StudyObjectVo>(result.getTotal(), studyObjectVoList);
    }



    @ApiOperation("研究对象删除")
    @RequestMapping(value = "delStudyObjectForm",method = RequestMethod.POST)
    public ObjectRestResponse delStudyObjectForm(@RequestParam String objectIdStr){
        boolean flag = studyObjectManager.delStudyObjectForm(objectIdStr) ;
        return new ObjectRestResponse().rel(flag) ;
    }

}
