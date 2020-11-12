/**
 * @program school-bus
 * @description: NoPayResponse
 * @author: mf
 * @create: 2020/03/15 16:18
 */

package com.dream.bus.order.param;

import com.dream.bus.param.AbstractResponse;
import lombok.Data;

import java.util.List;

@Data
public class NoPayResponse extends AbstractResponse {
    // 当前页
//    @ApiModelProperty(notes = "当前页")
    private Long currentPage;
    // 每页数量
//    @ApiModelProperty(notes = "每页数量")
    private Long pageSize;
    // 总量
//    @ApiModelProperty(notes = "总量")
    private Long total;
    // 总页
//    @ApiModelProperty(notes = "总页")
    private Long pages;
//    @ApiModelProperty(notes = "未支付订单列表")
    private List<NoPayDto> noPayDtos;
}
