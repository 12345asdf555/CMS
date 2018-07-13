package com.spring.service;

public interface WelderService {
	/**
	 * 获取所有焊工信息
	 * @param object
	 * @return
	 */
	Object getWelderAll(String object);
	
	/**
	 * 新增焊工信息
	 * @param object
	 */
	Object addWelder(String obj1, String obj2);
	
	/**
	 * 修改焊工信息
	 * @param object
	 */
	Object editWelder(String obj1, String obj2);
	
	/**
	 * 删除焊工信息
	 * @param object
	 */
	Object removeWelder(String obj1, String obj2);
	
	/**
	 * 判断焊工编号是否存在
	 * @param object
	 * @return
	 */
	int getWeldernoCount(String object);
	
	/**
	 * 根据id查找焊工
	 * @param object
	 * @return
	 */
	Object getWelderById(String object);
}
