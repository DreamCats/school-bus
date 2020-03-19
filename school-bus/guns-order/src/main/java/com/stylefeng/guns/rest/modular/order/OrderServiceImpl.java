/**
 * @program school-bus
 * @description: OrderServiceImpl
 * @author: mf
 * @create: 2020/03/04 21:50
 */

package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stylefeng.guns.core.constants.MqTags;
import com.stylefeng.guns.core.exception.CastException;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.bus.IBusService;
import com.stylefeng.guns.core.constants.SbCode;
import com.stylefeng.guns.rest.common.persistence.dao.OrderMapper;
import com.stylefeng.guns.rest.common.persistence.model.Order;
import com.stylefeng.guns.rest.modular.order.converter.OrderConvertver;
import com.stylefeng.guns.rest.mq.MQDto;
import com.stylefeng.guns.rest.myutils.UUIDUtils;
import com.stylefeng.guns.rest.order.IOrderService;
import com.stylefeng.guns.rest.order.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Component
@Service
public class OrderServiceImpl implements IOrderService {

    @Value("${mq.order.topic}")
    private String topic;

//    @Value("${mq.order.tag.cancel}")
    private String tag;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderConvertver orderConvertver;

    @Reference
    private IBusService busService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    /**
     * 返回未乘坐列表
     * @param request
     * @return
     */
    @Override
    public NoTakeBusResponse getNoTakeOrdersById(NoTakeBusRequest request) {
        NoTakeBusResponse response = new NoTakeBusResponse();
        IPage<NoTakeDto> orderIPage = new  Page<>(request.getCurrentPage(), request.getPageSize());
        QueryWrapper<NoTakeDto> queryWrapper = new QueryWrapper<>();
        // 获取系统年月日
        String day = DateUtil.getDay();
        String hours = DateUtil.getHours();
        System.out.println("当前日期:" + day);
        System.out.println("当前时间:" + hours);
        queryWrapper
                .eq("user_id", request.getUserId())
                .eq("order_status", "1")// 1：已经支付
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

    /**
     * 该业务可以和上一个业务合并
     * 返回未评价和评价的列表
     * @param request
     * @return
     */
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
                .eq("user_id", request.getUserId())
                .eq("order_status", "1")
                .and(o -> o.eq("sc.begin_date", day).lt("sc.begin_time", hours)
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

    /**
     * 返回未支付列表
     * @param request
     * @return
     */
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
                .eq("user_id", request.getUserId())
                .eq("order_status", "0")
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

    /**
     * 添加订单，这里比较重要
     * @param request
     * @return
     */
    @Override
    public AddOrderResponse addOrder(AddOrderRequest request) {
        // 判断座位，如果重复，直接退出，否则更新场次的座位信息
        AddOrderResponse response = new AddOrderResponse();
        // 全局orderId
        String orderId = UUIDUtils.getUUID();
        // 1。 判断座位，如果重复，直接退出，否则下一步
        // 2。 更新座位，如果没有异常，这是写操作
        // 3。 计算总金额，如果没有异常
        // 4。 添加订单，如果异常，这是写操作
        try {
            // 1。 判断座位，如果重复，直接退出，否则下一步
            tag = MqTags.ORDER_SEATS_CANCEL.getTag();
            boolean repeatSeats = busService.repeatSeats(request.getSeatsIds(), request.getCountId());
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
            boolean addSeats = busService.addSeats(request.getSeatsIds(), request.getCountId());
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
//            CastException.cast(SbCode.SYSTEM_ERROR);
            // 这里就不读了，耗时
//            QueryWrapper<OrderDto> wrapper = new QueryWrapper<>();
//            wrapper.eq("so.uuid", order.getUuid());
//            OrderDto orderDto = orderMapper.selectOrderById(wrapper);
            response.setCode(SbCode.SUCCESS.getCode());
            response.setMsg(SbCode.SUCCESS.getMessage());
//            response.setOrderDto(orderDto);
            return response;
        } catch (Exception e) {
            // 以上操作如果程序都不发生异常的话， 是不会执行这里的代码的
            // 也就是说不会发送回退消息的。
            // 目的是在高并发的情况下，程序内部发生异常，依然高可用
//            e.printStackTrace();
            log.error("订单业务发生异常");
            // 发消息，将座位退回，将订单退回
            MQDto mqDto = new MQDto();
            mqDto.setOrderId(orderId);
            mqDto.setCountId(request.getCountId());
            mqDto.setSeatsIds(request.getSeatsIds());
            try {
                sendCancelOrder(topic,tag,orderId, JSON.toJSONString(mqDto));
                log.warn("订单回退消息发送成功..." + mqDto);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return response;
        }
    }

    /**
     * 通过订单id返回订单详情
     * @param request
     * @return
     */
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

    /**
     * 更新订单状态 0-待支付,1-已支付,2-已关闭
     * @param request
     * @return
     */
    @Override
    public OrderResponse updateOrderStatus(OrderRequest request) {
        OrderResponse response = new OrderResponse();
        try {
            Order order = orderConvertver.res2Order(request);
            orderMapper.updateById(order);
            QueryWrapper<OrderDto> wrapper = new QueryWrapper<>();
            wrapper.eq("so.uuid", request.getUuid());
            OrderDto orderDto = orderMapper.selectOrderById(wrapper);
            response.setOrderDto(orderDto);
            response.setCode(SbCode.SUCCESS.getCode());
            response.setMsg(SbCode.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("updateOrderStatus", e);
            response.setCode(SbCode.DB_EXCEPTION.getCode());
            response.setMsg(SbCode.DB_EXCEPTION.getMessage());
            return response;
        }
        return response;
    }

    /**
     * 根据订单id删除订单
     * @param OrderId
     * @return
     */
    @Override
    public boolean deleteOrderById(String OrderId) {
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
}
