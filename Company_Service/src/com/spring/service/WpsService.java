package com.spring.service;

public interface WpsService {
	
	/**
	 * 查找所有焊接参数
	 * @param wpsnum wps编号
	 * @return
	 */
	Object findAll(String object);
	
	/**
	 * 判断工艺名称是否存在
	 * @param fwpsnum
	 * @return
	 */
	int getUsernameCount(String object);
	
	/**
	 * 根据id查找焊接参数
	 * @param fid
	 * @return
	 */
	Object findById(String object);
	
	/**
	 * 删除
	 * @param fid
	 * @return
	 */
	Object delete(String obj1, String obj2);
	
	/**
	 * 查找所有wps
	 * @param str
	 * @return
	 */
	Object findWpsAll(String object);
	
	/**
	 * 根据id查找wps
	 * @param fwpsnum
	 * @return
	 */
	Object findWpsByid(String object);
	
	/**
	 * 新增wps
	 * @param wps
	 * @return
	 */
	Object addWps(String obj1,String obj2);
	
	/**
	 * 修改wps
	 * @param wps
	 * @return
	 */
	Object updateWps(String obj1,String obj2);
	
	/**
	 * 删除wps
	 * @param fid
	 * @return
	 */
	Object deleteWps(String obj1,String obj2);
	

	/**
	 * 保存wps焊接参数
	 * @param fid
	 * @return
	 */
	Object saveChiledrenWps(String obj1,String obj2);
}