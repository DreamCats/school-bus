/**
 * @program school-bus
 * @description: AbstractResponse
 * @author: mf
 * @create: 2020/02/24 16:18
 */

package com.stylefeng.guns.rest.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class AbstractResponse implements Serializable {

    private static final long serivalVersionUID = 7642323132212L;
    @ApiModelProperty(notes = "状态码")
    private String code;
    @ApiModelProperty(notes = "状态消息")
    private String msg;

}
