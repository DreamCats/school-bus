/**
 * @program school-bus
 * @description: CurrentUser
 * @author: mf
 * @create: 2020/02/26 13:47
 */

package com.stylefeng.guns.rest.common;

public class CurrentUser {
    // 线程绑定的存储空间
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    public static void saveUserId(String userId){
        threadLocal.set(userId);
    }
    public static String getCurrentUser(){
        return threadLocal.get();
    }
}
