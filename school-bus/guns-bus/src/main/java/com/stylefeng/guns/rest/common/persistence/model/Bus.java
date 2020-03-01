package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 班车表
 * </p>
 *
 * @author Maifeng
 * @since 2020-03-01
 */
@TableName("sb_bus")
public class Bus extends Model<Bus> {

    private static final long serialVersionUID=1L;

      /**
     * 主键编号
     */
        @TableId(value = "uuid", type = IdType.AUTO)
      private Integer uuid;

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
      private LocalDateTime beginTime;

      /**
     * 修改时间
     */
      private LocalDateTime updateTime;

    
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
    
    public LocalDateTime getBeginTime() {
        return beginTime;
    }

      public void setBeginTime(LocalDateTime beginTime) {
          this.beginTime = beginTime;
      }
    
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

      public void setUpdateTime(LocalDateTime updateTime) {
          this.updateTime = updateTime;
      }

    @Override
    protected Serializable pkVal() {
          return this.uuid;
      }

    @Override
    public String toString() {
        return "Bus{" +
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
