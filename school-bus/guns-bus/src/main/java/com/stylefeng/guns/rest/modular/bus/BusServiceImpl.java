/**
 * @program school-bus
 * @description: BusServiceIml
 * @author: mf
 * @create: 2020/03/01 16:26
 */

package com.stylefeng.guns.rest.modular.bus;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.bus.IBusService;
import com.stylefeng.guns.rest.bus.dto.PageBusRequest;
import com.stylefeng.guns.rest.bus.dto.PageBusResponse;
import org.springframework.stereotype.Component;

@Component
@Service
public class BusServiceImpl implements IBusService {

    @Override
    public PageBusResponse getBus(PageBusRequest request) {
        return null;
    }
}
