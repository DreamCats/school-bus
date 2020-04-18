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

## 访问入口

- [前端源码]()
- [网站测试访问](http://ot.dreamcat.ink:8080/) (学校实验室服务器貌停电了，出现故障了，疫情过后，可以测试)只供测试学习使用哈，别搞崩了哇...**提醒：面向移动端**

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

该平台是针对电子科技大学班车预约平台利用最新的技术进行重构，其中主要提供沙河和清水河的班车场次的功能、下单功能、支付功能、退款功能等，并且按照学生需求增加相应的功能，比如未支付订单列表，未支付订单超时自动取消等。

#### 涉及技术

1. Springboot
2. MyBatisPlus（可自定义sql语句）
3. Dubbo（PRC、负载均衡）
4. Mysql
5. Redis(缓存、监听key过期)
6. RocketMQ(最终一致性、幂等性)
7. Swagger2
8. Druid监测
9. Spring定时器
10. SSO单点登录(JWT配合Redis)
11. Sentinel熔断降级限流
12. 分布式唯一ID(雪花算法)

#### 设计技术

1. 采用微服务的架构开发，整个项目分为：用户、班车、订单、支付四个服务。
2. 基于JWT进行SSO单点登录，并且根据携带的Token可以访问系统中其他服务，前期采用ThreadLocal绑定用户，后期采用Redis进行缓存绑定用户。
3. 前期采用Redis的list数据结构缓存班车场次列表的接口，并基于Spring定时器优化班车场次到点更改状态的业务，后期配合阿里巴巴开源的Sentinel中间件进行接口限流。
4. 下单和支付服务采用RocketMQ消息中间件保持数据的最终一致性，并且采用Redis缓存维持RocketMQ消息的幂等性，当然也利用了RocketMQ和Sentinel进行接口限流，采用Redis的监听key键过期事件保证未支付订单超时自动取消的业务。
5. 采用Dubbo提供的负载均衡机制将班车和订单服务分配到不同的服务器上，而提供一系列的接口的网关通过Nginx代理提供的负载均衡也分配到不同的服务器上。
6. 接下里的计划是进行MySql分库分表、以及分布式唯一ID、集群管理等。

#### 项目收获

通过该项目的开发，让我对高并发，大数据量等业务场景有了自己的认识，也知道在什么样的场景下，结合Redis以及RocketMQ等中间件的使用提高服务性能效率，以及面对高并发的场景做怎样的及时应对，在团队期间也让自己对自己的职业道路有了更清晰的规划，对自己的代码有了严格的要求，每次review代码也能发现我的成长。在团队的带领下，让自己有了不同传统企业的代码和业务思想不断收获总结。

测试
