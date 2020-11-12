/**
 * @program school-bus-cloud
 * @description: AuthController
 * @author: mf
 * @create: 2020/11/03 13:30
 */

package com.dream.bus.controller;

import com.dream.bus.auth.param.AuthRequest;
import com.dream.bus.common.RedisUtils;
import com.dream.bus.constants.RedisConstants;
import com.dream.bus.constants.SbCode;
import com.dream.bus.jwt.JwtTokenUtil;
import com.dream.bus.param.CommonBindingResult;
import com.dream.bus.param.ResponseData;
import com.dream.bus.param.ResponseUtil;
import com.dream.bus.user.UserClient;
import com.dream.bus.user.param.UserLoginRequst;
import com.dream.bus.user.param.UserLoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserClient userClient;

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping(value = "${jwt.auth-path}")
    public ResponseData createAuthenticationToken(@Validated AuthRequest authRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error("参数：{}校验失败，原因：{}", fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseUtil<>().setErrorMsg("用户参数设置错误:" + CommonBindingResult.getErrors(bindingResult));
        }
        UserLoginRequst req = new UserLoginRequst();
        req.setUsername(authRequest.getUserName());
        req.setPassword(authRequest.getPassword());
        Object result = userClient.login(req).getResult();
        System.out.println(result);
        LinkedHashMap res = (LinkedHashMap) result;
        UserLoginResponse res1 = new UserLoginResponse();
        long id = Long.parseLong(String.valueOf(res.get("userId")));
        if (0 != id) {
            String userId = "" + id;
            // 说明存在
            if (redisUtils.hasKey(userId)) {
                // 说明在别处已经登陆过了， 要删除
                redisUtils.del(userId);
            }
            res1.setUserId(id);
            res1.setRandomKey(jwtTokenUtil.getRandomKey());
            String token = jwtTokenUtil.generateToken(userId, res1.getRandomKey());
            res1.setToken(token);
            res1.setCode((String) res.get("code"));
            res1.setMsg((String) res.get("msg"));
            redisUtils.set(userId, token, RedisConstants.TOKEN_EXPIRE.getTime());
            return new ResponseUtil<>().setData(res1);

        } else {
            return new ResponseUtil<>().setErrorMsg("账号密码错误");
        }
    }
}
