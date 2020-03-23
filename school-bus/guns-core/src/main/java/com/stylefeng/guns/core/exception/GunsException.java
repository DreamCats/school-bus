/**
 * @program school-bus
 * @description: GunsException
 * @author: mf
 * @create: 2020/03/18 23:32
 */

package com.stylefeng.guns.core.exception;

public class GunsException extends RuntimeException{
    private Integer code;

    private String message;

    public GunsException(ServiceExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
