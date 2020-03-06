/**
 * @program school-bus
 * @description: IOrderSerice
 * @author: mf
 * @create: 2020/03/04 22:02
 */
package com.stylefeng.guns.rest.order;

import com.stylefeng.guns.rest.order.dto.EvaluateRequest;
import com.stylefeng.guns.rest.order.dto.EvaluateResponse;
import com.stylefeng.guns.rest.order.dto.NoTakeBusRequest;
import com.stylefeng.guns.rest.order.dto.NoTakeBusResponse;

public interface IOrderSerice {

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
}
