/**
 * @program school-bus
 * @description: UserController
 * @author: mf
 * @create: 2020/02/25 23:50
 */

package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.common.ResponseData;
import com.stylefeng.guns.rest.common.ResponseUtil;
import com.stylefeng.guns.rest.common.constants.RetCodeConstants;
import com.stylefeng.guns.rest.user.IUserService;
import com.stylefeng.guns.rest.user.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Reference
    private IUserService userAPI;

    @GetMapping("check")
    public ResponseData checkUsername(String username) {
        UserCheckRequest req = new UserCheckRequest();
        req.setUsername(username);
        UserCheckResponse res = userAPI.checkUsername(req);
        if (!res.getCode().equals(RetCodeConstants.SUCCESS.getCode())) {
            return new ResponseUtil<>().setErrorMsg(res.getMsg());
        }
        return new ResponseUtil().setData(res);
    }

    @PostMapping("register")
    public ResponseData register(@RequestBody UserRegisterRequest request) {
        if (StringUtils.isBlank(request.getUsername())) {
            return new ResponseUtil<>().setErrorMsg("没有用户名");
        }
        if (StringUtils.isBlank(request.getPassword())) {
            return new ResponseUtil<>().setErrorMsg("没有密码");
        }
        // 不想写那么多了
        UserRegisterResponse res = userAPI.regsiter(request);
        if (!res.getCode().equals(RetCodeConstants.SUCCESS.getCode())) {
            return new ResponseUtil<>().setErrorMsg("服务器内部错误..");
        }
        return new ResponseUtil<>().setData(res);
    }

    @GetMapping("getUserInfo")
    public ResponseData getUserById() {
        // 从本地缓存中取
        String userId = CurrentUser.getCurrentUser();
        if (userId == null) {
            return new ResponseUtil<>().setErrorMsg("请重新登陆..");
        }
        UserRequest request = new UserRequest();
        request.setId(Integer.parseInt(userId));
        UserResponse response = userAPI.getUserById(request);
        if (!response.getCode().equals(RetCodeConstants.SUCCESS.getCode())) {
            return new ResponseUtil<>().setErrorMsg("服务器内部错误..");
        }
        return new ResponseUtil<>().setData(response);
    }

    @PostMapping("updateInfo")
    public ResponseData updateUserInfo(UserUpdateInfoRequest request) {
        // id 从本队缓存中取
        String userId = CurrentUser.getCurrentUser();
        if (userId == null) {
            return new ResponseUtil<>().setErrorMsg("请重新登陆...");
        }
        request.setId(Integer.parseInt(userId));
        UserResponse response = userAPI.updateUserInfo(request);
        if (!response.getCode().equals(RetCodeConstants.SUCCESS.getCode())) {
            return new ResponseUtil<>().setErrorMsg("服务器内部错误..");
        }
        return new ResponseUtil<>().setData(response);
    }

    @GetMapping("logout")
    public ResponseData logout() {
        /*
            应用：
                1、前端存储JWT 【七天】 ： JWT的刷新
                2、服务器端会存储活动用户信息【30分钟】
                3、JWT里的userId为key，查找活跃用户
            退出：
                1、前端删除掉JWT
                2、后端服务器删除活跃用户缓存
            现状：
                1、前端删除掉JWT
         */
        return new ResponseUtil<>().setData("退出成功...");
    }
}
