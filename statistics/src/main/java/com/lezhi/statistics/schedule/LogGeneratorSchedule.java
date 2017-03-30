package com.lezhi.statistics.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lezhi.statistics.pojo.LogGeneratorBlock;
import com.lezhi.statistics.pojo.LogGeneratorDistrict;
import com.lezhi.statistics.service.LogService;
import com.lezhi.statistics.util.PropertyUtil;

/**
 * 日志解析
 * @author yyyoushen
 *
 */
@Component
public class LogGeneratorSchedule {
	@Autowired
	private LogService logService;
	
	@Scheduled(cron = "${log.district}")
	public void logDistrict() throws Exception {
		List<LogGeneratorDistrict> logList = new ArrayList<LogGeneratorDistrict>();
		String startTime = PropertyUtil.getStartHour(new Date()); 
		String endTime = PropertyUtil.getEndHour(new Date());
		 
		/*String startTime = "2017-03-14 00:00:00";
		String endTime = "2017-03-14 00:59:59";*/

		logList = logService.getDistrictResult(startTime, endTime);
		
		Map<String, LogGeneratorDistrict> map = new HashMap<String, LogGeneratorDistrict>();
		for (LogGeneratorDistrict logGeneratorDistrict : logList) {
			if (map.get(logGeneratorDistrict.getMac()
					+ logGeneratorDistrict.getChannelNo()) == null) {
				map.put(logGeneratorDistrict.getMac()
						+ logGeneratorDistrict.getChannelNo(),
						logGeneratorDistrict);
			} else if (map.get(logGeneratorDistrict.getMac()
					+ logGeneratorDistrict.getChannelNo()) != null) {
				LogGeneratorDistrict temp = new LogGeneratorDistrict();
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
			LogGeneratorDistrict logGeneratorDistrict = (LogGeneratorDistrict) mapentry
					.getValue();
			logGeneratorDistrict.setBeginTime(startTime);
			logGeneratorDistrict.setEndTime(endTime);
			logService.insertSummaryDistrict(logGeneratorDistrict);
		}
	}
	
	@Scheduled(cron = "${log.block}")
	public void logBlock() throws Exception {
		List<LogGeneratorBlock> logList = new ArrayList<LogGeneratorBlock>();
		/*String startTime = PropertyUtil.getStartHour(new Date()); 
		String endTime = PropertyUtil.getEndHour(new Date());*/
		 
		String startTime = "2017-03-14 00:00:00";
		String endTime = "2017-03-14 00:59:59";

		logList = logService.getBlockResult(startTime, endTime);
		
		Map<String, LogGeneratorBlock> map = new HashMap<String, LogGeneratorBlock>();
		for (LogGeneratorBlock logGeneratorBlock : logList) {
			if (map.get(logGeneratorBlock.getMac()
					+ logGeneratorBlock.getChannelNo()) == null) {
				map.put(logGeneratorBlock.getMac()
						+ logGeneratorBlock.getChannelNo(),
						logGeneratorBlock);
			} else if (map.get(logGeneratorBlock.getMac()
					+ logGeneratorBlock.getChannelNo()) != null) {
				LogGeneratorBlock temp = new LogGeneratorBlock();
				temp = map.get(logGeneratorBlock.getMac()
						+ logGeneratorBlock.getChannelNo());
				logGeneratorBlock.setPv(logGeneratorBlock.getPv()
						+ temp.getPv());
				logGeneratorBlock.setTotalTop(logGeneratorBlock
						.getTotalTop() + temp.getTotalTop());
				map.remove(logGeneratorBlock.getMac()
						+ logGeneratorBlock.getChannelNo());
				map.put(logGeneratorBlock.getMac()
						+ logGeneratorBlock.getChannelNo(),
						logGeneratorBlock);
			}
		}
		Set<?> set = map.entrySet();
		Iterator<?> iterator = set.iterator();
		while (iterator.hasNext()) {
			Entry<?, ?> mapentry = (Entry<?, ?>) iterator.next();
			System.out.println(mapentry.getKey() + "/"
					+ mapentry.getValue().toString());
			LogGeneratorBlock logGeneratorBlock = (LogGeneratorBlock) mapentry
					.getValue();
			logGeneratorBlock.setBeginTime(startTime);
			logGeneratorBlock.setEndTime(endTime);
			logService.insertSummaryBlock(logGeneratorBlock);
		}
	}
}
