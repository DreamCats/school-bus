/**
 * @program school-bus
 * @description: UUIDUtils
 * @author: mf
 * @create: 2020/03/15 02:06
 */

package com.stylefeng.guns.rest.myutils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;


public class UUIDUtils {

    private static Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    public static String getUUID() {
        return IdUtil.randomUUID();
    }

    public static Long flakesUUID() {
        return snowflake.nextId();
    }
}
