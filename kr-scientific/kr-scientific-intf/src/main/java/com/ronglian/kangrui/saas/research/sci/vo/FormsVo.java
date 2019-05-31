package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * form全节点信息
 *
 * @author lanyan
 * @create 2019-03-05 10:58
 **/
@Data
public class FormsVo implements Serializable {

    private Long objectId;
    private List<FormConfigVo> formConfigs;
    private FormDataVo data;


}
