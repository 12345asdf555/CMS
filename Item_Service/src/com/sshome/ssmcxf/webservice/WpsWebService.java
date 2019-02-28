package com.sshome.ssmcxf.webservice;

public interface WpsWebService {
	
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
	boolean delete(String object);
	
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
	boolean addWps(String object);
	
	/**
	 * 修改wps
	 * @param wps
	 * @return
	 */
	boolean updateWps(String object);
	
	/**
	 * 删除wps
	 * @param fid
	 * @return
	 */
	boolean deleteWps(String object);
	
	/**
	 * 新增wps焊接参数
	 * @param object
	 * @return
	 */
	Object saveChildrenWPS(String object);
}