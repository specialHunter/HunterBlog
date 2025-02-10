CREATE DATABASE IF NOT EXISTS `hunter_blog` DEFAULT CHARACTER SET utf8mb4;

USE `hunter_blog`;

--
-- Table structure for table `hunter_tag`
--

DROP TABLE IF EXISTS `hunter_tag`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hunter_tag`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `name`        varchar(128) DEFAULT NULL COMMENT '标签名',
    `create_by`   bigint       DEFAULT NULL,
    `create_time` datetime     DEFAULT NULL,
    `update_by`   bigint       DEFAULT NULL,
    `update_time` datetime     DEFAULT NULL,
    `del_flag`    int          DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='标签';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hunter_tag`
--

LOCK TABLES `hunter_tag` WRITE;
/*!40000 ALTER TABLE `hunter_tag`
    DISABLE KEYS */;
INSERT INTO `hunter_tag`
VALUES (1, '日志', 1, '2023-08-11 07:05:51', 1, '2023-08-11 07:05:51', 0, '我是描述'),
       (2, '开发', 1, '2023-08-11 07:06:08', 1, '2023-08-11 07:06:08', 0, '我是描述'),
       (3, '日常', 1, '2023-08-11 07:07:54', 1, '2023-08-11 07:07:54', 0, '我是描述'),
       (4, '管理', 1, '2023-08-11 07:08:04', 1, '2023-08-11 07:08:04', 0, '我是描述'),
       (5, '办公', 1, '2023-08-11 07:08:18', 1, '2023-08-11 07:08:18', 0, '我是描述'),
       (6, '笔记', 1, '2023-08-11 07:08:26', 1, '2023-08-11 07:08:26', 0, '我是描述'),
       (7, '学校', 1, '2023-08-11 07:09:06', 1, '2023-08-11 07:09:06', 0, '我是描述'),
       (8, '美食', 1, '2023-08-11 07:09:20', 1, '2023-08-11 07:09:20', 0, '我是描述'),
       (9, '学习', 1, '2023-08-11 07:10:09', 1, '2023-08-11 07:10:09', 0, '我是描述'),
       (10, '风景', 1, '2023-08-11 07:10:32', 1, '2023-08-11 07:10:32', 0, '我是描述');
/*!40000 ALTER TABLE `hunter_tag`
    ENABLE KEYS */;
UNLOCK TABLES;