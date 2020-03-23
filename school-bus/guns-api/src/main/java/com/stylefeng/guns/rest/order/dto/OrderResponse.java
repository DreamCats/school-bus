/**
 * @program school-bus
 * @description: OrderResponse
 * @author: mf
 * @create: 2020/03/15 03:11
 */

package com.stylefeng.guns.rest.order.dto;

import com.stylefeng.guns.rest.common.AbstractResponse;
import lombok.Data;

@Data
public class OrderResponse extends AbstractResponse {

    private OrderDto orderDto;
}
