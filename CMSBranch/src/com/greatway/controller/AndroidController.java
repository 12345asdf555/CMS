package com.greatway.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greatway.dto.ModelDto;
import com.greatway.dto.WeldDto;
import com.greatway.manager.InsframeworkManager;
import com.greatway.manager.LiveDataManager;
import com.greatway.model.Insframework;
import com.greatway.util.IsnullUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/android", produces = { "text/json;charset=UTF-8" })
public class AndroidController {
	
	@Autowired
	private LiveDataManager lm;
	
	@Autowired
	private InsframeworkManager insm;
	
	IsnullUtil iutil = new IsnullUtil();
	
	/**
	 * 跳转设备利用率页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goUseratio")
	public String goUseratio(HttpServletRequest request){
		lm.getUserId(request);
		return "android/useratio";
	}
	
	/**
	 * 跳转焊机最高排行页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goMachineMax")
	public String goMachineMax(HttpServletRequest request){
		lm.getUserId(request);
		return "android/machinemax";
	}
	
	/**
	 * 跳转焊机最低排行页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goMachineMin")
	public String goMachineMin(HttpServletRequest request){
		lm.getUserId(request);
		return "android/machinemin";
	}
	
	/**
	 * 跳转焊工最高排行页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goWelderMax")
	public String goWelderMax(HttpServletRequest request){
		lm.getUserId(request);
		return "android/weldermax";
	}
	
	/**
	 * 跳转焊工最高排行页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goWelderMin")
	public String goWelderMin(HttpServletRequest request){
		lm.getUserId(request);
		return "android/weldermin";
	}
	
	@RequestMapping("/goMachinecurve")
	public String goNextcurve(HttpServletRequest request){
	    String value = request.getParameter("value");
	    String valuename = request.getParameter("valuename");
	    String status = request.getParameter("status");
	    request.setAttribute("value", value);
	    request.setAttribute("valuename", valuename);
	    request.setAttribute("status", status);
	    return "android/machineCurve";
	}
	
	@RequestMapping("/gomachineAllTd")
	public String newAllTd(HttpServletRequest request){
		lm.getUserId(request);
		return "android/machineBackUp";
	}
	
	@RequestMapping("/goLivedata")
	public String goLivedata(HttpServletRequest request){
		try {
			lm.getUserId(request);
			String ary = request.getParameter("ary");
			if(iutil.isNull(ary) && !("[]").equals(ary)){
				request.setAttribute("ary", URLEncoder.encode(ary,"utf-8"));
			}
		    request.setAttribute("status", request.getParameter("status"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "android/livedata";
	}
	
	/**
	 * 跳转焊口列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/goJunctionAllTd")
	public String goJunctionAllTd(HttpServletRequest request){
		lm.getUserId(request);
		return "android/junction";
	}
	
	/**
	 * 跳转焊口实时
	 * @param request
	 * @return
	 */
	@RequestMapping("/goJunctioncurve")
	public String goJunctioncurve(HttpServletRequest request){
	    request.setAttribute("no", request.getParameter("no"));
	    request.setAttribute("itemid", request.getParameter("itemid"));
	    return "android/junctionCurve";
	}
	
	/**
	 * 获取焊机最高/低排行
	 * @param request
	 * @return
	 */
	@RequestMapping("getMachineList")
	@ResponseBody
	public String getMachineList(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		int status = Integer.parseInt(request.getParameter("status"));

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
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentId)){
			dto.setParent(new BigInteger(parentId));
		}
		dto.setDtoStatus(status);
		JSONObject obj = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject json = new JSONObject();
		try{
			List<ModelDto> list = lm.getWeldingmachineList(dto);
			for(ModelDto m:list){
				json.put("num", m.getLoads());
				json.put("equipment", m.getFname());
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("rows", ary);
		return obj.toString();
	}


	/**
	 * 获取焊工最高/低排行
	 * @param request
	 * @return
	 */
	@RequestMapping("getWelderList")
	@ResponseBody
	public String getWelderList(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		int status = Integer.parseInt(request.getParameter("status"));

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
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentId)){
			dto.setParent(new BigInteger(parentId));
		}
		dto.setDtoStatus(status);
		JSONObject obj = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject json = new JSONObject();
		try{
			List<ModelDto> list = lm.getWelderList(dto);
			for(ModelDto m:list){
				json.put("num", m.getLoads());
				json.put("equipment", m.getFname());
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("rows", ary);
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
		try{
			List<Insframework> insf = insm.getCause(parent, null);
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
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
}
