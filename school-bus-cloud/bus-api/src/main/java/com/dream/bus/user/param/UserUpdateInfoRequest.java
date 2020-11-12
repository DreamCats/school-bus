/**
 * @program school-bus-cloud
 * @description: UserUpdateInfoRequest
 * @author: mf
 * @create: 2020/10/31 15:29
 */

package com.dream.bus.user.param;

import com.dream.bus.param.AbstractRequest;
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
