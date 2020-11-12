/**
 * @program school-bus-cloud
 * @description: UserRequset
 * @author: mf
 * @create: 2020/10/31 15:21
 */

package com.dream.bus.user.param;

import com.dream.bus.param.AbstractRequest;
import lombok.Data;

@Data
public class UserRequest extends AbstractRequest {
    private Long id; // 通过id找

    private String userName;

    private String passPwd;
}
