/**
 * @program school-bus-cloud
 * @description: AuthClient
 * @author: mf
 * @create: 2020/11/03 14:10
 */
package com.dream.bus.auth;

import com.dream.bus.auth.param.AuthRequest;
import com.dream.bus.param.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "bus-auth")
public interface AuthClient {

    @RequestMapping("/auth")
    ResponseData createAuthenticationToken(AuthRequest authRequest);
}
