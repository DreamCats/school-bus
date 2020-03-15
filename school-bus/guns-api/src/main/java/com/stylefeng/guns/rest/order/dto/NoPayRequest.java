/**
 * @program school-bus
 * @description: NoPayRequest
 * @author: mf
 * @create: 2020/03/15 16:17
 */

package com.stylefeng.guns.rest.order.dto;

import com.stylefeng.guns.rest.common.AbstractRequest;
import lombok.Data;

@Data
public class NoPayRequest extends AbstractRequest {
    private Integer userId;
    // 当前页
    private Integer currentPage;
    // 每页数量
    private Integer pageSize;
}
