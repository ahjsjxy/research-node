package com.ronglian.kangrui.saas.research.sci.biz;

import com.google.common.collect.Lists;
import com.ronglian.kangrui.saas.research.common.context.BaseContextHandler;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Study;
import com.ronglian.kangrui.saas.research.sci.entity.StudyGroup;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyGroupMapper;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyMapper;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.vo.ButtonCodeVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyAddVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyGroupVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.management.BufferPoolMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.ronglian.kangrui.saas.research.sci.consts.RoleTypeConsts.STUDY_ADMIN_BUTTON_ARRAY;
import static java.util.stream.Collectors.toList;

@Service
public class StudyBiz extends BaseBiz<StudyMapper, Study> {

    @Autowired
    private StudyMapper studyMapper ;


    /**
     * 查询项目列表
     * @return
     */
    public List<StudyVo> getStudyList(StudyVo studyVo) {
        List<StudyVo> studyList = studyMapper.getStudyList(studyVo);
        List<StudyVo> resultVo = Lists.newArrayList();
        Long userId = BaseContextHandler.getUserID();
        for (StudyVo each : studyList) {
            StudyVo item = new StudyVo();
            each.setUserId(userId);
            BeanUtils.copyProperties(each,item);
            List<ButtonCodeVo> list = studyMapper.getButtonListByStudyAndUserId(each);
            List<String> codeList = list.stream().map(ButtonCodeVo->ButtonCodeVo.getCode()).collect(Collectors.toList());
            if(codeList.contains(STUDY_ADMIN_BUTTON_ARRAY[0])){
                item.setDeleteEnableFlag(1);
            } else {
                item.setDeleteEnableFlag(0);
            }
            if(codeList.contains(STUDY_ADMIN_BUTTON_ARRAY[1])){
                item.setSettingEnableFlag(1);
            } else {
                item.setSettingEnableFlag(0);
            }
            resultVo.add(item);
        }

        return resultVo ;
    }




    /**
     * 校验项目名称不重复（未删除）
     * @param temp
     * @return
     */
    public List<StudyVo> checkStudyNameExists(StudyVo temp) {
        return studyMapper.checkStudyNameExists(temp) ;
    }



    /**
     * 判断疾病ID是否有 关联的项目，1：存在未删除的项目，不允许删除
     * @param objectIdList
     * @return
     */
    public List<StudyVo> getStudyListByDiseaseIdList(List<Long> objectIdList) {
        return studyMapper.getStudyListByDiseaseIdList(objectIdList) ;
    }
}
