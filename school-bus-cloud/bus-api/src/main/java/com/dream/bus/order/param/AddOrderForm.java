/**
 * @program school-bus
 * @description: AddOrderForm
 * @author: mf
 * @create: 2020/03/15 03:01
 */

package com.dream.bus.order.param;

import lombok.Data;

//@ApiModel(value = "更新实体", description = "更新请求信息")
@Data
public class AddOrderForm {
//    @ApiModelProperty(value = "场次id", required = true)
    private Long countId;
//    @ApiModelProperty(value = "下单人", required = true)
    private String orderUser;
//    @ApiModelProperty(value = "0:沙河->清水河；1:清水河->沙河", required = true)
    private String busStatus;
//    @ApiModelProperty(value = "已选座位编号，例如：1,2", required = true)
    private String seatsIds;
//    @ApiModelProperty(value = "场次价格", required = true)
    private Double countPrice;
//    @ApiModelProperty(value = "订单过期时间", required = true)
    private Integer expireTime;
}
