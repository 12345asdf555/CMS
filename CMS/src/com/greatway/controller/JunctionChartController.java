package com.greatway.controller;

import java.math.BigInteger;
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
import com.greatway.model.WeldedJunction;
import com.greatway.page.Page;
import com.greatway.util.IsnullUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/junctionChart", produces = { "text/json;charset=UTF-8" })
public class JunctionChartController {
	private Page page;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int total = 0;

	
	@Autowired
	private LiveDataManager lm;
	@Autowired
	private InsframeworkManager insm;
	
	IsnullUtil iutil = new IsnullUtil();
	
	/**
	 * 跳转焊机工时
	 * @param request
	 * @return
	 */
	@RequestMapping("/goJunctionHour")
	public String goJunctionHour(HttpServletRequest request){
		String material = request.getParameter("material");
		String externalDiameter = request.getParameter("externalDiameter");
		String wallThickness = request.getParameter("wallThickness");
		String nextexternaldiameter = request.getParameter("nextexternaldiameter");
		String itemid = request.getParameter("itemid");
		String nextmaterial = request.getParameter("nextmaterial");
		String nextwall_thickness = request.getParameter("nextwall_thickness");
		String time1 = request.getParameter("time1");
		String time2 = request.getParameter("time2");
		request.setAttribute("item", itemid);
		request.setAttribute("material", material);
		request.setAttribute("externalDiameter",externalDiameter );
		request.setAttribute("wallThickness", wallThickness);
		request.setAttribute("nextexternaldiameter",nextexternaldiameter );
		request.setAttribute("nextmaterial", nextmaterial);
		request.setAttribute("nextwall_thickness",nextwall_thickness );
		request.setAttribute("parentime1", time1);
		request.setAttribute("parentime2", time2);
		lm.getUserId(request);
		return "junctionchart/junctionhour";
	}
	
	/**
	 * 跳转焊口明细
	 * @param request
	 * @return
	 */
	@RequestMapping("/goJunctionDetail")
	public String goJunctionDetail(HttpServletRequest request,@RequestParam BigInteger id,@RequestParam BigInteger item){
		WeldedJunction w = lm.getWeldedJunctionById(id);
		request.setAttribute("w", w);
		request.setAttribute("item", item);
		return "junctionchart/junctionDetail";
	}
	
	/**
	 * 跳转焊机超时待机
	 * @param request
	 * @return
	 */
	@RequestMapping("/goJunctionOvertime")
	public String goJunctionOvertime(HttpServletRequest request){
		String weldtime = request.getParameter("weldtime");
		String parentId = request.getParameter("parent");
		String dtoTime1 = request.getParameter("dtoTime1");
		String dtoTime2 = request.getParameter("dtoTime2");
		String number = request.getParameter("number");
		request.setAttribute("weldtime", weldtime);
		request.setAttribute("parent", parentId);
		request.setAttribute("number",number );
		request.setAttribute("time1",dtoTime1 );
		request.setAttribute("time2",dtoTime2 );
		insm.showParent(request, parentId);
		lm.getUserId(request);
		return "junctionchart/junctionovertime";
	}
	
