package com.spring.controller;

import java.math.BigInteger;
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
import com.greatway.manager.WeldingMachineManager;
import com.greatway.model.Insframework;
import com.spring.model.MyUser;
import com.spring.model.Td;
import com.spring.service.TdService;
import com.greatway.util.IsnullUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/td",produces = { "text/json;charset=UTF-8" })
public class TdController {
	
	@Autowired
	private TdService tdService;
	@Autowired
	private InsframeworkManager insfService;
	private Td td;
	
	@Autowired
	private InsframeworkManager im;
	
	@Autowired
	private LiveDataManager lm;
	
	IsnullUtil iutil = new IsnullUtil();
	
	/**
	 * 鑾峰彇鎵�鏈夌敤鎴峰垪琛�
	 * @param request
	 * @return
	 */
	@RequestMapping("/AllTdbf")
	@ResponseBody
	public String Alltdbf(HttpServletRequest request){
		String websocket = request.getSession().getServletContext().getInitParameter("websocket");
//		request.setAttribute("web_socket", websocket);
		JSONObject obj = new JSONObject();
		obj.put("web_socket", websocket);
		return obj.toString();
	}
	
	@RequestMapping("/AllTd")
	public String Alltd(HttpServletRequest request){
/*		MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
			    .getAuthentication()  
			    .getPrincipal();
		long uid = myuser.getId();
		int dic=tdService.findDic(uid);*/
		lm.getUserId(request);
		return "td/BackUp";
/*		if(dic==21){
		String insname = tdService.findInsname(tdService.findIns(uid));
		request.setAttribute("insname", insname);
		return "td/BackUp";
		}else{
		return "/Error";
		}*/
	}
	
	@RequestMapping("/AllTdd")
	public String AllTdd(HttpServletRequest request){
		request.setAttribute("divi", request.getParameter("value"));
		return "td/BackUp";
	}
	
	@RequestMapping("/AllTddp")
	public String AllTddp(HttpServletRequest request){
		BigInteger insfid = new BigInteger(request.getParameter("value"));
		String fname = insfService.getInsframeworkById(insfid);
		request.setAttribute("proj", insfid);
		request.setAttribute("fname", fname);
		return "td/BackUp";
	}
	
	@RequestMapping("/AllTddi")
	public String AllTddi(HttpServletRequest request){
		MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
			    .getAuthentication()  
			    .getPrincipal();
		long uid = myuser.getId();
		int dic=tdService.findDic(uid);
/*		if(dic==22){*/
		String insname = tdService.findInsname(tdService.findIns(uid));
		request.setAttribute("divi", insname);
		return "td/BackUp";
/*		}else{
			return "/Error";
			}*/
	}
	
	@RequestMapping("/AllTdp")
	public String AllTdp(HttpServletRequest request){
		MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
			    .getAuthentication()  
			    .getPrincipal();
		long uid = myuser.getId();
		int dic=tdService.findDic(uid);
/*		if(dic==23){*/
		String insname = tdService.findInsname(tdService.findIns(uid));
		request.setAttribute("proj", insname);
		return "td/BackUp";
/*		}else{
			return "/Error";
		}*/
	}
	
