package com.spring.service.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dao.LiveDataMapper;
import com.spring.dao.WeldedJunctionMapper;
import com.spring.dto.ModelDto;
import com.spring.dto.WeldDto;
import com.spring.model.LiveData;
import com.spring.model.WeldedJunction;
import com.spring.service.LiveDataService;

@Service
@Transactional
public class LiveDataServiceImpl implements LiveDataService {
	@Autowired
	private LiveDataMapper live;
	@Autowired
	private WeldedJunctionMapper wm;
	
	@Override
	public List<ModelDto> getCausehour(WeldDto dto, BigInteger parent) {
		try{
			return live.getCausehour(dto,parent);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getCompanyhour(WeldDto dto, BigInteger parent) {
		try{
			return live.getCompanyhour(dto, parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getItemhour(WeldDto dto) {
		try{
			return live.getItemhour(dto);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getJunctionHous(WeldDto dto) {
		try{
			return live.getJunctionHous(dto);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getCauseOverproof(WeldDto dto, BigInteger parent) {
		try{
			return live.getCauseOverproof(dto, parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<LiveData> getAllInsf(BigInteger parent,int type) {
		try{
			return live.getAllInsf(parent,type);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getAllTime(WeldDto dto) {
		try{
			return live.getAllTime(dto);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getCompanyOverproof(WeldDto dto,BigInteger parent) {
		try{
			return live.getCompanyOverproof(dto,parent);
		}catch(Exception e){
			return null;
		}
	}
	
	@Override
	public List<ModelDto> getcompanyOvertime(WeldDto dto, String num,BigInteger parent) {
		try{
			return live.getcompanyOvertime(dto, num,parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getCaustOvertime(WeldDto dto, String num, BigInteger parent) {
		try{
			return live.getCaustOvertime(dto, num, parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getItemOvertime(WeldDto dto, String num) {
		try{
			return live.getItemOvertime(dto, num);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<LiveData> getJunction(BigInteger parent) {
		try{
			return live.getJunction(parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getDetailovertime(WeldDto dto, String num, String junctionno) {
		try{
			return live.getDetailovertime(dto, num,junctionno);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getCompanyLoads(WeldDto dto,BigInteger parent) {
		try{
			return live.getCompanyLoads(dto,parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getCaustLoads(WeldDto dto, BigInteger parent) {
		try{
			return live.getCaustLoads(dto, parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getItemLoads(WeldDto dto, BigInteger parent) {
		try{
			return live.getItemLoads(dto, parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<LiveData> getMachine(BigInteger parent) {
		try{
			return live.getMachine(parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getDetailLoads(WeldDto dto, String machineno) {
		try{
			return live.getDetailLoads(dto, machineno);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getCompanyNoLoads(WeldDto dto,BigInteger parent) {
		try{
			return live.getCompanyNoLoads(dto,parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getCaustNOLoads(WeldDto dto, BigInteger parent) {
		try{
			return live.getCaustNoLoads(dto, parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getItemNOLoads(WeldDto dto, BigInteger parent,String equipmentno) {
		try{
			return live.getItemNOLoads(dto, parent,equipmentno);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getCompanyIdle(WeldDto dto,BigInteger parent) {
		try{
			return live.getCompanyIdle(dto,parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getCaustIdle(WeldDto dto, BigInteger parent) {
		try{
			return live.getCaustIdle(dto, parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getItemIdle(WeldDto dto, BigInteger itemid) {
		try{
			return live.getItemidle(dto, itemid);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public int getMachineCount(BigInteger id) {
		try{
			return live.getMachineCount(id);
		}catch(Exception e){
			return -1;
		}
	}

	@Override
	public List<ModelDto> getCompanyUse( WeldDto dto, BigInteger parent) {
		try{
			return live.getCompanyUse(dto, parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getCaustUse( WeldDto dto, BigInteger insid) {
		try{
			return live.getCaustUse(dto, insid);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getAllTimes(WeldDto dto) {
		try{
			return live.getAllTime(dto);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getBlochour( WeldDto dto) {
		try{
			return live.getBlochour(dto);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getBlocOverproof(WeldDto dto) {
		try{
			return live.getBlocOverproof(dto);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getBlocOvertime(WeldDto dto, String num) {
		try{
			return live.getBlocOvertime(dto, num);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getBlocLoads(WeldDto dto) {
		try{
			return live.getBlocLoads(dto);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getBlocNoLoads(WeldDto dto) {
		try{
			return live.getBlocNoLoads(dto);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getBlocIdle(WeldDto dto) {
		try{
			return live.getBlocIdle(dto);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getBlocUse(WeldDto dto, BigInteger parent) {
		try{
			return live.getBlocUse(dto, parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<LiveData> getBlocChildren() {
		try{
			return live.getBlocChildren();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> caustEfficiency(BigInteger parent, WeldDto dto, int min, int max) {
		try{
			return live.caustEfficiency(dto, parent,min,max);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> companyEfficiency(BigInteger parent, WeldDto dto, int min, int max) {
		try{
			return live.companyEfficiency(dto,parent,min,max);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> blocEfficiency(WeldDto dto,BigInteger parent, int min, int max) {
		try{
			return live.blocEfficiency(dto,parent,min,max);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getEfficiencyChartNum(WeldDto dto, BigInteger parent) {
		try{
			return live.getEfficiencyChartNum(dto, parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getEfficiencyChart(WeldDto dto, BigInteger parent, int minnum, int avgnum) {
		try{
			return live.getEfficiencyChart(dto, parent, minnum, avgnum);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public WeldedJunction getWeldedJunctionById(BigInteger id) {
		try{
			return wm.getWeldedJunctionById(id);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getHousClassify( BigInteger parent, String searchStr) {
		try{
			return live.getHousClassify(parent,searchStr);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getDetailNoLoads(WeldDto dto) {
		try{
			return live.getDetailNoLoads(dto);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getItemOverproof(WeldDto dto, BigInteger id) {
		try{
			return live.getItemOverproof(dto, id);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getItemUse( WeldDto dto, BigInteger insid) {
		try{
			return live.getItemUse(dto, insid);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getBlocMachineCount(WeldDto dto, BigInteger parent) {
		try{
			return live.getBlocMachineCount(dto, parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getCompanyMachineCount(WeldDto dto, BigInteger parent) {
		try{
			return live.getCompanyMachineCount(dto, parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<ModelDto> getCaustMachineCount(WeldDto dto, BigInteger parent) {
		try{
			return live.getCaustMachineCount(dto, parent);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Double getCountByTime(BigInteger parent, String time1,String time2, BigInteger mid,int type) {
		try{
			return live.getCountByTime(parent, time1, time2, mid, type);
		}catch(Exception e){
			return null;
		}
	}

	/*@Override
	public List<ModelDto> getJunctionByWelder(WeldDto dto, String welder) {
		try{
			return live.getJunctionByWelder(dto, welder);
		}catch(Exception e){
			return null;
		}
	}*/

	/*@Override
	public List<ModelDto> getExcessiveBack(String time, String welder, String junction) {
		try{
			return live.getExcessiveBack(time, welder, junction);
		}catch(Exception e){
			return null;
		}
	}*/
	
	/*@Override
	public List<ModelDto> getStandbytimeout(WeldDto dto) {
		try{
			return live.getStandbytimeout(dto);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}*/

	@Override
	public ModelDto getWelderMsg(WeldDto dto, BigInteger parent) {
		try{
			return live.getWelderMsg(dto, parent);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getWelderMaxTime(WeldDto dto, BigInteger parent) {
		try{
			return live.getWelderMaxTime(dto, parent);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getWelderMinTime(WeldDto dto, BigInteger parent) {
		try{
			return live.getWelderMinTime(dto, parent);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getMachineTotal(BigInteger parent) {
		try{
			return live.getMachineTotal(parent);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public ModelDto getMachineMsg(WeldDto dto, BigInteger parent) {
		try{
			return live.getMachineMsg(dto, parent);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getMachineStandby(WeldDto dto, BigInteger parent) {
		try{
			return live.getMachineStandby(dto, parent);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public String getMachineStandbyTime(WeldDto dto, BigInteger parent) {
		try{
			return live.getMachineStandbyTime(dto, parent);
		}catch(Exception e){
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	public int getWelderTotal(BigInteger parent) {
		try{
			return live.getWelderTotal(parent);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ModelDto> getExcessiveBack(WeldDto dto) {
		try{
			return live.getExcessiveBack(dto);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getExcessiveBackDetail(BigInteger id) {
		try{
			return live.getExcessiveBackDetail(id);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getBlocRunTime(BigInteger parent, WeldDto dto, int startindex, int endindex) {
		try{
			return live.getBlocRunTime(parent, dto, startindex, endindex);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getUseratio(String time1, String time2, String insftype) {
		try{
			return live.getUseratio(time1, time2, insftype);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getMaintenanceratio(WeldDto dto) {
		try{
			return live.getMaintenanceratio(dto);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ModelDto getSumMaintenance(WeldDto dto) {
		try{
			return live.getSumMaintenance(dto);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getStandbytimeout(WeldDto dto, int str) {
		try{
			return live.getStandbytimeout(dto, str);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getItemTypeMaintain(WeldDto dto, BigInteger itemid) {
		try{
			return live.getItemTypeMaintain(dto, itemid);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getItemMachineSumMoneyByType(BigInteger itemid) {
		try{
			return live.getItemMachineSumMoneyByType(itemid);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getMachineMoney() {
		try{
			return live.getMachineMoney();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getFaultRatio(WeldDto dto) {
		try{
			return live.getFaultRatio(dto);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getMaintenanceNum(WeldDto dto) {
		try{
			return live.getMaintenanceNum(dto);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getFaultNum(WeldDto dto) {
		try{
			return live.getFaultNum(dto);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getFaultRatioByType(WeldDto dto) {
		try{
			return live.getFaultRatioByType(dto);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getFaultDetail(WeldDto dto) {
		try{
			return live.getFaultDetail(dto);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getOnlineNumber(WeldDto dto, double time) {
		try{
			return live.getOnlineNumber(dto, time);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getOperatoreTime(WeldDto dto) {
		try{
			return live.getOperatoreTime(dto);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getItemWorkTime(WeldDto dto) {
		try{
			return live.getItemWorkTime(dto);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getItemStandbyTime(WeldDto dto) {
		try{
			return live.getItemStandbyTime(dto);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getInsfandMachinenum(BigInteger parent) {
		try{
			return live.getInsfandMachinenum(parent);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getUseDetail(BigInteger fid, int type, WeldDto dto) {
		try{
			return live.getUseDetail(fid, type, dto);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getDurationTime(String sql) {
		try{
			return live.getDurationTime(sql);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getWeldingmachineList(WeldDto dto) {
		try{
			return live.getWeldingmachineList(dto);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getWelderList(WeldDto dto) {
		try{
			return live.getWelderList(dto);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getNewOvertime(WeldDto dto, int num, String insftype) {
		try{
			return live.getNewOvertime(dto, num, insftype);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getNewOvertimeDetail(WeldDto dto, int num) {
		try{
			return live.getNewOvertimeDetail(dto, num);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getNewIdle(WeldDto dto) {
		try{
			return live.getNewIdle(dto);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModelDto> getMachineTypeTotal(BigInteger parent) {
		try{
			return live.getMachineTypeTotal(parent);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
