/**
 * @program school-bus
 * @description: BusController
 * @author: mf
 * @create: 2020/03/01 22:56
 */

package com.stylefeng.guns.rest.modular.bus;

import cn.hutool.core.convert.Convert;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
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
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "班车服务", description = "班车服务相关接口")
@RestController
@RequestMapping("/bus/")
public class BusController {

    @Reference(check = false)
    private IBusService busService;
    @Autowired
    private RedisUtils redisUtils;


    @ApiOperation(value = "获取车次列表", notes = "获取车次列表", response = PageCountResponse.class)
    @GetMapping("getCount")
    @SentinelResource(value = "getCount", blockHandler = "countBlockHandler",fallback = "countFallbackHandler")
    public ResponseData getCount(CountPageInfo pageInfo) {
        // 本来想用本地缓存的，试试redis吧  第一种方案
        try {
            String busStatus = pageInfo.getBusStatus();
            Long currentPage = pageInfo.getCurrentPage();
            String key = RedisConstants.COUNTS_EXPIRE.getKey() + busStatus + currentPage;
            if (redisUtils.hasKey(key)) {
                Object obj = redisUtils.get(key);
                log.warn("getCount->redis\n");
                return new ResponseUtil().setData(obj);
            }

            PageCountRequest request = new PageCountRequest();
            request.setCurrentPage(pageInfo.getCurrentPage());
            request.setPageSize(pageInfo.getPageSize());
            request.setBusStatus(pageInfo.getBusStatus());
            PageCountResponse response = busService.getCount(request);
            redisUtils.set(key, response, RedisConstants.COUNTS_EXPIRE.getTime());
            // 更新场次列表的页数缓存
            Long countPages = response.getPages();
            String countPagesKey = RedisConstants.COUNTS_PAGES_EXPIRE.getKey() + request.getBusStatus();
            // 肯定存在，就不判断了
            Long countPagesRedis = Convert.toLong(redisUtils.get(countPagesKey));
            if (!countPages.equals(countPagesRedis)) {
                // 如果不相等，更新
                redisUtils.set(countPagesKey, countPages); // 将response的总页数更新到缓存
            }
            log.warn("getCount\n");
            return new ResponseUtil().setData(response);
        } catch (Exception e) {
            log.error("getCount\n", e);
            CommonResponse response = new CommonResponse();
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return new ResponseUtil().setData(response);
        }
    }

    /**
     * 限流异常
     * @param pageInfo
     * @param exception
     * @return
     */
    public ResponseData countBlockHandler(CountPageInfo pageInfo, BlockException exception) {
        CommonResponse response = new CommonResponse();
        response.setCode(SbCode.FLOW_ERROR.getCode());
        response.setMsg(SbCode.FLOW_ERROR.getMessage());
        return new ResponseUtil().setData(response);
    }

    /**
     * 降级异常， 若同时设置了限流和降级， 则降级异常会进入限流自定义异常去...
     * @param pageInfo
     * @return
     */
    public ResponseData countFallbackHandler(CountPageInfo pageInfo) {
        CommonResponse response = new CommonResponse();
        response.setCode(SbCode.DEGRADE_ERROR.getCode());
        response.setMsg(SbCode.DEGRADE_ERROR.getMessage());
        return new ResponseUtil().setData(response);
    }

    @ApiOperation(value = "获取车次详情", notes = "获取车次详情", response = CountDetailResponse.class)
    @ApiImplicitParam(name = "countId", value = "场次id,CountSimpleDto中的uuid",required = true, dataType = "Long", paramType = "query")
    @GetMapping("getCountDetail")
    @SentinelResource(value = "getCountDetail", blockHandler = "countDetailBlockHandler", fallback = "countDetailFallbackHandler")
    public ResponseData d(Long countId) {
        // id 从本队缓存中取
        try {
            String key = RedisConstants.COUNT_DETAIL_EXPIRE.getKey()+countId;
            if (redisUtils.hasKey(key)) {
                Object obj = redisUtils.get(key);
                log.warn("getCountDetailById->redis\n");
                return new ResponseUtil().setData(obj);
            }
            CountDetailRequest request = new CountDetailRequest();
            request.setCountId(countId);
            CountDetailResponse response = busService.getCountDetailById(request);
            redisUtils.set(key, response, RedisConstants.COUNT_DETAIL_EXPIRE.getTime());
            log.warn("getCountDetailById\n");
            return new ResponseUtil().setData(response);
        } catch (Exception e) {
            log.error("getCountDetailById\n", e);
            CommonResponse response = new CommonResponse();
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return new ResponseUtil().setData(response);
        }
    }

    /**
     *
     * @param countId
     * @param exception
     * @return
     */
    public ResponseData countDetailBlockHandler(String countId, BlockException exception) {
        CommonResponse response = new CommonResponse();
        response.setCode(SbCode.FLOW_ERROR.getCode());
        response.setMsg(SbCode.FLOW_ERROR.getMessage());
        return new ResponseUtil().setData(response);
    }

    /**
     *
     * @param countId
     * @return
     */
    public ResponseData countDetailFallbackHandler(String countId) {
        CommonResponse response = new CommonResponse();
        response.setCode(SbCode.DEGRADE_ERROR.getCode());
        response.setMsg(SbCode.DEGRADE_ERROR.getMessage());
        return new ResponseUtil().setData(response);
    }
}
