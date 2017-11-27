数据库脚本
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for fengdaicallbackinfo
-- ----------------------------
DROP TABLE IF EXISTS `fengdaicallbackinfo`;
CREATE TABLE `fengdaicallbackinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `requestip` varchar(255) DEFAULT NULL,
  `callbackinfo` text,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for fengdaimockinfo
-- ----------------------------
DROP TABLE IF EXISTS `fengdaimockinfo`;
CREATE TABLE `fengdaimockinfo` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `mockserverip` varchar(255) DEFAULT NULL,
  `checkUrl` varchar(255) DEFAULT NULL,
  `mockType` varchar(255) DEFAULT NULL,
  `ContentType` varchar(255) DEFAULT NULL,
  `checkParams` varchar(255) DEFAULT NULL,
  `responseBody` text,
  `mocktime` datetime DEFAULT NULL,
  `opername` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for fengdaioperlog
-- ----------------------------
DROP TABLE IF EXISTS `fengdaioperlog`;
CREATE TABLE `fengdaioperlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `opertype` varchar(255) DEFAULT NULL,
  `opertime` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=542 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for fengdaiuser
-- ----------------------------
DROP TABLE IF EXISTS `fengdaiuser`;
CREATE TABLE `fengdaiuser` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) DEFAULT NULL,
  `userPassword` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;