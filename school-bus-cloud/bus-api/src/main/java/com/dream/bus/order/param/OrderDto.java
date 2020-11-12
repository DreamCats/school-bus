/**
 * @program school-bus
 * @description: OrderDto
 * @author: mf
 * @create: 2020/03/04 22:10
 */

package com.dream.bus.order.param;


import lombok.Data;

import java.io.Serializable;

@Data
//@ApiModel(description = "订单展示实体")
public class OrderDto implements Serializable {

//    @ApiModelProperty(notes = "订单id")
    private Long uuid;
//    @ApiModelProperty(notes = "场次id")
    private Long countId;
//    @ApiModelProperty(notes = "车牌号")
    private Long busId;
//    @ApiModelProperty(notes = "0：沙河->清水河，1：清水河->沙河")
    private String busStatus;
//    @ApiModelProperty(notes = "已选座位")
    private String seatsIds;
//    @ApiModelProperty(notes = "场次价格")
    private Double countPrice;
//    @ApiModelProperty(notes = "订单总价格")
    private Double orderPrice;
//    @ApiModelProperty(notes = "发车日期")
    private String beginDate;
//    @ApiModelProperty(notes = "发车时间")
    private String beginTime;
//    @ApiModelProperty(notes = "下单用户")
    private String orderUser;
//    @ApiModelProperty(notes = "订单状态，0-待支付,1-已支付,2-已关闭")
    private String orderStatus;
//    @ApiModelProperty(notes = "下单时间")
    private String orderTime;
}

