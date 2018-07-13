package com.greatway.manager;

import java.math.BigInteger;
import java.util.List;

import com.greatway.model.EquipmentManufacturer;
import com.greatway.page.Page;

public interface ManufacturerManager {
	/**
	 * 查询所有厂商信息
	 * @param page 分页
	 * @param str 查询条件
	 * @return
	 */
	List<EquipmentManufacturer> getmanuAll(Page page,String str);
	
	/**
	 * 根据厂商名及类型查找id
	 * @param name 厂商名
	 * @param type 类型
	 * @return
	 */
	BigInteger getManuidByValue(String name,String type);
	
	/**
	 * 判断厂商名及类型是否存在
	 * @param name 厂商名
	 * @param type 类型id
	 * @return
	 */
	int getManuCount(String name,int type);
	
	/**
	 * 根据id查找厂商
	 * @param id
	 * @return
	 */
	EquipmentManufacturer getManuById(BigInteger id);
	
	/**
	 * 新增厂商
	 * @param manu 厂商类
	 * @return
	 */
	boolean addManu(EquipmentManufacturer manu);

	/**
	 * 修改厂商
	 * @param manu 厂商类
	 * @return
	 */
	boolean editManu(EquipmentManufacturer manu);
	
	/**
	 * 删除厂商
	 * @param id 厂商id
	 * @return
	 */
	boolean deleteManu(BigInteger id);

}
