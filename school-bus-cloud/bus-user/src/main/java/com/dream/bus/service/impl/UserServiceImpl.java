/**
 * @program school-bus-cloud
 * @description: UserServiceImpl
 * @author: mf
 * @create: 2020/10/31 15:30
 */

package com.dream.bus.service.impl;


import cn.hutool.core.convert.Convert;
import com.alibaba.nacos.common.utils.Md5Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.bus.common.converter.UserConverter;
import com.dream.bus.constants.SbCode;
import com.dream.bus.dao.UserMapper;
import com.dream.bus.model.User;

import com.dream.bus.service.IUserService;
import com.dream.bus.user.param.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserConverter userConverter;

    @Override
    public UserCheckResponse checkUsername(UserCheckRequest request) {
        UserCheckResponse res = new UserCheckResponse();
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_name", request.getUsername());
            User user = userMapper.selectOne(queryWrapper);
            if (user != null) {
                res.setCheckUsername(0);
                res.setCode(SbCode.USERNAME_ALREADY_EXISTS.getCode());
                res.setMsg(SbCode.USERNAME_ALREADY_EXISTS.getMessage());
            } else {
                res.setCheckUsername(1);
                res.setCode(SbCode.USERNAME_ALREADY_NO_EXISTS.getCode());
                res.setMsg(SbCode.USERNAME_ALREADY_NO_EXISTS.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("checkUsername " + e.toString());
        }
        return res;
    }

    @Override
    public UserRegisterResponse regsiter(UserRegisterRequest request) {
        UserRegisterResponse res = new UserRegisterResponse();
        User user = userConverter.res2User(request);
        Date date = new Date();
        Instant instant = date.toInstant();
        //系统默认的时区
        ZoneId zoneId = ZoneId.systemDefault();
        user.setBeginTime(LocalDateTime.ofInstant(instant, zoneId));
        user.setMoney(0.0);
        user.setPayPassword("123456");
        // 加密
        System.out.println(user);
        String md5Password = Md5Utils.getMD5(user.getUserPwd().getBytes());
        System.out.println(md5Password);
        user.setUserPwd(md5Password);
        try {
            userMapper.insert(user);
            res.setRegister(true);
            res.setCode(SbCode.SUCCESS.getCode());
            res.setMsg(SbCode.SUCCESS.getMessage());
        } catch (Exception e) {
            res.setRegister(false);
            res.setCode(SbCode.USER_REGISTER_VERIFY_FAILED.getCode());
            res.setMsg(SbCode.USER_REGISTER_VERIFY_FAILED.getMessage());
            log.info("regsiter:", e);
            return res;
        }
        return res;
    }

    @Override
    public UserLoginResponse login(UserLoginRequst request) {
        UserLoginResponse res = new UserLoginResponse();
        res.setUserId(Convert.toLong(0));
        res.setCode(SbCode.USERORPASSWORD_ERRROR.getCode());
        res.setMsg(SbCode.USERORPASSWORD_ERRROR.getMessage());
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_name", request.getUsername());
            User user = userMapper.selectOne(queryWrapper);
            System.out.println(user);
            if (user != null && user.getUuid() > 0) {
                String md5Password = Md5Utils.getMD5(request.getPassword().getBytes());
                System.out.println(md5Password);
                if (user.getUserPwd().equals(md5Password)) {
                    res.setUserId(user.getUuid());
                    res.setCode(SbCode.SUCCESS.getCode());
                    res.setMsg(SbCode.SUCCESS.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("login:",e);
            return res;
            // 这里在auth那里设置了异常
        }
        return res;
    }

    @Override
    public UserResponse getUserById(UserRequest request) {
        UserResponse response = new UserResponse();
        try {
            User user = userMapper.selectById(request.getId());
            UserDto userDto = userConverter.User2Res(user);
            System.out.println(userDto);
            response.setUserDto(userDto);
            response.setCode(SbCode.SUCCESS.getCode());
            response.setMsg(SbCode.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(SbCode.DB_EXCEPTION.getCode());
            response.setMsg(SbCode.DB_EXCEPTION.getMessage());
            log.error("getUserById", e);
            return response;
        }

        return response;
    }

    @Override
    public UserResponse updateUserInfo(UserUpdateInfoRequest request) {
        UserResponse response = new UserResponse();
        User user = userConverter.res2User(request);
        System.out.println(user);
        try {
            // 不改变密码
            // 支付密码暂时用明文
            Integer integer = userMapper.updateById(user);

            if (integer == 0) {
                response.setCode(SbCode.USER_INFOR_INVALID.getCode());
                response.setMsg(SbCode.USER_INFOR_INVALID.getMessage());
            } else {
                User user1 = userMapper.selectById(user.getUuid());
                response.setUserDto(userConverter.User2Res(user1));
                response.setCode(SbCode.SUCCESS.getCode());
                response.setMsg(SbCode.SUCCESS.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(SbCode.DB_EXCEPTION.getCode());
            response.setMsg(SbCode.DB_EXCEPTION.getMessage());
            log.error("updateUserInfo", e);
            return response;
        }
        return response;
    }
}
