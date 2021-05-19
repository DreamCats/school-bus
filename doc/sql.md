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
|    UUID     |   BIGINT    |      主键编号       |
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
  `uuid` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户账号',
  `user_pwd` varchar(255) NOT NULL DEFAULT '' COMMENT '用户密码',
  `nick_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户昵称',
  `user_sex` int(11) DEFAULT NULL COMMENT '用户性别 0-男，1-女',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `user_phone` varchar(50) NOT NULL DEFAULT '' COMMENT '用户手机号',
  `begin_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `money` double NOT NULL COMMENT '用户余额',
  `pay_password` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '支付密码',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`) USING BTREE,
  UNIQUE KEY `user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1364578748174774275 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';

INSERT INTO `sb_user` (`uuid`, `user_name`, `user_pwd`, `nick_name`, `user_sex`, `email`, `user_phone`, `begin_time`, `update_time`, `money`, `pay_password`, `created_at`, `updated_at`, `deleted_at`) VALUES
('2', 'admin', '0192023a7bbd73250516f069df18b500', '隔壁泰山', '0', 'admin@qq.com', '13888888888', '2020-02-25 20:35:51', '2020-02-26 09:35:51', '0', '', NULL, NULL, NULL),
('3', 'jiangzh', '5e2de6bd1c9b50f6e27d4e55da43b917', '阿里郎', '0', 'jiangzh@qq.com', '13866666666', '2020-02-25 20:35:51', '2020-02-26 09:35:51', '0', '', NULL, NULL, NULL),
('4', 'mai', '202cb962ac59075b964b07152d234b70', '112', '1', '1595947@qq.com', '18438747368', '2020-02-26 16:10:39', '2021-05-14 03:42:57', '1e102', 'fgggghdsesgdhhhhhhhhhhhhhrsewhhhhhhhhhhhhhht', NULL, NULL, NULL),
('18', 'feng', '202cb962ac59075b964b07152d234b70', 'kun1', '1', '123@qq.com', '123', '2020-02-28 19:58:45', '2021-04-04 16:32:01', '9976.5', '123456', NULL, NULL, NULL),
('827270988935725056', 'gotest', '$2a$10$3V7RTfHD9O1fhFRKFJ1Dcu2U1qlnYowWFxCxE.xnqiHbF/7mx8s.y', '', '0', 'abcd@123.com', '1000', '0000-00-00 00:00:00', '2021-04-01 19:59:34', '0', '', '2021-04-01 19:59:34', '2021-04-01 19:59:34', NULL),
('827274900958871552', 'gotest1', '$2a$10$vH6PI3HpleXkfPlq0rcvJO8GW61AVnZjYLH5JBFxx0ebUMckzHuYe', '', '0', 'abcd@123.com', '1000', '0000-00-00 00:00:00', '2021-04-01 20:15:06', '0', '', '2021-04-01 20:15:06', '2021-04-01 20:15:06', NULL),
('1323530467558817794', 'laxiba3', '202cb962ac59075b964b07152d234b70', '', NULL, 'mai@qq.com', '1000', '2020-11-03 07:40:28', '2020-11-03 15:40:29', '0', '123456', NULL, NULL, NULL),
('1329205416181211138', 'haha', '202cb962ac59075b964b07152d234b70', '', NULL, '123@qq.com', '123', '2020-11-18 23:30:41', '2020-11-19 07:30:41', '0', '', NULL, NULL, NULL),
('1341282821389688834', 'demoData', 'ee03a63755efe1c128765c7b36ac3124', '', NULL, 'demoData', 'demoData', '2020-12-22 07:21:59', '2020-12-22 15:21:58', '0', '123456', NULL, NULL, NULL),
('1348884127237865474', '88', '7fa8282ad93047a4d6fe6111c93b308a', '', NULL, 'cyofgh90841@chacuo.net', '18370098077', '2021-01-12 06:46:52', '2021-01-12 14:46:52', '0', '', NULL, NULL, NULL),
('1364578748174774273', '222', 'f51ada2ca0320348d5c1471269c173e8', '', NULL, '194712232', '18370098079', '2021-02-24 14:11:40', '2021-02-24 22:11:41', '0', '123456', NULL, NULL, NULL);

