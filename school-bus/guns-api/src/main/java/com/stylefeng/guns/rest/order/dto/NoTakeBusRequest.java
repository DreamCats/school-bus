/**
 * @program school-bus
 * @description: NoTakeBusRequest
 * @author: mf
 * @create: 2020/03/04 22:16
 */

package com.stylefeng.guns.rest.order.dto;

import com.stylefeng.guns.rest.common.AbstractRequest;
import lombok.Data;

@Data
public class NoTakeBusRequest extends AbstractRequest {
    private Integer userId;
    // 当前页
    private Integer currentPage;
    // 每页数量
    private Integer pageSize;
}
