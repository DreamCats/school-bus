/**
 * @program school-bus-cloud
 * @description: IPayService
 * @author: mf
 * @create: 2020/11/12 21:14
 */
package com.dream.bus.service;

import com.dream.bus.pay.PayBackRequest;
import com.dream.bus.pay.PayRequset;
import com.dream.bus.pay.PayResponse;

public interface IPayService {
    /**
     * 支付业务逻辑
     * @param requset
     * @return
     */
    PayResponse pay(PayRequset requset);

    /**
     * 退款业务逻辑
     * @param request
     * @return
     */
    PayResponse payBack(PayBackRequest request);
}
