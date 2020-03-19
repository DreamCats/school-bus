/**
 * @program school-bus
 * @description: TestListener
 * @author: mf
 * @create: 2020/03/19 01:04
 */

package com.stylefeng.guns.rest.modular.user.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@RocketMQMessageListener(topic = "${mq.topic}", consumerGroup = "${mq.order.consumer.group.name}",messageModel = MessageModel.BROADCASTING)
public class TestListener implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            String body = new String(messageExt.getBody(), "UTF-8");
            System.out.println("收到消息：" + body);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
