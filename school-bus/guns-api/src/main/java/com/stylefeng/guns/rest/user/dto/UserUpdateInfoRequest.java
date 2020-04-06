/**
 * @program school-bus
 * @description: UserUpdateInfoRequest
 * @author: mf
 * @create: 2020/02/26 21:39
 */

package com.stylefeng.guns.rest.user.dto;

import com.stylefeng.guns.rest.common.AbstractRequest;
import lombok.Data;

@Data
public class UserUpdateInfoRequest extends AbstractRequest {

    private Long id; // 通过id找

//    private String userName;
    private String nickName;

    private Integer userSex;

    private String email;

    private String userPhone;

    private Double money;

    private String payPassword;
}
