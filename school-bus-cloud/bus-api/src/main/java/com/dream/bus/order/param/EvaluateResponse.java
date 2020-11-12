/**
 * @program school-bus
 * @description: EvaluateResponse
 * @author: mf
 * @create: 2020/03/06 02:40
 */

package com.dream.bus.order.param;

import com.dream.bus.param.AbstractResponse;
import lombok.Data;

import java.util.List;

@Data
//@ApiModel("订单评价列表Dto")
public class EvaluateResponse extends AbstractResponse {
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
//    @ApiModelProperty(notes = "订单列表")
//    List<OrderDto> orderDtos;
    List<EvaluateDto> evaluateDtos;
}
