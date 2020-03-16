/**
 * @program school-bus
 * @description: OrderController
 * @author: mf
 * @create: 2020/03/04 21:59
 */

package com.stylefeng.guns.rest.modular.order;

import cn.hutool.core.convert.Convert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.bus.IBusService;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.common.RedisUtils;
import com.stylefeng.guns.rest.common.ResponseData;
import com.stylefeng.guns.rest.common.ResponseUtil;
import com.stylefeng.guns.rest.common.constants.RedisConstants;
import com.stylefeng.guns.rest.common.constants.RetCodeConstants;
import com.stylefeng.guns.rest.exception.CommonResponse;
import com.stylefeng.guns.rest.modular.form.AddOrderForm;
import com.stylefeng.guns.rest.modular.form.OrderPageInfo;
import com.stylefeng.guns.rest.order.IOrderSerice;
import com.stylefeng.guns.rest.order.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(value = "班车服务", description = "班车服务相关接口")
@RestController
@RequestMapping("/order/")
public class OrderController {

    @Reference(check = false)
    private IOrderSerice orderSerice;
    @Reference(check = false)
    private IBusService busService;
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "获取未乘坐订单接口", notes = "前提Auth，获取用户订单未乘坐服务", response = NoTakeBusResponse.class)
    @GetMapping("getNoTakeOrders")
    public ResponseData getNoTakeOrdersById(OrderPageInfo pageInfo, HttpServletRequest req) {
        String token = CurrentUser.getToken(req);
        Object obj = redisUtils.get("getNoTakeOrdersById"+token);
        if (obj != null) {
            log.warn("getNoTakeOrdersById->redis:" + obj.toString());
            return new ResponseUtil().setData(obj);
        }
        String userId = Convert.toStr(redisUtils.get(token));
        NoTakeBusRequest request = new NoTakeBusRequest();
        request.setUserId(Integer.parseInt(userId));
        request.setCurrentPage(pageInfo.getCurrentPage());
        request.setPageSize(pageInfo.getPageSize());
        NoTakeBusResponse response = orderSerice.getNoTakeOrdersById(request);
        redisUtils.set("getNoTakeOrdersById"+token, response, RedisConstants.NO_TAKE_OREDERS_EXPIRE.getTime());
        log.warn("getNoTakeOrdersById:" + response.toString());
        return new ResponseUtil().setData(response);
    }

    @ApiOperation(value = "获取未支付订单接口", notes = "前提Auth，获取用户订单未支付服务", response = NoPayResponse.class)
    @GetMapping("getNoPayOrders")
    public ResponseData getNoPayOrdersById(OrderPageInfo pageInfo, HttpServletRequest req) {
        String token = CurrentUser.getToken(req);
        Object obj = redisUtils.get("getNoPayOrdersById"+token);
        if (obj != null) {
            log.warn("getNoPayOrdersById->redis:" + obj.toString());
            return new ResponseUtil().setData(obj);
        }
        String userId = Convert.toStr(redisUtils.get(token));
        NoPayRequest request = new NoPayRequest();
        request.setUserId(Integer.parseInt(userId));
        request.setCurrentPage(pageInfo.getCurrentPage());
        request.setPageSize(pageInfo.getPageSize());
        NoPayResponse response = orderSerice.getNoPayOrdersById(request);
//        redisUtils.set("getNoPayOrdersById"+token, response, RedisConstants.NO_PAY_ORDERS_EXPIRE.getTime());
        log.warn("getNoPayOrdersById:" + response.toString());
        return new ResponseUtil().setData(response);
    }

    @ApiOperation(value = "根据评价状态获取用户订单接口", notes = "前提Auth，根据评价状态获取订单服务", response = EvaluateResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "evaluateStauts", value = "评价状态：0->未评价 1->已评价", example = "0", required = true, dataType = "String")
    })
    @GetMapping("getEvaluateOrders")
    public ResponseData getEvaluateOrdersById(OrderPageInfo pageInfo, String evaluateStauts, HttpServletRequest req) {
        String token = CurrentUser.getToken(req);
        Object obj = redisUtils.get("getEvaluateOrdersById"+token);
        if (obj != null) {
            log.warn("getEvaluateOrdersById->redis:" + obj.toString());
            return new ResponseUtil().setData(obj);
        }
        String userId = Convert.toStr(redisUtils.get(token));
        EvaluateRequest request = new EvaluateRequest();
        request.setUserId(Integer.parseInt(userId));
        request.setCurrentPage(pageInfo.getCurrentPage());
        request.setPageSize(pageInfo.getPageSize());
        request.setEvaluateStatus(evaluateStauts);
        EvaluateResponse response = orderSerice.getEvaluateOrdersById(request);
        redisUtils.set("getEvaluateOrdersById"+token, response, RedisConstants.EVALUATE_ORDERS_EXPIRE.getTime());
        log.warn("getEvaluateOrders:" + response.toString());
        return new ResponseUtil().setData(response);
    }

    @ApiOperation(value = "添加订单接口", notes = "添加订单接口信息", response = AddOrderResponse.class)
    @PostMapping("addOrder")
    public ResponseData addOrder(AddOrderForm form, HttpServletRequest req) {
        // id 从本队缓存中取
        String token = CurrentUser.getToken(req);
        String userId = Convert.toStr(redisUtils.get(token));
        AddOrderRequest request = new AddOrderRequest();
        request.setCountId(form.getCountId());
        request.setUserId(Integer.parseInt(userId));
        request.setOrderUser(form.getOrderUser());
        request.setSeatsIds(form.getSeatsIds());
        request.setCountPrice(form.getCountPrice());
        request.setBusStatus(form.getBusStatus());
        // 判断座位是否重复
        boolean selectedSeats = busService.selectedSeats(request.getSeatsIds(), request.getCountId());
        if (selectedSeats) {
            CommonResponse response = new CommonResponse();
            response.setCode(RetCodeConstants.SELECTED_SEATS.getCode());
            response.setMsg(RetCodeConstants.SELECTED_SEATS.getMessage());
            return new ResponseUtil().setData(response);
        }
        // 更新座位
        //更新场次的座位信息，并更新场次的座位是否已满
        boolean updateSeats = busService.updateSeats(request.getSeatsIds(), request.getCountId());
        if (!updateSeats) {
            // 更新失败
            CommonResponse response = new CommonResponse();
            response.setCode(RetCodeConstants.DB_EXCEPTION.getCode());
            response.setMsg(RetCodeConstants.DB_EXCEPTION.getMessage());
            return new ResponseUtil().setData(response);
        }
        // 缓存失效
        Object obj = redisUtils.get("getCountDetailById" + request.getCountId());
        if (obj != null) {
            // 说明有缓存,清理掉
            redisUtils.del("getCountDetailById" + request.getCountId());
        }
        AddOrderResponse response = orderSerice.addOrder(request);
        // 待支付缓存失效
        obj = redisUtils.get("getNoPayOrdersById" + token);
        if (obj != null) {
            // 说明有缓存，清理掉
            redisUtils.del("getNoPayOrdersById" + token);
        }
        log.warn("addOrder:" + response.toString());
        return new ResponseUtil().setData(response);
    }

    @ApiOperation(value = "根据订单id获取详情订单", notes = "前提Auth，根据订单id获取详情订单", response = OrderResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderUuid", value = "订单id", example = "1", required = true, dataType = "String")
    })
    @GetMapping("getOrder")
    public ResponseData selectOrderById(String orderUuid, HttpServletRequest req) {
        String token = CurrentUser.getToken(req);
        Object obj = redisUtils.get("selectOrderById"+token);
        if (obj != null) {
            log.warn("selectOrderById->redis:" + obj.toString());
            return new ResponseUtil().setData(obj);
        }
        OrderRequest request = new OrderRequest();
        request.setUuid(orderUuid);
        OrderResponse response = orderSerice.selectOrderById(request);
        redisUtils.set("selectOrderById"+token, response, RedisConstants.SELECT_ORDER_EXPIRE.getTime());
        log.warn("selectOrderById:" + response.toString());
        return new ResponseUtil().setData(response);
    }

    @ApiOperation(value = "更改订单状态", notes = "前提Auth，更改订单状态", response = OrderResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", example = "1", required = true, dataType = "String"),
            @ApiImplicitParam(name = "orderStatus", value = "状态：0-待支付,1-已支付,2-已关闭", example = "1", required = true, dataType = "String")
    })
    @PostMapping("updateOrderStatus")
    public ResponseData updateOrderStatus(String orderId, String orderStatus, HttpServletRequest req) {
        String token = CurrentUser.getToken(req);
        Object obj = redisUtils.get("selectOrderById"+token);
        Object obj1 = redisUtils.get("getNoTakeOrdersById"+token);
        Object obj2 = redisUtils.get("getNoPayOrdersById"+token);
        Object obj3 = redisUtils.get("getEvaluateOrdersById"+token);
        if (obj != null || obj1 != null || obj2 != null || obj3 != null) {
            redisUtils.del("selectOrderById"+token);
            redisUtils.del("getNoTakeOrdersById"+token);
            redisUtils.del("getNoPayOrdersById"+token);
            redisUtils.del("getEvaluateOrdersById"+token);
        }
        OrderRequest request = new OrderRequest();
        request.setUuid(orderId);
        request.setOrderStatus(orderStatus);
        OrderResponse response = orderSerice.updateOrderStatus(request);
        log.warn("updateOrderStatus:" + response.toString());
        return new ResponseUtil().setData(response);
    }

}
