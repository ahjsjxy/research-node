package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-08 17:33
 **/
@Data
public class StudyObjectVo implements Serializable {
    private Long id;
    private Long studyId;
    private Long studyGroupId;
    private Date createTime;//录入时间
    private Date updateTime;//更新时间
    private Map<String, Object> fieldMap ;
}
