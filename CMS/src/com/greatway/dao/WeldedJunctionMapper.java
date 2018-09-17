package com.greatway.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.greatway.model.WeldedJunction;

import tk.mybatis.mapper.common.Mapper;

public interface WeldedJunctionMapper extends Mapper<WeldedJunction>{
	List<WeldedJunction> getWeldedJunctionAll(@Param("str")String str,@Param("parent")BigInteger parent);
	
	List<WeldedJunction> getLiveJunction(@Param("parent")BigInteger parent);
	
	WeldedJunction getWeldedJunctionById(@Param("id")BigInteger id);
	
	boolean addJunction(WeldedJunction wj);

	boolean updateJunction(WeldedJunction wj);

	boolean deleteJunction(@Param("id")BigInteger id);
	
	int getWeldedjunctionByNo(@Param("wjno")String wjno,@Param("parent") BigInteger parent);
}
