package com.ronglian.kangrui.saas.research.sci.rest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import com.ronglian.kangrui.saas.research.auth.client.controller.UserBaseController;
import com.ronglian.kangrui.saas.research.auth.common.util.jwt.IJWTInfo;
import com.ronglian.kangrui.saas.research.sci.biz.StudyBiz;
import com.ronglian.kangrui.saas.research.sci.entity.Study;
import com.ronglian.kangrui.saas.research.sci.manager.StudyManager;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.common.msg.TableResultResponse;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.manager.StudyGroupManager;
import com.ronglian.kangrui.saas.research.sci.vo.StudyAddVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目列表
 *
 * @author lanyan
 * @create 2019-03-05 11:31
 **/
@RestController
@RequestMapping("study")
@Slf4j
public class StudyRest extends UserBaseController<StudyBiz,Study>{

    @Autowired
    private StudyManager studyManager ;

    @Autowired
    private StudyGroupManager studyGroupManager ;
    @Autowired
    private StudyBiz studyBiz;


    @ApiOperation("项目列表")
    @RequestMapping(value = "/pageList",method = RequestMethod.GET)
    public TableResultResponse<StudyVo> pageList(@RequestParam(defaultValue = "10") int limit,
                                                 @RequestParam(defaultValue = "1") int page,
                                                 String name){
        StudyVo temp = new StudyVo() ;
        temp.setName(name);
        IJWTInfo user = getCurrentUserInfo();
        temp.setUserId(Long.parseLong(user.getId()));
        Page<StudyVo> result = PageHelper.startPage(page, limit,true);
        List<StudyVo> studyVoList = studyManager.getStudyList(temp) ;
        return new TableResultResponse<StudyVo>(result.getTotal(), studyVoList);
    }



    @ApiOperation("删除项目")
    @RequestMapping(value = "/deleteStudyById",method = RequestMethod.POST)
    public ObjectRestResponse deleteStudyById(@RequestParam String studyIdStr) {
        boolean flag = studyManager.deleteStudyById(studyIdStr) ;
        return new ObjectRestResponse().rel(flag) ;
    }


    @ApiOperation("项目-第一步保存")
    @RequestMapping(value = "/saveStudyStep1",method = RequestMethod.POST)
    public ObjectRestResponse saveStudyStep1(@RequestBody StudyAddVo studyAddVo) {
        studyAddVo = studyManager.saveStudyStep1(studyAddVo) ;
        return new ObjectRestResponse().rel(Boolean.TRUE).data(studyAddVo) ;
    }


    @ApiOperation("项目-第二步保存")
    @RequestMapping(value = "/saveStudyStep2",method = RequestMethod.POST)
    public ObjectRestResponse saveStudyStep2(@RequestBody StudyAddVo studyAddVo) {
        studyAddVo = studyManager.saveStudyStep2(studyAddVo) ;
        return new ObjectRestResponse().rel(Boolean.TRUE).data(studyAddVo) ;
    }


    @ApiOperation("查询项目详情根据项目ID")
    @RequestMapping(value = "/getStudyInfosByStudyId",method = RequestMethod.GET)
    public ObjectRestResponse getStudyInfosByStudyId(@RequestParam Long studyId) {
        StudyAddVo studyAddVo = studyManager.getStudyGroupByStudyId(studyId) ;
        return new ObjectRestResponse().data(studyAddVo) ;
    }


    @ApiOperation("校验项目名称重复")
    @RequestMapping(value = "checkStudyNameExists",method = RequestMethod.GET)
    public ObjectRestResponse checkStudyNameExists(@RequestParam String name,
                                              Long studyId) {
        boolean flag = studyManager.checkStudyNameExists(name.trim(), studyId) ;
        return new ObjectRestResponse().rel(flag) ;
    }



    @ApiOperation("校验队列名称重复")
    @RequestMapping(value = "checkStudyGroupNameExists",method = RequestMethod.GET)
    public ObjectRestResponse checkStudyGroupNameExists(
                                                        @RequestParam Long studyId,
                                                        @RequestParam String name,
                                                        Long studyGroupId) {
        boolean flag = studyGroupManager.checkStudyGroupNameExists(name.trim(), studyId, studyGroupId) ;
        return new ObjectRestResponse().rel(flag) ;
    }



    @ApiOperation("删除队列")
    @RequestMapping(value = "deleteStudyGroupInfo",method = RequestMethod.POST)
    public ObjectRestResponse deleteStudyGroupInfo(@RequestParam Long studyGroupId) {
        Msg msg = studyGroupManager.deleteStudyGroupInfo(studyGroupId) ;
        return new ObjectRestResponse().rel(msg.getSucFlag()).data(msg.getDesc()) ;
    }



    @ApiOperation("项目-判断是否有研究对象")
    @RequestMapping(value = "checkHasStudyObjectData",method = RequestMethod.GET)
    public ObjectRestResponse checkHasStudyObjectData(@RequestParam Long studyId) {
        boolean flag = studyManager.checkHasStudyObjectData(studyId) ;
        return new ObjectRestResponse().rel(flag)  ;
    }



    @ApiOperation("项目-第三步保存")
    @RequestMapping(value = "/saveStudyStep3",method = RequestMethod.POST)
    public ObjectRestResponse saveStudyStep3(@RequestParam Long studyId) {
        StudyVo studyVo = studyManager.saveStudyStep3(studyId) ;
        return new ObjectRestResponse().rel(Boolean.TRUE).data(studyVo) ;
    }


    @ApiOperation("用户默认-医疗机构")
    @RequestMapping(value = "getUserDefCenterList",method = RequestMethod.GET)
    public List<HospCenterVo> getUserDefCenterList() {
        return studyManager.getUserDefCenterList() ;
    }


    @ApiOperation("项目-已选择的中心")
    @RequestMapping(value = "getSelectedCenterList",method = RequestMethod.GET)
    public List<HospCenterVo> getSelectedCenterList(@RequestParam Long studyId) {
        return studyManager.getSelectedCenterList(studyId) ;
    }


    @ApiOperation("项目-未选择的中心")
    @RequestMapping(value = "geValidCenterList",method = RequestMethod.GET)
    public List<HospCenterVo> geValidCenterList(@RequestParam Long studyId) {
        return studyManager.geValidCenterList(studyId) ;
    }


    @ApiOperation("项目-第四步保存")
    @RequestMapping(value = "/saveStudyStep4",method = RequestMethod.POST)
    public ObjectRestResponse saveStudyStep4(@RequestBody StudyAddVo studyAddVo) {
        studyAddVo = studyManager.saveStudyStep4(studyAddVo) ;
        return new ObjectRestResponse().rel(Boolean.TRUE).data(studyAddVo) ;
    }

}
