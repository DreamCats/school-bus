/**
 * @program school-bus
 * @description: OrderPageInfo
 * @author: mf
 * @create: 2020/03/15 16:37
 */

package com.dream.bus.order.param;

import lombok.Data;

@Data
//@ApiModel(value = "分页实体", description = "分页请求信息")
public class OrderPageInfo {
//    @ApiModelProperty(name = "currentPage", value = "当前页", example = "1", dataType = "long", required = true)
    private Long currentPage;

//    @ApiModelProperty(name = "pageSize", value = "每页的条目数量", example = "4", dataType = "long", required = true)
    private Long pageSize;
}
