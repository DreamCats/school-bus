/**
 * @program school-bus
 * @description: PayBackRequest
 * @author: mf
 * @create: 2020/03/20 16:14
 */

package com.dream.bus.pay;

import com.dream.bus.param.AbstractRequest;
import lombok.Data;

@Data
public class PayBackRequest extends AbstractRequest {
    private Long orderId;
    private Long coundId;
    private Long userId;
    private String seatsIds;
    private Double totalMoney;
}
