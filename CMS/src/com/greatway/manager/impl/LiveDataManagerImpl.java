package com.greatway.manager.impl;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.greatway.dao.LiveDataMapper;
import com.greatway.dao.WeldedJunctionMapper;
import com.greatway.dto.ModelDto;
import com.greatway.dto.WeldDto;
import com.greatway.manager.LiveDataManager;
import com.greatway.model.LiveData;
import com.greatway.model.WeldedJunction;
import com.greatway.page.Page;
import com.spring.model.MyUser;

@Service
@Transactional
public class LiveDataManagerImpl implements LiveDataManager {
	@Autowired
	HttpServletRequest request ;
	
	@Autowired
	private LiveDataMapper live;
	@Autowired
	private WeldedJunctionMapper wm;
	
	@Override
	public List<ModelDto> getCausehour(Page page,WeldDto dto, BigInteger parent) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.getCausehour(dto,parent);
	}

	@Override
	public List<ModelDto> getCompanyhour(Page page,WeldDto dto, BigInteger parent) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.getCompanyhour(dto, parent);
	}

	@Override
	public List<ModelDto> getItemhour(Page page,WeldDto dto) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.getItemhour(dto);
	}

	@Override
	public List<ModelDto> getJunctionHous(Page page,WeldDto dto) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.getJunctionHous(dto);
	}

	@Override
	public List<ModelDto> getCauseOverproof(WeldDto dto, BigInteger parent) {
		return live.getCauseOverproof(dto, parent);
	}

	@Override
	public List<LiveData> getAllInsf(BigInteger parent,int type) {
		return live.getAllInsf(parent,type);
	}

	@Override
	public List<ModelDto> getAllTime(Page page,WeldDto dto) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.getAllTime(dto);
	}

	@Override
	public List<ModelDto> getCompanyOverproof(WeldDto dto,BigInteger parent) {
		return live.getCompanyOverproof(dto,parent);
	}

	@Override
	public List<ModelDto> getDatailOverproof(Page page,WeldDto dto,BigInteger parent) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.getDatailOverproof(dto,parent);
	}

	@Override
	public int getCountTime(String welderno, String machineno, String junctionno, String time,BigInteger id) {
		return live.getCountTime(welderno, machineno, junctionno, time, id);
	}

	@Override
	public List<ModelDto> getjunctionoverproof(String welderno, String machineno, String junctionno,
			String time, BigInteger itemid) {
		return live.getjunctionoverproof(welderno, machineno, junctionno, time, itemid);
	}

	@Override
	public List<ModelDto> getcompanyOvertime(WeldDto dto, String num,BigInteger parent) {
		return live.getcompanyOvertime(dto, num,parent);
	}

	@Override
	public List<ModelDto> getCaustOvertime(WeldDto dto, String num, BigInteger parent) {
		return live.getCaustOvertime(dto, num, parent);
	}

	@Override
	public List<ModelDto> getItemOvertime(WeldDto dto, String num) {
		return live.getItemOvertime(dto, num);
	}

	@Override
	public List<LiveData> getJunction(BigInteger parent) {
		return live.getJunction(parent);
	}

	@Override
	public List<ModelDto> getDetailovertime(Page page,WeldDto dto, String num, String junctionno) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.getDetailovertime(dto, num,junctionno);
	}

	@Override
	public List<ModelDto> getCompanyLoads(WeldDto dto,BigInteger parent) {
		return live.getCompanyLoads(dto,parent);
	}

	@Override
	public List<ModelDto> getCaustLoads(WeldDto dto, BigInteger parent) {
		return live.getCaustLoads(dto, parent);
	}

	@Override
	public List<ModelDto> getItemLoads(WeldDto dto, BigInteger parent) {
		return live.getItemLoads(dto, parent);
	}

	@Override
	public List<LiveData> getMachine(BigInteger parent) {
		return live.getMachine(parent);
	}

	@Override
	public List<ModelDto> getDetailLoads(Page page,WeldDto dto, String machineno) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.getDetailLoads(dto, machineno);
	}

	@Override
	public List<ModelDto> getCompanyNoLoads(WeldDto dto,BigInteger parent) {
		return live.getCompanyNoLoads(dto,parent);
	}

	@Override
	public List<ModelDto> getCaustNOLoads(WeldDto dto, BigInteger parent) {
		return live.getCaustNoLoads(dto, parent);
	}

	@Override
	public List<ModelDto> getItemNOLoads(WeldDto dto, BigInteger parent,String equipmentno) {
		return live.getItemNOLoads(dto, parent,equipmentno);
	}

	@Override
	public List<ModelDto> getCompanyIdle(WeldDto dto,BigInteger parent) {
		return live.getCompanyIdle(dto,parent);
	}

	@Override
	public List<ModelDto> getCaustIdle(WeldDto dto, BigInteger parent) {
		return live.getCaustIdle(dto, parent);
	}

	@Override
	public List<ModelDto> getItemIdle(WeldDto dto, BigInteger itemid) {
		return live.getItemidle(dto, itemid);
	}

	@Override
	public int getMachineCount(BigInteger id) {
		return live.getMachineCount(id);
	}

	@Override
	public List<ModelDto> getCompanyUse(Page page, WeldDto dto, BigInteger parent) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.getCompanyUse(dto, parent);
	}

	@Override
	public List<ModelDto> getCaustUse(Page page, WeldDto dto, BigInteger insid) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.getCaustUse(dto, insid);
	}
	
	@Override
	public BigInteger getUserId(HttpServletRequest request){
		try{
			//获取用户id
			Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(obj==null){
				request.setAttribute("afreshLogin", "您的Session已过期，请重新登录！");
				return null;
			}
			MyUser myuser = (MyUser)obj;
			BigInteger uid = new BigInteger(myuser.getId()+"");
			return uid;
		}catch(Exception e){
			request.setAttribute("afreshLogin", "您的Session已过期，请重新登录！");
			return null;
		}
	}
	

	@Override
	public List<ModelDto> getAllTimes(WeldDto dto) {
		return live.getAllTime(dto);
	}

	@Override
	public List<ModelDto> getBlochour(Page page, WeldDto dto) {
		PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		return live.getBlochour(dto);
	}

	@Override
	public List<ModelDto> getBlocOverproof(WeldDto dto) {
		return live.getBlocOverproof(dto);
	}

	@Override
	public List<ModelDto> getBlocOvertime(WeldDto dto, String num) {
		return live.getBlocOvertime(dto, num);
	}

	@Override
	public List<ModelDto> getBlocLoads(WeldDto dto) {
		return live.getBlocLoads(dto);
	}

	@Override
	public List<ModelDto> getBlocNoLoads(WeldDto dto) {
		return live.getBlocNoLoads(dto);
	}

	@Override
	public List<ModelDto> getBlocIdle(WeldDto dto) {
		return live.getBlocIdle(dto);
	}

	@Override
	public List<ModelDto> getBlocUse(Page page,WeldDto dto, BigInteger parent) {
		PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		return live.getBlocUse(dto, parent);
	}

	@Override
	public List<LiveData> getBlocChildren() {
		return live.getBlocChildren();
	}

	@Override
	public List<ModelDto> caustEfficiency(Page page, BigInteger parent, WeldDto dto, int min, int max) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.caustEfficiency(dto, parent,min,max);
	}

	@Override
	public List<ModelDto> companyEfficiency(Page page, BigInteger parent, WeldDto dto, int min, int max) {
		PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		return live.companyEfficiency(dto, parent, min, max);
		
	}

	@Override
	public List<ModelDto> blocEfficiency(Page page, WeldDto dto,BigInteger parent, int min, int max) {
		PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		return live.blocEfficiency(dto,parent,min,max);
	}

	@Override
	public List<ModelDto> getEfficiencyChartNum(WeldDto dto, BigInteger parent) {
		return live.getEfficiencyChartNum(dto, parent);
	}

	@Override
	public List<ModelDto> getEfficiencyChart(WeldDto dto, BigInteger parent, int minnum, int avgnum) {
		return live.getEfficiencyChart(dto, parent, minnum, avgnum);
	}

	@Override
	public WeldedJunction getWeldedJunctionById(BigInteger id) {
		return wm.getWeldedJunctionById(id);
	}

	@Override
	public List<ModelDto> getHousClassify(Page page, BigInteger parent, String searchStr) {
		PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		return live.getHousClassify(parent,searchStr);
	}

	@Override
	public List<ModelDto> getDetailNoLoads(Page page, WeldDto dto) {
		PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		return live.getDetailNoLoads(dto);
	}

	@Override
	public List<ModelDto> getItemOverproof(WeldDto dto, BigInteger id) {
		return live.getItemOverproof(dto, id);
	}

	@Override
	public List<ModelDto> getItemUse(Page page, WeldDto dto, BigInteger insid) {
		PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		return live.getItemUse(dto, insid);
	}

	@Override
	public BigInteger getDyneByJunctionno(String str) {
		return live.getDyneByJunctionno(str);
	}

	@Override
	public List<ModelDto> getCompanyMachineCount(WeldDto dto, BigInteger parent) {
		return live.getCompanyMachineCount(dto, parent);
	}

	@Override
	public List<ModelDto> getCaustMachineCount(WeldDto dto, BigInteger parent) {
		return live.getCaustMachineCount(dto, parent);
	}

	@Override
	public List<ModelDto> getBlocMachineCount(WeldDto dto, BigInteger parent) {
		return live.getBlocMachineCount(dto, parent);
	}

	@Override
	public BigInteger getCountByTime(BigInteger parent, String time, BigInteger mid) {
		return live.getCountByTime(parent, time, mid);
	}

	@Override
	public List<ModelDto> getJunctionByWelder(Page page,WeldDto dto, String welder) {
		PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		return live.getJunctionByWelder(dto, welder);
	}

	@Override
	public List<ModelDto> getExcessiveBack(String time, String welder, String junction) {
		return live.getExcessiveBack(time, welder, junction);
	}

	@Override
	public List<ModelDto> getBlocRunTime(Page page, BigInteger parent, WeldDto dto, int startindex, int endindex) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.getBlocRunTime(parent, dto, startindex, endindex);
	}

	@Override
	public List<ModelDto> getUseratio(String time1, String time2) {
		return live.getUseratio(time1, time2);
	}

	@Override
	public List<ModelDto> getMaintenanceratio(Page page, WeldDto dto) {
		PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		return live.getMaintenanceratio(dto);
	}

	@Override
	public List<ModelDto> getMaintenanceratio(WeldDto dto) {
		return live.getMaintenanceratio(dto);
	}
	
	@Override
	public ModelDto getSumMaintenance(WeldDto dto) {
		return live.getSumMaintenance(dto);
	}

	@Override
	public List<ModelDto> getStandbytimeout(WeldDto dto) {
		return live.getStandbytimeout(dto);
	}

}
