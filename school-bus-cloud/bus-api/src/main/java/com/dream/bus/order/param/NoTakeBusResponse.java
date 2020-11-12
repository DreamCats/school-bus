/**
 * @program school-bus
 * @description: NoTakeBusResponse
 * @author: mf
 * @create: 2020/03/04 22:18
 */

package com.dream.bus.order.param;

import com.dream.bus.param.AbstractResponse;
import lombok.Data;

import java.util.List;

@Data
//@ApiModel("未乘坐订单Dto")
public class NoTakeBusResponse extends AbstractResponse {
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
//    @ApiModelProperty(notes = "未乘坐订单列表")
    List<NoTakeDto> noTakeDtos;
//    List<OrderDto> orderDtos;
}
