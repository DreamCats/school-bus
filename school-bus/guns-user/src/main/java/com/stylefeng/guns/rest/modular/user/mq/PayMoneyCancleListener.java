/**
 * @program school-bus
 * @description: PayMoneyCancleListener
 * @author: mf
 * @create: 2020/03/19 21:34
 */

package com.stylefeng.guns.rest.modular.user.mq;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.constants.MqTags;
import com.stylefeng.guns.core.constants.RedisConstants;
import com.stylefeng.guns.rest.modular.user.RedisUtils;
import com.stylefeng.guns.rest.mq.MQDto;
import com.stylefeng.guns.rest.user.IUserService;
import com.stylefeng.guns.rest.user.dto.UserUpdateInfoRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = "${mq.pay.topic}", consumerGroup = "${mq.pay.consumer.group.name}",messageModel = MessageModel.BROADCASTING)
public class PayMoneyCancleListener implements RocketMQListener<MessageExt> {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 支付金额异常
     * @param messageExt
     */
    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            // 1. 解析消息
            String tags = messageExt.getTags();
            String payCancleTag = MqTags.PAY_MONEY_CANCLE.getTag();
            if (!tags.equals(payCancleTag)) {
                return;
            }
            // 2. 拿到key
            String key = messageExt.getKeys();
            if (!redisUtils.hasKey(key)) {
                String body = new String(messageExt.getBody(), "UTF-8");
                log.warn("收到订单服务异常：" + body);
                MQDto mqDto = JSON.parseObject(body, MQDto.class);
                if (mqDto.getUserId() != null && mqDto.getUserMoney() != null) {
                    UserUpdateInfoRequest request = new UserUpdateInfoRequest();
                    request.setId(mqDto.getUserId());
                    request.setMoney(mqDto.getUserMoney());
                    userService.updateUserInfo(request);
                    log.warn("余额已恢复");
                    redisUtils.set(key, mqDto.getUserId(), RedisConstants.PAY_EXCEPTION_CANCLE_EXPIRE.getTime());
                }
            }
        } catch (Exception e) {
            log.error("支付消费信息程序崩...\n", e);
        }
    }
}
