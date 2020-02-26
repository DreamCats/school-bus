package com.stylefeng.guns.rest.modular.auth.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.rest.common.ResponseData;
import com.stylefeng.guns.rest.common.ResponseUtil;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import com.stylefeng.guns.rest.user.UserAPI;
import com.stylefeng.guns.rest.user.vo.UserLoginRequst;
import com.stylefeng.guns.rest.user.vo.UserLoginResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource(name = "simpleValidator")
    private IReqValidator reqValidator;

    @Reference
    private UserAPI userAPI;

    @RequestMapping(value = "${jwt.auth-path}")
    public ResponseData createAuthenticationToken(AuthRequest authRequest) {
        if (StringUtils.isBlank(authRequest.getUserName())) {
            return new ResponseUtil<>().setErrorMsg("用户名不能为空");
        }
        if (StringUtils.isBlank(authRequest.getPassword())) {
            return new ResponseUtil<>().setErrorMsg("密码不能为空");
        }
        UserLoginRequst req = new UserLoginRequst();
        req.setUsername(authRequest.getUserName());
        req.setPassword(authRequest.getPassword());
        UserLoginResponse res = userAPI.login(req);
        if (res.getUserId() != 0) {
            res.setRandomKey(jwtTokenUtil.getRandomKey());
            res.setToken(jwtTokenUtil.generateToken(""+res.getUserId(), res.getRandomKey()));
            return new ResponseUtil<>().setData(res);
        } else {
            return new ResponseUtil<>().setErrorMsg("账号密码错误");
        }
    }
}
