/**
 * @program school-bus
 * @description: UserTest
 * @author: mf
 * @create: 2020/02/25 15:36
 */

package com.stylefeng.guns.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.common.ResponseUtil;
import com.stylefeng.guns.rest.user.UserAPI;
import com.stylefeng.guns.rest.user.vo.UserRequest;
import com.stylefeng.guns.rest.user.vo.UserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GunsUserApplication.class})
public class UserAPITest {

    @Reference
    private UserAPI userAPI;

    @Test
    public void checkUsernameTest() {
        UserRequest req = new UserRequest();
        UserResponse res = userAPI.checkUsername(req);
        System.out.println(res);
        System.out.println(res.getCode());
    }

}
