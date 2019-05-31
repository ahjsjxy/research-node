package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 研究对象编辑单个Crf Vo
 *
 * @author lanyan
 * @create 2019-03-13 9:42
 **/
@Data
public class FormUpdateVo implements Serializable {
    private FormsVo formsVo ;
    private Long objectId ;

}
