/**
 * @program school-bus
 * @description: CountDetailDto
 * @author: mf
 * @create: 2020/03/12 20:50
 */

package com.dream.bus.bus.param;

import lombok.Data;

import java.io.Serializable;

//@ApiModel(description = "场次详情Dto")
@Data
public class CountDetailDto implements Serializable {

//    @ApiModelProperty(notes = "场次id")
    private Long uuid;
//    @ApiModelProperty(notes = "班车id")
    private Long busId;
//    @ApiModelProperty(notes = "司机姓名")
    private String driverName;
//    @ApiModelProperty(notes = "0：沙河；1：清水河")
    private String busStatus;
//    @ApiModelProperty(notes = "班车出发时间")
    private String beginTime;
//    @ApiModelProperty(notes = "出发日期")
    private String beginDate;
//    @ApiModelProperty(notes = "已选座位")
    private String selectedSeats;
//    @ApiModelProperty(notes = "座位路径")
    private String seatsNumber;
//    @ApiModelProperty(notes = "场次价格")
    private Double price;

}
