/**
 * @program school-bus-cloud
 * @description: UserCheckResponse
 * @author: mf
 * @create: 2020/10/31 15:26
 */

package com.dream.bus.user.param;

import com.dream.bus.param.AbstractResponse;
import lombok.Data;

@Data
public class UserCheckResponse extends AbstractResponse {

//    @ApiModelProperty(notes = "0：用户已经存在,1：用户不存在")
    private int checkUsername; // 0 代表有， 1 代表没有
}
