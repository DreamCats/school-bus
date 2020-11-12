/**
 * @program school-bus-cloud
 * @description: 测试
 * @author: mf
 * @create: 2020/10/31 00:09
 */

package com.dream.bus.controller;



import com.dream.bus.param.ResponseData;
import com.dream.bus.user.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/order/demo")
    public String orderDemo(){
        return "order demo";
    }

    @GetMapping("/order/user")
    public String orderUser() {
        ResponseData<Object> objectResponseData = userClient.checkUsername("123");

        return "123";
    }
}
