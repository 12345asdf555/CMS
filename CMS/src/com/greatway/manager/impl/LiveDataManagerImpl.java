package com.greatway.manager.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	public List<ModelDto> getDetailovertime(Page page,WeldDto dto, String num, String junctionno,String sort) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.getDetailovertime(dto, num,junctionno,sort);
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
	public double getCountByTime(BigInteger parent, String time1,String time2, BigInteger mid, int type) {
		return live.getCountByTime(parent, time1, time2, mid, type);
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
	public List<ModelDto> getUseratio(String time1, String time2, String insftype) {
		return live.getUseratio(time1, time2, insftype);
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
	public List<ModelDto> getStandbytimeout(WeldDto dto,int str) {
		return live.getStandbytimeout(dto,str);
	}

	@Override
	public List<ModelDto> getItemTypeMaintain(WeldDto dto, BigInteger itemid) {
		return live.getItemTypeMaintain(dto, itemid);
	}

	@Override
	public List<ModelDto> getItemMachineSumMoneyByType(BigInteger itemid) {
		return live.getItemMachineSumMoneyByType(itemid);
	}

	@Override
	public List<ModelDto> getItemMachineSumMoneyByType(Page page,BigInteger itemid) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.getItemMachineSumMoneyByType(itemid);
	}

	@Override
	public List<ModelDto> getMachineMoney() {
		return live.getMachineMoney();
	}

	@Override
	public List<ModelDto> getFaultRatio(WeldDto dto) {
		return live.getFaultRatio(dto);
	}

	@Override
	public List<ModelDto> getMaintenanceNum(WeldDto dto) {
		return live.getMaintenanceNum(dto);
	}

	@Override
	public List<ModelDto> getFaultNum(WeldDto dto) {
		return live.getFaultNum(dto);
	}

	@Override
	public List<ModelDto> getFaultRatioByType(WeldDto dto) {
		return live.getFaultRatioByType(dto);
	}

	@Override
	public List<ModelDto> getFaultDetail(Page page,WeldDto dto) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.getFaultDetail(dto);
	}

	@Override
	public List<ModelDto> getOnlineNumber(WeldDto dto,double time) {
		return live.getOnlineNumber(dto,time);
	}

	@Override
	public List<ModelDto> getOperatoreTime(WeldDto dto) {
		return live.getOperatoreTime(dto);
	}

	@Override
	public List<ModelDto> getItemWorkTime(WeldDto dto) {
		return live.getItemWorkTime(dto);
	}

	@Override
	public List<ModelDto> getItemStandbyTime(WeldDto dto) {
		return live.getItemStandbyTime(dto);
	}

	@Override
	public List<ModelDto> getInsfandMachinenum(BigInteger parent) {
		return live.getInsfandMachinenum(parent);
	}

	@Override
	public List<ModelDto> getInsfandMachinenum(Page page,BigInteger parent) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.getInsfandMachinenum(parent);
	}

	@Override
	public List<ModelDto> getDurationTime(String time1, String time2, int timetype) {
		String strsql = "";
		if(timetype==1){
			strsql = getYear(time1, time2);
		}else if(timetype==2){
			strsql = getMonth(time1, time2);
		}else if(timetype==3){
			strsql = getDay(time1, time2);
		}else if(timetype==4){
			strsql = getWeek(time1, time2);
		}
		return live.getDurationTime(strsql);
	}

	@Override
	public List<ModelDto> getDurationTime(Page page, String time1, String time2, int timetype) {
		String strsql = "";
		if(timetype==1){
			strsql = getYear(time1, time2);
		}else if(timetype==2){
			strsql = getMonth(time1, time2);
		}else if(timetype==3){
			strsql = getDay(time1, time2);
		}else if(timetype==4){
			strsql = getWeek(time1, time2);
		}
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.getDurationTime(strsql);
	}
	
	/**
	 * 获取日
	 * @return
	 */
	public String getDay(String time1,String time2){
		String str = "SELECT * FROM (";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = sdf.parse(time1);
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(d);
			int day=cal.get(Calendar.DATE);
			long t1 = sdf.parse(time1).getTime();
			long t2 = sdf.parse(time2).getTime();
			int days = day + (int)((t2-t1)/(1000*60*60*24))+1;
			for(int i = day; i < days; i++){
				Calendar calendar = Calendar.getInstance();  
				Date dates = sdf.parse(time1);
				calendar.setTime(dates);
				calendar.set(Calendar.DATE, i);
				if(i!=days-1){
					str += "SELECT '"+sdf.format(calendar.getTime())+"' AS weldTime UNION ALL ";
				}else{
					str += "SELECT '"+sdf.format(calendar.getTime())+"' AS weldTime)temp";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 获取月
	 * @return
	 */
	public String getMonth(String time1,String time2){
		String str = "SELECT * FROM (";
		try{ 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date d = sdf.parse(time1);
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(d);
			int day=cal.get(Calendar.MONTH);
	        Calendar c1 = Calendar.getInstance();
	        Calendar c2 = Calendar.getInstance();
	        c1.setTime(sdf.parse(time1));
	        c2.setTime(sdf.parse(time2));
	        int result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
	        int month = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12;
	        int days =  day + Math.abs(month + result);
			for(int i = day; i <= days; i++){
				Calendar calendar = Calendar.getInstance();  
				Date dates = sdf.parse(time1);
				calendar.setTime(dates);
				calendar.set(Calendar.MONTH, i);
				if(i!=days){
					str += "SELECT '"+sdf.format(calendar.getTime())+"' AS weldTime UNION ALL ";
				}else{
					str += "SELECT '"+sdf.format(calendar.getTime())+"' AS weldTime)temp";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 获取年
	 * @return
	 */
	public String getYear(String time1,String time2){
		String str = "SELECT * FROM (";
		try{ 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			Date d = sdf.parse(time1);
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(d);
			int day=cal.get(Calendar.YEAR);
	        Calendar c1 = Calendar.getInstance();
	        Calendar c2 = Calendar.getInstance();
	        c1.setTime(sdf.parse(time1));
	        c2.setTime(sdf.parse(time2));
	        int result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
	        int month = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) ;
	        int days =  day + Math.abs(month+result);
			for(int i = day; i <= days; i++){
				Calendar calendar = Calendar.getInstance();  
				Date dates = sdf.parse(time1);
				calendar.setTime(dates);
				calendar.set(Calendar.YEAR, i);
				if(i!=days){
					str += "SELECT '"+sdf.format(calendar.getTime())+"' AS weldTime UNION ALL ";
				}else{
					str += "SELECT '"+sdf.format(calendar.getTime())+"' AS weldTime)temp";
				}
			} 
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}
	

	
	/**
	 * 获取周
	 * @return
	 */
	public String getWeek(String time1,String time2){
		String str = "SELECT * FROM (";
		try{
			//日期不能超过两年
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
			Calendar c1 = Calendar.getInstance();    
			Calendar c2 = Calendar.getInstance();   
			c1.setTime(sdf.parse(time1)); 
			c2.setTime(sdf.parse(time2));     
			int week1 = c1.get(Calendar.WEEK_OF_YEAR);
			int week2 = c2.get(Calendar.WEEK_OF_YEAR); 
			c1.add(Calendar.DAY_OF_MONTH, -7);    
			c2.add(Calendar.DAY_OF_MONTH, -7);  
			int year1 = c1.get(Calendar.YEAR);  
			if(week1<c1.get(Calendar.WEEK_OF_YEAR)){  
			    year1+=1;  
			}
			int year2 = c2.get(Calendar.YEAR);  
			if(week2<c2.get(Calendar.WEEK_OF_YEAR)){  
			    year2+=1;  
			} 
			if(year1!=year2){
				for(int i = week1; i <=52; i++){
					str += "SELECT '"+ year1+"-"+ i +"' AS weldTime UNION ALL ";
				} 
				for(int i = 1; i <=week2; i++){
					if(i!=week2){
						str += "SELECT '"+ year2+"-"+ i +"' AS weldTime UNION ALL ";
					}else{
						str += "SELECT '"+ year2+"-"+ i +"' AS weldTime)temp";
					}
				} 
			}else{
				for(int i = week1; i <=week2; i++){
					if(i!=week2){
						str += "SELECT '"+ year1+"-"+ i +"' AS weldTime UNION ALL ";
					}else{
						str += "SELECT '"+ year1+"-"+ i +"' AS weldTime)temp";
					}
				} 
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public List<ModelDto> getUseDetail(Page page, BigInteger fid, int type, WeldDto dto) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return live.getUseDetail(fid, type, dto);
	}

	@Override
	public List<ModelDto> getBlocRunTime(BigInteger parent, WeldDto dto, int startindex, int endindex) {
		return live.getBlocRunTime(parent, dto, startindex, endindex);
	}

}
