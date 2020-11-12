/**
 * @program school-bus-cloud
 * @description: IBusSerice
 * @author: mf
 * @create: 2020/11/08 13:20
 */
package com.dream.bus.service;

import com.dream.bus.bus.param.*;

public interface IBusService {

    /**
     * 分页获取班车
     * @param request
     * @return
     */
    PageBusResponse getBus(PageBusRequest request);

    /**
     * 按照系统当前分页获取场次
     * @param request
     * @return
     */
    PageCountResponse getCount(PageCountRequest request);

    /**
     * 根据场次id获取场次详情
     * @param request
     * @return
     */
    CountDetailResponse getCountDetailById(CountDetailRequest request);

    /**
     * 我懒了，这个方法就不想写request的形式了，本身参数不是特别多
     * 判断一下 传进来的座位， 是否和当前场次的座位中， 有没有重复的
     * @param seats
     * @param coundId
     * @return
     */
    boolean repeatSeats(String seats, Long coundId);

    /**
     * 更新座位信息
     * @param seats
     * @param coundId
     * @return
     */
    boolean addSeats(String seats, Long coundId);

    /**
     * 还原场次座位，去掉coundId中已出现seats的座位
     * @param seats
     * @param coundId
     * @return
     */
    boolean filterRepeatSeats(String seats, Long coundId);

    /**
     * 每天上午7点到晚上21点，每隔30分钟执行一次
     */
    void schedulChangeBusStatus();

    /**
     * 每天凌晨0点1分执行
     */
    void addCounts();

    /**
     * 每5s执行一次
     */
    void scheduledTest();
}