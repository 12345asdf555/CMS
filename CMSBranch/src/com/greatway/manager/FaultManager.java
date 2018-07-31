package com.greatway.manager;

import java.math.BigInteger;
import java.util.List;

import com.greatway.model.Fault;
import com.greatway.page.Page;

public interface FaultManager {
	/**
	 * 查询所有故障代码
	 * @param page 分页
	 * @param str 查询条件
	 * @return
	 */
	List<Fault> getFaultAll(Page page,String str);
	
	/**
	 * 根据id查询故障代码
	 * @param id 故障代码id
	 * @return
	 */
	Fault getFaultById(BigInteger id);
	
	/**
	 * 新增故障代码
	 * @param fault 故障代码model
	 * @return
	 */
	boolean addFault(Fault fault);
	
	/**
	 * 修改故障代码
	 * @param fault 故障代码model
	 * @return
	 */
	boolean editFault(Fault fault);
	
	/**
	 * 删除故障代码
	 * @param id 故障代码id
	 * @return
	 */
	boolean deleteFault(BigInteger id);
}
