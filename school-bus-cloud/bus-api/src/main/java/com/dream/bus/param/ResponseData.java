/**
 * @program school-bus-cloud
 * @description: ResponseData
 * @author: mf
 * @create: 2020/10/31 15:52
 */

package com.dream.bus.param;

import lombok.Data;

@Data
public class ResponseData<T> {
    private boolean success;

    private String message; // 消息

    private int code;

    private T result; // 返回的数据

    private long timestamp = System.currentTimeMillis(); // 时间戳
}
