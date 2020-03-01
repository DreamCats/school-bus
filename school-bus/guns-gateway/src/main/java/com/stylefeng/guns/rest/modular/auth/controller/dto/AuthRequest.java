package com.stylefeng.guns.rest.modular.auth.controller.dto;

import com.stylefeng.guns.rest.modular.auth.validator.dto.Credence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 认证的请求dto
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:00
 */
@ApiModel(value = "Auth请求实体", description = "Auth请求参数")
public class AuthRequest implements Credence {

    @NotNull(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", example = "mai", required = true)
    private String userName;

    @NotNull(message = "密码不能为空")
    @ApiModelProperty(value = "密码", example = "123", required = true)
    private String password;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String getCredenceName() {
        return this.userName;
    }

    @Override
    public String getCredenceCode() {
        return this.password;
    }
}
