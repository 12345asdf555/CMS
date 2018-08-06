package com.spring.service;

import java.math.BigInteger;
import java.util.List;
import com.spring.model.Wps;

public interface WpsService {
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
	boolean save(String object);
	
	/**
	 * 修改
	 * @param wps
	 * @return
	 */
	boolean update(String object);
	
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
	boolean delete(String object);
}