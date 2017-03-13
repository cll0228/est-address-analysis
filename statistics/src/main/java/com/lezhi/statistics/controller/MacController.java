package com.lezhi.statistics.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lezhi.statistics.service.MacService;

/**
 * Created by wangyh on 2017/3/13.
 */
@Controller
@RequestMapping("/mac/")
public class MacController {
	@Autowired MacService macService;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public Map<String, Object> loginSubmit(
			@RequestParam(value = "districtId", required = false) String districtId,
			@RequestParam(value = "blockId", required = false) String blockId,
			@RequestParam(value = "residenceId", required = false) String residenceId,
			@RequestParam(value = "pageNo", required = false) String pageNo,
			@RequestParam(value = "pageSize", required = false) String pageSize) {
		Map<String, Object> result = new HashMap<>();
//		macService.getMacInfoList(type, id, start, end);
		// result.put("list", list);
		return result;
	}
}
