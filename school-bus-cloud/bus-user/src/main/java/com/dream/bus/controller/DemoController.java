/**
 * @program school-bus-cloud
 * @description: 测试
 * @author: mf
 * @create: 2020/10/30 23:59
 */

package com.dream.bus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/user/demo")
    public String demo() {
        return "user demo";
    }
}
