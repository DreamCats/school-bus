/**
 * @program school-bus
 * @description: OrderMapperTest
 * @author: mf
 * @create: 2020/03/04 21:52
 */

package com.stylefeng.guns.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stylefeng.guns.rest.common.persistence.dao.OrderMapper;
import com.stylefeng.guns.rest.common.persistence.model.Order;
import com.stylefeng.guns.rest.order.dto.EvaluateDto;
import com.stylefeng.guns.rest.order.dto.NoTakeDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void test() {
        Order order = orderMapper.selectById(1);
        System.out.println(order);
    }

    @Test
    public void noTakeOrders() {
        IPage<NoTakeDto> noTakeDtoIPage = new Page<>(1,2);
        QueryWrapper<NoTakeDto> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", 4);
        noTakeDtoIPage = orderMapper.selectNoTakeOrders(noTakeDtoIPage, wrapper);
        System.out.println(noTakeDtoIPage.getRecords());
        System.out.println(noTakeDtoIPage.getSize());
    }

    @Test
    public void evaluateOrders() {
        IPage<EvaluateDto> evaluateDtoIPage = new Page<>(1,2);
        QueryWrapper<EvaluateDto> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", 4);
        evaluateDtoIPage = orderMapper.selectEvaluateOrders(evaluateDtoIPage, wrapper);
        System.out.println(evaluateDtoIPage.getRecords());
        System.out.println(evaluateDtoIPage.getSize());
    }
}
