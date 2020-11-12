/**
 * @program school-bus
 * @description: OrderResponse
 * @author: mf
 * @create: 2020/03/15 03:11
 */

package com.dream.bus.order.param;

import com.dream.bus.param.AbstractResponse;
import lombok.Data;

@Data
public class OrderResponse extends AbstractResponse {

    private OrderDto orderDto;
}
