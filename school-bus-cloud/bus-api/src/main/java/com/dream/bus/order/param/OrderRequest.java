/**
 * @program school-bus
 * @description: OrderRequest
 * @author: mf
 * @create: 2020/03/15 03:10
 */

package com.dream.bus.order.param;

import com.dream.bus.param.AbstractRequest;
import lombok.Data;

@Data
public class OrderRequest extends AbstractRequest {

    private Long uuid;

    private String orderStatus;
}