	/**
	 * 跳转工艺超标页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/gojunctionoverproof")
	public String gojunctionoverproof(HttpServletRequest request){
		request.setAttribute("welderno", request.getParameter("welderno"));
		request.setAttribute("machineno", request.getParameter("machineno"));
		request.setAttribute("junctionno", request.getParameter("junctionno"));
		request.setAttribute("time", request.getParameter("time"));
		String itemid = request.getParameter("itemid");
		request.setAttribute("itemid", itemid);
		insm.showParent(request,itemid);
		lm.getUserId(request);
		return "junctionchart/junctionoverproof";
	}
	
	/**
	 * 跳转负荷率明细页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goDetailLoads")
	public String goDetailLoads(HttpServletRequest request){
		String weldtime = request.getParameter("weldtime");
		String itemid = request.getParameter("itemid");
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String otype = request.getParameter("otype");
		insm.showParent(request,itemid);
		lm.getUserId(request);
		request.setAttribute("parent",itemid);
		request.setAttribute("weldtime",weldtime);
		request.setAttribute("time1",time1);
		request.setAttribute("time2",time2);
		request.setAttribute("otype",otype);
		return "junctionchart/detailloads";
	}
	
	/**
	 * 跳转空载率明细页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goDetailNoLoads")
	public String goDetailNoLoads(HttpServletRequest request){
		String weldtime = request.getParameter("weldtime");
		String itemid = request.getParameter("itemid");
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		insm.showParent(request,itemid);
		lm.getUserId(request);
		request.setAttribute("parent",itemid);
		request.setAttribute("weldtime",weldtime);
		request.setAttribute("time1",time1);
		request.setAttribute("time2",time2);
		return "junctionchart/detailnoloads";
	}
	
	/**
	 * 获取焊机工时报表信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getJunctionHour")
	@ResponseBody
	public String getJunctionHour(HttpServletRequest request){
		if(iutil.isNull(request.getParameter("page"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
		}
		if(iutil.isNull(request.getParameter("rows"))){
			pageSize = Integer.parseInt(request.getParameter("rows"));
		}
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String item = request.getParameter("item");
		String material = request.getParameter("material");
		String externalDiameter = request.getParameter("externalDiameter");
		String wallThickness = request.getParameter("wallThickness");
		String nextexternaldiameter = request.getParameter("nextexternaldiameter");
		String nextmaterial = request.getParameter("nextmaterial");
		String nextwallthickness = request.getParameter("nextwallthickness");
		String time3 = request.getParameter("time1");
		String time4 = request.getParameter("time2");
		WeldDto dto = new WeldDto();
		if(!iutil.isNull(item)){
			//处理用户数据权限
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			int type = insm.getUserInsfType(uid);
			if(type==22){
				dto.setParent(insm.getUserInsfId(uid));
			}else if(type==23){
				item = insm.getUserInsfId(uid).toString();
			}
		}
		if(iutil.isNull(time3)){
			dto.setDtoTime1(time3);
		}
		if(iutil.isNull(time4)){
			dto.setDtoTime2(time4);
		}
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if( iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(item)){
			dto.setDtoItem(new BigInteger(item));
		}
		if(iutil.isNull(material)){
			dto.setDtoMaterial(material);
		}
		if(iutil.isNull(externalDiameter)){
			dto.setDtoExternalDiameter(externalDiameter);
		}
		if(iutil.isNull(wallThickness)){
			dto.setDtoWallThickness(wallThickness);
		}
		if(iutil.isNull(nextexternaldiameter)){
			dto.setDtoNextExternalDiameter(nextexternaldiameter);
		}
		if(iutil.isNull(nextmaterial)){
			dto.setNextmaterial(nextmaterial);
		}
		if(iutil.isNull(nextwallthickness)){
			dto.setNextwallthickness(nextwallthickness);
		}
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.getJunctionHous(page,dto);
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
				json.put("manhour", l.getHous());
				json.put("dyne", l.getDyne());
				json.put("name",l.getFname());
				json.put("itemid",l.getFid());
				json.put("starttime",l.getStarttime());
				json.put("endtime",l.getEndtime());
				json.put("iname",l.getIname());
				json.put("welder",l.getFwelder_id());
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
	 * 获取焊机超时报表信息
	 * @param request
	 * @param welderno
	 * @param machineno
	 * @param junctionno
	 * @param time
	 * @return
	 */
	@RequestMapping("/getjunctionovertime")
	@ResponseBody
	public String getjunctionovertime(HttpServletRequest request,@RequestParam String parent,
			@RequestParam String weldtime,@RequestParam String number){
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String time1 = request.getParameter("time1");
		String time2 = request.getParameter("time2");
		WeldDto dto = new WeldDto();
		dto.setTime(weldtime+"%");
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.getDetailovertime(page,dto, number, parent);
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
				json.put("overtime", l.getOvertime());
				json.put("weldtime", weldtime);
				json.put("welderno",l.getFwelder_id());
				json.put("machineno",l.getFmachine_id());
				json.put("wname",l.getWname());
				json.put("worktime", (double)Math.round(l.getWorktime()*100)/100);
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
	 * 获取焊机超标报表信息
	 * @param request
	 * @param welderno
	 * @param machineno
	 * @param junctionno
	 * @param time
	 * @return
	 */
	@RequestMapping("/getjunctionoverproof")
	@ResponseBody
	public String getjunctionoverproof(HttpServletRequest request,@RequestParam String welderno,
			@RequestParam String machineno,@RequestParam String junctionno,@RequestParam String time,@RequestParam BigInteger itemid){
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONArray ary1 = new JSONArray();
		try{
			List<ModelDto> causeoverproof = lm.getjunctionoverproof(welderno, machineno, junctionno, time, itemid);
			int[] num = new int[causeoverproof.size()];
			for(int i=0;i<causeoverproof.size();i++){
				num[i] = Integer.parseInt(causeoverproof.get(i).getOverproof().toString());
				json.put("weldtime", causeoverproof.get(i).getWeldTime());
				ary1.add(json);
			}
			json.put("overflage", num);
			json.put("junctionno", causeoverproof.get(0).getFjunction_id());
			ary.add(json);
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("ary1", ary1);
		obj.put("ary", ary);
		return obj.toString();
	}
	
	/**
	 * 获取焊机负荷率明细
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDetailLoads")
	@ResponseBody
	public String getDetailLoads(HttpServletRequest request){
		if(iutil.isNull(request.getParameter("page"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
		}
		if(iutil.isNull(request.getParameter("rows"))){
			pageSize = Integer.parseInt(request.getParameter("rows"));
		}
		String parent = request.getParameter("parent");
		String weldtime = request.getParameter("weldtime");
		String time1 = request.getParameter("time1");
		String time2 = request.getParameter("time2");
		String type = request.getParameter("otype");
		WeldDto dto = new WeldDto();
		dto.setDtoStatus(1);
		if(iutil.isNull(weldtime)){
			dto.setTime(weldtime+"%");
		}
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parent)){
			dto.setParent(new BigInteger(parent));
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
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.getDetailLoads(page, dto, null);
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
				double loads = (double)Math.round(l.getLoads()*100*100)/100;
				json.put("loads", ((double)Math.round(l.getLoads()*1000)/1000)+"/1="+loads+"%");
				json.put("weldtime", weldtime);
				json.put("name",l.getFname());
				json.put("itemid",l.getFid());
				json.put("machineno", l.getFmachine_id());
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
	 * 获取焊机空载率明细
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDetailNoLoads")
	@ResponseBody
	public String getDetailNoLoads(HttpServletRequest request){
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
		dto.setDtoStatus(1);
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
			dto.setParent(new BigInteger(parentid));;
		}
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.getDetailNoLoads(page,dto);
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
				BigInteger livecount = lm.getCountByTime(l.getFid(), weldtime,l.getJid());
				double loads = (double)Math.round(l.getLoads()/livecount.doubleValue()*100*100)/100;
				json.put("loads",  ((double)Math.round(l.getLoads()*1000)/1000)+"/"+((double)Math.round(livecount.doubleValue()*1000)/1000)+"/1="+loads+"%");
				json.put("weldtime", weldtime);
				json.put("name",l.getFname());
				json.put("itemid",l.getFid());
				json.put("machineno", l.getFmachine_id());
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}
	
}
