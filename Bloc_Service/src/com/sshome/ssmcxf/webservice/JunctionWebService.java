package com.sshome.ssmcxf.webservice;

import java.math.BigInteger;

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
	BigInteger addJunction(String object);

	/**
	 * 修改焊缝
	 * @param object
	 */
	boolean updateJunction(String object);

	/**
	 * 删除焊缝
	 * @param object
	 */
	boolean deleteJunction(String object);
}
