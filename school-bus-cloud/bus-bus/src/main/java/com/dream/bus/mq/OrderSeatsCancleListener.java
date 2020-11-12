/**
 * @program school-bus-cloud
 * @description: OrderSeatsCancleListener
 * @author: mf
 * @create: 2020/11/09 19:37
 */

package com.dream.bus.mq;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.dream.bus.common.CastException;
import com.dream.bus.common.RedisUtils;
import com.dream.bus.constants.RedisConstants;
import com.dream.bus.constants.SbCode;
import com.dream.bus.order.param.AddOrderRequest;
import com.dream.bus.service.IBusService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = "${mq.order.topic}", consumerGroup = "${mq.order.consumer.group.name}",messageModel = MessageModel.BROADCASTING)
public class OrderSeatsCancleListener implements RocketMQListener<String> {

    @Autowired
    private IBusService busService;

    @Autowired
    private RedisUtils redisUtils;


//    @Override
//    public void onMessage(MessageExt messageExt) {
//        String messageString = new String(messageExt.getBody(), "utf-8");
//        System.out.println("消息监听：" + messageString);
//        JSONObject jsonObject = JSONObject.parseObject(messageString);
//        String request = jsonObject.getString("request");
//        Long key = Convert.toLong(jsonObject.getString("key"));
//        String redisKey = RedisConstants.BIND_SEATS_EXPIRE.getKey() + Convert.toStr(key);
//        AddOrderRequest addOrderRequest = JSONObject.parseObject(request, AddOrderRequest.class);
//        if (!redisUtils.hasKey(redisKey)) {
//            // 调用业务，绑定座位
//            boolean b = busService.addSeats(addOrderRequest.getSeatsIds(), addOrderRequest.getCountId());
//            if (b) {
//                log.warn("绑定成功");
//                redisUtils.set(redisKey, key, RedisConstants.ORDER_EXCEPTION_CANCLE_EXPIRE.getTime());
//            }
//        }
//
//    }

    @Override
    public void onMessage(String s) {
        System.out.println("消息监听：" + s);
        JSONObject jsonObject = JSONObject.parseObject(s);
        String request = jsonObject.getString("request");
        Long key = Convert.toLong(jsonObject.getString("key"));
        String redisKey = RedisConstants.BIND_SEATS_EXPIRE.getKey() + Convert.toStr(key);
        AddOrderRequest addOrderRequest = JSONObject.parseObject(request, AddOrderRequest.class);
        if (!redisUtils.hasKey(redisKey)) {
            // 调用业务，绑定座位
//            CastException.cast(SbCode.SYSTEMBLOCK_ERROR);
            boolean b = busService.addSeats(addOrderRequest.getSeatsIds(), addOrderRequest.getCountId());
            if (b) {
                log.warn("绑定成功");
                redisUtils.set(redisKey, key, RedisConstants.BIND_SEATS_EXPIRE.getTime());
            }
        }

    }
}
