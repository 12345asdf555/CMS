package com.greatway.manager;

import java.math.BigInteger;
import java.util.List;

import com.greatway.model.WeldedJunction;
import com.greatway.page.Page;

public interface WeldedJunctionManager {
	
	/**
	 * 查询所有焊口
	 */
	List<WeldedJunction> getWeldedJunctionAll(Page page, String str);
	
	/**
	 * 根据id查找焊口
	 * @param id 焊口id
	 * @return
	 */
	WeldedJunction getWeldedJunctionById(BigInteger id);
	
	/**
	 * 判断焊缝编号是否存在
	 * @param wjno 悍缝编号
	 * @return 受影响的行数
	 */
	int getWeldedjunctionByNo(String wjno);
	
	/**
	 * 新增焊缝
	 * @param wj
	 */
	boolean addJunction(WeldedJunction wj);

	/**
	 * 修改焊缝
	 * @param wj
	 */
	boolean updateJunction(WeldedJunction wj);

	/**
	 * 删除焊缝
	 * @param wj
	 */
	boolean deleteJunction(BigInteger id);
}
