/**
 * @program school-bus
 * @description: NoTakeBusRequest
 * @author: mf
 * @create: 2020/03/04 22:16
 */

package com.dream.bus.order.param;

import com.dream.bus.param.AbstractRequest;
import lombok.Data;

@Data
public class NoTakeBusRequest extends AbstractRequest {
    private Long userId;
    // 当前页
    private Long currentPage;
    // 每页数量
    private Long pageSize;
}
