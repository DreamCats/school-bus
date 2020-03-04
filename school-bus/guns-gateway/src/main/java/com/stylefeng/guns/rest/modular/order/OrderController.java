/**
 * @program school-bus
 * @description: OrderController
 * @author: mf
 * @create: 2020/03/04 21:59
 */

package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.common.ResponseData;
import com.stylefeng.guns.rest.common.ResponseUtil;
import com.stylefeng.guns.rest.common.constants.RetCodeConstants;
import com.stylefeng.guns.rest.modular.form.PageInfo;
import com.stylefeng.guns.rest.order.IOrderSerice;
import com.stylefeng.guns.rest.order.dto.NoTakeBusRequest;
import com.stylefeng.guns.rest.order.dto.NoTakeBusResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "班车服务", description = "班车服务相关接口")
@RestController
@RequestMapping("/order/")
public class OrderController {

    @Reference
    private IOrderSerice orderSerice;

    @ApiOperation(value = "获取用户订单接口", notes = "前提Auth，获取用户订单未乘坐服务", response = ResponseData.class)
    @ApiImplicitParam(name = "分页信息")
    @GetMapping("getNoTakeOrder")
    public ResponseData getNoTakeOrdersById(PageInfo pageInfo) {
        NoTakeBusRequest request = new NoTakeBusRequest();
        String userId = CurrentUser.getCurrentUser();
        if (userId == null) {
            return new ResponseUtil<>().setErrorMsg("请重新登陆..");
        }
        request.setUserId(Integer.parseInt(userId));
        request.setCurrentPage(pageInfo.getCurrentPage());
        request.setPageSize(pageInfo.getPageSize());
        NoTakeBusResponse response = orderSerice.getNoTakeOrdersById(request);
        if (!response.getCode().equals(RetCodeConstants.SUCCESS.getCode())) {
            return new ResponseUtil<>().setErrorMsg(response.getMsg());
        }
        return new ResponseUtil().setData(response);
    }
}
