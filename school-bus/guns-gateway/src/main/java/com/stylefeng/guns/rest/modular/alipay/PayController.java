/**
 * @program school-bus
 * @description: PayController
 * @author: mf
 * @create: 2020/03/17 16:49
 */

package com.stylefeng.guns.rest.modular.alipay;

import cn.hutool.core.convert.Convert;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.stylefeng.guns.core.constants.SbCode;
import com.stylefeng.guns.rest.alipay.IPayService;
import com.stylefeng.guns.rest.alipay.dto.PayBackRequest;
import com.stylefeng.guns.rest.alipay.dto.PayRequset;
import com.stylefeng.guns.rest.alipay.dto.PayResponse;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.common.RedisUtils;
import com.stylefeng.guns.rest.common.ResponseData;
import com.stylefeng.guns.rest.common.ResponseUtil;
import com.stylefeng.guns.core.constants.RedisConstants;
import com.stylefeng.guns.rest.exception.CommonResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.form.PayBackForm;
import com.stylefeng.guns.rest.modular.form.PayForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(value = "支付服务", description = "支付服务相关接口")
@RestController
@RequestMapping("/pay/")
public class PayController {

    @Autowired
    private RedisUtils redisUtils;

    @Reference(check = false)
    private IPayService payService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 支付接口
     * @param payForm：去相关类查看参数
     * @param req：目的是获取token
     * @return
     */
    @ApiOperation(value = "支付接口", notes = "前提Auth，获取支付服务", response = PayResponse.class)
    @PostMapping("")
    @SentinelResource("pay")
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

    @ApiOperation(value = "退款接口", notes = "前提Auth，获取退款服务", response = PayResponse.class)
    @PostMapping("back")
    @SentinelResource("back")
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
