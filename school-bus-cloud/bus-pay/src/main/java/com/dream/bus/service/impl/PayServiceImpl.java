/**
 * @program school-bus-cloud
 * @description: PayServiceImpl
 * @author: mf
 * @create: 2020/11/12 21:18
 */

package com.dream.bus.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.dream.bus.bus.BusClient;
import com.dream.bus.common.RedisUtils;
import com.dream.bus.constants.MqTags;
import com.dream.bus.constants.RedisConstants;
import com.dream.bus.constants.SbCode;
import com.dream.bus.order.OrderClient;
import com.dream.bus.order.param.OrderRequest;
import com.dream.bus.pay.PayBackRequest;
import com.dream.bus.pay.PayRequset;
import com.dream.bus.pay.PayResponse;
import com.dream.bus.service.IPayService;
import com.dream.bus.user.UserClient;
import com.dream.bus.user.param.UserDto;
import com.dream.bus.user.param.UserRequest;
import com.dream.bus.user.param.UserResponse;
import com.dream.bus.user.param.UserUpdateInfoRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class PayServiceImpl implements IPayService {

    @Value("${mq.pay.topic}")
    private String topic;

    @Autowired
    private UserClient userClient;

    @Autowired
    private BusClient busClient;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;



    /**
     * 支付业务逻辑,这里就订单的事务消息一样， 我就不改了
     * @param requset
     * @return
     */
    @Override
    public PayResponse pay(PayRequset requset) {
        PayResponse payResponse = new PayResponse();
        Long userId = requset.getUserId();
        Double userMoney = null;
        String key = RedisConstants.USER_INFO_EXPIRE.getKey() + userId;
        UserResponse userResponse = new UserResponse();
        if (redisUtils.hasKey(key)) {
            userResponse = (UserResponse) redisUtils.get(key);
        } else {
            UserRequest request = new UserRequest();
            request.setId(userId);
            // 获取用户信息
            userResponse = userClient.localGetUserInfo(request);
        }
        // 支付密码不对
        if (!userResponse.getUserDto().getPayPassword().equals(requset.getPayPassword())) {
            payResponse.setCode(SbCode.PAY_PASSWORD_ERROR.getCode());
            payResponse.setMsg(SbCode.PAY_PASSWORD_ERROR.getMessage());
            return payResponse;
        }
        // 2。 核对余额是否够
        userMoney = userResponse.getUserDto().getMoney();
        Double subMoney = NumberUtil.sub(userMoney, requset.getTotalMoney());
        BigDecimal round = NumberUtil.round(subMoney, 2);
        if (round.doubleValue() < 0) {
            payResponse.setCode(SbCode.MONEY_ERROR.getCode());
            payResponse.setMsg(SbCode.MONEY_ERROR.getMessage());
            return payResponse;
        }
        // 3。 够，就写入
        UserUpdateInfoRequest request = new UserUpdateInfoRequest();
        request.setId(userId);
        request.setMoney(round.doubleValue());
        userClient.localUpdateInfo(request); // 暂时先不接受返回信息
        // 模拟异常
//            CastException.cast(SbCode.SYSTEM_ERROR);
        payResponse.setCode(SbCode.SUCCESS.getCode());
        payResponse.setMsg(SbCode.SUCCESS.getMessage());
        // 4. 按道理讲，这边更改订单状态......
        return payResponse;
    }

    /**
     * 退款， 也需要事务支持， 我就不改了
     * @param request
     * @return
     */
    @Override
    public PayResponse payBack(PayBackRequest request) {
        PayResponse response = new PayResponse();
        // 1. 退回金额
        // 读用户金额
        UserRequest userRequest = new UserRequest();
        userRequest.setId(request.getUserId());
        UserResponse userResponse = userClient.localGetUserInfo(userRequest);
        UserDto userDto = userResponse.getUserDto();
        // 计算金额
        BigDecimal add = NumberUtil.add(userDto.getMoney() + request.getTotalMoney());
        BigDecimal round = NumberUtil.round(add, 2);
        // 写回
        UserUpdateInfoRequest userUpdateInfoRequest = new UserUpdateInfoRequest();
        userUpdateInfoRequest.setId(request.getUserId());
        userUpdateInfoRequest.setMoney(round.doubleValue());
        userClient.localUpdateInfo(userUpdateInfoRequest);
        // 2. 退回座位
        busClient.filterRepeatSeats(request.getSeatsIds(), request.getCoundId());
        // 3. 更改订单状态：关闭
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUuid(request.getOrderId());
        orderRequest.setOrderStatus("3");
        orderClient.localUpdateOrderStatus(orderRequest);
        response.setCode(SbCode.SUCCESS.getCode());
        response.setMsg(SbCode.SUCCESS.getMessage());
        return response;
    }
}
