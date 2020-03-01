/**
 * @program school-bus
 * @description: UserRegisterResponse
 * @author: mf
 * @create: 2020/02/26 15:52
 */

package com.stylefeng.guns.rest.user.dto;

import com.stylefeng.guns.rest.common.AbstractResponse;
import lombok.Data;

@Data
public class UserRegisterResponse extends AbstractResponse {

    private boolean register;
}
