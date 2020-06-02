# SchoolBus

## 引言

> 所涉及的技术包含：
>
> - 后端：
>   - Springboot
>   - MyBatisPlus（可自定义sql语句）
>   - Dubbo（RPC、负载均衡）
>   - Mysql
>   - Redis(监听key过期->发布订阅模式)
>   - RocketMQ(最终一致性、幂等性)
>   - Swagger2
>   - Validated
>   - Druid监测
>   - Spring定时器
>   - 单点登录
>   - Sentinel熔断降级限流
>   - 分布式唯一ID(雪花算法)
>   - 持续补充...
> - 前端：
>   - vue
>   - vuex
>   - vue-router
>   - axios
>   - vant-ui
>   - 持续补充...

## 计划

- [x] 环境搭建的文档（Java、MySQL、Redis、Zoookeeper、Dubbo和RocketMQ）
- [x] Redis绑定用户Token分析文档
- [x] 用户服务所有接口分析文档
- [x] 班车服务所有接口分析文档
- [x] 订单服务所有接口分析文档
- [x] 支付服务所有接口分析文档
- [x] 添加订单、支付和退款的业务结合消息队列
- [x] Redis的Key键到期策略结合订单自动取消业务

## 访问入口

- [前端源码](https://github.com/Ly649423/online-ticket)
- [网站测试访问](http://ot.dreamcat.ink:8080/)只供测试学习使用哈，别搞崩了哇...     **友情提醒：面向移动端**

## 文档

- [环境搭建文档](/doc/环境搭建文档.md)
- [Redis绑定Token分析文档](/doc/Redis绑定Token.md)
- [用户服务所有接口分析文档](/doc/用户服务.md)
- [班车服务所有接口分析文档](/doc/班车服务.md)
- [订单服务所有接口分析文档](/doc/订单服务.md)
- [支付服务所有接口分析文档](/doc/支付服务.md)
- [添加订单、支付和退款的业务结合消息队列](/doc/RocketMQ最终一致性.md)
- [Redis的key过期事件结合自动取消订单业务](/doc/Redis的key过期事件.md)
- [Zookeeper的bug之一](doc/上线遇到的bug.md)

## 架构图

### 架构思考

- [团队开会文档记录](./doc/meeting.md)**记录一些开会讨论的事情以及接下来要做的计划**
- [数据模型建立记录](./doc/sql.md) 逐渐建立数据库模型
- **我也是第一次做这样的项目，没有什么经验，希望大家提供一些建议和经验，我也在这条坎坷的路上不断的去尝试和总结。希望大家多多支持，🙏**
- **注意**:由于学生，没有能力买好一点的服务器，因此采用内网穿透技术将本地服务映射个人的阿里云。
- 根据本校小程序的页面：[pages](https://www.processon.com/view/link/5e4eb17ae4b0e415c2756fd5)，由于疫情期间，下单模块的页面暂时无法分析。日后会补充...
- 根据仅有的页面：**班车预约主页**、**车次列表页面**、**我的车票页面**、**用户登陆注册修改页面**和待定的**订单和下单页面**
- 暂时采用的[架构技术图](https://www.processon.com/view/link/5e4eb5cde4b0a802afb2787c)，后续会改善其中的所采用的技术，并且会针对每一个所涉及的技术的**使用场景**。
- 后端环境搭建，暂时没有采用分布式，数据库忽略，后续补充。[后端搭建](https://www.processon.com/view/link/5e4f7c6ce4b0d4dc87667203)
- 后端部分启动测试，包括Zookeeper、Dubbo、Tomcat、RocketMQ、RocketMQ-Console等[启动图及命令](https://www.processon.com/view/link/5e500788e4b0cc44b5a570eb)
  - [全网最全frp内网穿透(ssh及web)](https://github.com/DreamCats/JavaBooks/blob/master/Tools/frp/全网最全frp内网穿透(ssh及web).md)
  - [centos7安装dubbo环境](https://github.com/DreamCats/JavaBooks/blob/master/Tools/dubbo/centos7安装dubbo环境.md)
  - [centos7安装rocketmq环境及配置](https://github.com/DreamCats/JavaBooks/blob/master/Tools/rocketmq/centos7安装rocketmq及配置.md)
- 为了快速搭建项目考虑采用[guns](https://gitee.com/stylefeng/guns.git)框架进行二次开发。从而将一些中间件集成进去。
- 2.24根据讨论，绝对项目中采用哪些服务.[架构图](https://www.processon.com/view/link/5e52a6e3e4b0c037b5fb1f2f)

#### 技术选型

![技术选型](./imgs/技术选型.png)

#### 架构路线图

![架构路线图](./imgs/总架构路线.png)

## 环境搭建

### 后端环境端口

![端口](./imgs/搭建后端环境.png)

### 环境启动

![环境启动](./imgs/后端环境启动.png)

### guns

**注意：在guns基础进行快速开发，使用guns的版本是：[4.2](https://gitee.com/stylefeng/guns/tree/v4.2/)**

- 我们使用的rest风格，因此上述项目中的admin和generator没有用上。
- 创建数据库，sql在你rest的db文件中
- 直接复制其项目中的rest和core就可以了，修改rest中的数据源，启动即可。
- 访问`http://localhost:8080/auth?userName=admin&password=admin`，即可看到jwt的信息了

### 调试工具

- [postwoman](https://postwoman.io/) 采用这个，没必要下载多余的插件
- [postman](谷歌插件有) 谷歌插件有，翻墙即可

### 生成db模型

- guns-rest的test目录下有生成数据库模型代码，注意输出目录的包名即可
- 安装idea的easycode插件

## 一些文档

- [sql文档](./doc/sql.md)
- [api文档](./doc/api.md)：**最后采用swagger**
- [会议文档](./doc/meeting.md)

## 模块

- 用户模块
- 班车模块
- 订单模块
- 支付模块

### 服务端口

| 服务名称 | 端口 |
| :------: | :--: |
| 用户服务 | 8081 |
| 班车服务 | 8082 |
| 订单服务 | 8083 |
| 支付服务 | 8084 |

### Gateway

| 服务名称 | 端口 |
| :------: | :--: |
| GateWay  | 8087 |

### Dubbo端口

| 服务名称 | 端口  |
| :------: | :---: |
| 用户服务 | 20881 |
| 班车服务 | 20882 |
| 订单服务 | 20883 |
| 支付服务 | 20884 |

### 文档树

```shell
.
├── doc
├── imgs
└── school-bus
    ├── guns-api
    ├── guns-bus
    ├── guns-core
    ├── guns-gateway
    ├── guns-order
    ├── guns-pay
    └── guns-user
```

- **doc**:**记录一些文档，如会议文档等**
- **imgs**:**存放写文档的图片，方便渲染**
- **guns-api**: **存放业务逻辑服务的相关接口，以及各种Dto实体等。**
- **guns-core**: **存放一些工具类，一些公共配置文件以及常量文件等。**
- **guns-bus**: **存放班车和场次相关的model，mapper，service等文件。**
- **guns-order**: **存放订单相关的model，mapper，service等文件。**
- **guns-pay**: **存放支付相关的model，mapper，service等文件。**
- **guns-user**: **存放用户相关的model，mapper，service等文件。**

### 前端页面

#### 用户服务页面

**太模糊了-->[高清](https://www.processon.com/view/link/5e78b8e1e4b027d999c5a1b7)**

![前端用户服务页面](./imgs/前端用户服务页面.png)

#### 班车服务页面

**太模糊了-->[高清](https://www.processon.com/view/link/5e78c51be4b011fcceaacdfa)**

![前端班车服务](./imgs/前端班车服务.png)

#### 下单服务页面

**太模糊了-->[高清](https://www.processon.com/view/link/5e78c611e4b015182036f13c)**

![下单服务页面](./imgs/下单服务页面.png)

#### 支付服务页面

**太模糊了-->[高清](https://www.processon.com/view/link/5e78c6f8e4b06b852ff1959d)**

![前端支付页面](./imgs/前端支付页面.png)

#### 我的订单页面

**太模糊了-->[高清](https://www.processon.com/view/link/5e78c7aee4b011fcceaad3f1)**

![前端我的订单](./imgs/前端我的订单.png)

## 简历

### 微服务在线班车预约平台

#### 描述

该平台是针对电子科技大学班车预约平台利用最新的技术进行重构，其中主要提供沙河和清水河的班 车场次的功能、下单功能、支付功能、退款功能等，并且按照学生需求增加相应的功能，比如未支付 订单列表，未支付订单超时自动取消等。

#### 涉及技术

Java、Springboot、MyBatis、Redis、MySQL、Dubbo、RocketMQ 等。

#### 设计技术

1. 采用 Dubbo 的架构开发，整个项目分为用户、班车、订单、支付四个服务，达到易维护的效果。
2. 基于 JWT 的 SSO 单点登录，并依携带的 Token 可以访问系统中其他服务，采用 Redis 缓存绑 定用户，达到用户登录一次处处能访问各个系统。
3. 采用 Redis 的 list 数据结构缓存班车场次列表，并基于 Spring 定时器优化班车场次到点更新班车 状态的业务，最后配合阿里巴巴开源的 Sentinel 中间件进行接口限流达到高并发、高可用的效果。
4. 下单和支付服务均采用基于阿里巴巴开源的 RocketMQ 消息中间件保持数据的最终一致性，并且 采用 Redis 缓存维持 RocketMQ 消息的幂等性，接着采用 RocketMQ 和 Sentinel 进行接口限流维 护系统的稳定性，最后采用 Redis 的监听 key 键过期事件保证未支付订单超时自动取消业务，达到高 并发、高可用的效果。
5. 分别采用 Dubbo 和 Nginx 提供的负载均衡机制将班车服务、订单服务、网关分配到不同的服务 器上，达到了高性能的效果。
6. 接下来的计划是 MySQL 读写分离、Redis 读写分离、以及分布式唯一 ID、集群管理等。
