## 模型建立

> 声明：
>
> - sb代表SchoolBus
> - t代表table
> - 表中所有的uuid， 随着自增长，暂时不考虑分布式id

## 用户服务

### sb_user

|    表名     |    类型     |        备注         |
| :---------: | :---------: | :-----------------: |
|    UUID     |     INT     |      主键编号       |
|  usen_name  | VARCHAR(50) |      用户账号       |
|  user_pwd   | VARCHAR(50) |      用户密码       |
|  nick_name  | VARCHAR(50) |      用户昵称       |
|  user_sex   |     INT     | 用户性别 0-男，1-女 |
|    email    | VARCHAR(50) |      用户邮箱       |
| user_phone  | VARCHAR(11) |     用户手机号      |
| begin_time  |  TIMESTAMP  |      创建时间       |
| update_time |  TIMESTAMP  |      更新时间       |

```sql

DROP TABLE IF EXISTS `sb_user`;
CREATE TABLE `sb_user` (
  `uuid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户账号',
  `user_pwd` varchar(50) NOT NULL DEFAULT '' COMMENT '用户密码',
  `nick_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户昵称',
  `user_sex` int(11) DEFAULT NULL COMMENT '用户性别 0-男，1-女',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `user_phone` varchar(50) NOT NULL DEFAULT '' COMMENT '用户手机号',
  `begin_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `money` double NOT NULL COMMENT '用户余额',
  `pay_password` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '支付密码',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

