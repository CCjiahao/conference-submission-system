/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 101.35.144.217:3306
 Source Schema         : conference_submission_system

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 16/06/2022 16:35:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for expertise
-- ----------------------------
DROP TABLE IF EXISTS `expertise`;
CREATE TABLE `expertise`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of expertise
-- ----------------------------
INSERT INTO `expertise` VALUES (1, 'CV', '计算机视觉是一门研究如何使机器“看”的科学，更进一步的说，就是是指用摄影机和电脑代替人眼对目标进行识别、跟踪和测量等机器视觉，并进一步做图形处理，使电脑处理成为更适合人眼观察或传送给仪器检测的图像。作为一个科学学科，计算机视觉研究相关的理论和技术，试图建立能够从图像或者多维数据中获取‘信息’的人工智能系统。这里所指的信息指Shannon定义的，可以用来帮助做一个“决定”的信息。因为感知可以看作是从感官信号中提 取信息，所以计算机视觉也可以看作是研究如何使人工系统从图像或多维数据中“感知”的科学。');
INSERT INTO `expertise` VALUES (2, 'NLP', 'NLP是神经语言程序学（Neuro-Linguistic Programming）的英文缩写。在香港，也有意译为身心语法程式学的。N（Neuro）指的是神经系统，包括大脑和思维过程。L（Linguistic）是指语言，更准确点说，是指从感觉信号的输入到构成意思的过程。P（Programming）是指为产生某种后果而要执行的一套具体指令。即指我们思维上及行为上的习惯，就如同电脑中的程序，可以透过更新软件而改变。故此，NLP被解释为研究我们的大脑如何工作的学问。也因此，NLP译为身心语法程式学或神经语言程序学。');
INSERT INTO `expertise` VALUES (3, 'ML', '机器学习(Machine Learning)，是研究计算机怎样模拟或实现人类的学习行为，以获取新的知识或技能，重新组织已有的知识结构使之不断改善自身的性能。它是人工智能的核心，是使计算机具有智能的根本途径，其应用遍及人工智能的各个领域，它主要使用归纳、综合而不是演绎。一些常见的机器学习任务列举如下：分类、输入缺失分类、回归、转录、机器翻译、结构化输出、异常检测、合成和采样、缺失值填补、去噪、密度估计等等。');
INSERT INTO `expertise` VALUES (4, 'ASR', 'ASR是指自动语音识别技术（Automatic Speech Recognition），是一种将人的语音转换为文本的技术。语音识别是一个多学科交叉的领域，它与声学、语音学、语言学、数字信号处理理论、信息论、计算机科学等众多学科紧密相连。由于语音信号的多样性和复杂性，语音识别系统只能在一定的限制条件下获得满意的性能，或者说只能应用于某些特定的场合。语音识别系统的性能大致取决于以下4类因素：1. 识别词汇表的大小和语音的复杂性；2. 语音信号的质量；3. 单个说话人还是多说话人；4. 硬件。');
INSERT INTO `expertise` VALUES (10, 'RL', '强化学习（Reinforcement Learning, RL），又称再励学习、评价学习或增强学习，是机器学习的范式和方法论之一，用于描述和解决智能体（agent）在与环境的交互过程中通过学习策略以达成回报最大化或实现特定目标的问题');

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES ('04120f56-cf90-4134-a7a0-ba152fc37015', '测试用例1.pdf');
INSERT INTO `file` VALUES ('052998db-8352-4420-8f86-a883bc46363b', '郭亦铭-项目申请书-222290462.pdf');
INSERT INTO `file` VALUES ('06fc8490-dbfc-4c3d-b8ce-5f42bf19950d', '项目申请模板.pdf');
INSERT INTO `file` VALUES ('1b0e37a8-1cde-4753-8a81-0ad35031c7fc', 'temp (2).pdf');
INSERT INTO `file` VALUES ('1c4aa70c-cbad-4eef-8547-9c527c709b03', 'temp.pdf');
INSERT INTO `file` VALUES ('2e7c670f-acb8-49e2-aeba-6bd27177a1c5', '测试用例5.pdf');
INSERT INTO `file` VALUES ('30b35ae5-4712-46d9-b6db-06380d7c10be', '测试用例4.pdf');
INSERT INTO `file` VALUES ('31d5ea1b-2925-48d5-8af7-d15db4013ac6', 'temp.pdf');
INSERT INTO `file` VALUES ('5b1cc8d9-074a-4211-bd94-1aba027d7a06', '郭亦铭-项目申请书-222290462.pdf');
INSERT INTO `file` VALUES ('5b4790f1-1e52-48f0-bf48-2148740222af', '简历-晁佳豪.pdf');
INSERT INTO `file` VALUES ('71822414-79f4-426f-903f-10ca294c9363', '郭亦铭-项目申请书-222290462.pdf');
INSERT INTO `file` VALUES ('9277cbb5-8f58-4f8e-b6cb-85af2eb99533', '测试用例6.pdf');
INSERT INTO `file` VALUES ('930ba7ae-3853-4cdd-81b8-4672e5d54cfa', '简历-晁佳豪.pdf');
INSERT INTO `file` VALUES ('9fb884e2-0710-4eef-947e-38eb8556538d', '测试用例3.pdf');
INSERT INTO `file` VALUES ('a6994347-2559-4667-8278-0400e569ca2c', 'temp (2).pdf');
INSERT INTO `file` VALUES ('b176e08c-4338-40b6-b254-11eca2bb43ad', '郭亦铭-项目申请书-222290462.pdf');
INSERT INTO `file` VALUES ('b92655d9-a2c4-48d5-86b8-2b697e679041', 'temp.pdf');
INSERT INTO `file` VALUES ('ca7e1b4a-5f1c-4b3e-a04a-1bce11cfc383', '测试用例2.pdf');
INSERT INTO `file` VALUES ('dc25036a-cd92-4696-aa0f-34055485e6eb', 'A mathematical theory of communication.pdf');
INSERT INTO `file` VALUES ('df1a4032-c064-48c1-ba72-090aa0f6bf0a', '测试用例7.pdf');
INSERT INTO `file` VALUES ('e2916257-9bab-468c-829d-84923e70d00d', 'temp.pdf');
INSERT INTO `file` VALUES ('e4f488a3-f0e4-44ac-a092-dc26c5112af5', '项目申请模板.pdf');
INSERT INTO `file` VALUES ('e58b9b6d-3186-4158-a592-8ce003053a0e', '《科学计算导论》期末作业.pdf');
INSERT INTO `file` VALUES ('e87f1426-2ce6-428d-acf4-283f1fa1fb28', '郭亦铭-项目申请书-222290462.pdf');
INSERT INTO `file` VALUES ('f7d2ff1a-938e-4253-95b4-a4c589a825b2', '郭亦铭-项目申请书-222290462.pdf');

