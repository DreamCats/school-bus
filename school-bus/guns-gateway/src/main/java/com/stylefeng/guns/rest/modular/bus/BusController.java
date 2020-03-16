/**
 * @program school-bus
 * @description: BusController
 * @author: mf
 * @create: 2020/03/01 22:56
 */

package com.stylefeng.guns.rest.modular.bus;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.bus.IBusService;
import com.stylefeng.guns.rest.bus.dto.*;
import com.stylefeng.guns.rest.common.ResponseData;
import com.stylefeng.guns.rest.common.ResponseUtil;
import com.stylefeng.guns.rest.modular.form.CountPageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "班车服务", description = "班车服务相关接口")
@RestController
@RequestMapping("/bus/")
public class BusController {

    @Reference
    private IBusService busService;


    @ApiOperation(value = "获取车次列表", notes = "获取车次列表", response = PageCountResponse.class)
    @GetMapping("getCount")
    public ResponseData getCount(CountPageInfo pageInfo) {
        PageCountRequest request = new PageCountRequest();
        request.setCurrentPage(pageInfo.getCurrentPage());
        request.setPageSize(pageInfo.getPageSize());
        request.setBusStatus(pageInfo.getBusStatus());
        PageCountResponse response = busService.getCount(request);
        log.info("getCount", response);
        return new ResponseUtil().setData(response);
    }

    @ApiOperation(value = "获取车次详情", notes = "获取车次详情", response = CountDetailResponse.class)
    @ApiImplicitParam(name = "countId", value = "场次id,CountSimpleDto中的uuid",required = true, dataType = "String", paramType = "query")
    @GetMapping("getCountDetail")
    public ResponseData getCountDetailById(String countId) {
        CountDetailRequest request = new CountDetailRequest();
        request.setCountId(Integer.parseInt(countId));
        CountDetailResponse response = busService.getCountDetailById(request);
        log.info("getCountDetailById", response);
        return new ResponseUtil().setData(response);
    }
}
