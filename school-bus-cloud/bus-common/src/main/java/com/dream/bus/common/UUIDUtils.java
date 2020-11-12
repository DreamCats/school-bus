/**
 * @program school-bus-cloud
 * @description: UUIDUtils
 * @author: mf
 * @create: 2020/11/08 13:45
 */

package com.dream.bus.common;

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