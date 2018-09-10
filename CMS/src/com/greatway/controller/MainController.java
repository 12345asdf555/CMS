package com.greatway.controller;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.greatway.dto.WeldDto;
import com.greatway.manager.InsframeworkManager;
import com.greatway.manager.LiveDataManager;
import com.greatway.manager.WelcomeManager;
import com.greatway.model.Insframework;
import com.greatway.model.Welcome;
import com.greatway.page.Page;
import com.greatway.util.IsnullUtil;
import com.spring.model.MyUser;
import com.spring.model.User;
import com.spring.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/hierarchy", produces = { "text/json;charset=UTF-8" })
public class MainController {private Page page;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int total = 0;
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
	 * 区分pc端与Android
	 * @return
	 */
	@RequestMapping("/goBranch")
	public String goBranch(HttpServletRequest request){
		String flag = request.getSession().getAttribute("AndroidFlag").toString();
		if(flag.equals("PC")){
			return "redirect:/index.jsp";
		}else{
			return "redirect:/indexs.jsp";
		}
	}
			
	/**
	 * 区分pc端与Android
	 * @return
	 */
	@RequestMapping("/goError")
	public String goError(HttpServletRequest request){
		String flag = request.getSession().getAttribute("AndroidFlag").toString();
		if(flag.equals("PC")){
			return "redirect:/login.jsp?login_error=1";
		}else{
			return "redirect:/logins.jsp?login_error=1";
		}
	}
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
	
	/**
	 * 跳转焊工工作量排行
	 * @return
	 */
	@RequestMapping("/goWorkRank")
	public String goWorkRank(HttpServletRequest request){
		return "welcome/workrank";
	}
	
	/**
	 * 跳转焊接规范负荷率
	 * @return
	 */
	@RequestMapping("/goLoadrate")
	public String goLoadrate(HttpServletRequest request){
		return "welcome/loadrate";
	}
	
	/**
	 * 跳转班组设备利用率
	 * @return
	 */
	@RequestMapping("/goUseratio")
	public String goUseratio(HttpServletRequest request){
		return "welcome/useratio";
	}
	
