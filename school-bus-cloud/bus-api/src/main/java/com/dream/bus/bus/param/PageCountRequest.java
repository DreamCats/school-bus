/**
 * @program school-bus
 * @description: PageCountRequest
 * @author: mf
 * @create: 2020/03/02 12:48
 */

package com.dream.bus.bus.param;

import com.dream.bus.param.AbstractRequest;
import lombok.Data;

@Data
public class PageCountRequest extends AbstractRequest {

    // 当前页
    private Long currentPage;
    // 每页数量
    private Long pageSize;
    // 地区状态
    private String busStatus;
}
