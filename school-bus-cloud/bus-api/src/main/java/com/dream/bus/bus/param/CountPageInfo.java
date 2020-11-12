/**
 * @program school-bus
 * @description: PageInfo
 * @author: mf
 * @create: 2020/03/01 22:58
 */

package com.dream.bus.bus.param;

import lombok.Data;

@Data
//@ApiModel(value = "分页实体", description = "分页请求信息")
public class CountPageInfo {

//    @ApiModelProperty(name = "currentPage", value = "当前页", example = "1", dataType = "long", required = true)
    private Long currentPage;

//    @ApiModelProperty(name = "pageSize", value = "每页的条目数量", example = "4", dataType = "long", required = true)
    private Long pageSize;

//    @ApiModelProperty(name = "busStatus", value = "0:沙河->清水河，1:清水河->沙河", example = "0", dataType = "String", required = true)
    private String busStatus;
}
