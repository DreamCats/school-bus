/**
 * @program school-bus
 * @description: BusServiceIml
 * @author: mf
 * @create: 2020/03/01 16:26
 */

package com.stylefeng.guns.rest.modular.bus;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.bus.IBusService;
import com.stylefeng.guns.rest.bus.dto.*;
import com.stylefeng.guns.rest.common.constants.RetCodeConstants;
import com.stylefeng.guns.rest.common.persistence.dao.BusMapper;
import com.stylefeng.guns.rest.common.persistence.dao.CountMapper;
import com.stylefeng.guns.rest.common.persistence.model.Bus;
import com.stylefeng.guns.rest.common.persistence.model.Count;
import com.stylefeng.guns.rest.modular.bus.converter.BusConverter;
import com.stylefeng.guns.rest.modular.bus.converter.CountConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Service
public class BusServiceImpl implements IBusService {

    @Autowired
    private BusMapper busMapper;
    @Autowired
    private CountMapper countMapper;
    @Autowired
    private BusConverter busConverter;
    @Autowired
    private CountConverter countConverter;

    @Override
    public PageBusResponse getBus(PageBusRequest request) {
        PageBusResponse response = new PageBusResponse();
        try {
            IPage<Bus> busIPage = new Page<>(request.getCurrentPage(), request.getPageSize());
            busIPage = busMapper.selectPage(busIPage, null);
            response.setCurrentPage(busIPage.getCurrent());
            response.setPageSize(busIPage.getSize());
            response.setPages(busIPage.getPages());
            response.setTotal(busIPage.getTotal());
            response.setBusDtos(busConverter.bus2List(busIPage.getRecords()));
            response.setCode(RetCodeConstants.SUCCESS.getCode());
            response.setMsg(RetCodeConstants.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(RetCodeConstants.DB_EXCEPTION.getCode());
            response.setMsg(RetCodeConstants.DB_EXCEPTION.getMessage());
            log.error("getBus:" , e);
            return response;
        }
        return response;
    }

    @Override
    public PageCountResponse getCount(PageCountRequest request) {
        PageCountResponse response = new PageCountResponse();
        try {
            IPage<CountSimpleDto> countIPage = new Page<>(request.getCurrentPage(), request.getPageSize());
            QueryWrapper<CountSimpleDto> queryWrapper = new QueryWrapper<>();
            // 获取时间
            String currHours = DateUtil.getHours();
            System.out.println("当前时间："+currHours);
            // 判断条件
            queryWrapper
                    .ge("begin_time", currHours) // 时间
                    .and(o -> o.eq("bus_status", request.getBusStatus()));

            countIPage = countMapper.selectCounts(countIPage, queryWrapper);
            response.setCurrentPage(countIPage.getCurrent());
            response.setPageSize(countIPage.getSize());
            response.setPages(countIPage.getPages());
            response.setTotal(countIPage.getTotal());
            response.setCountSimpleDtos(countIPage.getRecords());
            response.setCode(RetCodeConstants.SUCCESS.getCode());
            response.setMsg(RetCodeConstants.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(RetCodeConstants.DB_EXCEPTION.getCode());
            response.setMsg(RetCodeConstants.DB_EXCEPTION.getMessage());
            log.error("getCount:", e);
            return response;
        }
        return response;
    }

    /**
     * 私有，谁也无法访问
     */
    @Scheduled(cron = "0 0/30 7-21 * * ?") // 每天上午7点到晚上21点，每隔30分钟执行一次
    private void schedulChangeBusStatus() {
        // 获取
        String currTime = DateUtil.getHours();
        log.warn("schedulChangeBusStatus->目前时间：" + currTime);
        System.out.println("目前时间:"+ currTime);
        QueryWrapper<Count> queryWrapper = new QueryWrapper<>();
        // 先取出beingtime和now相等的表或者end_time和now相等到表
        queryWrapper
                .eq("begin_time", currTime)
                .or()
                .eq("end_time", currTime);
        List<Count> counts = countMapper.selectList(queryWrapper);
        log.warn("schedulChangeBusStatus->查询到的：" + counts.toString());
//        System.out.println("查询到的:"+counts.toString());
        // 开始作妖
        for (Count count : counts) {
            String busStatus = count.getBusStatus();
            String beginTime = count.getBeginTime();
            String endTime = count.getEndTime();
            if (currTime.equals(beginTime)) {
                if (busStatus.equals("0")) { // 沙河空闲
                    count.setBusStatus("2"); // 沙河->清水河
                }
                if (busStatus.equals("1")) { // 清水河空闲
                    count.setBusStatus("3"); // 清水河->沙河
                }
            }
            if (currTime.equals(endTime)) {
                if (busStatus.equals("2")) { // 沙河->清水河
                    count.setBusStatus("1"); // 清水河空闲
                }
                if (busStatus.equals("3")) { // 清水河->沙河
                    count.setBusStatus("0"); // 沙河空闲
                }
            }
            System.out.println("修改的：" + count);
            log.warn("schedulChangeBusStatus->修改的：" + count);
            // 写入数据库
            countMapper.updateById(count);
        }
    }
}
