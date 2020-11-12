/**
 * @program school-bus-cloud
 * @description: IUserService
 * @author: mf
 * @create: 2020/10/31 15:29
 */
package com.dream.bus.service;


import com.dream.bus.user.param.*;

public interface IUserService {

    /**
     * 检查用户名是否存在
     * @param request：username
     * @return
     */
    UserCheckResponse checkUsername(UserCheckRequest request);

    /**
     * 注册
     * @param request
     * @return
     */
    UserRegisterResponse regsiter(UserRegisterRequest request);

    /**
     * 用户登陆
     * @param request
     * @return
     */
    UserLoginResponse login(UserLoginRequst request);

    /**
     * 通过id找用户
     * @param request
     * @return
     */
    UserResponse getUserById(UserRequest request);

    /**
     * 更新用户信息
     * @param request
     * @return
     */
    UserResponse updateUserInfo(UserUpdateInfoRequest request);
}
