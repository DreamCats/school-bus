package com.dream.bus.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;

import com.dream.bus.model.Order;
import com.dream.bus.order.param.EvaluateDto;
import com.dream.bus.order.param.NoPayDto;
import com.dream.bus.order.param.NoTakeDto;
import com.dream.bus.order.param.OrderDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    /**
     *
     * @param page
     * @param wrapper
     * @return
     */
    IPage<NoTakeDto> selectNoTakeOrders(IPage<NoTakeDto> page, @Param(Constants.WRAPPER) Wrapper<NoTakeDto> wrapper);

    /**
     *
     * @param page
     * @param wrapper
     * @return
     */
    IPage<EvaluateDto> selectEvaluateOrders(IPage<EvaluateDto> page, @Param(Constants.WRAPPER) Wrapper<EvaluateDto> wrapper);


    /**
     *
     * @param page
     * @param wrapper
     * @return
     */
    IPage<NoPayDto> selectNoPayOrders(IPage<NoPayDto> page, @Param(Constants.WRAPPER) Wrapper<NoPayDto> wrapper);

    /**
     *
     * @param wrapper
     * @return
     */
    OrderDto selectOrderById(@Param(Constants.WRAPPER) Wrapper<OrderDto> wrapper);
}
