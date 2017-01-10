package com.address.controller;

import java.sql.Timestamp;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lezhi.app.enums.RoomDicStatus;
import com.lezhi.app.mapper.BuildingDicMapper;
import com.lezhi.app.mapper.ResidenceMapper;
import com.lezhi.app.mapper.RoomDicMapper;
import com.lezhi.app.model.BuildingDic;
import com.lezhi.app.model.RoomDic;
import com.lezhi.app.model.map.MapBuilder;
import com.lezhi.app.model.map.Residence;
import com.lezhi.app.model.map.StdAddr;

/**
 * Created by Colin Yan on 2016/7/15.
 */
@Controller
@RequestMapping("/")
public class RoomMapController {

    @Autowired
    private BuildingDicMapper buildingDicMapper;
    @Autowired
    private RoomDicMapper roomDicMapper;
    @Autowired
    private ResidenceMapper residenceMapper;

    @RequestMapping(value = "roomMap", method = RequestMethod.GET)
    public String roomMapPage(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "keyword", required = false) String keyword) {
        return "roomMap";
    }

    @ResponseBody
    @RequestMapping(value = "approve", method = RequestMethod.GET)
    public Map<String, String> approve(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "id", required = true) int id) {
    	Integer userId = Integer.parseInt(request.getSession(true).getAttribute("userId").toString());
        Map<String, String> result = new HashMap<>();
        Date date = new Date();       
        Timestamp modifyTime = new Timestamp(date.getTime());
        boolean success = 1 == roomDicMapper.updateStatus(id, userId, modifyTime, RoomDicStatus.CONFIRMED);
        result.put("status", success ? "success" : "failed");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "addBuilding")
    public Map<String, String> addBuilding(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "rid", required = true) int residenceId,
                                       @RequestParam(value = "no", required = true) String buildingName
                                           ) {
    	Integer userId = Integer.parseInt(request.getSession(true).getAttribute("userId").toString());
        Map<String, String> result = new HashMap<>();
        Date date = new Date();       
        Timestamp modifyTime = new Timestamp(date.getTime());
        BuildingDic dic = new BuildingDic();
        dic.setName(buildingName);
        dic.setResidenceId(residenceId);
        dic.setOperatorId(userId);
        dic.setModifyTime(modifyTime);
        BuildingDic buildingDic = buildingDicMapper.getBuildingByName(dic);
        boolean success = false;
        if(buildingDic != null){
            if (buildingDic.getDelStatus() == 0) {
                result.put("status", "exists");
                return result;
            } else if(buildingDic.getDelStatus() == 1){
                // 更新楼栋状态
                dic.setDelStatus(0);
                dic.setId(buildingDic.getId());
                success = 1 == buildingDicMapper.updateBuildingStatus(dic);
                //取得相对应的roomId
                List<RoomDic> ricList = new ArrayList<RoomDic>();
                ricList = roomDicMapper.queryRoomId(buildingDic.getId());
                String id = "";
                for (RoomDic roomDic : ricList) {
                    if(id=="") {
                        id = roomDic.getId().toString();
                    } else {
                        id += "," + roomDic.getId().toString();
                    }
                }
                // 更新房屋状态
                if(!"".equals(id) && id != null){
                    roomDicMapper.updateRoomStatus(id,userId, modifyTime,0);
                }
            }
        } else {
            dic.setDelStatus(0);
            success = 1 == buildingDicMapper.insert(dic);
        }
        result.put("status", success ? "success" : "failed");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "addRoom")
    public Map<String, String> addRoom(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "bid", required = true) int buildingId,
                                       @RequestParam(value = "rname", required = true) String roomName,
                                       @RequestParam(value = "rarea", required = true) Double area
    ) {
    	Integer userId = Integer.parseInt(request.getSession(true).getAttribute("userId").toString());
        Map<String, String> result = new HashMap<>();
        Date date = new Date();       
        Timestamp modifyTime = new Timestamp(date.getTime());

        RoomDic ric = new RoomDic();
        ric.setName(roomName);
        ric.setBuildingId(buildingId);
        ric.setArea(area);
        ric.setOperatorId(userId);
        ric.setModifyTime(modifyTime);
        int countRoom = roomDicMapper.countOldRoom(ric);
        boolean success = false;
        if(countRoom>0){
            ric.setDelStatus(0);
            success = 1 == roomDicMapper.updateNewRoomStatus(ric);
        } else {
            ric.setStatus(RoomDicStatus.MANUAL_CREATE);
            ric.setDelStatus(0);
            success = 1 == roomDicMapper.insertNewRoom(ric);
        }
        int rid = roomDicMapper.getNewRoomId(ric);
        result.put("rid",String.valueOf(rid));
        result.put("status", success ? "success" : "failed");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "addNewRoom")
    public Map<String, String> addNewRoom(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "bid", required = true) int buildingId,
                                       @RequestParam(value = "rname", required = true) String roomName,
                                       @RequestParam(value = "rarea", required = true) Double area
    ) {

    	Integer userId = Integer.parseInt(request.getSession(true).getAttribute("userId").toString());
        Map<String, String> result = new HashMap<>();
        Date date = new Date();       
        Timestamp modifyTime = new Timestamp(date.getTime());

        RoomDic ric = new RoomDic();
        ric.setName(roomName);
        ric.setBuildingId(buildingId);
        ric.setArea(area);
        ric.setOperatorId(userId);
        ric.setModifyTime(modifyTime);
        int countRoom = roomDicMapper.countOldRoom(ric);
        boolean success = false;
        if(countRoom>0){
            ric.setDelStatus(0);
            success = 1 == roomDicMapper.updateNewRoomStatus(ric);
        } else {
            ric.setStatus(RoomDicStatus.MANUAL_CREATE);
            ric.setDelStatus(0);
            success = 1 == roomDicMapper.insertNewRoom(ric);
        }
        result.put("status", success ? "success" : "failed");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "deleteRoom")
    public Map<String, String> deleteRoom(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam(value = "rid", required = true) int id
    ) {
    	Integer userId = Integer.parseInt(request.getSession(true).getAttribute("userId").toString());
        Map<String, String> result = new HashMap<>();
        Date date = new Date();       
        Timestamp modifyTime = new Timestamp(date.getTime());
        
        RoomDic ric = new RoomDic();
        ric.setId(id);
        ric.setDelStatus(1);
        ric.setOperatorId(userId);
        ric.setModifyTime(modifyTime);
        boolean success = 1 == roomDicMapper.deleteRoom(ric);
        result.put("status", success ? "success" : "failed");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "editTotalFloor")
    public Map<String, String> editTotalFloor(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam(value = "bid", required = true) int id,
                                          @RequestParam(value = "totalFloor", required = true) int totalFloor
    ) {

        Integer userId = Integer.parseInt(request.getSession(true).getAttribute("userId").toString());
        Map<String, String> result = new HashMap<>();
        Date date = new Date();
        Timestamp modifyTime = new Timestamp(date.getTime());

        BuildingDic bic = new BuildingDic();
        bic.setId(id);
        bic.setTotalFloor(totalFloor);
        bic.setOperatorId(userId);
        bic.setModifyTime(modifyTime);
        boolean success = 1 == buildingDicMapper.updateTotalFloor(bic);
        result.put("status", success ? "success" : "failed");
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "deleteBuilding")
    public Map<String, String> deleteBuilding(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam(value = "buildingId", required = true) int buildingId
    ) {
    	Integer userId = Integer.parseInt(request.getSession(true).getAttribute("userId").toString());
        Map<String, String> result = new HashMap<>();
        Date date = new Date();       
        Timestamp modifyTime = new Timestamp(date.getTime());
        BuildingDic dic = new BuildingDic();
        dic.setId(buildingId);
        dic.setDelStatus(1);
        dic.setOperatorId(userId);
        dic.setModifyTime(modifyTime);
        //取得相对应的roomId
        List<RoomDic> ricList = new ArrayList<RoomDic>();
        ricList = roomDicMapper.queryRoomId(buildingId);
        String id = "";
        for (RoomDic roomDic : ricList) {
        	if(id=="") {
        		id = roomDic.getId().toString();
        	} else {
        		id += "," + roomDic.getId().toString();
        	}
		}
        if(!"".equals(id) && id != null){
            roomDicMapper.updateRoomStatus(id,userId, modifyTime,1);
        }
        //更新楼栋状态为已删除
        boolean success = 1 == buildingDicMapper.updateBuildingStatus(dic);
        result.put("status", success ? "success" : "failed");
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "newArea")
    public Map<String, String> newArea(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "id", required = true) int id,
                                       @RequestParam(value = "newArea", required = true) Double newArea
                                           ) {
    	Integer userId = Integer.parseInt(request.getSession(true).getAttribute("userId").toString());
        Map<String, String> result = new HashMap<>();
        Date date = new Date();       
        Timestamp modifyTime = new Timestamp(date.getTime());
        RoomDic dic = new RoomDic();
        dic.setId(id);
        dic.setArea(newArea);
        dic.setOperatorId(userId);
        dic.setModifyTime(modifyTime);
        boolean success = 1 == roomDicMapper.update(dic);
        result.put("status", success ? "success" : "failed");
        return result;
    }

    @RequestMapping(value = "roomMap", method = RequestMethod.POST)
    public String roomMap(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "keywords", required = false) String __keywords) {
        request.setAttribute("keywords", __keywords);
        if (StringUtils.isNotBlank(__keywords)) {
            String keywords[] = __keywords.split(",");
            List<com.lezhi.app.model.Residence> residences = residenceMapper.find(keywords);
            if (residences != null && !residences.isEmpty()) {
                request.setAttribute("residences", residences);
                int rids[] = new int[residences.size()];
                for (int i = 0; i < residences.size(); i++) {
                    rids[i] = residences.get(i).getId();
                }

                MapBuilder builder = new MapBuilder();
                List<StdAddr> stdAddrs = roomDicMapper.findRoomExists(rids);
                for (StdAddr stdAddr : stdAddrs) {
                    builder.add(stdAddr);
                }

                builder.sortAll();
                List<Residence> residenceModels = builder.getResidenceList();
                request.setAttribute("residenceModels", residenceModels);

                for (com.lezhi.app.model.Residence r : residences) {
                    boolean exists = false;
                    for (Residence rm : residenceModels) {
                        if (r.getId().intValue() == rm.getResidenceId()) {
                            exists = true;
                            break;
                        }
                    }
                    r.setExists(exists);
                }
            }
        }

        return "roomMap";
    }
}
