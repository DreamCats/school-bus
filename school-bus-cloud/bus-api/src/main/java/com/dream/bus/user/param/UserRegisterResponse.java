/**
 * @program school-bus-cloud
 * @description: UserRegisterResponse
 * @author: mf
 * @create: 2020/10/31 15:28
 */

package com.dream.bus.user.param;

import com.dream.bus.param.AbstractResponse;
import lombok.Data;

@Data
public class UserRegisterResponse extends AbstractResponse {

//    @ApiModelProperty(notes = "true：注册成功，false：注册失败")
    private boolean register;
}
