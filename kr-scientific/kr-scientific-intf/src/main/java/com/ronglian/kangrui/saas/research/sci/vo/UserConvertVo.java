package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 17:04
 **/
@Data
public class UserConvertVo implements Serializable {
    private Long objectId ;// 项目ID或角色ID
    private Integer treeStudyFlag ;//是否项目(0：否  1：是)
    private List<StudyUserVo> selectUserList ;//用户列表
}
