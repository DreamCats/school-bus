package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Maifeng
 * @since 2020-03-01
 */
@TableName("sb_user")
public class User extends Model<User> {

    private static final long serialVersionUID=1L;

      /**
     * 主键编号
     */
        @TableId(value = "uuid", type = IdType.AUTO)
      private Integer uuid;

      /**
     * 用户账号
     */
      private String userName;

      /**
     * 用户密码
     */
      private String userPwd;

      /**
     * 用户昵称
     */
      private String nickName;

      /**
     * 用户性别 0-男，1-女
     */
      private Integer userSex;

      /**
     * 用户邮箱
     */
      private String email;

      /**
     * 用户手机号
     */
      private String userPhone;

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

    
    public Integer getUuid() {
        return uuid;
    }

      public void setUuid(Integer uuid) {
          this.uuid = uuid;
      }
    
    public String getUserName() {
        return userName;
    }

      public void setUserName(String userName) {
          this.userName = userName;
      }
    
    public String getUserPwd() {
        return userPwd;
    }

      public void setUserPwd(String userPwd) {
          this.userPwd = userPwd;
      }
    
    public String getNickName() {
        return nickName;
    }

      public void setNickName(String nickName) {
          this.nickName = nickName;
      }
    
    public Integer getUserSex() {
        return userSex;
    }

      public void setUserSex(Integer userSex) {
          this.userSex = userSex;
      }
    
    public String getEmail() {
        return email;
    }

      public void setEmail(String email) {
          this.email = email;
      }
    
    public String getUserPhone() {
        return userPhone;
    }

      public void setUserPhone(String userPhone) {
          this.userPhone = userPhone;
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
        return "User{" +
              "uuid=" + uuid +
                  ", userName=" + userName +
                  ", userPwd=" + userPwd +
                  ", nickName=" + nickName +
                  ", userSex=" + userSex +
                  ", email=" + email +
                  ", userPhone=" + userPhone +
                  ", beginTime=" + beginTime +
                  ", updateTime=" + updateTime +
              "}";
    }
}
