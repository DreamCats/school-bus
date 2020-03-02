/**
 * @program school-bus
 * @description: CountDto
 * @author: mf
 * @create: 2020/03/02 12:48
 */

package com.stylefeng.guns.rest.bus.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CountDto implements Serializable {

    private Integer uuid;
    private Integer busId;
    private String beginTime;
    private String endTime;
    private String bus_status;
    private Double price;
    private String selectedSeats;
    private String seatStatus;

}
