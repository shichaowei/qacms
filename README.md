# qacms
QA搭建平台
提供的通用功能：mock平台 第三方模拟 http请求 时间更改 删除测试数据
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


使用jenknis持续集成，增加测试平台黑盒代码覆盖率检测
1.下载jacoco
2.tomcat启动参数:-javaagent:/data/jacoco/lib/jacocoagent.jar=includes=com.wsc.*,output=tcpserver,port=8044,address=10.200.141.38
-Xverify:none
3.build.xml指定jacoco地址
4.jenknis增加ant dump -buildfile ./build.xml
5.jenknis增加Record JaCoCo coverage report



