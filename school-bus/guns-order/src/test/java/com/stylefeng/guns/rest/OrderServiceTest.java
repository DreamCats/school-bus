/**
 * @program school-bus
 * @description: OrderServiceTest
 * @author: mf
 * @create: 2020/03/04 22:35
 */

package com.stylefeng.guns.rest;

import cn.hutool.core.convert.Convert;
import com.stylefeng.guns.rest.order.IOrderService;
import com.stylefeng.guns.rest.order.dto.*;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Reference
    private IOrderService orderSerice;

    @Test
    public void getNoTakeOrdersById() {
        NoTakeBusRequest request = new NoTakeBusRequest();
        request.setUserId(Convert.toLong(4));
        request.setCurrentPage(Convert.toLong(1));
        request.setPageSize(Convert.toLong(2));
        NoTakeBusResponse response = orderSerice.getNoTakeOrdersById(request);
        System.out.println(response);
    }

    @Test
    public void getNoPayOrdersById() {
        NoPayRequest request = new NoPayRequest();
        request.setUserId(Convert.toLong(4));
        request.setCurrentPage(Convert.toLong(1));
        request.setPageSize(Convert.toLong(2));
        NoPayResponse response = orderSerice.getNoPayOrdersById(request);
        System.out.println(response);
    }

    @Test
    public void getEvaluateOrdersById() {
        EvaluateRequest request = new EvaluateRequest();
        request.setUserId(Convert.toLong(4));
        request.setCurrentPage(Convert.toLong(1));
        request.setPageSize(Convert.toLong(8));
        request.setEvaluateStatus("0");
        EvaluateResponse response = orderSerice.getEvaluateOrdersById(request);
        System.out.println(response);
    }

    @Test
    public void addOrder() {
        AddOrderRequest request = new AddOrderRequest();
        request.setBusStatus("0");// 沙河->清水河
        request.setCountId(Convert.toLong(390)); // 场次1
        request.setUserId(Convert.toLong(4)); // 4下单
        request.setCountPrice(4.00);
        request.setOrderUser("feng");
        request.setSeatsIds("3,4"); // 座位
        AddOrderResponse response = orderSerice.addOrder(request);
        System.out.println(response);
    }
}
