/*
Navicat MySQL Data Transfer

Source Server         : que
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : a

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2020-03-02 00:50:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `u_grade` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '年级',
  `u_college` varchar(50) DEFAULT NULL,
  `u_major` varchar(50) DEFAULT NULL,
  `u_class` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `college` VALUES ('1', '2016', '计算机学院', '软件工程', '16级软工1班');
INSERT INTO `college` VALUES ('2', '2016', '计算机学院', '软件工程', '16级软工创新班');
INSERT INTO `college` VALUES ('3', '2016', '计算机学院', '软件工程', '16级软工2班');
INSERT INTO `college` VALUES ('4', '2016', '计算机学院', '软件工程', '16级软工3班');
INSERT INTO `college` VALUES ('5', '2016', '计算机学院', '软件工程', '16级软工4班');
INSERT INTO `college` VALUES ('6', '2016', '计算机学院', '软件工程', '16级软工5班');
INSERT INTO `college` VALUES ('7', '2016', '计算机学院', '软件工程', '16级软工6班');
INSERT INTO `college` VALUES ('8', '2016', '计算机学院', '软件工程', '16级软工7班');
INSERT INTO `college` VALUES ('9', '2016', '计算机学院', '软件工程', '16级软工8班');
INSERT INTO `college` VALUES ('10', '2016', '计算机学院', '软件工程', '16级软工9班');
INSERT INTO `college` VALUES ('11', '2016', '计算机学院', '软件工程', '16级软工10班');
INSERT INTO `college` VALUES ('12', '2016', '计算机学院', '软件工程', '16级软工11班');
INSERT INTO `college` VALUES ('13', '2016', '计算机学院', '软件工程', '16级软工12班');
INSERT INTO `college` VALUES ('14', '2016', '计算机学院', '软件工程', '16级软工13班');

-- ----------------------------
-- Table structure for complaint
-- ----------------------------
DROP TABLE IF EXISTS `complaint`;
CREATE TABLE `complaint` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stu_id` varchar(25) DEFAULT NULL COMMENT '学号',
  `st_id` bigint(20) DEFAULT NULL COMMENT '素拓认证项',
  `cp_grade` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '年度',
  `cp_term` tinyint(4) DEFAULT NULL COMMENT '学期',
  `cp_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '申诉原因',
  `cp_applytime` datetime DEFAULT NULL COMMENT '申请时间',
  `cp_status` tinyint(4) DEFAULT '0' COMMENT '状态，0/1/2/3，未申诉/申诉中/申诉通过/申诉未通过',
  `cp_chargeid` bigint(20) DEFAULT NULL COMMENT '负责人id',
  `cp_dealtime` datetime DEFAULT NULL COMMENT '处理时间',
  `cp_dealreason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '处理原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of complaint
-- ----------------------------
INSERT INTO `complaint` VALUES ('1', '2016130217', '18', '2020', '1', '改意', '2020-02-20 13:45:56', '2', '3', '2020-02-21 11:47:55', '经核实，给予通过');

-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grade` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1900' COMMENT '年度',
  `term` int(11) DEFAULT '1' COMMENT '学期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES ('1', '2020', '1');

-- ----------------------------
-- Table structure for strelation
-- ----------------------------
DROP TABLE IF EXISTS `strelation`;
CREATE TABLE `strelation` (
  `st_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stu_id` varchar(25) DEFAULT NULL,
  `tuo_id` varchar(10) DEFAULT NULL,
  `st_message` varchar(255) DEFAULT NULL COMMENT '项目内容',
  `st_img` varchar(255) DEFAULT NULL COMMENT '图片',
  `st_grade` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '年度',
  `st_term` tinyint(4) DEFAULT '1' COMMENT '学期',
  `st_actime` varchar(50) DEFAULT NULL COMMENT '活动时间',
  `st_applytime` datetime DEFAULT NULL COMMENT '创建时间',
  `st_checkone` bigint(20) DEFAULT '0' COMMENT '一级负责人',
  `st_one_status` tinyint(4) DEFAULT '0' COMMENT '状态  0/1/2  待审核/通过/未通过',
  `st_one_time` datetime DEFAULT NULL COMMENT '审核时间',
  `st_checktwo` bigint(20) DEFAULT '0' COMMENT '二级负责人',
  `st_two_status` tinyint(4) DEFAULT '0',
  `st_two_time` datetime DEFAULT NULL,
  PRIMARY KEY (`st_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of strelation
-- ----------------------------
INSERT INTO `strelation` VALUES ('1', '2016130217', '10001', '提交入党申请书', '/publicImg/12121.jpg', '2020', '1', '2019.10.1', '2019-11-01 16:25:11', '0', '0', null, '0', '0', null);
INSERT INTO `strelation` VALUES ('2', '2016130217', '10005', '参加升国旗', '/publicImg/20191106111215.jpg', '2020', '1', '2019.10.15', '2019-11-06 03:12:18', '0', '0', null, '0', '0', null);
INSERT INTO `strelation` VALUES ('3', '2016130217', '20003', '声乐课', '/publicImg/20191114144143.jpg', '2020', '1', '2019.08.01', '2019-11-14 06:41:53', '0', '0', null, '0', '0', null);
INSERT INTO `strelation` VALUES ('4', '2016130217', '10003', 'ffff', '/publicImg/20191114145322.jpg', '2020', '1', '31422', '2019-11-14 06:53:27', '3', '1', '2020-02-12 07:10:51', '4', '1', '2020-02-12 07:12:18');
INSERT INTO `strelation` VALUES ('14', '2016130217', '20005', '777', '/publicImg/20191115102233.jpg', '2020', '1', '7777', '2019-11-15 02:22:37', '3', '2', '2020-02-12 06:03:15', '0', '0', null);
INSERT INTO `strelation` VALUES ('16', '2016130217', '30002', '只有', '/university/2020/1/计算机学院/2016130217/16级软工创新班/publicImg/20200212112213.jpg', '2020', '1', '嗡嗡嗡', '2020-02-12 03:22:20', '3', '2', '2020-02-12 06:03:15', '0', '0', null);
INSERT INTO `strelation` VALUES ('17', '2016130217', '20002', '回函和', '/university/2020/1/计算机学院/2016130217/16级软工创新班/publicImg/20200212112819.jpg', '2020', '1', 'hhh', '2020-02-12 03:28:23', '3', '1', '2020-02-12 07:09:49', '4', '2', '2020-02-14 09:08:29');
INSERT INTO `strelation` VALUES ('18', '2016130217', '50001', 'www', '/university/2020/1/计算机学院/2016130217/16级软工创新班/publicImg/20200212113420.jpg', '2020', '1', 'ee', '2020-02-12 03:34:23', '3', '2', '2020-02-14 14:18:02', '0', '0', null);
INSERT INTO `strelation` VALUES ('19', '2016130217', '50002', '让人', '/university/2020/1/计算机学院/2016130217/16级软工创新班/publicImg/20200212113441.png', '2020', '1', '分发给', '2020-02-12 03:34:44', '3', '1', '2020-02-14 14:17:05', '0', '0', null);
INSERT INTO `strelation` VALUES ('21', '2016130217', '40001', '4444', '/university/2020/1/计算机学院/2016130217/16级软工创新班/publicImg/202002291748211138.png', '2020', '1', '5555', '2020-02-29 09:48:24', '0', '0', null, '0', '0', null);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `stu_id` varchar(25) NOT NULL COMMENT '学号',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `stu_psword` varchar(255) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL,
  `stu_grade` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '年级：2016',
  `stu_major` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '专业：软件工程',
  `stu_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '班级',
  `stu_phone` varchar(50) DEFAULT NULL,
  `stu_college` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '学院',
  PRIMARY KEY (`stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('2016130217', '范双雀', 'd5b24d2089276d39100356d27fc72f4331aa316204f404abb92c74c8bfbb5362', '4f1oQ6dDObkwvsT3Eo1V', '2016', '软件工程', '16级软工创新班', '18316102612', '计算机学院');

-- ----------------------------
-- Table structure for stu_score
-- ----------------------------
DROP TABLE IF EXISTS `stu_score`;
CREATE TABLE `stu_score` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `s_stuid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学号',
  `s_grade` varchar(10) DEFAULT '2000' COMMENT '学年',
  `s_term` tinyint(4) DEFAULT '1' COMMENT '学期',
  `s_score` double(5,3) DEFAULT '0.000' COMMENT '总分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of stu_score
-- ----------------------------
INSERT INTO `stu_score` VALUES ('1', '2016130217', '2018', '1', '21.520');
INSERT INTO `stu_score` VALUES ('2', '2016130217', '2018', '2', '15.120');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态   0：不启用   1：启用',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', '0', '云存储配置信息');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `perms` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '授权',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部门管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '0', '超级管理员', 'sys:system,sys:passone,sys:passtwo,sys:appeal,sys:common', '0', '0');
INSERT INTO `sys_dept` VALUES ('2', '1', '一级认证管理员', 'sys:passone,sys:appeal,sys:common', '1', '0');
INSERT INTO `sys_dept` VALUES ('3', '1', '二级认证管理员', 'sys:passtwo,sys:appeal,sys:common', '2', '0');
INSERT INTO `sys_dept` VALUES ('4', '1', '学生', 'sys:student', '3', '0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', '1', '1', '2016-11-11 11:11:11', null);
INSERT INTO `sys_user` VALUES ('3', 'angle', 'b2c016479f4594517281422a8d3d5064f9b5bf4f8a6f82ca7ff9a622e4496925', '4PnHgujbAkGO1IPOP8Qs', null, null, '1', '2', '2019-11-11 03:47:17', '1');
INSERT INTO `sys_user` VALUES ('4', '张三', 'd5b24d2089276d39100356d27fc72f4331aa316204f404abb92c74c8bfbb5362', '4f1oQ6dDObkwvsT3Eo1V', null, null, '1', '2', '2020-02-12 09:55:15', '1');

-- ----------------------------
-- Table structure for tuoproject
-- ----------------------------
DROP TABLE IF EXISTS `tuoproject`;
CREATE TABLE `tuoproject` (
  `tuo_id` varchar(10) NOT NULL,
  `tuo_mess` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `tuo_source` int(4) DEFAULT NULL,
  `tuo_type` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`tuo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tuoproject
-- ----------------------------
INSERT INTO `tuoproject` VALUES ('10001', '递交入党申请书', '1', 'B');
INSERT INTO `tuoproject` VALUES ('10002', '参加院系团学干部培训学习活动', '1', 'B');
INSERT INTO `tuoproject` VALUES ('10003', '参加各种班团活动', '3', 'B');
INSERT INTO `tuoproject` VALUES ('10004', '递交入团申请书并发展为团员', '1', 'B');
INSERT INTO `tuoproject` VALUES ('10005', '参加升国旗活动', '3', 'B');
INSERT INTO `tuoproject` VALUES ('10006', '所在宿舍被评为合格宿舍', '3', 'B');
INSERT INTO `tuoproject` VALUES ('10007', '参加系党团知识竞赛', '3', 'X');
INSERT INTO `tuoproject` VALUES ('10008', '参加团校学习', '2', 'X');
INSERT INTO `tuoproject` VALUES ('10009', '拾金不昧、见义勇为、助人为乐', '4', 'X');
INSERT INTO `tuoproject` VALUES ('10010', '被评为系“优秀团员”', '2', 'X');
INSERT INTO `tuoproject` VALUES ('10011', '被评为系“优秀干部”', '2', 'X');
INSERT INTO `tuoproject` VALUES ('10012', '被评为系“三好学生”', '2', 'X');
INSERT INTO `tuoproject` VALUES ('10013', '被评为系“优秀志愿者”', '2', 'X');
INSERT INTO `tuoproject` VALUES ('10014', '所在班级被评为系“先进班集体”', '2', 'X');
INSERT INTO `tuoproject` VALUES ('10015', '顺利通过团校考试并结业', '3', 'X');
INSERT INTO `tuoproject` VALUES ('10016', '在系学生会被评为“积极分子”', '2', 'X');
INSERT INTO `tuoproject` VALUES ('10017', '参加系级团活动', '4', 'X');
INSERT INTO `tuoproject` VALUES ('10018', '所在宿舍参与系星级宿舍评比', '4', 'X');
INSERT INTO `tuoproject` VALUES ('10019', '所在宿舍被评为系“星级宿舍”', '2', 'X');
INSERT INTO `tuoproject` VALUES ('10020', '所在宿舍参加院星级宿舍评比', '4', 'X');
INSERT INTO `tuoproject` VALUES ('10021', '军训期间被评为内务规范宿舍', '2', 'X');
INSERT INTO `tuoproject` VALUES ('10022', '在系党团知识竞赛中获奖', '2', 'X');
INSERT INTO `tuoproject` VALUES ('10023', '参加党校学习并通过考试', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('10024', '被发展为党员', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('10025', '参加学校组织的思想政治教育和道德实践活动', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('10026', '被评为院“优秀团员”', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('10027', '被评为院“优秀干部”', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('10028', '被评为院“三好学生”', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('10029', '被评为院“优秀志愿者”', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('10030', '所在班级被评为“院先进班集体” ', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('10031', '在校学生会被评为“积极分子”', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('10032', '参加院级团的活动', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('10033', '被评为“军训先进个人”', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('10034', '所在宿舍被评为校“星级宿舍”', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('10035', '在院党团知识竞赛中获奖', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('10036', '在院刊发表思想政治与道德修养文章', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('10037', '被评为“优秀党员”', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('10038', '作为学生代表参加院级以上召开的思想政治教育会议', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('10039', '被评为市级 “优秀志愿者”', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('10040', '被评为省级 “优秀志愿者”', '6', 'Y');
INSERT INTO `tuoproject` VALUES ('10041', '被评为国家级 “优秀志愿者”', '7', 'Y');
INSERT INTO `tuoproject` VALUES ('10042', '无偿献血、捐献骨髓等', '10', 'Y');
INSERT INTO `tuoproject` VALUES ('20001', '参与班级所举行的关于人文素质修养的主题班会等活动 例如礼仪培训 ★', '4', 'B');
INSERT INTO `tuoproject` VALUES ('20002', '参加班级心理健康教育活动（包括班级心理论坛和心理沙龙、心理主题班会）★', '4', 'B');
INSERT INTO `tuoproject` VALUES ('20003', '参加由学校开展的有关于人文素质修养的选修课（中国诗歌、小说、散文、戏剧发展概述；文学作品赏析；音乐、绘画、书法、摄影等艺术鉴赏介绍；事务性文书及新闻写作等），完成选修课学习★', '6', 'X');
INSERT INTO `tuoproject` VALUES ('20004', '参加系举办的人文讲座，直至结束并上交心得体会（不低于200字） ★', '5', 'X');
INSERT INTO `tuoproject` VALUES ('20005', '参加系级相关素质拓展活动（户外拓展训练营、团队协作训练营、论文比赛、心理健康知识讲座等）★', '4', 'X');
INSERT INTO `tuoproject` VALUES ('20006', '参加院级相关素质拓展活动（户外拓展训练营、团队协作训练营、论文比赛等）顺利结训者', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('20007', '参加院级的人文讲座，直到结束并上交心得体会（不低于200字）★', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('20008', '参加院级心理健康教育活动（包括“晴朗杯”心理知识竞赛、“健心杯”心理主题演讲大赛、团体心理训练、“广科杯”校园心理剧大赛、心理工作坊、心灵成长小组、校级大学生综合心理素质训练营等）★', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('30001', '参加班级组织的志愿活动（如去敬老院、儿童康复中心、学雷锋树新风等）★', '4', 'B');
INSERT INTO `tuoproject` VALUES ('30002', '注册志愿者，满一年，经志愿服务中心考核合格', '5', 'X');
INSERT INTO `tuoproject` VALUES ('30003', '扶助失学儿童，访贫问苦，企业观摩，爱心资助，有证明', '2', 'X');
INSERT INTO `tuoproject` VALUES ('30004', '在校期间利用课余时间进行创业试验、兼职实习，勤工俭学，科技实践，挂职锻炼等活动 ★', '6', 'X');
INSERT INTO `tuoproject` VALUES ('30005', '参加系组织志愿者（如去敬老院、儿童康复中心、学雷锋树新风）等活动 ★', '4', 'X');
INSERT INTO `tuoproject` VALUES ('30006', '担任新生兼职副班主任获得认可', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('30007', '评为优秀副班主任', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('30008', '参加“三下乡”社会实践活动者', '2', 'Y');
INSERT INTO `tuoproject` VALUES ('30009', '参加社会治安志愿者活动', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('30010', '参加非学院组织的环境保护等公益活动（有证明） ★', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('30011', '参加寒暑假各种实践活动，例如：暑假工、做义工、学习培训等（提交心得及单位证明）  ★', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('30012', '参加学院组织的志愿者活动  ★', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('30013', '参加回访高中母校宣传学院社会实践活动 ', '6', 'Y');
INSERT INTO `tuoproject` VALUES ('30014', '参加各种合法社会机构的社会调研并撰写社会调研报告上交', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('30015', '参加“三下乡”社会实践活动，并被评为“优秀”', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('40001', '参加班级大学生课外学术科技作品竞赛  ★', '3', 'B');
INSERT INTO `tuoproject` VALUES ('40002', '参加班级大学生创业计划大赛与创业实践   ★', '3', 'B');
INSERT INTO `tuoproject` VALUES ('40003', '参加班级学科专业类竞赛  ★', '5', 'B');
INSERT INTO `tuoproject` VALUES ('40004', '参加系级大学生课外学术科技作品竞赛  ★', '2', 'X');
INSERT INTO `tuoproject` VALUES ('40005', '参加系级大学生创业计划大赛与创业实践★', '4', 'X');
INSERT INTO `tuoproject` VALUES ('40006', '参加系级学科专业类竞赛  ★', '5', 'X');
INSERT INTO `tuoproject` VALUES ('40007', '参加系组织的科普宣传、学术报告等活动表现积极并获得该活动负责任人给予认可', '2', 'X');
INSERT INTO `tuoproject` VALUES ('40008', '参加系组织的知识竞赛、科技文化交流，院组织的科普宣传、学术报告等活动表现积极', '2', 'X');
INSERT INTO `tuoproject` VALUES ('40009', '参加系级各种协助他人完成重要学术课题研究', '2', 'X');
INSERT INTO `tuoproject` VALUES ('40010', '参加系级各种科研考察，独立或参与完成重要学术课题研究', '3', 'X');
INSERT INTO `tuoproject` VALUES ('40011', '参加院级大学生课外学术科技作品竞赛', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('40012', '参加院级大学生创业计划大赛与创业实践★', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('40013', '参加院级学科专业类竞赛', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('40014', '参加院级组织的科普宣传、学术报告等活动表现积极者  ★', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('40015', '参加院级组织的科普知识竞赛、科技文化交流、院组织的科普宣传、学术报告等活动表现积极', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('40016', '参加院级各种协助他人完成重要学术课题研究', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('40017', '参加院级各种科研考察，独立完成重要学术课题研究', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('40018', '在院级正式出版的刊物上，发表论文或其他类文章', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('40019', '参加院工商模拟市场活动', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('40020', '参加市级大学生课外学术科技作品竞赛', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('40021', '参加市级大学生创业计划大赛与创业实践', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('40022', '参加市级学科专业类竞赛', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('40023', '参加市组织的知识竞赛、科技文化交流、科普宣传、学术报告等活动表现积极', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('40024', '凡个人所在班级、社团及相关团队，参与市级以上科技竞赛，可以“×××集体成员”的形式记录', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('40025', '凡个人所在班级、社团及相关团队，参与省级以上科技竞赛，并获得名次，可以“×××集体成员”的形式记录', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('40026', '参加市级各种协助他人完成重要学术课题研究', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('40027', '参加市级各种科研考察，独立完成重要学术课题研究', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('40028', '在市级正式出版的刊物上，发表学术论文或其他类文章', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('40029', '参加省级别大学生课外学术科技作品竞赛', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('40030', '参加省级别大学生创业计划大赛与创业实践', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('40031', '参加省级学科专业类竞赛', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('40032', '参加省级组织的科普宣传、学术报告等活动表现积极/获得名次', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('40033', '参加省级组织的知识竞赛、科技文化交流、科普宣传、学术报告等活动表现积极', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('40034', '协助他人完成重要学术课题研究', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('40035', '在科研方面有重大发现，获得校级以上发明专利或校级以上单位授予“科技进步奖”', '6', 'Y');
INSERT INTO `tuoproject` VALUES ('40036', '参加省级各种科研考察，独立完成重要学术课题研究', '6', 'Y');
INSERT INTO `tuoproject` VALUES ('40037', '在省级正式出版的刊物上，发表学术论文或其他类文章', '6', 'Y');
INSERT INTO `tuoproject` VALUES ('40038', '参加国家级大学生课外学术科技作品竞赛', '6', 'Y');
INSERT INTO `tuoproject` VALUES ('40039', '参加国家级大学生创业计划大赛与创业实践', '6', 'Y');
INSERT INTO `tuoproject` VALUES ('40040', '参加国家级学科专业类竞赛', '6', 'Y');
INSERT INTO `tuoproject` VALUES ('40041', '参加国家组织的知识竞赛、科技文化交流、科普宣传、学术报告等活动表现积极', '6', 'Y');
INSERT INTO `tuoproject` VALUES ('40042', '参加国家各种协助他人完成重要学术课题研究', '6', 'Y');
INSERT INTO `tuoproject` VALUES ('40043', '参加国家各种科研考察，独立完成重要学术课题研究', '7', 'Y');
INSERT INTO `tuoproject` VALUES ('40044', '在国家正式出版的刊物上，发表学术论文或其他类文章（交复印件)', '7', 'Y');
INSERT INTO `tuoproject` VALUES ('40045', '凡个人所在班级、社团及相关团队，参加国家以上科技竞赛，并获得名次，可以“×××集体成员”的形式记录', '6', 'Y');
INSERT INTO `tuoproject` VALUES ('50001', '参加班组织文体活动（篮球、足球、排球、羽毛球比赛等）★', '4', 'B');
INSERT INTO `tuoproject` VALUES ('50002', '参加校内外各种业余比赛  ★', '4', 'B');
INSERT INTO `tuoproject` VALUES ('50003', '参加书画，乐器，象棋比赛等', '2', 'B');
INSERT INTO `tuoproject` VALUES ('50004', '参加系级学生手册知识竞赛或安全知识竞赛   ★', '5', 'X');
INSERT INTO `tuoproject` VALUES ('50005', '参加系辩论赛者  ★', '3', 'X');
INSERT INTO `tuoproject` VALUES ('50006', '参加系文艺活动（演出，竞赛等） ★', '4', 'X');
INSERT INTO `tuoproject` VALUES ('50007', '参加校运会各项体育项目（获优秀奖、三、四、五等奖者）', '2', 'X');
INSERT INTO `tuoproject` VALUES ('50008', '参加院内外各种业余比赛获奖', '4', 'X');
INSERT INTO `tuoproject` VALUES ('50009', '参加系举办的文体活动（如篮球赛，排球、拔河等） ★', '2', 'X');
INSERT INTO `tuoproject` VALUES ('50010', '参加系举办的书画，乐器，象棋等比赛获奖者', '2', 'X');
INSERT INTO `tuoproject` VALUES ('50011', '参加院级学生手册知识竞赛或安全知识竞赛进决赛', '2', 'X');
INSERT INTO `tuoproject` VALUES ('50012', '参加院辩论赛者', '3', 'X');
INSERT INTO `tuoproject` VALUES ('50013', '参加系辩论赛获奖者', '5', 'X');
INSERT INTO `tuoproject` VALUES ('50014', '参加院文体活动（篮球、足球、排球、羽毛球比赛等） ★', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('50015', '参加校运会各项体育项目获一、二等奖', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('50016', '参加院举办的文体活动（如篮球赛，排球、拔河等）★', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('50017', '参加院举办的书画，乐器，象棋比赛获奖者', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('50018', '参加院级学生手册知识竞赛或安全知识竞赛获一等奖', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('50019', '参加院辩论赛获奖者', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('50020', '参加省大学生运动会', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('50021', '参加市级文艺活动（演出，竞赛等）', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('50022', '参加市级各项体育项目获奖', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('50023', '参加省大学生运动会获奖', '6', 'Y');
INSERT INTO `tuoproject` VALUES ('50024', '参加省级文体活动（演出，竞赛等）', '6', 'Y');
INSERT INTO `tuoproject` VALUES ('50025', '参加省级各项体育项目获奖', '7', 'Y');
INSERT INTO `tuoproject` VALUES ('50026', '参加国家级大学生运动会获奖', '7', 'Y');
INSERT INTO `tuoproject` VALUES ('50027', '参加国家级文艺活动（演出，竞赛等）', '8', 'Y');
INSERT INTO `tuoproject` VALUES ('50028', '参加国家级各项体育项目获奖', '20', 'Y');
INSERT INTO `tuoproject` VALUES ('60001', '任班干部（除班长，团支书外）和社团干事任职满一学期且考核成绩合格的', '2', 'B');
INSERT INTO `tuoproject` VALUES ('60002', '积极参加班各类校园文化活动   ★', '3', 'B');
INSERT INTO `tuoproject` VALUES ('60003', '利用报刊，广播，网络，板报等形式，积极宣传班级团学组织活动和第二课堂建设成果取得突出成绩。', '1', 'B');
INSERT INTO `tuoproject` VALUES ('60004', '参加社团举办的内部活动', '1', 'B');
INSERT INTO `tuoproject` VALUES ('60005', '参加社团并成为会员，满一学期，经考核合格  ★', '4', 'B');
INSERT INTO `tuoproject` VALUES ('60006', '成为实践类和学术类社团会员，满一学期，经考核合格，多加二分', '2', 'B');
INSERT INTO `tuoproject` VALUES ('60007', '任班长或团支书职务、学院各组织干事(除系两会)系两会学生干部、协会干部任职满1年且考核成绩合格的', '4', 'X');
INSERT INTO `tuoproject` VALUES ('60008', '参加系各类校园文化活动 ★', '4', 'X');
INSERT INTO `tuoproject` VALUES ('60009', '在系级团学刊物从事编辑工作或在刊物上发表文章', '2', 'X');
INSERT INTO `tuoproject` VALUES ('60010', '利用报刊，广播，网络，板报等形式，积极宣传系级团学组织活动和第二课堂建设成果取得突出成绩', '2', 'X');
INSERT INTO `tuoproject` VALUES ('60011', '社团获得“优秀学生社团”称号，出示社团主要负责人证明，可以“xxx优秀学生社团成员”形式记录（干部、干事、会员）', '2', 'X');
INSERT INTO `tuoproject` VALUES ('60012', '参与组织系级文化活动等经历  ★', '4', 'X');
INSERT INTO `tuoproject` VALUES ('60013', '作为社团代表参加系级义演', '2', 'X');
INSERT INTO `tuoproject` VALUES ('60014', '任各系两会主席团、校两会、志愿服务中心和社联副部以上干部，任职满一学期且考核成绩合格的', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('60015', '积极参加院级各类校园文化活动  ★', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('60016', '在校级团学刊物从事编辑工作或在刊物上发表文章的', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('60017', '利用报刊，广播，网络，板报等形式，积极宣传院级团学组织活动和第二课堂建设成果取得突出成绩', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('60018', '社团、协会获得“优秀学生社团”称号时，经同意并依据社联主要负责人证明，可以“xxx优秀学生社团成员”形式记录（正，副会长）', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('60019', '参与组织院级文化活动等经历 ★', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('60020', '作为社团代表参加校级义演', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('60021', '参加社团举办面向全院的活动   ★', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('60022', '任院两会、志愿服务中心、社联负责人（包括正副主席、秘书长）、任职满1学期且考核成绩合格的', '6', 'Y');
INSERT INTO `tuoproject` VALUES ('60023', '作为学校代表的社团成员参加校外义演', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('60024', '参与组织市级文化活动等经历', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('60025', '参与组织省级文化活动等经历', '6', 'Y');
INSERT INTO `tuoproject` VALUES ('60026', '参与组织国家级文化活动等经历', '7', 'Y');
INSERT INTO `tuoproject` VALUES ('70001', '参与职业资格培训  ★', '3', 'B');
INSERT INTO `tuoproject` VALUES ('70002', '参与技能类培训或实训  ★', '3', 'X');
INSERT INTO `tuoproject` VALUES ('70003', '参与职业资格考试  ', '4', 'X');
INSERT INTO `tuoproject` VALUES ('70004', '参与技能类考试', '4', 'X');
INSERT INTO `tuoproject` VALUES ('70005', '参加系级各种季度，年度职业技能相关比赛（兴趣赛，资格赛，职业资格赛） ★', '4', 'X');
INSERT INTO `tuoproject` VALUES ('70006', '获得职业资格证书', '3', 'X');
INSERT INTO `tuoproject` VALUES ('70007', '获得技能类资格证书(教师证、会计证等)', '3', 'X');
INSERT INTO `tuoproject` VALUES ('70008', '参加院级各种职业技能相关比赛（兴趣赛，资格赛，职业资格赛）★', '3', 'Y');
INSERT INTO `tuoproject` VALUES ('70009', '在院内发表职业、技能相关论文', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('70010', '在院外参与各种季度，年度职业技能相关比赛（兴趣赛，资格赛，职业资格赛）', '4', 'Y');
INSERT INTO `tuoproject` VALUES ('70011', '在市级刊物发表职业、技能等相关论文', '5', 'Y');
INSERT INTO `tuoproject` VALUES ('70012', '在省级刊物发表职业、技能等相关论文', '6', 'Y');
INSERT INTO `tuoproject` VALUES ('70013', '在国家刊物发表职业、技能等相关论文', '7', 'Y');
