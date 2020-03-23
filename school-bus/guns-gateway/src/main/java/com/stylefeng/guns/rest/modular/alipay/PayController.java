/**
 * @program school-bus
 * @description: PayController
 * @author: mf
 * @create: 2020/03/17 16:49
 */

package com.stylefeng.guns.rest.modular.alipay;

import cn.hutool.core.convert.Convert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.alipay.IPayService;
import com.stylefeng.guns.rest.alipay.dto.PayBackRequest;
import com.stylefeng.guns.rest.alipay.dto.PayRequset;
import com.stylefeng.guns.rest.alipay.dto.PayResponse;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.common.RedisUtils;
import com.stylefeng.guns.rest.common.ResponseData;
import com.stylefeng.guns.rest.common.ResponseUtil;
import com.stylefeng.guns.core.constants.RedisConstants;
import com.stylefeng.guns.rest.modular.form.PayBackForm;
import com.stylefeng.guns.rest.modular.form.PayForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Reference
    private IPayService payService;

    /**
     * 支付接口
     * @param payForm：去相关类查看参数
     * @param req：目的是获取token
     * @return
     */
    @ApiOperation(value = "支付接口", notes = "前提Auth，获取支付服务", response = PayResponse.class)
    @PostMapping("")
    public ResponseData pay(PayForm payForm , HttpServletRequest req) {
        String token = CurrentUser.getToken(req);
        String userId = Convert.toStr(redisUtils.get(token));
        PayRequset requset = new PayRequset();
        requset.setUserId(Integer.parseInt(userId));
        requset.setPayPassword(payForm.getPayPassword());
        requset.setTotalMoney(payForm.getTotalMoney());
        PayResponse response = payService.pay(requset);
        log.warn("pay:" + response.toString());
        // ok的话， 删缓存
        redisUtils.del(RedisConstants.USER_INFO_EXPIRE.getKey() + userId);
        return new ResponseUtil().setData(response);
    }

    @ApiOperation(value = "退款接口", notes = "前提Auth，获取退款服务", response = PayResponse.class)
    @PostMapping("back")
    public ResponseData payBack(PayBackForm payBackFrom, HttpServletRequest req) {
        String token = CurrentUser.getToken(req);
        String userId = Convert.toStr(redisUtils.get(token));
        PayBackRequest request = new PayBackRequest();
        request.setUserId(Integer.parseInt(userId));
        request.setOrderId(payBackFrom.getOrderId());
        request.setCoundId(payBackFrom.getCoundId());
        request.setSeatsIds(payBackFrom.getSeatsIds());
        request.setTotalMoney(payBackFrom.getTotalMoney());
        PayResponse response = payService.payBack(request);
        log.warn("payBack:" + response.toString());
        // 清理缓存
        redisUtils.del(RedisConstants.USER_INFO_EXPIRE.getKey() + userId);
        redisUtils.del(RedisConstants.NO_TAKE_OREDERS_EXPIRE.getKey() + userId);
        redisUtils.del(RedisConstants.SELECT_ORDER_EXPIRE.getKey() + payBackFrom.getOrderId());
        redisUtils.del(RedisConstants.COUNT_DETAIL_EXPIRE.getKey() + payBackFrom.getCoundId());
        return new ResponseUtil().setData(response);
    }
}
