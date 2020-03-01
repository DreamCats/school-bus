/**
 * @program school-bus
 * @description: UserVo
 * @author: mf
 * @create: 2020/02/24 16:27
 */

package com.stylefeng.guns.rest.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    private Integer uuid; // 根据用户id

    private String userName; //

//    private String userPwd; // 密码不能报漏出去

    private String nickName;

    private Integer UserSex;

    private String email;

    private String userPhone;

    private String beginTime;

    private String updateTime;
}
