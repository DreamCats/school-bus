/**
 * @program school-bus
 * @description: UserMapperTest
 * @author: mf
 * @create: 2020/02/26 00:54
 */

package com.stylefeng.guns.rest;

import com.stylefeng.guns.rest.common.persistence.dao.SbUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.SbUserT;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GunsUserApplication.class})
public class UserMapperTest {

    @Autowired
    private SbUserTMapper sbUserTMapper;

    @Test
    public void test() {
        SbUserT sbUserT = sbUserTMapper.selectById(2);
        System.out.println(sbUserT);
    }
}
