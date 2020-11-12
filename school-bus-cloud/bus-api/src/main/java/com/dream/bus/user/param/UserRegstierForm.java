/**
 * @program school-bus-cloud
 * @description: UserRegstierForm
 * @author: mf
 * @create: 2020/10/31 16:04
 */

package com.dream.bus.user.param;


import lombok.Data;

@Data
public class UserRegstierForm {

//    @NotNull(message = "用户名不能为空")
//    @ApiModelProperty(value = "用户名", example = "mai", required = true)
    private String username;

//    @NotNull(message = "密码不能为空")
//    @ApiModelProperty(value = "密码", example = "123", required = true)
    private String password;

//    @ApiModelProperty(value = "邮箱", required = false)
    private String email;

//    @ApiModelProperty(value = "手机号码", required = false)
    private String phone;
}
