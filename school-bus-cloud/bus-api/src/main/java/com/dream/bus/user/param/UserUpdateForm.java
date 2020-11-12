/**
 * @program school-bus
 * @description: UserUpdateForm
 * @author: mf
 * @create: 2020/03/02 00:55
 */

package com.dream.bus.user.param;

import lombok.Data;

@Data
public class UserUpdateForm {
    private Long id; // 通过id找

    //    private String userName;
    private String nickName;

    private Integer userSex;

    private String email;

    private String userPhone;

    private Double money;

    private String payPassword;
}
