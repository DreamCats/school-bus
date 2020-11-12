/**
 * @program school-bus
 * @description: EvaluateRequest
 * @author: mf
 * @create: 2020/03/06 02:39
 */

package com.dream.bus.order.param;

import com.dream.bus.param.AbstractRequest;
import lombok.Data;

@Data
public class EvaluateRequest extends AbstractRequest {
    private Long userId;
    // 评价状态
    private String evaluateStatus;
    // 当前页
    private Long currentPage;
    // 每页数量
    private Long pageSize;
}
