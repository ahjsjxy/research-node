-- 1,科研端按钮
CREATE TABLE `portal_button` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(100) DEFAULT NULL COMMENT '项目下-按钮代码',
  `name` varchar(100) DEFAULT NULL COMMENT '项目下-按钮名称',
  `deleted` int(11) DEFAULT '0' COMMENT '是否删除(0：未删除  1：删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



-- 2,科研端项目用户关联中心
CREATE TABLE `study_user_center` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `study_user_id` bigint(20) NOT NULL COMMENT 'study_user表的主键ID',
  `center_id` bigint(20) NOT NULL COMMENT '中心ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- 3,科研端项目用户关联队列
CREATE TABLE `study_user_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `study_user_id` bigint(20) NOT NULL COMMENT 'study_user表的主键ID',
  `group_id` bigint(20) NOT NULL COMMENT '队列ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- 4,科研端项目关联中心
CREATE TABLE `study_center` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `study_id` bigint(20) NOT NULL COMMENT '项目ID',
  `center_id` bigint(20) NOT NULL COMMENT '医疗机构ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- 5,科研端角色关联项目表
CREATE TABLE `study_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `study_id` bigint(20) NOT NULL COMMENT '项目ID',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `allow_delete` int(11) DEFAULT NULL COMMENT '是否允许删除(0：可删除  1：禁止删除)',
  `deleted` int(11) DEFAULT '0' COMMENT '是否删除(0：未删除  1：删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



-- 6,科研端角色关联按钮表
CREATE TABLE `study_role_button` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) NOT NULL COMMENT 'study_role表的主键ID',
  `button_code` bigint(20) NOT NULL COMMENT '按钮代码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- 7,科研端角色关联用户表
CREATE TABLE `study_role_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) NOT NULL COMMENT 'study_role表的主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- 8,科研端项目关联用户表
CREATE TABLE `study_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `study_id` bigint(20) NOT NULL COMMENT '项目ID',
  `user_id` bigint(20) NOT NULL COMMENT '成员ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


alter table study_object add COLUMN center_id bigint(20) NOT NULL COMMENT '中心ID';

alter table study_object add COLUMN `study_flag` int(11) DEFAULT '0' COMMENT '项目类型(0: 单中心；1：多中心)'