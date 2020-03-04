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
DROP TABLE IF EXISTS sb_user;
CREATE TABLE sb_user(
   uuid INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键编号',
   user_name VARCHAR(50) NOT NULL DEFAULT '' UNIQUE COMMENT '用户账号',
   user_pwd VARCHAR(50) NOT NULL DEFAULT '' COMMENT '用户密码',
   nick_name VARCHAR(50) NOT NULL DEFAULT '' COMMENT '用户昵称',
   user_sex INT COMMENT '用户性别 0-男，1-女',
   email VARCHAR(50) NOT NULL DEFAULT '' COMMENT '用户邮箱',
   user_phone VARCHAR(50) NOT NULL DEFAULT '' COMMENT '用户手机号',
   begin_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) COMMENT '用户表' ENGINE = INNODB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

insert into sb_user(user_name,user_pwd,nick_name,user_sex,email,user_phone) values('admin','0192023a7bbd73250516f069df18b500','隔壁泰山',0,'admin@qq.com','13888888888');
insert into sb_user(user_name,user_pwd,nick_name,user_sex,email,user_phone) values('jiangzh','5e2de6bd1c9b50f6e27d4e55da43b917','阿里郎',0,'jiangzh@qq.com','13866666666');
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
DROP TABLE IF EXISTS sb_count;
CREATE TABLE `sb_count` (
  `uuid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `bus_id` int(11) NOT NULL COMMENT '班车id',
  `begin_time` varchar(50) NOT NULL DEFAULT '' COMMENT '开始时间',
  `end_time` varchar(50) NOT NULL DEFAULT '' COMMENT '结束时间',
  `bus_status` varchar(50) NOT NULL DEFAULT '' COMMENT '0：沙河；1：清水河；2：沙河到清水河；3：清水河到沙河',
  `price` double NOT NULL COMMENT '价格',
  `selected_seats` varchar(50) NOT NULL DEFAULT '' COMMENT '已选座位',
  `seat_status` varchar(50) NOT NULL DEFAULT '' COMMENT '0:未满；1:已满',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='场次表';

insert into sb_count(bus_id,begin_time,end_time,bus_status,price,selected_seats,seat_status) values(1,'08:00','09:00','0',4.00,'', '0');
insert into sb_count(bus_id,begin_time,end_time,bus_status,price,selected_seats,seat_status) values(2,'08:30','09:30','0',4.00,'', '0');
insert into sb_count(bus_id,begin_time,end_time,bus_status,price,selected_seats,seat_status) values(3,'09:00','10:00','0',4.00, '', '0');
insert into sb_count(bus_id,begin_time,end_time,bus_status,price,selected_seats,seat_status) values(4,'09:30','10:30','0',4.00, '', '0');
insert into sb_count(bus_id,begin_time,end_time,bus_status,price,selected_seats,seat_status) values(1,'09:30','10:30','1',4.00, '', '0');
insert into sb_count(bus_id,begin_time,end_time,bus_status,price,selected_seats,seat_status) values(2,'10:00','11:30','1',4.00, '', '0');
insert into sb_count(bus_id,begin_time,end_time,bus_status,price,selected_seats,seat_status) values(5,'08:00','09:00','1',4.00, '', '0');
insert into sb_count(bus_id,begin_time,end_time,bus_status,price,selected_seats,seat_status) values(6,'09:30','10:30','1',4.00, '', '0');
insert into sb_count(bus_id,begin_time,end_time,bus_status,price,selected_seats,seat_status) values(3,'14:00','15:00','1',4.00, '', '0');
insert into sb_count(bus_id,begin_time,end_time,bus_status,price,selected_seats,seat_status) values(4,'15:30','16:30','1',4.00, '', '0');
insert into sb_count(bus_id,begin_time,end_time,bus_status,price,selected_seats,seat_status) values(7,'17:00','18:00','1',4.00, '', '0');
insert into sb_count(bus_id,begin_time,end_time,bus_status,price,selected_seats,seat_status) values(5,'14:00','15:00','0',4.00, '', '0');
insert into sb_count(bus_id,begin_time,end_time,bus_status,price,selected_seats,seat_status) values(6,'15:30','16:30','0',4.00, '', '0');
insert into sb_count(bus_id,begin_time,end_time,bus_status,price,selected_seats,seat_status) values(8,'17:00','18:00','0',4.00, '', '0');
insert into sb_count(bus_id,begin_time,end_time,bus_status,price,selected_seats,seat_status) values(7,'20:00','21:00','0',4.00, '', '0');
insert into sb_count(bus_id,begin_time,end_time,bus_status,price,selected_seats,seat_status) values(8,'20:00','21:00','1',4.00, '', '0');
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
DROP TABLE IF EXISTS sb_order;
CREATE TABLE `sb_order` (
  `uuid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `count_id` int(11) NOT NULL COMMENT '场次id',
  `bus_status` varchar(50) NOT NULL DEFAULT '' COMMENT '0:沙河->清水河；1:清水河->沙河',
  `seats_ids` varchar(50) NOT NULL DEFAULT '' COMMENT '已售座位编号',
  `count_price` double NOT NULL  COMMENT '场次预售价格',
  `order_price` double NOT NULL COMMENT '订单总金额',
  `order_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `order_user` int(11) NOT NULL COMMENT '下单人',
  `order_status` varchar(50) NOT NULL DEFAULT '' COMMENT '0-待支付,1-已支付,2-已关闭',
  `evaluate_status` varchar(50) NOT NULL DEFAULT '' COMMENT '0:未评价；1:已评价',
  `comment` varchar(255) NOT NULL DEFAULT '' COMMENT '评论',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='订单表';

insert into sb_order(count_id,bus_status,seats_ids,count_price,order_price,order_time,order_user,order_status, evaluate_status,comment) values(1,'0','1',4.00,4.00, NOW(),4, '0', '0', '');
insert into sb_order(count_id,bus_status,seats_ids,count_price,order_price,order_time,order_user,order_status, evaluate_status,comment) values(1,'0','1',4.00,4.00, NOW(),4, '1', '1', 'aaa');
insert into sb_order(count_id,bus_status,seats_ids,count_price,order_price,order_time,order_user,order_status, evaluate_status,comment) values(1,'0','1',4.00,4.00, NOW(),4, '2', '0', '');
```

