package com.greatway.manager;

import java.math.BigInteger;
import java.util.List;

import com.greatway.dto.WeldDto;
import com.greatway.model.Welcome;
public interface WelcomeManager {

	/**
	 * 获取项目部焊机总数
	 * @param page 分页
	 * @param parent 组织机构父id
	 * @return
	 */
	List<Welcome> getItemMachineCount(BigInteger parent);
	
	/**
	 * 焊工工作量排行
	 * @param parent 事业部id
	 * @param time 时间
	 * @return
	 */
	List<Welcome> getWorkRank(BigInteger parent,String time);
	
	/**
	 * 获取工作的焊机数
	 * @param itemid
	 * @param time
	 * @return
	 */
	Welcome getWorkMachineCount(BigInteger itemid, String time);
	
	/**
	 * 获取项目部正常工作时长
	 * @param dto 事业部id，起始时间，结束时间
	 * @return
	 */
	List<Welcome> getItemWeldTime(WeldDto dto);
	
	/**
	 * 获取项目部超标工作时长
	 * @param dto 事业部id，起始时间，结束时间
	 * @return
	 */
	List<Welcome> getItemOverProofTime(WeldDto dto);

}
