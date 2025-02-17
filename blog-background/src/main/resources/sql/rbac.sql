USE `hunter_blog`;
# RBAC (基于角色的访问控制) 需要5张表，分别3张实体表、2张关系表。
# 实体表：
# 1. sys_user：用户表，已存在。
# 2. sys_role：角色表
# 3. sys_menu：菜单权限表
# 4. sys_role_menu：角色和菜单关联表
# 5. sys_user_role：用户和角色关联表


-- 角色表
DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name`   varchar(30)  NOT NULL COMMENT '角色名称',
    `role_key`    varchar(100) NOT NULL COMMENT '角色权限字符串',
    `role_sort`   int          NOT NULL COMMENT '显示顺序',
    `status`      char(1)      NOT NULL COMMENT '角色状态（0正常 1停用）',
    `del_flag`    char(1)      DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `create_by`   bigint       DEFAULT NULL COMMENT '创建者',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_by`   bigint       DEFAULT NULL COMMENT '更新者',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
    # 设置了表的自动递增起始值为4，因为初始化时已插入了1-3 id的角色
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb3 COMMENT ='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role`
    DISABLE KEYS */;
INSERT INTO `sys_role`
VALUES (1, '超级管理员', 'admin', 1, '0', '0', 0, '2023-07-12 07:46:19', 0, NULL, '超级管理员'),
       (2, '普通角色', 'common', 2, '0', '0', 0, '2023-08-10 19:46:19', NULL, '2023-08-10 07:01:35', '普通角色'),
       (3, '开发者', 'system:build:test', 3, '0', '0', 1, '2023-08-10 08:12:03', 1, '2023-08-10 09:05:47', '开发者');
/*!40000 ALTER TABLE `sys_role`
    ENABLE KEYS */;
UNLOCK TABLES;


-- 菜单权限表
DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_name`   varchar(50) NOT NULL COMMENT '菜单名称',
    `parent_id`   bigint       DEFAULT '0' COMMENT '父菜单ID',
    `order_num`   int          DEFAULT '0' COMMENT '显示顺序',
    `path`        varchar(200) DEFAULT '' COMMENT '路由地址',
    `component`   varchar(255) DEFAULT NULL COMMENT '组件路径',
    `is_frame`    int          DEFAULT '1' COMMENT '是否为外链（0是 1否）',
    `menu_type`   char(1)      DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `visible`     char(1)      DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
    `status`      char(1)      DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
    `perms`       varchar(100) DEFAULT NULL COMMENT '权限标识',
    `icon`        varchar(100) DEFAULT '#' COMMENT '菜单图标',
    `create_by`   bigint       DEFAULT NULL COMMENT '创建者',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_by`   bigint       DEFAULT NULL COMMENT '更新者',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) DEFAULT '' COMMENT '备注',
    `del_flag`    char(1)      DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2030
  DEFAULT CHARSET = utf8mb3 COMMENT ='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu`
    DISABLE KEYS */;
