/**
 * @program school-bus
 * @description: AddOrderRequest
 * @author: mf
 * @create: 2020/03/14 18:47
 */

package com.stylefeng.guns.rest.order.dto;

import com.stylefeng.guns.rest.common.AbstractRequest;
import lombok.Data;

@Data
public class AddOrderRequest extends AbstractRequest {
    // 场次id
    private Integer countId;
    // 用户id
    private Integer userId;
    // 班车id
    private Integer busId;
    private String orderUser;
    private String busStatus;
    private String seatsIds;
}