```

## 班车服务

### sb_bus

|     表名     |   类型    |          备注          |
| :----------: | :-------: | :--------------------: |
|     uuid     |  BIGINT   | 车牌包，用id吧，不麻烦 |
| limit_number |  VARCHAR  |        限制人数        |
| driver_name  |  VARCHAR  |        司机姓名        |
| drive_phone  |  VARCHAR  |        司机电话        |
| seats_number |  VARCHAR  |   座位排列：json文件   |
|  begin_time  | TIMESTAMP |        创建时间        |
| update_time  | TIMESTAMP |        更新时间        |

```sql
DROP TABLE IF EXISTS `sb_bus`;
CREATE TABLE `sb_bus` (
  `uuid` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `limit_number` varchar(10) NOT NULL DEFAULT '' COMMENT '限制人数',
  `driver_name` varchar(50) NOT NULL DEFAULT '' COMMENT '司机姓名',
  `driver_phone` varchar(50) NOT NULL DEFAULT '' COMMENT '司机电话',
  `seats_number` varchar(50) NOT NULL DEFAULT '' COMMENT '座位排列：json文件',
  `begin_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=828017250127577089 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='班车表';

INSERT INTO `sb_bus` (`uuid`, `limit_number`, `driver_name`, `driver_phone`, `seats_number`, `begin_time`, `update_time`, `updated_at`, `deleted_at`, `created_at`) VALUES
('1', '20', '买', '135', '/file/seat.json', '2020-10-09 07:26:23', '2020-10-09 19:26:23', NULL, NULL, NULL),
('2', '20', '赵', '135', '/file/seat.json', '2020-10-09 07:26:23', '2020-10-09 19:26:23', NULL, NULL, NULL),
('3', '20', '李', '135', '/file/seat.json', '2020-10-09 07:26:23', '2020-10-09 19:26:23', NULL, NULL, NULL),
('4', '20', '王', '135', '/file/seat.json', '2020-10-09 07:26:23', '2020-10-09 19:26:23', NULL, NULL, NULL),
('5', '20', '牛', '135', '/file/seat.json', '2020-10-09 07:26:23', '2020-10-09 19:26:23', NULL, NULL, NULL),
('6', '20', '马', '135', '/file/seat.json', '2020-10-09 07:26:23', '2020-10-09 19:26:23', NULL, NULL, NULL),
('7', '20', '狗', '135', '/file/seat.json', '2020-10-09 07:26:23', '2020-10-09 19:26:23', NULL, NULL, NULL),
('8', '20', '王', '135', '/file/seat.json', '2020-10-09 07:26:23', '2020-10-09 19:26:23', NULL, NULL, NULL),
('827674640758341632', '40', '老司机', '123', '/file.json', '0000-00-00 00:00:00', '2021-04-02 22:43:32', '2021-04-02 22:43:32', NULL, '2021-04-02 22:43:32'),
('828017250127577088', '', '', '', '', '0000-00-00 00:00:00', '2021-04-03 21:24:56', '2021-04-03 21:24:56', NULL, '2021-04-03 21:24:56');
```

### sb_count

|      字段      |  类型   |                         备注                         |
| :------------: | :-----: | :--------------------------------------------------: |
|      uuid      | BIGINT  |                        场次id                        |
|     bus_id     | BIGINT  |                        班车id                        |
|   begin_time   | VARCHAR |                       开始时间                       |
|    end_time    | VARCHAR |                       结束时间                       |
|   bus_status   | VARCHAR | 0：沙河；1：清水河；2：沙河到清水河；3：清水河到沙河 |
|     price      | DOUBLE  |                         票价                         |
| selected_seats | VARCHAR |                       已选座位                       |
|  seat_status   | VARCHAR |                    0:未满；1:已满                    |

```sql


DROP TABLE IF EXISTS `sb_count`;
CREATE TABLE `sb_count` (
  `uuid` bigint(50) NOT NULL COMMENT '主键编号',
  `bus_id` bigint(11) NOT NULL COMMENT '班车id',
  `begin_time` varchar(50) NOT NULL DEFAULT '' COMMENT '开始时间',
  `end_time` varchar(50) NOT NULL DEFAULT '' COMMENT '结束时间',
  `bus_status` varchar(50) NOT NULL DEFAULT '' COMMENT '0：沙河；1：清水河；2：沙河到清水河；3：清水河到沙河',
  `price` double NOT NULL COMMENT '价格',
  `selected_seats` varchar(50) NOT NULL DEFAULT '' COMMENT '已选座位',
  `seat_status` varchar(50) NOT NULL DEFAULT '' COMMENT '0:未满；1:已满',
  `begin_date` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT 'mm-dd格式',
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`) USING BTREE,
  UNIQUE KEY `uuid` (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='场次表';


INSERT INTO `sb_count` (`uuid`, `bus_id`, `begin_time`, `end_time`, `bus_status`, `price`, `selected_seats`, `seat_status`, `begin_date`, `updated_at`, `deleted_at`, `created_at`) VALUES
('1', '1', '08:00', '09:00', '1', '4', '', '0', '2020-03-09', NULL, NULL, NULL),
('2', '2', '08:30', '09:30', '1', '4', '', '0', '2020-03-16', NULL, NULL, NULL),
('3', '3', '09:00', '10:00', '1', '4', '', '0', '2020-03-09', NULL, NULL, NULL),
('4', '4', '09:30', '10:30', '1', '4', '', '0', '2020-03-09', NULL, NULL, NULL),
('5', '1', '09:30', '10:30', '0', '4', '', '0', '2020-03-09', NULL, NULL, NULL),
('6', '2', '10:00', '11:00', '0', '4', '', '0', '2020-03-09', NULL, NULL, NULL),
('7', '5', '08:00', '09:00', '0', '4', '', '0', '2020-03-09', NULL, NULL, NULL),
('8', '6', '09:30', '10:30', '0', '4', '', '0', '2020-03-09', NULL, NULL, NULL),
('9', '3', '14:00', '15:00', '0', '4', '', '0', '2020-03-09', NULL, NULL, NULL),
('10', '4', '15:30', '16:30', '0', '4', '', '0', '2020-03-09', NULL, NULL, NULL),
('11', '7', '17:00', '18:00', '0', '4', '', '0', '2020-03-09', NULL, NULL, NULL),
('12', '5', '14:00', '15:00', '1', '4', '', '0', '2020-03-09', NULL, NULL, NULL),
('13', '6', '15:30', '16:30', '1', '4', '', '0', '2020-03-09', NULL, NULL, NULL),
('14', '8', '17:00', '18:00', '1', '4', '', '0', '2020-03-09', NULL, NULL, NULL),
('15', '7', '20:00', '21:00', '1', '4', '', '0', '2020-03-09', NULL, NULL, NULL),
('16', '8', '20:00', '21:00', '0', '4', '', '0', '2020-03-09', NULL, NULL, NULL),
('17', '8', '24:00', '24:30', '0', '4', '', '0', '2020-03-09', NULL, NULL, NULL),
('86', '1', '08:00', '09:00', '1', '4', '', '0', '2020-03-20', NULL, NULL, NULL),
('87', '2', '08:30', '09:30', '1', '4', '', '0', '2020-03-20', NULL, NULL, NULL),
('88', '3', '09:00', '10:00', '1', '4', '', '0', '2020-03-20', NULL, NULL, NULL),
('89', '4', '09:30', '10:30', '1', '4', '', '0', '2020-03-20', NULL, NULL, NULL),
('90', '1', '09:30', '10:30', '0', '4', '', '0', '2020-03-20', NULL, NULL, NULL),
('91', '2', '10:00', '11:00', '0', '4', '', '0', '2020-03-20', NULL, NULL, NULL),
('92', '5', '08:00', '09:00', '0', '4', '', '0', '2020-03-20', NULL, NULL, NULL),
('93', '6', '09:30', '10:30', '0', '4', '', '0', '2020-03-20', NULL, NULL, NULL),
('94', '3', '14:00', '15:00', '0', '4', '', '0', '2020-03-20', NULL, NULL, NULL),
('95', '4', '15:30', '16:30', '0', '4', '', '0', '2020-03-20', NULL, NULL, NULL),
('96', '7', '17:00', '18:00', '0', '4', '', '0', '2020-03-20', NULL, NULL, NULL),
('97', '5', '14:00', '15:00', '1', '4', '', '0', '2020-03-20', NULL, NULL, NULL),
('98', '6', '15:30', '16:30', '1', '4', '', '0', '2020-03-20', NULL, NULL, NULL),
('99', '8', '17:00', '18:00', '1', '4', '', '0', '2020-03-20', NULL, NULL, NULL),
('100', '7', '20:00', '21:00', '1', '4', '', '0', '2020-03-20', NULL, NULL, NULL);

```

## 订单服务

### sb_order

|      字段       |   类型    |              备注              |
| :-------------: | :-------: | :----------------------------: |
|      uuid       |  BIGINT   |             订单id             |
|    count_id     |  BIGINT   |             场次id             |
|   bus_status    |  VARCHAR  | 0:沙河->清水河；1:清水河->沙河 |
|    seats_ids    |  VARCHAR  |          已售座位编号          |
|   count_price   |  DOUBLE   |          场次预售价格          |
|   order_price   |  DOUBLE   |           订单总金额           |
|   order_time    | TIMESTAMP |            下单时间            |
|   order_user    |  BIGINT   |             下单人             |
|  order_status   |  VARCHAR  |   0-待支付,1-已支付,2-已关闭   |
| evaluate_status |  VARCHAR  |       0:未评价；1:已评价       |
|     comment     |  VARCHAR  |              评论              |

```sql


DROP TABLE IF EXISTS `sb_order`;
CREATE TABLE `sb_order` (
  `uuid` bigint(20) NOT NULL COMMENT '主键编号',
  `count_id` bigint(20) NOT NULL COMMENT '场次id',
  `bus_status` varchar(50) NOT NULL DEFAULT '' COMMENT '0:沙河->清水河；1:清水河->沙河',
  `seats_ids` varchar(50) NOT NULL DEFAULT '' COMMENT '已售座位编号',
  `count_price` double NOT NULL COMMENT '场次预售价格',
  `order_price` double NOT NULL COMMENT '订单总金额',
  `order_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_id` bigint(11) NOT NULL COMMENT '下单人id',
  `order_status` varchar(50) NOT NULL DEFAULT '' COMMENT '0-待支付,1-已支付,2-已关闭',
  `evaluate_status` varchar(50) NOT NULL DEFAULT '' COMMENT '0:未评价；1:已评价',
  `comment` varchar(255) NOT NULL DEFAULT '' COMMENT '评论',
  `order_user` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '下单人',
  `updated_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `test_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='订单表';

INSERT INTO `sb_order` (`uuid`, `count_id`, `bus_status`, `seats_ids`, `count_price`, `order_price`, `order_time`, `user_id`, `order_status`, `evaluate_status`, `comment`, `order_user`, `updated_at`, `created_at`, `test_time`) VALUES
('100000', '777778', '0', '7', '4', '4', '2021-02-24 21:18:20', '4', '2', '0', '', '4', NULL, NULL, NULL),
('129551', '637509', '0', '2', '4', '4', '2021-02-27 16:00:34', '4', '3', '0', '', '4', NULL, NULL, NULL),
('134377', '242768', '0', '2', '4', '4', '2021-03-04 00:00:51', '4', '0', '0', '', '4', NULL, NULL, NULL),
('135573', '242768', '0', '2,3', '4', '8', '2021-03-03 23:58:04', '4', '2', '0', '', '4', NULL, NULL, NULL),
('154867', '526060', '0', '2', '4', '4', '2021-03-01 17:31:31', '4', '2', '0', '', '4', NULL, NULL, NULL),
('164540', '654458', '0', '4', '4', '4', '2021-03-01 11:11:18', '4', '3', '0', '', '4', NULL, NULL, NULL),
('167392', '507996', '0', '1', '4', '4', '2021-04-04 16:21:48', '4', '0', '0', '', '4', NULL, NULL, NULL),
('181560', '9999888', '0', '6', '4', '4', '2021-02-25 13:07:06', '4', '2', '0', '', '4', NULL, NULL, NULL),
('183500', '769935', '0', '1', '4', '4', '2021-04-02 11:23:35', '18', '2', '0', '', '18', NULL, NULL, NULL),
('186538', '533702', '0', '6', '4', '4', '2021-02-27 10:40:40', '4', '2', '0', '', '4', NULL, NULL, NULL),
('187751', '533702', '0', '6', '4', '4', '2021-02-27 10:41:38', '4', '1', '0', '', '4', NULL, NULL, NULL),
('200000', '777778', '0', '', '4', '4', '2021-02-24 22:04:02', '4', '2', '0', '', '4', NULL, NULL, NULL),
('201866', '533702', '0', '8', '4', '4', '2021-02-27 11:27:00', '4', '3', '0', '', '4', NULL, NULL, NULL),
('205043', '533702', '0', '6', '4', '4', '2021-02-27 10:52:40', '4', '1', '0', '', '4', NULL, NULL, NULL),
('232057', '921891', '0', '1', '4', '4', '2021-03-01 20:10:09', '4', '2', '0', '', '4', NULL, NULL, NULL),
('242180', '253862', '0', '1', '4', '4', '2021-02-26 11:30:32', '4', '1', '0', '', '4', NULL, NULL, NULL),
('248601', '533702', '0', '6', '4', '4', '2021-02-27 10:59:45', '4', '3', '0', '', '4', NULL, NULL, NULL),
('252010', '872781', '0', '1', '4', '4', '2021-02-26 17:18:11', '4', '2', '0', '', '4', NULL, NULL, NULL),
('262831', '533702', '0', '6', '4', '4', '2021-02-27 10:57:00', '4', '1', '0', '', '4', NULL, NULL, NULL),
('263479', '507996', '0', '1', '4', '4', '2021-04-04 16:11:21', '4', '2', '0', '', '4', NULL, NULL, NULL),
('263907', '872781', '0', '1', '4', '4', '2021-02-26 17:25:11', '4', '1', '0', '', '4', NULL, NULL, NULL),
('264518', '654458', '0', '1', '4', '4', '2021-03-01 11:11:08', '4', '2', '0', '', '4', NULL, NULL, NULL),
('281761', '533702', '0', '7', '4', '4', '2021-02-27 11:21:19', '4', '1', '0', '', '4', NULL, NULL, NULL),
('282890', '872781', '0', '1', '4', '4', '2021-02-26 17:30:48', '4', '0', '0', '', '4', NULL, NULL, NULL),
('300000', '777778', '0', '', '4', '4', '2021-02-24 22:05:44', '4', '2', '0', '', '4', NULL, NULL, NULL),
('300307', '251021', '0', '5', '4', '4', '2021-02-26 20:32:16', '4', '0', '0', '', '4', NULL, NULL, NULL),
('309188', '872781', '0', '1', '4', '4', '2021-02-26 17:20:56', '4', '2', '0', '', '4', NULL, NULL, NULL),
('342288', '872781', '0', '1', '4', '4', '2021-02-26 19:57:52', '4', '1', '0', '', '4', NULL, NULL, NULL),
('356568', '533702', '0', '3', '4', '4', '2021-02-27 10:26:07', '4', '0', '0', '', '4', NULL, NULL, NULL),
('367647', '872781', '0', '5', '4', '4', '2021-02-26 18:13:16', '4', '1', '0', '', '4', NULL, NULL, NULL),
('380194', '637509', '0', '1', '4', '4', '2021-02-27 15:59:35', '4', '0', '0', '', '4', NULL, NULL, NULL),
('386405', '921891', '0', '1', '4', '4', '2021-03-01 20:17:16', '4', '2', '0', '', '4', NULL, NULL, NULL),
('400000', '777778', '0', '', '4', '4', '2021-02-24 22:05:45', '4', '2', '0', '', '4', NULL, NULL, NULL),
('405938', '637509', '0', '3', '4', '4', '2021-02-27 16:00:24', '4', '2', '0', '', '4', NULL, NULL, NULL),
('406336', '507996', '0', '1', '4', '4', '2021-04-04 16:10:01', '4', '3', '0', '', '4', NULL, NULL, NULL),
('412789', '526060', '0', '1', '4', '4', '2021-03-01 18:11:51', '4', '0', '0', '', '4', NULL, NULL, NULL),
('435545', '872781', '0', '1', '4', '4', '2021-02-26 17:18:56', '4', '2', '0', '', '4', NULL, NULL, NULL),
('438004', '533702', '0', '6', '4', '4', '2021-02-27 10:41:10', '4', '2', '0', '', '4', NULL, NULL, NULL),
('458800', '330112', '0', '1', '4', '4', '2021-03-01 17:06:56', '4', '2', '0', '', '4', NULL, NULL, NULL),
('473381', '242768', '0', '1', '4', '4', '2021-03-03 20:23:54', '4', '3', '0', '', '4', NULL, NULL, NULL),
('483979', '242768', '0', '11', '4', '4', '2021-03-03 23:59:05', '4', '1', '0', '', '4', NULL, NULL, NULL),
('498511', '507996', '0', '1', '4', '4', '2021-04-04 16:21:43', '4', '0', '0', '', '4', NULL, NULL, NULL),
('500000', '777778', '0', '3', '4', '4', '2021-02-24 20:45:15', '4', '2', '0', '', '4', NULL, NULL, NULL),
('507420', '533702', '0', '2', '4', '4', '2021-02-27 10:16:36', '4', '2', '0', '', '4', NULL, NULL, NULL),
('514935', '654458', '0', '1', '4', '4', '2021-03-01 12:39:21', '4', '0', '0', '', '4', NULL, NULL, NULL),
('530237', '469979', '0', '1', '4', '4', '2021-04-04 16:10:44', '4', '3', '0', '', '4', NULL, NULL, NULL),
('532060', '872781', '0', '1', '4', '4', '2021-02-26 17:21:43', '4', '1', '0', '', '4', NULL, NULL, NULL),
('571240', '507996', '0', '2', '4', '4', '2021-04-04 16:35:56', '4', '2', '0', '', '4', NULL, NULL, NULL),
('581841', '533702', '0', '6', '4', '4', '2021-02-27 11:20:30', '4', '0', '0', '', '4', NULL, NULL, NULL),
('593526', '654458', '0', '5', '4', '4', '2021-03-01 11:11:00', '4', '2', '0', '', '4', NULL, NULL, NULL),
('595548', '251021', '0', '2', '4', '4', '2021-02-26 20:18:09', '4', '2', '0', '', '4', NULL, NULL, NULL),
('595588', '872781', '0', '3', '4', '4', '2021-02-26 17:36:23', '4', '2', '0', '', '4', NULL, NULL, NULL),
('596810', '507996', '0', '3,4,7,8,11,12', '4', '24', '2021-04-04 16:32:01', '18', '1', '0', '', '18', NULL, NULL, NULL),
('600000', '777778', '0', '', '4', '4', '2021-02-24 22:14:59', '4', '2', '0', '', '4', NULL, NULL, NULL),
('630461', '872781', '0', '1', '4', '4', '2021-02-26 19:58:49', '4', '1', '0', '', '4', NULL, NULL, NULL),
('635575', '242768', '0', '7', '4', '4', '2021-03-04 00:00:25', '4', '0', '0', '', '4', NULL, NULL, NULL),
('642358', '251021', '0', '4', '4', '4', '2021-02-26 20:18:30', '4', '2', '0', '', '4', NULL, NULL, NULL),
('647694', '361090', '0', '2,3', '4', '8', '2021-02-28 21:16:26', '4', '2', '0', '', '4', NULL, NULL, NULL),
('661953', '361090', '0', '1', '4', '4', '2021-02-28 21:14:59', '4', '3', '0', '', '4', NULL, NULL, NULL),
('662508', '872781', '0', '4', '4', '4', '2021-02-26 18:12:17', '4', '2', '0', '', '4', NULL, NULL, NULL),
('667756', '654458', '0', '1', '4', '4', '2021-03-01 11:11:56', '4', '2', '0', '', '4', NULL, NULL, NULL),
('667944', '769935', '0', '1', '4', '4', '2021-04-02 10:30:23', '4', '3', '0', '', '4', NULL, NULL, NULL),
('681108', '533702', '0', '4', '4', '4', '2021-02-27 10:26:29', '4', '2', '0', '', '4', NULL, NULL, NULL),
('689197', '9999888', '0', '5', '4', '4', '2021-02-25 13:05:40', '4', '1', '0', '', '4', NULL, NULL, NULL),
('702103', '251021', '0', '3', '4', '4', '2021-02-26 20:17:46', '4', '1', '0', '', '4', NULL, NULL, NULL),
('726150', '533702', '0', '6', '4', '4', '2021-02-27 10:54:52', '4', '1', '0', '', '4', NULL, NULL, NULL),
('731615', '507996', '0', '2', '4', '4', '2021-04-04 16:35:28', '4', '3', '0', '', '4', NULL, NULL, NULL),
('736321', '654458', '0', '2', '4', '4', '2021-03-01 11:11:35', '4', '3', '0', '', '4', NULL, NULL, NULL),
('745831', '242768', '0', '3', '4', '4', '2021-03-04 00:00:41', '4', '0', '0', '', '4', NULL, NULL, NULL),
('763268', '272072', '0', '1', '4', '4', '2021-02-26 15:51:11', '4', '0', '0', '', '4', NULL, NULL, NULL),
('774864', '654458', '0', '6', '4', '4', '2021-03-01 11:08:05', '18', '1', '0', '', '18', NULL, NULL, NULL),
('785818', '872781', '0', '2', '4', '4', '2021-02-26 17:52:25', '4', '2', '0', '', '4', NULL, NULL, NULL);

```

