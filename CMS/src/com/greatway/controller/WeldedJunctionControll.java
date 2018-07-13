package com.greatway.controller;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.greatway.manager.InsframeworkManager;
import com.greatway.manager.WeldedJunctionManager;
import com.greatway.model.WeldedJunction;
import com.greatway.page.Page;
import com.greatway.util.IsnullUtil;
import com.spring.model.MyUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/weldedjunction", produces = { "text/json;charset=UTF-8" })
public class WeldedJunctionControll {
	private Page page;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int total = 0;
	
	@Autowired
	private WeldedJunctionManager wjm;

	@Autowired
	private InsframeworkManager im;

	IsnullUtil iutil = new IsnullUtil();
	
	@RequestMapping("/goWeldedJunction")
	public String goWeldedJunction(){
		return "weldingjunction/weldedjunction";
	}
	
	@RequestMapping("/goShowMoreJunction")
	public String goShowMoreJunction(HttpServletRequest request,@RequestParam String id){
		try{
			WeldedJunction wj = wjm.getWeldedJunctionById(new BigInteger(id));
			request.setAttribute("wj", wj);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "weldingjunction/showmore";
	}

	@RequestMapping("/goAddWeldedJunction")
	public String goAddWeldedJunction(){
		return "weldingjunction/addweldedjunction";
	}

	@RequestMapping("/goEditWeldedJunction")
	public String goEditWeldedJunction(HttpServletRequest request){
		WeldedJunction wj = wjm.getWeldedJunctionById(new BigInteger(request.getParameter("id")));
		request.setAttribute("wj", wj);
		return "weldingjunction/editweldedjunction";
	}

	@RequestMapping("/goRemoveWeldedJunction")
	public String goRemoveWeldedJunction(HttpServletRequest request){
		WeldedJunction wj = wjm.getWeldedJunctionById(new BigInteger(request.getParameter("id")));
		request.setAttribute("wj", wj);
		return "weldingjunction/removeweldedjunction";
	}
	
	@RequestMapping("/getWeldedJunctionList")
	@ResponseBody
	public String getWeldedJunctionList(HttpServletRequest request){
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String serach = request.getParameter("searchStr");
		
		page = new Page(pageIndex,pageSize,total);
		List<WeldedJunction> list = wjm.getWeldedJunctionAll(page, serach);
		long total = 0;
		
		if(list != null){
			PageInfo<WeldedJunction> pageinfo = new PageInfo<WeldedJunction>(list);
			total = pageinfo.getTotal();
		}
		
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			for(WeldedJunction w:list){
				json.put("id", w.getId());
				json.put("weldedJunctionno", w.getWeldedJunctionno());
				json.put("serialNo", w.getSerialNo());
				json.put("pipelineNo", w.getPipelineNo());
				json.put("roomNo", w.getRoomNo());
				json.put("unit", w.getUnit());
				json.put("area", w.getArea());
				json.put("systems", w.getSystems());
				json.put("children", w.getChildren());
				json.put("externalDiameter", w.getExternalDiameter());
				json.put("wallThickness", w.getWallThickness());
				json.put("dyne", w.getDyne());
				json.put("specification", w.getSpecification());
				json.put("maxElectricity", w.getMaxElectricity());
				json.put("minElectricity", w.getMinElectricity());
				json.put("maxValtage", w.getMaxValtage());
				json.put("minValtage", w.getMinValtage());
				json.put("material", w.getMaterial());
				json.put("nextexternaldiameter", w.getNextexternaldiameter());
				json.put("itemname", w.getItemid().getName());
				json.put("startTime", w.getStartTime());
				json.put("endTime", w.getEndTime());
				json.put("creatTime", w.getCreatTime());
				json.put("updateTime", w.getUpdateTime());
				json.put("updatecount", w.getUpdatecount());
				json.put("nextwall_thickness", w.getNextwall_thickness());
				json.put("next_material", w.getNext_material());
				json.put("electricity_unit", w.getElectricity_unit());
				json.put("valtage_unit", w.getValtage_unit());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}

	@RequestMapping("/addWeldedJunction")
	@ResponseBody
	public String addWeldedJunction(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		try{
			//当前层级
			String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
			//获取当前用户
			Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyUser myuser = (MyUser)object;
			//获取项目层url
			String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
			//获取公司发布地址
			String companyurl = im.webserviceDto(request, new BigInteger(request.getParameter("itemid")));
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(companyurl);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"junctionWebServiceImpl\",\"METHOD\":\"addJunction\"}";
			String obj2 = "{\"JUNCTIONNO\":\""+request.getParameter("weldedJunctionno")+"\",\"SERIALNO\":\""+request.getParameter("serialNo")+"\",\"PIPELINENO\":\""+request.getParameter("pipelineNo")+"\",\"ROOMNO\":\""+request.getParameter("roomNo")+"\"," +
					"\"UNIT\":\""+request.getParameter("unit")+"\",\"AREA\":\""+request.getParameter("area")+"\",\"SYSTEMS\":\""+request.getParameter("systems")+"\",\"CHILDREN\":\""+request.getParameter("children")+"\",\"DYNE\":\""+request.getParameter("dyne")+"\"," +
					"\"SPECIFICATION\":\""+request.getParameter("specification")+"\",\"MAXELECTRICITY\":\""+request.getParameter("maxElectricity")+"\",\"MINELECTRICITY\":\""+request.getParameter("minElectricity")+"\",\"MAXVALTAGE\":\""+request.getParameter("maxValtage")+"\"," +
					"\"MINVALTAGE\":\""+request.getParameter("minValtage")+"\",\"MATERIAL\":\""+request.getParameter("material")+"\",\"NEXTMATERIAL\":\""+request.getParameter("next_material")+"\",\"EXTERNALDIAMETER\":\""+request.getParameter("externalDiameter")+"\"," +
					"\"NEXTEXTERNALDIAMETER\":\""+request.getParameter("nextexternaldiameter")+"\",\"WALLTHICKNESS\":\""+request.getParameter("wallThickness")+"\",\"NEXTWALLTHICKNESS\":\""+request.getParameter("nextwall_thickness")+"\",\"ELECTRICITYUNIT\":\""+request.getParameter("electricity_unit")+"\"," +
					"\"VALTAGEUNIT\":\""+request.getParameter("valtage_unit")+"\",\"STARTTIME\":\""+request.getParameter("startTime")+"\",\"ENDTIME\":\""+request.getParameter("endTime")+"\",\"INSFID\":\""+request.getParameter("itemid")+"\"," +
					"\"CREATOR\":\""+myuser.getId()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
			Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{obj1,obj2});  
			if(objects[0].toString().equals("true")){
				obj.put("success", true);
			}else if(!objects[0].toString().equals("false")){
				obj.put("success", true);
				obj.put("msg", objects[0].toString());
			}else{
				obj.put("success", false);
				obj.put("errorMsg", "操作失败！");
			}
		}catch(Exception e){
			obj.put("success", false);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
	}
	

	@RequestMapping("/editWeldedJunction")
	@ResponseBody
	public String editWeldedJunction(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		try{
			//当前层级
			String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
			//获取当前用户
			Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyUser myuser = (MyUser)object;
			//获取项目层url
			String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
			//获取公司发布地址
			String companyurl = im.webserviceDto(request, new BigInteger(request.getParameter("itemid")));
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(companyurl);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"junctionWebServiceImpl\",\"METHOD\":\"updateJunction\"}";
			String obj2 = "{\"ID\":\""+request.getParameter("id")+"\",\"JUNCTIONNO\":\""+request.getParameter("weldedJunctionno")+"\",\"SERIALNO\":\""+request.getParameter("serialNo")+"\",\"PIPELINENO\":\""+request.getParameter("pipelineNo")+"\",\"ROOMNO\":\""+request.getParameter("roomNo")+"\"," +
					"\"UNIT\":\""+request.getParameter("unit")+"\",\"AREA\":\""+request.getParameter("area")+"\",\"SYSTEMS\":\""+request.getParameter("systems")+"\",\"CHILDREN\":\""+request.getParameter("children")+"\",\"DYNE\":\""+request.getParameter("dyne")+"\"," +
					"\"SPECIFICATION\":\""+request.getParameter("specification")+"\",\"MAXELECTRICITY\":\""+request.getParameter("maxElectricity")+"\",\"MINELECTRICITY\":\""+request.getParameter("minElectricity")+"\",\"MAXVALTAGE\":\""+request.getParameter("maxValtage")+"\"," +
					"\"MINVALTAGE\":\""+request.getParameter("minValtage")+"\",\"MATERIAL\":\""+request.getParameter("material")+"\",\"NEXTMATERIAL\":\""+request.getParameter("next_material")+"\",\"EXTERNALDIAMETER\":\""+request.getParameter("externalDiameter")+"\"," +
					"\"NEXTEXTERNALDIAMETER\":\""+request.getParameter("nextexternaldiameter")+"\",\"WALLTHICKNESS\":\""+request.getParameter("wallThickness")+"\",\"NEXTWALLTHICKNESS\":\""+request.getParameter("nextwall_thickness")+"\",\"ELECTRICITYUNIT\":\""+request.getParameter("electricity_unit")+"\"," +
					"\"VALTAGEUNIT\":\""+request.getParameter("valtage_unit")+"\",\"STARTTIME\":\""+request.getParameter("startTime")+"\",\"ENDTIME\":\""+request.getParameter("endTime")+"\",\"INSFID\":\""+request.getParameter("itemid")+"\"," +
					"\"MODIFITER\":\""+myuser.getId()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
			Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{obj1,obj2});  
			if(objects[0].toString().equals("true")){
				obj.put("success", true);
			}else if(!objects[0].toString().equals("false")){
				obj.put("success", true);
				obj.put("msg", objects[0].toString());
			}else{
				obj.put("success", false);
				obj.put("errorMsg", "操作失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			obj.put("success", false);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
	}
	
	@RequestMapping("/removeWeldedJunction")
	@ResponseBody
	public String removeWeldedJunction(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		try{
			//当前层级
			String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
			//获取项目层url
			String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
			//获取公司发布地址
			String companyurl = im.webserviceDto(request, new BigInteger(request.getParameter("insfid")));
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(companyurl);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"junctionWebServiceImpl\",\"METHOD\":\"deleteJunction\"}";
			String obj2 = "{\"ID\":\""+request.getParameter("id")+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\",\"INSFID\":\""+request.getParameter("insfid")+"\"}";
			Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{obj1,obj2});  
			if(objects[0].toString().equals("true")){
				obj.put("success", true);
			}else if(!objects[0].toString().equals("false")){
				obj.put("success", true);
				obj.put("msg", objects[0].toString());
			}else{
				obj.put("success", false);
				obj.put("errorMsg", "操作失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			obj.put("success", false);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
	}
	
	@RequestMapping("/wjNoValidate")
	@ResponseBody
	private String wjNoValidate(@RequestParam String wjno){
		boolean data = true;
		int count = wjm.getWeldedjunctionByNo(wjno);
		if(count>0){
			data = false;
		}
		return data + "";
	}

}
