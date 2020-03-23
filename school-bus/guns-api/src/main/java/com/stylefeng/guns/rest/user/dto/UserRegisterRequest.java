/**
 * @program school-bus
 * @description: UserRegisterRequest
 * @author: mf
 * @create: 2020/02/24 16:52
 */

package com.stylefeng.guns.rest.user.dto;

import com.stylefeng.guns.rest.common.AbstractRequest;
import lombok.Data;

@Data
public class UserRegisterRequest extends AbstractRequest {

    private String username;

    private String password;

    private String email;

    private String phone;
}
