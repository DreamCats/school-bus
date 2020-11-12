/**
 * @program school-bus-cloud
 * @description: OrderConvertver
 * @author: mf
 * @create: 2020/11/08 16:21
 */

package com.dream.bus.common.converter;

import com.dream.bus.convert.DateMapper;
import com.dream.bus.model.Order;
import com.dream.bus.order.param.AddOrderRequest;
import com.dream.bus.order.param.OrderDto;
import com.dream.bus.order.param.OrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface OrderConvertver {

    @Mappings({})
    List<OrderDto> order2Res(List<Order> orders);

    @Mappings({})
    Order res2Order(AddOrderRequest request);

    @Mappings({})
    OrderDto order2Res(Order order);

    @Mappings({})
    Order res2Order(OrderRequest request);
}