package com.greatway.controller;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.spring.model.MyUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/itemChart", produces = { "text/json;charset=UTF-8" })
public class ItemChartController {
	private Page page;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int total = 0;
	
	@Autowired
	private LiveDataManager lm;
	
	@Autowired
	private InsframeworkManager insm;
	
	@Autowired
	private WeldingMachineManager wm;
	
	IsnullUtil iutil = new IsnullUtil();
	
	/**
	 * 跳转项目工时页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goItemHour")
	public String goItemHour(HttpServletRequest request){
		String item = request.getParameter("item");
		lm.getUserId(request);
		insm.showParent(request, item);
		request.setAttribute("item", item);
		request.setAttribute("parentime1", request.getParameter("parentime1"));
		request.setAttribute("parentime2", request.getParameter("parentime2"));
		return "itemchart/itemhour";
	}
	
	/**
	 * 跳转超标明细页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goDetailoverproof")
	public String goDetailoverproof(HttpServletRequest request){
		String parent = request.getParameter("parent");
		String weldtime = request.getParameter("weldtime");
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		insm.showParent(request, parent);
		lm.getUserId(request);
		request.setAttribute("parent", parent);
		request.setAttribute("weldtime", weldtime);
		request.setAttribute("time1", time1);
		request.setAttribute("time2", time2);
		return "itemchart/detailoverproof";
	}
	
	/**
	 * 跳转项目部超标页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goItemoverproof")
	public String goItemoverproof(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		lm.getUserId(request);
		request.setAttribute("parent", parent);
		request.setAttribute("parentime1", request.getParameter("parentime1"));
		request.setAttribute("parentime2", request.getParameter("parentime2"));
		return "itemchart/itemoverproof";
	}
	
	/**
	 * 跳转项目部超时页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goItemOvertime")
	public String goItemOvertime(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		lm.getUserId(request);
		request.setAttribute("parent",parent);
		request.setAttribute("parentime1", request.getParameter("parentime1"));
		request.setAttribute("parentime2", request.getParameter("parentime2"));
		return "itemchart/itemovertime";
	}
	
	/**
	 * 跳转项目部负荷率页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goItemLoads")
	public String goItemLoads(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		lm.getUserId(request);
		request.setAttribute("parent",parent);
		request.setAttribute("parentime1", request.getParameter("parentime1"));
		request.setAttribute("parentime2", request.getParameter("parentime2"));
		return "itemchart/itemloads";
	}
	
	/**
	 * 跳转项目部空载率页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goItemNoLoads")
	public String goItemNoLoads(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		lm.getUserId(request);
		request.setAttribute("parent",parent);
		request.setAttribute("parentime1", request.getParameter("parentime1"));
		request.setAttribute("parentime2", request.getParameter("parentime2"));
		return "itemchart/itemnoloads";
	}
	
	/**
	 * 跳转项目部闲置页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goItemIdle")
	public String goItemIdle(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		lm.getUserId(request);
		request.setAttribute("parent",parent);
		request.setAttribute("parentime1", request.getParameter("parentime1"));
		request.setAttribute("parentime2", request.getParameter("parentime2"));
		return "itemchart/itemidle";
	}
	
	/**
	 * 跳转项目部单台设备运行数据统计
	 * @param request
	 * @return
	 */
	@RequestMapping("/goItemUse")
	public String goItemUse(HttpServletRequest request){
		lm.getUserId(request);
		return "itemchart/itemuse";
	}
	
