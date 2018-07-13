package com.greatway.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
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
/**
 * 集团报表
 * @author gpyf16
 *
 */
@Controller
@RequestMapping(value = "/blocChart", produces = { "text/json;charset=UTF-8" })
public class BlocChartController {
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
	
	/**
	 * 跳转集团工时页面
	 * @return
	 */
	@RequestMapping("/goBlocHour")
	public String goBlocHour(){
		return "blocchart/blocHour";
	}
	
	/**
	 * 跳转集团超标页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goBlocOverproof")
	public String goBlocOverproof(HttpServletRequest request){
		return "blocchart/blocoverproof";
	}
	
	/**
	 * 跳转集团超时待机页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goBlocOvertime")
	public String goBlocOvertime(HttpServletRequest request){
		return "blocchart/blocovertime";
	}
	
	/**
	 * 跳转集团设备负荷率页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goBlocLoads")
	public String goBlocLoads(HttpServletRequest request){
		return "blocchart/blocloads";
	}
	
	/**
	 * 跳转集团设备空载率页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goBlocNoLoads")
	public String goBlocNoLoads(HttpServletRequest request){
		return "blocchart/blocnoloads";
	}
	
	/**
	 * 跳转集团闲置率页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goBlocIdle")
	public String goBlocIdle(HttpServletRequest request){
		return "blocchart/blocidle";
	}
	
	/**
	 * 跳转集团单台设备运行数据统计页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goBlocUse")
	public String goBlocUse(HttpServletRequest request){
		return "blocchart/blocuse";
	}
	

	/**
	 * 跳转集团工效页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goBlocEfficiency")
	public String goCompanyEfficiency(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		lm.getUserId(request);
		request.setAttribute("parent",parent);
		return "blocchart/blocefficiency";
	}
	
	/**
	 * 跳转集团设备运行时长页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goBlocRunTime")
	public String goBlocRunTime(HttpServletRequest request){
		lm.getUserId(request);
		return "blocchart/blocruntime";
	}
	
	/**
	 * 跳转设备利用率页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goUseratio")
	public String goUseratio(HttpServletRequest request){
		lm.getUserId(request);
		return "blocchart/useratio";
	}

	/**
	 * 跳转设备维修率页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goMaintenanceratio")
	public String goMaintenanceratio(HttpServletRequest request){
		lm.getUserId(request);
		return "blocchart/maintenance";
	}
	
	/**
	 * 集团工时报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getBlocHour")
	@ResponseBody
	public String getBlocHour(HttpServletRequest request){
		if(iutil.isNull(request.getParameter("page"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
		}
		if(iutil.isNull(request.getParameter("rows"))){
			pageSize = Integer.parseInt(request.getParameter("rows"));
		}
		String search = request.getParameter("search");
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		WeldDto dto = new WeldDto();
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
		if(iutil.isNull(search)){
			dto.setSearch(search);
		}
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.getBlochour(page,dto);
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
				json.put("companyid",l.getFid());
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
	 * 集团超标报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getBlocOverproof")
	@ResponseBody
	public String getBlocOverproof(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String type = request.getParameter("otype");
		WeldDto dto = new WeldDto();
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
			List<ModelDto> list = lm.getBlocOverproof(dto);
			List<LiveData> ins = lm.getBlocChildren();
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
	 * 集团超时报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getBlocOvertime")
	@ResponseBody
	public String getBlocOvertime(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String type = request.getParameter("otype");
		String number = request.getParameter("number");
		WeldDto dto = new WeldDto();
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
			List<ModelDto> list = lm.getBlocOvertime(dto, number);
			List<LiveData> ins = lm.getBlocChildren();
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
	 * 集团负荷率报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getBlocLoads")
	@ResponseBody
	public String getBlocLoads(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String type = request.getParameter("otype");
		WeldDto dto = new WeldDto();
		dto.setDtoStatus(1);
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
			List<ModelDto> list = lm.getBlocLoads(dto);
			List<ModelDto> machine = lm.getBlocMachineCount(dto, null);
			List<LiveData> ins = lm.getBlocChildren();
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
	 * 集团空载率报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getBlocNoLoads")
	@ResponseBody
	public String getBlocNoLoads(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String type = request.getParameter("otype");
		WeldDto dto = new WeldDto();
		dto.setDtoStatus(0);
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
			List<ModelDto> list = lm.getBlocNoLoads(dto);
			List<ModelDto> machine = lm.getBlocMachineCount(dto, null);
			List<LiveData> ins = lm.getBlocChildren();
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
	 * 集团闲置率报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getBlocIdle")
	@ResponseBody
	public String getBlocIdle(HttpServletRequest request){
		if(iutil.isNull(request.getParameter("page"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
		}
		if(iutil.isNull(request.getParameter("rows"))){
			pageSize = Integer.parseInt(request.getParameter("rows"));
		}
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String type = request.getParameter("otype");
		WeldDto dto = new WeldDto();
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
			List<ModelDto> list = lm.getBlocIdle(dto);
			List<LiveData> ins = lm.getBlocChildren();
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
	 * 集团单台设备运行数据统计信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getBlocUse")
	@ResponseBody
	public String getBlocUse(HttpServletRequest request){
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
		List<ModelDto> list = lm.getBlocUse(page, dto, typeid);
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
	 * 公司下拉框
	 * @return 
	 */
	@RequestMapping("getCaust")
	@ResponseBody
	public String getCaust(){
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			List<Insframework> list = insm.getInsByType(21,null);
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
	 * 集团工效报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getBlocEfficiency")
	@ResponseBody
	public String getBlocEfficiency(HttpServletRequest request){
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		int min = -1,max = -1;
		if(iutil.isNull(request.getParameter("min"))){
			min = Integer.parseInt(request.getParameter("min"));
		}
		if(iutil.isNull(request.getParameter("max"))){
			max = Integer.parseInt(request.getParameter("max"));
		}
		BigInteger parent = null;
		WeldDto dto = new WeldDto();
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.blocEfficiency(page, dto,parent,min,max);
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
	/**
	 * 获取焊口分类信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getBlocHousClassify")
	@ResponseBody
	public String getBlocHousClassify(HttpServletRequest request){
		String searchStr = request.getParameter("searchStr");
		
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.getHousClassify(page, null, searchStr);
		PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(list);
		long total = pageinfo.getTotal();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			String s = "";
			for(ModelDto m : list){
				json.put("fid",m.getFid());
				json.put("material",m.getMaterial());
				json.put("nextmaterial",m.getNextmaterial());
				json.put("wall_thickness",m.getWallThickness());
				json.put("nextwall_thickness",m.getNextwallThickness());
				json.put("external_diameter",m.getExternalDiameter());
				json.put("nextExternal_diameter",m.getNextexternaldiameter());
				ary.add(json);
				s = " (fmaterial='"+list.get(0).getMaterial()+"' and fexternal_diameter='"+list.get(0).getExternalDiameter()+
						"' and fwall_thickness='"+list.get(0).getWallThickness()+"' and fnextExternal_diameter='"+list.get(0).getNextexternaldiameter()+
						"' and fnextwall_thickness ='"+list.get(0).getNextwallThickness()+"' and Fnext_material ='"+list.get(0).getNextmaterial()+"')";
			}
			request.getSession().setAttribute("s", s);
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}

	/**
	 * 集团设备运行时长
	 * @param request
	 * @return
	 */
	@RequestMapping("/gerBlocRunTime")
	@ResponseBody
	public String getBlocRunTime(HttpServletRequest request){
		if(iutil.isNull(request.getParameter("page"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
		}
		if(iutil.isNull(request.getParameter("rows"))){
			pageSize = Integer.parseInt(request.getParameter("rows"));
		}
		String parentid = request.getParameter("parent");
		String time1 = request.getParameter("time1");
		String time2 = request.getParameter("time2");
		String[] str = request.getParameter("ranking").split("-");
		int startindex=Integer.parseInt(str[0]),endindex=Integer.parseInt(str[1]);
		WeldDto dto = new WeldDto();
		BigInteger parent = null;
		double avgnum = 0;
		if(iutil.isNull(parentid)){
			parent = new BigInteger(parentid);
		}
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		Page page = new Page(pageIndex, pageSize, total);
		List<ModelDto> list = lm.getBlocRunTime(page, parent, dto, startindex, endindex);
		long total = 0;
		if(list!=null){
			PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(list);
			total = pageinfo.getTotal();
		}
		try{
			for(ModelDto i:list){
				avgnum += i.getTime();
				json.put("time", (double)Math.round(i.getTime()*100)/100);
				json.put("machineno", i.getFmachine_id());
				json.put("itemname", i.getFname());
				ary.add(json);
			}
			avgnum = (double)Math.round(avgnum/list.size()*100)/100;
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("avgnum", avgnum);
		obj.put("rows", ary);
		obj.put("total", total);
		return obj.toString();
	}
	
	/**
	 * 获取当前用户下的所有组织机构
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInsframework")
	@ResponseBody
	public String getInsframework(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{//数据权限处理
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
			BigInteger parent = insm.getUserInsfId(uid);
			if(type==20){
				Insframework userinsf = insm.getInsById(parent);
				json.put("id", userinsf.getId());
				json.put("name", userinsf.getName());
				ary.add(json);
				List<Insframework> company = insm.getConmpany(null);
				for(Insframework c:company){
					json.put("id", c.getId());
					json.put("name", "&nbsp;&nbsp;&nbsp;"+c.getName());
					ary.add(json);
					List<Insframework> caust = insm.getCause(c.getId(), null);
					for(Insframework ca:caust){
						json.put("id", ca.getId());
						json.put("name", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ca.getName());
						ary.add(json);
						List<Insframework> item = insm.getCause(ca.getId(), null);
						for(Insframework i:item){
							json.put("id", i.getId());
							json.put("name", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+i.getName());
							ary.add(json);
						}
					}
				}
			}else if(type==21){
				Insframework userinsf = insm.getInsById(parent);
				json.put("id", userinsf.getId());
				json.put("name", userinsf.getName());
				ary.add(json);
				List<Insframework> caust = insm.getCause(parent, null);
				for(Insframework ca:caust){
					json.put("id", ca.getId());
					json.put("name", "&nbsp;&nbsp;&nbsp;"+ca.getName());
					ary.add(json);
					List<Insframework> item = insm.getCause(ca.getId(), null);
					for(Insframework i:item){
						json.put("id", i.getId());
						json.put("name", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+i.getName());
						ary.add(json);
					}
				}
			}else if(type==22){
				Insframework userinsf = insm.getInsById(parent);
				json.put("id", userinsf.getId());
				json.put("name", userinsf.getName());
				ary.add(json);
				List<Insframework> item = insm.getCause(parent, null);
				for(Insframework i:item){
					json.put("id", i.getId());
					json.put("name", "&nbsp;&nbsp;&nbsp;"+i.getName());
					ary.add(json);
				}
			}else if(type==23){
				Insframework item = insm.getInsById(parent);
				if(item!=null){
					json.put("id", item.getId());
					json.put("name", item.getName());
					ary.add(json);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("ary", ary);
		return obj.toString();
	}

	/**
	 * 获取当前用户下的所有组织机构
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInsframeworkType")
	@ResponseBody
	public String getInsframeworkType(@RequestParam BigInteger id){
		JSONObject obj = new JSONObject();
		obj.put("type", insm.getTypeById(id));
		return obj.toString();
	}
	
	/**
	 * 利用率
	 * @param request
	 * @return
	 */
	@RequestMapping("/getUseratio")
	@ResponseBody
	public String getUseratio(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		String parentid = request.getParameter("parent");
		String time1 = request.getParameter("time1");
		String time2 = request.getParameter("time2");
		int flag = Integer.parseInt(request.getParameter("flag"));
		BigInteger parent = null;
		List<Insframework> caust = null;
		if(iutil.isNull(parentid)){
			parent = new BigInteger(parentid);
		}
		if(iutil.isNull(request.getParameter("page")) && iutil.isNull(request.getParameter("rows"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
			page = new Page(pageIndex,pageSize,total);
			caust = insm.getCause(page, parent);
		}else{
			caust = insm.getCause(parent,null);
		}
		long total = 0;
		if(caust!=null){
			PageInfo<Insframework> pageinfo = new PageInfo<Insframework>(caust);
			total = pageinfo.getTotal();
		}
		try{
			List<ModelDto> list = lm.getUseratio(time1, time2);
			//获取时间差
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long t1 = sdf.parse(time1).getTime();
			long t2 = sdf.parse(time2).getTime();
			int days = (int)((t2-t1)/(1000*60*60*24))+1;
			if(flag==0){//集团层
				for(Insframework c:caust){
					double worktime = 0;
					for(ModelDto i:list){
						if(i.getFid().equals(c.getId())){
							worktime += i.getWorktime();
						}
					}
					double wt = (double)Math.round(worktime*100)/100;
					int num = lm.getMachineCount(c.getId());
					double useratio = (double)Math.round(wt/num/days*100*100)/100;
					json.put("name", c.getName());
					json.put("day", days);
					json.put("time", wt);
					json.put("num", num);
					json.put("useratio", useratio);
					ary.add(json);
				}
			}else if(flag==1){//公司层
				for(Insframework c:caust){
					double worktime = 0;
					for(ModelDto i:list){
						if(i.getCaustid().equals(c.getId())){
							worktime += i.getWorktime();
						}
					}
					double wt = (double)Math.round(worktime*100)/100;
					int num = lm.getMachineCount(c.getId());
					double useratio = (double)Math.round(wt/num/days*100*100)/100;
					json.put("name", c.getName());
					json.put("day", days);
					json.put("time", wt);
					json.put("num", num);
					json.put("useratio", useratio);
					ary.add(json);
				}
			}else if(flag==2){
				for(Insframework c:caust){
					double worktime = 0;
					for(ModelDto i:list){
						if(i.getItemid().equals(c.getId())){
							worktime += i.getWorktime();
						}
					}
					double wt = (double)Math.round(worktime*100)/100;
					int num = lm.getMachineCount(c.getId());
					double useratio = (double)Math.round(wt/num/days*100*100)/100;
					json.put("name", c.getName());
					json.put("day", days);
					json.put("time", wt);
					json.put("num", num);
					json.put("useratio", useratio);
					ary.add(json);
				}
			}else if(flag==3){
				boolean flags = false;
				for(ModelDto i:list){
					if(i.getItemid().equals(parent)){
						flags = true;
						double wt = (double)Math.round(i.getWorktime()*100)/100;
						int num = lm.getMachineCount(i.getItemid());
						double useratio = (double)Math.round(wt/num/days*100*100)/100;
						json.put("name", i.getFname());
						json.put("day", days);
						json.put("time", wt);
						json.put("num", num);
						json.put("useratio", useratio);
						ary.add(json);
					}
				}
				if(!flags){
					Insframework ins = insm.getInsById(parent);
					if(ins!=null){
						json.put("name", ins.getName());
						json.put("day", days);
						json.put("time", 0);
						json.put("num", lm.getMachineCount(ins.getId()));
						json.put("useratio", "0");
						ary.add(json);
					}
				}
				total = 1;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("rows", ary);
		obj.put("total", total);
		return obj.toString();
	}
	
	/**
	 * 维修率
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMaintenanceratio")
	@ResponseBody
	public String getMaintenanceratio(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		String parentid = request.getParameter("parent");
		String time1 = request.getParameter("time1");
		String time2 = request.getParameter("time2");
		int flag = Integer.parseInt(request.getParameter("flag"));
		WeldDto dto = new WeldDto();
		BigInteger parent = null;
		List<ModelDto> list = null;
		if(iutil.isNull(parentid)){
			parent = new BigInteger(parentid);
		}
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(request.getParameter("page")) && iutil.isNull(request.getParameter("rows"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
			page = new Page(pageIndex,pageSize,total);
			list = lm.getMaintenanceratio(page, dto);
		}else{
			list = lm.getMaintenanceratio(dto);
		}
		long total = 0;
		if(list!=null){
			PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(list);
			total = pageinfo.getTotal();
		}
		try{
			//获取所选组织机构的所有下级部门
			List<Insframework> insf = insm.getCause(parent, null);
			if(flag==0){//集团层
				for(int j=0;j<insf.size();j++){
					boolean flagnum = false;
					int rmoney = 0, mmoney = 0, num = 0;
					for(int i=0;i<list.size();i++){
						if(list.get(i).getFid().equals(insf.get(j).getId())){
							flagnum = true;
							rmoney += list.get(i).getRmoney();
							mmoney += list.get(i).getMmoney();
							num += list.get(i).getTotal();
						}
					}
					if(flagnum){
						json.put("name",insf.get(j).getName());
						json.put("total", num);
						json.put("rmoney", rmoney);
						json.put("mmoney", mmoney);
						ary.add(json);
					}
				}
			}else if(flag==1){//公司层
				for(int i=0;i<list.size();i++){
					if(list.get(i).getIid().equals(parent)){
						json.put("name", list.get(i).getIname());
						json.put("total", list.get(i).getTotal());
						json.put("rmoney", list.get(i).getRmoney());
						json.put("mmoney", list.get(i).getMmoney());
						ary.add(json);
					}
				}
			}else if(flag==2){
				for(int i=0;i<list.size();i++){
					if(list.get(i).getCaustid().equals(parent)){
						json.put("name", list.get(i).getWname());
						json.put("total", list.get(i).getTotal());
						json.put("rmoney", list.get(i).getRmoney());
						json.put("mmoney", list.get(i).getMmoney());
						ary.add(json);
					}
				}
			}else if(flag==3){
				for(int i=0;i<list.size();i++){
					if(list.get(i).getItemid().equals(parent)){
						json.put("name", list.get(i).getWname());
						json.put("total", list.get(i).getTotal());
						json.put("rmoney", list.get(i).getRmoney());
						json.put("mmoney", list.get(i).getMmoney());
						ary.add(json);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("rows", ary);
		obj.put("total", total);
		return obj.toString();
	}
}