INSERT INTO `sys_menu`
VALUES (1, '系统管理', 0, 1, 'system', NULL, 1, 'M', '0', '0', '', 'system', 0, '2021-11-12 10:46:19', 0, NULL,
        '系统管理目录', '0'),
       (100, '用户管理', 1, 1, 'user', 'system/user/index', 1, 'C', '0', '0', 'system:user:list', 'user', 0,
        '2021-11-12 10:46:19', 1, '2023-08-10 06:17:35', '用户管理菜单', '0'),
       (101, '角色管理', 1, 2, 'role', 'system/role/index', 1, 'C', '0', '0', 'system:role:list', 'peoples', 0,
        '2021-11-12 10:46:19', 0, NULL, '角色管理菜单', '0'),
       (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', 1, 'C', '0', '0', 'system:menu:list', 'tree-table', 0,
        '2021-11-12 10:46:19', 0, NULL, '菜单管理菜单', '0'),
       (1001, '用户查询', 100, 1, '', '', 1, 'F', '0', '0', 'system:user:query', '#', 0, '2021-11-12 10:46:19', 0, NULL,
        '', '0'),
       (1002, '用户新增', 100, 2, '', '', 1, 'F', '0', '0', 'system:user:add', '#', 0, '2021-11-12 10:46:19', 0, NULL,
        '', '0'),
       (1003, '用户修改', 100, 3, '', '', 1, 'F', '0', '0', 'system:user:edit', '#', 0, '2021-11-12 10:46:19', 0, NULL,
        '', '0'),
       (1004, '用户删除', 100, 4, '', '', 1, 'F', '0', '0', 'system:user:remove', '#', 0, '2021-11-12 10:46:19', 0,
        NULL, '', '0'),
       (1005, '用户导出', 100, 5, '', '', 1, 'F', '0', '0', 'system:user:export', '#', 0, '2021-11-12 10:46:19', 0,
        NULL, '', '0'),
       (1006, '用户导入', 100, 6, '', '', 1, 'F', '0', '0', 'system:user:import', '#', 0, '2021-11-12 10:46:19', 0,
        NULL, '', '0'),
       (1007, '重置密码', 100, 7, '', '', 1, 'F', '0', '0', 'system:user:resetPwd', '#', 0, '2021-11-12 10:46:19', 0,
        NULL, '', '0'),
       (1008, '角色查询', 101, 1, '', '', 1, 'F', '0', '0', 'system:role:query', '#', 0, '2021-11-12 10:46:19', 0, NULL,
        '', '0'),
       (1009, '角色新增', 101, 2, '', '', 1, 'F', '0', '0', 'system:role:add', '#', 0, '2021-11-12 10:46:19', 0, NULL,
        '', '0'),
       (1010, '角色修改', 101, 3, '', '', 1, 'F', '0', '0', 'system:role:edit', '#', 0, '2021-11-12 10:46:19', 0, NULL,
        '', '0'),
       (1011, '角色删除', 101, 4, '', '', 1, 'F', '0', '0', 'system:role:remove', '#', 0, '2021-11-12 10:46:19', 0,
        NULL, '', '0'),
       (1012, '角色导出', 101, 5, '', '', 1, 'F', '0', '0', 'system:role:export', '#', 0, '2021-11-12 10:46:19', 0,
        NULL, '', '0'),
       (1013, '菜单查询', 102, 1, '', '', 1, 'F', '0', '0', 'system:menu:query', '#', 0, '2021-11-12 10:46:19', 0, NULL,
        '', '0'),
       (1014, '菜单新增', 102, 2, '', '', 1, 'F', '0', '0', 'system:menu:add', '#', 0, '2021-11-12 10:46:19', 0, NULL,
        '', '0'),
       (1015, '菜单修改', 102, 3, '', '', 1, 'F', '0', '0', 'system:menu:edit', '#', 0, '2021-11-12 10:46:19', 0, NULL,
        '', '0'),
       (1016, '菜单删除', 102, 4, '', '', 1, 'F', '0', '0', 'system:menu:remove', '#', 0, '2021-11-12 10:46:19', 0,
        NULL, '', '0'),
       (2017, '内容管理', 0, 4, 'content', NULL, 1, 'M', '0', '0', NULL, 'table', NULL, '2022-01-08 02:44:38', 1,
        '2022-07-31 12:34:23', '', '0'),
       (2018, '分类管理', 2017, 1, 'category', 'content/category/index', 1, 'C', '0', '0', 'content:category:list',
        'example', NULL, '2022-01-08 02:51:45', NULL, '2022-01-08 02:51:45', '', '0'),
       (2019, '文章管理', 2017, 0, 'article', 'content/article/index', 1, 'C', '0', '0', 'content:article:list',
        'build', NULL, '2022-01-08 02:53:10', NULL, '2022-01-08 02:53:10', '', '0'),
       (2021, '标签管理', 2017, 6, 'tag', 'content/tag/index', 1, 'C', '0', '0', 'content:tag:index', 'button', NULL,
        '2022-01-08 02:55:37', NULL, '2022-01-08 02:55:50', '', '0'),
       (2022, '友链管理', 2017, 4, 'link', 'content/link/index', 1, 'C', '0', '0', 'content:link:list', '404', NULL,
        '2022-01-08 02:56:50', NULL, '2022-01-08 02:56:50', '', '0'),
       (2023, '写博文', 0, 0, 'write', 'content/article/write/index', 1, 'C', '0', '0', 'content:article:writer',
        'build', NULL, '2022-01-08 03:39:58', 1, '2022-07-31 22:07:05', '', '0'),
       (2024, '友链新增', 2022, 0, '', NULL, 1, 'F', '0', '0', 'content:link:add', '#', NULL, '2022-01-16 07:59:17',
        NULL, '2022-01-16 07:59:17', '', '0'),
       (2025, '友链修改', 2022, 1, '', NULL, 1, 'F', '0', '0', 'content:link:edit', '#', NULL, '2022-01-16 07:59:44',
        NULL, '2022-01-16 07:59:44', '', '0'),
       (2026, '友链删除', 2022, 1, '', NULL, 1, 'F', '0', '0', 'content:link:remove', '#', NULL, '2022-01-16 08:00:05',
        NULL, '2022-01-16 08:00:05', '', '0'),
       (2027, '友链查询', 2022, 2, '', NULL, 1, 'F', '0', '0', 'content:link:query', '#', NULL, '2022-01-16 08:04:09',
        NULL, '2022-01-16 08:04:09', '', '0'),
       (2028, '导出分类', 2018, 1, '', NULL, 1, 'F', '0', '0', 'content:category:export', '#', NULL,
        '2022-01-21 07:06:59', NULL, '2022-01-21 07:06:59', '', '0');
/*!40000 ALTER TABLE `sys_menu`
    ENABLE KEYS */;
UNLOCK TABLES;

-- 角色和菜单关联表
DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu`
(
    `role_id` bigint NOT NULL COMMENT '角色ID',
    `menu_id` bigint NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`, `menu_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3 COMMENT ='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu`
    DISABLE KEYS */;
INSERT INTO `sys_role_menu`
VALUES (0, 0),
       (2, 1),
       (2, 100),
       (2, 101),
       (2, 102),
       (2, 1001),
       (2, 1002),
       (2, 1003),
       (2, 1004),
       (2, 1005),
       (2, 1006),
       (2, 1007),
       (2, 1008),
       (2, 1009),
       (2, 1010),
       (2, 1011),
       (2, 1012),
       (2, 1013),
       (2, 1014),
       (2, 1015),
       (2, 1016),
       (2, 2017),
       (2, 2018),
       (2, 2019),
       (2, 2020),
       (2, 2021),
       (2, 2022),
       (2, 2023),
       (2, 2024),
       (2, 2025),
       (2, 2026),
       (2, 2027),
       (2, 2028);
/*!40000 ALTER TABLE `sys_role_menu`
    ENABLE KEYS */;
UNLOCK TABLES;


-- 用户和角色关联表
DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role`
(
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3 COMMENT ='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role`
    DISABLE KEYS */;
INSERT INTO `sys_user_role`
VALUES (1, 1),
       (2, 2);
/*!40000 ALTER TABLE `sys_user_role`
    ENABLE KEYS */;
UNLOCK TABLES;