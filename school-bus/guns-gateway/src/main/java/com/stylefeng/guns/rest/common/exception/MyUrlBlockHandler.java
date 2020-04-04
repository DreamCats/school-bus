/**
 * @program school-bus
 * @description: MyUrlBlockHandler
 * @author: mf
 * @create: 2020/04/05 00:34
 */

package com.stylefeng.guns.rest.common.exception;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.constants.SbCode;
import com.stylefeng.guns.rest.exception.CommonResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyUrlBlockHandler implements UrlBlockHandler {
    @Override
    public void blocked(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws IOException {
        CommonResponse response = new CommonResponse();
        if (e instanceof FlowException){
            response.setCode(SbCode.FLOW_ERROR.getCode());
            response.setMsg(SbCode.FLOW_ERROR.getMessage());
        }else if (e instanceof DegradeException){
            response.setCode(SbCode.DEGRADE_ERROR.getCode());
            response.setMsg(SbCode.DEGRADE_ERROR.getMessage());
        }else if (e instanceof ParamFlowException){
            response.setCode(SbCode.PARAMFLOW_ERROR.getCode());
            response.setMsg(SbCode.PARAMFLOW_ERROR.getMessage());
        }else if (e instanceof SystemBlockException){
            response.setCode(SbCode.SYSTEMBLOCK_ERROR.getCode());
            response.setMsg(SbCode.SYSTEMBLOCK_ERROR.getMessage());
        }else if (e instanceof AuthorityException){
            response.setCode(SbCode.AUTHORITY_ERROR.getCode());
            response.setMsg(SbCode.AUTHORITY_ERROR.getMessage());
            // 设置返回json数据
            String header= MediaType.APPLICATION_JSON_UTF8_VALUE;
            httpServletResponse.setStatus(200);
            httpServletResponse.setHeader("content-Type",header);
            httpServletResponse.setContentType(header);
            httpServletResponse.getWriter().write(JSON.toJSONString(response));
        }
    }
}
