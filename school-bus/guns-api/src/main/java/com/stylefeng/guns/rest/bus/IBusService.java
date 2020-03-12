/**
 * @program school-bus
 * @description: IBusService:班车服务
 * @author: mf
 * @create: 2020/03/01 16:21
 */
package com.stylefeng.guns.rest.bus;

import com.stylefeng.guns.rest.bus.dto.*;

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
}
