/**
 * @program school-bus
 * @description: RedisKeyExpirationListener
 * @author: mf
 * @create: 2020/03/20 18:55
 */

package com.stylefeng.guns.rest.modular.order.rd;

import cn.hutool.core.convert.Convert;
import com.stylefeng.guns.core.constants.RedisConstants;
import com.stylefeng.guns.rest.order.IOrderService;
import com.stylefeng.guns.rest.order.dto.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Autowired
    private IOrderService orderService;

    /**
     * 监听key过期事件
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        super.onMessage(message, pattern);
        String expiredKey = message.toString();
        log.info("redis key过期：{}",expiredKey);
        String orderCancleKey = RedisConstants.ORDER_CANCLE_EXPIRE.getKey();
        if (expiredKey.startsWith(orderCancleKey)) {
            // 获取订单id
            String[] strings = expiredKey.split(orderCancleKey);
            String orderId = strings[1];
            log.warn("过期订单ID：" + orderId);
            // 得到过期订单。
            // 1。 更改订单状态就完事了
            OrderRequest request = new OrderRequest();
            request.setOrderStatus("2"); // 关闭订单
            request.setUuid(Convert.toLong(orderId));
            orderService.updateOrderStatus(request);
            log.warn("过期订单已处理：" + orderId);
        }
    }
}
