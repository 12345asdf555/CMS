package com.greatway.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greatway.dto.ModelDto;
import com.greatway.dto.WeldDto;
import com.greatway.manager.InsframeworkManager;
import com.greatway.manager.LiveDataManager;
import com.greatway.manager.WelcomeManager;
import com.greatway.model.Insframework;
import com.greatway.model.LiveData;
import com.greatway.model.Welcome;
import com.greatway.util.IsnullUtil;
import com.spring.model.MyUser;
import com.spring.model.User;
import com.spring.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/hierarchy", produces = { "text/json;charset=UTF-8" })
public class MainController {
	@Autowired
	private UserService user;
	
	@Autowired
	private WelcomeManager wm;
	
	@Autowired
	private LiveDataManager lm;
	
	@Autowired
	private InsframeworkManager im;
	
	
	IsnullUtil iutil = new IsnullUtil();
	
	/**
	 * 跳转index页面进行分层
	 * @return
	 */
	@RequestMapping("/goIndex")
	@ResponseBody
	public String goGather(HttpServletRequest request){
		String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
		request.setAttribute("hierarchy", hierarchy);
		JSONObject obj = new JSONObject();
		obj.put("hierarchy", hierarchy);
		return obj.toString();
	}
	
	@RequestMapping("/getUserInsframework")
	@ResponseBody
	public String getUserInsframework(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(object==null){
			obj.put("uname", "请登录");
			obj.put("insframework", "无");
			return obj.toString();
		}
		MyUser u = (MyUser)object;
		User list = user.getUserInsframework(new BigInteger(u.getId()+""));
		obj.put("id", u.getId());
		obj.put("uname", list.getUserName());
		obj.put("type", list.getType());
		obj.put("insframework", list.getInsname());
		return obj.toString();
	}
	
	@RequestMapping("/getHierarchy")
	@ResponseBody
	public String getHierarchy(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		JSONArray ary1 = new JSONArray();
		JSONArray ary2 = new JSONArray();
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		BigInteger uid = lm.getUserId(request);
		
		List<Insframework> insframework = im.getInsByUserid(uid);
		int types = insframework.get(0).getType();
		BigInteger insfid = insframework.get(0).getId();
		if(types==20){
			List<Insframework> company = im.getConmpany(null);
			for(Insframework i:company){
				json1.put("companyid", i.getId());
				json1.put("companyname", i.getName());
				ary1.add(json1);
				List<Insframework> caust = im.getCause(i.getId(), null);
				for(Insframework j:caust){
					json2.put("companyid", i.getId());
					json2.put("caustid", j.getId());
					json2.put("caustname", j.getName());
					ary2.add(json2);
				}
			}
		}else if(types==21){
			List<Insframework> caust = im.getCause(insfid, null);
			for(Insframework i:caust){
				json1.put("companyid", i.getId());
				json1.put("companyname", i.getName());
				ary1.add(json1);
				List<Insframework> item = im.getCause(i.getId(), null);
				for(Insframework j:item){
					json2.put("companyid", i.getId());
					json2.put("caustid", j.getId());
					json2.put("caustname", j.getName());
					ary2.add(json2);
				}
			}
		}else if(types==22){
			Insframework insf = im.getInsById(insfid);
			if(insf!=null){
				json1.put("companyid", insf.getId());
				json1.put("companyname", insf.getName());
				ary1.add(json1);
				List<Insframework> item = im.getCause(insf.getId(), null);
				for(Insframework j:item){
					json2.put("companyid", insf.getId());
					json2.put("caustid", j.getId());
					json2.put("caustname", j.getName());
					ary2.add(json2);
				}
			}
		}else if(types==23){
			Insframework insf = im.getInsById(insfid);
			if(insf!=null){
				json1.put("companyid", insf.getId());
				json1.put("companyname", insf.getName());
				ary1.add(json1);
				json2.put("companyid", insf.getId());
				json2.put("caustid", insf.getId());
				json2.put("caustname", insf.getName());
				ary2.add(json2);
			}
		}
		obj.put("ary1", ary1);
		obj.put("ary2", ary2);
		return obj.toString();
	}
	
