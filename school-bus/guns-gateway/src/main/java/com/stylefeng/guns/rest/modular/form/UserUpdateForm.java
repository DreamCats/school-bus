/**
 * @program school-bus
 * @description: UserUpdateForm
 * @author: mf
 * @create: 2020/03/02 00:55
 */

package com.stylefeng.guns.rest.modular.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "更新实体", description = "更新请求信息")
@Data
public class UserUpdateForm {
    @ApiModelProperty(hidden = true)
    private Integer id; // 通过id找

    //    private String userName;
    @ApiModelProperty(value = "昵称", required = false)
    private String nickName;

    @ApiModelProperty(value = "性别", dataType = "integer", required = false)
    private Integer userSex;

    @ApiModelProperty(value = "邮箱", required = false)
    private String email;

    @ApiModelProperty(value = "手机号", required = false)
    private String userPhone;
}
