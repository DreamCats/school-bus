/**
 * @program school-bus
 * @description: TestListener
 * @author: mf
 * @create: 2020/03/19 01:04
 */

package com.stylefeng.guns.rest.modular.bus.mq;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.constants.MqTags;
import com.stylefeng.guns.core.constants.RedisConstants;
import com.stylefeng.guns.rest.bus.IBusService;
import com.stylefeng.guns.rest.common.RedisUtils;
import com.stylefeng.guns.rest.mq.MQDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@RocketMQMessageListener(topic = "${mq.order.topic}", consumerGroup = "${mq.order.consumer.group.name}",messageModel = MessageModel.BROADCASTING)
public class OrderSeatsCancleListener implements RocketMQListener<MessageExt> {

    @Autowired
    private IBusService busService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 回退座位
     * @param messageExt
     */
    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            // 1. 解析消息
            String tags = messageExt.getTags();
            String orderTag = MqTags.ORDER_SEATS_CANCEL.getTag();
            if (tags.equals(orderTag)) {
                return;
            }
            String key = messageExt.getKeys();
            System.out.println("取消订单消息：" + key);
            if (!redisUtils.hasKey(key)) {
                String body = new String(messageExt.getBody(), "UTF-8");
                log.warn("收到订单服务异常：" + body);
                MQDto mqDto = JSON.parseObject(body, MQDto.class);
                // 判断需要的值在不在
                if (mqDto.getCountId() != null && mqDto.getSeatsIds() != null) {
                    // 2. 调用业务，回退座位
                    boolean b = busService.filterRepeatSeats(mqDto.getSeatsIds(), mqDto.getCountId());
                    if (b) {
                        log.warn("回退座位成功");
                        redisUtils.set(key, mqDto.getOrderId(), RedisConstants.ORDER_EXCEPTION_CANCLE_EXPIRE.getTime());
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            log.error("座位回退程序崩了...好好检查程序吧", e);
        }
    }
}
