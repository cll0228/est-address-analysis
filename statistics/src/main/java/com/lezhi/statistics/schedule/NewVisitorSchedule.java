package com.lezhi.statistics.schedule;

import com.lezhi.statistics.mapper.MacMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Colin Yan on 2017/5/31.
 */
@Component
public class NewVisitorSchedule {

    @Autowired
    private MacMapper macMapper;

    // every minute
    @Scheduled(fixedDelay = 60 * 1000, initialDelay = 20 * 1000)
    public void newVisitor() throws Exception {
        synchronized (this) {
            this.macMapper.newVisitorSchedule();
        }
    }
}
