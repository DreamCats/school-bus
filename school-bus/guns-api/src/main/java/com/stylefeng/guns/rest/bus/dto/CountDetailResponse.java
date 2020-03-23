/**
 * @program school-bus
 * @description: CountDetailResponse
 * @author: mf
 * @create: 2020/03/12 21:06
 */

package com.stylefeng.guns.rest.bus.dto;

import com.stylefeng.guns.rest.common.AbstractResponse;
import lombok.Data;

@Data
public class CountDetailResponse extends AbstractResponse {
    private CountDetailDto countDetailDto;
}
