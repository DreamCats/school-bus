# SchoolBus

## 引言

> 所涉及的技术包含：
>
> - 后端：
>   - Springboot
>   - MyBatis
>   - Dubbo
>   - Mysql
>   - Redis
>   - RocketMQ
>   - 持续补充...
> - 前端：
>   - vue
>   - vuex
>   - vue-router
>   - axios
>   - element-ui
>   - 持续补充...


## 架构图

### 架构思考

- [团队开会文档记录](./doc/meeting.md)**记录一些开会讨论的事情以及接下来要做的计划**
- [数据模型建立记录](./doc/sql.md) 逐渐建立数据库模型

- **我也是第一次做这样的项目，没有什么经验，希望大家提供一些建议和经验，我也在这条坎坷的路上不断的去尝试和总结。希望大家多多支持，🙏**
- **注意**:由于学生，没有能力买好一点的服务器，因此采用内网穿透技术将本地服务映射个人的阿里云。
- 根据本校小程序的页面：[pages](https://www.processon.com/view/link/5e4eb17ae4b0e415c2756fd5)，由于疫情期间，下单模块的页面暂时无法分析。日后会补充...
- 根绝仅有的页面：**班车预约主页**、**车次列表页面**、**我的车票页面**、**用户登陆注册修改页面**和待定的**订单和下单页面**
- 暂时采用的[架构技术图](https://www.processon.com/view/link/5e4eb5cde4b0a802afb2787c)，后续会改善其中的所采用的技术，并且会针对每一个所涉及的技术的**使用场景**。
- 后端环境搭建，暂时没有采用分布式，数据库忽略，后续补充。[后端搭建](https://www.processon.com/view/link/5e4f7c6ce4b0d4dc87667203)
- 后端部分启动测试，包括Zookeeper、Dubbo、Tomcat、RocketMQ、RocketMQ-Console等[启动图及命令](https://www.processon.com/view/link/5e500788e4b0cc44b5a570eb)
  - [全网最全frp内网穿透(ssh及web)](https://github.com/DreamCats/JavaBooks/blob/master/Tools/frp/全网最全frp内网穿透(ssh及web).md)
  - [centos7安装dubbo环境](https://github.com/DreamCats/JavaBooks/blob/master/Tools/dubbo/centos7安装dubbo环境.md)
  - [centos7安装rocketmq环境及配置](https://github.com/DreamCats/JavaBooks/blob/master/Tools/rocketmq/centos7安装rocketmq及配置.md)

- 为了快速搭建项目考虑采用[guns](https://gitee.com/stylefeng/guns.git)框架进行二次开发。从而将一些中间件集成进去。
- 2.24根据讨论，绝对项目中采用哪些服务.[架构图](https://www.processon.com/view/link/5e52a6e3e4b0c037b5fb1f2f)



## 参考项目

- [springboot-seckill](https://github.com/zaiyunduan123/springboot-seckill) 基于SpringBoot + MySQL + Redis + RabbitMQ + Guava开发的高并发商品限时秒杀系统
- [MeetingFilm](https://github.com/daydreamdev/MeetingFilm) 基于微服务架构的在线电影购票平台 
- [dbblog](https://github.com/llldddbbb/dbblog) 基于SpringBoot2.x+Vue2.x+ElementUI+Iview+Elasticsearch+RabbitMQ+Redis+Shiro的多模块前后端分离的博客项目
- [mall](<https://github.com/macrozheng/mall>) mall项目是一套电商系统，包括前台商城系统及后台管理系统，基于SpringBoot+MyBatis实现
- [mall-swarm](https://github.com/macrozheng/mall-swarm)mall-swarm是一套微服务商城系统，采用了 Spring Cloud Greenwich、Spring Boot 2、MyBatis、Docker、Elasticsearch等核心技术，同时提供了基于Vue的管理后台方便快速搭建系统

