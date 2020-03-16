/**
 * @program school-bus
 * @description: RedisConstants
 * @author: mf
 * @create: 2020/03/16 16:49
 */

package com.stylefeng.guns.rest.common.constants;


public enum RedisConstants {

    TOKEN_EXPIRE                                 (3600, "token过期时间"), // 1 小时
    USER_INFO_EXPIRE                             (3600, "用户信息过期时间"), //1小时
    COUNT_DETAIL_EXPIRE                             (600, "场次详情过期时间"),// 10分钟
    COUNTS_EXPIRE                                (600, "场次列表"),// 10分钟
    NO_TAKE_OREDERS_EXPIRE                                (600, "未乘坐订单列表"),// 10分钟
    NO_PAY_ORDERS_EXPIRE                                (600, "未支付订单列表"),// 10分钟
    EVALUATE_ORDERS_EXPIRE                                (600, "评价订单列表"),// 10分钟
    SELECT_ORDER_EXPIRE                                (600, "评价订单列表");// 10分钟
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
