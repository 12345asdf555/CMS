package com.greatway.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.greatway.dto.ModelDto;
import com.greatway.dto.WeldDto;
import com.greatway.manager.InsframeworkManager;
import com.greatway.manager.LiveDataManager;
import com.greatway.manager.WeldingMachineManager;
import com.greatway.model.Insframework;
import com.greatway.model.LiveData;
import com.greatway.page.Page;
import com.greatway.util.IsnullUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/companyChart", produces = { "text/json;charset=UTF-8" })
public class CompanyChartController {
	private Page page;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int total = 0;
	
	@Autowired
	private LiveDataManager lm;
	
	@Autowired
	private WeldingMachineManager wm;
	@Autowired
	private InsframeworkManager insm;
	
	IsnullUtil iutil = new IsnullUtil();
	
	@RequestMapping("/searchoverproof")
	public String searchoverproof(HttpServletRequest request){
		request.setAttribute("weldtime", request.getParameter("weldtime"));
		request.setAttribute("electricitys", request.getParameter("electricitys"));
		request.setAttribute("welder", request.getParameter("welder"));
		request.setAttribute("junction", request.getParameter("junction"));
		request.setAttribute("maxelectricity", request.getParameter("maxelectricity"));
		request.setAttribute("minelectricity", request.getParameter("minelectricity"));
		return "companychart/overproof";
	}
	
	@RequestMapping("/goOverproofTimeQuantum")
	public String goOverproofTimeQuantum(HttpServletRequest request){
		request.setAttribute("welder", request.getParameter("welder"));
		request.setAttribute("junction", request.getParameter("junction"));
		request.setAttribute("weldtime", request.getParameter("weldtime"));
		return "companychart/timequantum";
	}
	/**
	 * 跳转公司工时页面
	 * @return
	 */
	@RequestMapping("/goCompanyHour")
	public String goCompany(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		lm.getUserId(request);
		request.setAttribute("parent", parent);
		request.setAttribute("parentime1", request.getParameter("parentime1"));
		request.setAttribute("parentime2", request.getParameter("parentime2"));
		return "companychart/companyHour";
	}
	
