/**
 * @program school-bus
 * @description: AddOrderRequest
 * @author: mf
 * @create: 2020/03/14 18:47
 */

package com.dream.bus.order.param;

import com.dream.bus.param.AbstractRequest;
import lombok.Data;

@Data
public class AddOrderRequest extends AbstractRequest {
    // 场次id
    private Long countId;
    // 用户id
    private Long userId;
    private String orderUser;
    private String busStatus;
    private String seatsIds;
    private Double countPrice;
    private Integer expireTime;
}
