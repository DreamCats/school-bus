/**
 * @program school-bus
 * @description: OrderMQDto
 * @author: mf
 * @create: 2020/03/19 11:06
 */

package com.stylefeng.guns.rest.mq;

import lombok.Data;

import java.io.Serializable;

@Data
public class MQDto implements Serializable {
    private String orderId; // 订单id
    private Integer countId; // 场次id
    private Integer userId; // 用户id
    private Double userMoney; // 用户钱
    private String seatsIds; // 座位id
}
