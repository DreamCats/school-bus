package com.stylefeng.guns.rest.common.persistence.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 场次表
 * </p>
 *
 * @author Maifeng
 * @since 2020-03-01
 */
public class SbCountT extends Model<SbCountT> {

    private static final long serialVersionUID=1L;

      /**
     * 主键编号
     */
        @TableId(value = "uuid", type = IdType.AUTO)
      private Integer uuid;

      /**
     * 班车id
     */
      private Integer busId;

      /**
     * 开始时间
     */
      private String beginTime;

      /**
     * 结束时间
     */
      private String endTime;

      /**
     * 0：沙河；1：清水河；2：沙河到清水河；3：清水河到沙河
     */
      private String busStatus;

      /**
     * 价格
     */
      private Double price;

      /**
     * 已选座位
     */
      private String selectedSeats;

      /**
     * 0:未满；1:已满
     */
      private String seatStatus;

    
    public Integer getUuid() {
        return uuid;
    }

      public void setUuid(Integer uuid) {
          this.uuid = uuid;
      }
    
    public Integer getBusId() {
        return busId;
    }

      public void setBusId(Integer busId) {
          this.busId = busId;
      }
    
    public String getBeginTime() {
        return beginTime;
    }

      public void setBeginTime(String beginTime) {
          this.beginTime = beginTime;
      }
    
    public String getEndTime() {
        return endTime;
    }

      public void setEndTime(String endTime) {
          this.endTime = endTime;
      }
    
    public String getBusStatus() {
        return busStatus;
    }

      public void setBusStatus(String busStatus) {
          this.busStatus = busStatus;
      }
    
    public Double getPrice() {
        return price;
    }

      public void setPrice(Double price) {
          this.price = price;
      }
    
    public String getSelectedSeats() {
        return selectedSeats;
    }

      public void setSelectedSeats(String selectedSeats) {
          this.selectedSeats = selectedSeats;
      }
    
    public String getSeatStatus() {
        return seatStatus;
    }

      public void setSeatStatus(String seatStatus) {
          this.seatStatus = seatStatus;
      }

    @Override
    protected Serializable pkVal() {
          return this.uuid;
      }

    @Override
    public String toString() {
        return "SbCountT{" +
              "uuid=" + uuid +
                  ", busId=" + busId +
                  ", beginTime=" + beginTime +
                  ", endTime=" + endTime +
                  ", busStatus=" + busStatus +
                  ", price=" + price +
                  ", selectedSeats=" + selectedSeats +
                  ", seatStatus=" + seatStatus +
              "}";
    }
}
