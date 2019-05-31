package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 15:18
 **/
@Data
public class StudyRoleTreeVo implements Serializable {
    private Long id;
    private String name;//项目名称
    private Integer treeStudyFlag ;//是否项目(0：否  1：是)
    private List<StudyRoleVo> studyRoleVoList ;//角色列表
}