	@RequestMapping("getWorkRank")
	@ResponseBody
	public String getWorkRank(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject json = new JSONObject();
		try{ 
			String parentid = request.getParameter("parent");
			BigInteger parent = null;
			if(iutil.isNull(parentid)){
				parent = new BigInteger(parentid);
			}
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<Welcome> list = wm.getWorkRank(parent, sdf.format(date));
			for(int i=0;i<list.size();i++){
				json.put("rownum", i+1);
				json.put("welderno", list.get(i).getWelderno());
				json.put("name", list.get(i).getName());
				json.put("item", list.get(i).getInsname());
				json.put("hour", (double)Math.round(list.get(i).getHour()*100)/100);
				ary.add(json);
			}
			obj.put("rows", ary);
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj.toString();
	}
	
	/**
	 * 跳转利用率报表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getUseRatio")
	@ResponseBody
	public String getUseRatio(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject json = new JSONObject();
		try{
			String parentid = request.getParameter("parent");
			BigInteger parent = null;
			if(iutil.isNull(parentid)){
				parent = new BigInteger(parentid);
			}
			List<Welcome> list = wm.getItemMachineCount(parent);
			for(Welcome i:list){
				json.put("itemname", i.getName());//班组
				json.put("machinenum", i.getTotal());//设备总数
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Welcome machine = wm.getWorkMachineCount(i.getId(), sdf.format(date));
				if(machine!=null){
					json.put("worknum", machine.getMachinenum());//工作设备数
					double useratio =(double)Math.round(Double.valueOf(machine.getMachinenum())/Double.valueOf(i.getTotal())*100*100)/100;
					json.put("useratio", useratio);//设备利用率
				}else{
					json.put("worknum", 0);//工作设备数
					json.put("useratio", 0);//设备利用率
				}
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("ary", ary);
		return obj.toString();
	}
	
	
	/**
	 * 符合率
	 * @param request
	 * @return
	 */
	@RequestMapping("/getLoadRate")
	@ResponseBody
	public String getLoadRate(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONArray timeary = new JSONArray();
		JSONObject json = new JSONObject();
		JSONObject timejson = new JSONObject();
		try{
			String parentid = request.getParameter("parent");
			String time1 = request.getParameter("time1");
			String time2 = request.getParameter("time2");
			WeldDto dto = new WeldDto();
			BigInteger parent = null;
			if(iutil.isNull(parentid)){
				parent = new BigInteger(parentid);
				dto.setParent(parent);
			}
			dto.setDtoTime1(time1.substring(0, 10));
			dto.setDtoTime2(time2.substring(0, 10));
			dto.setDay("day");
			List<Welcome> ilist = wm.getItemWeldTime(dto);
			List<Welcome> olist = wm.getItemOverProofTime(dto);
			List<ModelDto> time =  lm.getAllTimes(dto);
			List<LiveData> insf = lm.getAllInsf(parent, 23);
			List<Welcome> temp = ilist;
			for(int i=0;i<ilist.size();i++){
				double num = 100;
				temp.get(i).setInsname(ilist.get(i).getName());
				temp.get(i).setTime(ilist.get(i).getTime());
				for(int o=0;o<olist.size();o++){
					if(ilist.get(i).getId().equals(olist.get(o).getId()) && ilist.get(i).getTime().equals(olist.get(o).getTime())){
						num = (double)Math.round(((ilist.get(i).getHour()-olist.get(o).getHour())/ilist.get(i).getHour())*100*100)/100;
					}
				}
				temp.get(i).setHour(num);
			}
			for(ModelDto t:time){
				timejson.put("weldtime", t.getWeldTime());//日期
				timeary.add(timejson);
			}
			for(LiveData item:insf){
				json.put("itemname", item.getFname());//班组
				double[] num = new double[time.size()];
				for(int i=0;i<time.size();i++){
					num[i] = 0;
					for(Welcome t:temp){
						if(time.get(i).getWeldTime().equals(t.getTime()) && item.getFname().equals(t.getName())){
							num[i] = t.getHour();
						}
					}
				}
				json.put("hour", num);
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("time", timeary);
		obj.put("ary", ary);
		return obj.toString();
	}
}
