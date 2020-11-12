/**
 * @program school-bus-cloud
 * @description: AbstractResponse
 * @author: mf
 * @create: 2020/10/31 15:24
 */

package com.dream.bus.param;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class AbstractResponse implements Serializable {
    private static final long serivalVersionUID = 7642323132212L;
//    @ApiModelProperty(notes = "状态码")
    private String code;
//    @ApiModelProperty(notes = "状态消息")
    private String msg;
}
