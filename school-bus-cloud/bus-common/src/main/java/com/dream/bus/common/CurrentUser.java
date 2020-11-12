/**
 * @program school-bus-cloud
 * @description: CurrentUser
 * @author: mf
 * @create: 2020/11/03 15:20
 */

package com.dream.bus.common;

import javax.servlet.http.HttpServletRequest;

public class CurrentUser {
    public static String getToken(HttpServletRequest request) {
        final String requestHeader = request.getHeader("Authorization");
        String authToken = requestHeader.substring(7);
        return authToken;
    }
}
