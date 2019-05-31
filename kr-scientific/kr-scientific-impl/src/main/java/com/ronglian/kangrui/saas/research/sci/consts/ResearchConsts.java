package com.ronglian.kangrui.saas.research.sci.consts;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-06 18:25
 **/
public class ResearchConsts {
    public static final int DELETED_NO = 0;
    public static final int DELETED_YES = 1;

    /* 项目状态: 1：创建中 2：待发布 3：已发布 4：已完成 */
    public static final int STUDY_STATUS_CREATING = 1 ;//项目状态(1：创建中)
    public static final int STUDY_STATUS_NOT_PUBLISHED = 2 ;//项目状态(2：待发布)
    public static final int STUDY_STATUS_PUBLISHED = 3 ;//项目状态(3：已发布)
    public static final int STUDY_STATUS_FINISHED = 4;//项目状态(4：已完成)

    public static final int CRF_TYPE_FORM = 1 ;//表单类型 1:CRF
    public static final int CRF_TYPE_TESTLETS = 2 ;//表单类型 2:题组


    public static final int GENERATE_TO_DB_NO = 0 ;//动态表单是否已创建（0：未创建）
    public static final int GENERATE_TO_DB_YES = 1 ;//动态表单是否已创建（1：已创建）


    public static final long CRF_FORM_PARENT_ROOT = -1 ;//CRF 根节点


    public static final int STUDY_TREE_LEVEL_STUDY = 1 ;// 树形结构-项目级别
    public static final int STUDY_TREE_LEVEL_CRF_FORM = 2 ;//树形结构-CRF 表单级别
    public static final int STUDY_TREE_LEVELE_CRF_TESTLETS = 3 ;//树形结构- 题组级别


    public static final int DISPLAY_YES = 0 ;// 是否显示(0：显示 )
    public static final int DISPLAY_NO = 1 ;// 是否显示(1：隐藏)


    public static final String STUDY_GROUP_NOT_DELETED_MSG = "该队列存在研究对象，不允许删除" ;
    public static final String STUDY_GROUP_DEL_SUC_MSG = "删除队列成功" ;


    public static final String CRF_FORM_NOT_DELETED_MSG = "CRF已经存在指标值不允许删除" ;
    public static final String CRF_FORM_DEL_SUC_MSG = "删除CRF成功" ;


    public static final String CRF_TESTLETS_NOT_DELETED_MSG = "题组已经存在指标值不允许删除" ;
    public static final String CRF_TESTLETS_DEL_SUC_MSG = "删除题组成功" ;


    public static final String DISEASE_NOT_DELETED_MSG = "疾病存在关联的项目，不允许删除" ;


    /********* 采集是否完成  0: 已填完；1：未填完  2：未开始填 ************/
    public static final int COLLECT_FLAG_FINISHED = 0 ;// 采集是否完成  (0: 已填完)
    public static final int COLLECT_FLAG_NOT_FINISHED = 1 ;// 采集是否完成  (1：未填完)
    public static final int COLLECT_FLAG_NOT_BEGIN = 2 ; // 采集是否完成  (2：未开始填)




    /********* 是否允许操作(0：允许修改删除  1：禁止修改删除) ************/
    public static final int ALLOW_OPERATE_YES = 0 ;//0：允许修改删除
    public static final int ALLOW_OPERATE_NO = 1; //1：禁止修改删除


    /*********  是否项目(0：否  1：是)  ************/
    public static final int TREE_STUDY_FLAG_STUDY = 1 ;//是否项目 1：是
    public static final int TREE_STUDY_FLAG_ROLE = 0 ;//是否项目 0：否
}
