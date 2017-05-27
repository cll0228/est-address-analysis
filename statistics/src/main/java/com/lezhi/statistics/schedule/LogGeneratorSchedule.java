package com.lezhi.statistics.schedule;

import com.lezhi.statistics.pojo.LogGenerator;
import com.lezhi.statistics.service.LogService;
import com.lezhi.statistics.util.PropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Map.Entry;

/**
 * 日志解析
 * @author yyyoushen
 *
 */
@Component
public class LogGeneratorSchedule {
	@Autowired
	private LogService logService;
	
	@Scheduled(cron = "${log.prepare}")
	public void logDistrict() throws Exception {
		List<LogGenerator> logList = new ArrayList<LogGenerator>();
		String startTime = PropertyUtil.getStartHour(new Date()); 
		String endTime = PropertyUtil.getEndHour(new Date());
		 
		/*String startTime = "2017-03-14 00:00:00";
		String endTime = "2017-03-14 00:59:59";*/

		logList = logService.getResult(startTime, endTime);
		
		Map<String, LogGenerator> map = new HashMap<String, LogGenerator>();
		for (LogGenerator logGeneratorDistrict : logList) {
			if (map.get(logGeneratorDistrict.getMac()
					+ logGeneratorDistrict.getChannelNo()) == null) {
				map.put(logGeneratorDistrict.getMac()
						+ logGeneratorDistrict.getChannelNo(),
						logGeneratorDistrict);
			} else if (map.get(logGeneratorDistrict.getMac()
					+ logGeneratorDistrict.getChannelNo()) != null) {
				LogGenerator temp = new LogGenerator();
				temp = map.get(logGeneratorDistrict.getMac()
						+ logGeneratorDistrict.getChannelNo());
				logGeneratorDistrict.setPv(logGeneratorDistrict.getPv()
						+ temp.getPv());
				logGeneratorDistrict.setTotalTop(logGeneratorDistrict
						.getTotalTop() + temp.getTotalTop());
				map.remove(logGeneratorDistrict.getMac()
						+ logGeneratorDistrict.getChannelNo());
				map.put(logGeneratorDistrict.getMac()
						+ logGeneratorDistrict.getChannelNo(),
						logGeneratorDistrict);
			}
		}
		Set<?> set = map.entrySet();
		Iterator<?> iterator = set.iterator();
		while (iterator.hasNext()) {
			Entry<?, ?> mapentry = (Entry<?, ?>) iterator.next();
			System.out.println(mapentry.getKey() + "/"
					+ mapentry.getValue().toString());
			LogGenerator logGeneratorDistrict = (LogGenerator) mapentry
					.getValue();
			logGeneratorDistrict.setBeginTime(startTime);
			logGeneratorDistrict.setEndTime(endTime);
			logService.insertSummary(logGeneratorDistrict);
		}
	}

}
