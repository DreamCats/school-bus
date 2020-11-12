/**
 * @program school-bus-cloud
 * @description: UserLoginResponse
 * @author: mf
 * @create: 2020/10/31 15:27
 */

package com.dream.bus.user.param;

import com.dream.bus.param.AbstractResponse;
import lombok.Data;

@Data
public class UserLoginResponse extends AbstractResponse {
    private String randomKey;

    private String token;

    private Long userId;
}
