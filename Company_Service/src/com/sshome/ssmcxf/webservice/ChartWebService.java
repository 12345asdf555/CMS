package com.sshome.ssmcxf.webservice;

public interface ChartWebService {
	/**
	 * 工效
	 * @param object
	 * @return
	 */
	Object getEfficiency(String object);
	
	/**
	 * 工时
	 * @param object
	 * @return
	 */
	Object getHour(String object);
	
	/**
	 * 超时待机
	 * @param object
	 * @return
	 */
	Object getOvertime(String object);
	
	/**
	 * 焊接工艺超标
	 * @param object
	 * @return
	 */
	Object getOverproof(String object);
	
	/**
	 * 负荷率
	 * @param object
	 * @return
	 */
	Object getLoads(String object);

	/**
	 * 空载率
	 * @param object
	 * @return
	 */
	Object getNoLoads(String object);
	
	/**
	 * 闲置率
	 * @param object
	 * @return
	 */
	Object getIdle(String object);
	
	/**
	 * 单台设备运行数据统计
	 * @param object
	 * @return
	 */
	Object getUse(String object);
}
