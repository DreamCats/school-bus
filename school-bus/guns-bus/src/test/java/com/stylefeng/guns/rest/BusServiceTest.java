/**
 * @program school-bus
 * @description: BusServiceTest
 * @author: mf
 * @create: 2020/03/01 21:34
 */

package com.stylefeng.guns.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.bus.IBusService;
import com.stylefeng.guns.rest.bus.dto.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;

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

    @Test
    public void getCountDetailById() {
        CountDetailRequest request = new CountDetailRequest();
        request.setCountId(1);
        CountDetailResponse response = busService.getCountDetailById(request);
        System.out.println(response);
    }

    @Test
    public void repeatSeats() {
        String a = "1,2";
        String b = "2,3";
        String[] as = a.split(",");
        String[] bs = b.split(",");
        HashSet<String> hashSet = new HashSet<>(Arrays.asList(bs)); // 这步存在并发问题 值得优化的地方
        for (String s : as) {
            if (hashSet.contains(s)) {
                System.out.println("包含");
                return;
            }
        }
        System.out.println("不包含");
    }
}
