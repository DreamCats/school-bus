/**
 * @program school-bus-cloud
 * @description: AuthRequest
 * @author: mf
 * @create: 2020/11/03 14:12
 */

package com.dream.bus.auth.param;

import lombok.Data;

@Data
public class AuthRequest {

    private String userName;

    private String password;
}
