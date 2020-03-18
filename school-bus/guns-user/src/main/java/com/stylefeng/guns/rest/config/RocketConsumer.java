/**
 * @program school-bus
 * @description: RocketConsumer
 * @author: mf
 * @create: 2020/03/18 20:27
 */

package com.stylefeng.guns.rest.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public abstract class RocketConsumer {

    @Value("${apache.rocketmq.consumer.pushConsumer}")
    private String pushConsumer;

    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    public void consume(String topic, String tag) {
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer(pushConsumer);
        consumer.setNamesrvAddr(namesrvAddr);
        try {
            consumer.subscribe(topic, tag);
            log.warn("订阅成功...");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    return RocketConsumer.this.dealBody(list);
                }
            });
            consumer.start();
            log.warn("consume启动成功...");
        } catch (Exception e) {
            log.warn("consume订阅失败 -> error:" + e);
        }
    }
    public abstract ConsumeConcurrentlyStatus dealBody(List<MessageExt> msgs);
}