INSERT INTO `sb_user` (`uuid`, `user_name`, `user_pwd`, `nick_name`, `user_sex`, `email`, `user_phone`, `begin_time`, `update_time`, `money`, `pay_password`) VALUES ('2', 'admin', '0192023a7bbd73250516f069df18b500', '隔壁泰山', '0', 'admin@qq.com', '13888888888', '2020-02-25 20:35:51', '2020-02-25 20:35:51', '0', ''),
('3', 'jiangzh', '5e2de6bd1c9b50f6e27d4e55da43b917', '阿里郎', '0', 'jiangzh@qq.com', '13866666666', '2020-02-25 20:35:51', '2020-02-25 20:35:51', '0', ''),
('4', 'mai', '202cb962ac59075b964b07152d234b70', '你是风儿，他是草', '0', '1595947@qq.com', '18438747362', '2020-02-26 16:10:39', '2020-03-20 00:53:34', '199900', '123456'),
('18', 'feng', '202cb962ac59075b964b07152d234b70', '买', '1', '123@qq.com', '123', '2020-02-28 19:58:45', '2020-03-15 16:00:52', '2', '123456');
```

## 班车服务

### sb_bus

|     表名     |   类型    |          备注          |
| :----------: | :-------: | :--------------------: |
|     uuid     |    int    | 车牌包，用id吧，不麻烦 |
|              |           |                        |
| limit_number |  VARCHAR  |        限制人数        |
| driver_name  |  VARCHAR  |        司机姓名        |
| drive_phone  |  VARCHAR  |        司机电话        |
|              |           |                        |
| seats_number |  VARCHAR  |   座位排列：json文件   |
|  begin_time  | TIMESTAMP |        创建时间        |
| update_time  | TIMESTAMP |        更新时间        |

```sql
DROP TABLE IF EXISTS sb_bus;
CREATE TABLE sb_bus(
   uuid INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键编号',
   limit_number VARCHAR(10) NOT NULL DEFAULT '' COMMENT '限制人数',
   driver_name VARCHAR(50) NOT NULL DEFAULT '' COMMENT '司机姓名',
   drive_phone VARCHAR(50) NOT NULL DEFAULT '' COMMENT '司机电话',
   seats_number VARCHAR(50) NOT NULL DEFAULT '' COMMENT '座位排列：json文件',
   `begin_time` datetime NOT NULL COMMENT '创建时间',
   `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) COMMENT '班车表' ENGINE = INNODB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

insert into sb_bus(limit_number,driver_name,drive_phone,seats_number, begin_time) values('20','买','135','/file/seat.json', NOW());
insert into sb_bus(limit_number,driver_name,drive_phone,seats_number, begin_time) values('20','赵','135','/file/seat.json', NOW());
insert into sb_bus(limit_number,driver_name,drive_phone,seats_number, begin_time) values('20','李','135','/file/seat.json', NOW());
insert into sb_bus(limit_number,driver_name,drive_phone,seats_number, begin_time) values('20','王','135','/file/seat.json', NOW());
insert into sb_bus(limit_number,driver_name,drive_phone,seats_number, begin_time) values('20','牛','135','/file/seat.json', NOW());
insert into sb_bus(limit_number,driver_name,drive_phone,seats_number, begin_time) values('20','马','135','/file/seat.json', NOW());
insert into sb_bus(limit_number,driver_name,drive_phone,seats_number, begin_time) values('20','狗','135','/file/seat.json', NOW());
insert into sb_bus(limit_number,driver_name,drive_phone,seats_number, begin_time) values('20','王','135','/file/seat.json', NOW());
```

### sb_count

|      字段      |  类型   |                         备注                         |
| :------------: | :-----: | :--------------------------------------------------: |
|      uuid      |   int   |                        场次id                        |
|     bus_id     |   INT   |                        班车id                        |
|   begin_time   | VARCHAR |                       开始时间                       |
|    end_time    | VARCHAR |                       结束时间                       |
|   bus_status   | VARCHAR | 0：沙河；1：清水河；2：沙河到清水河；3：清水河到沙河 |
|     price      | DOUBLE  |                         票价                         |
| selected_seats | VARCHAR |                       已选座位                       |
|  seat_status   | VARCHAR |                    0:未满；1:已满                    |

```sql


CREATE TABLE `sb_count` (
  `uuid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `bus_id` int(11) NOT NULL COMMENT '班车id',
  `begin_time` varchar(50) NOT NULL DEFAULT '' COMMENT '开始时间',
  `end_time` varchar(50) NOT NULL DEFAULT '' COMMENT '结束时间',
  `bus_status` varchar(50) NOT NULL DEFAULT '' COMMENT '0：沙河；1：清水河；2：沙河到清水河；3：清水河到沙河',
  `price` double NOT NULL COMMENT '价格',
  `selected_seats` varchar(50) NOT NULL DEFAULT '' COMMENT '已选座位',
  `seat_status` varchar(50) NOT NULL DEFAULT '' COMMENT '0:未满；1:已满',
  `begin_date` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT 'mm-dd格式',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='场次表';

INSERT INTO `sb_count` (`uuid`, `bus_id`, `begin_time`, `end_time`, `bus_status`, `price`, `selected_seats`, `seat_status`, `begin_date`) VALUES ('1', '1', '08:00', '09:00', '1', '4', '', '0', '2020-03-09'),
('2', '2', '08:30', '09:30', '1', '4', '', '0', '2020-03-16'),
('3', '3', '09:00', '10:00', '1', '4', '', '0', '2020-03-09'),
('4', '4', '09:30', '10:30', '1', '4', '', '0', '2020-03-09'),
('5', '1', '09:30', '10:30', '0', '4', '', '0', '2020-03-09'),
('6', '2', '10:00', '11:00', '0', '4', '', '0', '2020-03-09'),
('7', '5', '08:00', '09:00', '0', '4', '', '0', '2020-03-09'),
('8', '6', '09:30', '10:30', '0', '4', '', '0', '2020-03-09'),
('9', '3', '14:00', '15:00', '0', '4', '', '0', '2020-03-09'),
('10', '4', '15:30', '16:30', '0', '4', '', '0', '2020-03-09'),
('11', '7', '17:00', '18:00', '0', '4', '', '0', '2020-03-09'),
('12', '5', '14:00', '15:00', '1', '4', '', '0', '2020-03-09'),
('13', '6', '15:30', '16:30', '1', '4', '', '0', '2020-03-09'),
('14', '8', '17:00', '18:00', '1', '4', '', '0', '2020-03-09'),
('15', '7', '20:00', '21:00', '1', '4', '', '0', '2020-03-09'),
('16', '8', '20:00', '21:00', '0', '4', '', '0', '2020-03-09'),
('17', '8', '24:00', '24:30', '0', '4', '', '0', '2020-03-09'),
('86', '1', '08:00', '09:00', '1', '4', '', '0', '2020-03-20'),
('87', '2', '08:30', '09:30', '1', '4', '', '0', '2020-03-20'),
('88', '3', '09:00', '10:00', '1', '4', '', '0', '2020-03-20'),
('89', '4', '09:30', '10:30', '1', '4', '', '0', '2020-03-20'),
('90', '1', '09:30', '10:30', '0', '4', '', '0', '2020-03-20'),
('91', '2', '10:00', '11:00', '0', '4', '', '0', '2020-03-20'),
('92', '5', '08:00', '09:00', '0', '4', '', '0', '2020-03-20'),
('93', '6', '09:30', '10:30', '0', '4', '', '0', '2020-03-20'),
('94', '3', '14:00', '15:00', '0', '4', '', '0', '2020-03-20'),
('95', '4', '15:30', '16:30', '0', '4', '', '0', '2020-03-20'),
('96', '7', '17:00', '18:00', '0', '4', '', '0', '2020-03-20'),
('97', '5', '14:00', '15:00', '1', '4', '', '0', '2020-03-20'),
('98', '6', '15:30', '16:30', '1', '4', '', '0', '2020-03-20'),
('99', '8', '17:00', '18:00', '1', '4', '', '0', '2020-03-20'),
('100', '7', '20:00', '21:00', '1', '4', '', '0', '2020-03-20'),
('101', '8', '20:00', '21:00', '0', '4', '', '0', '2020-03-20'),
('102', '8', '24:00', '24:30', '0', '4', '', '0', '2020-03-20');

```

## 订单服务

### sb_order

|      字段       |   类型    |              备注              |
| :-------------: | :-------: | :----------------------------: |
|      uuid       |    INT    |             订单id             |
|    count_id     |    INT    |             场次id             |
|   bus_status    |  VARCHAR  | 0:沙河->清水河；1:清水河->沙河 |
|    seats_ids    |  VARCHAR  |          已售座位编号          |
|   count_price   |  DOUBLE   |          场次预售价格          |
|   order_price   |  DOUBLE   |           订单总金额           |
|   order_time    | TIMESTAMP |            下单时间            |
|   order_user    |    INT    |             下单人             |
|  order_status   |  VARCHAR  |   0-待支付,1-已支付,2-已关闭   |
| evaluate_status |  VARCHAR  |       0:未评价；1:已评价       |
|     comment     |  VARCHAR  |              评论              |

```sql


CREATE TABLE `sb_order` (
  `uuid` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '主键编号',
  `count_id` int(11) NOT NULL COMMENT '场次id',
  `bus_status` varchar(50) NOT NULL DEFAULT '' COMMENT '0:沙河->清水河；1:清水河->沙河',
  `seats_ids` varchar(50) NOT NULL DEFAULT '' COMMENT '已售座位编号',
  `count_price` double NOT NULL COMMENT '场次预售价格',
  `order_price` double NOT NULL COMMENT '订单总金额',
  `order_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_id` int(11) NOT NULL COMMENT '下单人id',
  `order_status` varchar(50) NOT NULL DEFAULT '' COMMENT '0-待支付,1-已支付,2-已关闭',
  `evaluate_status` varchar(50) NOT NULL DEFAULT '' COMMENT '0:未评价；1:已评价',
  `comment` varchar(255) NOT NULL DEFAULT '' COMMENT '评论',
  `order_user` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '下单人',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='订单表';

INSERT INTO `sb_order` (`uuid`, `count_id`, `bus_status`, `seats_ids`, `count_price`, `order_price`, `order_time`, `user_id`, `order_status`, `evaluate_status`, `comment`, `order_user`) VALUES ('19f649e', '80', '0', '2', '4', '4', '2020-03-19 09:41:24', '4', '1', '0', '', '4'),
('2955f1f', '83', '0', '2', '4', '4', '2020-03-19 18:19:44', '4', '2', '0', '', '4'),
('3307d98', '80', '0', '1', '4', '4', '2020-03-19 13:38:50', '4', '2', '0', '', '4'),
('435bcc2', '83', '0', '10', '4', '4', '2020-03-19 18:14:11', '4', '1', '0', '', '4'),
('683cf38', '68', '0', '15', '4', '4', '2020-03-18 16:17:33', '4', '1', '0', '', '4'),
('7805fa9', '83', '0', '6', '4', '4', '2020-03-19 18:21:25', '4', '2', '0', '', '4'),
('8b99107', '69', '0', '1', '4', '4', '2020-03-19 00:25:38', '4', '1', '0', '', '4'),
('8feca50', '65', '0', '4', '4', '4', '2020-03-18 16:00:55', '4', '1', '0', '', '4'),
('b7a3232', '65', '0', '8', '4', '4', '2020-03-18 16:11:54', '4', '1', '0', '', '4'),
('d3f3f93', '65', '0', '9', '4', '4', '2020-03-18 16:17:11', '4', '2', '0', '', '4'),
('f1a2e3e', '65', '0', '20', '4', '4', '2020-03-18 15:53:34', '4', '1', '0', '', '4');

```

