/**
 * @program school-bus
 * @description: CurrentUser
 * @author: mf
 * @create: 2020/02/26 13:47
 */

package com.stylefeng.guns.rest.common;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;

public class CurrentUser {
    //     线程绑定的存储空间
//    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
//    public static void saveUserId(String userId){
//        threadLocal.set(userId);
//    }
//    public static String getCurrentUser(){
//        return threadLocal.get();
//    }

    // 还是用concurrentHashMap配合redis来单点，类似于qq那样
    // 键为userId， 值为token key满了怎么办，有什么方案可以清除到呢？lru
    // google guava cache 内部是类似于concurrentHashMap，并且还有一个缓冲队列，lru的。
    // 不过， 将来要是分布式的话， 本地缓冲就凉凉了...
    // 那还是用redis的hash
    private static final ConcurrentHashMap<String, String> concurrentHashMap= new ConcurrentHashMap<>();

    public static void saveUserIdAndToken(String userId, String token) {
        concurrentHashMap.put(userId, token);
    }

    public static String getTokenByUserId(String userId) {
        return concurrentHashMap.get(userId);
    }

    public static void deleteUserId(String userId) {
        concurrentHashMap.remove(userId);
    }
    // redis绑定
    public static String getToken(HttpServletRequest request) {
        final String requestHeader = request.getHeader("Authorization");
        String authToken = requestHeader.substring(7);
        return authToken;
    }
}
