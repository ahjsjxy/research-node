package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-12 12:57
 **/
@Data
public class StudyObjectFormVo implements Serializable {
    private Long value ;//form id
    private Integer collectFlag ;//采集是否完成  0: 已填完；1：未填完  2：未开始填
    private boolean hasNewFields ;// crf 是否有新的列未生成字段到DB  【true：有新列  false：无】
    private boolean editFlag ;//是否能点击编辑按钮
}
