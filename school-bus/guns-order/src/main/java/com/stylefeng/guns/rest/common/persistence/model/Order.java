package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

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
@Data
public class Order extends Model<Order> {

    private static final long serialVersionUID=1L;

      /**
     * 主键编号
     */
      @TableId(value = "uuid")
      private Long uuid;

      /**
     * 场次id
     */
      private Long countId;

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
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
      private LocalDateTime orderTime;

      /**
     * 下单人
     */
      private String orderUser;

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

    /**
     * 用户id
     */
    private Long userId;

}
