/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : zutan

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 20/01/2018 17:30:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for face_token
-- ----------------------------
DROP TABLE IF EXISTS `face_token`;
CREATE TABLE `face_token`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_lksjn9kv7aej6q1qo1si0dd5k`(`id_card`) USING BTREE,
  UNIQUE INDEX `UK_3qh3njymrgy2cqisnxexn82hw`(`token`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of face_token
-- ----------------------------
INSERT INTO `face_token` VALUES (1, '412824199203124753', 'e1a5c2115efe942c495a4f063840b344');
INSERT INTO `face_token` VALUES (2, '430981199104135435', '516675775cd8aa6bb7e671b3618202a0');

-- ----------------------------
-- Table structure for nim_token
-- ----------------------------
DROP TABLE IF EXISTS `nim_token`;
CREATE TABLE `nim_token`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rong_talk_token
-- ----------------------------
DROP TABLE IF EXISTS `rong_talk_token`;
CREATE TABLE `rong_talk_token`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_b4c4khs3lp5ife6qk9m5jqpj6`(`id_card`) USING BTREE,
  UNIQUE INDEX `UK_4t41d09f02tawjw2h9vuvhmlp`(`token`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rong_talk_token
-- ----------------------------
INSERT INTO `rong_talk_token` VALUES (1, '412824199203124753', 'TtW3sOF3cE0luImP+h1SXLTWmWxzaRJKzBiskcWhiCNGyxGM1TfsnaRKGJaerDgJ4e1PLMMQog2qW8dnghfWQMLR7gLcitERKdKhMS/Nq7s=');
INSERT INTO `rong_talk_token` VALUES (2, '430981199104135435', 'KaS9egMd3Ta2BgKaIc1Omc64uScX5Ccd+//+knO2sVMDPZPYwYSU3Mmj4Alcm0vFL5JjryEhk32FfETgksVPxQbCSqqYDjPUsOp7Dzn0KJs=');

-- ----------------------------
-- Table structure for t_apply_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_apply_relation`;
CREATE TABLE `t_apply_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `apply_id_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `apply_time` datetime(0) DEFAULT NULL,
  `apply_to_id_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `relation` int(11) NOT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  `zu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_apply_relation
-- ----------------------------
INSERT INTO `t_apply_relation` VALUES (3, '430981199104135435', '2018-01-20 17:12:20', '412824199203124753', 0, '申请中', '2018-01-20 17:12:20', 0);

-- ----------------------------
-- Table structure for t_friend
-- ----------------------------
DROP TABLE IF EXISTS `t_friend`;
CREATE TABLE `t_friend`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `career` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `create_user_id` bigint(20) NOT NULL,
  `evaluate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `poster` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `race` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_picture
-- ----------------------------
DROP TABLE IF EXISTS `t_picture`;
CREATE TABLE `t_picture`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `belong_id` bigint(20) DEFAULT NULL,
  `create_id` bigint(20) DEFAULT NULL,
  `create_time` datetime(0) DEFAULT NULL,
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `catong_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `face_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `faceset_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `father_id_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `id_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `is_check` bit(1) NOT NULL,
  `mother_id_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nim_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `race` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `zu_id` bigint(20) DEFAULT NULL,
  `elder_child_id_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `elder_id_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `younger_id_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_dfk6gqsf1j128u59yw9inkjua`(`face_token`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (3, '河南省西平县柏亭街道办事处东吕三组18号', '1992-03-12', 'http://192.168.1.137:8090/default/4.jpg', 'e1a5c2115efe942c495a4f063840b344', NULL, NULL, '412824199203124753', b'0', NULL, '吕志豪', NULL, '3124753', 'http://192.168.1.137:8073/default/0.jpg', '回', '男', 6, NULL, NULL, NULL);
INSERT INTO `t_user` VALUES (4, '湖南省沅江市四季红镇长征路141号', '1991-04-13', 'http://192.168.1.137:8073/default/6.jpg', '516675775cd8aa6bb7e671b3618202a0', NULL, NULL, '430981199104135435', b'0', NULL, '胡勇', NULL, '4135435', 'http://192.168.1.137:8073/default/2.jpg', '汉', '男', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_user_location
-- ----------------------------
DROP TABLE IF EXISTS `t_user_location`;
CREATE TABLE `t_user_location`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `county` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_zu
-- ----------------------------
DROP TABLE IF EXISTS `t_zu`;
CREATE TABLE `t_zu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` date DEFAULT NULL,
  `create_user_id` bigint(20) NOT NULL,
  `holder_user_id` bigint(20) NOT NULL,
  `logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `poster` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `watchword` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_zu
-- ----------------------------
INSERT INTO `t_zu` VALUES (6, '2018-01-18', 3, 3, 'http://192.168.1.137:8073/zu/fbef01516265449514.jpg', '跳一跳', NULL, 0, '古往今来,千秋万代');

SET FOREIGN_KEY_CHECKS = 1;