	@RequestMapping("/getUserInsframework")
	@ResponseBody
	public String getUserInsframework(HttpServletRequest request){
		JSONObject json = new JSONObject();
		try{
			//获取用户id
			Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(obj==null){
				request.setAttribute("afreshLogin", "您的Session已过期，请重新登录！");
				return null;
			}
			MyUser myuser = (MyUser)obj;
			User list = user.getUserInsframework(new BigInteger(myuser.getId()+""));
			json.put("id", myuser.getId());
			json.put("uname", list.getUserName());
			json.put("type", list.getType());
			json.put("insframework", list.getInsname());
		}catch(Exception e){
			json.put("afreshLogin", "您的Session已过期，请重新登录！");
		}
		return json.toString();
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
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		page = new Page(pageIndex,pageSize,total);
		JSONObject obj = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject json = new JSONObject();
		long total = 0;
		try{
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(request.getParameter("time1"));
			dto.setDtoTime2(request.getParameter("time2"));
			BigInteger uid = lm.getUserId(request);
			dto.setParent(im.getUserInsfId(uid));
			List<Welcome> list = wm.getWorkRank(page,dto);
			if(list != null){
				PageInfo<Welcome> pageinfo = new PageInfo<Welcome>(list);
				total = pageinfo.getTotal();
			}
			for(int i=0;i<list.size();i++){
				json.put("rownum", i+1);
				json.put("id", list.get(i).getId());
				json.put("welderno", list.get(i).getWelderno());
				json.put("name", list.get(i).getName());
				json.put("item", list.get(i).getInsname());
				json.put("hour", (double)Math.round(list.get(i).getHour()*100)/100);
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
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(request.getParameter("time1"));
			dto.setDtoTime2(request.getParameter("time2"));
			BigInteger uid = lm.getUserId(request);
			BigInteger parent = im.getUserInsfId(uid);
			dto.setParent(parent);
			int type = im.getTypeById(parent);
			List<Welcome> list = wm.getItemMachineCount(dto);
			List<Insframework> insf = im.getCause(parent, null);
			Insframework item = null;
			if(type==23){
				item = im.getInsById(parent);
				for(int j=0;j<list.size();j++){
					if(list.get(j).getId().equals(parent)){
						total += list.get(j).getTotal();
					}
				}
				double useratio = 0;
				Welcome machine = wm.getWorkMachineCount(item.getId(), dto);
				if(total!=0){
					useratio = (double)Math.round((double)machine.getMachinenum()/(double)total*100*100)/100;
				}
				json.put("itemname", item.getName());//班组
				json.put("machinenum", total);//设备总数
				json.put("worknum", machine.getMachinenum());//工作设备数
				json.put("useratio", useratio);//设备利用率
				ary.add(json);
			}else{
				for(int i=0;i<insf.size();i++){
					int total = 0;
					for(int j=0;j<list.size();j++){
						BigInteger insfid = null;
						if(type==20){
							insfid = list.get(j).getInsfid();
						}else if(type==21){
							insfid = list.get(j).getInsid();
						}else if(type==22){
							insfid = list.get(j).getId();
						}
						if(insf.get(i).getId().equals(insfid)){
							total += list.get(j).getTotal();
						}
					}
					double useratio = 0;
					Welcome machine = wm.getWorkMachineCount(insf.get(i).getId(), dto);
					if(total!=0){
						useratio = (double)Math.round((double)machine.getMachinenum()/(double)total*100*100)/100;
					}
					json.put("itemname", insf.get(i).getName());//班组
					json.put("machinenum", total);//设备总数
					json.put("worknum", machine.getMachinenum());//工作设备数
					json.put("useratio", useratio);//设备利用率
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
	 * 符合率
	 * @param request
	 * @return
	 */
	@RequestMapping("/getLoadRate")
	@ResponseBody
	public String getLoadRate(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject json = new JSONObject();
		try{
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(request.getParameter("time1"));
			dto.setDtoTime2(request.getParameter("time2"));
			BigInteger uid = lm.getUserId(request);
			BigInteger parent = im.getUserInsfId(uid);
			dto.setParent(parent);
			dto.setDay("day");
			int type = im.getTypeById(parent);
			List<Insframework> insf = im.getCause(parent, null);
			List<Welcome> ilist = wm.getItemWeldTime(dto);
			List<Welcome> olist = wm.getItemOverProofTime(dto);
			Insframework item = null;
			if(type==23){
				item = im.getInsById(parent);
				double itotal = 0,ototal = 0;
				for(int j=0;j<ilist.size();j++){
					if(ilist.get(j).getId().equals(parent)){
						itotal += ilist.get(j).getHour();
					}
				}
				for(int o=0;o<olist.size();o++){
					if(olist.get(o).getId().equals(parent)){
						ototal += olist.get(o).getHour();
					}
				}
				json.put("itemname", item.getName());//班组
				json.put("loadtime", (double)Math.round(itotal*100)/100);//焊接时长
				json.put("normaltime", (double)Math.round((itotal - ototal)*100)/100);//正常焊接时长
				double ratio = 0;
				if(itotal != 0){
					ratio = (double)Math.round((itotal - ototal)/itotal*10000)/100;
				}
				json.put("weldtime", ratio);//符合率
				ary.add(json);
			}else{
				for(int i=0;i<insf.size();i++){
					double itotal = 0,ototal = 0;
					for(int j=0;j<ilist.size();j++){
						BigInteger insfid = null;
						if(type==20){
							insfid = ilist.get(j).getInsfid();
						}else if(type==21){
							insfid = ilist.get(j).getInsid();
						}else if(type==22 || type==23){
							insfid = ilist.get(j).getId();
						}
						if(insf.get(i).getId().equals(insfid)){
							itotal += ilist.get(j).getHour();
						}
					}
					for(int o=0;o<olist.size();o++){
						BigInteger oinsfid = null;
						if(type==20){
							oinsfid = olist.get(o).getInsfid();
						}else if(type==21){
							oinsfid = olist.get(o).getInsid();
						}else if(type==22 || type==23){
							oinsfid = olist.get(o).getId();
						}
						if(insf.get(i).getId().equals(oinsfid)){
							ototal += olist.get(o).getHour();
						}
					}
					json.put("itemname", insf.get(i).getName());//班组
					json.put("loadtime", (double)Math.round(itotal*100)/100);//焊接时长
					json.put("normaltime", (double)Math.round((itotal - ototal)*100)/100);//正常焊接时长
					double ratio = 0;
					if(itotal != 0){
						ratio = (double)Math.round((itotal - ototal)/itotal*10000)/100;
					}
					json.put("weldtime", ratio);//符合率
					ary.add(json);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("ary", ary);
		return obj.toString();
	}
}
