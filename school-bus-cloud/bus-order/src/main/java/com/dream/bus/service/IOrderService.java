/**
 * @program school-bus-cloud
 * @description: IOrderService
 * @author: mf
 * @create: 2020/11/08 16:17
 */
package com.dream.bus.service;

import com.dream.bus.order.param.*;

public interface IOrderService {

    /**
     * 获取未乘坐订单
     * @param request
     * @return
     */
    NoTakeBusResponse getNoTakeOrdersById(NoTakeBusRequest request);

    /**
     * 根据评价状态获取订单
     * @param request
     * @return
     */
    EvaluateResponse getEvaluateOrdersById(EvaluateRequest request);

    /**
     * 获取待支付订单
     * @param request
     * @return
     */
    NoPayResponse getNoPayOrdersById(NoPayRequest request);

    /**
     * 添加订单
     * @param request
     * @return
     */
    AddOrderResponse addOrder(AddOrderRequest request);

    /**
     * 通过id获取详情订单呢
     * @param request
     * @return
     */
    OrderResponse selectOrderById(OrderRequest request);

    /**
     * 修改订单状态
     * @param request
     * @return
     */
    OrderResponse updateOrderStatus(OrderRequest request);

    /**
     * 根据订单Id删除订单
     * @param OrderId
     * @return
     */
    boolean deleteOrderById(Long OrderId);

    AddOrderResponse addOrderTest(AddOrderRequest request);

    int doAddOrder(Long orderId, String redisKey, AddOrderRequest request);
}
