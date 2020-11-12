/**
 * @program school-bus-cloud
 * @description: UserCheckRequest
 * @author: mf
 * @create: 2020/10/31 15:26
 */

package com.dream.bus.user.param;

import com.dream.bus.param.AbstractRequest;
import lombok.Data;

@Data
public class UserCheckRequest extends AbstractRequest {

    private String username;
}
