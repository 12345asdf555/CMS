package com.greatway.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.greatway.dto.WeldDto;
import com.greatway.model.Welcome;

import tk.mybatis.mapper.common.Mapper;

public interface WelcomeMapper  extends Mapper<Welcome>{
	List<Welcome> getItemMachineCount(@Param("parent") BigInteger parent);
	
	List<Welcome> getWorkRank(@Param("parent")BigInteger parent,@Param("time")String time);
	
	Welcome getWorkMachineCount(@Param("itemid")BigInteger itemid,@Param("time")String time);
	
	List<Welcome> getItemWeldTime(@Param("dto")WeldDto dto);
	
	List<Welcome> getItemOverProofTime(@Param("dto")WeldDto dto);
}
