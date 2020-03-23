/**
 * @program school-bus
 * @description: UserAPI
 * @author: mf
 * @create: 2020/02/24 16:25
 */

package com.stylefeng.guns.rest.user;

import com.stylefeng.guns.rest.user.dto.*;

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
