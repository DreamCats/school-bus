/**
 * @program school-bus-cloud
 * @description: SbCode
 * @author: mf
 * @create: 2020/10/31 15:38
 */

package com.dream.bus.filter;

public enum SbCode {
    // 系统公用
    SUCCESS("000000", "成功"),
    USERORPASSWORD_ERRROR("003001", "用户名或密码不正确"),
    TOKEN_VALID_FAILED("003002", "token校验失败"),
    USERNAME_ALREADY_EXISTS("003003", "用户名已存在"),
    USER_REGISTER_FAILED("003004", "注册失败，请联系管理员"),
    KAPTCHA_CODE_ERROR("003005", "验证码不正确"),
    USER_ISVERFIED_ERROR("003006", "用户名尚未激活"),
    USER_REGISTER_VERIFY_FAILED("003007", "用户注册失败插入验证数据失败"),
    USER_INFOR_INVALID("003008", "用户信息不合法"),
    USERNAME_ALREADY_NO_EXISTS("003009", "用户名不存在"),

    REQUEST_FORMAT_ILLEGAL("003060", "请求数据格式非法"),
    REQUEST_IP_ILLEGAL("003061", "请求IP非法"),
    REQUEST_CHECK_FAILURE("003062", "请求数据校验失败"),
    DATA_NOT_EXIST("003070", "数据不存在"),
    DATA_REPEATED("003071", "数据重复"),
    REQUEST_DATA_NOT_EXIST("003072", "传入对象不能为空"),
    REQUEST_DATA_ERROR("003074", "必要的参数不正确"),
    REQUISITE_PARAMETER_NOT_EXIST("003073", "必要的参数不能为空"),
    PERMISSION_DENIED("003091", "权限不足"),
    DB_EXCEPTION("003097", "数据库异常"),
    SYSTEM_TIMEOUT("003098", "系统超时"),
    SYSTEM_ERROR("003099", "系统错误"),

    USERVERIFY_INFOR_INVALID("003200", "用户注册验证验证信息不合法"),

    SELECTED_SEATS("004000", "座位已被选择，请重新选择座位"),
    PAY_PASSWORD_ERROR("004001", "支付密码错误"),
    MONEY_ERROR("004002", "用户余额不足，请充值"),

    FLOW_ERROR("005001", "已限流，请稍后再试哦"),
    DEGRADE_ERROR("005002", "已降级，请稍后再试哦"),
    PARAMFLOW_ERROR("005003", "已参数限流，请稍后再试哦"),
    SYSTEMBLOCK_ERROR("005004", "系统规则，请稍后再试哦"),
    AUTHORITY_ERROR("005005", "认证限流，请稍后再试哦");

    private String code;
    private String message;

    SbCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     *
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>message</tt>.
     *
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>message</tt>.
     *
     * @param message value to be assigned to property message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public static String getMessage(String code) {
        for (SbCode s : SbCode.values()) {
            if (null == code)
                break;
            if (s.code.equals(code)) {
                return s.message;
            }
        }
        return null;
    }
}

