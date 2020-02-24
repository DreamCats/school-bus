/**
 * @program school-bus
 * @description: UserServiceImpl
 * @author: mf
 * @create: 2020/02/25 01:07
 */

package com.stylefeng.guns.api.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.vo.UserListResponse;
import com.stylefeng.guns.api.user.vo.UserRegisterRequest;
import com.stylefeng.guns.api.user.vo.UserRequest;
import com.stylefeng.guns.api.user.vo.UserResponse;
import org.springframework.stereotype.Component;


@Component
@Service
public class UserServiceImpl implements UserAPI {

    @Override
    public UserResponse checkUsername(UserRequest request) {
        return null;
    }

    @Override
    public UserResponse regsiter(UserRegisterRequest request) {
        return null;
    }

    @Override
    public UserResponse login(UserRequest request) {
        return null;
    }

    @Override
    public UserResponse userById(UserRequest request) {
        return null;
    }

    @Override
    public UserListResponse users() {
        return null;
    }
}
