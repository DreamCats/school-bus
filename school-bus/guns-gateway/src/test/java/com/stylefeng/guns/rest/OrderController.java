/**
 * @program school-bus
 * @description: OrderController
 * @author: mf
 * @create: 2020/03/19 18:46
 */

package com.stylefeng.guns.rest;

import cn.hutool.core.convert.Convert;
import com.stylefeng.guns.rest.alipay.IPayService;
import com.stylefeng.guns.rest.alipay.dto.PayRequset;
import com.stylefeng.guns.rest.alipay.dto.PayResponse;
import com.stylefeng.guns.rest.order.IOrderService;
import com.stylefeng.guns.rest.order.dto.AddOrderRequest;
import com.stylefeng.guns.rest.order.dto.AddOrderResponse;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderController {

    @Reference(check = false)
    private IOrderService orderSerice;

    @Reference(check = false)
    private IPayService payService;

    @Test
    public void addOrder() {
        AddOrderRequest request = new AddOrderRequest();
        request.setBusStatus("0");// 沙河->清水河
        request.setCountId(Convert.toLong(1)); // 场次1
        request.setUserId(Convert.toLong(4)); // 4下单
        request.setCountPrice(4.00);
        request.setOrderUser("feng");
        request.setSeatsIds("3,4"); // 座位
        AddOrderResponse response = orderSerice.addOrder(request);
        System.out.println(response);
    }

    @Test
    public void pay() {
        PayRequset requset = new PayRequset();
        requset.setUserId(Convert.toLong(4));
        requset.setPayPassword("123456");
        requset.setTotalMoney(100.00);
        PayResponse response = payService.pay(requset);
        System.out.println(response);
    }
}
