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

    /**
     * 注册业务逻辑
     * @param request
     * @return
     */
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
            SbUserT sbUserT = new SbUserT();
            sbUserT.setUserName(request.getUsername());
            SbUserT sbUserT1 = sbUserTMapper.selectOne(sbUserT);
            if (sbUserT1 != null && sbUserT1.getUuid() > 0) {
                String md5Password = MD5Util.encrypt(request.getPassword());
                if (sbUserT1.getUserPwd().equals(md5Password)) {
                    res.setUserId(sbUserT1.getUuid());
                    res.setCode(RetCodeConstants.SUCCESS.getCode());
                    res.setMsg(RetCodeConstants.SUCCESS.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public UserResponse getUserById(UserRequest request) {
        UserResponse response = new UserResponse();
        SbUserT sbUserT = sbUserTMapper.selectById(request.getId());
        UserVo userVo = userConverter.sbUserT2Res(sbUserT);
        response.setUserVo(userVo);
        response.setCode(RetCodeConstants.SUCCESS.getCode());
        response.setMsg(RetCodeConstants.SUCCESS.getMessage());
        return response;
    }

    @Override
    public UserResponse updateUserInfo(UserUpdateInfoRequest request) {
        UserResponse response = new UserResponse();
        SbUserT sbUserT = userConverter.res2SbUserT(request);
        // 不改变密码
        Integer integer = sbUserTMapper.updateById(sbUserT);
        if (integer == 0) {
            response.setCode(RetCodeConstants.USER_INFOR_INVALID.getCode());
            response.setMsg(RetCodeConstants.USER_INFOR_INVALID.getMessage());
        }
        SbUserT sbUserT1 = sbUserTMapper.selectById(sbUserT.getUuid());
        response.setUserVo(userConverter.sbUserT2Res(sbUserT1));
        response.setCode(RetCodeConstants.SUCCESS.getCode());
        response.setMsg(RetCodeConstants.SUCCESS.getMessage());
        return response;
    }
}
