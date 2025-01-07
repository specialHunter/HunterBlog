/*
SQLyog v10.2 
MySQL - 5.5.40 : Database - hunter_blog
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE = ''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS */`hunter_blog` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `hunter_blog`;

/*Table structure for table `hunter_link` */

DROP TABLE IF EXISTS `hunter_link`;

CREATE TABLE `hunter_link`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `name`        varchar(256) DEFAULT NULL,
    `logo`        varchar(256) DEFAULT NULL,
    `description` varchar(512) DEFAULT NULL,
    `address`     varchar(128) DEFAULT NULL COMMENT '网站地址',
    `status`      char(1)      DEFAULT '2' COMMENT '审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)',
    `create_by`   bigint(20)   DEFAULT NULL,
    `create_time` datetime     DEFAULT NULL,
    `update_by`   bigint(20)   DEFAULT NULL,
    `update_time` datetime     DEFAULT NULL,
    `del_flag`    int(1)       DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='友链';

/*Data for the table `hunter_link` */

insert into `hunter_link`(`id`, `name`, `logo`, `description`, `address`, `status`, `create_by`, `create_time`,
                          `update_by`, `update_time`, `del_flag`)
values (1, 'sda',
        'https://infinityicon.infinitynewtab.com/user-share-icon/e9b0df13819c1029fdc4287a6a83bf6c.png',
        'sda', 'https://www.douban.com', '1', NULL, '2022-01-13 08:25:47', NULL, '2022-01-13 08:36:14', 0),
       (2, 'sda',
        'https://infinityicon.infinitynewtab.com/user-share-icon/0f2ab700f8fff5b6e9ebc7d6a976981f.png',
        'dada', 'https://www.weibo.com', '1', NULL, '2022-01-13 09:06:10', NULL, '2022-01-13 09:07:09', 0),
       (3, 'sa',
        'https://infinityicon.infinitynewtab.com/user-share-icon/d8b62f4d64bda8800b1c788cd5ba3c68.png',
        'da', 'https://www.bilibili.com', '1', NULL, '2022-01-13 09:23:01', NULL, '2022-01-13 09:23:01', 0);

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;
