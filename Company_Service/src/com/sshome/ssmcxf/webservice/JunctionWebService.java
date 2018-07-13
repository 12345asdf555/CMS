package com.sshome.ssmcxf.webservice;

public interface JunctionWebService {
	
	/**
	 * 查询所有焊口
	 * @param object
	 * @return
	 */
	Object getWeldedJunctionAll(String object);
	
	/**
	 * 根据id查找焊口
	 * @param object
	 * @return
	 */
	Object getWeldedJunctionById(String object);
	
	/**
	 * 判断焊缝编号是否存在
	 * @param object
	 * @return 受影响的行数
	 */
	int getWeldedjunctionByNo(String object);
	
	/**
	 * 新增焊缝
	 * @param object
	 */
	Object addJunction(String obj1, String obj2);

	/**
	 * 修改焊缝
	 * @param object
	 */
	Object updateJunction(String obj1, String obj2);

	/**
	 * 删除焊缝
	 * @param object
	 */
	Object deleteJunction(String obj1, String obj2);
}
