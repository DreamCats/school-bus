/**
 * @program school-bus
 * @description: CountMapperTest
 * @author: mf
 * @create: 2020/03/10 01:25
 */

package com.stylefeng.guns.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stylefeng.guns.rest.bus.dto.CountDetailDto;
import com.stylefeng.guns.rest.bus.dto.CountSimpleDto;
import com.stylefeng.guns.rest.common.persistence.dao.CountMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
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
    public void selectCountDetailById() {
        QueryWrapper<CountDetailDto> wrapper = new QueryWrapper<>();
        wrapper.eq("sc.uuid", 1);
        CountDetailDto countDetailDto = countMapper.selectCountDetailById(wrapper);
        System.out.println(countDetailDto);
    }
}
