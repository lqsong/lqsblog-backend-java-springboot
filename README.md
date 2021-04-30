# lqsblog-backend-java-springboot

 它（[Github](https://github.com/lqsong/lqsblog-backend-java-springboot) 、 [Gitee](https://gitee.com/lqsong/lqsblog-backend-java-springboot)）是一个基于微服务思想的API后端服务，它基于 [spring-boot](https://spring.io/projects/spring-boot)实现（maven + springboot + mybatis + mybatis plus + shiro + jwt（+ 自动刷新） + restful）。


## 开发文档

- [lqsBlog官方文档](http://docs.liqingsong.cc/guide/backendservice/java-springboot.html)。

- [ADMIN DEMO](http://lqsblog-demo.admin-element-vue.liqingsong.cc/)

- [PC DEMO](http://liqingsong.cc/)

## 功能

```sh
# 一、api 模块 (admin、pc)
- 登录 / 注销 (shiro + jwt 验证，自动刷新jwt)
- 首页 / 统计
- 随笔
- 作品
- 专题
- 左邻右舍
- 设置
  - 关于我
  - 标签
  - 账号
  - 角色
  - 后台菜单
  - 后台API
  - 站点配置
# 二、webs 模块,自己自定义扩展
```

## 技术选型

- 核心框架：Sring boot + Spring Framework
- 安全框架：Apache Shiro
- 持久层框架： MyBatis + MyBatis-Plus
- 会话管理: JWT
- 日志管理：SLF4J
- JSON转换: Jackson FastJson
- JAVA库：Lombok
- 工具：Maven
- api风格：restful
- 模板引擎：thymeleaf



## 目录结构

本项目已经为你生成了一个完整的开发框架，下面是整个项目的目录结构。

```bash
├── lqsblog-api                            # api服务
│   │── cc.liqingsong.api.admin            # admin后台api接口
│   └── cc.liqingsong.api.pc               # pc前台api接口
├── lqsblog-api-common                     # api服务公共
├── lqsblog-common                         # 项目公共
├── lqsblog-database                       # 项目数据库(实体类、DTO、VO)
├── lqsblog-service                        # 项目服务类
├── lqsblog-webs                           # jsp Web PC前台
├── lqsblog-webs-common                    # jsp Web 公共
└── pom.xml                                # maven 配置文件
```

## 基础环境

- JDK11 +
- MySQL5.7 +
- maven3.6 +
- Idea 安装 lombok 设置动态编译


## 捐赠

如果你觉得这个项目帮助到了你，你可以请作者喝咖啡表示鼓励.

**ALIPAY**             |  **WECHAT**
:-------------------------:|:-------------------------:
![Alipay](http://uploads.liqingsong.cc/20210430/f62d2436-8d92-407d-977f-35f1e4b891fc.png)  |  ![Wechat](http://uploads.liqingsong.cc/20210430/3e24efa9-8e79-4606-9bd9-8215ce1235ac.png)


