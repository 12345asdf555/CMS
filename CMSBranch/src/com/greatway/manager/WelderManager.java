package com.greatway.manager;

import java.math.BigInteger;
import java.util.List;

import com.greatway.model.Welder;
import com.greatway.page.Page;

public interface WelderManager {
	/**
	 * 获取所有焊工信息
	 * @param page
	 * @param we
	 * @return
	 */
	List<Welder> getWelderAll(Page page, String str);
	
	/**
	 * 新增焊工信息
	 * @param we
	 */
	void addWelder(Welder we);
	
	/**
	 * 修改焊工信息
	 * @param we
	 */
	void editWelder(Welder we);
	
	/**
	 * 删除焊工信息
	 * @param id
	 */
	void removeWelder(BigInteger id);
	
	/**
	 * 判断焊工编号是否存在
	 * @param wno
	 * @return
	 */
	int getWeldernoCount(String wno);
	
	/**
	 * 根据id查找焊工
	 * @param id
	 * @return
	 */
	Welder getWelderById(BigInteger id);
}
