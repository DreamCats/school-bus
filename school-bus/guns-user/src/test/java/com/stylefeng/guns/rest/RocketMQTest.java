/**
 * @program school-bus
 * @description: RocetMQTest
 * @author: mf
 * @create: 2020/03/19 01:10
 */

package com.stylefeng.guns.rest;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RocketMQTest {

//    @Value("${mq.order.topic}")
//    private String topic;
//
//    @Value("${mq.order.tag.cancel}")
//    private String tag;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Test
    public void test() {
        Message message = new Message("orderTopic", "order_cancel", "userId", "我是生产者".getBytes());
        try {
            rocketMQTemplate.getProducer().send(message);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
