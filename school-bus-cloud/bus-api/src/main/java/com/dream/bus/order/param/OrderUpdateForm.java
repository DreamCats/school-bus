/**
 * @program school-bus
 * @description: OrderUpdateForm
 * @author: mf
 * @create: 2020/04/06 17:14
 */

package com.dream.bus.order.param;

import lombok.Data;

//@ApiModel(value = "更新实体", description = "更新请求信息")
@Data
public class OrderUpdateForm {

//    @ApiModelProperty(name = "orderId", value = "订单id", example = "1", required = true, dataType = "Long")
    private Long OrderId;
//    @ApiModelProperty(name = "orderStatus", value = "订单装填", example = "1", required = true, dataType = "String")
    private String orderStatus;
}
