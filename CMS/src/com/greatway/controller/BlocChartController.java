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
import com.greatway.manager.DictionaryManager;
import com.greatway.manager.InsframeworkManager;
import com.greatway.manager.LiveDataManager;
import com.greatway.manager.WeldingMachineManager;
import com.greatway.model.Dictionarys;
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
	
	@Autowired
	private DictionaryManager dm;
	
	IsnullUtil iutil = new IsnullUtil();

	/**
	 * 焊工焊接工作时间
	 * @return
	 */
	@RequestMapping("/goWelderWorkTime")
	public String goWelderWorkTime(){
		return "blocchart/welderWorkTime";
	}
	
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
	 * 跳转设备维修率页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goFaultratio")
	public String goFaultratio(HttpServletRequest request){
		lm.getUserId(request);
		return "blocchart/fault";
	}
	
	/**
	 * 跳转操作者效率页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goOperatorEfficiency")
	public String goOperatorEfficiency(HttpServletRequest request){
		lm.getUserId(request);
		return "blocchart/operatorefficiency";
	}
	
	@RequestMapping("goNewOvertime")
	public String goNewOvertime(HttpServletRequest request){
		lm.getUserId(request);
		request.setAttribute("nextparent", request.getParameter("parent"));
		return "blocchart/newovertime";
	}
	
	@RequestMapping("goNewIdle")
	public String goNewIdle(HttpServletRequest request){
		lm.getUserId(request);
		return "blocchart/newidle";
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
				json.put("manhour", (double)Math.round(l.getTime()*100)/100);
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
			time = lm.getDurationTime(page, time1, time2, Integer.parseInt(type));
		}else{
			time = lm.getDurationTime(time1, time2, Integer.parseInt(type));
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
			double[] num = null;
			for(ModelDto live :time){
				json.put("weldTime",live.getWeldTime());
				arys.add(json);
			}
			for(int i=0;i<ins.size();i++){
				num = new double[time.size()];
				for(int j=0;j<time.size();j++){
					num[j] = 0;
					for(ModelDto l:list){
						if(ins.get(i).getFname().equals(l.getFname()) && time.get(j).getWeldTime().equals(l.getWeldTime())){
							num[j] = (double)Math.round(l.getOverproof()*100)/100;
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
			time = lm.getDurationTime(page, time1, time2, Integer.parseInt(type));
		}else{
			time = lm.getDurationTime(time1, time2, Integer.parseInt(type));
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
			time = lm.getDurationTime(page, time1, time2, Integer.parseInt(type));
		}else{
			time = lm.getDurationTime(time1, time2, Integer.parseInt(type));
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
								if(ins.get(i).getId().equals(l.getIid()) && time.get(j).getWeldTime().equals(l.getWeldTime())){
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
			time = lm.getDurationTime(page, time1, time2, Integer.parseInt(type));
		}else{
			time = lm.getDurationTime(time1, time2, Integer.parseInt(type));
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
//			List<ModelDto> machine = lm.getBlocMachineCount(dto, null);
			List<LiveData> ins = lm.getBlocChildren();
			double[] num = null;
			for(ModelDto live :time){
				json.put("weldTime",live.getWeldTime());
				arys.add(json);
			}
			for(int i=0;i<ins.size();i++){
				double[] noload=new double[time.size()],livecount=new double[time.size()];
				num = new double[time.size()];
				for(int j=0;j<time.size();j++){
					num[j] = 0;
					for(ModelDto l:list){
//						for(ModelDto m:machine){
//							if(m.getWeldTime().equals(l.getWeldTime()) && m.getFid().equals(l.getIid())){
								if(ins.get(i).getId().equals(l.getIid()) && time.get(j).getWeldTime().equals(l.getWeldTime())){
									if(Integer.parseInt(type)!=4){
										livecount[j] = lm.getCountByTime(l.getIid(), l.getWeldTime(),null,null,Integer.parseInt(type));
									}else{
										String[] str = l.getWeldTime().split("-");
										String weekdate = iutil.getWeekDay(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
										String[] weektime = weekdate.split("/");
										if(j==0){
											livecount[j] = lm.getCountByTime(l.getIid(), time1,weektime[1],null,Integer.parseInt(type));
										}else if(j==time.size()-1){
											livecount[j] = lm.getCountByTime(l.getIid(), weektime[0],time2,null,Integer.parseInt(type));
										}else{
											livecount[j] = lm.getCountByTime(l.getIid(), weektime[0],weektime[1],null,Integer.parseInt(type));
										}
										
									}
									noload[j] = l.getLoads();
									num[j] = (double)Math.round(l.getLoads()/livecount[j]*100*100)/100;
								}
//							}
//						}
					}
				}
				json.put("loads",num);
				json.put("name",ins.get(i).getFname());
				json.put("itemid",ins.get(i).getId());
				json.put("noload", noload);
				json.put("livecount", livecount);
				arys1.add(json);
			}
			JSONObject object = new JSONObject();
			
			for(int i=0;i<time.size();i++){
				for(int j=0;j<arys1.size();j++){
					JSONObject js = (JSONObject)arys1.get(j);
					String overproof = js.getString("loads").substring(1, js.getString("loads").length()-1);
					String load = js.getString("noload").substring(1, js.getString("noload").length()-1);
					String livecount = js.getString("livecount").substring(1, js.getString("livecount").length()-1);
					String[] overproofstr = overproof.split(",");
					String[] loadstr = load.split(",");
					String[] livecountstr= livecount.split(",");
					object.put("a"+j, (double) Math.round(Double.valueOf(loadstr[i])*1000)/1000+"/"+(double) Math.round(Double.valueOf(livecountstr[i])*1000)/1000+"="+overproofstr[i]+"%");
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
			}else if(type.equals("5")){
				dto.setDay("day");
			}else if(type.equals("6")){
				dto.setWeek("week");
			}
		}
		List<ModelDto> time = null;
		if(iutil.isNull(request.getParameter("page")) && iutil.isNull(request.getParameter("rows"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
			page = new Page(pageIndex,pageSize,total);
			time = lm.getDurationTime(page, time1, time2, Integer.parseInt(type));
		}else{
			time = lm.getDurationTime(time1, time2, Integer.parseInt(type));
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
			double[] bilv = null;
			for(ModelDto live :time){
				if(type.equals("6")){
					String[] str = live.getWeldTime().split("-");
					if(str[1].equals("1")){
						json.put("weldTime",str[0]+"-上半年");
					}else{
						json.put("weldTime",str[0]+"-下半年");
					}
				}else{
					json.put("weldTime",live.getWeldTime());
				}
				arys.add(json);
			}
			for(int i=0;i<ins.size();i++){
				num = new double[time.size()];
				bilv = new double[time.size()];
				int count = lm.getMachineCount(ins.get(i).getFid());
				for(int j=0;j<time.size();j++){
					num[j] = count;
					if(count==0){
						bilv[j] = 0;
					}else{
						bilv[j] = (double)Math.round(num[j]*10000/count)/100;
					}
					for(ModelDto l:list){
						if(ins.get(i).getFname().equals(l.getFname()) && time.get(j).getWeldTime().equals(l.getWeldTime())){
							num[j] = count - l.getNum().doubleValue();
							bilv[j] = (double)Math.round(num[j]*10000/count)/100;
						}
					}
				}
				json.put("bilv", bilv);
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
				if(type.equals("6")){
					String[] str = time.get(i).getWeldTime().split("-");
					if(str[1].equals("1")){
						object.put("w",str[0]+"-上半年");
					}else{
						object.put("w",str[0]+"-下半年");
					}
				}else{
					object.put("w",time.get(i).getWeldTime());
				}
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
				json.put("name", l.getFname());
				json.put("type", l.getType());
				json.put("fid",l.getFid());
				json.put("num", num);
				json.put("typeid", l.getTypeid());
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
				if(iutil.isNull(m.getJidgather())){
					String[] str = m.getJidgather().split(",");
					/*String search = "and (";
					for(int i=0;i<str.length;i++){
						search += " fid = "+str[i];
						if(i<str.length-1){
							search += " or";
						}
					}
					search += " )";
					BigInteger dyne = lm.getDyneByJunctionno(search);
					json.put("dyne",dyne);*/
					json.put("num",str.length);
				}else{
					json.put("num",0);
				}
				double weldtime = (double)Math.round(Double.valueOf(m.getWeldTime())*100)/100;
				json.put("weldtime",weldtime);
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
		String parentid = request.getParameter("parent");
		String time1 = request.getParameter("time1");
		String time2 = request.getParameter("time2");
