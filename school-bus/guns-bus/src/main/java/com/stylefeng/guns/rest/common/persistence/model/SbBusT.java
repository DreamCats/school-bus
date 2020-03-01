package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 班车表
 * </p>
 *
 * @author Maifeng
 * @since 2020-03-01
 */
@TableName("sb_bus_t")
public class SbBusT extends Model<SbBusT> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    @TableId(value = "uuid", type = IdType.AUTO)
    private Integer uuid;
    /**
     * 限制人数
     */
    @TableField("limit_number")
    private String limitNumber;
    /**
     * 司机姓名
     */
    @TableField("driver_name")
    private String driverName;
    /**
     * 司机电话
     */
    @TableField("drive_phone")
    private String drivePhone;
    /**
     * 座位排列：json文件
     */
    @TableField("seats_number")
    private String seatsNumber;
    /**
     * 创建时间
     */
    @TableField("begin_time")
    private Date beginTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;


    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(String limitNumber) {
        this.limitNumber = limitNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDrivePhone() {
        return drivePhone;
    }

    public void setDrivePhone(String drivePhone) {
        this.drivePhone = drivePhone;
    }

    public String getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(String seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "SbBusT{" +
        "uuid=" + uuid +
        ", limitNumber=" + limitNumber +
        ", driverName=" + driverName +
        ", drivePhone=" + drivePhone +
        ", seatsNumber=" + seatsNumber +
        ", beginTime=" + beginTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
