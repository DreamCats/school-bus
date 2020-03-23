/**
 * @program school-bus
 * @description: UserCheckResponse
 * @author: mf
 * @create: 2020/02/26 00:31
 */

package com.stylefeng.guns.rest.user.dto;

import com.stylefeng.guns.rest.common.AbstractResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserCheckResponse extends AbstractResponse {

    @ApiModelProperty(notes = "0：用户已经存在,1：用户不存在")
    private int checkUsername; // 0 代表有， 1 代表没有
}
