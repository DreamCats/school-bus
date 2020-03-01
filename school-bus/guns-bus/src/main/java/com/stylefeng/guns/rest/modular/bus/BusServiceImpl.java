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
import com.stylefeng.guns.rest.common.constants.RetCodeConstants;
import com.stylefeng.guns.rest.common.persistence.dao.BusMapper;
import com.stylefeng.guns.rest.common.persistence.model.Bus;
import com.stylefeng.guns.rest.modular.bus.converter.BusConverter;
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
    private BusConverter busConverter;

    @Override
    public PageBusResponse getBus(PageBusRequest request) {
        PageBusResponse response = new PageBusResponse();
        try {
            IPage<Bus> sbBusTPage = new Page<>(request.getCurrentPage(), request.getPageSize());
            sbBusTPage = busMapper.selectPage(sbBusTPage, null);
            response.setCurrentPage(sbBusTPage.getCurrent());
            response.setPageSize(sbBusTPage.getSize());
            response.setPages(sbBusTPage.getPages());
            response.setTotal(sbBusTPage.getTotal());
            response.setBusDtos(busConverter.sbBusT2List(sbBusTPage.getRecords()));
            response.setCode(RetCodeConstants.SUCCESS.getCode());
            response.setMsg(RetCodeConstants.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(RetCodeConstants.DB_EXCEPTION.getCode());
            response.setMsg(RetCodeConstants.DB_EXCEPTION.getMessage());
            log.error("getBus:" , e);
        }
        return response;
    }
}
