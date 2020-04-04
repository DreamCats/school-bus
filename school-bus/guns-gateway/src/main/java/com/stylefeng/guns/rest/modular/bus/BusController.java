/**
 * @program school-bus
 * @description: BusController
 * @author: mf
 * @create: 2020/03/01 22:56
 */

package com.stylefeng.guns.rest.modular.bus;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.core.constants.SbCode;
import com.stylefeng.guns.rest.bus.IBusService;
import com.stylefeng.guns.rest.bus.dto.*;
import com.stylefeng.guns.rest.common.RedisUtils;
import com.stylefeng.guns.rest.common.ResponseData;
import com.stylefeng.guns.rest.common.ResponseUtil;
import com.stylefeng.guns.core.constants.RedisConstants;
import com.stylefeng.guns.rest.exception.CommonResponse;
import com.stylefeng.guns.rest.modular.form.CountPageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private RedisUtils redisUtils;


    @ApiOperation(value = "获取车次列表", notes = "获取车次列表", response = PageCountResponse.class)
    @GetMapping("getCount")
    public ResponseData getCount(CountPageInfo pageInfo) {
        // 本来想用本地缓存的，试试redis吧  第一种方案
        String busStatus = pageInfo.getBusStatus();
        Integer currentPage = pageInfo.getCurrentPage();
        String key = RedisConstants.COUNTS_EXPIRE.getKey() + busStatus;
        try {
            // 在这加一个锁 , 效率极其的慢

            if (redisUtils.hasKey(key)) {
                Object obj = redisUtils.lGetIndex(key, currentPage - 1);
                if (null != obj) {
                    log.warn("getCount->redis\n");
                    return new ResponseUtil().setData(obj);
                }
            }

            PageCountRequest request = new PageCountRequest();
            request.setCurrentPage(pageInfo.getCurrentPage());
            request.setPageSize(pageInfo.getPageSize());
            request.setBusStatus(pageInfo.getBusStatus());
            PageCountResponse response = busService.getCount(request);
            // 写缓存
            // 这里高并发模拟有问题， 虽然上面判断了， 但是并发情况，依然来这里了，
            if (!redisUtils.hasKey(key)) {
                synchronized (this) {
                    Object obj = redisUtils.lGetIndex(key, currentPage - 1);
                    if (null == obj) {
                        redisUtils.lSet(key, response, RedisConstants.COUNTS_EXPIRE.getTime());
                    }
                }
            }
            log.warn("getCount\n");
            return new ResponseUtil().setData(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getCount\n");
            CommonResponse response = new CommonResponse();
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return new ResponseUtil().setData(response);
        }

    }

    @ApiOperation(value = "获取车次详情", notes = "获取车次详情", response = CountDetailResponse.class)
    @ApiImplicitParam(name = "countId", value = "场次id,CountSimpleDto中的uuid",required = true, dataType = "String", paramType = "query")
    @GetMapping("getCountDetail")
    public ResponseData getCountDetailById(String countId) {
        // id 从本队缓存中取
        Object obj = redisUtils.get(RedisConstants.COUNT_DETAIL_EXPIRE.getKey()+countId);
        if (obj != null) {
            log.warn("getCountDetailById->redis:" + obj.toString());
            return new ResponseUtil().setData(obj);
        }
        CountDetailRequest request = new CountDetailRequest();
        request.setCountId(Integer.parseInt(countId));
        CountDetailResponse response = busService.getCountDetailById(request);
        redisUtils.set(RedisConstants.COUNT_DETAIL_EXPIRE.getKey()+countId, response, RedisConstants.COUNT_DETAIL_EXPIRE.getTime());
        log.warn("getCountDetailById:" + response.toString());
        return new ResponseUtil().setData(response);
    }
}
