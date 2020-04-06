/**
 * @program school-bus
 * @description: OrderRequest
 * @author: mf
 * @create: 2020/03/15 03:10
 */

package com.stylefeng.guns.rest.order.dto;

import com.stylefeng.guns.rest.common.AbstractRequest;
import lombok.Data;

@Data
public class OrderRequest extends AbstractRequest {

    private Long uuid;

    private String orderStatus;
}
