package com.stylefeng.guns.rest.common.persistence.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.stylefeng.guns.rest.common.persistence.model.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stylefeng.guns.rest.order.dto.EvaluateDto;
import com.stylefeng.guns.rest.order.dto.NoPayDto;
import com.stylefeng.guns.rest.order.dto.NoTakeDto;
import com.stylefeng.guns.rest.order.dto.OrderDto;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author Maifeng
 * @since 2020-03-04
 */
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
