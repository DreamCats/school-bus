/**
 * @program school-bus
 * @description: HutoolTest
 * @author: mf
 * @create: 2020/03/16 13:09
 */

package com.stylefeng.guns.rest;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HutoolTest {

    @Test
    public void test() {
        // 转换为字符串
        int a = 1;
        String s = Convert.toStr(a);
        System.out.println(a);
        //转换为指定类型数组
        String[] b = {"1", "2", "3", "4"};
        Integer[] bArr = Convert.toIntArray(b);
        System.out.println(bArr);
        //转换为日期对象
        String dateStr = "2017-05-06";
        Date date = Convert.toDate(dateStr);
        System.out.println(date);
        //转换为列表
        String[] strArr = {"a", "b", "c", "d"};
        List<String> toList = Convert.toList(String.class, strArr);
        System.out.println(toList);

        //Date、long、Calendar之间的相互转换
        //当前时间
        DateTime time = DateUtil.date();
        System.out.println(time);
        //时间戳转Date
        date = DateUtil.date(System.currentTimeMillis());
        System.out.println(date);
        //自动识别格式转换
        String dateStr1 = "2017-03-01";
        date = DateUtil.parse(dateStr1);
        System.out.println(date);
        //自定义格式化转换
        date = DateUtil.parse(dateStr, "yyyy-MM-dd");
        System.out.println(date);

        //格式化输出日期
        String format = DateUtil.format(date, "yyyy-MM-dd");
        System.out.println(format);
        //获得年的部分
        int year = DateUtil.year(date);
        System.out.println(year);
        //获得月份，从0开始计数
        int month = DateUtil.month(date);
        System.out.println(month);

        //判断是否为空字符串
        String str = "test";
        System.out.println(StrUtil.isEmpty(str));
        //去除字符串的前后缀
        System.out.println(StrUtil.removeSuffix("a.jpg", ".jpg"));
        System.out.println(StrUtil.removePrefix("a.jpg", "a."));
        //格式化字符串
        String template = "这只是个占位符:{}";
        String str2 = StrUtil.format(template, "我是占位符");

        // 雪花算法
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long id = snowflake.nextId();
        System.out.println(id);
        long id1 = snowflake.nextId();
        System.out.println(id1);
        Snowflake snowflake1 = IdUtil.createSnowflake(2, 1);
        System.out.println(snowflake1.nextIdStr());
    }
}