-- ----------------------------
-- Table structure for paper
-- ----------------------------
DROP TABLE IF EXISTS `paper`;
CREATE TABLE `paper`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `abstracts` varchar(9999) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `expertise` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `collaborators` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `paper` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `commit_time` datetime NOT NULL,
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of paper
-- ----------------------------
INSERT INTO `paper` VALUES (10, 'ccjiahao', 'CJH的第一篇论文', 'CJH的第一篇论文CJH的第一篇论文CJH的第一篇论文CJH的第一篇论文CJH的第一篇论文CJH的第一篇论文CJH的第一篇论文CJH的第一篇论文CJH的第一篇论文', 'CV', 'GavinGYM,DeanZhong', 'e58b9b6d-3186-4158-a592-8ce003053a0e', '2022-06-13 17:13:04', '已接收');
INSERT INTO `paper` VALUES (11, 'ccjiahao', 'CJH的第二篇论文', 'CJH的第二篇论文CJH的第二篇论文CJH的第二篇论文CJH的第二篇论文CJH的第二篇论文CJH的第二篇论文CJH的第二篇论文CJH的第二篇论文CJH的第二篇论文CJH的第二篇论文CJH的第二篇论文CJH的第二篇论文', 'NLP', '', '1c4aa70c-cbad-4eef-8547-9c527c709b03', '2022-06-13 17:13:35', '已接收');
INSERT INTO `paper` VALUES (12, 'GavinGYM', 'GYM的第一篇论文', 'GYM的第一篇论文GYM的第一篇论文GYM的第一篇论文GYM的第一篇论文GYM的第一篇论文GYM的第一篇论文GYM的第一篇论文GYM的第一篇论文GYM的第一篇论文GYM的第一篇论文GYM的第一篇论文GYM的第一篇论文', 'CV', 'ccjiahao,DeanZhong', '5b4790f1-1e52-48f0-bf48-2148740222af', '2022-06-13 17:14:46', '已拒绝');
INSERT INTO `paper` VALUES (13, 'GavinGYM', 'GYM的第二篇论文', 'GYM的第二篇论文GYM的第二篇论文GYM的第二篇论文GYM的第二篇论文GYM的第二篇论文GYM的第二篇论文GYM的第二篇论文GYM的第二篇论文GYM的第二篇论文GYM的第二篇论文GYM的第二篇论文GYM的第二篇论文GYM的第二篇论文', 'NLP', '', '31d5ea1b-2925-48d5-8af7-d15db4013ac6', '2022-06-13 17:16:09', '待辩论');
INSERT INTO `paper` VALUES (14, 'DeanZhong', 'ZD的第一篇论文', 'ZD的第一篇论文ZD的第一篇论文ZD的第一篇论文ZD的第一篇论文ZD的第一篇论文ZD的第一篇论文ZD的第一篇论文ZD的第一篇论文ZD的第一篇论文ZD的第一篇论文ZD的第一篇论文ZD的第一篇论文', 'CV', 'ccjiahao,GavinGYM', 'e2916257-9bab-468c-829d-84923e70d00d', '2022-06-13 17:17:03', '已确认');
INSERT INTO `paper` VALUES (15, 'DeanZhong', 'ZD的第二篇论文', 'ZD的第二篇论文ZD的第二篇论文ZD的第二篇论文ZD的第二篇论文ZD的第二篇论文ZD的第二篇论文ZD的第二篇论文ZD的第二篇论文ZD的第二篇论文ZD的第二篇论文ZD的第二篇论文ZD的第二篇论文ZD的第二篇论文ZD的第二篇论文', 'NLP', '', 'e4f488a3-f0e4-44ac-a092-dc26c5112af5', '2022-06-13 17:17:28', '待辩论');
INSERT INTO `paper` VALUES (16, 'ccjiahao', 'CJH的第三篇论文', 'CJH的第三篇论文CJH的第三篇论文CJH的第三篇论文CJH的第三篇论文CJH的第三篇论文CJH的第三篇论文CJH的第三篇论文CJH的第三篇论文CJH的第三篇论文', 'CV', '', 'b92655d9-a2c4-48d5-86b8-2b697e679041', '2022-06-13 18:20:58', '已辩论');
INSERT INTO `paper` VALUES (17, 'DeanZhong', '测试审阅', '我的母亲有 蓝色 的眼睛，我的父亲有 碧绿色 的眼睛。', 'CV', '', 'dc25036a-cd92-4696-aa0f-34055485e6eb', '2022-06-13 21:07:19', '已拒绝');
INSERT INTO `paper` VALUES (18, 'ccjiahao', '第四篇论文', '第四篇论文第四篇论文第四篇论文第四篇论文第四篇论文第四篇论文第四篇论文第四篇论文第四篇论文第四篇论文第四篇论文第四篇论文', 'CV', '', '1b0e37a8-1cde-4753-8a81-0ad35031c7fc', '2022-06-14 16:05:39', '已接收');
INSERT INTO `paper` VALUES (25, 'ccjiahao', '测试邮件', '测试邮件', 'CV', '', 'a6994347-2559-4667-8278-0400e569ca2c', '2022-06-15 01:42:54', '已辩论');
INSERT INTO `paper` VALUES (26, 'DeanZhong', '测试用例1', '这是测试用例1，分类为CV,无协作者', 'CV', '', '04120f56-cf90-4134-a7a0-ba152fc37015', '2022-06-15 11:16:53', '待辩论');
INSERT INTO `paper` VALUES (27, 'DeanZhong', '测试用例2', '这是测试用例2，分类CV，无协作者，测试评审完成返回大厅后是否会刷新', 'CV', '', 'ca7e1b4a-5f1c-4b3e-a04a-1bce11cfc383', '2022-06-15 11:30:47', '待辩论');
INSERT INTO `paper` VALUES (28, 'DeanZhong', '测试用例3', 'asdfas', 'CV', '', '9fb884e2-0710-4eef-947e-38eb8556538d', '2022-06-15 12:01:07', '待辩论');
INSERT INTO `paper` VALUES (29, 'ccjiahao', '论母猪的产后护理', '母猪的产后护理十分重要，母猪的产后护理十分重要，母猪的产后护理十分重要，母猪的产后护理十分重要，母猪的产后护理十分重要。', 'NLP', '', '06fc8490-dbfc-4c3d-b8ce-5f42bf19950d', '2022-06-15 13:08:51', '待审核');
INSERT INTO `paper` VALUES (30, 'DeanZhong', '测试用例4', '这是测试用例4', 'CV', '', '30b35ae5-4712-46d9-b6db-06380d7c10be', '2022-06-15 15:34:02', '已辩论');
INSERT INTO `paper` VALUES (31, 'DeanZhong', '测试用例5', '这是测试用例5', 'CV', '', '2e7c670f-acb8-49e2-aeba-6bd27177a1c5', '2022-06-15 16:02:23', '已辩论');
INSERT INTO `paper` VALUES (32, 'DeanZhong', '测试用例6', '修改辩驳之后应该给审稿人发送邮件的bug', 'CV', '', '9277cbb5-8f58-4f8e-b6cb-85af2eb99533', '2022-06-15 16:33:47', '已拒绝');
INSERT INTO `paper` VALUES (33, 'DeanZhong', '测试用例7', '这是测试用例7，分类是ML,协作者为审稿人reviewer', 'ML', 'reviewer', 'df1a4032-c064-48c1-ba72-090aa0f6bf0a', '2022-06-16 10:07:12', '待审核');
INSERT INTO `paper` VALUES (34, 'ccjiahao', '测试新的标签', '测试新的标签测试新的标签测试新的标签测试新的标签测试新的标签', 'ASR', 'DeanZhong', '930ba7ae-3853-4cdd-81b8-4672e5d54cfa', '2022-06-16 14:57:04', '待审核');