	@RequestMapping("/AllTdad")
	@ResponseBody
	public String AllTdad(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		String eq = request.getParameter("e");
		String an = request.getParameter("a");
		String vo = request.getParameter("v");
		String value = request.getParameter("value");
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			String[] equ = eq.split(",");
			String[] anp = an.split(",");
			String[] vol = vo.split(",");
			System.out.println(equ);
			for(int i = 0;i < equ.length;i++)
			{
				if(value.equals(equ[i])){
				json.put("voltage",vol[i]);
				json.put("electricity",anp[i]);
				ary.add(json);
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
		
	}
	
	@RequestMapping("/AllTda")
	public String AllTda(HttpServletRequest request){
		request.setAttribute("av", request.getParameter("value"));
		return "td/AV";
	}
	
	@RequestMapping("/getAllTd")
	@ResponseBody
	public String getAllTd(HttpServletRequest request){
		
		JSONObject obj = new JSONObject();
		String da = request.getParameter("data");
/*		System.out.println(da);*/
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(int i = 0;i < da.length();i+=53)
			{
				json.put("fstatus_id", da.substring(0+i, 2+i));
				json.put("finsframework_id", da.substring(2+i, 4+i));
				json.put("fequipment_no", da.substring(4+i, 8+i));
				json.put("fwelder_no", da.substring(8+i, 12+i));
				json.put("voltage", da.substring(12+i, 16+i));
				json.put("electricity", da.substring(16+i, 20+i));
				json.put("time", da.substring(20+i, 41+i));
				json.put("maxele", da.substring(41+i, 44+i));
				json.put("minele", da.substring(44+i, 47+i));
				json.put("maxvol", da.substring(47+i, 50+i));
				json.put("minvol", da.substring(50+i, 53+i));
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getAllTddiv")
	@ResponseBody
	public String getAllTddiv(HttpServletRequest request){
		
		MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
			    .getAuthentication()  
			    .getPrincipal();
		JSONObject obj = new JSONObject();
		long uid = myuser.getId();
		String insname = tdService.findInsname(uid);
		List<Td> findAlld = tdService.findAlldiv(tdService.findIns(uid));
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(Td td:findAlld)
			{
				json.put("fid", td.getFdi());
				json.put("fname", td.getFdn());
				json.put("fparent", td.getFdp());
				json.put("ftype", td.getFdt());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getAllTdp")
	@ResponseBody
	public String getAllTdp(HttpServletRequest request){
		
		JSONObject obj = new JSONObject();
		String da = request.getParameter("data");
		System.out.println(da);
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(int i = 0;i < da.length();i+=53)
			{
				json.put("fstatus_id", da.substring(0+i, 2+i));
				json.put("finsframework_id", da.substring(2+i, 4+i));
				json.put("fequipment_no", da.substring(4+i, 8+i));
				json.put("fwelder_no", da.substring(8+i, 12+i));
				String weldname = tdService.findweld(da.substring(8+i, 12+i));
				json.put("fname", weldname);
				json.put("voltage", da.substring(12+i, 16+i));
				json.put("electricity", da.substring(16+i, 20+i));
				json.put("time", da.substring(20+i, 41+i));
				json.put("maxele", da.substring(41+i, 44+i));
				json.put("minele", da.substring(44+i, 47+i));
				json.put("maxvol", da.substring(47+i, 50+i));
				json.put("minvol", da.substring(50+i, 53+i));
				String position = tdService.findPosition(da.substring(4+i,8+i));
				json.put("fposition", position);
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getAllTdp1")
	@ResponseBody
	public String getAllTdp1(HttpServletRequest request){
		
		JSONObject obj = new JSONObject();
		long uid = Long.parseLong(request.getParameter("ins"));
		List<Td> findAllpro = tdService.findAlldiv(uid);
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(Td td:findAllpro){
				json.put("fid", td.getFdi());
				json.put("fname", td.getFdn());
				json.put("fparent", td.getFdp());
				json.put("ftype", td.getFdt());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getAllTdp2")
	@ResponseBody
	public String getAllTdp2(HttpServletRequest request){
		
		JSONObject obj = new JSONObject();
		int insid = Integer.parseInt(request.getParameter("div"));
//		long insid = tdService.findInsid(insname);
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			
				json.put("fid", insid);
				ary.add(json);
			
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getAllTdd")
	@ResponseBody
	public String getAllTdd(HttpServletRequest request){
		
		JSONObject obj = new JSONObject();
		long insid = Long.parseLong(request.getParameter("div"));
//		long insid = tdService.findInsid(insname);
		List<Td> findAlld = tdService.findAlldiv(insid);
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(Td td:findAlld)
			{
				json.put("fid", td.getFdi());
				json.put("fname", td.getFdn());
				json.put("fparent", td.getFdp());
				json.put("ftype", td.getFdt());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getAllTdd1")
	@ResponseBody
	public String getAllTdd1(HttpServletRequest request){
		
		JSONObject obj = new JSONObject();
		List<Td> findAllcom = tdService.findAllcom();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(Td td:findAllcom){
/*				json.put("fpname",td.getFinsp());
				json.put("fdname",td.getFinsd());
				json.put("fcname",td.getFinsc());*/
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getAllTdd2")
	@ResponseBody
	public String getAllTdd2(HttpServletRequest request){
		
		JSONObject obj = new JSONObject();
		List<Td> findAllcom = tdService.findAllcom();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(Td td:findAllcom){
/*				json.put("fpname",td.getFinsp());
				json.put("fdname",td.getFinsd());
				json.put("fcname",td.getFinsc());*/
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getWeld")
	@ResponseBody
	public String getWeld(HttpServletRequest request){
		
		String weldid = request.getParameter("weldid");
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
				json.put("fweldname",tdService.findweld(weldid));
				ary.add(json);
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getPosition")
	@ResponseBody
	public String getPosition(HttpServletRequest request){
		
		String equip = request.getParameter("equip");
		String eee = tdService.findPosition(equip);
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
				json.put("fpositin",eee);
				ary.add(json);
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getAllPosition")
	@ResponseBody
	public String getAllPosition(HttpServletRequest request){
		String parentId = request.getParameter("parent");
		BigInteger parent = null;
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
			List<Td> getAP = tdService.getAllPosition(parent);
			try{
				for(Td td:getAP){
					json.put("fid",td.getId());
					json.put("fequipment_no", td.getFequipment_no());
					json.put("fposition", td.getFposition());
					json.put("finsid", td.getFci());
					json.put("finsname", td.getFcn());
					ary.add(json);
				}
			}catch(Exception e){
				e.getMessage();
			}
		}else{
			MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
				    .getAuthentication()  
				    .getPrincipal();
			long uid = myuser.getId();
			if(im.getInsByUserid(BigInteger.valueOf(uid)).get(0).getType()==20){
				List<Td> getAP = tdService.getAllPosition(parent);
				try{
					for(Td td:getAP){
						json.put("fid",td.getId());
						json.put("fequipment_no", td.getFequipment_no());
						json.put("fposition", td.getFposition());
						json.put("finsid", td.getFci());
						json.put("finsname", td.getFcn());
						ary.add(json);
					}
				}catch(Exception e){
					e.getMessage();
				}
			}else{
				List<Insframework> in = im.getInsIdByParent(im.getInsByUserid(BigInteger.valueOf(uid)).get(0).getId(),24);
				List<Td> getAP = tdService.getAllPosition(parent);
				try{
					for(Td td:getAP){
						for(Insframework ins:in){
							if(td.getFci()==Integer.valueOf(ins.getId().toString())){
								json.put("fid",td.getId());
								json.put("fequipment_no", td.getFequipment_no());
								json.put("fposition", td.getFposition());
								json.put("finsid", td.getFci());
								json.put("finsname", td.getFcn());
								ary.add(json);
							}
						}
					}
				}catch(Exception e){
					e.getMessage();
				}
			}
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/isnull")
	@ResponseBody
	public String isnull(HttpServletRequest request){
		
		JSONObject obj = new JSONObject();
		String da = request.getParameter("dd");
		String po = request.getParameter("posit");
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(int i = 0;i < da.length();i+=159)
			{
				String pos = tdService.findPosition(da.substring(4+i, 8+i));
				if(pos.equals(po)){
				json.put("fstatus_id", da.substring(0+i, 2+i));
				json.put("fequipment_no", da.substring(4+i, 8+i));
				json.put("fwelder_no", da.substring(8+i, 12+i));
				String weldname = tdService.findweld(da.substring(8+i, 12+i));
				json.put("fname", weldname);
				json.put("fposition", pos);
				ary.add(json);
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/geInsname")
	@ResponseBody
	public String geInsname(HttpServletRequest request){
		
		int iid =  Integer.parseInt(request.getParameter("iid"));
		String insname = tdService.findInsname(iid);;
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
				json.put("fid", insname);
				ary.add(json);
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/allWeldname")
	@ResponseBody
	public String allWeldname(HttpServletRequest request){
		
		List<Td> fwn = tdService.allWeldname();	
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(Td td:fwn){
				json.put("fname",td.getFname());
				json.put("fwelder_no", td.getFwelder_no());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/standbytimeout")
	@ResponseBody
	public String standbytimeout(HttpServletRequest request){
		
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
		List<ModelDto> md = lm.getStandbytimeout(dto);
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(ModelDto sta:md){
				json.put("fname",sta.getFname());
				json.put("ftime", sta.getHous());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
}