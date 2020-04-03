/**
 * @program school-bus
 * @description: UserController
 * @author: mf
 * @create: 2020/02/25 23:50
 */

package com.stylefeng.guns.rest.modular.user;

import cn.hutool.core.convert.Convert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.common.*;
import com.stylefeng.guns.core.constants.RedisConstants;
import com.stylefeng.guns.core.constants.SbCode;
import com.stylefeng.guns.rest.exception.CommonResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.form.UserRegstierForm;
import com.stylefeng.guns.rest.modular.form.UserUpdateForm;
import com.stylefeng.guns.rest.user.IUserService;
import com.stylefeng.guns.rest.user.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(value = "用户服务", description = "用户服务相关接口")
@RestController
@RequestMapping("/user/")
public class UserController {

    @Reference(check = false)
    private IUserService userAPI;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 检查用户名接口
     * @param username
     * @return
     */
    @ApiOperation(value = "检查用户名接口", notes = "给定用户名，查询是否存在", response = UserCheckResponse.class)
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query")
    @GetMapping("check")
    public ResponseData checkUsername(String username) {
        if (username.equals("")) {
            return new ResponseUtil<>().setErrorMsg("用户名不能为空");
        }
        UserCheckRequest req = new UserCheckRequest();
        req.setUsername(username);
        UserCheckResponse res = userAPI.checkUsername(req);
        log.info("checkUsername", res.toString());
        return new ResponseUtil().setData(res);
    }

    /**
     * 注册接口
     * @param form
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "注册接口", notes = "用户注册相关信息", response = UserRegisterResponse.class)
    @PostMapping("register")
    public ResponseData register(@Validated UserRegstierForm form, BindingResult bindingResult) {
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
        // 不想写那么多了
        UserRegisterResponse res = userAPI.regsiter(request);
        log.info("register", res.toString());
        return new ResponseUtil<>().setData(res);
    }

    /**
     * 获取用户信息接口
     * @param req
     * @return
     */
    @ApiOperation(value = "获取用户信息接口", notes = "获取用户相关信息，前提请获取用户token放在headers中", response = UserResponse.class)
    @GetMapping("getUserInfo")
    public ResponseData getUserById(HttpServletRequest req) {
        // 从本地缓存中取
        String token = CurrentUser.getToken(req);
        String userId = jwtTokenUtil.getUsernameFromToken(token);
        Object obj = redisUtils.get(RedisConstants.USER_INFO_EXPIRE.getKey()+userId);
        if (obj != null) {
            log.warn("getUserById->redis:" + obj.toString());
            return new ResponseUtil<>().setData(obj);
        }
        UserRequest request = new UserRequest();
        request.setId(Integer.parseInt(userId));
        UserResponse response = userAPI.getUserById(request);
        redisUtils.set(RedisConstants.USER_INFO_EXPIRE.getKey()+userId, response, RedisConstants.USER_INFO_EXPIRE.getTime());
        log.info("getUserById", response.toString());
        return new ResponseUtil<>().setData(response);
    }

    /**
     * 更新接口
     * @param form
     * @param req
     * @return
     */
    @ApiOperation(value = "更新接口", notes = "更新用户相关信息", response = UserResponse.class)
    @PostMapping("updateInfo")
    public ResponseData updateUserInfo(UserUpdateForm form, HttpServletRequest req) {
        // id 从本队缓存中取
        String token = CurrentUser.getToken(req);
        String userId = jwtTokenUtil.getUsernameFromToken(token);
        Object obj = redisUtils.get(RedisConstants.USER_INFO_EXPIRE.getKey()+userId);
        if (obj != null) {
            redisUtils.del(RedisConstants.USER_INFO_EXPIRE.getKey()+userId);
        }
        UserUpdateInfoRequest request = new UserUpdateInfoRequest();
        request.setUserSex(form.getUserSex());
        request.setNickName(form.getNickName());
        request.setEmail(form.getEmail());
        request.setUserPhone(form.getUserPhone());
        request.setMoney(form.getMoney());
        request.setPayPassword(form.getPayPassword());
        request.setId(Integer.parseInt(userId));
        UserResponse response = userAPI.updateUserInfo(request);
        log.info("updateUserInfo", response.toString());
        return new ResponseUtil<>().setData(response);
    }

    /**
     * 登出接口
     * @param req
     * @return
     */
    @ApiOperation(value = "登出接口", notes = "用户登出，暂时是前端删除token", response = CommonResponse.class)
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
}
