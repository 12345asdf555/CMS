package com.greatway.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.greatway.model.Welder;

import tk.mybatis.mapper.common.Mapper;

public interface WelderMapper extends Mapper<Welder> {
	List<Welder> getWelderAll(@Param("str")String str,@Param("parent")BigInteger parent);
	
	void addWelder(Welder we);
	
	void editWelder(Welder we);
	
	void removeWelder(BigInteger id);
	
	int getWeldernoCount(@Param("wno")String wno,@Param("parent")BigInteger parent);
	
	Welder getWelderById(@Param("id")BigInteger id);
	
	List<Welder> getOverWelder(@Param("str")String str,@Param("parent")BigInteger parent);
}
