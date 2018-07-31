package com.greatway.manager.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.greatway.dao.WeldingMachineMapper;
import com.greatway.dao.WeldingMaintenanceMapper;
import com.greatway.manager.MaintainManager;
import com.greatway.model.MaintenanceRecord;
import com.greatway.model.WeldingMachine;
import com.greatway.model.WeldingMaintenance;
import com.greatway.page.Page;

@Service
@Transactional
public class MaintainManagerImpl implements MaintainManager {
	@Autowired
	private WeldingMaintenanceMapper wmm;
	@Autowired
	private WeldingMachineMapper wm;
	
	@Override
	public List<WeldingMaintenance> getWeldingMaintenanceAllPage(Page page,BigInteger wid, String str) {
		PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		return wmm.getWeldingMaintenanceAll(wid,str);
	}

	@Override
	public List<WeldingMaintenance> getWeldingMaintenanceAll(String str) {
		return wmm.getWeldingMaintenanceAll(null,str);
	}

	@Override
	public void addMaintian(WeldingMaintenance w, MaintenanceRecord mr,BigInteger wid) {
		wmm.addMaintenanceRecord(mr);
		BigInteger mid = mr.getId();
		w.getMaintenance().setId(mid);
		wmm.addWeldingMaintenance(w);
		if(mr.getEndTime()!=""||mr.getEndTime()!=null){
			//修焊机状态为维护中
			wm.editstatus(wid,33);
		}
	}

	@Override
	public List<WeldingMachine> getEquipmentno() {
		return wm.getEquipmentno();
	}

	@Override
	public void updateEndtime(BigInteger wid) {
		wmm.updateEndtime(wid);
	}

	@Override
	public void updateMaintenanceRecord(MaintenanceRecord mr) {
		wmm.updateMaintenanceRecord(mr);
	}

	@Override
	public void deleteMaintenanceRecord(BigInteger mid) {
		wmm.deleteMaintenanceRecord(mid);
	}

	@Override
	public void deleteWeldingMaintenance(BigInteger wid) {
		wmm.deleteWeldingMaintenance(wid);		
	}

	@Override
	public List<WeldingMaintenance> getMaintainByWeldingMachinId(BigInteger wid) {
		return wmm.getMaintainByWeldingMachinId(wid);
	}

	@Override
	public WeldingMaintenance getWeldingMaintenanceById(BigInteger wid) {
		return wmm.getWeldingMaintenanceById(wid);
	}

	@Override
	public List<WeldingMaintenance> getEndtime(BigInteger wid) {
		return wmm.getEndtime(wid);
	}

	@Override
	public void editstatus(BigInteger wid, int status) {
		wm.editstatus(wid, status);
	}

	@Override
	public BigInteger getInsfidByMachineid(BigInteger mid) {
		return wmm.getInsfidByMachineid(mid);
	}

}