//		int rank1 = Integer.parseInt(request.getParameter("rank1"))-1;
//		int rank2 = Integer.parseInt(request.getParameter("rank2"));
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
		List<ModelDto> list = null;
		if(iutil.isNull(request.getParameter("page")) && iutil.isNull(request.getParameter("rows"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
			page = new Page(pageIndex,pageSize,total);
			list = lm.getBlocRunTime(page, parent, dto, 0, 0);
		}else{
			list = lm.getBlocRunTime(parent, dto, 0, 0);
		}
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
		String time1 = request.getParameter("time1")+" 00:00:00";
		String time2 = request.getParameter("time2")+" 23:59:59";
		int flag = Integer.parseInt(request.getParameter("flag"));
		BigInteger parent = null;
		List<Insframework> insf = null;
		String status = "itemid";
		if(iutil.isNull(parentid)){
			parent = new BigInteger(parentid);
			int type = insm.getTypeById(parent);
			if(type==20){
				status = "fid";
			}else if(type==21){
				status = "caustid";
			}else if(type==22){
				status = "itemid";
			}else if(type==23){
				status = "itemid";
			}
		}
		if(iutil.isNull(request.getParameter("page")) && iutil.isNull(request.getParameter("rows"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
			page = new Page(pageIndex,pageSize,total);
			insf = insm.getCause(page, parent);
		}else{
			insf = insm.getCause(parent, null);
		}
		long total = 0;
		if(insf!=null){
			PageInfo<Insframework> pageinfo = new PageInfo<Insframework>(insf);
			total = pageinfo.getTotal();
		}
		try{
			List<ModelDto> list = lm.getUseratio(time1, time2, status);
			//获取时间差
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long t1 = sdf.parse(time1).getTime();
			long t2 = sdf.parse(time2).getTime();
			int days = (int)((t2-t1)/(1000*60*60*24))+1;
			if(flag==0){//集团层
				for(int i=0;i<insf.size();i++){
					double max = 0,worktime = 0;
					int machinenum = lm.getMachineCount(insf.get(i).getId());
					int maxnum = 0;
					for(int j=0;j<list.size();j++){
						if(insf.get(i).getId().equals(list.get(j).getFid())){
							worktime = list.get(j).getWorktime();
							maxnum = list.get(j).getTotal();
							max = (double)Math.round(((double)list.get(j).getTotal()/(double)machinenum)*10000)/100;
						}
					}
					json.put("name", insf.get(i).getName());
					json.put("day", days);
					json.put("time", (double)Math.round(worktime*100)/100);
					json.put("maxnum", maxnum);
					json.put("num", machinenum);
					json.put("useratio", max);
					ary.add(json);
				}
			}else if(flag==1){//公司层
				for(int i=0;i<insf.size();i++){
					double max = 0,worktime = 0;
					int machinenum = lm.getMachineCount(insf.get(i).getId());
					int maxnum = 0;
					for(int j=0;j<list.size();j++){
						if(insf.get(i).getId().equals(list.get(j).getCaustid())){
							worktime = list.get(j).getWorktime();
							maxnum = list.get(j).getTotal();
							max = (double)Math.round(((double)list.get(j).getTotal()/machinenum)*10000)/100;
						}
					}
					json.put("name", insf.get(i).getName());
					json.put("day", days);
					json.put("time", (double)Math.round(worktime*100)/100);
					json.put("maxnum", maxnum);
					json.put("num", machinenum);
					json.put("useratio", max);
					ary.add(json);
				}
			}else if(flag==2){
				for(int i=0;i<insf.size();i++){
					double max = 0,worktime = 0;
					int machinenum = lm.getMachineCount(insf.get(i).getId());
					int maxnum = 0;
					for(int j=0;j<list.size();j++){
						if(insf.get(i).getId().equals(list.get(j).getItemid())){
							worktime = list.get(j).getWorktime();
							maxnum = list.get(j).getTotal();
							max = (double)Math.round(((double)list.get(j).getTotal()/machinenum)*10000)/100;
						}
					}
					json.put("name", insf.get(i).getName());
					json.put("day", days);
					json.put("time", (double)Math.round(worktime*100)/100);
					json.put("maxnum", maxnum);
					json.put("num", machinenum);
					json.put("useratio", max);
					ary.add(json);
				}
			}else if(flag==3){
				boolean flags = false;
				for(int i=0;i<list.size();i++){
					double max = 0,worktime = 0;
					int machinenum = 0, maxnum = 0;
					if(list.get(i).getItemid().equals(parent)){
						flags = true;
						worktime = list.get(i).getWorktime();
						maxnum = list.get(i).getTotal();
						machinenum = lm.getMachineCount(parent);
						max = (double)Math.round(((double)list.get(i).getTotal()/machinenum)*10000)/100;
						json.put("name", list.get(i).getFname());
						json.put("day", days);
						json.put("time", (double)Math.round(worktime*100)/100);
						json.put("maxnum", maxnum);
						json.put("num", machinenum);
						json.put("useratio", max);
						ary.add(json);
					}
				}
				if(!flags){
					Insframework ins = insm.getInsById(parent);
					if(ins!=null){
						json.put("name", ins.getName());
						json.put("day", days);
						json.put("time", 0);
						json.put("maxnum", 0);
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
		JSONObject jsons = new JSONObject();
		JSONArray arys = new JSONArray();
		String parentid = request.getParameter("parent");
		String time1 = request.getParameter("time1");
		String time2 = request.getParameter("time2");
		int flag = Integer.parseInt(request.getParameter("flag"));
		WeldDto dto = new WeldDto();
		BigInteger parent = null;
		List<Insframework> insf = null;
		if(iutil.isNull(parentid)){
			parent = new BigInteger(parentid);
			dto.setParent(parent);
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
			insf = insm.getCause(page, parent);
		}else{
			insf = insm.getCause(parent, null);
		}
		long total = 0;
		if(insf!=null){
			PageInfo<Insframework> pageinfo = new PageInfo<Insframework>(insf);
			total = pageinfo.getTotal();
		}
		try{
			//获取所选组织机构的所有下级部门
			List<ModelDto> list = lm.getMaintenanceratio(dto);
			List<ModelDto> money = lm.getMachineMoney();
			List<ModelDto> fault = lm.getFaultRatio(dto);
			int sumnum = 0,faultnum = 0,summoney = 0,sumrmoney = 0;
			for(int i=0;i<list.size();i++){
				sumnum += list.get(i).getTotal();
				sumrmoney += list.get(i).getRmoney();
			}
			for(int i=0;i<fault.size();i++){
				faultnum += fault.get(i).getTotal();
			}
			for(int i=0;i<money.size();i++){
				summoney += money.get(i).getMmoney();
			}
			jsons.put("sumnum", sumnum);//总维护次数
			jsons.put("faultnum", faultnum);//总故障次数
			if(faultnum==0){
				jsons.put("sumfaultmaintenance", 100);//总故障维修率
			}else{
				jsons.put("sumfaultmaintenance", (double)Math.round((double)sumnum/(double)faultnum*100*100)/100);//总故障维修率
			}
			jsons.put("sumrmoney", sumrmoney);//维护总费用
			jsons.put("summoney", summoney);//焊机总费用
			arys.add(jsons);
			if(flag==0){//集团层
				for(int j=0;j<insf.size();j++){
					boolean flagnum = true;
					int rmoney = 0, mmoney = 0, num = 0, faultratio = 0;
					//统计设备费用
					for(int x=0;x<money.size();x++){
						if(insf.get(j).getId().equals(money.get(x).getFid())){
							mmoney += money.get(x).getMmoney();
						}
					}
					//统计设备故障率
					for(int x=0;x<fault.size();x++){
						if(insf.get(j).getId().equals(fault.get(x).getFid())){
							faultratio += fault.get(x).getTotal();
						}
					}
					for(int i=0;i<list.size();i++){
						if(list.get(i).getFid().equals(insf.get(j).getId())){
							rmoney += list.get(i).getRmoney();
							num += list.get(i).getTotal();
						}
					}
					if(list.isEmpty()){
						flagnum = false;
						json.put("id",insf.get(j).getId());
						json.put("name",insf.get(j).getName());
						json.put("total", 0);
						json.put("rmoney", 0);
						json.put("mmoney", mmoney);
						json.put("sumnum", 0);
						json.put("proportion", 0);
						json.put("faultratio", 0);
						json.put("faultmaintenanceratio", 100);
						ary.add(json);
					}
					if(flagnum){
						json.put("id",insf.get(j).getId());
						json.put("name",insf.get(j).getName());
						json.put("total", num);
						json.put("rmoney", rmoney);
						json.put("mmoney", mmoney);
						json.put("sumnum", sumnum);
						json.put("proportion", (double)Math.round((double)num/(double)sumnum*100)/100);
						json.put("faultratio", faultratio);
						if(faultratio==0){
							json.put("faultmaintenanceratio", 100);
						}else{
							json.put("faultmaintenanceratio", (double)Math.round((double)num/(double)faultratio*100*100)/100);
						}
						ary.add(json);
					}
				}
			}else if(flag==1){//公司层
				for(int j=0;j<insf.size();j++){
					boolean flagnum = true;
					int rmoney = 0, mmoney = 0, num = 0, faultratio = 0;
					//统计设备费用
					for(int x=0;x<money.size();x++){
						if(insf.get(j).getId().equals(money.get(x).getCaustid())){
							mmoney += money.get(x).getMmoney();
						}
					}
					//统计设备故障率
					for(int x=0;x<fault.size();x++){
						if(insf.get(j).getId().equals(fault.get(x).getCaustid())){
							faultratio += fault.get(x).getTotal();
						}
					}
					for(int i=0;i<list.size();i++){
						if(list.get(i).getCaustid().equals(insf.get(j).getId())){
							rmoney += list.get(i).getRmoney();
							num += list.get(i).getTotal();
						}
					}
					if(list.isEmpty()){
						flagnum = false;
						json.put("id",insf.get(j).getId());
						json.put("name",insf.get(j).getName());
						json.put("total", 0);
						json.put("rmoney", 0);
						json.put("mmoney", mmoney);
						json.put("sumnum", 0);
						json.put("proportion", 0);
						json.put("faultratio", 0);
						json.put("faultmaintenanceratio", 100);
						ary.add(json);
					}
					if(flagnum){
						json.put("id",insf.get(j).getId());
						json.put("name",insf.get(j).getName());
						json.put("total", num);
						json.put("rmoney", rmoney);
						json.put("mmoney", mmoney);
						json.put("sumnum", sumnum);
						json.put("proportion", (double)Math.round((double)num/(double)sumnum*100)/100);
						json.put("faultratio", faultratio);
						if(faultratio==0){
							json.put("faultmaintenanceratio", 100);
						}else{
							json.put("faultmaintenanceratio", (double)Math.round((double)num/(double)faultratio*100*100)/100);
						}
						ary.add(json);
					}
				}
			}else if(flag==2){
				for(int j=0;j<insf.size();j++){
					boolean flagnum = true;
					int rmoney = 0, mmoney = 0, num = 0, faultratio = 0 ;
					//统计设备费用
					for(int x=0;x<money.size();x++){
						if(insf.get(j).getId().equals(money.get(x).getItemid())){
							mmoney += money.get(x).getMmoney();
						}
					}
					//统计设备故障率
					for(int x=0;x<fault.size();x++){
						if(insf.get(j).getId().equals(fault.get(x).getItemid())){
							faultratio += fault.get(x).getTotal();
						}
					}
					for(int i=0;i<list.size();i++){
						if(list.get(i).getItemid().equals(insf.get(j).getId())){
							rmoney += list.get(i).getRmoney();
							num += list.get(i).getTotal();
						}
					}
					if(list.isEmpty()){
						flagnum = false;
						json.put("id",insf.get(j).getId());
						json.put("name",insf.get(j).getName());
						json.put("total", 0);
						json.put("rmoney", 0);
						json.put("mmoney", mmoney);
						json.put("sumnum", 0);
						json.put("proportion", 0);
						json.put("faultratio", 0);
						json.put("faultmaintenanceratio", 100);
						ary.add(json);
					}
					if(flagnum){
						json.put("id",insf.get(j).getId());
						json.put("name",insf.get(j).getName());
						json.put("total", num);
						json.put("rmoney", rmoney);
						json.put("mmoney", mmoney);
						json.put("sumnum", sumnum);
						json.put("proportion", (double)Math.round((double)num/(double)sumnum*100)/100);
						json.put("faultratio", faultratio);
						if(faultratio==0){
							json.put("faultmaintenanceratio", 100);
						}else{
							json.put("faultmaintenanceratio", (double)Math.round((double)num/(double)faultratio*100*100)/100);
						}
						ary.add(json);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("rows", ary);
		obj.put("ary", arys);
		obj.put("total", total);
		return obj.toString();
	}
	

	/**
	 * 故障率
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFaultratio")
	@ResponseBody
	public String getFaultratio(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		String parentid = request.getParameter("parent");
		String time1 = request.getParameter("time1");
		String time2 = request.getParameter("time2");
		WeldDto dto = new WeldDto();
		List<Dictionarys> faulttype = null;
		if(iutil.isNull(parentid)){
			dto.setParent(new BigInteger(parentid));
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
			faulttype = dm.getDictionaryValue(page, 7);
		}else{
			faulttype = dm.getDictionaryValue(7);
		}
		long total = 0;
		if(faulttype!=null){
			PageInfo<Dictionarys> pageinfo = new PageInfo<Dictionarys>(faulttype);
			total = pageinfo.getTotal();
		}
		try{

			List<ModelDto> list = lm.getFaultRatioByType(dto);
			int faultnum = 0;
			for(int i=0;i<list.size();i++){
				faultnum += list.get(i).getTotal();
			}
			for(int x=0;x<faulttype.size();x++){

				if(list.isEmpty()){
					json.put("typeid", faulttype.get(x).getValue());
					json.put("type", faulttype.get(x).getValueName());
					json.put("faultnum", 0);
					json.put("faultratio", (double)Math.round(1/(double)faulttype.size()*100*100)/100);
				}else{
					boolean flag = false;
					for(int i=0;i<list.size();i++){
						if(faulttype.get(x).getValue()==list.get(i).getTypeid()){
							flag = true;
							json.put("typeid", list.get(i).getTypeid());
							json.put("type", list.get(i).getType());
							json.put("faultnum", list.get(i).getTotal());
							if(faultnum==0){
								json.put("faultratio", (double)Math.round(1/(double)faulttype.size()*100*100)/100);
							}else{
								json.put("faultratio", (double)Math.round((double)list.get(i).getTotal()/(double)faultnum*100*100)/100);
							}
						}
					}
					if(!flag){
						json.put("typeid", faulttype.get(x).getValue());
						json.put("type", faulttype.get(x).getValueName());
						json.put("faultnum", 0);
						json.put("faultratio", 0);
					}
				}
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("rows", ary);
		obj.put("total", total);
		return obj.toString();
	}

	/**
	 * 操作者效率
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOperatorEfficiency")
	@ResponseBody
	public String getOperatorEfficiency(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		String parentid = request.getParameter("parent");
		String time1 = request.getParameter("time1");
		String time2 = request.getParameter("time2");
		int flag = Integer.parseInt(request.getParameter("flag"));
		WeldDto dto = new WeldDto();
		BigInteger parent = null;
		List<Insframework> insf = null;
		if(iutil.isNull(parentid)){
			parent = new BigInteger(parentid);
			dto.setParent(parent);
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
			insf = insm.getCause(page, parent);
		}else{
			insf = insm.getCause(parent, null);
		}
		long total = 0;
		if(insf!=null){
			PageInfo<Insframework> pageinfo = new PageInfo<Insframework>(insf);
			total = pageinfo.getTotal();
		}
		try{
			List<Dictionarys> dictionary = dm.getDictionaryValue(10);
			//获取所选组织机构的所有下级部门
			List<ModelDto> list = lm.getOnlineNumber(dto,Double.valueOf(dictionary.get(0).getValueName()));
			List<ModelDto> time = lm.getOperatoreTime(dto);
			//获取时间差
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long t1 = sdf.parse(time1).getTime();
			long t2 = sdf.parse(time2).getTime();
			int days = (int)((t2-t1)/(1000*60*60*24))+1;
			if(flag==0){//集团层
				for(int j=0;j<insf.size();j++){
					int count = wm.getCountByInsframework(insf.get(j).getId());
					double worktime = 0,boottime = 0,weldtime = 0,standbytime = 0;
					//上班时长
					for(int x=0;x<list.size();x++){
						if(insf.get(j).getId().equals(list.get(x).getFid())){
							worktime += list.get(x).getTotal();
						}
					}
					//开机时长,焊接时长，待机时长
					for(int x=0;x<time.size();x++){
						if(insf.get(j).getId().equals(time.get(x).getFid())){
							boottime += time.get(x).getWorktime();
							weldtime += time.get(x).getLoads();
							standbytime += time.get(x).getTime();
						}
					}
					if(list.isEmpty()){
						json.put("name",insf.get(j).getName());
						json.put("worktime", 0);//上班时长
						json.put("boottime", 0);//开机时长
						json.put("shutdowntime", days*24*count);//关机时长
						json.put("weldtime", 0);//焊接时长
						json.put("standbytime", 0);//待机时长
						json.put("sjratio", 0);//上机率
						json.put("effectiveratio", 0);//有效焊接率
						json.put("workratio", 0);//工作效率
						ary.add(json);
					}else{
						json.put("name",insf.get(j).getName());
						json.put("worktime", (double)Math.round(worktime*100)/100);
						json.put("boottime", (double)Math.round(boottime*100)/100);
						json.put("shutdowntime", (double)Math.round((days*24*count-boottime)*100)/100);
						json.put("weldtime", (double)Math.round(weldtime*100)/100);
						json.put("standbytime", (double)Math.round(standbytime*100)/100);
						json.put("sjratio", (double)Math.round(boottime/worktime*10000)/100);
						json.put("effectiveratio", (double)Math.round(weldtime/boottime*10000)/100);
						json.put("workratio", (double)Math.round(weldtime/worktime*10000)/100);
						ary.add(json);
					}
				}
			}else if(flag==1){//公司层
				for(int j=0;j<insf.size();j++){
					int count = wm.getCountByInsframework(insf.get(j).getId());
					double worktime = 0,boottime = 0,weldtime = 0,standbytime = 0;
					//上班时长
					for(int x=0;x<list.size();x++){
						if(insf.get(j).getId().equals(list.get(x).getCaustid())){
							worktime += list.get(x).getTotal();
						}
					}
					//开机时长,焊接时长，待机时长
					for(int x=0;x<time.size();x++){
						if(insf.get(j).getId().equals(time.get(x).getCaustid())){
							boottime += time.get(x).getWorktime();
							weldtime += time.get(x).getLoads();
							standbytime += time.get(x).getTime();
						}
					}
					if(list.isEmpty()){
						json.put("name",insf.get(j).getName());
						json.put("worktime", 0);//上班时长
						json.put("boottime", 0);//开机时长
						json.put("shutdowntime", days*24*count);//关机时长
						json.put("weldtime", 0);//焊接时长
						json.put("standbytime", 0);//待机时长
						json.put("sjratio", 0);//上机率
						json.put("effectiveratio", 0);//有效焊接率
						json.put("workratio", 0);//工作效率
						ary.add(json);
					}else{
						json.put("name",insf.get(j).getName());
						json.put("worktime", (double)Math.round(worktime*100)/100);
						json.put("boottime", (double)Math.round(boottime*100)/100);
						json.put("shutdowntime", (double)Math.round((days*24*count-boottime)*100)/100);
						json.put("weldtime", (double)Math.round(weldtime*100)/100);
						json.put("standbytime", (double)Math.round(standbytime*100)/100);
						json.put("sjratio", (double)Math.round(boottime/worktime*10000)/100);
						json.put("effectiveratio", (double)Math.round(weldtime/boottime*10000)/100);
						json.put("workratio", (double)Math.round(weldtime/worktime*10000)/100);
						ary.add(json);
					}
				}
			}else if(flag==2){
				for(int j=0;j<insf.size();j++){
					int count = wm.getCountByInsframework(insf.get(j).getId());
					double worktime = 0,boottime = 0,weldtime = 0,standbytime = 0;
					//上班时长
					for(int x=0;x<list.size();x++){
						if(insf.get(j).getId().equals(list.get(x).getItemid())){
							worktime += list.get(x).getTotal();
						}
					}
					//开机时长,焊接时长，待机时长
					for(int x=0;x<time.size();x++){
						if(insf.get(j).getId().equals(time.get(x).getItemid())){
							boottime += time.get(x).getWorktime();
							weldtime += time.get(x).getLoads();
							standbytime += time.get(x).getTime();
						}
					}
					if(list.isEmpty()){
						json.put("name",insf.get(j).getName());
						json.put("worktime", 0);//上班时长
						json.put("boottime", 0);//开机时长
						json.put("shutdowntime", days*24*count);//关机时长
						json.put("weldtime", 0);//焊接时长
						json.put("standbytime", 0);//待机时长
						json.put("sjratio", 0);//上机率
						json.put("effectiveratio", 0);//有效焊接率
						json.put("workratio", 0);//工作效率
						ary.add(json);
					}else{
						json.put("name",insf.get(j).getName());
						json.put("worktime", (double)Math.round(worktime*100)/100);
						json.put("boottime", (double)Math.round(boottime*100)/100);
						json.put("shutdowntime", (double)Math.round((days*24*count-boottime)*100)/100);
						json.put("weldtime", (double)Math.round(weldtime*100)/100);
						json.put("standbytime", (double)Math.round(standbytime*100)/100);
						json.put("sjratio", (double)Math.round(boottime/worktime*10000)/100);
						json.put("effectiveratio", (double)Math.round(weldtime/boottime*10000)/100);
						json.put("workratio", (double)Math.round(weldtime/worktime*10000)/100);
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

	/**
	 * 连续超时报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getNewOvertime")
	@ResponseBody
	public String getNewOvertime(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String type = request.getParameter("otype");
		int number = Integer.parseInt(request.getParameter("number"));
		int parentflag = Integer.parseInt(request.getParameter("parentflag"));
		String parentid = request.getParameter("parent"),insfstr = "";
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
		if(iutil.isNull(parentid)){
			dto.setParent(new BigInteger(parentid));
		}else{
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			dto.setParent(insm.getUserInsfId(uid));
		}
		if(parentflag==1){
			insfstr = insm.showParents(dto.getParent().toString());
		}
		List<ModelDto> time = null;
		if(iutil.isNull(request.getParameter("page")) && iutil.isNull(request.getParameter("rows"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
			page = new Page(pageIndex,pageSize,total);
			time = lm.getDurationTime(page, time1, time2, Integer.parseInt(type));
		}else{
			time = lm.getDurationTime(time1, time2, Integer.parseInt(type));
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
			int usertype = insm.getTypeById(dto.getParent()),types = usertype;
			String status = "itemid";
			if(usertype==20){
				types = 21;
				status = "fid";
			}else if(usertype==21){
				types = 22;
				status = "caustid";
			}else if(usertype==22){
				types = 23;
			}
			List<ModelDto> list = lm.getNewOvertime(dto, number, status);
			List<LiveData> ins = lm.getAllInsf(dto.getParent(), types);
			int[] num = null;
			for(ModelDto live :time){
				json.put("weldTime",live.getWeldTime());
				json.put("type",usertype);
				arys.add(json);
			}
			for(int i=0;i<ins.size();i++){
				num = new int[time.size()];
				for(int j=0;j<time.size();j++){
					num[j] = 0;
					for(ModelDto l:list){
						BigInteger id = l.getItemid();
						if(usertype==20){
							id = l.getFid();
						}else if(usertype==21){
							id = l.getCaustid();
						}
						if(ins.get(i).getId().equals(id) && time.get(j).getWeldTime().equals(l.getWeldTime())){
							num[j] = l.getTotal();
						}
					}
				}
				json.put("num",num);
				json.put("name",ins.get(i).getFname());
				json.put("itemid",ins.get(i).getId());
				json.put("type", usertype);
				json.put("insfstr", insfstr);
				arys1.add(json);
			}
			JSONObject object = new JSONObject();
			
			for(int i=0;i<time.size();i++){
				for(int j=0;j<arys1.size();j++){
					JSONObject js = (JSONObject)arys1.get(j);
					String overproof = js.getString("num").substring(1, js.getString("num").length()-1);
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
	 * 设备类型闲置率报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getNewIdle")
	@ResponseBody
	public String getNewIdle(HttpServletRequest request){
		if(iutil.isNull(request.getParameter("page"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
		}
		if(iutil.isNull(request.getParameter("rows"))){
			pageSize = Integer.parseInt(request.getParameter("rows"));
		}
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String type = request.getParameter("otype");
		String parentid = request.getParameter("parent");
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
			}else if(type.equals("5")){
				dto.setDay("day");
			}else if(type.equals("6")){
				dto.setWeek("week");
			}
		}
		if(iutil.isNull(parentid)){
			dto.setParent(new BigInteger(parentid));
		}else{
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			dto.setParent(insm.getUserInsfId(uid));
		}
		List<ModelDto> time = null;
		if(iutil.isNull(request.getParameter("page")) && iutil.isNull(request.getParameter("rows"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
			page = new Page(pageIndex,pageSize,total);
			time = lm.getDurationTime(page, time1, time2, Integer.parseInt(type));
		}else{
			time = lm.getDurationTime(time1, time2, Integer.parseInt(type));
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
			List<ModelDto> list = lm.getNewIdle(dto);
			List<ModelDto> ins = lm.getMachineTypeTotal(dto.getParent());
			double[] num = null;
			double[] bilv = null;
			for(ModelDto live :time){
				if(type.equals("6")){
					String[] str = live.getWeldTime().split("-");
					if(str[1].equals("1")){
						json.put("weldTime",str[0]+"-上半年");
					}else{
						json.put("weldTime",str[0]+"-下半年");
					}
				}else{
					json.put("weldTime",live.getWeldTime());
				}
				arys.add(json);
			}
			for(int i=0;i<ins.size();i++){
				num = new double[time.size()];
				bilv = new double[time.size()];
				int count = ins.get(i).getTotal();
				for(int j=0;j<time.size();j++){
					num[j] = count;
					if(count==0){
						bilv[j] = 0;
					}else{
						bilv[j] = (double)Math.round(num[j]*10000/count)/100;
					}
					for(int x=0;x<list.size();x++){
						if(list.get(x).getTypeid() == ins.get(i).getTypeid()){
							num[j] = count - list.get(x).getTotal();
							bilv[j] = (double)Math.round(num[j]*10000/count)/100;
						}
					}
				}
				json.put("bilv", bilv);
				json.put("idle",num);
				json.put("name",ins.get(i).getFname());
				json.put("id",ins.get(i).getTypeid());
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
				if(type.equals("6")){
					String[] str = time.get(i).getWeldTime().split("-");
					if(str[1].equals("1")){
						object.put("w",str[0]+"-上半年");
					}else{
						object.put("w",str[0]+"-下半年");
					}
				}else{
					object.put("w",time.get(i).getWeldTime());
				}
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
	 * 公司下拉框
	 * @return 
	 */
	@RequestMapping("getChildren")
	@ResponseBody
	public String getChildren(HttpServletRequest request){
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			int usertype = insm.getUserInsfType(uid);
			if(usertype==20){
				usertype = 21;
			}else if(usertype==21){
				usertype = 22;
			}else if(usertype==22){
				usertype = 23;
			}
			List<LiveData> list = lm.getAllInsf(insm.getUserInsfId(uid), usertype);
			for(LiveData i:list){
				json.put("id", i.getId());
				json.put("name", i.getFname());
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("ary", ary);
		return obj.toString();
	}
	
	/**
	 * 获取上级组织机构id
	 * @return 
	 */
	@RequestMapping("getParent")
	@ResponseBody
	public String getParent(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		String parentid = request.getParameter("parent");
		BigInteger parent = null;
		if(iutil.isNull(parentid)){
			parent = new BigInteger(parentid);
		}
		try{
			Insframework insf = insm.getParent(parent);
			obj.put("parent", insf.getId());
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj.toString();
	}
	
	/**
	 * 获取焊工焊接工作时间
	 * @param request
	 * @return
	 */
	@RequestMapping("getWelderWorkTime")
	@ResponseBody
	public String getWelderWorkTime(HttpServletRequest request) {
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String parentid = request.getParameter("parent");
		String time1 = request.getParameter("time1");
		String time2 = request.getParameter("time2");
		WeldDto dto = new WeldDto();
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentid)){
			dto.setParent(new BigInteger(parentid));
		}
		page = new Page(pageIndex,pageSize,total);
		int usertype = insm.getTypeById(dto.getParent());
		String insftype = "itemid";
		if(usertype==20){
			insftype = "";
		}else if(usertype==21){
			insftype = "fid";
		}else if(usertype==22){
			insftype = "caustid";
		}
		List<ModelDto> weldertime = lm.getWelderWorkTime(page, dto, insftype);
		long total = 0;
		if(weldertime != null){
			PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(weldertime);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		ModelDto avg = lm.getWelderAvgWorkTime(dto);
		try{
			for(int i=0;i<weldertime.size();i++){
				BigInteger id = weldertime.get(i).getItemid();
				if(usertype==21){
					id = weldertime.get(i).getFid();
				}else if(usertype==22){
					id = weldertime.get(i).getCaustid();
				}
				if(dto.getParent().equals(id) || usertype==20){
					json.put("name", weldertime.get(i).getFname());
					json.put("welderno", weldertime.get(i).getFwelder_id());
					json.put("worktime", (double)Math.round(weldertime.get(i).getWorktime()*100)/100);
					json.put("time", (double)Math.round(weldertime.get(i).getTime()*100)/100);
					ary.add(json);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(avg!=null){
			obj.put("avgWorktime", (double)Math.round(avg.getWorktime()*100)/100);
			obj.put("avgtime", (double)Math.round(avg.getTime()*100)/100);
		}else{
			obj.put("avgWorktime", 0);
			obj.put("avgtime", 0);
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}
}