-- ----------------------------
-- Table structure for rebuttal
-- ----------------------------
DROP TABLE IF EXISTS `rebuttal`;
CREATE TABLE `rebuttal`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `paper_id` int NOT NULL,
  `reviewer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `rebuttal` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rebuttal
-- ----------------------------
INSERT INTO `rebuttal` VALUES (6, 10, 'reviewer', '我觉得你写得很好，我后续会认真的吸取');
INSERT INTO `rebuttal` VALUES (7, 12, 'reviewer', '开发商的菊花开了世界东方航空楼上的回复累计发电量陆军空军和范德萨客流高峰卡的话三个房间尽管受到激发和嘎就开始的功夫尽快哈岁的');
INSERT INTO `rebuttal` VALUES (8, 14, 'reviewer', '而尔特瑞特瑞特让他问问让台湾人特为让他而特温特热舞涛涛涛涛涛涛涛涛涛涛涛涛他');
INSERT INTO `rebuttal` VALUES (9, 17, 'reviewer', '这是我的rebuttal');
INSERT INTO `rebuttal` VALUES (10, 11, 'chairman', '委曲求全钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱失而复得顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶士大夫方法方法烦烦烦烦烦烦烦烦烦烦烦烦');
INSERT INTO `rebuttal` VALUES (11, 16, 'reviewer', '我钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱钱开啥会的卡号是单卡双卡的计划空间是单卡双卡好看的货物i');
INSERT INTO `rebuttal` VALUES (12, 18, 'chairman', '请问撒大了就爱上了滴哦请我喝酒客户打死u以后千万倍权威u啊速度变快了');
INSERT INTO `rebuttal` VALUES (13, 25, 'reviewer', '请问呃呃呃呃呃呃呃呃呃呃呃呃呃呃呃呃呃请问恶气委屈请问');
INSERT INTO `rebuttal` VALUES (14, 31, 'reviewer', '这是我的辩驳内容！');
INSERT INTO `rebuttal` VALUES (15, 30, 'reviewer', '辩驳之后，应该给审稿人发邮件通知');
INSERT INTO `rebuttal` VALUES (16, 32, 'reviewer', '好的，你看看邮箱里收没收到我的辩驳结果？');

-- ----------------------------
-- Table structure for review
-- ----------------------------
DROP TABLE IF EXISTS `review`;
CREATE TABLE `review`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `reviewer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `paper_id` int NOT NULL,
  `is_associated` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `logic` int NOT NULL,
  `sci` int NOT NULL,
  `innovation` int NOT NULL,
  `pass_or_reject` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `suggestion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `review_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of review
