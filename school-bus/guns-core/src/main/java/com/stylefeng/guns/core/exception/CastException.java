/**
 * @program school-bus
 * @description: CastException
 * @author: mf
 * @create: 2020/03/19 00:46
 */

package com.stylefeng.guns.core.exception;

import com.stylefeng.guns.core.constants.SbCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CastException {
    public static void cast(SbCode sbCode) {
        log.error(sbCode.getMessage());
        throw new CustomerException(sbCode);
    }
}
