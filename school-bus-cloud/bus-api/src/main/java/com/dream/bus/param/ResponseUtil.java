/**
 * @program school-bus-cloud
 * @description: ResponseUtil
 * @author: mf
 * @create: 2020/10/31 15:53
 */

package com.dream.bus.param;

public class ResponseUtil<T> {
    private ResponseData<T> responseData;


    /**
     * 构造函数
     */
    public ResponseUtil() {
        responseData = new ResponseData<T>();
        responseData.setSuccess(true);
        responseData.setMessage("success");
        responseData.setCode(200);
    }

    /**
     * 封装结果
     * @param t
     * @return
     */
    public ResponseData<T> setData(T t) {
        this.responseData.setResult(t);
        this.responseData.setSuccess(true);
        responseData.setCode(200);
        return this.responseData;
    }

    /**
     * 封装结果以及消息
     * @param t
     * @param msg
     * @return
     */
    public ResponseData<T> setData(T t, String msg) {
        this.responseData.setResult(t);
        this.responseData.setMessage(msg);
        this.responseData.setSuccess(true);
        responseData.setCode(200);
        return responseData;
    }

    /**
     * 封装错误信息
     * @param msg
     * @return
     */
    public ResponseData<T> setErrorMsg(String msg) {
        this.responseData.setSuccess(false);
        this.responseData.setMessage(msg);
        responseData.setCode(500);
        return this.responseData;
    }

    /**
     * 错误返回码及消息
     * @param code
     * @param msg
     * @return
     */
    public ResponseData<T> setErrorMsg(Integer code, String msg) {
        this.responseData.setSuccess(false);
        this.responseData.setMessage(msg);
        responseData.setCode(code);
        return this.responseData;
    }
}
