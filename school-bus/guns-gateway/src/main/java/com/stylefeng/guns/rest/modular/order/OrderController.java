/**
 * @program school-bus
 * @description: OrderController
 * @author: mf
 * @create: 2020/03/04 21:59
 */

package com.stylefeng.guns.rest.modular.order;

import cn.hutool.core.convert.Convert;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.bus.IBusService;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.common.RedisUtils;
import com.stylefeng.guns.rest.common.ResponseData;
import com.stylefeng.guns.rest.common.ResponseUtil;
import com.stylefeng.guns.core.constants.RedisConstants;
import com.stylefeng.guns.core.constants.SbCode;
import com.stylefeng.guns.rest.exception.CommonResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.form.AddOrderForm;
import com.stylefeng.guns.rest.modular.form.OrderPageInfo;
import com.stylefeng.guns.rest.modular.form.OrderUpdateForm;
import com.stylefeng.guns.rest.order.IOrderService;
import com.stylefeng.guns.rest.order.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(value = "订单服务", description = "订单服务相关接口")
@RestController
@RequestMapping("/order/")
public class OrderController {

    @Reference(check = false)
    private IOrderService orderService;
    @Reference(check = false)
    private IBusService busService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 获取未乘坐订单接口
     * @param pageInfo：去相关类查看参数
     * @param req：目的是获取token
     * @return
     */
    @ApiOperation(value = "获取未乘坐订单接口", notes = "前提Auth，获取用户订单未乘坐服务", response = NoTakeBusResponse.class)
    @GetMapping("getNoTakeOrders")
    @SentinelResource("getNoTakeOrders")
    public ResponseData getNoTakeOrdersById(OrderPageInfo pageInfo, HttpServletRequest req) {
        try {
            String token = CurrentUser.getToken(req);
            String userId = jwtTokenUtil.getUsernameFromToken(token);
            String key = RedisConstants.NO_TAKE_OREDERS_EXPIRE.getKey()+userId;
            NoTakeBusRequest request = new NoTakeBusRequest();
            request.setUserId(Convert.toLong(userId));
            request.setCurrentPage(pageInfo.getCurrentPage());
            request.setPageSize(pageInfo.getPageSize());
            if (redisUtils.hasKey(key)) {
                Object obj = redisUtils.get(key);
                // 判断发车时间
                NoTakeBusResponse response = (NoTakeBusResponse) obj;
                for (NoTakeDto noTakeDto : response.getNoTakeDtos()) {
                    String beginTime = noTakeDto.getBeginTime();
                    if (beginTime.compareTo(DateUtil.getHours()) <= -1) {
                        // 尝试这一种方案
                        // 删缓存
                        redisUtils.del(key);
                        response = orderService.getNoTakeOrdersById(request);
                        redisUtils.set(key, response, RedisConstants.NO_TAKE_OREDERS_EXPIRE.getTime());
                        return new ResponseUtil().setData(response);
                    }
                }
                log.warn("getNoTakeOrdersById->redis\n");
                return new ResponseUtil().setData(obj);
            }
            NoTakeBusResponse response = orderService.getNoTakeOrdersById(request);
            redisUtils.set(key, response, RedisConstants.NO_TAKE_OREDERS_EXPIRE.getTime());
            log.warn("getNoTakeOrdersById\n");
            return new ResponseUtil().setData(response);
        } catch (Exception e) {
            log.error("getNoTakeOrdersById\n", e);
            CommonResponse response = new CommonResponse();
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return new ResponseUtil().setData(response);
        }

    }

    /**
     * 获取未支付订单接口
     * @param pageInfo：去相关类查看参数
     * @param req：目的是获取token
     * @return
     */
    @ApiOperation(value = "获取未支付订单接口", notes = "前提Auth，获取用户订单未支付服务", response = NoPayResponse.class)
    @GetMapping("getNoPayOrders")
    @SentinelResource("getNoPayOrders")
    public ResponseData getNoPayOrdersById(OrderPageInfo pageInfo, HttpServletRequest req) {
        try {
            String token = CurrentUser.getToken(req);
            String userId = jwtTokenUtil.getUsernameFromToken(token);
            String key = RedisConstants.NO_PAY_ORDERS_EXPIRE.getKey()+userId;
            if (redisUtils.hasKey(key)) {
                Object obj = redisUtils.get(key);
                log.warn("getNoPayOrdersById->redis\n");
                return new ResponseUtil().setData(obj);
            }
            NoPayRequest request = new NoPayRequest();
            request.setUserId(Convert.toLong(userId));
            request.setCurrentPage(pageInfo.getCurrentPage());
            request.setPageSize(pageInfo.getPageSize());
            NoPayResponse response = orderService.getNoPayOrdersById(request);
            redisUtils.set(key, response, RedisConstants.NO_PAY_ORDERS_EXPIRE.getTime());
            log.warn("getNoPayOrdersById\n");
            return new ResponseUtil().setData(response);
        } catch (Exception e) {
            log.error("getNoPayOrdersById\n", e);
            CommonResponse response = new CommonResponse();
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return new ResponseUtil().setData(response);
        }

    }

