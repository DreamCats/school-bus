/**
 * @program school-bus
 * @description: OrderDto
 * @author: mf
 * @create: 2020/03/04 22:10
 */

package com.stylefeng.guns.rest.order.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDto implements Serializable {

    private Integer uuid;
    private Integer countId;
    private String busStatus;
    private String seatsIds;
    private Double countPrice;
    private Double orderPrice;
    private String orderTime;
    private Integer orderUser;
    private String orderStatus;
    private String evaluateStatus;
    private String comment;
}

