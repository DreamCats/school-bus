/**
 * @program school-bus
 * @description: IBusService:班车服务
 * @author: mf
 * @create: 2020/03/01 16:21
 */
package com.stylefeng.guns.rest.bus;

import com.stylefeng.guns.rest.bus.dto.PageBusRequest;
import com.stylefeng.guns.rest.bus.dto.PageBusResponse;
import com.stylefeng.guns.rest.bus.dto.PageCountRequest;
import com.stylefeng.guns.rest.bus.dto.PageCountResponse;

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
}
