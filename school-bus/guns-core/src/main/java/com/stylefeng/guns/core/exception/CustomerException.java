/**
 * @program school-bus
 * @description: CustomerException
 * @author: mf
 * @create: 2020/03/19 00:44
 */

package com.stylefeng.guns.core.exception;

import com.stylefeng.guns.core.constants.SbCode;

public class CustomerException extends RuntimeException {

    private SbCode sbCode;

    public CustomerException(SbCode sbCode) {
        this.sbCode = sbCode;
    }
}
