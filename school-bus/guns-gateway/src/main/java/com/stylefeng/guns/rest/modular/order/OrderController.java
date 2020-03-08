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
import com.stylefeng.guns.rest.exception.CommonResponse;
import com.stylefeng.guns.rest.modular.form.PageInfo;
import com.stylefeng.guns.rest.order.IOrderSerice;
import com.stylefeng.guns.rest.order.dto.EvaluateRequest;
import com.stylefeng.guns.rest.order.dto.EvaluateResponse;
import com.stylefeng.guns.rest.order.dto.NoTakeBusRequest;
import com.stylefeng.guns.rest.order.dto.NoTakeBusResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "班车服务", description = "班车服务相关接口")
@RestController
@RequestMapping("/order/")
public class OrderController {

    @Reference(check = false)
    private IOrderSerice orderSerice;

    @ApiOperation(value = "根据订单状态获取订单接口", notes = "前提Auth，获取用户订单未乘坐服务", response = NoTakeBusResponse.class)
    @GetMapping("getNoTakeOrders")
    public ResponseData getNoTakeOrdersById(PageInfo pageInfo) {
        NoTakeBusRequest request = new NoTakeBusRequest();
        String userId = CurrentUser.getCurrentUser();
        if (userId == null) {
            CommonResponse response = new CommonResponse();
            response.setCode(RetCodeConstants.TOKEN_VALID_FAILED.getCode());
            response.setMsg(RetCodeConstants.TOKEN_VALID_FAILED.getMessage()+",请重新登陆...");
            return new ResponseUtil<>().setData(response);
        }
        request.setUserId(Integer.parseInt(userId));
        request.setCurrentPage(pageInfo.getCurrentPage());
        request.setPageSize(pageInfo.getPageSize());
        NoTakeBusResponse response = orderSerice.getNoTakeOrdersById(request);
        return new ResponseUtil().setData(response);
    }

    @ApiOperation(value = "根据评价状态获取用户订单接口", notes = "前提Auth，根据评价状态获取订单服务", response = EvaluateResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "evaluateStauts", value = "评价状态：0->未评价 1->已评价", example = "0", required = true, dataType = "String")
    })
    @GetMapping("getEvaluateOrders")
    public ResponseData getEvaluateOrdersById(PageInfo pageInfo, String evaluateStauts) {
        EvaluateRequest request = new EvaluateRequest();
        String userId = CurrentUser.getCurrentUser();
        if (userId == null) {
            CommonResponse response = new CommonResponse();
            response.setCode(RetCodeConstants.TOKEN_VALID_FAILED.getCode());
            response.setMsg(RetCodeConstants.TOKEN_VALID_FAILED.getMessage()+",请重新登陆...");
            return new ResponseUtil<>().setData(response);
        }
        request.setUserId(Integer.parseInt(userId));
        request.setCurrentPage(pageInfo.getCurrentPage());
        request.setPageSize(pageInfo.getPageSize());
        request.setEvaluateStatus(evaluateStauts);
        EvaluateResponse response = orderSerice.getEvaluateOrdersById(request);
        return new ResponseUtil().setData(response);
    }

}
