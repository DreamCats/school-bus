/**
 * @program school-bus
 * @description: UserServiceListener
 * @author: mf
 * @create: 2020/03/18 20:31
 */

package com.stylefeng.guns.rest.modular.user.listener;

import com.stylefeng.guns.rest.config.RocketConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserServiceListener extends RocketConsumer implements CommandLineRunner {
    @Override
    public ConsumeConcurrentlyStatus dealBody(List<MessageExt> msgs) {
        for (MessageExt msg : msgs) {
            log.warn("接收到了消息：" + new String(msg.getBody()));
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    @Override
    public void run(String... args) throws Exception {
        // 调用consume初始化，并且开始监听
        super.consume("gateway", "pay");
    }
}
