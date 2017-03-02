package com.lezhi.adminlj.util;

import com.lezhi.adminlj.pojo.DataList;
import com.lezhi.adminlj.pojo.Neighborhood;
import com.lezhi.adminlj.pojo.ShDistrict;
import com.lezhi.adminlj.pojo.Town;

import java.util.List;

/**
 * Created by Cuill on 2017/2/24.
 */
public class MyUtli {
	public static List<ShDistrict> changData(List<ShDistrict> shDistricts,
			List<DataList> dataLists) {
		if (null == dataLists) {
			return shDistricts;
		}
		for (ShDistrict district : shDistricts) {
			for (DataList list : dataLists) {
				if (list.getDataId()
						.equals(district.getDistrictId().toString())) {
					district.setHdUserNum(list.getHouseholds());
					continue;
				}
			}
		}
		return shDistricts;
	}

	public static List<Town> changData1(List<Town> shDistricts,
			List<DataList> dataLists) {
		if (null == dataLists) {
			return shDistricts;
		}
		for (Town district : shDistricts) {
			for (DataList list : dataLists) {
				if (list.getDataId().equals(district.getTownId().toString())) {
					district.setHdUserNum(list.getHouseholds());
					continue;
				}
			}
		}
		return shDistricts;
	}

	public static List<Neighborhood> changData2(List<Neighborhood> shDistricts,
			List<DataList> dataLists) {
		if (null == dataLists) {
			return shDistricts;
		}
		for (Neighborhood district : shDistricts) {
			for (DataList list : dataLists) {
				if (list.getDataId().equals(
						district.getNeighborhoodId().toString())) {
					district.setHdUserNum(list.getHouseholds());
					continue;
				}
			}
		}
		return shDistricts;
	}

	public static double convert(double value) {
		long l1 = Math.round(value * 100); // 四舍五入
		double ret = l1 / 100.0; // 注意：使用 100.0 而不是 100
		return ret;
	}
}
