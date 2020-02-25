/**
 * @program school-bus
 * @description: UserServiceImpl
 * @author: mf
 * @create: 2020/02/25 01:07
 */

package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.dao.SbUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.SbUserT;
import com.stylefeng.guns.rest.user.UserAPI;
import com.stylefeng.guns.rest.user.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Service
public class UserServiceImpl implements UserAPI {

    @Autowired
    private SbUserTMapper sbUserTMapper;
    /**
     * 检查用户名是否已经存在
     * @param request ：username
     * @return
     */
    @Override
    public UserCheckResponse checkUsername(UserCheckRequest request) {
        UserCheckResponse res = new UserCheckResponse();
        try {
            EntityWrapper<SbUserT> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("user_name", request.getUsername());
            Integer count = sbUserTMapper.selectCount(entityWrapper);
            if (count != null && count > 0) {
                res.setCheckUsername(0);
            } else {
                res.setCheckUsername(1);
            }
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
