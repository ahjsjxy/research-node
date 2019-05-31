package com.ronglian.kangrui.saas.research.sci.biz;


import com.ronglian.kangrui.saas.research.sci.entity.StudyGroup;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyGroupMapper;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.vo.StudyGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class StudyGroupBiz extends BaseBiz<StudyGroupMapper, StudyGroup> {

    @Autowired
    private StudyGroupMapper studyGroupMapper ;


    /**
     * 校验队列名称不重复（1：项目下的队列不重复  2：未删除的队列）
     * @param name
     * @param studyGroupId
     * @return
     */
    public boolean checkStudyGroupNameExists(String name, Long studyId, Long studyGroupId) {
        boolean isRepeat = Boolean.FALSE ;

        // 准备查询参数
        StudyGroupVo studyGroupVoTemp = new StudyGroupVo() ;
        studyGroupVoTemp.setStudyId(studyId);
        List<StudyGroupVo> studyGroupVoList = studyGroupMapper.getGroupListByStudyId(studyGroupVoTemp) ;
        List<StudyGroupVo> repeatList = null ;
        if (studyGroupVoList!=null && studyGroupVoList.size()>0) {
            if (studyGroupId!=null) {
                StudyGroupVo temp = new StudyGroupVo() ;
                temp.setId(studyGroupId);
                temp.setStudyId(studyId);
                temp.setName(name);
                // 编辑的时候判断name是否重复
                repeatList = studyGroupMapper.checkStudyGroupNameExists(temp) ;
            } else {
                repeatList = studyGroupVoList.stream().filter(studyGroupVo -> name.equals(studyGroupVo.getName())).collect(toList());
            }
        }

        isRepeat = (repeatList!=null && repeatList.size()>0) ? Boolean.TRUE : Boolean.FALSE ;
        return isRepeat ;
    }

}
