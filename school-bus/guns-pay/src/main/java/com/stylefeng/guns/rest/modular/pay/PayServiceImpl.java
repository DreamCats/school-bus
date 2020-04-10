/**
 * @program school-bus
 * @description: PayServiceImpl
 * @author: mf
 * @create: 2020/03/19 20:59
 */

package com.stylefeng.guns.rest.modular.pay;



import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.constants.MqTags;
import com.stylefeng.guns.core.constants.RedisConstants;
import com.stylefeng.guns.core.constants.SbCode;
import com.stylefeng.guns.core.exception.CastException;
import com.stylefeng.guns.rest.alipay.IPayService;
import com.stylefeng.guns.rest.alipay.dto.PayBackRequest;
import com.stylefeng.guns.rest.alipay.dto.PayRequset;
import com.stylefeng.guns.rest.alipay.dto.PayResponse;
import com.stylefeng.guns.rest.bus.IBusService;
import com.stylefeng.guns.rest.bus.dto.CountDetailDto;
import com.stylefeng.guns.rest.bus.dto.CountDetailRequest;
import com.stylefeng.guns.rest.bus.dto.CountDetailResponse;
import com.stylefeng.guns.rest.mq.MQDto;
import com.stylefeng.guns.rest.order.IOrderService;
import com.stylefeng.guns.rest.order.dto.OrderRequest;
import com.stylefeng.guns.rest.user.IUserService;
import com.stylefeng.guns.rest.user.dto.UserDto;
import com.stylefeng.guns.rest.user.dto.UserRequest;
import com.stylefeng.guns.rest.user.dto.UserResponse;
import com.stylefeng.guns.rest.user.dto.UserUpdateInfoRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@Service
public class PayServiceImpl implements IPayService {

    @Value("${mq.pay.topic}")
    private String topic;

    //    @Value("${mq.order.tag.cancel}")
    private String tag;

    @Reference(check = false)
    private IUserService userService;

    @Reference(check = false)
    private IBusService busService;

    @Reference(check = false)
    private IOrderService orderService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 支付业务逻辑
     * @param requset
     * @return
     */
    @Override
    public PayResponse pay(PayRequset requset) {
        PayResponse payResponse = new PayResponse();
        Long userId = requset.getUserId();
        Double userMoney = null;
        try {
            // 1. 先核对支付密码是否正确
            tag = MqTags.PAY_CHECK_CANCLE.getTag();
            String key = RedisConstants.USER_INFO_EXPIRE.getKey() + userId;
            UserResponse userResponse = new UserResponse();
            if (redisUtils.hasKey(key)) {
                userResponse = (UserResponse) redisUtils.get(key);
            } else {
                UserRequest request = new UserRequest();
                request.setId(userId);
                // 获取用户信息
                userResponse = userService.getUserById(request);
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
            tag = MqTags.PAY_MONEY_CANCLE.getTag();
            userService.updateUserInfo(request); // 暂时先不接受返回信息
            // 模拟异常
//            CastException.cast(SbCode.SYSTEM_ERROR);
            payResponse.setCode(SbCode.SUCCESS.getCode());
            payResponse.setMsg(SbCode.SUCCESS.getMessage());
            // 4. 按道理讲，这边更改订单状态......
            return payResponse;
        } catch (Exception e) {
            log.error("支付业务发生异常");
            MQDto mqDto = new MQDto();
            mqDto.setUserId(userId);
            mqDto.setUserMoney(userMoney);
            // 发送消息
            try {
                String key = RedisConstants.PAY_EXCEPTION_CANCLE_EXPIRE.getKey() + Convert.toStr(userId);
                sendCancelPay(topic,tag,key, JSON.toJSONString(mqDto));
                log.warn("支付回退消息已发送");
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error("支付消息都崩的话...");
            }
            payResponse.setCode(SbCode.SYSTEM_ERROR.getCode());
            payResponse.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return payResponse;
        }

    }

    /**
     * 退款业务逻辑
     * @param request
     * @return
     */
    @Override
    public PayResponse payBack(PayBackRequest request) {
        PayResponse response = new PayResponse();
        try {
            // 1. 退回金额
            // 读用户金额
            UserRequest userRequest = new UserRequest();
            userRequest.setId(request.getUserId());
            UserResponse userResponse = userService.getUserById(userRequest);
            UserDto userDto = userResponse.getUserDto();
            // 计算金额
            BigDecimal add = NumberUtil.add(userDto.getMoney() + request.getTotalMoney());
            BigDecimal round = NumberUtil.round(add, 2);
            // 写回
            UserUpdateInfoRequest userUpdateInfoRequest = new UserUpdateInfoRequest();
            userUpdateInfoRequest.setId(request.getUserId());
            userUpdateInfoRequest.setMoney(round.doubleValue());
            userService.updateUserInfo(userUpdateInfoRequest);
            // 2. 退回座位
            busService.filterRepeatSeats(request.getSeatsIds(), request.getCoundId());
            // 3. 更改订单状态：关闭
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setUuid(request.getOrderId());
            orderRequest.setOrderStatus("3");
            orderService.updateOrderStatus(orderRequest);
            response.setCode(SbCode.SUCCESS.getCode());
            response.setMsg(SbCode.SUCCESS.getMessage());
            return  response;
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("退款业务异常");
            // 这里可以发消息， 此处先省略
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return response;
        }
    }

    /**
     * 发送支付消息
     * @param topic
     * @param tag
     * @param keys
     * @param body
     * @throws Exception
     */
    private void sendCancelPay(String topic, String tag, String keys, String body) throws Exception {
        Message message = new Message(topic,tag,keys,body.getBytes());
        rocketMQTemplate.getProducer().send(message);
    }
}
