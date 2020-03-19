/**
 * @program school-bus
 * @description: RedisTest
 * @author: mf
 * @create: 2020/03/13 18:57
 */

package com.stylefeng.guns.rest;


import com.stylefeng.guns.rest.modular.user.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void test() {
        System.out.println(redisUtils.set("a", 1));
        System.out.println(redisUtils.get("a"));
        redisUtils.set("b", 2, 20);
    }
}
