package com.stylefeng.guns.rest.modular.auth.filter;

import com.stylefeng.guns.core.util.RenderUtil;
import com.stylefeng.guns.rest.common.RedisUtils;
import com.stylefeng.guns.rest.common.ResponseUtil;
import com.stylefeng.guns.core.constants.SbCode;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.exception.CommonResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对客户端请求的jwt token验证过滤器
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:04
 */
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("当前url：" + request.getServletPath());
        log.info("当前url：" + request.getServletPath());
        if (request.getServletPath().equals("/" + jwtProperties.getAuthPath())) {
            chain.doFilter(request, response);
            return;
        }
        // 配置忽略列表
        String ignoreUrl = jwtProperties.getIgnoreUrl();
        String[] ignoreUrls = ignoreUrl.split(",");
        for(int i=0;i<ignoreUrls.length;i++){
            if(request.getServletPath().startsWith(ignoreUrls[i])){
                chain.doFilter(request, response);
                return;
            }
        }

        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
//            if (redisUtils.get(authToken) == null) {
//                CommonResponse response1 = new CommonResponse();
//                response1.setCode(SbCode.TOKEN_VALID_FAILED.getCode());
//                response1.setMsg(SbCode.TOKEN_VALID_FAILED.getMessage());
//                RenderUtil.renderJson(response, new ResponseUtil<>().setData(response1));
//                return; // 你虽然携带了， 但是我redis的不存在，说明你传的是过期的
//            }
//            // 通过Token获取userID，并且将之存入Threadlocal，以便后续业务调用
//            String userId = jwtTokenUtil.getUsernameFromToken(authToken);
//            if(userId == null){
//                return;
//            } else {
//                CurrentUser.saveUserId(userId);
//            }

            // 为了，满足单点登录类似于qq， 继续优化
            // 先获取id
            try {
                System.out.println(authToken);
                String userId = jwtTokenUtil.getUsernameFromToken(authToken);
                if (!redisUtils.hasKey(userId)) {
                    CommonResponse response1 = new CommonResponse();
                    response1.setCode(SbCode.TOKEN_VALID_FAILED.getCode());
                    response1.setMsg(SbCode.TOKEN_VALID_FAILED.getMessage());
                    RenderUtil.renderJson(response, new ResponseUtil<>().setData(response1));
                    return;
                } else {
                    // 取出token
                    String token = (String) redisUtils.get(userId);
                    if(!token.equals(authToken)) {
                        CommonResponse response1 = new CommonResponse();
                        response1.setCode(SbCode.TOKEN_VALID_FAILED.getCode());
                        response1.setMsg(SbCode.TOKEN_VALID_FAILED.getMessage());
                        RenderUtil.renderJson(response, new ResponseUtil<>().setData(response1));
                        return;
                    }
                }
            } catch (Exception e) {
                CommonResponse response1 = new CommonResponse();
                response1.setCode(SbCode.TOKEN_VALID_FAILED.getCode());
                response1.setMsg(SbCode.TOKEN_VALID_FAILED.getMessage());
                RenderUtil.renderJson(response, new ResponseUtil<>().setData(response1));
                return;
            }
            //验证token是否过期,包含了验证jwt是否正确
            try {
                boolean flag = jwtTokenUtil.isTokenExpired(authToken);
                if (flag) {
//                    RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_EXPIRED.getCode(), BizExceptionEnum.TOKEN_EXPIRED.getMessage()));
                    CommonResponse response1 = new CommonResponse();
                    response1.setCode(SbCode.TOKEN_VALID_FAILED.getCode());
                    response1.setMsg(SbCode.TOKEN_VALID_FAILED.getMessage());
                    RenderUtil.renderJson(response, new ResponseUtil<>().setData(response1));
                    return;
                }
            } catch (JwtException e) {
                //有异常就是token解析失败
//                RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
                CommonResponse response1 = new CommonResponse();
                response1.setCode(SbCode.TOKEN_VALID_FAILED.getCode());
                response1.setMsg(SbCode.TOKEN_VALID_FAILED.getMessage());
                RenderUtil.renderJson(response, new ResponseUtil<>().setData(response1));
                return;
            }
        } else {
            //header没有带Bearer字段
//            RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
            CommonResponse response1 = new CommonResponse();
            response1.setCode(SbCode.TOKEN_VALID_FAILED.getCode());
            response1.setMsg(SbCode.TOKEN_VALID_FAILED.getMessage());
            RenderUtil.renderJson(response, new ResponseUtil<>().setData(response1));
            return;
        }
        chain.doFilter(request, response);
    }
}