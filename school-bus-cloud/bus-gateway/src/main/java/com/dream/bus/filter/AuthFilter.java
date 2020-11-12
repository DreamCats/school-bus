/**
 * @program school-bus-cloud
 * @description: AuthFilter
 * @author: mf
 * @create: 2020/11/01 12:33
 */

package com.dream.bus.filter;


import com.dream.bus.jwt.JwtProperties;
import com.dream.bus.jwt.JwtTokenUtil;
import com.dream.bus.param.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import java.util.List;

@Component
public class AuthFilter implements GlobalFilter, Ordered{

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();
        System.out.println(url);
        // 忽略auth
        if (exchange.getRequest().getURI().getPath().equals("/" + jwtProperties.getAuthPath())) {
            return chain.filter(exchange);
        }
        // 配置忽略列表
        String ignoreUrl = jwtProperties.getIgnoreUrl();
        String[] ignoreUrls = ignoreUrl.split(",");
        for (int i = 0; i < ignoreUrls.length; i++) {
            if (exchange.getRequest().getURI().getPath().startsWith(ignoreUrls[i])) {
                System.out.println("忽略列表：" + ignoreUrls[i]);
                return chain.filter(exchange);
            }
        }

        // 开始走jwt的业务逻辑
        final HttpHeaders requestHeader = exchange.getRequest().getHeaders();
        List<String> strings = requestHeader.get(jwtProperties.getHeader());
        String authToken = null;
        if (strings != null && strings.get(0).startsWith("Bearer ")) {
            // return
            authToken = strings.get(0).substring(7);
            try {
                System.out.println("authToken: " + authToken);
                String userId = jwtTokenUtil.getUsernameFromToken(authToken);
                // 判断userId在redis中是否存在
                if (!redisUtils.hasKey(userId)) {
                    // 不存在
                    return error(exchange);
                } else {
                    // 存在的话， 取出token
                    String token = (String) redisUtils.get(userId);
                    if (!token.equals(authToken)) {
                        // 如果用户的token和缓存的token不一样
                        return error(exchange);
                    }
                }

            } catch (Exception e) {
                return error(exchange);
            }

            // 验证token是否过期
            try {
                boolean flag = jwtTokenUtil.isTokenExpired(authToken);
                if (flag) {
                    return error(exchange);
                }
            } catch (Exception e) {
                return error(exchange);
            }

        } else {
            // header没有该字段或者 Bearer字段
            return error(exchange);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
    /**
     * 渲染json对象
     * @return
     */
    public static Mono<Void> renderJson(ServerHttpResponse originalResponse) {
        originalResponse.setRawStatusCode(200);
        originalResponse.getHeaders().add("Content-Type","application/json;charset=UTF-8");
        byte[] response = "{\"code\": \"003002\",\"msg\": \"token校验失败.\"}"
                .getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
        return originalResponse.writeWith(Flux.just(buffer));
    }


    public static Mono<Void> error(ServerWebExchange exchange) {
        CommonResponse response = new CommonResponse();
        response.setCode(SbCode.TOKEN_VALID_FAILED.getCode());
        response.setMsg(SbCode.TOKEN_VALID_FAILED.getMessage());
        return renderJson(exchange.getResponse());
    }
}
