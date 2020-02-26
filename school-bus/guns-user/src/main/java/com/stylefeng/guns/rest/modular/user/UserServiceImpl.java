/**
 * @program school-bus
 * @description: UserServiceImpl
 * @author: mf
 * @create: 2020/02/25 01:07
 */

package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.constants.RetCodeConstants;
import com.stylefeng.guns.rest.common.persistence.dao.SbUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.SbUserT;
import com.stylefeng.guns.rest.modular.user.converter.UserConverter;
import com.stylefeng.guns.rest.user.UserAPI;
import com.stylefeng.guns.rest.user.vo.*;
import jdk.nashorn.internal.runtime.regexp.joni.constants.EncloseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Service
public class UserServiceImpl implements UserAPI {

    @Autowired
    private SbUserTMapper sbUserTMapper;

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
            EntityWrapper<SbUserT> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("user_name", request.getUsername());
            Integer count = sbUserTMapper.selectCount(entityWrapper);
            if (count != null && count > 0) {
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

    @Override
    public UserRegisterResponse regsiter(UserRegisterRequest request) {
        UserRegisterResponse res = new UserRegisterResponse();
        SbUserT sbUserT = userConverter.res2SbUserT(request);
        System.out.println(sbUserT);
        // 加密
        String md5Password = MD5Util.encrypt(sbUserT.getUserPwd());
        sbUserT.setUserPwd(md5Password);
        Integer insert = sbUserTMapper.insert(sbUserT);
        if (insert > 0) {
            res.setRegister(true);
            res.setCode(RetCodeConstants.SUCCESS.getCode());
            res.setMsg(RetCodeConstants.SUCCESS.getMessage());
        } else {
            res.setRegister(false);
            res.setCode(RetCodeConstants.USER_REGISTER_VERIFY_FAILED.getCode());
            res.setMsg(RetCodeConstants.USER_REGISTER_VERIFY_FAILED.getMessage());
        }
        return res;
    }

    @Override
    public UserLoginResponse login(UserLoginRequst request) {
        UserLoginResponse res = new UserLoginResponse();
        res.setUserId(0);
        try {
            SbUserT sbUserT = new SbUserT();
            sbUserT.setUserName(request.getUsername());
            SbUserT sbUserT1 = sbUserTMapper.selectOne(sbUserT);
            if (sbUserT1 != null && sbUserT.getUuid() > 0) {
                String md5Password = MD5Util.encrypt(request.getPassword());
                if (sbUserT1.getUserPwd().equals(md5Password)) {
                    res.setUserId(sbUserT1.getUuid());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
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
