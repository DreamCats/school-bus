/**
 * @program school-bus
 * @description: PageBusCountResponse
 * @author: mf
 * @create: 2020/03/01 16:54
 */

package com.dream.bus.bus.param;


import com.dream.bus.param.AbstractResponse;
import lombok.Data;

import java.util.List;

@Data
//@ApiModel(description = "班车Dto")
public class PageBusResponse extends AbstractResponse {

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
    // BusDto
//    @ApiModelProperty(notes = "班车列表")
    private List<BusDto> busDtos;

}
