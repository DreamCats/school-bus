/**
 * @program school-bus
 * @description: UserMapperTest
 * @author: mf
 * @create: 2020/02/26 00:54
 */

package com.stylefeng.guns.rest;

import com.stylefeng.guns.rest.common.persistence.dao.UserMapper;
import com.stylefeng.guns.rest.common.persistence.model.User;
import com.stylefeng.guns.rest.modular.user.converter.UserConverter;
import com.stylefeng.guns.rest.user.vo.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GunsUserApplication.class})
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserConverter userConverter;

    @Test
    public void test() {
        User user = userMapper.selectById(2);
        System.out.println(user);
    }


    @Test
    public void registerTest() {
        User user = userMapper.selectById(2);
        UserVo userVo = userConverter.sbUserT2Res(user);
        System.out.println(userVo);
    }
}
