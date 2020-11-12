/**
 * @program school-bus-cloud
 * @description: Bus
 * @author: mf
 * @create: 2020/11/08 13:16
 */

package com.dream.bus.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("sb_bus")
@Data
public class Bus implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键编号
     */
    @TableId(value = "uuid")
    private Long uuid;

    /**
     * 限制人数
     */
    private String limitNumber;

    /**
     * 司机姓名
     */
    private String driverName;

    /**
     * 司机电话
     */
    private String drivePhone;

    /**
     * 座位排列：json文件
     */
    private String seatsNumber;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime beginTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}

