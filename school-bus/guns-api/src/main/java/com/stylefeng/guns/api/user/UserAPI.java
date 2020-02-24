/**
 * @program school-bus
 * @description: UserAPI
 * @author: mf
 * @create: 2020/02/24 16:25
 */

package com.stylefeng.guns.api.user;

import com.stylefeng.guns.api.user.vo.UserListResponse;
import com.stylefeng.guns.api.user.vo.UserRequest;
import com.stylefeng.guns.api.user.vo.UserResponse;

public interface UserAPI {

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
