/**
 * @program school-bus
 * @description: CountDetailResponse
 * @author: mf
 * @create: 2020/03/12 21:06
 */

package com.dream.bus.bus.param;

import com.dream.bus.param.AbstractResponse;
import lombok.Data;

@Data
public class CountDetailResponse extends AbstractResponse {
    private CountDetailDto countDetailDto;
}
