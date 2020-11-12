/**
 * @program school-bus-cloud
 * @description: OrderServiceTest
 * @author: mf
 * @create: 2020/11/08 21:23
 */

package com.dream.bus;

import cn.hutool.core.convert.Convert;
import com.dream.bus.order.param.*;
import com.dream.bus.service.IOrderService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private IOrderService orderService;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Test
    public void getNoTakeOrdersById() {
        NoTakeBusRequest request = new NoTakeBusRequest();
        request.setUserId(Convert.toLong(4));
        request.setCurrentPage(Convert.toLong(1));
        request.setPageSize(Convert.toLong(2));
        NoTakeBusResponse response = orderService.getNoTakeOrdersById(request);
        System.out.println(response);
    }

    @Test
    public void getNoPayOrdersById() {
        NoPayRequest request = new NoPayRequest();
        request.setUserId(Convert.toLong(4));
        request.setCurrentPage(Convert.toLong(1));
        request.setPageSize(Convert.toLong(2));
        NoPayResponse response = orderService.getNoPayOrdersById(request);
        System.out.println(response);
    }

    @Test
    public void getEvaluateOrdersById() {
        EvaluateRequest request = new EvaluateRequest();
        request.setUserId(Convert.toLong(4));
        request.setCurrentPage(Convert.toLong(1));
        request.setPageSize(Convert.toLong(8));
        request.setEvaluateStatus("0");
        EvaluateResponse response = orderService.getEvaluateOrdersById(request);
        System.out.println(response);
    }

    @Test
    public void addOrder() {
        AddOrderRequest request = new AddOrderRequest();
        request.setBusStatus("0");// 沙河->清水河
        request.setCountId(Convert.toLong(102)); // 场次1
        request.setUserId(Convert.toLong(4)); // 4下单
        request.setCountPrice(4.00);
        request.setOrderUser("feng");
        request.setSeatsIds("3,4"); // 座位
        request.setExpireTime(300); // 过期时间 s
        AddOrderResponse response = orderService.addOrder(request);
        System.out.println(response);
    }

    @Test
    public void sendMQ() {
        rocketMQTemplate.convertAndSend("orderTopic", "springbootmq");
    }
}
