/**
 * @program school-bus
 * @description: UserTest
 * @author: mf
 * @create: 2020/02/25 15:36
 */

package com.stylefeng.guns.rest;

import cn.hutool.core.convert.Convert;
import com.stylefeng.guns.rest.modular.user.RedisUtils;
import com.stylefeng.guns.rest.user.IUserService;
import com.stylefeng.guns.rest.user.dto.*;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class UserServiceTest {

    @Reference
    private IUserService userAPI;

    @Test
    public void checkUsernameTest() {
        UserCheckRequest req = new UserCheckRequest();
        req.setUsername("admin");
        UserCheckResponse res = userAPI.checkUsername(req);
        System.out.println(res);
    }

    @Test
    public void registerTest() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("haha");
        request.setPassword("123");
        request.setEmail("123@qq.com");
        request.setPhone("123");
        UserRegisterResponse response = userAPI.regsiter(request);
        System.out.println(response);
    }

    @Test
    public void updateUserInfo() {
        UserUpdateInfoRequest request = new UserUpdateInfoRequest();
        request.setNickName("峰");
        request.setId(Convert.toLong(4));
        request.setUserSex(0);
        UserResponse userResponse = userAPI.updateUserInfo(request);
        System.out.println(userResponse);
    }

    @Test
    public void  getUserInfoById() {
        UserRequest request = new UserRequest();
        request.setId(Convert.toLong(4));
        UserResponse response = userAPI.getUserById(request);
        System.out.println(response);
    }

}