	/**
	 * 跳转项目部工效页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goItemEfficiency")
	public String goCompanyEfficiency(HttpServletRequest request){
		String nextparent = request.getParameter("nextparent");
		insm.showParent(request, nextparent);
		lm.getUserId(request);
		request.setAttribute("nextparent", nextparent);
		request.setAttribute("parentime1", request.getParameter("parentime1"));
		request.setAttribute("parentime2", request.getParameter("parentime2"));
		return "itemchart/itemefficiency";
	}
	
	/**
	 * 查询项目工时明细
	 * @param request
	 * @return
	 */
	@RequestMapping("/getitemHour")
	@ResponseBody
	public String getitemHour(HttpServletRequest request){
		if(iutil.isNull(request.getParameter("page"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
		}
		if(iutil.isNull(request.getParameter("rows"))){
			pageSize = Integer.parseInt(request.getParameter("rows"));
		}
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String item = request.getParameter("item");
		String search = request.getParameter("search");
		WeldDto dto = new WeldDto();
		if(!iutil.isNull(item)){
			//处理用户数据权限
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			int type = insm.getUserInsfType(uid);
			if(type==21){
				dto.setCompanyid(insm.getUserInsfId(uid));
			}else if(type==22){
				dto.setParent(insm.getUserInsfId(uid));
			}else if(type==23){
				item = insm.getUserInsfId(uid).toString();
			}
		}
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
		if(iutil.isNull(item)){
			dto.setDtoItem(new BigInteger(item));
		}
		if(iutil.isNull(search)){
			dto.setSearch(search);
		}
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.getItemhour(page,dto);
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
				}else{
					json.put("jidgather", str.length);
				}
				json.put("manhour", l.getHous());
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
				json.put("nextexternaldiameter",l.getNextexternaldiameter());
				json.put("externalDiameter",l.getExternalDiameter());
				json.put("wallThickness",l.getWallThickness());
				json.put("material",l.getMaterial());
				json.put("itemid",l.getItemid());
				json.put("nextmaterial",l.getNextmaterial());
				json.put("nextwall_thickness",l.getNextwallThickness());
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
	 * 项目部负荷率报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getItemLoads")
	@ResponseBody
	public String getItemLoads(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		String item = request.getParameter("item");
		String type = request.getParameter("otype");
		WeldDto dto = new WeldDto();
		dto.setDtoStatus(1);
		BigInteger parent = null;
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}else{
			parent = new BigInteger(item);
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
		JSONObject object = new JSONObject();
		try{
			Insframework ins =  insm.getInsById(parent);;
			List<ModelDto> list = lm.getItemLoads(dto, parent);
			List<ModelDto> machine = lm.getCaustMachineCount(dto, parent);
			double[] num = new double[time.size()];
			for(int i=0;i<time.size();i++){
				if(list.size()>0){
					double[] load=new double[time.size()],summachine=new double[time.size()];
					num[i] = 0;
					for(ModelDto m:list){
						for(ModelDto ma:machine){
							if(ma.getWeldTime().equals(m.getWeldTime()) && ma.getFid().equals(m.getIid())){
								if(time.get(i).getWeldTime().equals(m.getWeldTime())){
									load[i] =m.getLoads();
									summachine[i] = ma.getLoads();
									num[i] =  (double)Math.round(m.getLoads()/ma.getLoads()*100*100)/100;
								}
							}
						}
						
					}
					json.put("loads",(double) Math.round(Double.valueOf(load[i])*1000)/1000+"/"+summachine[i]+"="+num[i]);
					json.put("itemid", list.get(0).getIid());
					json.put("weldTime",time.get(i).getWeldTime());
					ary.add(json);
				}else{
					json.put("loads",0);
					json.put("itemid", ins.getId());
					json.put("weldTime",time.get(i).getWeldTime());
					ary.add(json);
				}
			}
			if(list.size()<=0){
				object.put("name", ins.getName());
			}else{
				object.put("name",list.get(0).getFname());
			}
			object.put("num", num);
			arys.add(object);
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		obj.put("arys", arys);
		return obj.toString();
	}

	/**
	 * 项目部超标统计
	 * @param request
	 * @return
	 */
	@RequestMapping("/getItemOverproof")
	@ResponseBody
	public String getItemOverproof(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		String item = request.getParameter("item");
		String type = request.getParameter("otype");
		WeldDto dto = new WeldDto();
		BigInteger id = null;
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentId)){
			id = new BigInteger(parentId);
		}else{
			id = new BigInteger(item);
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
		JSONObject object = new JSONObject();
		try{
			Insframework ins = insm.getInsById(id);
			List<ModelDto> list = lm.getItemOverproof(dto, id);
			BigInteger[] num = new BigInteger[time.size()];
			for(int i=0;i<time.size();i++){
				num[i] = new BigInteger("0");
				if(list.size()>0){
					for(ModelDto m:list){
						if(time.get(i).getWeldTime().equals(m.getWeldTime())){
							num[i] = m.getOverproof();
						}
					}
					json.put("weldTime",time.get(i).getWeldTime());
					json.put("overproof",num[i]);
					json.put("itemid", list.get(0).getFid());
					ary.add(json);
				}else{
					json.put("weldTime",time.get(i).getWeldTime());
					json.put("overproof",num[i]);
					json.put("itemid", ins.getId());
					ary.add(json);
				}
			}
			if(list.size()<=0){
				object.put("name", ins.getName());
			}else{
				object.put("name",list.get(0).getFname());
			}
			object.put("num", num);
			arys.add(object);
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		obj.put("arys", arys);
		return obj.toString();
	}

	
	/**
	 * 查询超标明细
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDatailOverproof")
	@ResponseBody
	public String getDatailOverproof(HttpServletRequest request){
		if(iutil.isNull(request.getParameter("page"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
		}
		if(iutil.isNull(request.getParameter("rows"))){
			pageSize = Integer.parseInt(request.getParameter("rows"));
		}
		String parentid = request.getParameter("parent");
		String weldtime = request.getParameter("weldtime");
		String time1 = request.getParameter("time1");
		String time2 = request.getParameter("time2");
		WeldDto dto = new WeldDto();
		//处理用户数据权限
		String afreshLogin = (String)request.getAttribute("afreshLogin");
		if(iutil.isNull(afreshLogin)){
			return "0";
		}
		BigInteger parent = null;
		if(iutil.isNull(weldtime)){
			dto.setTime(weldtime+"%");
		}
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentid)){
			parent = new BigInteger(parentid);
		}
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.getDatailOverproof(page,dto,parent);
		long total = 0;
		if(list != null){
			PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(list);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			for(ModelDto c:list){
				json.put("overproof", c.getOverproof());
				json.put("weldtime", c.getWeldTime());
				json.put("iid", c.getIid());
				json.put("jid", c.getJid());
				json.put("fwelder_id", c.getFwelder_id());
				json.put("fmachine_id", c.getFmachine_id());
				json.put("fjunction_id", c.getFjunction_id());
				json.put("iname", c.getIname());
				json.put("wname", c.getWname());
				json.put("livecount", c.getLivecount());
				json.put("fmax_electricity", c.getFmax_electricity());
				json.put("fmin_electricity", c.getFmin_electricity());
				json.put("jidgather", c.getJidgather());
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getCountTime")
	@ResponseBody
	public String getCountTime(HttpServletRequest request,@RequestParam String welderno
			,@RequestParam String junctionno,@RequestParam String time,@RequestParam BigInteger id){
		JSONObject json = new JSONObject();
		try{
			String machineno = request.getParameter("machineno");
			int count = lm.getCountTime(welderno, machineno, junctionno, time, id);
			json.put("count", count);
		}catch(Exception e){
			e.printStackTrace();
		}
		return json.toString();
	}
	
	/**
	 * 项目部超时报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getItemOvertime")
	@ResponseBody
	public String getItemOvertime(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		String item = request.getParameter("item");
		String type = request.getParameter("otype");
		String number = request.getParameter("number");
		WeldDto dto = new WeldDto();
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentId)){
			dto.setParent(new BigInteger(parentId));
		}else{
			dto.setParent(new BigInteger(item));
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
		JSONObject object = new JSONObject();
		try{
			Insframework ins = insm.getInsById(dto.getParent());
			List<ModelDto> list = lm.getItemOvertime(dto, number);
			String[] num = new String[time.size()];
			for(int i=0;i<time.size();i++){
				num[i] = "0";
				if(list.size()>0){
					for(ModelDto m:list){
						if(time.get(i).getWeldTime().equals(m.getWeldTime())){
							num[i] = m.getOvertime();
						}
					}
					json.put("weldTime",time.get(i).getWeldTime());
					json.put("overtime",num[i]);
					json.put("id", list.get(0).getFid());
					ary.add(json);
				}else{
					json.put("weldTime",time.get(i).getWeldTime());
					json.put("overtime",num[i]);
					json.put("id", ins.getId());
					ary.add(json);
				}
			}
			if(list.size()<=0){
				object.put("name", ins.getName());
			}else{
				object.put("name",list.get(0).getFname());
			}
			object.put("num", num);
			arys.add(object);
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		obj.put("arys", arys);
		return obj.toString();
	}

	/**
	 * 项目部空载率报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getItemNoLoads")
	@ResponseBody
	public String getItemNoLoads(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		String item = request.getParameter("item");
		String type = request.getParameter("otype");
		WeldDto dto = new WeldDto();
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
		}else{
			parent = new BigInteger(item);
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
		JSONObject object = new JSONObject();
		try{
			Insframework ins = insm.getInsById(parent);
			List<ModelDto> list = lm.getItemNOLoads(dto, parent,null);
			List<ModelDto> machine = lm.getCaustMachineCount(dto, parent);
			double[] num = new double[time.size()];
			for(int i=0;i<time.size();i++){
				if(list.size()>0){
					double[] noload=new double[time.size()],summachine=new double[time.size()],livecount=new double[time.size()];
					num[i] = 0;
					for(ModelDto m:list){
						for(ModelDto ma:machine){
							if(ma.getWeldTime().equals(m.getWeldTime()) && ma.getFid().equals(m.getFid())){
								if(time.get(i).getWeldTime().equals(m.getWeldTime())){
									livecount[i] = lm.getCountByTime(m.getIid(), m.getWeldTime(),null).doubleValue();
									noload[i] = m.getLoads();
									summachine[i] = ma.getLoads();
									num[i] = (double)Math.round(noload[i]/livecount[i]/summachine[i]*100*100)/100;
								}
							}
						}
					}
					json.put("weldTime",time.get(i).getWeldTime());
					json.put("loads",(double) Math.round(Double.valueOf(noload[i])*1000)/1000+"/"+(double) Math.round(Double.valueOf(livecount[i])*1000)/1000+"/"+summachine[i]+"="+num[i]);
					json.put("itemid", list.get(0).getFid());
					ary.add(json);
				}else{
					json.put("weldTime",time.get(i).getWeldTime());
					json.put("loads", 0);
					json.put("itemid", ins.getId());
					ary.add(json);
				}
			}

			if(list.size()<=0){
				object.put("name", ins.getName());
			}else{
				object.put("name",list.get(0).getFname());
			}
			object.put("num", num);
			arys.add(object);
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		obj.put("arys", arys);
		return obj.toString();
	}

	/**
	 * 项目部闲置率
	 * @param request
	 * @return
	 */
	@RequestMapping("/getItemIdle")
	@ResponseBody
	public String getItemIdle(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		String item = request.getParameter("item");
		String type = request.getParameter("otype");
		WeldDto dto = new WeldDto();
		BigInteger parent = null;
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}else{
			parent = new BigInteger(item);
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
		JSONObject object = new JSONObject();
		try{
			List<ModelDto> list = lm.getItemIdle(dto, parent);
			List<LiveData> ins = lm.getAllInsf(parent,23);
			double[] num = new double[time.size()];
			if(list.size()>0){
				for(int i=0;i<time.size();i++){
					int count = lm.getMachineCount(ins.get(0).getFid());
					num[i] = count;
					for(ModelDto m:list){
						if(time.get(i).getWeldTime().equals(m.getWeldTime())){
							num[i] = count - m.getNum().doubleValue();
						}
					}
					json.put("weldTime",time.get(i).getWeldTime());
					json.put("num",num[i]);
					ary.add(json);
				}
				object.put("name", list.get(0).getFname());
				object.put("num", num);
				arys.add(object);
			}else{
				int count = lm.getMachineCount(ins.get(0).getFid());
				for(int i=0;i<time.size();i++){
					json.put("weldTime",time.get(i).getWeldTime());
					json.put("num",count);
					num[i] = count;
					ary.add(json);
				}
				object.put("name", ins.get(0).getFname());
				object.put("num", num);
				arys.add(object);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		obj.put("arys", arys);
		return obj.toString();
	}

	
	/**
	 * 项目部单台设备运行数据统计信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getItemUse")
	@ResponseBody
	public String getItemUse(HttpServletRequest request){
		if(iutil.isNull(request.getParameter("page"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
		}
		if(iutil.isNull(request.getParameter("rows"))){
			pageSize = Integer.parseInt(request.getParameter("rows"));
		}
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		WeldDto dto = new WeldDto();
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			//获取用户id
			Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyUser myuser = (MyUser)object;
			List<Insframework> insf = insm.getInsByUserid(new BigInteger(myuser.getId()+""));
			page = new Page(pageIndex,pageSize,total);
			for(Insframework ins:insf){
				List<ModelDto> list = lm.getItemUse(page, dto, ins.getId());
				long total = 0;
				if(list != null){
					PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(list);
					total = pageinfo.getTotal();
				}
				for(ModelDto l:list){
					double num = wm.getMachineCountByManu(l.getFid(),ins.getId()).doubleValue();
					double time = (double)Math.round(l.getTime()/num*100)/100;
					json.put("time", time);
					json.put("fname", l.getFname()+" - "+l.getType());
					json.put("type", l.getType());
					json.put("fid",l.getFid());
					json.put("num", num);
					ary.add(json);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}
	
	
	/**
	 * 项目部工效报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getItemEfficiency")
	@ResponseBody
	public String getItemEfficiency(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("nextparent");
		int min = -1,max = -1;
		if(iutil.isNull(request.getParameter("min"))){
			min = Integer.parseInt(request.getParameter("min"));
		}
		if(iutil.isNull(request.getParameter("max"))){
			max = Integer.parseInt(request.getParameter("max"));
		}
		WeldDto dto = new WeldDto();
		if(!iutil.isNull(parentId)){
			//处理用户数据权限
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			int types = insm.getUserInsfType(uid);
			if(types==21){
				parentId = insm.getUserInsfId(uid).toString();
			}else if(types==22){
				parentId = insm.getUserInsfId(uid).toString();
			}else if(types==23){
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
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.caustEfficiency(page, parent, dto, min, max);
		PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(list);
		long total = pageinfo.getTotal();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			for(ModelDto m : list){
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
	@RequestMapping("/getItemHousClassify")
	@ResponseBody
	public String getItemHousClassify(HttpServletRequest request){
		String parentId = request.getParameter("item");
		String searchStr = request.getParameter("searchStr");
		if(!iutil.isNull(parentId)){
			//处理用户数据权限
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			int types = insm.getUserInsfType(uid);
			if(types==21){
				parentId = insm.getUserInsfId(uid).toString();
			}else if(types==22){
				parentId = insm.getUserInsfId(uid).toString();
			}else if(types==23){
				parentId = insm.getUserInsfId(uid).toString();
			}
		}
		BigInteger parent = null;
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.getHousClassify(page, parent, searchStr);
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
	 * 获取工效图表信息
	 * @param request
	 * @param parent
	 * @return
	 */
	@RequestMapping("/getItemEfficiencyChart")
	@ResponseBody
	public String getItemEfficiencyChart(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			String time1 = request.getParameter("dtoTime1");
			String time2 = request.getParameter("dtoTime2");
			String nextparent = request.getParameter("nextparent");
			WeldDto dto = new WeldDto();
			BigInteger parent = null;
			if(!iutil.isNull(nextparent)){
				//处理用户数据权限
				BigInteger uid = lm.getUserId(request);
				String afreshLogin = (String)request.getAttribute("afreshLogin");
				if(iutil.isNull(afreshLogin)){
					return "0";
				}
				int types = insm.getUserInsfType(uid);
				if(types==21){
					nextparent = insm.getUserInsfId(uid).toString();
				}else if(types==22){
					nextparent = insm.getUserInsfId(uid).toString();
				}else if(types==23){
					nextparent = insm.getUserInsfId(uid).toString();
				}
			}
			if(iutil.isNull(time1)){
				dto.setDtoTime1(time1);
			}
			if(iutil.isNull(time2)){
				dto.setDtoTime2(time2);
			}
			if(iutil.isNull(nextparent)){
				parent = new BigInteger(nextparent);
			}
			List<ModelDto> list = lm.getEfficiencyChartNum(dto, parent);
			List<ModelDto> efficiency = null;
			String[] num1 = new String[10];
			double[] num2 = new double[10];
			int oldnum = 0,newnum = 0,maxnum = 0;
			for(ModelDto m:list){
				if(m!=null){
					if(m.getAvgnum()==0){
						m.setAvgnum(2);
						if(m.getMinnum()>0){
							num1[0] = m.getMinnum()-1+"-"+(m.getMinnum()+m.getAvgnum());//-1是为了避免最小数取整而导致查询时搜索不到
						}else{
							num1[0] = m.getMinnum()+"-"+(m.getMinnum()+m.getAvgnum());
						}
						for(int i=1;i<10;i++){
							oldnum = m.getMinnum()+m.getAvgnum()*i+1;
							newnum = m.getMinnum()+m.getAvgnum()*(i+1);
							num1[i] = oldnum+"-"+newnum;
						}
					}else{
						if(m.getMinnum()>0){
							num1[0] = m.getMinnum()-1+"-"+(m.getMinnum()+m.getAvgnum());
						}else{
							num1[0] = m.getMinnum()+"-"+(m.getMinnum()+m.getAvgnum());
						}
						for(int i=1;i<9;i++){
							oldnum = m.getMinnum()+m.getAvgnum()*i+1;
							newnum = m.getMinnum()+m.getAvgnum()*(i+1);
							num1[i] = oldnum+"-"+newnum;
						}
						maxnum = m.getMinnum()+m.getAvgnum()*10+10;
						num1[9] = newnum+1+"-"+maxnum;
					}
					efficiency = lm.getEfficiencyChart(dto, parent, m.getMinnum(), m.getAvgnum());
					for(ModelDto e:efficiency){
						double sum = e.getSum1()+e.getSum2()+e.getSum3()+e.getSum4()+e.getSum5()+e.getSum6()+e.getSum7()+e.getSum8()+e.getSum9()+e.getSum10();
						num2[0] = e.getSum1()/sum*100;num2[1] = e.getSum2()/sum*100;
						num2[2] = e.getSum3()/sum*100;num2[3] = e.getSum4()/sum*100;
						num2[4] = e.getSum5()/sum*100;num2[5] = e.getSum6()/sum*100;
						num2[6] = e.getSum7()/sum*100;num2[7] = e.getSum7()/sum*100;
						num2[8] = e.getSum9()/sum*100;num2[9] = e.getSum10()/sum*100;
					}
					for(int i=0;i<num2.length;i++){
						num2[i] = (double)Math.round(num2[i]*100)/100;
					}
					json.put("num1", num1);
					json.put("num2", num2);
					ary.add(json);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("ary", ary);
		return obj.toString();
	}
	

	@RequestMapping("/getAllItem")
	@ResponseBody
	public String getAllItem(HttpServletRequest request){
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		String parentid = request.getParameter("parent");
		BigInteger parent = null;
		if(iutil.isNull(parentid)){
			parent = insm.getParentById(new BigInteger(parentid));
		}else{
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
			}else if(type==22){
				parent = insm.getUserInsfId(uid);
			}else if(type==23){
				parent = insm.getUserInsfId(uid);
			}
		}
		try{
			List<Insframework> list = insm.getInsByType(23,parent);
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
}