    /**
     * 根据评价状态获取用户订单接口
     * @param pageInfo：查看相关参数
     * @param evaluateStauts：评价状态：0->未评价 1->已评价
     * @param req：目的是获取token
     * @return
     */
    @ApiOperation(value = "根据评价状态获取用户订单接口", notes = "前提Auth，根据评价状态获取订单服务", response = EvaluateResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "evaluateStauts", value = "评价状态：0->未评价 1->已评价", example = "0", required = true, dataType = "String")
    })
    @GetMapping("getEvaluateOrders")
    @SentinelResource("getEvaluateOrders")
    public ResponseData getEvaluateOrdersById(OrderPageInfo pageInfo, String evaluateStauts, HttpServletRequest req) {
        try {
            String token = CurrentUser.getToken(req);
            String userId = jwtTokenUtil.getUsernameFromToken(token);
            String key = RedisConstants.EVALUATE_ORDERS_EXPIRE.getKey()+userId;
            if (redisUtils.hasKey(key)) {
                Object obj = redisUtils.get(key);
                log.warn("getEvaluateOrdersById->redis\n");
                return new ResponseUtil().setData(obj);
            }
            EvaluateRequest request = new EvaluateRequest();
            request.setUserId(Convert.toLong(userId));
            request.setCurrentPage(pageInfo.getCurrentPage());
            request.setPageSize(pageInfo.getPageSize());
            request.setEvaluateStatus(evaluateStauts);
            EvaluateResponse response = orderService.getEvaluateOrdersById(request);
            redisUtils.set(key, response, RedisConstants.EVALUATE_ORDERS_EXPIRE.getTime());
            log.warn("getEvaluateOrders\n");
            return new ResponseUtil().setData(response);
        } catch (Exception e) {
            log.error("getEvaluateOrdersById\n", e);
            CommonResponse response = new CommonResponse();
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return new ResponseUtil().setData(response);
        }

    }

    /**
     * 添加订单接口
     * @param form：查看相关参数
     * @param req：目的是获取token
     * @return
     */
    @ApiOperation(value = "添加订单接口", notes = "添加订单接口信息", response = AddOrderResponse.class)
    @PostMapping("addOrder")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "form", value = "添加订单表", required = true, dataType = "AddOrderForm")
    )
    @SentinelResource(value = "addOrder", blockHandler = "addOrderBlockHandler", fallback = "addOrderFallbackHandler")
    public ResponseData addOrder(@RequestBody AddOrderForm form, HttpServletRequest req) {
        try {
            // id 从本队缓存中取
            String token = CurrentUser.getToken(req);
            String userId = jwtTokenUtil.getUsernameFromToken(token);
            AddOrderRequest request = new AddOrderRequest();
            request.setCountId(form.getCountId());
            request.setUserId(Convert.toLong(userId));
            request.setOrderUser(form.getOrderUser());
            request.setSeatsIds(form.getSeatsIds());
            request.setCountPrice(form.getCountPrice());
            request.setBusStatus(form.getBusStatus());
            request.setExpireTime(form.getExpireTime());
            AddOrderResponse response = orderService.addOrder(request);

            String countKey = RedisConstants.COUNT_DETAIL_EXPIRE.getKey() + request.getCountId();
            if (redisUtils.hasKey(countKey)) {
                redisUtils.del(countKey);
            }
            String noPayKey = RedisConstants.NO_PAY_ORDERS_EXPIRE.getKey() + userId;
            if (redisUtils.hasKey(noPayKey)) {
                redisUtils.del(noPayKey);
            }
            log.warn("addOrder\n");
            return new ResponseUtil().setData(response);
        } catch (Exception e) {
            log.error("addOrder\n", e);
            CommonResponse response = new CommonResponse();
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return new ResponseUtil().setData(response);
        }
    }

    /**
     *
     * @param form
     * @param req
     * @param exception
     * @return
     */
    public ResponseData addOrderBlockHandler(AddOrderForm form, HttpServletRequest req, BlockException exception) {
        CommonResponse response = new CommonResponse();
        response.setCode(SbCode.FLOW_ERROR.getCode());
        response.setMsg(SbCode.FLOW_ERROR.getMessage());
        return new ResponseUtil().setData(response);
    }

    /**
     *
     * @param form
     * @param req
     * @return
     */
    public ResponseData addOrderFallbackHandler(AddOrderForm form, HttpServletRequest req) {
        CommonResponse response = new CommonResponse();
        response.setCode(SbCode.FLOW_ERROR.getCode());
        response.setMsg(SbCode.FLOW_ERROR.getMessage());
        return new ResponseUtil().setData(response);
    }

    /**
     * 根据订单id获取详情订单
     * @param orderId：orderId
     * @param req：目的获取token
     * @return
     */
    @ApiOperation(value = "根据订单id获取详情订单", notes = "前提Auth，根据订单id获取详情订单", response = OrderResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", example = "1", required = true, dataType = "Long")
    })
    @GetMapping("getOrder")
    @SentinelResource("getOrder")
    public ResponseData selectOrderById(Long orderId, HttpServletRequest req) {
        try {
            String key = RedisConstants.SELECT_ORDER_EXPIRE.getKey() + orderId;
            if (redisUtils.hasKey(key)) {
                Object obj = redisUtils.get(key);
                log.warn("selectOrderById->redis\n");
                return new ResponseUtil().setData(obj);
            }
            OrderRequest request = new OrderRequest();
            request.setUuid(orderId);
            OrderResponse response = orderService.selectOrderById(request);
            redisUtils.set(key, response, RedisConstants.SELECT_ORDER_EXPIRE.getTime());
            log.warn("selectOrderById\n");
            return new ResponseUtil().setData(response);
        } catch (Exception e) {
            log.error("selectOrderById\n", e);
            CommonResponse response = new CommonResponse();
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return new ResponseUtil().setData(response);
        }
    }

    /**
     * 更改订单状态
     * @param req：目的是获取token
     * @return
     */
    @ApiOperation(value = "更改订单状态", notes = "前提Auth，更改订单状态", response = OrderResponse.class)
    @PostMapping("updateOrderStatus")
    @ApiImplicitParam(name = "form", value = "更改订单状态表", required = true)
    @SentinelResource("updateOrderStatus")
    public ResponseData updateOrderStatus(@RequestBody OrderUpdateForm form, HttpServletRequest req) {
        try {
            String token = CurrentUser.getToken(req);
            String userId = jwtTokenUtil.getUsernameFromToken(token);
            String selectOrderKey = RedisConstants.SELECT_ORDER_EXPIRE.getKey()+form.getOrderId();
            String noTakeKey = RedisConstants.NO_TAKE_OREDERS_EXPIRE.getKey()+userId;
            String noPayKey = RedisConstants.NO_PAY_ORDERS_EXPIRE.getKey()+userId;
            String evaluateKey = RedisConstants.EVALUATE_ORDERS_EXPIRE.getKey()+userId;
            OrderRequest request = new OrderRequest();
            request.setUuid(form.getOrderId());
            request.setOrderStatus(form.getOrderStatus());
            OrderResponse response = orderService.updateOrderStatus(request);
            log.warn("updateOrderStatus\n");
            if (redisUtils.hasKey(selectOrderKey)) {
                redisUtils.del(selectOrderKey);
            }
            if (redisUtils.hasKey(noTakeKey)) {
                redisUtils.del(noTakeKey);
            }
            if (redisUtils.hasKey(noPayKey)) {
                redisUtils.del(noPayKey);
            }
            if (redisUtils.hasKey(evaluateKey)) {
                redisUtils.del(evaluateKey);
            }
            return new ResponseUtil().setData(response);
        } catch (Exception e) {
            log.error("updateOrderStatus\n", e);
            CommonResponse response = new CommonResponse();
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return new ResponseUtil().setData(response);
        }
    }
}
