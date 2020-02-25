/**
 * @program school-bus
 * @description: UserListResponse
 * @author: mf
 * @create: 2020/02/24 16:38
 */

package com.stylefeng.guns.rest.user.vo;

import com.stylefeng.guns.rest.common.AbstractResponse;
import lombok.Data;

import java.util.List;

@Data
public class UserListResponse extends AbstractResponse {

    private List<UserVo> userVos; // 封装用户列表
}
