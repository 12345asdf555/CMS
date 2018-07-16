package com.sshome.ssmcxf.webservice;

import java.math.BigInteger;

public interface WeldingMachineWebService {

	/**
	 * 查询所有焊机信息
	 */
	Object getWeldingMachineAll(String object);
	
	/**
	 * 查询所有厂商信息
	 */
	Object getManuAll();
	
	/**
	 * 新增设备
	 */
	Object addWeldingMachine(String obj1,String obj2);
	
	/**
	 * 新增设备至项目部
	 * @return
	 */
	Object addMachineToItem(String obj1,String obj2);
	
	/**
	 * 修改设备
	 */
	Object editWeldingMachine(String obj1,String obj2);
	/**
	 * 修改设备
	 */
	Object editMachineToBlocCompany(String obj1,String obj2);
	
	/**
	 * 删除设备
	 */
	Object deleteWeldingChine(String obj1,String obj2);
	
	/**
	 * 根据焊机编号查找id
	 */
	BigInteger getWeldingMachineByEno(String object);
	
	/**
	 * 判断焊机编号是否存在
	 */
	int getEquipmentnoCount(String object);
	
	int getMachineCountToItem(String obj1,String obj2);
	
	/**
	 * 判断采集序号是否存在
	 */
	int getGatheridCount(String object);
	
	/**
	 * 根据厂商值和类型查找厂商id
	 */
	BigInteger getManuidByValue(String object);
	
	/**
	 * 根据id查找记录
	 */
	Object getWeldingMachineById(String object);
	
	/**
	 * 根据项目名称获取项目id
	 */
	BigInteger getInsframeworkByName(String object);
	
	/**
	 * 获取某厂商下的焊机总数
	 */
	BigInteger getMachineCountByManu(String object);

	/**
	 * 根据组织机构及采集编号获取焊机编号
	 */
	String getGatheridMachine(String obj1, String obj2);
	
	/**
	 * 修改项目部焊机设备
	 */
	Object editMachineToItem(String obj1, String obj2);
	
	/**
	 * 根据组织机构及采集编号获取焊机编号
	 */
	String getMachineByGather(String object);
}
