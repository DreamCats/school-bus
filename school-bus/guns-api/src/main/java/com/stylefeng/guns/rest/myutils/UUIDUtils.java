/**
 * @program school-bus
 * @description: UUIDUtils
 * @author: mf
 * @create: 2020/03/15 02:06
 */

package com.stylefeng.guns.rest.myutils;

import cn.hutool.core.util.IdUtil;

import java.util.UUID;

public class UUIDUtils {

    public static String getUUID() {
        return IdUtil.randomUUID();
    }

    public static String flakesUUID() {
        return "";
    }
}
