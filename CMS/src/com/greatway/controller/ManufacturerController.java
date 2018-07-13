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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.greatway.manager.DictionaryManager;
import com.greatway.manager.InsframeworkManager;
import com.greatway.manager.ManufacturerManager;
import com.greatway.model.Dictionarys;
import com.greatway.model.EquipmentManufacturer;
import com.greatway.model.Insframework;
import com.greatway.page.Page;
import com.greatway.util.IsnullUtil;
import com.spring.model.MyUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/manufacturer", produces = { "text/json;charset=UTF-8" })
public class ManufacturerController {
	private Page page;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int total = 0;
	
	@Autowired
	private ManufacturerManager mm;

	@Autowired
	private DictionaryManager dm;

	@Autowired
	private InsframeworkManager im;
	
	private IsnullUtil iutil = new IsnullUtil();
	
	@RequestMapping("/goManufacturer")
	public String goManufacturer(){
		return "manufacturer/manufacturer";
	}
	
	@RequestMapping("/goaddManufacturer")
	public String goaddManufacturer(){
		return "manufacturer/addmanufacturer";
	}
	
	@RequestMapping("/goeditManufacturer")
	public String goeditManufacturer(HttpServletRequest request,@RequestParam String id){
		request.setAttribute("m", mm.getManuById(new BigInteger(id)));
		return "manufacturer/editmanufacturer";
	}
	
	@RequestMapping("/goremoveManufacturer")
	public String goremoveManufacturer(HttpServletRequest request,@RequestParam String id){
		request.setAttribute("m", mm.getManuById(new BigInteger(id)));
		return "manufacturer/removemanufacturer";
	}
	
