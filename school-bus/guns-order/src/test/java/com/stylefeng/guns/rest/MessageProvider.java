/**
 * @program school-bus
 * @description: 生产者
 * @author: mf
 * @create: 2020/05/29 19:47
 */

package com.stylefeng.guns.rest;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Component
public class MessageProvider {

//    static Logger logger = LoggerFactory.getLogger(MessageProvider.class);


    private static int delay = 30;//30秒，可自己动态传入

    @Resource
    private RedisMQ redisMQ;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //改造成redis
    public void sendMessage(String messageContent) {
        try {
            if (messageContent != null){
                String seqId = UUID.randomUUID().toString();
                // 将有效信息放入消息队列和消息池中
                RedisMessage message = new RedisMessage();
                // 可以添加延迟配置
                message.setDelay(delay*1000);
                message.setCreateTime(System.currentTimeMillis());
                message.setBody(messageContent);
                message.setId(seqId);
                // 设置消息池ttl，防止长期占用
                message.setTtl(delay + 360);
                redisMQ.addMsgPool(message);
                //当前时间加上延时的时间，作为score
                Long delayTime = message.getCreateTime() + message.getDelay();
                String d = sdf.format(message.getCreateTime());
                System.out.println("当前时间：" + d+",消费的时间：" + sdf.format(delayTime));
                redisMQ.enMessage(RedisMQ.QUEUE_NAME,delayTime, message.getId());
            }else {
//                logger.warn("消息内容为空！！！！！");
                System.out.println("消息内容为空");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
