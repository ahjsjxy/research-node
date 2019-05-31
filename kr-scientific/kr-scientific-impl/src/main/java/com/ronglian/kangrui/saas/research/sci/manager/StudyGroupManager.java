package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.sci.biz.StudyGroupBiz;
import com.ronglian.kangrui.saas.research.sci.biz.StudyObjectBiz;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.StudyGroup;
import com.ronglian.kangrui.saas.research.sci.entity.StudyObject;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StudyGroupManager {

    @Autowired
    private StudyGroupBiz studyGroupBiz ;

    @Autowired
    private StudyObjectBiz studyObjectBiz ;


    /**
     * 校验队列名称不重复（1：项目下的队列不重复  2：未删除的队列）
     * @param name
     * @param studyId
     * @param studyGroupId
     * @return
     */
    public boolean checkStudyGroupNameExists(String name, Long studyId, Long studyGroupId) {
        return studyGroupBiz.checkStudyGroupNameExists(name, studyId, studyGroupId) ;
    }


    /**
     * 删除项目队列
     * @param studyGroupId
     * @return
     */
    public Msg deleteStudyGroupInfo(Long studyGroupId) {
        Msg msg = new Msg() ;


        boolean flag = Boolean.FALSE ;
        String desc = null ;
        // 判断当前队列下，是否有研究对象
        List<StudyObject> studyObjectList = studyObjectBiz.getStudyObjectByGroupId(studyGroupId) ;
        if (studyObjectList!=null && studyObjectList.size()>0) {
            flag = (studyObjectList!=null && studyObjectList.size()>0) ;
            desc = ResearchConsts.STUDY_GROUP_NOT_DELETED_MSG ;//该队列存在研究对象，不允许删除
        } else {
            // 队列下没有研究对象，可以删除队列
            StudyGroup studyGroup = new StudyGroup() ;
            studyGroup.setId(studyGroupId);
            studyGroup.setDeleted(ResearchConsts.DELETED_YES);
            studyGroup.setUpdateTime(new Date());
            studyGroupBiz.updateSelectiveById(studyGroup);

            desc = ResearchConsts.STUDY_GROUP_DEL_SUC_MSG ;//删除队列成功
        }

        msg.setSucFlag(flag);
        msg.setDesc(desc);

        return msg ;
    }


}
