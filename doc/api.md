## API文档

### 用户服务

**注意：除了/user/check，/user/register以外，都需要携带token**

#### 检查用户名

- URL：/user/check?username=mai
- 请求方式：get
- 请求参数：

|   字段   |  备注  | 是否必须 |
| :------: | :----: | :------: |
| username | 用户名 |    是    |

- 返回结果：

|     字段      |       备注       |
| :-----------: | :--------------: |
| checkUsername | 0:存在；1:不存在 |

```json
{
    "code": 200,
    "message": "success",
    "result": {
        "checkUsername": 0,
        "code": "000000",
        "msg": "成功"
    },
    "success": true,
    "timestamp": "1582887082687"
}
```

### 注册用户

- URL:/user/register
- 请求方式：post
- 请求参数

|   字段    |   备注   | 是否必须 |
| :-------: | :------: | :------: |
| username  |  用户名  |    是    |
| passworod | 用户密码 |    是    |
|   email   |   邮箱   |    否    |
|   phone   |  手机号  |    否    |

- 返回结果：

|   字段   |        备注        |
| :------: | :----------------: |
| register | 注册是否成功：true |

```json
{
    "code": 200,
    "message": "success",
    "result": {
        "code": "000000",
        "msg": "成功",
        "register": true
    },
    "success": true,
    "timestamp": "1582891125674"
}
```

#### 获取token

- url：/auth?userName=mai&password=123
- 请求方式：get
- 请求参数

|   字段   |  备注  | 是否必须 |
| :------: | :----: | -------- |
| userName | 用户名 | 是       |
| password |  密码  | 是       |

- 返回结果

|   字段    |           备注           |
| :-------: | :----------------------: |
| randomKey |         随机key          |
|   token   | 作为统一访问的服务的钥匙 |
|  userId   |          用户id          |

```json
{
    "code": 200,
    "message": "success",
    "result": {
        "code": "000000",
        "msg": "成功",
        "randomKey": "zey79e",
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJyYW5kb21LZXkiOiJ6ZXk3OWUiLCJzdWIiOiI0IiwiZXhwIjoxNTgzNDk2MjA1LCJpYXQiOjE1ODI4OTE0MDV9.1DjBoH_w0E9zr8eyf-FivTeyfaSAgej02-bWGOy5D3PVpSGtuJmWKyddKFTteSwlyMoRld7hePHsioZvPQzFyg",
        "userId": 4
    },
    "success": true,
    "timestamp": "1582891405939"
}
```

#### 获取用户信息

- url:/user/getUserInfo
- 请求方式：get
- 无参数
- 返回结果

|    字段    |    备注    |
| :--------: | :--------: |
|    uuid    |   用户id   |
|  userName  |  用户账号  |
|  nickName  |  用户昵称  |
| userPhone  |  手机号码  |
|  userSex   | 0:男；1:女 |
|   email    |  用户邮箱  |
| updateTime |  更新时间  |
| beginTime  |  创建时间  |

```json
{
    "code": 200,
    "message": "success",
    "result": {
        "code": "000000",
        "msg": "成功",
        "userVo": {
            "beginTime": "1582755039000",
            "email": "123@qq.com",
            "nickName": "峰",
            "updateTime": "1582782713000",
            "userName": "mai",
            "userPhone": "123",
            "userSex": 0,
            "uuid": 4
        }
    },
    "success": true,
    "timestamp": "1582891495253"
}
```

#### 更新用户信息

- url：/user/updateInfo
- 请求方式：post
- 请求参数

|   字段    |   备注   | 是否必须 |
| :-------: | :------: | :------: |
| nickName  | 用户昵称 |    否    |
|  userSex  | 用户性别 |    否    |
|   email   | 用户邮箱 |    否    |
| userPhone | 用户手机 |    否    |

- 返回结果

|  字段  |   备注   |
| :----: | :------: |
| userVo | 用户信息 |



```json
{
    "code": 200,
    "message": "success",
    "result": {
        "code": "000000",
        "msg": "成功",
        "userVo": {
            "beginTime": "1582755039000",
            "email": "mai@qq.com",
            "nickName": "哈哈哈",
            "updateTime": "1582942461000",
            "userName": "mai",
            "userPhone": "123",
            "userSex": 1,
            "uuid": 4
        }
    },
    "success": true,
    "timestamp": "1582892061669"
}
```

#### 登出

- url：/user/logout
- 请求方式：get
- 请求参数：无
- 返回结果

```json
{
    "code": 200,
    "message": "success",
    "result": "退出成功...",
    "success": true,
    "timestamp": "1582892320719"
}
```

- 注意：前端删除token，后端暂时还没有用上redis。

