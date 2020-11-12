/**
 * @program school-bus-cloud
 * @description: UserDto
 * @author: mf
 * @create: 2020/10/31 15:19
 */

package com.dream.bus.user.param;

import lombok.Data;

import java.io.Serializable;

@Data
//@ApiModel(description = "用户表现实体")
public class UserDto implements Serializable {

//    @ApiModelProperty(notes = "用户id，这没啥好说的，暂时按自增")
    private Long uuid; // 根据用户id
//    @ApiModelProperty(notes = "用户名")
    private String userName; //

    //    private String userPwd; // 密码不能报漏出去
//    @ApiModelProperty(notes = "用户昵称")
    private String nickName;
//    @ApiModelProperty(notes = "0：男，1：女")
    private Integer UserSex;
//    @ApiModelProperty(notes = "用户邮件")
    private String email;
//    @ApiModelProperty(notes = "用户手机号码")
    private String userPhone;
//    @ApiModelProperty(notes = "用户余额")
    private Double money;
//    @ApiModelProperty(notes = "用户支付密码") // 后期加密
    private String payPassword;
//    @ApiModelProperty("创建时间")
    private String beginTime;
//    @ApiModelProperty("更新时间")
    private String updateTime;
}
