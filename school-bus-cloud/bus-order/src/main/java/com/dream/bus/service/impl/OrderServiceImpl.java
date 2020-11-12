/**
 * @program school-bus-cloud
 * @description: OrderServiceImpl
 * @author: mf
 * @create: 2020/11/08 16:18
 */

package com.dream.bus.service.impl;

import cn.hutool.core.convert.Convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.bus.bus.BusClient;
import com.dream.bus.common.CastException;
import com.dream.bus.common.DateUtil;
import com.dream.bus.common.RedisUtils;
import com.dream.bus.common.UUIDUtils;
import com.dream.bus.common.converter.OrderConvertver;
import com.dream.bus.constants.MqTags;
import com.dream.bus.constants.RedisConstants;
import com.dream.bus.constants.SbCode;
import com.dream.bus.dao.OrderMapper;
import com.dream.bus.model.Order;
import com.dream.bus.mq.MQDto;
import com.dream.bus.order.param.*;
import com.dream.bus.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    @Value("${mq.order.topic}")
    private String topic;

    private String tag;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderConvertver orderConvertver;

    @Autowired
    private BusClient busClient;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public NoTakeBusResponse getNoTakeOrdersById(NoTakeBusRequest request) {
        NoTakeBusResponse response = new NoTakeBusResponse();
        IPage<NoTakeDto> orderIPage = new Page<>(request.getCurrentPage(), request.getPageSize());
        QueryWrapper<NoTakeDto> queryWrapper = new QueryWrapper<>();
        // 获取系统年月日
        String day = DateUtil.getDay();
        String hours = DateUtil.getHours();
        System.out.println("当前日期:" + day);
        System.out.println("当前时间:" + hours);
        queryWrapper
                .eq("so.user_id", request.getUserId())
                .eq("so.order_status", "1")// 1：已经支付
                .ge("sc.begin_date", day) // 比如，
                .ge("sc.begin_time", hours)
                .orderByAsc("sc.begin_time")
                .orderByDesc("so.order_time");
//        orderIPage = orderMapper.selectPage(orderIPage, queryWrapper);
        try {
            orderIPage = orderMapper.selectNoTakeOrders(orderIPage, queryWrapper);
            response.setCurrentPage(orderIPage.getCurrent());
            response.setPages(orderIPage.getPages());
            response.setPageSize(orderIPage.getSize());
            response.setTotal(orderIPage.getTotal());
            response.setNoTakeDtos(orderIPage.getRecords());
            response.setCode(SbCode.SUCCESS.getCode());
            response.setMsg(SbCode.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getNoTakeOrdersById:",e);
            response.setCode(SbCode.DB_EXCEPTION.getCode());
            response.setMsg(SbCode.DB_EXCEPTION.getMessage());
            return response;
        }
        return response;
    }

    @Override
    public EvaluateResponse getEvaluateOrdersById(EvaluateRequest request) {
        EvaluateResponse response = new EvaluateResponse();
        IPage<EvaluateDto> orderIPage = new Page<>(request.getCurrentPage(), request.getPageSize());
        QueryWrapper<EvaluateDto> queryWrapper = new QueryWrapper<>();
        // 获取系统年月日
        String day = DateUtil.getDay();
        String hours = DateUtil.getHours();
        System.out.println("当前日期:" + day);
        System.out.println("当前时间:" + hours);
        queryWrapper
                .eq("so.user_id", request.getUserId())
                .eq("so.order_status", "1")
                .and(o -> o.eq("sc.begin_date", day)
                        .lt("sc.begin_time", hours)
                        .or().lt("sc.begin_date", day))
                .eq("evaluate_status", request.getEvaluateStatus())
                .orderByDesc("sc.begin_time")
                .orderByDesc("so.order_time");
        try {
            orderIPage = orderMapper.selectEvaluateOrders(orderIPage, queryWrapper);
            response.setCurrentPage(orderIPage.getCurrent());
            response.setPages(orderIPage.getPages());
            response.setPageSize(orderIPage.getSize());
            response.setTotal(orderIPage.getTotal());
            response.setEvaluateDtos(orderIPage.getRecords());
            response.setCode(SbCode.SUCCESS.getCode());
            response.setMsg(SbCode.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getEvaluateOrdersById:",e);
            response.setCode(SbCode.DB_EXCEPTION.getCode());
            response.setMsg(SbCode.DB_EXCEPTION.getMessage());
            return response;
        }
        return response;
    }

    @Override
    public NoPayResponse getNoPayOrdersById(NoPayRequest request) {
        NoPayResponse response = new NoPayResponse();
        IPage<NoPayDto> noPayDtoIPage = new Page<>(request.getCurrentPage(), request.getPageSize());
        QueryWrapper<NoPayDto> queryWrapper = new QueryWrapper<>();
        // 获取系统年月日
        String day = DateUtil.getDay();
        String hours = DateUtil.getHours();
        System.out.println("当前日期:" + day);
        System.out.println("当前时间:" + hours);
        queryWrapper
                .eq("so.user_id", request.getUserId())
                .eq("so.order_status", "0")
                .ge("sc.begin_date", day) // 比如，
                .ge("sc.begin_time", hours)
                .orderByDesc("sc.begin_time")
                .orderByDesc("so.order_time"); // 未支付
        try {
            noPayDtoIPage = orderMapper.selectNoPayOrders(noPayDtoIPage, queryWrapper);
            response.setCurrentPage(noPayDtoIPage.getCurrent());
            response.setPages(noPayDtoIPage.getPages());
            response.setPageSize(noPayDtoIPage.getSize());
            response.setTotal(noPayDtoIPage.getTotal());
            response.setNoPayDtos(noPayDtoIPage.getRecords());
            response.setCode(SbCode.SUCCESS.getCode());
            response.setMsg(SbCode.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getNoPayOrdersById:",e);
            response.setCode(SbCode.DB_EXCEPTION.getCode());
            response.setMsg(SbCode.DB_EXCEPTION.getMessage());
            return response;
        }
        return response;
    }

    @Override
    public AddOrderResponse addOrder(AddOrderRequest request) {
        // 暂时用普通消息演示， 后期用事务消息
        // 判断座位，如果重复，直接退出，否则更新场次的座位信息
        AddOrderResponse response = new AddOrderResponse();
        // 全局orderId
        Long orderId = UUIDUtils.flakesUUID();
        // 1。 判断座位，如果重复，直接退出，否则下一步
        // 2。 更新座位，如果没有异常，这是写操作
        // 3。 计算总金额，如果没有异常
        // 4。 添加订单，如果异常，这是写操作
        try {
            // 1。 判断座位，如果重复，直接退出，否则下一步
            tag = MqTags.ORDER_SEATS_CANCEL.getTag();
            boolean repeatSeats = busClient.repeatSeats(request.getSeatsIds(), request.getCountId());
            if (repeatSeats) {
                // b:true 说明重复
                response.setCode(SbCode.SELECTED_SEATS.getCode());
                response.setMsg(SbCode.SELECTED_SEATS.getMessage());
                return response;
            }
//            CastException.cast(SbCode.SYSTEM_ERROR);
            // 2。 更新座位，如果没有异常，这是写操作
            // 用tags来过滤消息
            tag = MqTags.ORDER_ADD_SEATS_CANCLE.getTag();
            boolean addSeats = busClient.addSeats(request.getSeatsIds(), request.getCountId());
            if (!addSeats) {
                response.setCode(SbCode.DB_EXCEPTION.getCode());
                response.setMsg(SbCode.DB_EXCEPTION.getMessage());
                return response;
            }
            // 模拟系统异常
//            CastException.cast(SbCode.SYSTEM_ERROR);
            // 3。 计算总金额，如果没有异常
            tag = MqTags.ORDER_CALC_MONEY_CANCLE.getTag();
            String seatIds = request.getSeatsIds();
            Integer seatNumber = seatIds.split(",").length;
            Double countPrice = request.getCountPrice();
            Double totalPrice = getTotalPrice(seatNumber, countPrice);
//            CastException.cast(SbCode.SYSTEM_ERROR);
            // 4。 添加订单，如果异常，这是写操作
            Order order = orderConvertver.res2Order(request);
            order.setOrderPrice(totalPrice);
            order.setEvaluateStatus("0"); // 未评价
            order.setOrderStatus("0"); // 未支付
            order.setUuid(orderId); // 唯一id
            tag = MqTags.ORDER_ADD_CANCLE.getTag();
            int insert = orderMapper.insert(order);// 插入 不判断了
            response.setCode(SbCode.SUCCESS.getCode());
            response.setMsg(SbCode.SUCCESS.getMessage());
            response.setOrderId(orderId);
            // 这里放redis 未支付缓存，时间前端给定
            redisUtils.set(RedisConstants.ORDER_CANCLE_EXPIRE.getKey() + orderId, orderId, request.getExpireTime());
            return response;
        } catch (Exception e) {
            // 以上操作如果程序都不发生异常的话， 是不会执行这里的代码的
            // 也就是说不会发送回退消息的。
            // 目的是在高并发的情况下，程序内部发生异常，依然高可用
            e.printStackTrace();
            log.error("订单业务发生异常");
            // 发消息，将座位退回，将订单退回
            MQDto mqDto = new MQDto();
            mqDto.setOrderId(orderId);
            mqDto.setCountId(request.getCountId());
            mqDto.setSeatsIds(request.getSeatsIds());
            try {
                try {
                    String key = RedisConstants.ORDER_EXCEPTION_CANCLE_EXPIRE.getKey() + Convert.toStr(orderId);
                    sendCancelOrder(topic,tag, key, JSON.toJSONString(mqDto));
                    log.warn("订单回退消息发送成功..." + mqDto);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return response;
        }
    }

    @Override
    public OrderResponse selectOrderById(OrderRequest request) {
        OrderResponse response = new OrderResponse();
        try {
            QueryWrapper<OrderDto> wrapper = new QueryWrapper<>();
            wrapper.eq("so.uuid", request.getUuid());
            OrderDto orderDto = orderMapper.selectOrderById(wrapper);
            response.setOrderDto(orderDto);
            response.setCode(SbCode.SUCCESS.getCode());
            response.setMsg(SbCode.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("selectOrderById", e);
            response.setCode(SbCode.DB_EXCEPTION.getCode());
            response.setMsg(SbCode.DB_EXCEPTION.getMessage());
            return response;
        }
        return response;
    }

    @Override
    public OrderResponse updateOrderStatus(OrderRequest request) {
        OrderResponse response = new OrderResponse();
        try {
            // 获取orderDto
            QueryWrapper<OrderDto> wrapper = new QueryWrapper<>();
            wrapper.eq("so.uuid", request.getUuid());
            OrderDto orderDto = orderMapper.selectOrderById(wrapper);
            // 1， 检查状态是否为2
            if (request.getOrderStatus().equals("2")) {
                // 说明关闭订单，回退座位
                busClient.filterRepeatSeats(orderDto.getSeatsIds(), orderDto.getCountId());
                redisUtils.del(RedisConstants.COUNT_DETAIL_EXPIRE.getKey()
                        + orderDto.getCountId());
                // 清除场次详情的缓存
            }
            if (request.getOrderStatus().equals("1")) {
                // 说明已经支付，删掉5分钟的订单缓存
                redisUtils.del(RedisConstants.ORDER_CANCLE_EXPIRE.getKey() + request.getUuid());
            }
            Order order = orderConvertver.res2Order(request);
            // 更新状态
            orderMapper.updateById(order);
            // 暂时就不获取了
            response.setCode(SbCode.SUCCESS.getCode());
            response.setMsg(SbCode.SUCCESS.getMessage());
            redisUtils.del(RedisConstants.NO_PAY_ORDERS_EXPIRE.getKey()+order.getUserId());
            redisUtils.del(RedisConstants.SELECT_ORDER_EXPIRE.getKey() + request.getUuid());
        } catch (Exception e) {
            log.error("updateOrderStatus", e);
            response.setCode(SbCode.DB_EXCEPTION.getCode());
            response.setMsg(SbCode.DB_EXCEPTION.getMessage());
            return response;
        }
        return response;
    }

    @Override
    public boolean deleteOrderById(Long OrderId) {
        try {
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("uuid", OrderId);
            orderMapper.delete(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("deleteOrderById:", e);
            return false;
        }
        return true;
    }



    /**
     * 计算总价格
     * @param seatNumbers：座位数量
     * @param countPrice：场次价格
     * @return
     */
    private double getTotalPrice(Integer seatNumbers, double countPrice) {
        BigDecimal seatNumbersDeci = new BigDecimal(seatNumbers);
        BigDecimal countPriceDeci = new BigDecimal(countPrice);
        BigDecimal result = seatNumbersDeci.multiply(countPriceDeci);
        // 四舍五入，取小数点后一位
        BigDecimal bigDecimal = result.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    /**
     * 发送订单回退消息
     * @param topic
     * @param tag
     * @param keys
     * @param body
     * @throws Exception
     */
    private void sendCancelOrder(String topic, String tag, String keys, String body) throws Exception{
        Message message = new Message(topic,tag,keys,body.getBytes());
        rocketMQTemplate.getProducer().send(message);
    }

    @Override
    public AddOrderResponse addOrderTest(AddOrderRequest request){
        AddOrderResponse response = new AddOrderResponse();
        // 生成id
        Long orderId = UUIDUtils.flakesUUID();
        // 生成mqdto
        String redisKey = RedisConstants.ADD_ORDER_EXPIRE.getKey() + Convert.toStr(orderId);
        // 发送
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("request", request);
        jsonObject.put("key", orderId);
        jsonObject.put("redisKey", redisKey);
        org.springframework.messaging.Message<String> message = MessageBuilder.withPayload(JSON.toJSONString(jsonObject)).build();
        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction("tx_add_order_group", topic, message, null);
//        System.out.println(sendResult.getSendStatus());
//        System.out.println(sendResult.getLocalTransactionState());
        if (sendResult.getLocalTransactionState().equals(LocalTransactionState.ROLLBACK_MESSAGE)) {
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return response;
        }
        response.setOrderId(orderId);
        response.setCode(SbCode.SUCCESS.getCode());
        response.setMsg(SbCode.SUCCESS.getMessage());
        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int doAddOrder(Long orderId, String redisKey, AddOrderRequest request) {
        // 幂等性质判断
        if (redisUtils.hasKey(redisKey))
            CastException.cast(SbCode.SYSTEMBLOCK_ERROR);
        int result = 0;

        // 1。 判断座位，如果重复，直接退出，否则下一步
        // 2。 添加订单，如果异常，这是写操作
        boolean repeatSeats = busClient.repeatSeats(request.getSeatsIds(), request.getCountId());
        if (repeatSeats) {
            // b:true 说明重复
            return result;
        }
        // 计算金额和下单
        String seatIds = request.getSeatsIds();
        Integer seatNumber = seatIds.split(",").length;
        Double countPrice = request.getCountPrice();
        Double totalPrice = getTotalPrice(seatNumber, countPrice);
        Order order = orderConvertver.res2Order(request);
        order.setOrderPrice(totalPrice);
        order.setEvaluateStatus("0"); // 未评价
        order.setOrderStatus("0"); // 未支付
        order.setUuid(orderId); // 唯一id
        // 插入成功1， 插入失败0
        result = orderMapper.insert(order);// 插入 不判断了
        redisUtils.set(RedisConstants.ADD_ORDER_EXPIRE.getKey() + orderId, orderId, RedisConstants.ADD_ORDER_EXPIRE.getTime());
//        CastException.cast(SbCode.SYSTEMBLOCK_ERROR);
        if (result == 1){
            return result;
        }
        return result;
    }
}
