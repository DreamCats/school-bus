/**
 * @program school-bus
 * @description: UserServiceImpl
 * @author: mf
 * @create: 2020/02/25 01:07
 */

package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.user.UserAPI;
import com.stylefeng.guns.rest.user.vo.UserListResponse;
import com.stylefeng.guns.rest.user.vo.UserRegisterRequest;
import com.stylefeng.guns.rest.user.vo.UserRequest;
import com.stylefeng.guns.rest.user.vo.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Service
public class UserServiceImpl implements UserAPI {

    /**
     * 检查用户名是否已经存在
     * @param request ：username
     * @return
     */
    @Override
    public UserResponse checkUsername(UserRequest request) {
        UserResponse res = new UserResponse();
        try {
            // 先测试一下
            res.setCode("200");
            res.setMsg("demo");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("checkUsername " + e.toString());
        }
        return res;
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
