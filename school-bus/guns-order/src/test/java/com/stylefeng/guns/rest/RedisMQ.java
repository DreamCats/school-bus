/**
 * @program school-bus
 * @description: RedisMQ
 * @author: mf
 * @create: 2020/05/29 19:40
 */

package com.stylefeng.guns.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisMQ {

    /**
     * 消息池前缀，以此前缀加上传递的消息id作为key，以消息{@link RedisMessage}
     * 的消息体body作为值存储
     */
    public static final String MSG_POOL = "Message:Pool:";
    /**
     * zset队列 名称 queue
     */
    public static final String QUEUE_NAME = "Message:Queue:";

    private static final int SEMIH = 30*60;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 存入消息池
     * @param message
     * @return
     */
    public boolean addMsgPool(RedisMessage message) {

        if (null != message) {
            return redisUtils.set(MSG_POOL + message.getId(), message.getBody(), Long.valueOf(message.getTtl() + SEMIH));
        }
        return false;
    }

    /**
     * 从消息池中删除消息
     * @param id
     */
    public void deMsgPool(String id) {
        redisUtils.del(MSG_POOL + id);
    }

    /**
     * 向队列中添加消息
     * @param key
     * @param score
     * @param val
     */
    public void enMessage(String key, long  score, String val) {
        redisUtils.zsset(key, val, score);
    }

    /**
     * 从队列额删除消息
     * @param key
     * @param id
     * @return
     */
    public boolean deMessage(String key, String id) {
        return redisUtils.zdel(key, id);
    }
}
