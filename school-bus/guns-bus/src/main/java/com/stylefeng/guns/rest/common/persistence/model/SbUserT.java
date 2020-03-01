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
 * 用户表
 * </p>
 *
 * @author Maifeng
 * @since 2020-02-25
 */
@TableName("sb_user_t")
public class SbUserT extends Model<SbUserT> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    @TableId(value = "uuid", type = IdType.AUTO)
    private Integer uuid;
    /**
     * 用户账号
     */
    @TableField("user_name")
    private String userName;
    /**
     * 用户密码
     */
    @TableField("user_pwd")
    private String userPwd;
    /**
     * 用户昵称
     */
    @TableField("nick_name")
    private String nickName;
    /**
     * 用户性别 0-男，1-女
     */
    @TableField("user_sex")
    private Integer userSex;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户手机号
     */
    @TableField("user_phone")
    private String userPhone;
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
        return "SbUserT{" +
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
