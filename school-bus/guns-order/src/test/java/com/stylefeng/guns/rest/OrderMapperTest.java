/**
 * @program school-bus
 * @description: OrderMapperTest
 * @author: mf
 * @create: 2020/03/04 21:52
 */

package com.stylefeng.guns.rest;

import com.stylefeng.guns.rest.common.persistence.dao.OrderMapper;
import com.stylefeng.guns.rest.common.persistence.model.Order;
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
}
