/**
 * @program school-bus
 * @description: UserRegisterRequest
 * @author: mf
 * @create: 2020/02/24 16:52
 */

package com.stylefeng.guns.rest.user.vo;

import com.stylefeng.guns.rest.common.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "注册实体", description = "注册请求信息")
@Data
public class UserRegisterRequest extends AbstractRequest {

    @ApiModelProperty(value = "用户名", example = "mai", required = true)
    private String username;

    @ApiModelProperty(value = "密码", example = "123", required = true)
    private String password;

    @ApiModelProperty(value = "邮箱", required = false)
    private String email;

    @ApiModelProperty(value = "手机号码", required = false)
    private String phone;
}
