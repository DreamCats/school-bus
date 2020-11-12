/**
 * @program school-bus
 * @description: CountDetailRequest
 * @author: mf
 * @create: 2020/03/12 21:07
 */

package com.dream.bus.bus.param;

import com.dream.bus.param.AbstractRequest;
import lombok.Data;


@Data
public class CountDetailRequest extends AbstractRequest {
    // 场次id
    private Long countId;
}
