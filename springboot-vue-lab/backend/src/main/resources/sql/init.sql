CREATE DATABASE IF NOT EXISTS web
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE web;

DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS user_info;
DROP TABLE IF EXISTS login_user;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS `user`;

CREATE TABLE login_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  birthday DATE NOT NULL,
  nickname VARCHAR(50),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE user_info (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  gender VARCHAR(10) NOT NULL,
  age INT NOT NULL,
  phone VARCHAR(20) NOT NULL,
  department VARCHAR(50) NOT NULL,
  status VARCHAR(20) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE article (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(120) NOT NULL,
  category VARCHAR(50) NOT NULL,
  author VARCHAR(50) NOT NULL,
  summary VARCHAR(500) NOT NULL,
  status VARCHAR(20) NOT NULL,
  publish_time DATETIME,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO login_user (username, password, email, birthday, nickname) VALUES
('admin', '123456', 'admin@example.com', '2002-06-01', '管理员'),
('student', '123456', 'student@example.com', '2004-09-12', '学生用户');

INSERT INTO user_info (name, gender, age, phone, department, status) VALUES
('林清雅', '女', 21, '13800000001', '内容运营部', '正常'),
('周明远', '男', 22, '13800000002', '技术支持部', '正常'),
('陈思雨', '女', 20, '13800000003', '用户服务部', '正常'),
('何嘉诚', '男', 23, '13800000004', '产品策划部', '待审核'),
('赵若曦', '女', 21, '13800000005', '视觉设计部', '正常'),
('孙浩然', '男', 24, '13800000006', '内容运营部', '停用'),
('吴梦瑶', '女', 22, '13800000007', '市场推广部', '正常'),
('郑宇航', '男', 20, '13800000008', '技术支持部', '正常'),
('刘芷萱', '女', 23, '13800000009', '用户服务部', '待审核'),
('王景行', '男', 22, '13800000010', '产品策划部', '正常'),
('顾安宁', '女', 21, '13800000011', '视觉设计部', '正常'),
('唐逸辰', '男', 24, '13800000012', '市场推广部', '正常'),
('宋知夏', '女', 20, '13800000013', '内容运营部', '正常'),
('许承泽', '男', 23, '13800000014', '技术支持部', '正常'),
('马依然', '女', 22, '13800000015', '用户服务部', '正常'),
('冯景澄', '男', 21, '13800000016', '产品策划部', '待审核'),
('梁舒桐', '女', 24, '13800000017', '视觉设计部', '正常'),
('邹远航', '男', 22, '13800000018', '市场推广部', '正常'),
('谢雨晴', '女', 21, '13800000019', '内容运营部', '停用'),
('姜泽宇', '男', 25, '13800000020', '技术支持部', '正常'),
('方一诺', '女', 20, '13800000021', '用户服务部', '正常'),
('邱明轩', '男', 23, '13800000022', '产品策划部', '正常'),
('罗诗涵', '女', 22, '13800000023', '视觉设计部', '待审核'),
('潘嘉木', '男', 24, '13800000024', '市场推广部', '正常'),
('程安琪', '女', 21, '13800000025', '内容运营部', '正常'),
('袁子墨', '男', 22, '13800000026', '技术支持部', '正常'),
('戴星辰', '女', 23, '13800000027', '用户服务部', '正常'),
('魏思远', '男', 20, '13800000028', '产品策划部', '停用'),
('韩若琳', '女', 24, '13800000029', '视觉设计部', '正常'),
('沈亦凡', '男', 21, '13800000030', '市场推广部', '正常');

INSERT INTO article (title, category, author, summary, status, publish_time) VALUES
('校园活动预约流程更新', '通知公告', '管理员', '整理活动场地预约、审批和签到流程，帮助学生组织快速完成线上申请。', '已发布', NOW()),
('五月学习计划推荐', '学习资料', '管理员', '围绕 Web 前端、Spring Boot 和数据库实践提供一周学习安排。', '已发布', NOW()),
('用户中心功能上线说明', '系统说明', '管理员', '介绍登录注册、密码找回、用户列表和文章管理等核心功能。', '已发布', NOW()),
('移动端页面适配规范', '设计规范', '设计组', '说明系统在手机端的菜单折叠、表格滚动和表单布局处理方式。', '草稿', NOW()),
('期末项目验收准备清单', '教学安排', '教师助理', '汇总项目运行、数据库初始化、实验报告截图和答辩演示注意事项。', '已发布', NOW()),
('内容审核规则说明', '运营规范', '运营组', '对文章标题、摘要、分类和发布状态进行规范化管理。', '草稿', NOW()),
('新生账号开通指南', '通知公告', '管理员', '说明新用户账号开通、初始密码修改和个人信息完善流程。', '已发布', NOW()),
('资料中心使用说明', '系统说明', '管理员', '介绍资料分类、文章检索和常用内容收藏方式。', '已发布', NOW()),
('用户信息维护规范', '运营规范', '运营组', '规范手机号、部门、状态等用户资料字段的维护标准。', '已发布', NOW()),
('周度内容更新计划', '教学安排', '教师助理', '安排本周通知公告、学习资料和系统说明的更新节奏。', '草稿', NOW()),
('响应式布局检查记录', '设计规范', '设计组', '记录桌面端、平板端和手机端页面适配检查情况。', '已发布', NOW()),
('文章分类命名建议', '运营规范', '运营组', '统一文章分类名称，减少重复分类和含义不清的问题。', '草稿', NOW()),
('首页日历模块说明', '系统说明', '管理员', '介绍首页日历、今日待办、数据统计和近期文章模块。', '已发布', NOW()),
('用户状态处理流程', '通知公告', '管理员', '说明正常、待审核和停用状态的使用场景及处理流程。', '已发布', NOW()),
('期末复习资料汇总', '学习资料', '教师助理', '汇总 HTML、CSS、JavaScript、Vue、Spring Boot 和 MyBatis 重点内容。', '已发布', NOW()),
('内容发布前检查清单', '运营规范', '运营组', '发布前检查标题、摘要、分类、作者和状态是否填写完整。', '草稿', NOW()),
('系统演示流程安排', '教学安排', '教师助理', '整理系统登录、用户列表、文章管理和主题切换的演示顺序。', '已发布', NOW()),
('移动端抽屉菜单优化', '设计规范', '设计组', '说明小屏幕下菜单收起、抽屉打开和内容滚动的交互细节。', '草稿', NOW());
