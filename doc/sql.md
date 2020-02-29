## 模型建立

> 声明：
>
> - sb代表SchoolBus
> - t代表table

## 用户服务

### sb_user_t

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
DROP TABLE IF EXISTS sb_user_t;
CREATE TABLE sb_user_t(
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

insert into sb_user_t(user_name,user_pwd,nick_name,user_sex,email,user_phone) values('admin','0192023a7bbd73250516f069df18b500','隔壁泰山',0,'admin@qq.com','13888888888');
insert into sb_user_t(user_name,user_pwd,nick_name,user_sex,email,user_phone) values('jiangzh','5e2de6bd1c9b50f6e27d4e55da43b917','阿里郎',0,'jiangzh@qq.com','13866666666');
```

## 班车服务

### sb_bus_t

|     表名     |   类型    |                         备注                         |
| :----------: | :-------: | :--------------------------------------------------: |
|     uuid     |    int    |                车牌包，用id吧，不麻烦                |
|  bus_status  |  VARCHAR  | 0：沙河；1：清水河；2：沙河到清水河；3：清水河到沙河 |
| limit_number |  VARCHAR  |                       限制人数                       |
| driver_name  |  VARCHAR  |                       司机姓名                       |
| drive_phone  |  VARCHAR  |                       司机电话                       |
|   weekday    |  VARCHAR  |                工作日(1,2,3,4,5,6,7)                 |
| seats_number |  VARCHAR  |                  座位排列：json文件                  |
|  begin_time  | TIMESTAMP |                       创建时间                       |
| update_time  | TIMESTAMP |                       更新时间                       |

```sql
DROP TABLE IF EXISTS sb_user_t;
CREATE TABLE sb_user_t(
   uuid INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键编号',
   limit_number VARCHAR(10) NOT NULL DEFAULT '' COMMENT '限制人数',
   driver_name VARCHAR(50) NOT NULL DEFAULT '' COMMENT '司机姓名',
   drive_phone VARCHAR(50) NOT NULL DEFAULT '' COMMENT '司机电话',
   seats_number VARCHAR(50) NOT NULL DEFAULT '' COMMENT '座位排列：json文件',
   begin_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) COMMENT '班车表' ENGINE = INNODB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

insert into sb_user_t(bus_status,limit_number,driver_name,drive_phone,seat_number) values('40','买','135','/file/seat.json');
insert into sb_user_t(bus_status,limit_number,driver_name,drive_phone,seat_number) values('40','赵','135','/file/seat.json');
insert into sb_user_t(bus_status,limit_number,driver_name,drive_phone,seat_number) values('40','李','135','/file/seat.json');
insert into sb_user_t(bus_status,limit_number,driver_name,drive_phone,seat_number) values('40','王','135','/file/seat.json');
```

### sb_count_t

|    字段    |  类型   |                         备注                         |
| :--------: | :-----: | :--------------------------------------------------: |
|    uuid    |   int   |                        场次id                        |
|   bus_id   |   INT   |                        班车id                        |
| begin_time | VARCHAR |                       开始时间                       |
|  end_time  | VARCHAR |                       结束时间                       |
| bus_status | VARCHAR | 0：沙河；1：清水河；2：沙河到清水河；3：清水河到沙河 |
|   price    |   INT   |                         票价                         |
|            |         |                                                      |

