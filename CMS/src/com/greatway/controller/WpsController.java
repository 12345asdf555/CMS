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
import com.greatway.manager.WpsService;
import com.greatway.model.Wps;
import com.greatway.page.Page;
import com.greatway.util.IsnullUtil;
import com.spring.model.MyUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/wps", produces = { "text/json;charset=UTF-8" })
public class WpsController {
	private Page page;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int total = 0;

	@Autowired
	private WpsService wpsService;
	
	@Autowired
	private InsframeworkManager im;

	IsnullUtil iutil = new IsnullUtil();

	@RequestMapping("/goWps")
	public String goWelder() {
		return "weldwps/allWps";
	}

	@RequestMapping("/getAllWps")
	@ResponseBody
	public String getAllWps(HttpServletRequest request) {
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String search = request.getParameter("searchStr");
		String parentId = request.getParameter("parent");
		BigInteger parent = null;
		if (iutil.isNull(parentId)) {
			parent = new BigInteger(parentId);
		}
		page = new Page(pageIndex, pageSize, total);
		List<Wps> findAll = wpsService.findAll(page, parent, search);
		long total = 0;

		if (findAll != null) {
			PageInfo<Wps> pageinfo = new PageInfo<Wps>(findAll);
			total = pageinfo.getTotal();
		}

		request.setAttribute("wpsList", findAll);
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try {
			for (Wps wps : findAll) {
				json.put("fid", wps.getFid());
				json.put("fwpsnum", wps.getFwpsnum());
				json.put("fweld_i", wps.getFweld_i());
				json.put("fweld_v", wps.getFweld_v());
				json.put("fweld_i_max", wps.getFweld_i_max());
				json.put("fweld_i_min", wps.getFweld_i_min());
				json.put("fweld_v_max", wps.getFweld_v_max());
				json.put("fweld_v_min", wps.getFweld_v_min());
				json.put("fweld_alter_i", wps.getFweld_alter_i());
				json.put("fweld_alter_v", wps.getFweld_alter_v());
				json.put("fweld_prechannel", wps.getFweld_prechannel());
				json.put("insname", wps.getInsname());
				json.put("insid", wps.getInsid());
				json.put("fback", wps.getFback());
				json.put("fname", wps.getFname());
				json.put("fdiameter", wps.getFdiameter());
				ary.add(json);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}


	@RequestMapping("/addWps")
	@ResponseBody
	public String addUser(HttpServletRequest request,Wps wps) {
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
			String companyurl = im.webserviceDto(request, wps.getInsid());
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(companyurl);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"wpsWebServiceImpl\",\"METHOD\":\"save\"}";
			String obj2 = "{\"WPSNO\":\""+wps.getFwpsnum()+"\",\"PRECHANNEL\":\""+wps.getFweld_prechannel()+"\",\"ALERTELECTRICITY\":\""+wps.getFweld_alter_i()+"\",\"ALERTVALTAGE\":\""+wps.getFweld_alter_v()+"\",\"ELECTRICITY\":\""+wps.getFweld_i()+"\",\"VALTAGE\":\""+
					wps.getFweld_v()+"\",\"MAXELECTRICITY\":\""+wps.getFweld_i_max()+"\",\"MINELECTRICITY\":\""+wps.getFweld_i_min()+"\",\"MAXVALTAGE\":\""+wps.getFweld_v_max()+"\",\"MINVALTAGE\":\""+wps.getFweld_v_min()+"\",\"NAME\":\""
					+wps.getFname()+"\",\"DIAMETER\":\""+wps.getFdiameter()+"\",\"INSFID\":\""+wps.getInsid()+"\",\"BACK\":\""+wps.getFback()+"\",\"CREATOR\":\""+myuser.getId()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
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

	@RequestMapping("/updateWps")
	@ResponseBody
	public String updateWps(Wps wps, HttpServletRequest request) {
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
			String companyurl = im.webserviceDto(request, wps.getInsid());
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(companyurl);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"wpsWebServiceImpl\",\"METHOD\":\"update\"}";
			String obj2 = "{\"ID\":\""+wps.getFid()+"\",\"WPSNO\":\""+wps.getFwpsnum()+"\",\"PRECHANNEL\":\""+wps.getFweld_prechannel()+"\",\"ALERTELECTRICITY\":\""+wps.getFweld_alter_i()+"\",\"ALERTVALTAGE\":\""+wps.getFweld_alter_v()+"\",\"ELECTRICITY\":\""+wps.getFweld_i()+"\",\"VALTAGE\":\""+
					wps.getFweld_v()+"\",\"MAXELECTRICITY\":\""+wps.getFweld_i_max()+"\",\"MINELECTRICITY\":\""+wps.getFweld_i_min()+"\",\"MAXVALTAGE\":\""+wps.getFweld_v_max()+"\",\"MINVALTAGE\":\""+wps.getFweld_v_min()+"\",\"NAME\":\""
					+wps.getFname()+"\",\"DIAMETER\":\""+wps.getFdiameter()+"\",\"INSFID\":\""+wps.getInsid()+"\",\"BACK\":\""+wps.getFback()+"\",\"MODIFIER\":\""+myuser.getId()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
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

	@RequestMapping("/destroyWps")
	@ResponseBody
	public String destroyWps(HttpServletRequest request, @RequestParam BigInteger fid, @RequestParam BigInteger insfid) {
		JSONObject obj = new JSONObject();
		try{
			//当前层级
			String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
			//获取项目层url
			String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
			//获取公司发布地址
			String companyurl = im.webserviceDto(request, fid);
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(companyurl);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"wpsWebServiceImpl\",\"METHOD\":\"delete\"}";
			String obj2 = "{\"ID\":\""+fid+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\",\"INSFID\":\""+insfid+"\"}";
			Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{obj1,obj2});  
			if(objects[0].toString().equals("true")){
				obj.put("success", true);
				obj.put("msg", null);
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

	@RequestMapping("/wpsvalidate")
	@ResponseBody
	private String wpsvalidate(@RequestParam String fwpsnum) {
		boolean data = true;
		int count = wpsService.getUsernameCount(fwpsnum);
		if (count > 0) {
			data = false;
		}
		return data + "";
	}

	
}
