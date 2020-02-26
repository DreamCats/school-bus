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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "UserController(用户模块)")
@RequestMapping("/user/")
public class UserController {

    @Reference
    private UserAPI userAPI;

    @ApiOperation("通过username检查用户是否存在")
    @ApiImplicitParam(name = "username", value = "用户", paramType = "path", required = true)
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
}
