package com.dream.bus.constants;

public enum MqTags {
    ORDER_CANCEL("order_cancel", "订单取消异常"),
    ORDER_SEATS_CANCEL("order_seats_cancel", "判断座位异常"),
    ORDER_ADD_SEATS_CANCLE("order_add_seats_cancle", "更新座位异常"),
    ORDER_CALC_MONEY_CANCLE("order_calc_money_cancle", "计算总金额异常"),
    ORDER_ADD_CANCLE("order_add_cancle", "添加订单异常"),
    PAY_CANCLE("pay_cancle", "支付异常"),
    PAY_CHECK_CANCLE("pay_check_cancle", "校验支付密码和余额"),
    PAY_MONEY_CANCLE("pay_money_cancle", "支付余额写入异常"),
    ;
    private String tag;
    private String message;


    MqTags(String tag, String message) {
        this.tag = tag;
        this.message = message;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
