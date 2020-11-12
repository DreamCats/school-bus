/**
 * @program school-bus
 * @description: RedisConstants
 * @author: mf
 * @create: 2020/03/16 16:49
 */

package com.dream.bus.constants;


public enum RedisConstants {

    TOKEN_EXPIRE(3600, "token过期时间"), // 1 小时
    USER_INFO_EXPIRE(3600, "用户信息过期时间", "getUserById "), //1小时
    COUNT_DETAIL_EXPIRE(600, "场次详情过期时间", "getCountDetailById "),// 10分钟
    COUNTS_EXPIRE(600, "场次列表", "getCount "),// 10分钟
    COUNTS_PAGES_EXPIRE(-1, "场次列表页数", "getCount "),// 没有时间
    NO_TAKE_OREDERS_EXPIRE(600, "未乘坐订单列表", "getNoTakeOrdersById "),// 10分钟
    NO_PAY_ORDERS_EXPIRE(600, "未支付订单列表", "getNoPayOrdersById "),// 10分钟
    EVALUATE_ORDERS_EXPIRE(600, "评价订单列表", "getEvaluateOrdersById "),// 10分钟
    SELECT_ORDER_EXPIRE(600, "订单详情", "selectOrderById "),// 10分钟
    ORDER_CANCLE_EXPIRE(300, "定时取消订单", "order_cancle_expire "),// 10分钟
    ORDER_EXCEPTION_CANCLE_EXPIRE(2, "异常取消订单", "order_exception_cancle_expire "),// 2s
    PAY_EXCEPTION_CANCLE_EXPIRE(2, "异常回退金额", "pay_exception_cancle_expire "),// 2s
    ADD_ORDER_EXPIRE(1, "添加订单", "add_order_expire "),// 2s
    BIND_SEATS_EXPIRE(1, "绑定座位", "bind_seats_expire ");// 2s

    private Integer time;
    private String message;
    private String key;

    RedisConstants(Integer time, String message, String key) {
        this.time = time;
        this.message = message;
        this.key = key;
    }

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
