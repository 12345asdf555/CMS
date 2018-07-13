package com.sshome.ssmcxf.webservice;

import java.math.BigInteger;

public interface MaintainWebService {

	
	/**
	 * 获取所有维修记录
	 * @return
	 */
	Object getWeldingMaintenanceAll(String object);

	/**
	 * 获取维修结束时间
	 * @param wid
	 * @return
	 */
	Object getEndtime(String object);
	
	/**
	 * 根据id查询所有信息
	 * @param wid
	 * @return
	 */
	Object getWeldingMaintenanceById(String object);
	
	/**
	 * 获取设备编码
	 * @return
	 */
	Object getEquipmentno();
	
	/**
	 * 新增维修记录
	 * @param wm
	 */
	Object addMaintian(String obj1,String obj2);
	
	/**
	 * 修改结束时间为当前时间
	 * @param wid
	 */
	Object updateEndtime(String obj1,String obj2);
	
	/**
	 * 修改
	 * @param mr
	 */
	Object updateMaintenanceRecord(String obj1,String obj2);
	
	/**
	 * 删除维修记录
	 * @param mid
	 */
	Object deleteMaintenanceRecord(String obj1,String obj2);
	
	/**
	 * 删除焊机维修记录
	 * @param wid
	 */
	boolean deleteWeldingMaintenance(String object);
	
	/**
	 * 根据焊机id查找维修记录id
	 * @param wid
	 * @return
	 */
	Object getMaintainByWeldingMachinId(String object);
	
	/**
	 * 修改焊机维修状态
	 * @param wid
	 * @param status
	 */
	boolean editstatus(String object);
	

	/**
	 * 修改焊机维修状态
	 * @param wid
	 * @param status
	 */
	boolean editStatusToItem(String obj1,String obj2);

	/**
	 * 根据焊机id获取组织机构id
	 * @param mid
	 * @return
	 */
	BigInteger getInsfidByMachineid(String object);
}
