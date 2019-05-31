package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.entity.StudyCenter;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyCenterMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-27 16:41
 **/
@Service
public class StudyCenterBiz extends BaseBiz<StudyCenterMapper, StudyCenter> {

    @Autowired
    private StudyCenterMapper studyCenterMapper ;


    /**
     * 获取当前用户默认选中的中心
     * @param userId
     * @return
     */
    public List<HospCenterVo> getUserDefCenterList(Long userId){
        return studyCenterMapper.getUserDefCenterList(userId) ;
    }


    /**
     * 获取项目选中的中心
     * @param studyId
     * @return
     */
    public List<HospCenterVo> getSelectedCenterList(Long studyId){
        return studyCenterMapper.getSelectedCenterList(studyId) ;
    }


    /**
     * 项目-获取未选择的中心
     * @param studyId
     * @return
     */
    public List<HospCenterVo> getValidCenterList(Long studyId){
        return studyCenterMapper.getValidCenterList(studyId) ;
    }



    /**
     * 根据项目ID 删除关联的中心集合
     * @param studyId
     */
    public void deleteByStudyId(Long studyId) {
        studyCenterMapper.deleteByStudyId(studyId) ;
    }
}
