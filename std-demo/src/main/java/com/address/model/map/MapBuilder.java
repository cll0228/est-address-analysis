package com.address.model;

import com.address.model.map.Building;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Colin Yan on 2016/7/19.
 */
public class MapBuilder {

    public static void main(String[] args) {

    }

    public static enum AddResult {
        success, failed, alreadyExists
    }

    public synchronized List<Residence> getResidenceList() {
        if (!sorted) {
            throw new RuntimeException("never sorted");
        }
        return residenceList;
    }

    private volatile boolean sorted = false;

    public synchronized void sortAll() {
        Collections.sort(this.residenceList);
        for (Residence r : residenceList) {
            try {
                Collections.sort(r.getBuildings());
            } catch (Exception e) {
                Collections.sort(r.getBuildings(), new Comparator<Building>() {
                    @Override
                    public int compare(Building o1, Building o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
            }

            for (Building b : r.getBuildings()) {
                b.fillIn();

                int minRoom = 99;
                int maxRoom = 0;

                int totalFloor = 0;
                for (Floor f : b.getFloors()) {
                    for (Room r1 : f.getRooms()) {
                        int fi = r1.getIntName();
                        if (fi % 100 < minRoom % 100) {
                            minRoom = fi;
                        }
                        if (maxRoom == 0 || fi % 100 > maxRoom % 100) {
                            maxRoom = fi;
                        }
                    }
                    if (f.getIntName() != null && f.getIntName() > totalFloor) {
                        totalFloor = f.getIntName();
                    }
                }

                b.setTotalFloor(totalFloor);
                minRoom = minRoom % 100;
                maxRoom = maxRoom % 100;

                for (Floor f : b.getFloors()) {
                    f.fillIn(minRoom, maxRoom);
                    Collections.sort(f.getRooms());
                }
                try {
                    Collections.sort(b.getFloors());
                } catch (Exception ignore) {
                    Collections.sort(b.getFloors(), new Comparator<Floor>() {
                        @Override
                        public int compare(Floor o1, Floor o2) {
                            return o1.getName().compareTo(o2.getName());
                        }
                    });
                }
            }

            try {
                int min = 1000;
                int max = 0;

                for (Building b : r.getBuildings()) {
                    if (b.getNameInt() < min) {
                        min = b.getNameInt();
                    }
                    if (b.getNameInt() > max) {
                        max = b.getNameInt();
                    }
                }
                r.setMinBuilding(min);

                List<BuildingBitMap> buildingBitMaps = new ArrayList<>();
                for (int i = min; i <= max; i++) {
                    Integer bid = null;
                    String buildingName = null;
                    Integer buildingNameInt = null;
                    for (Building b : r.getBuildings()) {
                        if (b.getNameInt() == i) {
                            bid = b.getId();
                            buildingName = b.getName();
                            buildingNameInt = i;
                            break;
                        }
                    }
                    BuildingBitMap bitMap = new BuildingBitMap();
                    bitMap.setExists(bid != null);
                    bitMap.setBuildingId(bid);
                    if (bid != null) {
                        bitMap.setBuildingName(buildingName);
                        bitMap.setBuildingNameInt(buildingNameInt);
                    } else {
                        bitMap.setBuildingName(String.valueOf(i));
                        bitMap.setBuildingNameInt(i);
                    }
                    buildingBitMaps.add(bitMap);
                }
                r.getBuildingBitMaps().addAll(buildingBitMaps);
            } catch (Exception ignore) {
                for (Building b : r.getBuildings()) {
                    BuildingBitMap bitMap = new BuildingBitMap();
                    bitMap.setExists(true);
                    bitMap.setBuildingId(b.getId());
                    bitMap.setBuildingName(b.getName());
                    bitMap.setBuildingNameInt(b.getNameInt());
                    r.getBuildingBitMaps().add(bitMap);
                }
            }
        }
        sorted = true;
    }

    private List<Residence> residenceList = new ArrayList<>();

    public synchronized AddResult add(StdAddr stdAddr) {
        if (sorted) {
            throw new RuntimeException("already sorted");
        }
        String _floor = null;
        if ("".equals(stdAddr.getRoom()) || stdAddr.getRoom() == null) {
            stdAddr.setRoom("");
        } else {
            _floor = parseFloor(stdAddr.getRoom());
            if (_floor == null)
                return AddResult.failed;
        }

        Residence residence = null;

        for (Residence r : residenceList) {
            if (r.getName().equals(stdAddr.getResidence())) {
                residence = r;
                break;
            }
        }
        if (residence == null) {
            residence = new Residence();
            residence.setName(stdAddr.getResidence());
            residence.setResidenceId(stdAddr.getResidenceId());
            residenceList.add(residence);
        }


        Building building = null;
        for (Building b : residence.getBuildings()) {
            if (b.getName().equals(stdAddr.getBuilding())) {
                building = b;
                break;
            }
        }
        if (building == null) {
            building = new Building();
            building.setId(stdAddr.getBuildingId());
            building.setName(stdAddr.getBuilding());
            building.setResidenceId(stdAddr.getResidenceId());
            residence.getBuildings().add(building);
        }

        Floor floor = null;
        if (!"".equals(stdAddr.getRoom()) && stdAddr.getRoom() != null) {
            for (Floor f : building.getFloors()) {
                if (f.getName().equals(_floor)) {
                    floor = f;
                    break;
                }
            }
            if (floor == null) {
                floor = new Floor();
                floor.setName(_floor);
                floor.setTotalFloor(stdAddr.getTotalFloor());
                building.getFloors().add(floor);
            }

            List<Room> rooms = floor.getRooms();

            Room room = null;
            for (Room r : rooms) {
                if (r.getName().equals(stdAddr.getRoom())) {
                    room = r;
                    break;
                }
            }
            if (room == null) {
                room = new Room();
                room.setName(stdAddr.getRoom());
                room.setId(stdAddr.getRoomId());
                room.setArea(stdAddr.getArea());
                room.setStatus(stdAddr.getStatus());
                room.setSrc(stdAddr.getSrc());
                room.setOriAddress(stdAddr.getOriAddress());
                rooms.add(room);

                return AddResult.success;
            } else {
                return AddResult.alreadyExists;
            }
        }
        return AddResult.success;
    }

    private String parseFloor(String roomNo) {
        if (roomNo.matches("^[1-9]\\d+$")) {
            int r = Integer.valueOf(roomNo);
            if (r >= 100 && r <= 9999) {
                return String.valueOf(r / 100);
            }
        }
        return null;
    }

}
