/**
 * @program school-bus-cloud
 * @description: OrderController
 * @author: mf
 * @create: 2020/11/08 22:51
 */

package com.dream.bus.controller;

import cn.hutool.core.convert.Convert;
import com.dream.bus.bus.BusClient;
import com.dream.bus.common.CurrentUser;
import com.dream.bus.common.DateUtil;
import com.dream.bus.common.RedisUtils;
import com.dream.bus.constants.RedisConstants;
import com.dream.bus.constants.SbCode;
import com.dream.bus.jwt.JwtTokenUtil;
import com.dream.bus.order.param.*;
import com.dream.bus.param.CommonResponse;
import com.dream.bus.param.ResponseData;
import com.dream.bus.param.ResponseUtil;
import com.dream.bus.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private BusClient busClient;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("getNoTakeOrders")
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

    @GetMapping("getNoPayOrders")
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

    @GetMapping("getEvaluateOrders")
    public ResponseData getEvaluateOrdersById(OrderPageInfo pageInfo, String evaluateStauts, HttpServletRequest req) {
        try {
            String token = CurrentUser.getToken(req);
            String userId = jwtTokenUtil.getUsernameFromToken(token);
            String key = RedisConstants.EVALUATE_ORDERS_EXPIRE.getKey() + userId;
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

    @PostMapping("addOrder")
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

    @GetMapping("getOrder")
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

    @PostMapping("updateOrderStatus")
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

//    @GetMapping("addOrderTest")
    @PostMapping("addOrder")
    public ResponseData addOrderTest(@RequestBody AddOrderForm form, HttpServletRequest req){
        try {
            // id 从本队缓存中取
            String token = CurrentUser.getToken(req);
            String userId = jwtTokenUtil.getUsernameFromToken(token);
            AddOrderRequest request = new AddOrderRequest();
            request.setBusStatus("0");// 沙河->清水河
            request.setCountId(Convert.toLong(102)); // 场次1
            request.setUserId(Convert.toLong(4)); // 4下单
            request.setCountPrice(4.00);
            request.setOrderUser("feng");
            request.setSeatsIds("3,4"); // 座位
            request.setExpireTime(300); // 过期时间 s
            AddOrderResponse response = orderService.addOrderTest(request);
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
            log.error("addOrderTest\n", e);
            CommonResponse response = new CommonResponse();
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return new ResponseUtil().setData(response);
        }
    }


    @PostMapping("localUpdateOrderStatus")
    public OrderResponse updateOrderStatus(OrderRequest request) {
        OrderResponse orderResponse = new OrderResponse();
        try {
            orderResponse = orderService.updateOrderStatus(request);
        } catch (Exception e) {
            log.error("updateOrderStatus\n", e);
            CommonResponse response = new CommonResponse();
            orderResponse.setCode(SbCode.SYSTEM_ERROR.getCode());
            orderResponse.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return orderResponse;
        }
        return orderResponse;
    }
}