-- ----------------------------
INSERT INTO `review` VALUES (8, 'reviewer', 10, '符合', 67, 68, 92, '弱接收', 'i会打款时间和打卡时间很多卡素好低啊撒考核人分厘卡个无愧于人体骨骼研发速度快ui敢死队风格有咖啡圣迭戈火箭大胜高房价', '2022-06-13 17:18:06');
INSERT INTO `review` VALUES (9, 'reviewer', 12, '符合', 100, 100, 100, '强接收', '主席有没有收到？', '2022-06-13 17:18:35');
INSERT INTO `review` VALUES (11, 'reviewer', 16, '符合', 43, 18, 82, '弱接收', '撒旦卢卡斯京东卡手机贺卡上打开链接哈是还款时间蝴蝶卡杀手空间的哈萨克将很快客户看见啥肯德基哈萨克觉得很喀什狂欢节狂欢节撒旦看见哈桑h', '2022-06-13 18:41:36');
INSERT INTO `review` VALUES (16, 'reviewer', 17, '符合', 9, 9, 8, '强拒绝', '123456', '2022-06-13 21:09:39');
INSERT INTO `review` VALUES (18, 'chairman', 11, '符合', 52, 49, 40, '强拒绝', '客户接受度卡拉胶话费卡会考虑放寒假打客户接受度卡拉胶话费卡会考虑放寒假打算客户接受度卡拉胶话费卡会考虑放寒假打客户接受度卡拉胶话费卡会考虑放寒假打算', '2022-06-14 15:58:30');
INSERT INTO `review` VALUES (20, 'chairman', 13, '符合', 33, 33, 33, '弱拒绝', '我去恶趣味请问请问请问请问请问请问请问请问请问请问请问请问请问请问', '2022-06-14 16:01:12');
INSERT INTO `review` VALUES (21, 'chairman', 15, '符合', 47, 15, 85, '强接收', '我去恶趣味请问请问请问请问请问请问请问请问请问请问请问请问请问请问啊实打实水水水水水水水水水水水水水水水手动阀恶法厄访问的二七二七二微软大厦', '2022-06-14 16:04:50');
INSERT INTO `review` VALUES (22, 'chairman', 18, '符合', 51, 68, 43, '强接收', '阿萨大厦库哈斯打开手段是对手大噶山豆根看士大夫发射点发射点会发生都会', '2022-06-14 16:06:37');
INSERT INTO `review` VALUES (23, 'reviewer', 25, '符合', 33, 33, 33, '弱拒绝', '完全呃呃呃呃呃呃呃呃呃呃呃23五千万', '2022-06-15 01:47:18');
INSERT INTO `review` VALUES (24, 'reviewer', 26, '不符合', 0, 0, 0, '强拒绝', '', '2022-06-15 11:18:37');
INSERT INTO `review` VALUES (25, 'reviewer', 27, '符合', 33, 33, 33, '弱拒绝', '这是测试用例2的评审意见，评审意见为空的条件约束已完善。', '2022-06-15 11:58:18');
INSERT INTO `review` VALUES (26, 'reviewer', 28, '符合', 33, 33, 33, '弱拒绝', '撒打发斯蒂芬', '2022-06-15 12:01:55');
INSERT INTO `review` VALUES (27, 'chairman', 14, '符合', 33, 33, 33, '弱拒绝', '我去恶趣味请问请问请问请问请问请问请问请问请问请问请问请问请问请问', '2022-06-14 16:01:12');
INSERT INTO `review` VALUES (28, 'reviewer', 30, '符合', 76, 86, 50, '强接收', '修改评审意见提交后返回审稿记录页', '2022-06-15 15:58:26');
INSERT INTO `review` VALUES (29, 'reviewer', 31, '符合', 79, 95, 85, '强接收', '这是评审意见5', '2022-06-15 16:04:32');
INSERT INTO `review` VALUES (30, 'reviewer', 32, '符合', 80, 81, 82, '弱拒绝', '我收到了辩驳意见了哦，主席和作者收没收到最终的确认意见啊？', '2022-06-15 16:34:47');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `school` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `expertise` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('ccjiahao', '晁佳豪', '123456', '51215902006@stu.ecnu.edu.cn', 'ECNU', '中国', 'CV,ASR', 'researcher');
INSERT INTO `user` VALUES ('chairman', '期刊主席', '123456', '13462260020@163.com', 'ECNU', '中国', 'CV,ML,NLP,ASR,RL', 'chairman');
INSERT INTO `user` VALUES ('DeanZhong', '仲点', '123456', '51215902005@stu.ecnu.edu.cn', 'ECNU', '中国', 'CV', 'researcher');
INSERT INTO `user` VALUES ('GavinGYM', '郭亦铭', '981211', 'gavin-gym@foxmail.com', 'ECNU', '中国', 'CV,ML,NLP', 'researcher');
INSERT INTO `user` VALUES ('reviewer', '审稿人', '123456', 'Dean_Zhong912@163.com', 'ECNU', '中国', 'CV,ML', 'reviewer');

-- ----------------------------
-- Table structure for verification_code
-- ----------------------------
DROP TABLE IF EXISTS `verification_code`;
CREATE TABLE `verification_code`  (
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `expiration_time` datetime NOT NULL,
  PRIMARY KEY (`email`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of verification_code
-- ----------------------------
INSERT INTO `verification_code` VALUES ('13462260020@163.com', '665508', '2022-06-13 22:55:18');
INSERT INTO `verification_code` VALUES ('51215902006@stu.ecnu.edu.cn', '750952', '2022-06-13 22:36:08');

SET FOREIGN_KEY_CHECKS = 1;
