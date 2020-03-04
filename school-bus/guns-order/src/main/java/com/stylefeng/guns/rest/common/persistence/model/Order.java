package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author Maifeng
 * @since 2020-03-04
 */
@TableName("sb_order")
public class Order extends Model<Order> {

    private static final long serialVersionUID=1L;

      /**
     * 主键编号
     */
        @TableId(value = "uuid", type = IdType.AUTO)
      private Integer uuid;

      /**
     * 场次id
     */
      private Integer countId;

      /**
     * 0:沙河->清水河；1:清水河->沙河
     */
      private String busStatus;

      /**
     * 已售座位编号
     */
      private String seatsIds;

      /**
     * 场次预售价格
     */
      private Double countPrice;

      /**
     * 订单总金额
     */
      private Double orderPrice;

      /**
     * 修改时间
     */
      private LocalDateTime orderTime;

      /**
     * 下单人
     */
      private Integer orderUser;

      /**
     * 0-待支付,1-已支付,2-已关闭
     */
      private String orderStatus;

      /**
     * 0:未评价；1:已评价
     */
      private String evaluateStatus;

      /**
     * 评论
     */
      private String comment;

    
    public Integer getUuid() {
        return uuid;
    }

      public void setUuid(Integer uuid) {
          this.uuid = uuid;
      }
    
    public Integer getCountId() {
        return countId;
    }

      public void setCountId(Integer countId) {
          this.countId = countId;
      }
    
    public String getBusStatus() {
        return busStatus;
    }

      public void setBusStatus(String busStatus) {
          this.busStatus = busStatus;
      }
    
    public String getSeatsIds() {
        return seatsIds;
    }

      public void setSeatsIds(String seatsIds) {
          this.seatsIds = seatsIds;
      }
    
    public Double getCountPrice() {
        return countPrice;
    }

      public void setCountPrice(Double countPrice) {
          this.countPrice = countPrice;
      }
    
    public Double getOrderPrice() {
        return orderPrice;
    }

      public void setOrderPrice(Double orderPrice) {
          this.orderPrice = orderPrice;
      }
    
    public LocalDateTime getOrderTime() {
        return orderTime;
    }

      public void setOrderTime(LocalDateTime orderTime) {
          this.orderTime = orderTime;
      }
    
    public Integer getOrderUser() {
        return orderUser;
    }

      public void setOrderUser(Integer orderUser) {
          this.orderUser = orderUser;
      }
    
    public String getOrderStatus() {
        return orderStatus;
    }

      public void setOrderStatus(String orderStatus) {
          this.orderStatus = orderStatus;
      }
    
    public String getEvaluateStatus() {
        return evaluateStatus;
    }

      public void setEvaluateStatus(String evaluateStatus) {
          this.evaluateStatus = evaluateStatus;
      }
    
    public String getComment() {
        return comment;
    }

      public void setComment(String comment) {
          this.comment = comment;
      }

    @Override
    protected Serializable pkVal() {
          return this.uuid;
      }

    @Override
    public String toString() {
        return "Order{" +
              "uuid=" + uuid +
                  ", countId=" + countId +
                  ", busStatus=" + busStatus +
                  ", seatsIds=" + seatsIds +
                  ", countPrice=" + countPrice +
                  ", orderPrice=" + orderPrice +
                  ", orderTime=" + orderTime +
                  ", orderUser=" + orderUser +
                  ", orderStatus=" + orderStatus +
                  ", evaluateStatus=" + evaluateStatus +
                  ", comment=" + comment +
              "}";
    }
}
