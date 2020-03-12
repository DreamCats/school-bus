/**
 * @program school-bus
 * @description: CountDetailDto
 * @author: mf
 * @create: 2020/03/12 20:50
 */

package com.stylefeng.guns.rest.bus.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(description = "场次详情Dto")
@Data
public class CountDetailDto implements Serializable {

    @ApiModelProperty(notes = "场次id")
    private Integer uuid;
    @ApiModelProperty(notes = "班车id")
    private Integer busId;
    @ApiModelProperty(notes = "司机姓名")
    private String driverName;
    @ApiModelProperty(notes = "0：沙河；1：清水河")
    private String bus_status;
    @ApiModelProperty(notes = "班车出发时间")
    private String beginTime;
    @ApiModelProperty(notes = "已选座位")
    private String selectedSeats;

}
