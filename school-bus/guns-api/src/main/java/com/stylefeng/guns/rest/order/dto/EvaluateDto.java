/**
 * @program school-bus
 * @description: EvaluateDto
 * @author: mf
 * @create: 2020/03/10 00:51
 */

package com.stylefeng.guns.rest.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvaluateDto {
    @ApiModelProperty(notes = "订单id")
    private Integer uuid;
    @ApiModelProperty(notes = "0：沙河->清水河，1：清水河->沙河")
    private String busStatus;
    @ApiModelProperty(notes = "已选座位")
    private String seatsIds;
    @ApiModelProperty(notes = "下单时间")
    private String orderTime;
    @ApiModelProperty(notes = "下单用户")
    private String orderUser;
    @ApiModelProperty(notes = "班车出发时间")
    private String beginTime;
    @ApiModelProperty(notes = "班车id")
    private Integer busId;
    @ApiModelProperty(notes = "0:未评价；1:已评价")
    private String evaluateStatus;
    @ApiModelProperty(notes = "评论内容")
    private String comment;
}
