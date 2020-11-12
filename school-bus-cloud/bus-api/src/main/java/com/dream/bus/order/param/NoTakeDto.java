/**
 * @program school-bus
 * @description: NoTakeDto
 * @author: mf
 * @create: 2020/03/09 22:25
 */

package com.dream.bus.order.param;

import lombok.Data;

import java.io.Serializable;

@Data
//@ApiModel(description = "未乘坐订单简单Dto")
public class NoTakeDto implements Serializable {
//    @ApiModelProperty(notes = "订单id")
    private Long uuid;
//    @ApiModelProperty(notes = "0：沙河->清水河，1：清水河->沙河")
    private String busStatus;
//    @ApiModelProperty(notes = "已选座位")
    private String seatsIds;
//    @ApiModelProperty(notes = "下单时间")
    private String orderTime;
//    @ApiModelProperty(notes = "下单用户")
    private String orderUser;
//    @ApiModelProperty(notes = "订单状态，0-待支付,1-已支付,2-已关闭")
    private String orderStatus;
//    @ApiModelProperty(notes = "班车出发时间")
    private String beginTime;
//    @ApiModelProperty(notes = "班车id")
    private Long busId;
}
