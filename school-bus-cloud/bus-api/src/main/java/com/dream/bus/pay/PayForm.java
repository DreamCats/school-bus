/**
 * @program school-bus
 * @description: PayForm
 * @author: mf
 * @create: 2020/03/17 16:56
 */

package com.dream.bus.pay;

import lombok.Data;

//@ApiModel(value = "支付实体", description = "支付请求信息")
@Data
public class PayForm {
//    @ApiModelProperty(value = "支付密码", required = true) // 这里值得优化
    private String payPassword;
//    @ApiModelProperty(value = "总价格，直接传钱，感觉不安全", required = true) // 这里值得优化
    private Double totalMoney; // 直接传钱， 感觉不安全
}
