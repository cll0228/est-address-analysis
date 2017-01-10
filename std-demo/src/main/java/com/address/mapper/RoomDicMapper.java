package com.address.mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.lezhi.app.model.RoomDic;
import com.lezhi.app.model.RoomEx;
import com.lezhi.app.model.map.StdAddr;

/**
 * Created by Colin Yan on 2016/7/20.
 */
public interface RoomDicMapper {

    void insert(RoomDic roomDic);

    int insertNewRoom(RoomDic roomDic);

    int deleteRoom(RoomDic ric);

    List<RoomDic> find(@Param("buildingId") Integer buildingId, @Param("name") String name);

    List<StdAddr> findRoomExists(@Param("rids") int rids[]);

    List<RoomDic> findAll(RowBounds rowBounds);


    int batchUpdateArea(@Param("rooms") Set<RoomDic> roomDic);

    int count();

    int updateStatus(@Param("id") int id, @Param("operatorId") int operatorId, @Param("modifyTime") Timestamp modifyTime, @Param("status") int status);

    int countDealUnique();
    
    int update(RoomDic roomDic);

    int getNewRoomId(RoomDic ric);

    int countOldRoom(RoomDic ric);

    int updateNewRoomStatus(RoomDic ric);
    
    List<RoomDic> queryRoomId(@Param("buildingId") Integer buildingId);
    
    int updateRoomStatus(@Param("id") String id, @Param("operatorId") int operatorId, @Param("modifyTime") Timestamp modifyTime, @Param("delStatus") int delStatus);

    void batchInsert(@Param("rooms") Set<RoomDic> roomBuffer);

    RoomEx findById(@Param("id") Integer houseId);

    List<RoomEx>  findByBuildingId(@Param("buildingId") Integer buildingId);

    RoomEx findByNames(@Param("residenceId") Integer residenceId, @Param("buildingNo") String buildingNo, @Param("roomNo") String roomNo);

    List<RoomDic> findTopRooms(@Param("buildingId") Integer buildingId);
}
