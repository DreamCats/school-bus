## 模型建立

> 声明：
>
> - sb代表SchoolBus
> - t代表table

## 用户相关

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
CREATE TABLE mooc_user_t(
   UUID INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键编号',
   user_name VARCHAR(50) NOT NULL DEFAULT '' COMMENT '用户账号',
   user_pwd VARCHAR(50) NOT NULL DEFAULT '' COMMENT '用户密码',
   nick_name VARCHAR(50) NOT NULL DEFAULT '' COMMENT '用户昵称',
   user_sex INT COMMENT '用户性别 0-男，1-女',
   email VARCHAR(50) NOT NULL DEFAULT '' COMMENT '用户邮箱',
   user_phone VARCHAR(50) NOT NULL DEFAULT '' COMMENT '用户手机号',
   begin_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) COMMENT '用户表' ENGINE = INNODB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

insert into sb_user_t(user_name,user_pwd,nick_name,user_sex,email,user_phone) values('admin','0192023a7bbd73250516f069df18b500','隔壁泰山',0,,'admin@qq.com','13888888888');
insert into sb_user_t(user_name,user_pwd,nick_name,user_sex,email,user_phone) values('jiangzh','5e2de6bd1c9b50f6e27d4e55da43b917','阿里郎',0,'jiangzh@qq.com','13866666666');

```

