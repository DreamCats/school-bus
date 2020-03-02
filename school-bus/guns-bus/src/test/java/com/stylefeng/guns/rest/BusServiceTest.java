/**
 * @program school-bus
 * @description: BusServiceTest
 * @author: mf
 * @create: 2020/03/01 21:34
 */

package com.stylefeng.guns.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.bus.IBusService;
import com.stylefeng.guns.rest.bus.dto.PageBusRequest;
import com.stylefeng.guns.rest.bus.dto.PageBusResponse;
import com.stylefeng.guns.rest.bus.dto.PageCountRequest;
import com.stylefeng.guns.rest.bus.dto.PageCountResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusServiceTest {

    @Reference
    private IBusService busService;

    @Test
    public void getBus() {
        PageBusRequest request = new PageBusRequest();
        request.setCurrentPage(1);
        request.setPageSize(3);
        PageBusResponse response = busService.getBus(request);
        System.out.println(response);
    }

    @Test
    public void getCount() {
        PageCountRequest request = new PageCountRequest();
        request.setCurrentPage(1);
        request.setPageSize(4);
        PageCountResponse response = busService.getCount(request);
        System.out.println(response);
    }
}