	/**
	 * 跳转公司超标页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goCompanyOverproof")
	public String goCompanyOverproof(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		lm.getUserId(request);
		request.setAttribute("parent", parent);
		request.setAttribute("parentime1", request.getParameter("parentime1"));
		request.setAttribute("parentime2", request.getParameter("parentime2"));
		return "companychart/companyoverproof";
	}
	
	/**
	 * 跳转超标焊工焊口页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goSelectWelderJunction")
	public String goSelectWelder(HttpServletRequest request){
		return "companychart/welderandjunction";
	}
	
	/**
	 * 跳转公司超时待机页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goCompanyOvertime")
	public String goCompanyOvertime(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		lm.getUserId(request);
		request.setAttribute("parent", parent);
		request.setAttribute("parentime1", request.getParameter("parentime1"));
		request.setAttribute("parentime2", request.getParameter("parentime2"));
		return "companychart/companyovertime";
	}
	
	/**
	 * 跳转公司设备负荷率页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goCompanyLoads")
	public String goCompanyLoads(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		lm.getUserId(request);
		request.setAttribute("parent", parent);
		request.setAttribute("parentime1", request.getParameter("parentime1"));
		request.setAttribute("parentime2", request.getParameter("parentime2"));
		return "companychart/companyloads";
	}
	
	/**
	 * 跳转公司设备空载率页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goCompanyNoLoads")
	public String goCompanyNoLoads(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		lm.getUserId(request);
		request.setAttribute("parent", parent);
		request.setAttribute("parentime1", request.getParameter("parentime1"));
		request.setAttribute("parentime2", request.getParameter("parentime2"));
		return "companychart/companynoloads";
	}
	
	/**
	 * 跳转公司闲置率页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goCompanyIdle")
	public String goCompanyIdle(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		lm.getUserId(request);
		request.setAttribute("parent", parent);
		request.setAttribute("parentime1", request.getParameter("parentime1"));
		request.setAttribute("parentime2", request.getParameter("parentime2"));
		return "companychart/companyidle";
	}
	
	/**
	 * 跳转公司单台设备运行数据统计页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goCompanyUse")
	public String goCompanyUse(HttpServletRequest request){
		lm.getUserId(request);
		return "companychart/companyuse";
	}
	
	/**
	 * 跳转公司工效页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goCompanyEfficiency")
	public String goCompanyEfficiency(HttpServletRequest request){
		String nextparent = request.getParameter("nextparent");
		insm.showParent(request, nextparent);
		lm.getUserId(request);
		request.setAttribute("nextparent",nextparent);
		request.setAttribute("parentime1", request.getParameter("parentime1"));
		request.setAttribute("parentime2", request.getParameter("parentime2"));
		return "companychart/companyefficiency";
	}
	
	/**
	 * 公司工时报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCompanyHour")
	@ResponseBody
	public String getCompanyHour(HttpServletRequest request){
		if(iutil.isNull(request.getParameter("page"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
		}
		if(iutil.isNull(request.getParameter("rows"))){
			pageSize = Integer.parseInt(request.getParameter("rows"));
		}
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		String search = request.getParameter("search");
		WeldDto dto = new WeldDto();
		if(!iutil.isNull(parentId)){
			//数据权限处理
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			int types = insm.getUserInsfType(uid);
			if(types==21){
				parentId = insm.getUserInsfId(uid).toString();
			}
		}
		BigInteger parent = null;
		String s = (String)request.getSession().getAttribute("s");
		if(iutil.isNull(s)){
			dto.setSearch(s);
		}
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}
		if(iutil.isNull(search)){
			dto.setSearch(search);
		}
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.getCompanyhour(page,dto, parent);
		long total = 0;
		if(list != null){
			PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(list);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			for(ModelDto l:list){
				String[] str = l.getJidgather().split(",");
				if(l.getJidgather().equals("0")){
					json.put("jidgather", "0");
					json.put("dyne",0);
				}else{
					json.put("jidgather", str.length);
					String strsql = "and (";
					for(int i=0;i<str.length;i++){
						strsql += " fid = "+str[i];
						if(i<str.length-1){
							strsql += " or";
						}
					}
					strsql += " )";
					BigInteger dyne = lm.getDyneByJunctionno(strsql);
					json.put("dyne",dyne);
				}
				json.put("manhour", l.getHous());
				json.put("name",l.getFname());
				json.put("itemid",l.getFid());
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}
	
	/**
	 * 公司超标报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCompanyOverproof")
	@ResponseBody
	public String getCompanyOverproof(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		String type = request.getParameter("otype");
		WeldDto dto = new WeldDto();
		if(!iutil.isNull(parentId)){
			//数据权限处理
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			int types = insm.getUserInsfType(uid);
			if(types==21){
				parentId = insm.getUserInsfId(uid).toString();
			}
		}
		BigInteger parent = null;
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}
		if(iutil.isNull(type)){
			if(type.equals("1")){
				dto.setYear("year");
			}else if(type.equals("2")){
				dto.setMonth("month");
			}else if(type.equals("3")){
				dto.setDay("day");
			}else if(type.equals("4")){
				dto.setWeek("week");
			}
		}
		List<ModelDto> time = null;
		if(iutil.isNull(request.getParameter("page")) && iutil.isNull(request.getParameter("rows"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
			page = new Page(pageIndex,pageSize,total);
			time = lm.getAllTime(page,dto);
		}else{
			time = lm.getAllTimes(dto);
		}
		long total = 0;
		if(time != null){
			PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(time);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONArray arys = new JSONArray();
		JSONArray arys1 = new JSONArray();
		try{
			List<ModelDto> list = lm.getCompanyOverproof(dto,parent);
			List<LiveData> ins = lm.getAllInsf(parent,22);
			BigInteger[] num = null;
			for(ModelDto live :time){
				json.put("weldTime",live.getWeldTime());
				arys.add(json);
			}
			for(int i=0;i<ins.size();i++){
				num = new BigInteger[time.size()];
				for(int j=0;j<time.size();j++){
					num[j] = new BigInteger("0");
					for(ModelDto l:list){
						if(ins.get(i).getFname().equals(l.getFname()) && time.get(j).getWeldTime().equals(l.getWeldTime())){
							num[j] = l.getOverproof();
						}
					}
				}
				json.put("overproof",num);
				json.put("name",ins.get(i).getFname());
				json.put("itemid",ins.get(i).getFid());
				arys1.add(json);
			}
			JSONObject object = new JSONObject();
			
			for(int i=0;i<time.size();i++){
				for(int j=0;j<arys1.size();j++){
					JSONObject js = (JSONObject)arys1.get(j);
					String overproof = js.getString("overproof").substring(1, js.getString("overproof").length()-1);
					String[] str = overproof.split(",");
					object.put("a"+j, str[i]);
				}
				object.put("w",time.get(i).getWeldTime());
				ary.add(object);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		obj.put("arys", arys);
		obj.put("arys1", arys1);
		return obj.toString();
	}

	
	/**
	 * 公司超时报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCompanyOvertime")
	@ResponseBody
	public String getCompanyOvertime(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		String type = request.getParameter("otype");
		String number = request.getParameter("number");
		WeldDto dto = new WeldDto();
		if(!iutil.isNull(parentId)){
			//数据权限处理
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			int types = insm.getUserInsfType(uid);
			if(types==21){
				parentId = insm.getUserInsfId(uid).toString();
			}
		}
		BigInteger parent = null;
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}
		if(iutil.isNull(type)){
			if(type.equals("1")){
				dto.setYear("year");
			}else if(type.equals("2")){
				dto.setMonth("month");
			}else if(type.equals("3")){
				dto.setDay("day");
			}else if(type.equals("4")){
				dto.setWeek("week");
			}
		}
		if(!iutil.isNull(number)){
			number = "0";
		}
		List<ModelDto> time = null;
		if(iutil.isNull(request.getParameter("page")) && iutil.isNull(request.getParameter("rows"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
			page = new Page(pageIndex,pageSize,total);
			time = lm.getAllTime(page,dto);
		}else{
			time = lm.getAllTimes(dto);
		}
		long total = 0;
		if(time != null){
			PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(time);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONArray arys = new JSONArray();
		JSONArray arys1 = new JSONArray();
		try{
			List<ModelDto> list = lm.getcompanyOvertime(dto, number, parent);
			List<LiveData> ins = lm.getAllInsf(parent,22);
			int[] num = null;
			for(ModelDto live :time){
				json.put("weldTime",live.getWeldTime());
				arys.add(json);
			}
			for(int i=0;i<ins.size();i++){
				num = new int[time.size()];
				for(int j=0;j<time.size();j++){
					num[j] = 0;
					for(ModelDto l:list){
						if(ins.get(i).getFname().equals(l.getFname()) && time.get(j).getWeldTime().equals(l.getWeldTime())){
							num[j] = Integer.parseInt(l.getOvertime().toString());;
						}
					}
				}
				json.put("overtime",num);
				json.put("name",ins.get(i).getFname());
				json.put("itemid",ins.get(i).getId());
				arys1.add(json);
			}
			JSONObject object = new JSONObject();
			
			for(int i=0;i<time.size();i++){
				for(int j=0;j<arys1.size();j++){
					JSONObject js = (JSONObject)arys1.get(j);
					String overproof = js.getString("overtime").substring(1, js.getString("overtime").length()-1);
					String[] str = overproof.split(",");
					object.put("a"+j, str[i]);
				}
				object.put("w",time.get(i).getWeldTime());
				ary.add(object);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		obj.put("arys", arys);
		obj.put("arys1", arys1);
		return obj.toString();
	}

	/**
	 * 公司负荷率报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCompanyLoads")
	@ResponseBody
	public String getCompanyLoads(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		String type = request.getParameter("otype");
		WeldDto dto = new WeldDto();
		dto.setDtoStatus(1);
		if(!iutil.isNull(parentId)){
			//数据权限处理
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			int types = insm.getUserInsfType(uid);
			if(types==21){
				parentId = insm.getUserInsfId(uid).toString();
			}
		}
		BigInteger parent = null;
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}
		if(iutil.isNull(type)){
			if(type.equals("1")){
				dto.setYear("year");
			}else if(type.equals("2")){
				dto.setMonth("month");
			}else if(type.equals("3")){
				dto.setDay("day");
			}else if(type.equals("4")){
				dto.setWeek("week");
			}
		}
		List<ModelDto> time = null;
		if(iutil.isNull(request.getParameter("page")) && iutil.isNull(request.getParameter("rows"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
			page = new Page(pageIndex,pageSize,total);
			time = lm.getAllTime(page,dto);
		}else{
			time = lm.getAllTimes(dto);
		}
		long total = 0;
		if(time != null){
			PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(time);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONArray arys = new JSONArray();
		JSONArray arys1 = new JSONArray();
		try{
			List<ModelDto> list = lm.getCompanyLoads(dto,parent);
			List<ModelDto> machine = lm.getCompanyMachineCount(dto, parent);
			List<LiveData> ins = lm.getAllInsf(parent,22);
			double[] num = null;
			for(ModelDto live :time){
				json.put("weldTime",live.getWeldTime());
				arys.add(json);
			}
			for(int i=0;i<ins.size();i++){
				double[] load=new double[time.size()],summachine=new double[time.size()];
				num = new double[time.size()];
				for(int j=0;j<time.size();j++){
					num[j] = 0;
					for(ModelDto l:list){
						for(ModelDto m:machine){
							if(m.getWeldTime().equals(l.getWeldTime()) && m.getFid().equals(l.getIid())){
								if(ins.get(i).getFname().equals(l.getFname()) && time.get(j).getWeldTime().equals(l.getWeldTime())){
									load[j] = l.getLoads();
									summachine[j] = m.getLoads();
									num[j] = (double)Math.round(l.getLoads()/m.getLoads()*100*100)/100;
								}
							}
						}
					}
				}
				json.put("loads",num);
				json.put("name",ins.get(i).getFname());
				json.put("itemid",ins.get(i).getId());
				json.put("load",load);
				json.put("summachine",summachine);
				arys1.add(json);
			}
			JSONObject object = new JSONObject();
			
			for(int i=0;i<time.size();i++){
				for(int j=0;j<arys1.size();j++){
					JSONObject js = (JSONObject)arys1.get(j);
					String overproof = js.getString("loads").substring(1, js.getString("loads").length()-1);
					String load = js.getString("load").substring(1, js.getString("load").length()-1);
					String summachine = js.getString("summachine").substring(1, js.getString("summachine").length()-1);
					String[] overproofstr = overproof.split(",");
					String[] loadstr = load.split(",");
					String[] sumstr = summachine.split(",");
					object.put("a"+j, (double) Math.round(Double.valueOf(loadstr[i])*1000)/1000+"/"+sumstr[i]+"="+overproofstr[i]+"%");
				}
				object.put("w",time.get(i).getWeldTime());
				ary.add(object);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		obj.put("arys", arys);
		obj.put("arys1", arys1);
		return obj.toString();
	}

	/**
	 * 公司空载率报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCompanyNoLoads")
	@ResponseBody
	public String getCompanyNoLoads(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		String type = request.getParameter("otype");
		WeldDto dto = new WeldDto();
		if(!iutil.isNull(parentId)){
			//数据权限处理
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			int types = insm.getUserInsfType(uid);
			if(types==21){
				parentId = insm.getUserInsfId(uid).toString();
			}
		}
		BigInteger parent = null;
		dto.setDtoStatus(0);
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}
		if(iutil.isNull(type)){
			if(type.equals("1")){
				dto.setYear("year");
			}else if(type.equals("2")){
				dto.setMonth("month");
			}else if(type.equals("3")){
				dto.setDay("day");
			}else if(type.equals("4")){
				dto.setWeek("week");
			}
		}
		List<ModelDto> time = null;
		if(iutil.isNull(request.getParameter("page")) && iutil.isNull(request.getParameter("rows"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
			page = new Page(pageIndex,pageSize,total);
			time = lm.getAllTime(page,dto);
		}else{
			time = lm.getAllTimes(dto);
		}
		long total = 0;
		if(time != null){
			PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(time);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONArray arys = new JSONArray();
		JSONArray arys1 = new JSONArray();
		try{
			List<ModelDto> list = lm.getCompanyNoLoads(dto,parent);
			List<ModelDto> machine = lm.getCompanyMachineCount(dto, parent);
			List<LiveData> ins = lm.getAllInsf(parent,22);
			double[] num = null;
			for(ModelDto live :time){
				json.put("weldTime",live.getWeldTime());
				arys.add(json);
			}
			for(int i=0;i<ins.size();i++){
				double[] noload=new double[time.size()],summachine=new double[time.size()],livecount=new double[time.size()];
				num = new double[time.size()];
				for(int j=0;j<time.size();j++){
					num[j] = 0;
					for(ModelDto l:list){
						for(ModelDto m:machine){
							if(m.getWeldTime().equals(l.getWeldTime()) && m.getFid().equals(l.getIid())){
								if(ins.get(i).getFname().equals(l.getFname()) && time.get(j).getWeldTime().equals(l.getWeldTime())){
									livecount[j] = lm.getCountByTime(l.getIid(), l.getWeldTime(),null).doubleValue();
									noload[j] = l.getLoads();
									summachine[j] = m.getLoads();
									num[j] = (double)Math.round(l.getLoads()/livecount[j]/m.getLoads()*100*100)/100;
								}
							}
						}
					}
				}
				json.put("loads",num);
				json.put("name",ins.get(i).getFname());
				json.put("itemid",ins.get(i).getId());
				json.put("noload", noload);
				json.put("livecount", livecount);
				json.put("summachine", summachine);
				arys1.add(json);
			}
			JSONObject object = new JSONObject();
			
			for(int i=0;i<time.size();i++){
				for(int j=0;j<arys1.size();j++){
					JSONObject js = (JSONObject)arys1.get(j);
					String overproof = js.getString("loads").substring(1, js.getString("loads").length()-1);
					String load = js.getString("noload").substring(1, js.getString("noload").length()-1);
					String livecount = js.getString("livecount").substring(1, js.getString("livecount").length()-1);
					String summachine = js.getString("summachine").substring(1, js.getString("summachine").length()-1);
					String[] overproofstr = overproof.split(",");
					String[] loadstr = load.split(",");
					String[] livecountstr= livecount.split(",");
					String[] sumstr = summachine.split(",");
					object.put("a"+j, (double) Math.round(Double.valueOf(loadstr[i])*1000)/1000+"/"+(double) Math.round(Double.valueOf(livecountstr[i])*1000)/1000+"/"+sumstr[i]+"="+overproofstr[i]+"%");
				}
				object.put("w",time.get(i).getWeldTime());
				ary.add(object);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		obj.put("arys", arys);
		obj.put("arys1", arys1);
		return obj.toString();
	}

	
	/**
	 * 公司闲置率报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCompanyIdle")
	@ResponseBody
	public String getCompanyIdle(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String type = request.getParameter("otype");
		String parentId = request.getParameter("parent");
		WeldDto dto = new WeldDto();
		if(!iutil.isNull(parentId)){
			//数据权限处理
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			int types = insm.getUserInsfType(uid);
			if(types==21){
				parentId = insm.getUserInsfId(uid).toString();
			}
		}
		BigInteger parent = null;
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(type)){
			if(type.equals("1")){
				dto.setYear("year");
			}else if(type.equals("2")){
				dto.setMonth("month");
			}else if(type.equals("3")){
				dto.setDay("day");
			}else if(type.equals("4")){
				dto.setWeek("week");
			}
		}
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}
		List<ModelDto> time = null;
		if(iutil.isNull(request.getParameter("page")) && iutil.isNull(request.getParameter("rows"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
			page = new Page(pageIndex,pageSize,total);
			time = lm.getAllTime(page,dto);
		}else{
			time = lm.getAllTimes(dto);
		}
		long total = 0;
		if(time != null){
			PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(time);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONArray arys = new JSONArray();
		JSONArray arys1 = new JSONArray();
		try{
			List<ModelDto> list = lm.getCompanyIdle(dto,parent);
			List<LiveData> ins = lm.getAllInsf(parent,22);
			double[] num = null;
			for(ModelDto live :time){
				json.put("weldTime",live.getWeldTime());
				arys.add(json);
			}
			for(int i=0;i<ins.size();i++){
				num = new double[time.size()];
				int count = lm.getMachineCount(ins.get(i).getFid());
				for(int j=0;j<time.size();j++){
					num[j] = count;
					for(ModelDto l:list){
						if(ins.get(i).getFname().equals(l.getFname()) && time.get(j).getWeldTime().equals(l.getWeldTime())){
							num[j] = count - l.getNum().doubleValue();
						}
					}
				}
				json.put("idle",num);
				json.put("name",ins.get(i).getFname());
				json.put("id",ins.get(i).getFid());
				arys1.add(json);
			}
			JSONObject object = new JSONObject();
			
			for(int i=0;i<time.size();i++){
				for(int j=0;j<arys1.size();j++){
					JSONObject js = (JSONObject)arys1.get(j);
					String overproof = js.getString("idle").substring(1, js.getString("idle").length()-1);
					String[] str = overproof.split(",");
					object.put("a"+j, str[i]);
				}
				object.put("w",time.get(i).getWeldTime());
				ary.add(object);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		obj.put("arys", arys);
		obj.put("arys1", arys1);
		return obj.toString();
	}
	
	/**
	 * 公司单台设备运行数据统计信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCompanyUse")
	@ResponseBody
	public String getCompanyUse(HttpServletRequest request){
		if(iutil.isNull(request.getParameter("page"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
		}
		if(iutil.isNull(request.getParameter("rows"))){
			pageSize = Integer.parseInt(request.getParameter("rows"));
		}
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String type = request.getParameter("type");
		WeldDto dto = new WeldDto();
		lm.getUserId(request);
		String afreshLogin = (String)request.getAttribute("afreshLogin");
		if(iutil.isNull(afreshLogin)){
			return "0";
		}
		BigInteger typeid = null;
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(type)){
			typeid = new BigInteger(type);
		}
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.getCompanyUse(page, dto, typeid);
		long total = 0;
		if(list != null){
			PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(list);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			for(ModelDto l:list){
				double num = wm.getMachineCountByManu(l.getFid(),typeid).doubleValue();
				double time = (double)Math.round(l.getTime()/num*100)/100;
				json.put("time", time);
				json.put("fname", l.getFname()+" - "+l.getType());
				json.put("type", l.getType());
				json.put("fid",l.getFid());
				json.put("num", num);
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}
	
	/**
	 * 事业部下拉框
	 * @return
	 */
	@RequestMapping("getCaust")
	@ResponseBody
	public String getCaust(HttpServletRequest request){
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		BigInteger parent = null;
		//数据权限处理
		BigInteger uid = lm.getUserId(request);
		String afreshLogin = (String)request.getAttribute("afreshLogin");
		if(iutil.isNull(afreshLogin)){
			json.put("id", 0);
			json.put("name", "无");
			ary.add(json);
			obj.put("ary", ary);
			return obj.toString();
		}
		int type = insm.getUserInsfType(uid);
		if(type==21){
			parent = insm.getUserInsfId(uid);
		}
		try{
			List<Insframework> list = insm.getInsByType(22,parent);
			for(Insframework i:list){
				json.put("id", i.getId());
				json.put("name", i.getName());
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("ary", ary);
		return obj.toString();
	}
	
	/**
	 * 公司工效报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCompanyEfficiency")
	@ResponseBody
	public String getCompanyEfficiency(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		String nextparent = request.getParameter("nextparent");
		int min = -1,max = -1;
		if(iutil.isNull(request.getParameter("min"))){
			min = Integer.parseInt(request.getParameter("min"));
		}
		if(iutil.isNull(request.getParameter("max"))){
			max = Integer.parseInt(request.getParameter("max"));
		}
		WeldDto dto = new WeldDto();
		BigInteger parent = null;
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(nextparent)){
			parent = new BigInteger(nextparent);
		}else if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.companyEfficiency(page, parent, dto, min, max);
		PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(list);
		long total = pageinfo.getTotal();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			for(ModelDto m : list){
				json.put("id",m.getFid());
				json.put("iname",m.getIname());
				json.put("wname",m.getWname());
				json.put("wid",m.getFwelder_id());
				String[] str = m.getJidgather().split(",");
				String search = "and (";
				for(int i=0;i<str.length;i++){
					search += " fid = "+str[i];
					if(i<str.length-1){
						search += " or";
					}
				}
				search += " )";
				BigInteger dyne = lm.getDyneByJunctionno(search);
				json.put("dyne",dyne);
				double weldtime = (double)Math.round(Double.valueOf(m.getWeldTime())*100)/100;
				json.put("weldtime",weldtime);
				json.put("num",str.length);
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getJunctionByWelder")
	@ResponseBody
	public String getJunctionByWelder(HttpServletRequest request){
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String welder = request.getParameter("welder");
		String time1 = request.getParameter("dtoTime1");
		WeldDto dto = new WeldDto();
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.getJunctionByWelder(page, dto, welder);
		long total = 0;
		
		if(list != null){
			PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(list);
			total = pageinfo.getTotal();
		}
		
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			for(ModelDto j:list){
				json.put("weldedJunctionno", j.getFname());
				json.put("externalDiameter", j.getExternalDiameter());
				json.put("wallThickness", j.getWallThickness());
				json.put("maxElectricity", j.getFmax_electricity());
				json.put("minElectricity", j.getFmin_electricity());
				json.put("maxValtage", j.getFmax_valtage());
				json.put("minValtage", j.getFmin_valtage());
				json.put("material", j.getMaterial());
				json.put("nextexternaldiameter", j.getNextexternaldiameter());
				json.put("itemname", j.getIname());
				json.put("nextwall_thickness", j.getNextwallThickness());
				json.put("next_material", j.getNextmaterial());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}

	@RequestMapping("/getTimequantum")
	@ResponseBody
	public String getTimequantum(HttpServletRequest request,@RequestParam String welder,@RequestParam String junction, @RequestParam String time){
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.getExcessiveBack(time, welder, junction);
		long total = 0;
		if(list != null){
			PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(list);
			total = pageinfo.getTotal();
		}
		
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			int num=1;
			for(int i=0;i<list.size();i+=num){
				//遇到连续值
				if(list.get(i).getSum1()==1){
					int count =0;
					//判断是否连续5个
					for(int j=i;j<i+5;j++){
						if(list.get(j).getSum1()==1){
							count++;
						}
					}

					if(count==5){
						String[] electricity = new String[15];
						String[] weldtime = new String[15];
						String[] befor = new String[5];
						String[] after = new String[5];
						int index = 5;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						//前五秒
						Date date = sdf.parse(list.get(i).getWeldTime());
						Calendar calendar = Calendar.getInstance();    
						calendar.setTime(date);
						for(int p=befor.length-1;p>=0;p--){
							calendar.add(Calendar.SECOND, -1);
							befor[p] = sdf.format(calendar.getTime());
						}
						//后五秒
						Date afterdate = sdf.parse(list.get(i+4).getWeldTime());
						Calendar aftercalendar = Calendar.getInstance();    
						aftercalendar.setTime(afterdate);
						for(int p=0;p<=after.length-1;p++){
							aftercalendar.add(Calendar.SECOND, +1);
							after[p] = sdf.format(aftercalendar.getTime());
						}
						
						//当前方不足五位时
						if(i<5){
							for(int x=0;x<5-i;x++){//一定未超标的数据
								electricity[x]="0";
							}
							for(int y=5-i;y<5;y++){//可能超标的数据
								for(int p=0;p<befor.length;p++){
									if(list.get(y).getWeldTime().equals(befor[p])){
										electricity[y] = list.get(p).getFelectricity();
									}
									weldtime[p] = befor[p];
								}
							}
						}else{
							//获取前五位数据
							for(int y=i-5;y<i;y++){
								for(int p=0;p<befor.length;p++){
									if(list.get(y).getWeldTime().equals(befor[p])){
										electricity[p]=list.get(y).getFelectricity();
									}
									weldtime[p] = befor[p];
								}
							}
						}
						//获取当前超标五秒
						for(int x=i;x<i+5;x++){
							electricity[index]=list.get(x).getFelectricity();
							weldtime[index]=list.get(x).getWeldTime();
							index++;
						}
						//当后方不足5位时
						if(list.size()-i<5){
							for(int x=15-1;x>=10+(list.size()-i);x++){//一定未超标的数据
								electricity[x]="0";
							}
							for(int y=10;y<10+(list.size()-i);y++){//可能超标的数据
								for(int z=i;z<list.size();z++){
									for(int p=0;p<after.length;p++){
										if(list.get(z).getWeldTime().equals(after[p])){
											electricity[y] = list.get(z).getFelectricity();
										}
										weldtime[y] = after[p];
									}
								}
							}
						}else{
							//获取后五位数据
							for(int y=i+5;y<i+10;y++){
								for(int p=0;p<after.length;p++){
									if(list.get(y).getWeldTime().equals(after[p])){
										electricity[p+10] = list.get(y).getFelectricity();
									}
									weldtime[p+10] = after[p];
								}
							}
						}
						for(int p=0;p<weldtime.length;p++){
							if(!iutil.isNull(electricity[p])){
								electricity[p] = "0";
							}
						}
						json.put("maxelectricity", list.get(i).getFmax_electricity());
						json.put("minelectricity", list.get(i).getFmin_electricity());
						json.put("welder", list.get(i).getFwelder_id());
						json.put("junction", list.get(i).getFjunction_id());
						json.put("weldtime", list.get(i).getWeldTime());
						json.put("electricity", list.get(i).getFelectricity());
						json.put("weldtimes", weldtime);
						json.put("electricitys", electricity);
						ary.add(json);
						num = 5;
					}else{
						num = 1;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", ary.size());
		obj.put("rows", ary);
		return obj.toString();
	}

}
