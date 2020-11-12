/**
 * @program school-bus
 * @description: CountDto
 * @author: mf
 * @create: 2020/03/02 12:48
 */

package com.dream.bus.bus.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class CountDto implements Serializable {
//    @ApiModelProperty(notes = "场次id")
    private Long uuid;
//    @ApiModelProperty(notes = "班车id")
    private Long busId;
//    @ApiModelProperty(notes = "班车出发时间")
    private String beginTime;
//    @ApiModelProperty(notes = "班车结束时间")
    private String endTime;
//    @ApiModelProperty(notes = "0：沙河；1：清水河；2：沙河到清水河；3：清水河到沙河")
    private String bus_status;
//    @ApiModelProperty(notes = "场次价格")
    private Double price;
//    @ApiModelProperty(notes = "已选座位")
    private String selectedSeats;
//    @ApiModelProperty(notes = "0:未满；1:已满")
    private String seatStatus;
//    @ApiModelProperty(notes = "出发日期")
    private String beginDate;
//    @ApiModelProperty(notes = "更新时间")
    private String updateTime;

}
