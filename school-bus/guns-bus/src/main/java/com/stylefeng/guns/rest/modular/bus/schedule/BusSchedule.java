/**
 * @program school-bus
 * @description: BusServiceSchedule
 * @author: mf
 * @create: 2020/03/18 20:55
 */

package com.stylefeng.guns.rest.modular.bus.schedule;

import com.stylefeng.guns.rest.bus.IBusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BusSchedule {

    @Autowired
    private IBusService busService;

    /**
     * 每天上午7点到晚上21点，每隔30分钟执行一次
     */
    @Scheduled(cron = "0 0/30 7-21 * * ?")
    private void schedulChangeBusStatus() {
        busService.schedulChangeBusStatus();
    }

    /**
     * 每天凌晨0点1分执行
     */
    @Scheduled(cron = "0 1 0 * * ? ")
    private void addCounts(){
        busService.addCounts();
    }

    /**
     * 每5s执行一次
     */
    @Scheduled(cron = "5 * * * * ? ")
    private void scheduledTest(){
        busService.scheduledTest();
    }
}
