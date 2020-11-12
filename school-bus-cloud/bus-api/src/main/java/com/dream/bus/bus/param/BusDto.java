/**
 * @program school-bus
 * @description: BusVO
 * @author: mf
 * @create: 2020/03/01 16:42
 */

package com.dream.bus.bus.param;


import lombok.Data;

import java.io.Serializable;

@Data
//@ApiModel(description = "班车表现实体")
public class BusDto implements Serializable {
//    @ApiModelProperty(notes = "班车id")
    private Long uuid;
//    @ApiModelProperty(notes = "座位人数")
    private String limitNumber;
//    @ApiModelProperty(notes = "司机姓名")
    private String driverName;
//    @ApiModelProperty(notes = "司机号码")
    private String driverPhone;
//    @ApiModelProperty(notes = "座位路径")
    private String seatsNumber;
//    @ApiModelProperty(notes = "创建时间")
    private String beginTime;
//    @ApiModelProperty(notes = "更新时间")
    private String updateTime;

}
