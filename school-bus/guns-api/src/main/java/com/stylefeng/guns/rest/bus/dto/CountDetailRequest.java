/**
 * @program school-bus
 * @description: CountDetailRequest
 * @author: mf
 * @create: 2020/03/12 21:07
 */

package com.stylefeng.guns.rest.bus.dto;

import com.stylefeng.guns.rest.common.AbstractRequest;
import lombok.Data;


@Data
public class CountDetailRequest extends AbstractRequest {
    // 场次id
    private Long countId;
}
