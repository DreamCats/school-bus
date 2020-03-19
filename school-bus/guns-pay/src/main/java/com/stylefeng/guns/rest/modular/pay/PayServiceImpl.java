/**
 * @program school-bus
 * @description: PayServiceImpl
 * @author: mf
 * @create: 2020/03/19 20:59
 */

package com.stylefeng.guns.rest.modular.pay;



import cn.hutool.core.util.NumberUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.constants.MqTags;
import com.stylefeng.guns.core.constants.RedisConstants;
import com.stylefeng.guns.core.constants.SbCode;
import com.stylefeng.guns.core.exception.CastException;
import com.stylefeng.guns.rest.alipay.IPayService;
import com.stylefeng.guns.rest.alipay.dto.PayRequset;
import com.stylefeng.guns.rest.alipay.dto.PayResponse;
import com.stylefeng.guns.rest.mq.MQDto;
import com.stylefeng.guns.rest.user.IUserService;
import com.stylefeng.guns.rest.user.dto.UserRequest;
import com.stylefeng.guns.rest.user.dto.UserResponse;
import com.stylefeng.guns.rest.user.dto.UserUpdateInfoRequest;
import lombok.extern.slf4j.Slf4j;
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
        Integer userId = requset.getUserId();
        Double userMoney = null;
        try {
            // 1. 先核对支付密码是否正确
            tag = MqTags.PAY_CHECK_CANCLE.getTag();
            Object obj = redisUtils.get(RedisConstants.USER_INFO_EXPIRE.getKey() + userId);
            UserResponse userResponse = new UserResponse();
            if (null != obj) {
                userResponse = (UserResponse) obj;
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
            return payResponse;
        } catch (Exception e) {
            log.error("支付业务发生异常");
            MQDto mqDto = new MQDto();
            mqDto.setUserId(userId);
            mqDto.setUserMoney(userMoney);
            // 发送消息
            try {
                sendCancelPay(topic,tag,userId.toString(), JSON.toJSONString(mqDto));
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
