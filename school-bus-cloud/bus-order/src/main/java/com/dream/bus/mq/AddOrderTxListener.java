/**
 * @program school-bus-cloud
 * @description: AddOrderTxListener
 * @author: mf
 * @create: 2020/11/11 10:52
 */

package com.dream.bus.mq;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dream.bus.common.CastException;
import com.dream.bus.constants.SbCode;
import com.dream.bus.order.param.AddOrderRequest;
import com.dream.bus.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Component
@RocketMQTransactionListener(txProducerGroup = "tx_add_order_group")
public class AddOrderTxListener implements RocketMQLocalTransactionListener {

    @Autowired
    private IOrderService orderService;

    /**
     * 事务消息发送后的回调方法，当消息发送给mq成功，此方法回调
     * @param message
     * @param o
     * @return
     */

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o){
        try {
            // 解析message
            String messageString = new String((byte[]) message.getPayload());
            JSONObject jsonObject = JSONObject.parseObject(messageString);
            String request = jsonObject.getString("request");
            Long key = Convert.toLong(jsonObject.getString("key"));
            String redisKey = jsonObject.getString("redisKey");
            AddOrderRequest addOrderRequest = JSONObject.parseObject(request, AddOrderRequest.class);
            // 执行本地事务
            System.out.println("执行本地事务");
            int result = orderService.doAddOrder(key, redisKey, addOrderRequest);
            // 本地事务执行完毕
            if (result == 1) {
                return RocketMQLocalTransactionState.COMMIT;
            }
            // 告诉broker，本地事务有问题
            if (result == 0) {
                return RocketMQLocalTransactionState.ROLLBACK;
            }
            // broker 回查
            if (result == 2) {
                return RocketMQLocalTransactionState.UNKNOWN;
            }

        } catch (Exception e) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * 事务回查
     * @param message
     * @return
     */
    @Transactional
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        // 暂时先不写
        // 回查数据库状态是否变更之类的
        // 或者找一下订单是否存在
        return null;
    }
}
