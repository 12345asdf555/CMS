package com.greatway.manager;

import java.math.BigInteger;
import java.util.List;

import com.greatway.model.Wps;
import com.greatway.page.Page;

public interface WpsService {
	/**
	 * 查找所有焊接参数
	 * @param wpsnum wps编号
	 * @return
	 */
	List<Wps> findAll(BigInteger parent,String wpsnum);
	List<Wps> findAll(BigInteger parent,String wpsnumm,String search);
	List<Wps> findAll(Page page,BigInteger parent,String wpsnum,String search);
	
	/**
	 * 保存焊接参数
	 * @param wps
	 * @return
	 */
	boolean save(Wps wps);
	
	/**
	 * 修改焊接参数
	 * @param wps
	 * @return
	 */
	boolean update(Wps wps);
	
	/**
	 * 判断wps编号是否存在
	 * @param fwpsnum 编号
	 * @return
	 */
	int getUsernameCount(String fwpsnum);
	
	/**
	 * 根据id查找焊接参数
	 * @param fid
	 * @return
	 */
	Wps findById(BigInteger fid);
	
	/**
	 * 删除焊接参数
	 * @param fid
	 * @return
	 */
	boolean delete(BigInteger fid);
	
	/**
	 * 查找所有wps
	 * @param str
	 * @return
	 */
	List<Wps> findWpsAll(Page page,BigInteger parent, String str);
	List<Wps> findWpsAll(BigInteger parent, String str);
	
	/**
	 * 根据id查找wps
	 * @param fwpsnum
	 * @return
	 */
	Wps findWpsByid(BigInteger id);
	
	/**
	 * 新增wps
	 * @param wps
	 * @return
	 */
	boolean addWps(Wps wps);
	
	/**
	 * 修改wps
	 * @param wps
	 * @return
	 */
	boolean updateWps(Wps wps);
	
	/**
	 * 删除wps
	 * @param fid
	 * @return
	 */
	boolean deleteWps(String wpsnum, BigInteger fid);
}
