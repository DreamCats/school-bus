/**
 * @program school-bus
 * @description: CountSimpleDto
 * @author: mf
 * @create: 2020/03/10 01:13
 */

package com.dream.bus.bus.param;

import lombok.Data;

import java.io.Serializable;

@Data
//@ApiModel(description = "车次列表的Dto")
public class CountSimpleDto implements Serializable {
//    @ApiModelProperty(notes = "场次id")
    private Long uuid;
//    @ApiModelProperty(notes = "班车id")
    private Long busId;
//    @ApiModelProperty(notes = "班车出发时间")
    private String beginTime;
//    @ApiModelProperty(notes = "0：沙河；1：清水河；2：沙河到清水河；3：清水河到沙河")
    private String busStatus;
//    @ApiModelProperty(notes = "出发日期")
    private String beginDate;
//    @ApiModelProperty(notes = "0:未满；1:已满")
    private String seatStatus;
}
