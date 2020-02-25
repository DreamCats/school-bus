/**
 * @program school-bus
 * @description: UserAPI
 * @author: mf
 * @create: 2020/02/24 16:25
 */

package com.stylefeng.guns.rest.user;

import com.stylefeng.guns.rest.user.vo.*;

public interface UserAPI {

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
    UserResponse regsiter(UserRegisterRequest request);

    /**
     * 用户登陆
     * @param request
     * @return
     */
    UserResponse login(UserRequest request);

    /**
     * 通过id找用户
     * @param request
     * @return
     */
    UserResponse userById(UserRequest request);

    /**
     * 返回用户列表
     * @return
     */
    UserListResponse users();
}
