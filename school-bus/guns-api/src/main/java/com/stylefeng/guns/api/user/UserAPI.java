/**
 * @program school-bus
 * @description: UserAPI
 * @author: mf
 * @create: 2020/02/24 16:25
 */

package com.stylefeng.guns.api.user;

import com.stylefeng.guns.api.user.vo.UserListResponse;
import com.stylefeng.guns.api.user.vo.UserRegisterRequest;
import com.stylefeng.guns.api.user.vo.UserRequest;
import com.stylefeng.guns.api.user.vo.UserResponse;

public interface UserAPI {

    /**
     * 检查用户名是否存在
     * @param request
     * @return
     */
    UserResponse checkUsername(UserRequest request);

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
