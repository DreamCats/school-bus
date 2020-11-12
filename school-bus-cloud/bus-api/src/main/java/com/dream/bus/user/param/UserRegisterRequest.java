/**
 * @program school-bus-cloud
 * @description: UserRegisterRequest
 * @author: mf
 * @create: 2020/10/31 15:28
 */

package com.dream.bus.user.param;

import com.dream.bus.param.AbstractRequest;
import lombok.Data;

@Data
public class UserRegisterRequest extends AbstractRequest {
    private String username;

    private String password;

    private String email;

    private String phone;
}
