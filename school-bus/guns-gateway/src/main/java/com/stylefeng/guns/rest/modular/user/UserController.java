/**
 * @program school-bus
 * @description: UserController
 * @author: mf
 * @create: 2020/02/25 23:50
 */

package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.common.ResponseData;
import com.stylefeng.guns.rest.common.ResponseUtil;
import com.stylefeng.guns.rest.common.constants.RetCodeConstants;
import com.stylefeng.guns.rest.user.UserAPI;
import com.stylefeng.guns.rest.user.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Reference
    private UserAPI userAPI;

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
}
