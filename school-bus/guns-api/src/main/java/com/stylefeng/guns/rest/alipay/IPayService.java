/**
 * @program school-bus
 * @description: IPayService
 * @author: mf
 * @create: 2020/03/17 16:53
 */
package com.stylefeng.guns.rest.alipay;

import com.stylefeng.guns.rest.alipay.dto.PayBackRequest;
import com.stylefeng.guns.rest.alipay.dto.PayRequset;
import com.stylefeng.guns.rest.alipay.dto.PayResponse;

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
