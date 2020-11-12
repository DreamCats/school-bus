/**
 * @program school-bus-cloud
 * @description: BusClient
 * @author: mf
 * @create: 2020/11/08 13:22
 */
package com.dream.bus.bus;

import com.dream.bus.param.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "bus-bus")
public interface BusClient {

    @GetMapping("/bus/repeatSeats")
    boolean repeatSeats(@RequestParam String seats, @RequestParam Long coundId);

    @GetMapping("/bus/addSeats")
    boolean addSeats(@RequestParam String seats, @RequestParam Long coundId);

    @GetMapping("/bus/filterRepeatSeats")
    boolean filterRepeatSeats(@RequestParam String seats, @RequestParam Long coundId);
}
