package com.spring.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.model.Wps;

public interface WpsMapper {
	
	List<Wps> findAll(@Param("wpsnum")String wpsnum);
	
	boolean save(Wps wps);
	
	boolean update(Wps wps);
	
	int getUsernameCount(@Param("fwpsnum")String fwpsnum);
	
	Wps findById(BigInteger fid);
	
	boolean delete(BigInteger fid);
	
	List<Wps> findWpsAll(@Param("parent")BigInteger parent,@Param("str")String str);
	
	Wps findWpsByid(@Param("fid")BigInteger fid);
	
	boolean addWps(Wps wps);
	
	boolean updateWps(Wps wps);
	
	boolean deleteWps(@Param("fid")BigInteger fid);
	
	boolean deleteByWpsno(@Param("wpsnum")String wpsnum);
}
