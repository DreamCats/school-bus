/**
 * @program school-bus-cloud
 * @description: CastException
 * @author: mf
 * @create: 2020/11/11 13:44
 */

package com.dream.bus.common;

import com.dream.bus.constants.SbCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CastException{

    public static void cast(SbCode sbCode) {
        log.error(sbCode.getMessage());
        throw new CustomerException(sbCode);
    }
}

class CustomerException extends RuntimeException {

    private SbCode sbCode;

    public CustomerException(SbCode sbCode) {
        this.sbCode = sbCode;
    }
}
