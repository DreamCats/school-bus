/**
 * @program school-bus
 * @description: PageInfo
 * @author: mf
 * @create: 2020/03/01 22:58
 */

package com.stylefeng.guns.rest.modular.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PageInfo {

    @ApiModelProperty(name = "currentPage", value = "当前页", example = "1", dataType = "int", required = true)
    private Integer currentPage;

    @ApiModelProperty(name = "pageSize", value = "每页的条目数量", example = "4", dataType = "int", required = true)
    private Integer pageSize;
}
