/**
 * @program school-bus
 * @description: UserVo
 * @author: mf
 * @create: 2020/02/24 16:27
 */

package com.stylefeng.guns.api.user.vo;

import lombok.Data;

@Data
public class UserVo{

    private Integer uuid; // 根据用户id

    private String userName; //

//    private String userPwd; // 密码不能报漏出去

    private String nickName;

    private Integer UserSex;

    private String email;

    private String userPhone;

    private long beginTime;

    private long updateTime;
}
