/**
 * @program school-bus
 * @description: OrderController
 * @author: mf
 * @create: 2020/03/04 21:59
 */

package com.stylefeng.guns.rest.modular.order;

import cn.hutool.core.convert.Convert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.common.RedisUtils;
import com.stylefeng.guns.rest.common.ResponseData;
import com.stylefeng.guns.rest.common.ResponseUtil;
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
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "根据订单状态获取订单接口", notes = "前提Auth，获取用户订单未乘坐服务", response = NoTakeBusResponse.class)
    @GetMapping("getNoTakeOrders")
    public ResponseData getNoTakeOrdersById(OrderPageInfo pageInfo, HttpServletRequest req) {
        NoTakeBusRequest request = new NoTakeBusRequest();
        String token = CurrentUser.getToken(req);
        String userId = Convert.toStr(redisUtils.get(token));
        request.setUserId(Integer.parseInt(userId));
        request.setCurrentPage(pageInfo.getCurrentPage());
        request.setPageSize(pageInfo.getPageSize());
        NoTakeBusResponse response = orderSerice.getNoTakeOrdersById(request);
        log.warn("getNoTakeOrdersById:" + response.toString());
        return new ResponseUtil().setData(response);
    }

    @ApiOperation(value = "根据订单状态获取订单接口", notes = "前提Auth，获取用户订单未支付服务", response = NoPayResponse.class)
    @GetMapping("getNoPayOrders")
    public ResponseData getNoPayOrdersById(OrderPageInfo pageInfo, HttpServletRequest req) {
        NoPayRequest request = new NoPayRequest();
        String token = CurrentUser.getToken(req);
        String userId = Convert.toStr(redisUtils.get(token));
        request.setUserId(Integer.parseInt(userId));
        request.setCurrentPage(pageInfo.getCurrentPage());
        request.setPageSize(pageInfo.getPageSize());
        NoPayResponse response = orderSerice.getNoPayOrdersById(request);
        log.warn("getNoPayOrdersById:" + response.toString());
        return new ResponseUtil().setData(response);
    }

    @ApiOperation(value = "根据评价状态获取用户订单接口", notes = "前提Auth，根据评价状态获取订单服务", response = EvaluateResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "evaluateStauts", value = "评价状态：0->未评价 1->已评价", example = "0", required = true, dataType = "String")
    })
    @GetMapping("getEvaluateOrders")
    public ResponseData getEvaluateOrdersById(OrderPageInfo pageInfo, String evaluateStauts, HttpServletRequest req) {
        EvaluateRequest request = new EvaluateRequest();
        String token = CurrentUser.getToken(req);
        String userId = Convert.toStr(redisUtils.get(token));
        request.setUserId(Integer.parseInt(userId));
        request.setCurrentPage(pageInfo.getCurrentPage());
        request.setPageSize(pageInfo.getPageSize());
        request.setEvaluateStatus(evaluateStauts);
        EvaluateResponse response = orderSerice.getEvaluateOrdersById(request);
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
        AddOrderResponse response = orderSerice.addOrder(request);
        log.warn("addOrder:" + response.toString());
        return new ResponseUtil().setData(response);
    }

    @ApiOperation(value = "根据订单id获取详情订单", notes = "前提Auth，根据订单id获取详情订单", response = OrderResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderUuid", value = "订单id", example = "1", required = true, dataType = "String")
    })
    @GetMapping("getOrder")
    public ResponseData selectOrderById(String orderUuid, HttpServletRequest req) {
        OrderRequest request = new OrderRequest();
        request.setUuid(orderUuid);
        OrderResponse response = orderSerice.selectOrderById(request);
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
        OrderRequest request = new OrderRequest();
        request.setUuid(orderId);
        request.setOrderStatus(orderStatus);
        OrderResponse response = orderSerice.updateOrderStatus(request);
        log.warn("updateOrderStatus:" + response.toString());
        return new ResponseUtil().setData(response);
    }

}
