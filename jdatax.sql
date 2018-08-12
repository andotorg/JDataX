/*
Navicat MySQL Data Transfer

Source Server         : 239
Source Server Version : 50622
Source Host           : 192.168.10.239:3306
Source Database       : jdatax

Target Server Type    : MYSQL
Target Server Version : 50622
File Encoding         : 65001

Date: 2018-08-12 16:07:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `configId` int(10) DEFAULT NULL,
  `configName` varchar(30) DEFAULT NULL,
  `subId` int(10) DEFAULT NULL,
  `subName` varchar(512) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES ('1', '数据库类型', '1', 'MySQL');
INSERT INTO `config` VALUES ('1', '数据库类型', '2', 'Oracle');
INSERT INTO `config` VALUES ('1', '数据库类型', '3', 'SQLServer');
INSERT INTO `config` VALUES ('2', '数据库类', '1', 'com.mysql.jdbc.Driver');
INSERT INTO `config` VALUES ('2', '数据库类', '2', 'oracle.jdbc.driver.OracleDriver');
INSERT INTO `config` VALUES ('2', '数据库类', '3', 'com.microsoft.sqlserver.jdbc.SQLServerDriver');

-- ----------------------------
-- Table structure for dbinfo
-- ----------------------------
DROP TABLE IF EXISTS `dbinfo`;
CREATE TABLE `dbinfo` (
  `dbId` int(10) NOT NULL AUTO_INCREMENT,
  `dbName` varchar(50) DEFAULT NULL,
  `dbUrl` varchar(255) DEFAULT NULL,
  `dbClass` varchar(150) DEFAULT NULL,
  `dbType` int(5) DEFAULT NULL,
  `dbUser` varchar(100) DEFAULT NULL,
  `dbPwd` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`dbId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dbinfo
-- ----------------------------
INSERT INTO `dbinfo` VALUES ('1', 'test', null, null, '0', 'root', 'andot');
