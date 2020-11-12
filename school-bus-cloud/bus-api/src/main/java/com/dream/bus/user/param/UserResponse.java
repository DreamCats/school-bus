/**
 * @program school-bus-cloud
 * @description: UserResponse
 * @author: mf
 * @create: 2020/10/31 15:22
 */

package com.dream.bus.user.param;

import com.dream.bus.param.AbstractResponse;
import lombok.Data;

@Data
public class UserResponse extends AbstractResponse {

//    @ApiModelProperty(notes = "userDto：用户表现体")
    private UserDto userDto; // 封装可返回展示的页面数据
}
