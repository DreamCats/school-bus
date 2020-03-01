/**
 * @program school-bus
 * @description: BusVO
 * @author: mf
 * @create: 2020/03/01 16:42
 */

package com.stylefeng.guns.rest.bus.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BusVo implements Serializable {

    private Integer uuid;
    private String limitNumber;
    private String driverName;
    private String driverPhone;
    private String seatsNumber;
    private String beginTime;
    private String updateTime;

}
