package com.sshome.ssmcxf.webservice;

public interface WpsWebService {
	/**
	 * 查找所有工艺
	 * @param parent
	 * @param str
	 * @return
	 */
	Object findAll(String object);
	
	/**
	 * 新增工艺
	 * @param wps
	 * @return
	 */
	Object save(String obj1,String obj2);
	
	/**
	 * 修改
	 * @param wps
	 * @return
	 */
	Object update(String obj1,String obj2);
	
	/**
	 * 判断工艺名称是否存在
	 * @param fwpsnum
	 * @return
	 */
	int getUsernameCount(String object);
	
	/**
	 * 根据id查询
	 * @param fid
	 * @return
	 */
	Object findById(String object);
	
	/**
	 * 删除
	 * @param fid
	 * @return
	 */
	Object delete(String obj1,String obj2);
}