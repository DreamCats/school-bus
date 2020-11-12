/**
 * @program school-bus
 * @description: PayBackForm
 * @author: mf
 * @create: 2020/03/20 17:39
 */

package com.dream.bus.pay;

import lombok.Data;

//@ApiModel(value = "退款实体", description = "退款请求信息")
@Data
public class PayBackForm {

//    @ApiModelProperty(value = "订单id", required = true)
    private Long orderId;
//    @ApiModelProperty(value = "场次id", required = true)
    private Long coundId;
//    @ApiModelProperty(value = "座位id", required = true)
    private String seatsIds;
//    @ApiModelProperty(value = "总金额", required = true)
    private Double totalMoney;
}
