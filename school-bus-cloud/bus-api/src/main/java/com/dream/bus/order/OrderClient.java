/**
 * @program school-bus-cloud
 * @description: OrdClient
 * @author: mf
 * @create: 2020/11/08 14:45
 */
package com.dream.bus.order;

import com.dream.bus.order.param.OrderRequest;
import com.dream.bus.order.param.OrderResponse;
import com.dream.bus.user.param.UserUpdateInfoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "bus-order")
public interface OrderClient {

    @PostMapping("user/localUpdateOrderStatus")
    OrderResponse localUpdateOrderStatus(@RequestBody OrderRequest request);
}
