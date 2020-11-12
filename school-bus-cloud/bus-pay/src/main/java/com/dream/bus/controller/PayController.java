/**
 * @program school-bus-cloud
 * @description: PayController
 * @author: mf
 * @create: 2020/11/12 23:33
 */

package com.dream.bus.controller;

import cn.hutool.core.convert.Convert;
import com.dream.bus.common.CurrentUser;
import com.dream.bus.common.RedisUtils;
import com.dream.bus.constants.RedisConstants;
import com.dream.bus.constants.SbCode;
import com.dream.bus.jwt.JwtTokenUtil;
import com.dream.bus.param.CommonResponse;
import com.dream.bus.param.ResponseData;
import com.dream.bus.param.ResponseUtil;
import com.dream.bus.pay.*;
import com.dream.bus.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/pay/")
public class PayController {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IPayService payService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("")
    public ResponseData pay(@RequestBody PayForm payForm , HttpServletRequest req) {
        try {
            String token = CurrentUser.getToken(req);
            String userId = jwtTokenUtil.getUsernameFromToken(token);
            String key = RedisConstants.USER_INFO_EXPIRE.getKey() + userId;
            PayRequset requset = new PayRequset();
            requset.setUserId(Convert.toLong(userId));
            requset.setPayPassword(payForm.getPayPassword());
            requset.setTotalMoney(payForm.getTotalMoney());
            PayResponse response = payService.pay(requset);
            log.warn("pay\n");
            // ok的话， 删缓存
            if (redisUtils.hasKey(key)) {
                redisUtils.del(key);
            }
            // 按道理讲， 我这边要更改状态
            return new ResponseUtil().setData(response);
        } catch (Exception e) {
            log.error("pay\n", e);
            CommonResponse response = new CommonResponse();
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return new ResponseUtil().setData(response);
        }
    }

    @PostMapping("back")
    public ResponseData payBack(@RequestBody PayBackForm payBackFrom, HttpServletRequest req) {
        try {
            String token = CurrentUser.getToken(req);
            String userId = jwtTokenUtil.getUsernameFromToken(token);
            PayBackRequest request = new PayBackRequest();
            request.setUserId(Convert.toLong(userId));
            request.setOrderId(payBackFrom.getOrderId());
            request.setCoundId(payBackFrom.getCoundId());
            request.setSeatsIds(payBackFrom.getSeatsIds());
            request.setTotalMoney(payBackFrom.getTotalMoney());
            PayResponse response = payService.payBack(request);
            log.warn("payBack:" + response.toString());
            // 清理缓存
            String userInfoKey = RedisConstants.USER_INFO_EXPIRE.getKey() + userId;
            String noTakeKey = RedisConstants.NO_TAKE_OREDERS_EXPIRE.getKey() + userId;
            String selectOrderKey = RedisConstants.SELECT_ORDER_EXPIRE.getKey() + payBackFrom.getOrderId();
            String countDetailKey = RedisConstants.COUNT_DETAIL_EXPIRE.getKey() + payBackFrom.getCoundId();
            if (redisUtils.hasKey(userInfoKey)) {
                redisUtils.del(userInfoKey);
            }
            if (redisUtils.hasKey(noTakeKey)) {
                redisUtils.del(noTakeKey);
            }
            if (redisUtils.hasKey(selectOrderKey)) {
                redisUtils.del(selectOrderKey);
            }
            if (redisUtils.hasKey(countDetailKey)) {
                redisUtils.del(countDetailKey);
            }
            return new ResponseUtil().setData(response);
        } catch (Exception e) {
            log.error("back\n", e);
            CommonResponse response = new CommonResponse();
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return new ResponseUtil().setData(response);
        }
    }
}
