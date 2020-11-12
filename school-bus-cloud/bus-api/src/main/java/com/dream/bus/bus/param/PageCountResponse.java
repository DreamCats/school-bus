/**
 * @program school-bus
 * @description: PageCountResponse
 * @author: mf
 * @create: 2020/03/02 12:51
 */

package com.dream.bus.bus.param;


import com.dream.bus.param.AbstractResponse;
import lombok.Data;

import java.util.List;

@Data
//@ApiModel(description = "车次列表Dto")
public class PageCountResponse extends AbstractResponse {
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
    // count
//    @ApiModelProperty(notes = "场次列表")
    List<CountSimpleDto> countSimpleDtos;
//    List<CountDto> countDtos;

}
