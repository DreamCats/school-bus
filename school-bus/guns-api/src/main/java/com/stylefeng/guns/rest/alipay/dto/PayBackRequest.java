/**
 * @program school-bus
 * @description: PayBackRequest
 * @author: mf
 * @create: 2020/03/20 16:14
 */

package com.stylefeng.guns.rest.alipay.dto;

import com.stylefeng.guns.rest.common.AbstractRequest;
import lombok.Data;

@Data
public class PayBackRequest extends AbstractRequest {
    private String orderId;
    private Integer coundId;
    private Integer userId;
    private String seatsIds;
    private String totalMoney;
}
