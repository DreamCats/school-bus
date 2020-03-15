/**
 * @program school-bus
 * @description: UserServiceImpl
 * @author: mf
 * @create: 2020/02/25 01:07
 */

package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.constants.RetCodeConstants;
import com.stylefeng.guns.rest.common.persistence.dao.UserMapper;
import com.stylefeng.guns.rest.common.persistence.model.User;
import com.stylefeng.guns.rest.modular.user.converter.UserConverter;
import com.stylefeng.guns.rest.user.IUserService;
import com.stylefeng.guns.rest.user.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserConverter userConverter;

    /**
     * 检查用户名是否已经存在
     * @param request ：username
     * @return
     */
    @Override
    public UserCheckResponse checkUsername(UserCheckRequest request) {
        UserCheckResponse res = new UserCheckResponse();
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_name", request.getUsername());
            User user = userMapper.selectOne(queryWrapper);
            if (user != null) {
                res.setCheckUsername(0);
                res.setCode(RetCodeConstants.SUCCESS.getCode());
                res.setMsg(RetCodeConstants.SUCCESS.getMessage());
            } else {
                res.setCheckUsername(1);
                res.setCode(RetCodeConstants.USERNAME_ALREADY_EXISTS.getCode());
                res.setMsg(RetCodeConstants.USERNAME_ALREADY_EXISTS.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("checkUsername " + e.toString());
        }
        return res;
    }

    /**
     * 注册业务逻辑
     * @param request
     * @return
     */
    @Override
    public UserRegisterResponse regsiter(UserRegisterRequest request) {
        UserRegisterResponse res = new UserRegisterResponse();
        User user = userConverter.res2User(request);
        System.out.println(user);
        // 加密
        String md5Password = MD5Util.encrypt(user.getUserPwd());
        user.setUserPwd(md5Password);
        try {
            userMapper.insert(user);
            res.setRegister(true);
            res.setCode(RetCodeConstants.SUCCESS.getCode());
            res.setMsg(RetCodeConstants.SUCCESS.getMessage());
        } catch (Exception e) {
            res.setRegister(false);
            res.setCode(RetCodeConstants.USER_REGISTER_VERIFY_FAILED.getCode());
            res.setMsg(RetCodeConstants.USER_REGISTER_VERIFY_FAILED.getMessage());
            log.info("regsiter:", e);
            return res;
        }
        return res;
    }

    /**
     * 登陆业务逻辑
     * @param request
     * @return
     */
    @Override
    public UserLoginResponse login(UserLoginRequst request) {
        UserLoginResponse res = new UserLoginResponse();
        res.setUserId(0);
        res.setCode(RetCodeConstants.USERORPASSWORD_ERRROR.getCode());
        res.setMsg(RetCodeConstants.USERORPASSWORD_ERRROR.getMessage());
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_name", request.getUsername());
            User user = userMapper.selectOne(queryWrapper);
            if (user != null && user.getUuid() > 0) {
                String md5Password = MD5Util.encrypt(request.getPassword());
                if (user.getUserPwd().equals(md5Password)) {
                    res.setUserId(user.getUuid());
                    res.setCode(RetCodeConstants.SUCCESS.getCode());
                    res.setMsg(RetCodeConstants.SUCCESS.getMessage());
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

    /**
     * 通过id获取用户信息
     * @param request
     * @return
     */
    @Override
    public UserResponse getUserById(UserRequest request) {
        UserResponse response = new UserResponse();
        try {
            User user = userMapper.selectById(request.getId());
            UserDto userDto = userConverter.User2Res(user);
            System.out.println(userDto);
            response.setUserDto(userDto);
            response.setCode(RetCodeConstants.SUCCESS.getCode());
            response.setMsg(RetCodeConstants.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(RetCodeConstants.DB_EXCEPTION.getCode());
            response.setMsg(RetCodeConstants.DB_EXCEPTION.getMessage());
            log.error("getUserById", e);
            return response;
        }

        return response;
    }

    /**
     * 更新用户
     * @param request
     * @return
     */
    @Override
    public UserResponse updateUserInfo(UserUpdateInfoRequest request) {
        UserResponse response = new UserResponse();
        User user = userConverter.res2User(request);
        try {
            // 不改变密码
            // 支付密码暂时用明文
            Integer integer = userMapper.updateById(user);

            if (integer == 0) {
                response.setCode(RetCodeConstants.USER_INFOR_INVALID.getCode());
                response.setMsg(RetCodeConstants.USER_INFOR_INVALID.getMessage());
            } else {
                User user1 = userMapper.selectById(user.getUuid());
                response.setUserDto(userConverter.User2Res(user1));
                response.setCode(RetCodeConstants.SUCCESS.getCode());
                response.setMsg(RetCodeConstants.SUCCESS.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(RetCodeConstants.DB_EXCEPTION.getCode());
            response.setMsg(RetCodeConstants.DB_EXCEPTION.getMessage());
            log.error("updateUserInfo", e);
            return response;
        }
        return response;
    }
}
