/**
 * @program school-bus
 * @description: UserResponse
 * @author: mf
 * @create: 2020/02/24 16:34
 */

package com.stylefeng.guns.rest.user.dto;

import com.stylefeng.guns.rest.common.AbstractResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class UserResponse extends AbstractResponse {

    @ApiModelProperty(notes = "userDto：用户表现体")
    private UserDto userDto; // 封装可返回展示的页面数据
}
