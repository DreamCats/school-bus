/**
 * @program school-bus-cloud
 * @description: UserController
 * @author: mf
 * @create: 2020/10/31 15:48
 */

package com.dream.bus.controller;

import cn.hutool.core.convert.Convert;
import com.dream.bus.common.CurrentUser;
import com.dream.bus.common.RedisUtils;
import com.dream.bus.constants.RedisConstants;
import com.dream.bus.constants.SbCode;
import com.dream.bus.jwt.JwtTokenUtil;
import com.dream.bus.param.*;
import com.dream.bus.service.IUserService;
import com.dream.bus.user.param.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 检查用户名接口
     * @param username
     * @return
     */
    @GetMapping("check")
    public ResponseData checkUsername(String username) {
        try {
            if (username.equals("")) {
                return new ResponseUtil<>().setErrorMsg("用户名不能为空");
            }
            UserCheckRequest req = new UserCheckRequest();
            req.setUsername(username);
            UserCheckResponse res = userService.checkUsername(req);
            log.warn("checkUsername", res.toString());
            return new ResponseUtil().setData(res);
        } catch (Exception e) {
            log.error("checkUsername\n", e);
            CommonResponse response = new CommonResponse();
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return new ResponseUtil().setData(response);
        }
    }

    /**
     * 注册接口
     * @param form
     * @param bindingResult
     * @return
     */
    @PostMapping("register")
    public ResponseData register(@Validated @RequestBody UserRegstierForm form, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                CommonResponse response = new CommonResponse();
                response.setCode(SbCode.REQUISITE_PARAMETER_NOT_EXIST.getCode());
                response.setCode(SbCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
                return new ResponseUtil<>().setData(response);
            }
            UserRegisterRequest request = new UserRegisterRequest();
            request.setUsername(form.getUsername());
            request.setPassword(form.getPassword());
            request.setPhone(form.getPhone());
            request.setEmail(form.getEmail());
            System.out.println(request);
            // 不想写那么多了
            UserRegisterResponse res = userService.regsiter(request);
            log.warn("register\n");
            return new ResponseUtil<>().setData(res);
        } catch (Exception e) {
            log.error("register\n", e);
            CommonResponse response = new CommonResponse();
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return new ResponseUtil().setData(response);
        }
    }

    /**
     * 登陆
     * @param requst
     * @return
     */
    @PostMapping("login")
    public ResponseData login(@RequestBody UserLoginRequst requst) {
        if (requst.getUsername().equals("")) {
            return new ResponseUtil<>().setErrorMsg("用户名不能为空");
        }
        if (requst.getPassword().equals("")) {
            return new ResponseUtil<>().setErrorMsg("密码不能为空");
        }
        try {
            UserLoginResponse response = userService.login(requst);
            log.warn("login" + response.toString());
            return new ResponseUtil().setData(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("login/n", e);
            CommonResponse response = new CommonResponse();
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return new ResponseUtil().setData(response);
        }
    }

    /**
     * 获取用户信息接口
     * @param req
     * @return
     */
    @GetMapping("getUserInfo")
    public ResponseData getUserById(HttpServletRequest req) {
        // 从本地缓存中取
        try {
            String token = CurrentUser.getToken(req);
            String userId = jwtTokenUtil.getUsernameFromToken(token);
            String key = RedisConstants.USER_INFO_EXPIRE.getKey()+userId;
            if (redisUtils.hasKey(key)) {
                Object obj = redisUtils.get(key);
                log.warn("getUserById->redis\n");
                return new ResponseUtil<>().setData(obj);
            }
            UserRequest request = new UserRequest();
            request.setId(Convert.toLong(userId));
            UserResponse response = userService.getUserById(request);
            redisUtils.set(key, response, RedisConstants.USER_INFO_EXPIRE.getTime());
            log.info("getUserById\n");
            return new ResponseUtil<>().setData(response);
        } catch (Exception e) {
            log.error("getUserById\n", e);
            CommonResponse response = new CommonResponse();
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return new ResponseUtil().setData(response);
        }
    }

    /**
     * 更新接口
     * @param form
     * @param req
     * @return
     */
    @PostMapping("updateInfo")
    public ResponseData updateUserInfo(@RequestBody UserUpdateForm form, HttpServletRequest req) {
        // id 从本队缓存中取
        try {
            String token = CurrentUser.getToken(req);
            String userId = jwtTokenUtil.getUsernameFromToken(token);
            String key = RedisConstants.USER_INFO_EXPIRE.getKey()+userId;
            if (redisUtils.hasKey(key)) {
                redisUtils.del(key);
            }
            UserUpdateInfoRequest request = new UserUpdateInfoRequest();
            request.setUserSex(form.getUserSex());
            request.setNickName(form.getNickName());
            request.setEmail(form.getEmail());
            request.setUserPhone(form.getUserPhone());
            request.setMoney(form.getMoney());
            request.setPayPassword(form.getPayPassword());
            request.setId(Convert.toLong(userId));
            System.out.println(request);
            UserResponse response = userService.updateUserInfo(request);
            log.info("updateUserInfo\n");
            return new ResponseUtil<>().setData(response);
        } catch (Exception e) {
            log.error("updateUserInfo\n", e);
            CommonResponse response = new CommonResponse();
            response.setCode(SbCode.SYSTEM_ERROR.getCode());
            response.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            return new ResponseUtil().setData(response);
        }
    }

    /**
     * 登出接口
     * @param req
     * @return
     */
    @GetMapping("logout")
    public ResponseData logout(HttpServletRequest req) {
        /*
            应用：
                1、前端存储JWT 【七天】 ： JWT的刷新
                2、服务器端会存储活动用户信息【30分钟】
                3、JWT里的userId为key，查找活跃用户
            退出：
                1、前端删除掉JWT
                2、后端服务器删除活跃用户缓存
            现状：
                1、前端删除掉JWT
         */
        String token = CurrentUser.getToken(req);
        String userId = jwtTokenUtil.getUsernameFromToken(token);
//        CurrentUser.deleteUserId(userId);
        redisUtils.del(userId);
        CommonResponse response = new CommonResponse();
        response.setCode(SbCode.SUCCESS.getCode());
        response.setMsg(SbCode.SUCCESS.getMessage());
        log.info("logout", response.toString());
        return new ResponseUtil<>().setData(response);
    }

    @GetMapping("localLogin")
    public UserLoginResponse localLogin(UserLoginRequst requst) {
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        try {
            userLoginResponse = userService.login(requst);
        } catch (Exception e) {
            log.error("localLogin: " + e);
            userLoginResponse.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            userLoginResponse.setCode(SbCode.SYSTEM_ERROR.getCode());
            return userLoginResponse;
        }
        return userLoginResponse;
    }

    @GetMapping("localGetUserInfo")
    public UserResponse localGetUserInfo(UserRequest request) {
        UserResponse userResponse = new UserResponse();
        try {
            userResponse = userService.getUserById(request);
        } catch (Exception e) {
            log.error("localLogin: " + e);
            userResponse.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            userResponse.setCode(SbCode.SYSTEM_ERROR.getCode());
            return userResponse;
        }
        return userResponse;
    }

    @PostMapping("localUpdateInfo")
    public UserResponse updateUserInfo(UserUpdateInfoRequest request) {
        UserResponse userResponse = new UserResponse();
        try {
            userResponse = userService.updateUserInfo(request);
        } catch (Exception e) {
            log.error("localLogin: " + e);
            userResponse.setMsg(SbCode.SYSTEM_ERROR.getMessage());
            userResponse.setCode(SbCode.SYSTEM_ERROR.getCode());
            return userResponse;
        }
        return userResponse;
    }

}