	@RequestMapping("/getManufacturerList")
	@ResponseBody
	public String getManufacturerList(HttpServletRequest request){
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String searchStr = request.getParameter("searchStr");
		page = new Page(pageIndex, pageSize, total);
		List<EquipmentManufacturer> list = mm.getmanuAll(page, searchStr);
		long total = 0;
		if(list!=null){
			PageInfo<EquipmentManufacturer> pageinfo = new PageInfo<EquipmentManufacturer>(list);
			total = pageinfo.getTotal();
		}
		JSONObject obj = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject json = new JSONObject();
		try{
			for(int i=0;i<list.size();i++){
				json.put("id", list.get(i).getId());
				json.put("name", list.get(i).getName());
				json.put("type", list.get(i).getType());
				json.put("typevalue", list.get(i).getTypeValue());
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}

	@RequestMapping("/addManufacturer")
	@ResponseBody
	public String addManufacturer(HttpServletRequest request,@ModelAttribute("manufacturer") EquipmentManufacturer manufacturer){
		JSONObject obj = new JSONObject();
		try{
			//当前层级
			String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
			//获取当前用户
			Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyUser myuser = (MyUser)object;
			BigInteger insfid = im.getUserInsfId(new BigInteger(myuser.getId()+""));
			int type = im.getUserInsfType(new BigInteger(myuser.getId()+""));
			String url = "",method = "enterTheIDU",itemid="";
			if(hierarchy.equals("1")){
				url = request.getSession().getServletContext().getInitParameter("blocurl");
				method = "enterTheWS";
			}else{
				if(type==23){
					itemid = insfid.toString();
					Insframework ins = im.getParent(insfid);
					Insframework companyid = im.getParent(ins.getId());
					url = request.getSession().getServletContext().getInitParameter(companyid.getId().toString());
				}else{
					url = request.getSession().getServletContext().getInitParameter("companyurl");
				}
			}
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(url);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"manuWebServiceImpl\",\"METHOD\":\"addManu\"}";
			String obj2 = "{\"NAME\":\""+manufacturer.getName()+"\",\"TYPEID\":\""+manufacturer.getType()+"\",\"TYPEVALUE\":\""+manufacturer.getTypeValue()+"\",\"CREATOR\":\"1\",\"INSFID\":\""+itemid+"\"}";
			Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", method), new Object[]{obj1,obj2});  
			if(method.equals("enterTheWS")){
				//集团层返回的是id
				if(objects[0].toString()!=null && !"".equals(objects[0].toString())){
					obj.put("success", true);
				}
			}else{
				if(objects[0].toString().equals("true")){
					obj.put("success", true);
				}else if(!objects[0].toString().equals("false")){
					obj.put("success", true);
					obj.put("msg", objects[0].toString());
				}else{
					obj.put("success", false);
					obj.put("errorMsg", "操作失败！");
				}
			}
		}catch(Exception e){
			obj.put("success", false);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
	}

	@RequestMapping("/editManufacturer")
	@ResponseBody
	public String editManufacturer(HttpServletRequest request,@ModelAttribute("manufacturer") EquipmentManufacturer manufacturer){
		JSONObject obj = new JSONObject();
		try{
			//获取创建者信息
			BigInteger insfid = im.getUserInsfId(new BigInteger(manufacturer.getCreator()));
			int type = im.getUserInsfType(new BigInteger(manufacturer.getCreator()));
			String url = "",method = "enterTheIDU",itemid="";
			if(type==20){
				url = request.getSession().getServletContext().getInitParameter("blocurl");
				method = "enterTheWS";
			}else if(type==23){
				itemid = insfid.toString();
				Insframework ins = im.getParent(insfid);
				Insframework companyid = im.getParent(ins.getId());
				url = request.getSession().getServletContext().getInitParameter(companyid.getId().toString());
			}else{
				url = request.getSession().getServletContext().getInitParameter("companyurl");
			}
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(url);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"manuWebServiceImpl\",\"METHOD\":\"editManu\"}";
			String obj2 = "{\"ID\":\""+manufacturer.getId()+"\",\"NAME\":\""+manufacturer.getName()+"\",\"TYPEID\":\""+manufacturer.getType()+"\",\"TYPEVALUE\":\""+manufacturer.getTypeValue()+"\",\"MODIFIER\":\"1\",\"HIERARCHY\":\""+manufacturer.getId()+"\",\"INSFID\":\""+itemid+"\"}";
			Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", method), new Object[]{obj1,obj2});  
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

	@RequestMapping("/removeManufacturer")
	@ResponseBody
	public String removeManufacturer(HttpServletRequest request,@RequestParam String id){
		JSONObject obj = new JSONObject();
		try{
			//获取创建者信息
			String uid = request.getParameter("uid");
			BigInteger insfid = im.getUserInsfId(new BigInteger(uid));
			int type = im.getUserInsfType(new BigInteger(uid));
			String url = "",method = "enterTheIDU",itemid="";
			if(type==20){
				url = request.getSession().getServletContext().getInitParameter("blocurl");
				method = "enterTheWS";
			}else if(type==23){
				itemid = insfid.toString();
				Insframework ins = im.getParent(insfid);
				Insframework companyid = im.getParent(ins.getId());
				url = request.getSession().getServletContext().getInitParameter(companyid.getId().toString());
			}else{
				url = request.getSession().getServletContext().getInitParameter("companyurl");
			}
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(url);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"manuWebServiceImpl\",\"METHOD\":\"deleteManu\"}";
			String obj2 = "{\"ID\":\""+id+"\",\"INSFID\":\""+itemid+"\"}";
			Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", method), new Object[]{obj1,obj2});  
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
			obj.put("success", true);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
	}
	

	/**
	 * 获取生产厂商类型
	 * @return
	 */
	@RequestMapping("/getTypeAll")
	@ResponseBody
	public String getTypeAll(){
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			List<Dictionarys> dictionary = dm.getDictionaryValue(4);
			for(Dictionarys d:dictionary){
				json.put("id", d.getValue());
				json.put("name", d.getValueName());
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("ary", ary);
		return obj.toString();
	}
	

	/**
	 * 校验生产厂商是否存在
	 * @param name
	 * @return
	 */
	@RequestMapping("/getManuCount")
	@ResponseBody
	public String getManuCount(HttpServletRequest request,@RequestParam String name,@RequestParam int type){
		boolean flag = true;
		int count = mm.getManuCount(name, type);
		if(count > 0){
			flag = false;
		}
		return flag + "";
	}
}
