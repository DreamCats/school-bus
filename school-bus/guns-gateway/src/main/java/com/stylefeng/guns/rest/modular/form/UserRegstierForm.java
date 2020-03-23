/**
 * @program school-bus
 * @description: UserRegstierForm
 * @author: mf
 * @create: 2020/03/02 00:51
 */

package com.stylefeng.guns.rest.modular.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(value = "注册实体", description = "注册请求信息")
@Data
public class UserRegstierForm {

    @NotNull(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", example = "mai", required = true)
    private String username;

    @NotNull(message = "密码不能为空")
    @ApiModelProperty(value = "密码", example = "123", required = true)
    private String password;

    @ApiModelProperty(value = "邮箱", required = false)
    private String email;

    @ApiModelProperty(value = "手机号码", required = false)
    private String phone;
}
