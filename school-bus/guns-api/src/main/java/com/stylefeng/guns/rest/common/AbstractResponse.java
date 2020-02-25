/**
 * @program school-bus
 * @description: AbstractResponse
 * @author: mf
 * @create: 2020/02/24 16:18
 */

package com.stylefeng.guns.rest.common;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class AbstractResponse implements Serializable {

    private static final long serivalVersionUID = 7642323132212L;

    private String code;

    private String msg;

}
