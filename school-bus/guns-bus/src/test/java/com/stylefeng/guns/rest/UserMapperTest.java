/**
 * @program school-bus
 * @description: UserMapperTest
 * @author: mf
 * @create: 2020/02/26 00:54
 */

package com.stylefeng.guns.rest;

import com.stylefeng.guns.rest.common.persistence.dao.SbUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.SbUserT;
import com.stylefeng.guns.rest.modular.bus.converter.UserConverter;
import com.stylefeng.guns.rest.user.vo.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GunsBusApplication.class})
public class UserMapperTest {

    @Autowired
    private SbUserTMapper sbUserTMapper;

    @Autowired
    private UserConverter userConverter;

    @Test
    public void test() {
        SbUserT sbUserT = sbUserTMapper.selectById(2);
        System.out.println(sbUserT);
    }


    @Test
    public void registerTest() {
        SbUserT sbUserT = sbUserTMapper.selectById(2);
        UserVo userVo = userConverter.sbUserT2Res(sbUserT);
        System.out.println(userVo);
    }
}
