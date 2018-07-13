package com.spring.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.dto.ModelDto;

import tk.mybatis.mapper.common.Mapper;

public interface ChartMapper extends Mapper<ModelDto>{
	List<ModelDto> getEfficiency(@Param("time") String time);
	
	BigInteger getDyneByJunctionno(@Param("str") String str);
	
	List<ModelDto> getHour(@Param("time") String time);
	
	List<ModelDto> getOvertime(@Param("time") String time,@Param("num") int num);
	
	List<ModelDto> getAllInsf();
	
	List<ModelDto> getOverproof(@Param("time") String time);
	
	List<ModelDto> getLoads(@Param("time") String time);
	
	List<ModelDto> getNoLoads(@Param("time") String time);
	
	List<ModelDto> getMachineCount(@Param("time") String time,@Param("status") int status);
	
	BigInteger getCountByTime(@Param("time") String time,@Param("id") BigInteger id);
	
	List<ModelDto> getIdle(@Param("time") String time);
	
	int getMachineCountByIns(@Param("id") BigInteger id);
	
	List<ModelDto> getUse(@Param("time") String time);
}
