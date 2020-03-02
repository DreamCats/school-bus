/**
 * @program school-bus
 * @description: BusServiceIml
 * @author: mf
 * @create: 2020/03/01 16:26
 */

package com.stylefeng.guns.rest.modular.bus;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stylefeng.guns.rest.bus.IBusService;
import com.stylefeng.guns.rest.bus.dto.PageBusRequest;
import com.stylefeng.guns.rest.bus.dto.PageBusResponse;
import com.stylefeng.guns.rest.bus.dto.PageCountRequest;
import com.stylefeng.guns.rest.bus.dto.PageCountResponse;
import com.stylefeng.guns.rest.common.constants.RetCodeConstants;
import com.stylefeng.guns.rest.common.persistence.dao.BusMapper;
import com.stylefeng.guns.rest.common.persistence.dao.CountMapper;
import com.stylefeng.guns.rest.common.persistence.model.Bus;
import com.stylefeng.guns.rest.common.persistence.model.Count;
import com.stylefeng.guns.rest.modular.bus.converter.BusConverter;
import com.stylefeng.guns.rest.modular.bus.converter.CountConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
            IPage<Count> countIPage = new Page<>(request.getCurrentPage(), request.getPageSize());
            countIPage = countMapper.selectPage(countIPage, null);
            response.setCurrentPage(countIPage.getCurrent());
            response.setPageSize(countIPage.getSize());
            response.setPages(countIPage.getPages());
            response.setTotal(countIPage.getTotal());
            response.setCountDtos(countConverter.count2Res(countIPage.getRecords()));
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
}
