/**
 * @program school-bus
 * @description: PageBusCountRequest
 * @author: mf
 * @create: 2020/03/01 16:51
 */

package com.stylefeng.guns.rest.bus.dto;

import com.stylefeng.guns.rest.common.AbstractRequest;
import lombok.Data;

@Data
public class PageBusRequest extends AbstractRequest {

    // 当前页
    private Integer currentPage;
    // 每页数量
    private Integer pageSize;
}
