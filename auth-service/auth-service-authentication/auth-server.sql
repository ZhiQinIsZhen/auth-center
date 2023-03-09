CREATE TABLE `auth_application`  (
     `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
     `application_name` varchar(128) NOT NULL COMMENT '应用名',
     `dubbo_group` varchar(32) NOT NULL COMMENT 'dubbo组',
     `dubbo_version` varchar(16) NOT NULL COMMENT 'dubbo版本号',
     `dubbo_timeout` int(10) NOT NULL DEFAULT 1000 COMMENT 'dubbo超时时间',
     `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
     `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 | 0、未删除 1、已删除',
     `version` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '版本号',
     PRIMARY KEY (`id`) USING BTREE,
     UNIQUE INDEX `uniq_application_name`(`application_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '认证服务应用表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_application
-- ----------------------------
INSERT INTO `auth_application` VALUES (1, 'open-api', 'user', '1.0.0', 1000000, '2022-09-02 15:18:21', '2022-09-02 15:18:21', 0, 0);

CREATE TABLE `auth_jwt` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `application_name` varchar(128) NOT NULL COMMENT '应用名',
    `jwt_prefix` varchar(32) NOT NULL COMMENT 'token前缀',
    `signing_key` varchar(16) NOT NULL COMMENT 'jwt的签名key',
    `expiration` bigint(20) NOT NULL DEFAULT '604800' COMMENT 'jwt的签名失效时间(s)',
    `signature_algorithm` varchar(16) NOT NULL DEFAULT 'HS512' COMMENT 'jwt的签名算法',
    `is_authority` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否权限控制(0:没有;1:有)',
    `one_online` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否同设备一个在线(0:没有限制;1:同设备只有一个)',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 | 0、未删除 1、已删除',
    `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_application_name` (`application_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='认证服务jwt信息配置表';

INSERT INTO `auth`.`auth_jwt`(`id`, `application_name`, `jwt_prefix`, `signing_key`, `expiration`, `signature_algorithm`, `is_authority`, `one_online`, `create_time`, `update_time`, `is_deleted`, `version`) VALUES (1, 'open-api', 'Bearer ', 'Bonnie', 604800, 'HS512', 0, 1, '2022-09-06 13:47:45', '2022-09-07 10:03:18', 0, 0);
