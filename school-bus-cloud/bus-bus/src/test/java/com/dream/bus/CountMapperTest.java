/**
 * @program school-bus-cloud
 * @description: CountMapperTest
 * @author: mf
 * @create: 2020/11/08 14:26
 */

package com.dream.bus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.bus.bus.param.CountSimpleDto;
import com.dream.bus.dao.CountMapper;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class CountMapperTest {

    @Autowired
    private CountMapper countMapper;

    @Test
    public void selectCounts() {
        IPage<CountSimpleDto> simpleDtoIPage = new Page<>(1, 2);
        QueryWrapper<CountSimpleDto> wrapper = new QueryWrapper<>();
        wrapper.eq("bus_id", 1);
        simpleDtoIPage = countMapper.selectCounts(simpleDtoIPage, wrapper);
        System.out.println(simpleDtoIPage.getRecords());
    }

    @Test
    public void test(){
        System.out.println("hh");
    }
}
