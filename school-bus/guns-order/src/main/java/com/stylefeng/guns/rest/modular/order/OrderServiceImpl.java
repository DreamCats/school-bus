/**
 * @program school-bus
 * @description: OrderServiceImpl
 * @author: mf
 * @create: 2020/03/04 21:50
 */

package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stylefeng.guns.rest.common.constants.RetCodeConstants;
import com.stylefeng.guns.rest.common.persistence.dao.OrderMapper;
import com.stylefeng.guns.rest.common.persistence.model.Order;
import com.stylefeng.guns.rest.modular.order.converter.OrderConvertver;
import com.stylefeng.guns.rest.order.IOrderSerice;
import com.stylefeng.guns.rest.order.dto.EvaluateRequest;
import com.stylefeng.guns.rest.order.dto.EvaluateResponse;
import com.stylefeng.guns.rest.order.dto.NoTakeBusRequest;
import com.stylefeng.guns.rest.order.dto.NoTakeBusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Service
public class OrderServiceImpl implements IOrderSerice {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderConvertver orderConvertver;

    @Override
    public NoTakeBusResponse getNoTakeOrdersById(NoTakeBusRequest request) {
        NoTakeBusResponse response = new NoTakeBusResponse();
        IPage<Order> orderIPage = new  Page<>(request.getCurrentPage(), request.getPageSize());
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("order_user", request.getUserId())
                .and(o -> o.eq("order_status", "0"));
        orderIPage = orderMapper.selectPage(orderIPage, queryWrapper);
        try {
            response.setCurrentPage(orderIPage.getCurrent());
            response.setPages(orderIPage.getPages());
            response.setPageSize(orderIPage.getSize());
            response.setTotal(orderIPage.getTotal());
            response.setOrderDtos(orderConvertver.order2Res(orderIPage.getRecords()));
            response.setCode(RetCodeConstants.SUCCESS.getCode());
            response.setMsg(RetCodeConstants.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getNoTakeOrdersById:",e);
            response.setCode(RetCodeConstants.DB_EXCEPTION.getCode());
            response.setMsg(RetCodeConstants.DB_EXCEPTION.getMessage());
            return response;
        }
        return response;
    }

    /**
     * 该业务可以和上一个业务合并
     * @param request
     * @return
     */
    @Override
    public EvaluateResponse getEvaluateOrdersById(EvaluateRequest request) {
        EvaluateResponse response = new EvaluateResponse();
        IPage<Order> orderIPage = new Page<>(request.getCurrentPage(), request.getPageSize());
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("order_user", request.getUserId())
                .eq("order_status", "1")
                .eq("evaluate_status", request.getEvaluateStatus());
        try {
            orderIPage = orderMapper.selectPage(orderIPage, queryWrapper);
            response.setCurrentPage(orderIPage.getCurrent());
            response.setPages(orderIPage.getPages());
            response.setPageSize(orderIPage.getSize());
            response.setTotal(orderIPage.getTotal());
            response.setOrderDtos(orderConvertver.order2Res(orderIPage.getRecords()));
            response.setCode(RetCodeConstants.SUCCESS.getCode());
            response.setMsg(RetCodeConstants.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getEvaluateOrdersById:",e);
            response.setCode(RetCodeConstants.DB_EXCEPTION.getCode());
            response.setMsg(RetCodeConstants.DB_EXCEPTION.getMessage());
            return response;
        }
        return response;
    }
}
