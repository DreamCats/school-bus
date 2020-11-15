/**
 * @program school-bus
 * @description: BusServiceTest
 * @author: mf
 * @create: 2020/03/01 21:34
 */

package com.stylefeng.guns.rest;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.bus.IBusService;
import com.stylefeng.guns.rest.bus.dto.*;
import com.stylefeng.guns.rest.common.RedisUtils;
import com.stylefeng.guns.rest.common.persistence.dao.CountMapper;
import com.stylefeng.guns.rest.common.persistence.model.Count;
import com.stylefeng.guns.rest.myutils.UUIDUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusServiceTest {

    @Reference(check = false)
    private IBusService busService;
    @Autowired
    private CountMapper countMapper;
    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void getBus() {
        PageBusRequest request = new PageBusRequest();
        request.setCurrentPage(Convert.toLong(1));
        request.setPageSize(Convert.toLong(3));
        PageBusResponse response = busService.getBus(request);
        System.out.println(response);
    }

    @Test
    public void getCount() {
        PageCountRequest request = new PageCountRequest();
        request.setCurrentPage(Convert.toLong(2));
        request.setPageSize(Convert.toLong(1));
        request.setBusStatus("0");
        PageCountResponse response = busService.getCount(request);
        List<CountSimpleDto> countSimpleDtos = response.getCountSimpleDtos();
//        redisUtils.lSet("list", countSimpleDtos, 500);
//        redisUtils.lRemove("list", 3, countSimpleDtos);
        System.out.println(response);
    }

    @Test
    public void getCountDetailById() {
        CountDetailRequest request = new CountDetailRequest();
        request.setCountId(Convert.toLong(1));
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

    @Test
    public void addCounts() {
        // 获取日期
        String day = DateUtil.getDay();
        // 获取前17个场次
        QueryWrapper<Count> wrapper = new QueryWrapper<>();
        wrapper.last("limit 17");
        List<Count> counts = countMapper.selectList(wrapper);
        // 开始修改 这里可以用java8 的特性， 还不是很熟悉，后期优化一下
        for (Count count : counts) {
            // 更改日期
            count.setBeginDate(day);
            // 更改uuid
            count.setUuid(UUIDUtils.flakesUUID());
            // 清空座位
            count.setSelectedSeats("");
            // 将走位状态清零
            count.setSeatStatus("0");
            // 插入
            System.out.println(count);
            countMapper.insert(count);
        }
    }

    @Test
    public void getCountsRedis() {
        // 获取对象
        PageCountRequest request = new PageCountRequest();
        request.setCurrentPage(Convert.toLong(1));
        request.setPageSize(Convert.toLong(4));
        request.setBusStatus("0");
        PageCountResponse response = busService.getCount(request);
        List<CountSimpleDto> simpleDtos = response.getCountSimpleDtos();
        List<CountSimpleDto> cacheSimpleDtos = new ArrayList<>(); // 注意并发
        // 获取当前系统发车时间
        String hours = DateUtil.getHours();
        System.out.println("当前系统时间："+hours);
        for (CountSimpleDto simpleDto : simpleDtos) {
            String beginTime = simpleDto.getBeginTime();
            System.out.println("发车时间:" + beginTime);
            System.out.println(beginTime.compareTo("21:00"));
            if (beginTime.compareTo("21:00") > -1) {
                cacheSimpleDtos.add(simpleDto);
            }
        }
        System.out.println(cacheSimpleDtos);

    }

    @Test
    public void countRedisListTest() {
        ArrayList<String> list = new ArrayList<>();
        list.add("3");
        list.add("4");
//        redisUtils.lSet("list", list);
        list.add("5");
//        redisUtils.lSet("list", list);
//        redisUtils.lSet("list", "6");
//        redisUtils.lSet("list", "7");
//        redisUtils.lSet("list", "8");
//        redisUtils.lSet("list", "9");
//        redisUtils.lSet("list", "10");
        List<Object> list1 = redisUtils.lGet("list", 0, 1);
        // 不设置时间， 不管是谁， 只要触发请求，就请求所有的
        // 两种方案来实现这个吧...
        System.out.println(list1);
        System.out.println(redisUtils.lGetIndex("list", 5));
        System.out.println(redisUtils.hasKey("list2"));
    }
}
