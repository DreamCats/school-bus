/**
 * @program school-bus
 * @description: UserLoginRequst
 * @author: mf
 * @create: 2020/02/26 01:03
 */

package com.stylefeng.guns.rest.user.dto;

import com.stylefeng.guns.rest.common.AbstractRequest;
import lombok.Data;

@Data
public class UserLoginRequst extends AbstractRequest {

    private String username;

    private String password;
}
