package com.address.model.map;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colin Yan on 2016/7/19.
 */
public class Floor implements Comparable<Floor> {

    private String name;
    private boolean real = true;
    private List<Room> rooms = new ArrayList<>();

    private Integer intName;
    private Integer totalFloor;

    public void fillIn(int minRoom, int maxRoom) {
        List<Room> filled = new ArrayList<>();
        try {
     //       int minRoom = 10000;
     /*       int maxRoom = 0;
            for (Room f : rooms) {
                int fi = f.getIntName();
                if (fi > maxRoom) {
                    maxRoom = fi;
                }
            }*/
            /*
            for (Room f : rooms) {
                int fi = f.getIntName();
                if (fi < minRoom) {
                    minRoom = fi;
                }
            }
            minRoom = minRoom % 100;
            */
    //        maxRoom = maxRoom % 100;

            if (minRoom == 0 || maxRoom == 0)
                return;

            for (int i = minRoom; i <=maxRoom; i++) {
                int roomInt = intName * 100 + i;
                boolean exists = false;
                for (Room f : rooms) {
                    int fi = f.getIntName();
                    if (fi == roomInt) {
                        exists = true;
                    }
                }
                if (!exists) {
                    Room f = new Room();
                    f.setReal(false);
                    f.setName(String.valueOf(roomInt));
                    filled.add(f);
                }
            }
            this.rooms.addAll(filled);
        } catch (Exception ignore) {
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        try {
            intName = Integer.valueOf(name);
        } catch (Exception ignore) {
        }
        this.name = name;
    }

    public Integer getIntName() {
        return intName;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public boolean getReal() {
        return real;
    }

    public void setReal(boolean real) {
        this.real = real;
    }

    @Override
    public int compareTo(Floor o) {
        return intName.compareTo(o.getIntName());
    }

    public Integer getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(Integer totalFloor) {
        this.totalFloor = totalFloor;
    }
}
