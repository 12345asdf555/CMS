package com.greatway.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.greatway.model.MaintenanceRecord;
import com.greatway.model.WeldingMaintenance;

import tk.mybatis.mapper.common.Mapper;

public interface WeldingMaintenanceMapper extends Mapper<WeldingMaintenance> {
	List<WeldingMaintenance> getWeldingMaintenanceAll(@Param("wid")BigInteger wid,@Param("str")String str);
	
	List<WeldingMaintenance> getMaintainByWeldingMachinId(@Param("wid")BigInteger wid);
	
	List<WeldingMaintenance> getEndtime(@Param("wid") BigInteger wid);
	
	WeldingMaintenance getWeldingMaintenanceById(@Param("wid") BigInteger wid);
	
	void addMaintenanceRecord(MaintenanceRecord mr);
	
	void addWeldingMaintenance(WeldingMaintenance wm);
	
	void updateEndtime(@Param("wid")BigInteger wid);
	
	void updateMaintenanceRecord(MaintenanceRecord mr);
	
	void deleteMaintenanceRecord(@Param("mid")BigInteger mid);
	
	void deleteWeldingMaintenance(@Param("wid")BigInteger wid);
	
	BigInteger getInsfidByMachineid(@Param("mid")BigInteger mid);
	
}
