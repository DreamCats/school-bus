/**
 * @program school-bus
 * @description: PayController
 * @author: mf
 * @create: 2020/03/17 16:49
 */

package com.stylefeng.guns.rest.modular.alipay;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.alipay.dto.PayResponse;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.common.RedisUtils;
import com.stylefeng.guns.rest.common.ResponseData;
import com.stylefeng.guns.rest.common.ResponseUtil;
import com.stylefeng.guns.rest.common.constants.RedisConstants;
import com.stylefeng.guns.rest.common.constants.RetCodeConstants;
import com.stylefeng.guns.rest.modular.form.PayForm;
import com.stylefeng.guns.rest.user.IUserService;
import com.stylefeng.guns.rest.user.dto.UserRequest;
import com.stylefeng.guns.rest.user.dto.UserResponse;
import com.stylefeng.guns.rest.user.dto.UserUpdateInfoRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Slf4j
@Api(value = "支付服务", description = "支付服务相关接口")
@RestController
@RequestMapping("/pay/")
public class PayController {

    @Autowired
    private RedisUtils redisUtils;

    @Reference(check = false)
    private IUserService userService;

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
        // 1. 先判断密码对不对撒
        // 先获取用户信息，可以先从redis看看
        Object obj = redisUtils.get(RedisConstants.USER_INFO_EXPIRE.getKey() + userId);
        UserResponse userResponse = new UserResponse();
        if (obj != null) {
            userResponse = (UserResponse) obj;
        } else {
            UserRequest request = new UserRequest();
            request.setId(Integer.parseInt(userId));
            userResponse = userService.getUserById(request);
        }
        PayResponse payResponse = new PayResponse();
        if (!userResponse.getUserDto().getPayPassword().equals(payForm.getPayPassword())) {
            payResponse.setCode(RetCodeConstants.PAY_PASSWORD_ERROR.getCode());
            payResponse.setMsg(RetCodeConstants.PAY_PASSWORD_ERROR.getMessage());
            return new ResponseUtil().setData(payResponse);
        }
        // 判断余额足不足
        Double totalMoney = NumberUtil.sub(userResponse.getUserDto().getMoney(), payForm.getTotalMoney());
        BigDecimal round = NumberUtil.round(totalMoney, 2);
        if (round.doubleValue() < 0) {
            payResponse.setCode(RetCodeConstants.MONEY_ERROR.getCode());
            payResponse.setMsg(RetCodeConstants.MONEY_ERROR.getMessage());
            return new ResponseUtil().setData(payResponse);
        }
        // 余额写入数据库
        UserUpdateInfoRequest request = new UserUpdateInfoRequest();
        request.setMoney(round.doubleValue());
        userService.updateUserInfo(request); // 暂时先不接受返回信息
        // ok了
        // 感觉不仅仅这么少， 虽然少了邮箱手机验证码各种验证
        payResponse.setCode(RetCodeConstants.SUCCESS.getCode());
        payResponse.setMsg(RetCodeConstants.SUCCESS.getMessage());
        // ok的话， 删缓存
        redisUtils.del(RedisConstants.USER_INFO_EXPIRE.getKey() + userId);
        return new ResponseUtil().setData(payResponse);
    }
}
