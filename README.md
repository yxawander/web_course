# Vue + Spring Boot + MyBatis Web 课程实验项目

本项目用于完成 Web 开发课程实验一、实验二、实验三。系统采用前后端分离结构，前端使用 Vue 3 + Element Plus，后端使用 Spring Boot + MyBatis + MySQL。

## 项目功能

- 登录：用户名和密码校验，成功后进入系统首页。
- 注册：校验用户名、密码、电子邮箱和出生日期。
- 修改密码：校验用户名、旧密码、新密码和确认密码。
- 首页日历：展示日历、今日待办、近期文章和数据统计。
- 用户管理：从数据库读取一般用户信息，使用 Element Plus 表格和分页展示。
- 文章管理：支持文章查询、新增、编辑、删除和发布状态维护。
- 主题切换：支持蓝色风格和暖黄色风格。
- 响应式布局：电脑端显示侧边菜单，手机端使用抽屉菜单。

## 实验对应关系

### 实验一：HTML 静态网页设计

前端实现登录、注册、修改密码、主页、用户列表和文章管理页面。页面使用 Vue 3 与 Element Plus 组件完成，包含表单校验、响应式布局、主题切换和按钮跳转。

对应目录：

```text
springboot-vue-lab/frontend
```

### 实验二：基于 Spring Boot 的 Web 系统设计

后端通过 Controller 接收前端请求，并调用 Service 完成业务逻辑。实现注册、登录、修改密码、列表展示和文章管理接口。

核心分层：

```text
controller  接收请求
service     处理业务
mapper      数据访问接口
entity      实体对象
dto         请求对象
```

对应目录：

```text
springboot-vue-lab/backend/src/main/java/com/example/backend
```

### 实验三：基于 Spring Boot + MyBatis 的 Web 系统设计

系统使用 MyBatis 连接 MySQL 数据库。数据库中包含实验要求的两个核心表：

- `login_user`：登录用户表，用于注册、登录、修改密码。
- `user_info`：一般用户信息表，用于用户列表展示。

另外增加：

- `article`：文章表，用于文章管理模块。

## 项目结构

```text
springboot-vue-lab/
  backend/
    src/main/java/com/example/backend/
      common/
      controller/
      dto/
      entity/
      mapper/
      service/
    src/main/resources/
      mapper/
      sql/init.sql
      application.properties
    pom.xml
  frontend/
    src/
      api/
      assets/
      layouts/
      router/
      views/
    package.json
```

## 环境要求

- JDK 17 或以上
- Maven 3.8 或以上
- Node.js 20 或以上
- MySQL 8 或以上
- VS Code

## 数据库初始化

1. 打开 MySQL 或 VS Code 数据库插件。
2. 执行初始化 SQL 文件：

```text
springboot-vue-lab/backend/src/main/resources/sql/init.sql
```

3. 确认 `application.properties` 中数据库配置正确：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/web?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=你的数据库密码
```

如果本机数据库密码不同，需要修改 `spring.datasource.password`。

## 启动后端

进入后端目录：

```bash
cd springboot-vue-lab/backend
mvn spring-boot:run
```

启动成功后，后端地址为：

```text
http://localhost:8080
```

## 启动前端

进入前端目录：

```bash
cd springboot-vue-lab/frontend
npm install
npm run dev
```

启动成功后，前端地址通常为：

```text
http://localhost:5173
```

前端已配置 Vite 代理，`/api` 请求会转发到 `http://localhost:8080`。

## 演示账号

```text
用户名：admin
密码：123456
```

备用账号：

```text
用户名：student
密码：123456
```

## 主要接口

```text
POST /api/auth/login       登录
POST /api/auth/register    注册
POST /api/auth/password    修改密码，需校验旧密码
GET  /api/dashboard/summary 首页统计
GET  /api/users            用户列表分页
GET  /api/articles         文章列表分页
POST /api/articles         新增文章
PUT  /api/articles/{id}    编辑文章
DELETE /api/articles/{id}  删除文章
```

## 验收展示建议

1. 先展示登录页在电脑端和手机端的响应式效果。
2. 展示注册页的邮箱和出生日期校验。
3. 展示修改密码页的新密码一致性校验。
4. 登录进入首页，展示日历、今日待办、统计卡片和近期文章。
5. 切换蓝色风格和暖黄色风格。
6. 打开用户管理，展示数据库用户列表和分页。
7. 打开文章管理，演示新增、编辑、删除文章。
8. 打开后端代码说明 Controller 调用 Service，Service 调用 Mapper。
9. 打开 `init.sql` 说明 `login_user` 和 `user_info` 两张实验要求表。

## 说明

本项目为了课程验收采用明文密码，便于演示注册、登录和修改密码流程。真实项目中应使用 BCrypt 等方式对密码加密存储。
