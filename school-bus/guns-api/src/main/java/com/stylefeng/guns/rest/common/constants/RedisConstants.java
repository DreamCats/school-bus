/**
 * @program school-bus
 * @description: RedisConstants
 * @author: mf
 * @create: 2020/03/16 16:49
 */

package com.stylefeng.guns.rest.common.constants;


public enum RedisConstants {

    TOKEN_EXPIRE                                 (3600, "token过期时间"),
    USER_INFO_EXPIRE                             (3600, "用户信息过期时间");
    private Integer time;
    private String message;

    RedisConstants(Integer time, String message) {
        this.time = time;
        this.message = message;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
