package com.address.model.map;

/**
 * Created by Colin Yan on 2016/7/19.
 */
public class Room implements Comparable<Room> {

    private String name;

    private Integer intName;

    private boolean real = true;
    private Double area;
    private Integer id;
    private Integer status;
    private String src;
    private String oriAddress;
    
    public String getOriAddress() {
		return oriAddress;
	}

	public void setOriAddress(String oriAddress) {
		this.oriAddress = oriAddress;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public boolean getReal() {
        return real;
    }

    public void setReal(boolean real) {
        this.real = real;
    }

    @Override
    public int compareTo(Room o) {
        return name.compareTo(o.getName());
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getArea() {
        return area;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
