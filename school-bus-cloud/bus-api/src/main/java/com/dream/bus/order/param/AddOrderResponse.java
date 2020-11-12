/**
 * @program school-bus
 * @description: AddOrderResponse
 * @author: mf
 * @create: 2020/03/14 19:00
 */

package com.dream.bus.order.param;

import com.dream.bus.param.AbstractResponse;
import lombok.Data;

@Data
public class AddOrderResponse extends AbstractResponse {

    private OrderDto orderDto;

    private Long orderId;
}
