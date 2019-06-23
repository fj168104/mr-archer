# DROP DATABASE IF EXISTS `credit`;
CREATE DATABASE `credit`;

USE credit;

-- DROP TABLE IF EXISTS `resources`;
-- CREATE TABLE `resources` (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT,
--   `resource_name` varchar(50) NOT NULL,
--   `resource_desc` varchar(200) DEFAULT NULL,
--   `app_name` varchar(50) NOT NULL,
--   `enabled` bit(1) NOT NULL DEFAULT b'1',
--   `create_by` varchar(20) NOT NULL,
--   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
--   `update_by` varchar(20),
--   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
--   PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
--
--
-- DROP TABLE IF EXISTS `role_privilege`;
-- CREATE TABLE `role_privilege` (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT,
--   `resource_id` bigint(20) NOT NULL,
--   `role_id` bigint(20) NOT NULL,
--   `privilege_value` int(11) NOT NULL,
--   `enabled` bit(1) NOT NULL DEFAULT b'1',
--   `create_by` varchar(20) NOT NULL,
--   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
--   `update_by` varchar(20),
--   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
--   PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
--
--
-- DROP TABLE IF EXISTS `roles`;
-- CREATE TABLE `roles` (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT,
--   `role_name` varchar(50) NOT NULL,
--   `role_desc` varchar(200) DEFAULT NULL,
--   `is_admin` bit(1) NOT NULL,
--   `enabled` bit(1) NOT NULL DEFAULT '1',
--   `create_by` varchar(20) NOT NULL,
--   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
--   `update_by` varchar(20),
--   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
--   PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
--
--
-- DROP TABLE IF EXISTS `user_role`;
-- CREATE TABLE `user_role` (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT,
--   `user_name` varchar(20) NOT NULL,
--   `role_id` varchar(50) NOT NULL,
--   `enabled` bit(1) NOT NULL DEFAULT b'1',
--   `create_by` varchar(20) NOT NULL,
--   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
--   `update_by` varchar(20),
--   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
--   PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `sys_perm`;

CREATE TABLE `sys_perm` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `perm_value` varchar(50) NOT NULL COMMENT '权限值，path值',
  `parent` bigint(20) NOT NULL COMMENT '父权限id',
  `perm_name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `perm_type` int(3) DEFAULT 1 COMMENT '权限类型：1.菜单 2.按钮 3.接口 4.特殊',
  `leaf` tinyint(1) DEFAULT NULL COMMENT '是否叶子节点, 1 是; 0 不是',
  `create_by` varchar(20) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(20),
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限';


/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名，用于显示',
  `role_desc` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `role_value` varchar(100) NOT NULL COMMENT '角色值，用于权限判断',
  `create_by` varchar(20) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(20),
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uidx_role_value` (`role_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

/*Data for the table `sys_role` */

-- insert  into `sys_role`(`role_name`,`role_desc`,`role_value`,`is_admin`,`create_by`,`update_by`) values ('超级管理员','具有本系统中最高权限','root',1,'admin','admin');

/*Table structure for table `sys_role_perm` */

DROP TABLE IF EXISTS `sys_role_perm`;

CREATE TABLE `sys_role_perm` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `perm_id` bigint(20) NOT NULL,
  `create_by` varchar(20) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(20),
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限表';

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) DEFAULT NULL COMMENT '登录名，不可改',
  `nick` varchar(50) DEFAULT NULL COMMENT '用户昵称，可改',
  `mail` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(50) DEFAULT NULL COMMENT '手机',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `lock` tinyint(1) DEFAULT 0 COMMENT '是否锁定, 1 锁定; 0 未锁定',
  `create_by` varchar(20) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(20),
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uidx_user_name` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user_token`;

CREATE TABLE `sys_user_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `token` varchar(50) DEFAULT NULL COMMENT '登录后的token，采用数据库来存储token时使用',
  `username` varchar(50) DEFAULT NULL COMMENT '登录名，不可改',
  `create_by` varchar(20) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(20),
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户登录token表';


/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(25) NOT NULL,
  `role_id` varchar(25) NOT NULL,
  `create_by` varchar(20) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(20),
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户角色表';

--
-- Table structure for table `app_audit`
--

DROP TABLE IF EXISTS `app_audit`;

CREATE TABLE `app_audit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` bigint(20) NOT NULL COMMENT '应用id',
  `user_id` bigint(20) NOT NULL COMMENT '申请人的id',
  `user_name` varchar(64) NOT NULL COMMENT '用户名',
  `type` tinyint(4) NOT NULL COMMENT '申请类型:0:用户注册,1:CHANNEL申请,2:QUEUE申请',
  `param1` varchar(600) DEFAULT NULL COMMENT '预留参数1',
  `param2` varchar(600) DEFAULT NULL COMMENT '预留参数2',
  `param3` varchar(600) DEFAULT NULL COMMENT '预留参数3',
  `info` varchar(360) NOT NULL COMMENT '申请描述',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:等待审批; 1:审批通过; -1:驳回',
  `refuse_reason` varchar(360) DEFAULT NULL COMMENT '驳回理由',
  `create_by` varchar(20) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(20),
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用审核表';

--
-- Table structure for table `app_audit_log`
--
DROP TABLE IF EXISTS `app_audit_log`;

CREATE TABLE `app_audit_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` bigint(20) NOT NULL COMMENT '应用id',
  `user_id` bigint(20) NOT NULL COMMENT '审批操作人id',
  `info` longtext NOT NULL COMMENT 'app审批的详细信息',
  `app_audit_id` bigint(20) NOT NULL COMMENT '审批id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='app审核日志表';

