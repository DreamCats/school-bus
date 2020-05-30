/**
 * @program school-bus
 * @description: 消息消费者
 * @author: mf
 * @create: 2020/05/29 19:50
 */

package com.stylefeng.guns.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Set;

@Component
public class RedisMQConsumer {

    @Resource
    private RedisMQ redisMQ;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MessageProvider provider;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 消息队列监听器
     * 也可以开个线程池
     * 主动轮询
     */
    @Scheduled(cron = "*/1 * * * * *")
    public void monitor() {
        // 取出0到当前时间的权重值
        Set<Object> set = redisUtils.rangeByScore(RedisMQ.QUEUE_NAME, 0, System.currentTimeMillis());
        if (null != set) {
            // 如果不为空
            // 获取当前时间
            long current = System.currentTimeMillis();
            for (Object id : set) {
                long  score = redisUtils.getScore(RedisMQ.QUEUE_NAME, (String) id).longValue();
                if (current >= score) {
                    // 已超时的消息拿出来消费
                    String str = "";
                    try {
                        // 根据id取出消息
                        str = (String) redisUtils.get(RedisMQ.MSG_POOL + id);
                        System.out.println("消费了:" + str+ ",消费的时间：" + sdf.format(System.currentTimeMillis()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        //如果取消息发生了异常，则将消息重新放回队列
                        System.out.println("消费异常，重新回到队列");
                        provider.sendMessage(str);
                    } finally {
                        // 不管消费成功与非，都要删除当前消息
                        redisMQ.deMessage(RedisMQ.QUEUE_NAME, (String) id);
                        redisMQ.deMsgPool((String) id);
                    }
                }
            }
        }
    }
}