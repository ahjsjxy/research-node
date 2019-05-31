package com.ronglian.kangrui.saas.research.sci.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 项目列表
 */
@Table(name = "study")
public class Study implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * 项目类型 1:前瞻性临床研究 2：回顾性临床研究 3：数据库及其它
     */
    private Integer type ;


    /**
     * 项目名称
     */
    private String name;


    /**
     * 试验分期(1: I期临床试验 2:Ⅱ期临床试验 3:Ⅲ期临床试验 4: Ⅳ期临床试验 5:生物等效性试验)
     */
    @Column(name = "test_stage")
    private Integer testStage ;


    /**
     * 入组数量
     */
    @Column(name = "entry_num")
    private Integer entryNum;

    /**
     * 分组设计（0：单臂试验；1：分组实验）
     */
    @Column(name = "group_type")
    private Integer groupType;

    /**
     * 疾病名称
     */
    @Column(name = "disease_id")
    private Long diseaseId;


    /**
     * 项目标志(0: 单中心；1：多中心)
     */
    @Column(name = "study_flag")
    private Integer studyFlag;


    /**
     * 是否删除(0：未删除  1：删除)
     */
    private Integer deleted;


    /**
     * 项目状态(1：创建中、2：已创建)
     */
    private Integer status;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人id
     */
    @Column(name = "create_user")
    private Long createUser;

    /**
     * 创建人姓名
     */
    @Column(name = "create_user_name")
    private String createUserName;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 更新人id
     */
    @Column(name = "update_user")
    private Long updateUser;

    /**
     * 更新人姓名
     */
    @Column(name = "update_user_name")
    private String updateUserName;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取项目名称
     *
     * @return name - 项目名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置项目名称
     *
     * @param name 项目名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    /**
     * 试验分期
     * @return
     */
    public Integer getTestStage() {
        return testStage;
    }


    /**
     * 试验分期
     * @param testStage
     */
    public void setTestStage(Integer testStage) {
        this.testStage = testStage;
    }

    /**
     * 获取入组数量
     *
     * @return entryNum - 入组数量
     */
    public Integer getEntryNum() {
        return entryNum;
    }


    /**
     * 设置入组数量
     *
     * @param entryNum 入组数量
     */
    public void setEntryNum(Integer entryNum) {
        this.entryNum = entryNum;
    }

    /**
     * 获取分组设计（0：单臂试验；1：分组实验）
     *
     * @return group_type - 分组设计（0：单臂试验；1：分组实验）
     */
    public Integer getGroupType() {
        return groupType;
    }

    /**
     * 设置分组设计（0：单臂试验；1：分组实验）
     *
     * @param groupType 分组设计（0：单臂试验；1：分组实验）
     */
    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }



    /**
     * 获取是否删除(0：未删除  1：删除)
     *
     * @return deleted - 是否删除(0：未删除  1：删除)
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 设置是否删除(0：未删除  1：删除)
     *
     * @param deleted 是否删除(0：未删除  1：删除)
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取创建日期
     *
     * @return create_time - 创建日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建日期
     *
     * @param createTime 创建日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建人id
     *
     * @return create_user - 创建人id
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * 设置创建人id
     *
     * @param createUser 创建人id
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取创建人姓名
     *
     * @return create_user_name - 创建人姓名
     */
    public String getCreateUserName() {
        return createUserName;
    }

    /**
     * 设置创建人姓名
     *
     * @param createUserName 创建人姓名
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取更新人id
     *
     * @return update_user - 更新人id
     */
    public Long getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置更新人id
     *
     * @param updateUser 更新人id
     */
    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 获取更新人姓名
     *
     * @return update_user_name - 更新人姓名
     */
    public String getUpdateUserName() {
        return updateUserName;
    }

    /**
     * 设置更新人姓名
     *
     * @param updateUserName 更新人姓名
     */
    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Study other = (Study) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getTestStage() == null ? other.getTestStage() == null : this.getTestStage().equals(other.getTestStage()))
            && (this.getGroupType() == null ? other.getGroupType() == null : this.getGroupType().equals(other.getGroupType()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCreateUserName() == null ? other.getCreateUserName() == null : this.getCreateUserName().equals(other.getCreateUserName()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getUpdateUserName() == null ? other.getUpdateUserName() == null : this.getUpdateUserName().equals(other.getUpdateUserName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getTestStage() == null) ? 0 : getTestStage().hashCode());
        result = prime * result + ((getGroupType() == null) ? 0 : getGroupType().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateUserName() == null) ? 0 : getCreateUserName().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateUserName() == null) ? 0 : getUpdateUserName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", testStage=").append(testStage);
        sb.append(", groupType=").append(groupType);
        sb.append(", deleted=").append(deleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", createUserName=").append(createUserName);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateUserName=").append(updateUserName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }


    public Long getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Integer getStudyFlag() {
        return studyFlag;
    }

    public void setStudyFlag(Integer studyFlag) {
        this.studyFlag = studyFlag;
    }
}