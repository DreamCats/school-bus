/**
 * @program school-bus
 * @description: OrderServiceTest
 * @author: mf
 * @create: 2020/03/04 22:35
 */

package com.stylefeng.guns.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.order.IOrderSerice;
import com.stylefeng.guns.rest.order.dto.EvaluateRequest;
import com.stylefeng.guns.rest.order.dto.EvaluateResponse;
import com.stylefeng.guns.rest.order.dto.NoTakeBusRequest;
import com.stylefeng.guns.rest.order.dto.NoTakeBusResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Reference
    private IOrderSerice orderSerice;

    @Test
    public void getNoTakeOrdersById() {
        NoTakeBusRequest request = new NoTakeBusRequest();
        request.setUserId(4);
        request.setCurrentPage(1);
        request.setPageSize(2);
        NoTakeBusResponse response = orderSerice.getNoTakeOrdersById(request);
        System.out.println(response);
    }

    @Test
    public void getEvaluateOrdersById() {
        EvaluateRequest request = new EvaluateRequest();
        request.setUserId(4);
        request.setCurrentPage(1);
        request.setPageSize(2);
        request.setEvaluateStatus("1");
        EvaluateResponse response = orderSerice.getEvaluateOrdersById(request);
        System.out.println(response);
    }
}
