/**
 * @program school-bus-cloud
 * @description: UserClient
 * @author: mf
 * @create: 2020/10/31 11:15
 */
package com.dream.bus.user;


import com.dream.bus.param.ResponseData;
import com.dream.bus.user.param.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "bus-user")
public interface UserClient {
    @GetMapping("/user/check")
    ResponseData checkUsername(@RequestParam("username") String username);

    @PostMapping("/user/register")
    ResponseData register(@RequestBody UserRegstierForm form);

    @PostMapping("/user/login")
    ResponseData login(@RequestBody UserLoginRequst requst); // 本身这里要用form的，我懒了

    @GetMapping("/user/localLogin")
    UserLoginResponse localLogin(@RequestBody UserLoginRequst requst);

    @GetMapping("user/localGetUserInfo")
    UserResponse localGetUserInfo(@RequestBody UserRequest request);

    @PostMapping("user/localUpdateInfo")
    UserResponse localUpdateInfo(@RequestBody UserUpdateInfoRequest request);
}
