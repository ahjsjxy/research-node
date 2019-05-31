package com.ronglian.kangrui.saas.research.admin.entity;

import java.util.Date;
import javax.persistence.*;

/**
 * 用户信息
 *
 **/
@Table(name = "base_user")
public class User{

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 手机号
     */
    @Column(name = "mobile_phone")
    private String mobilePhone;

    /**
     * 是否平台管理员(1：是，0：否)
     */
    @Column(name = "admin_flag")
    private Integer adminFlag;

    /**
     * 医疗机构ID
     */
    @Column(name = "center_id")
    private Integer centerId;

    /**
     * 科室ID
     */
    @Column(name = "dept_id")
    private Integer deptId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否启用(1:启用， 0：未启用)
     */
    @Column(name = "enable_flag")
    private Integer enableFlag;

    /**
     * 审核状态(1: 审核成功  0：审核失败)
     */
    @Column(name = "audit_status")
    private Integer auditStatus;

    /**
     * 是否删除(0：未删除  1：删除)
     */
    private Integer deleted;

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
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取用户姓名
     *
     * @return name - 用户姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户姓名
     *
     * @param name 用户姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取手机号
     *
     * @return mobile_phone - 手机号
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * 设置手机号
     *
     * @param mobilePhone 手机号
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    /**
     * 获取是否平台管理员(1：是，0：否)
     *
     * @return admin_flag - 是否平台管理员(1：是，0：否)
     */
    public Integer getAdminFlag() {
        return adminFlag;
    }

    /**
     * 设置是否平台管理员(1：是，0：否)
     *
     * @param adminFlag 是否平台管理员(1：是，0：否)
     */
    public void setAdminFlag(Integer adminFlag) {
        this.adminFlag = adminFlag;
    }

    /**
     * 获取医疗机构ID
     *
     * @return center_id - 医疗机构ID
     */
    public Integer getCenterId() {
        return centerId;
    }

    /**
     * 设置医疗机构ID
     *
     * @param centerId 医疗机构ID
     */
    public void setCenterId(Integer centerId) {
        this.centerId = centerId;
    }

    /**
     * 获取科室ID
     *
     * @return dept_id - 科室ID
     */
    public Integer getDeptId() {
        return deptId;
    }

    /**
     * 设置科室ID
     *
     * @param deptId 科室ID
     */
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取是否启用(1:启用， 0：未启用)
     *
     * @return enable_flag - 是否启用(1:启用， 0：未启用)
     */
    public Integer getEnableFlag() {
        return enableFlag;
    }

    /**
     * 设置是否启用(1:启用， 0：未启用)
     *
     * @param enableFlag 是否启用(1:启用， 0：未启用)
     */
    public void setEnableFlag(Integer enableFlag) {
        this.enableFlag = enableFlag;
    }

    /**
     * 获取审核状态(1: 审核成功  0：审核失败)
     *
     * @return audit_status - 审核状态(1: 审核成功  0：审核失败)
     */
    public Integer getAuditStatus() {
        return auditStatus;
    }

    /**
     * 设置审核状态(1: 审核成功  0：审核失败)
     *
     * @param auditStatus 审核状态(1: 审核成功  0：审核失败)
     */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
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
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
                && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getMobilePhone() == null ? other.getMobilePhone() == null : this.getMobilePhone().equals(other.getMobilePhone()))
                && (this.getAdminFlag() == null ? other.getAdminFlag() == null : this.getAdminFlag().equals(other.getAdminFlag()))
                && (this.getCenterId() == null ? other.getCenterId() == null : this.getCenterId().equals(other.getCenterId()))
                && (this.getDeptId() == null ? other.getDeptId() == null : this.getDeptId().equals(other.getDeptId()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
                && (this.getEnableFlag() == null ? other.getEnableFlag() == null : this.getEnableFlag().equals(other.getEnableFlag()))
                && (this.getAuditStatus() == null ? other.getAuditStatus() == null : this.getAuditStatus().equals(other.getAuditStatus()))
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
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getMobilePhone() == null) ? 0 : getMobilePhone().hashCode());
        result = prime * result + ((getAdminFlag() == null) ? 0 : getAdminFlag().hashCode());
        result = prime * result + ((getCenterId() == null) ? 0 : getCenterId().hashCode());
        result = prime * result + ((getDeptId() == null) ? 0 : getDeptId().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getEnableFlag() == null) ? 0 : getEnableFlag().hashCode());
        result = prime * result + ((getAuditStatus() == null) ? 0 : getAuditStatus().hashCode());
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
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", name=").append(name);
        sb.append(", mobilePhone=").append(mobilePhone);
        sb.append(", adminFlag=").append(adminFlag);
        sb.append(", centerId=").append(centerId);
        sb.append(", deptId=").append(deptId);
        sb.append(", remark=").append(remark);
        sb.append(", enableFlag=").append(enableFlag);
        sb.append(", auditStatus=").append(auditStatus);
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

}