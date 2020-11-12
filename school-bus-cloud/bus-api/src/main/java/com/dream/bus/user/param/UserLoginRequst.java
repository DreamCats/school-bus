/**
 * @program school-bus-cloud
 * @description: UserLoginRequst
 * @author: mf
 * @create: 2020/10/31 15:27
 */

package com.dream.bus.user.param;

import com.dream.bus.param.AbstractRequest;
import lombok.Data;

@Data
public class UserLoginRequst extends AbstractRequest {

    private String username;

    private String password;
}
