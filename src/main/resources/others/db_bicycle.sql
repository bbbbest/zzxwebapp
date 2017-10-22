-- -------------------------------------------------------------------------------------------------------------------------

DROP DATABASE IF EXISTS sbs \p;
CREATE DATABASE sbs
  DEFAULT CHARSET utf8 \p;
USE sbs \p;

/*
|> 项目里的bean应和数据库保持高度一致，类名与表名一致，变量与列名保持一致，并重写tostring()方法，具体为： class_name:[field:field_value,...]
*/

SET FOREIGN_KEY_CHECKS = 0 \p;

-- ----------------------------
-- Table structure for `adminRole`
-- ----------------------------
DROP TABLE IF EXISTS `adminRole` \p;
CREATE TABLE `adminRole` (
  `adminRoleId`          INT(2)                NOT NULL AUTO_INCREMENT,
  `requireUser`          BOOLEAN DEFAULT FALSE NOT NULL,
  `updateUser`           BOOLEAN DEFAULT FALSE NOT NULL,
  `requireActivity`      BOOLEAN DEFAULT FALSE NOT NULL,
  `updateActivity`       BOOLEAN DEFAULT FALSE NOT NULL,
  `requireAdmin`         BOOLEAN DEFAULT FALSE NOT NULL,
  `updateAdmin`          BOOLEAN DEFAULT FALSE NOT NULL,
  `requireAdvice`        BOOLEAN DEFAULT FALSE NOT NULL,
  `updateAdvice`         BOOLEAN DEFAULT FALSE NOT NULL,
  `requireBicycle`       BOOLEAN DEFAULT FALSE NOT NULL,
  `updateBicycle`        BOOLEAN DEFAULT FALSE NOT NULL,
  `requireCyclingrecord` BOOLEAN DEFAULT FALSE NOT NULL,
  `updateCyclingrecord`  BOOLEAN DEFAULT FALSE NOT NULL,
  `requireDealrecord`    BOOLEAN DEFAULT FALSE NOT NULL,
  `updateDealrecord`     BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (`adminRoleId`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8 \p;

-- ----------------------------
-- Table structure for `user`
-- username 登录账户
-- name 用户姓名
-- cardNumber 持有卡卡号
-- score 积分，主要培养老客户
-- phone 14个字符主要是指+86之类的
-- status 冻结项，0为冻结，留作挂失、恶意惩罚等
-- ----------------------------
DROP TABLE IF EXISTS `user` \p;
CREATE TABLE `user` (
  `userId`     INT(8) UNSIGNED                 NOT NULL AUTO_INCREMENT,
  `userName`   VARCHAR(18)                     NOT NULL UNIQUE,
  `password`   VARCHAR(20)                     NOT NULL,
  `name`       VARCHAR(16)                     NOT NULL,
  `cardNumber` INT(16)                         NOT NULL UNIQUE,
  `score`      INT(11)                         NOT NULL DEFAULT '1',
  `phone`      CHAR(14)                        NOT NULL,
  `status`     TINYINT(1)                      NOT NULL DEFAULT '1',
  `balance`    DECIMAL(8, 2) UNSIGNED ZEROFILL NOT NULL,
  PRIMARY KEY (`userId`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8 \p;

-- ----------------------------
-- Table structure for `activity`
-- userId 创建者
-- status -1代表未被阅读（负数代表未被阅读，正数代表以被阅读）
--        1代表已被阅读但未回复
--        2代表已被阅读并已准许
-- 		  3代表已被阅读并已回绝
-- --------------------------
DROP TABLE IF EXISTS `activity` \p;
CREATE TABLE `activity` (
  `activityId`   INT(10)         NOT NULL AUTO_INCREMENT,
  `userId`       INT(8) UNSIGNED NOT NULL,
  `title`        VARCHAR(20)     NOT NULL,
  `createTime`   DATETIME        NOT NULL,
  `startTime`    DATETIME        NOT NULL,
  `endTime`      DATETIME        NOT NULL,
  `description`  VARCHAR(200)    NOT NULL,
  `status`       TINYINT(1)      NOT NULL DEFAULT -1,
  `replyContent` VARCHAR(500)             DEFAULT NULL,
  PRIMARY KEY (`activityId`),
  CONSTRAINT `activity_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8 \p;

-- ----------------------------
-- Table structure for `admin`
-- adminId 默认最高位为1
-- ----------------------------
DROP TABLE IF EXISTS `admin` \p;
CREATE TABLE `admin` (
  `adminId`     INT(6) UNSIGNED NOT NULL AUTO_INCREMENT,
  `userName`    VARCHAR(18)     NOT NULL,
  `password`    VARCHAR(20)     NOT NULL,
  `name`        VARCHAR(16)     NOT NULL,
  `adminRoleId` INT(2)                   DEFAULT NULL,
  PRIMARY KEY (`adminId`),
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`adminRoleId`) REFERENCES `adminRole` (`adminRoleId`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8 \p;

-- ----------------------------
-- Table structure for `advise`
-- status 0代表未被阅读
--        1代表已被阅读但未回复
--        2代表已被阅读并已回复
-- ----------------------------
DROP TABLE IF EXISTS `advise` \p;
CREATE TABLE `advise` (
  `adviseId`     INT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  `adminId`      INT(8) UNSIGNED          DEFAULT NULL,
  `creator`      INT(8) UNSIGNED NOT NULL,
  `title`        VARCHAR(80)     NOT NULL,
  `content`      VARCHAR(500)    NOT NULL,
  `createTime`   DATETIME        NOT NULL,
  `status`       TINYINT(1)      NOT NULL DEFAULT '0',
  `replyContent` VARCHAR(500)             DEFAULT NULL,
  `replyTime`    DATETIME                 DEFAULT NULL,
  PRIMARY KEY (`adviseId`),
  CONSTRAINT `advise_ibfk_2` FOREIGN KEY (`creator`) REFERENCES `user` (`userId`),
  CONSTRAINT `advise_ibfk_1` FOREIGN KEY (`adminId`) REFERENCES `admin` (`adminId`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8 \p;

-- ----------------------------
-- Records of advise
-- ----------------------------

-- ----------------------------
-- Table structure for `bicycle`
-- from 默认为0即公司采购，否则即为用户编号
-- photoUrl 图片在服务器的路径
-- locationX locationY 自行车最后位置
-- status 0为停止使用，1为正在使用
-- ----------------------------
DROP TABLE IF EXISTS `bicycle` \p;
CREATE TABLE `bicycle` (
  `bicycleId` INT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  `from`      INT(8)          NOT NULL DEFAULT '0',
  `time`      DATETIME        NOT NULL,
  `photoUrl`  VARCHAR(200)    NOT NULL,
  `lockId`    INT(16)         NOT NULL UNIQUE,
  `locationX` DECIMAL(9, 6)            DEFAULT NULL,
  `locationY` DECIMAL(9, 6)            DEFAULT NULL,
  `status`    TINYINT(1)      NOT NULL DEFAULT '0',
  `energy`    FLOAT           NOT NULL,
  PRIMARY KEY (`bicycleId`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8 \p;

-- ----------------------------
-- Table structure for `cyclingrecord`
-- cyclingRecordId 拼接主键：userId + (endTime - startTime)
-- startTime 起始时间
-- startLocX startLocY 起始地点
-- ----------------------------
DROP TABLE IF EXISTS `cyclingrecord` \p;
CREATE TABLE `cyclingrecord` (
  `cyclingRecordId` VARCHAR(22)     NOT NULL,
  `bicycleId`       INT(8) UNSIGNED NOT NULL,
  `userId`          INT(8) UNSIGNED NOT NULL,
  `startTime`       DATETIME        NOT NULL,
  `endTime`         DATETIME        NOT NULL,
  `startLocX`       DECIMAL(9, 6)   NOT NULL,
  `startLocY`       DECIMAL(9, 6)   NOT NULL,
  `endLocX`         DECIMAL(9, 6)   NOT NULL,
  `endLocY`         DECIMAL(9, 6)   NOT NULL,
  PRIMARY KEY (`cyclingRecordId`),
  CONSTRAINT `cyclingrecord_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `cyclingrecord_ibfk_1` FOREIGN KEY (`bicycleId`) REFERENCES `bicycle` (`bicycleId`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8 \p;

-- ----------------------------
-- Records of cyclingrecord
-- ----------------------------

-- ----------------------------
-- Table structure for `dealrecord`
-- dealRecordId 当作订单号，拼接：userId+actionType+actionTime
-- actionType 交易类型，充值为1，消费为0
-- actionTime 默认为当前时间
-- curbalance 每次交易完成后对应的余额
-- alipayStatus 支付宝状态标识##取值：0【不使用支付宝】1【支付宝未验签】2【已验签成功】（其中0，2为合法交易记录，1为非法记录，一段时间后自动删除）
-- ----------------------------
DROP TABLE IF EXISTS `dealrecord` \p;
CREATE TABLE `dealrecord` (
  `dealRecordId` VARCHAR(23)                     NOT NULL,
  `userId`       INT(8) UNSIGNED                 NOT NULL,
  `actionType`   TINYINT(1)                      NOT NULL DEFAULT '0',
  `money`        DECIMAL(8, 2)                   NOT NULL,
  `actionTime`   DATETIME                        NOT NULL,
  `curbalance`   DECIMAL(8, 2) UNSIGNED ZEROFILL NOT NULL,
  `alipayStatus` TINYINT(1)                      NOT NULL DEFAULT '0',
  PRIMARY KEY (`dealRecordId`),
  KEY `userId` (`userId`),
  CONSTRAINT `dealrecord_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8 \p;

-- ----------------------------
-- Records of dealrecord
-- ----------------------------

-- ----------------------------
-- Table structure for `config`
-- price 骑自行车每小时的价钱
-- ----------------------------
DROP TABLE IF EXISTS `config` \p;
CREATE TABLE `config` (
  price DECIMAL(8, 2) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8 \p;

INSERT INTO config VALUES (1) \p;

-- ----------------------------
-- Table structure for `notice`
-- 管理员发布的公告信息
-- ----------------------------
DROP TABLE IF EXISTS `notice` \p;
CREATE TABLE `notice` (
  `noticeId`    INT(10)         NOT NULL AUTO_INCREMENT,
  `adminId`     INT(8) UNSIGNED NOT NULL,
  `title`       VARCHAR(20)     NOT NULL,
  `createTime`  DATETIME        NOT NULL,
  `startTime`   DATETIME        NOT NULL,
  `endTime`     DATETIME        NOT NULL,
  `description` VARCHAR(200)    NOT NULL,
  `status`      TINYINT(1)      NOT NULL DEFAULT '0',
  PRIMARY KEY (`noticeId`),
  CONSTRAINT `notice_ibfk_1` FOREIGN KEY (`adminId`) REFERENCES `admin` (`adminId`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8 \p;

-- ----------------------------
-- Table structure for `userInfo`
-- 骑行信息（在验证骑行用户身份的时候使用）
-- ----------------------------
DROP TABLE IF EXISTS `userInfo` \p;
CREATE TABLE `userInfo` (
  `userId`     INT(10)      NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(200) NOT NULL,
  `school`     VARCHAR(200) NOT NULL,
  `major`      VARCHAR(200) NOT NULL,
  `department` VARCHAR(200) NOT NULL,
  `phone`      VARCHAR(200) NOT NULL,
  PRIMARY KEY (`userId`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8 \p;
SET FOREIGN_KEY_CHECKS = 1 \p;

-- ----------------------------
-- Records of adminRole
-- ----------------------------
insert into adminRole values(null, true, true, true, true, true, true, true, true, true, true, true, true, true, true) \p \p;
insert into adminRole values(null, true, false, true, false, true, false, true, false, true, false, true, false, true, false) \p \p;
insert into adminRole values(null, false, false, false, false, false, false, false, false, false, false, false, false, false, false) \p \p;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO admin VALUES (NULL, 'admin', '888888', '超级管理员', 1) \p;
INSERT INTO admin VALUES (NULL, 'admin2', '888888', '普通管理员', 2) \p;

-- ----------------------------
-- Records of user
-- ----------------------------
insert into user values(null, 'user1', '888888', '骑行用户1', 1, 0, '15838859898', 1, 2000) \p;
insert into user values(null, 'user2', '888888', '骑行用户2', 2, 0, '15989878758', 1, 2000) \p;
insert into user values(null, 'user3', '888888', '骑行用户3', 3, 0, '15978745698', 1, 2000) \p;

-- ----------------------------
-- Records of bicycle
-- ----------------------------
insert into bicycle values (null, 0, now(), "", 1, 0.0, 0.0, 1, 100) \p;
insert into bicycle values (null, 0, now(), "", 2, 0.0, 0.0, 1, 100) \p;
insert into bicycle values (null, 0, now(), "", 3, 0.0, 0.0, 1, 100) \p;
