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
import com.greatway.manager.InsframeworkManager;
import com.greatway.manager.WelderManager;
import com.greatway.model.Welder;
import com.greatway.page.Page;
import com.greatway.util.IsnullUtil;
import com.spring.model.MyUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/welder", produces = { "text/json;charset=UTF-8" })
public class WelderController {
	private Page page;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int total = 0;
	
	@Autowired
	private WelderManager wm;

	@Autowired
	private InsframeworkManager im;
	
	IsnullUtil iutil = new IsnullUtil();
	
	@RequestMapping("/goWelder")
	public String goWelder(){
		return "welder/welder";
	}
	
	@RequestMapping("/goAddWelder")
	public String goAddWeldedJunction(){
		return "welder/addwelder";
	}

	@RequestMapping("/goEditWelder")
	public String goEditWeldedJunction(HttpServletRequest request){
		Welder w = wm.getWelderById(new BigInteger(request.getParameter("id")));
		request.setAttribute("w", w);
		return "welder/editwelder";
	}

	@RequestMapping("/goRemoveWelder")
	public String goRemoveWeldedJunction(HttpServletRequest request){
		Welder w = wm.getWelderById(new BigInteger(request.getParameter("id")));
		request.setAttribute("w", w);
		return "welder/removewelder";
	}
	
	
	@RequestMapping("/getWelderList")
	@ResponseBody
	public String getWelderList(HttpServletRequest request){
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String search = request.getParameter("searchStr");
		page = new Page(pageIndex,pageSize,total);
		List<Welder> list =wm.getWelderAll(page, search);
		long total = 0;
		
		if(list != null){
			PageInfo<Welder> pageinfo = new PageInfo<Welder>(list);
			total = pageinfo.getTotal();
		}
		
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			for(Welder we:list){
				json.put("id", we.getId());
				json.put("name", we.getName());
				json.put("welderno", we.getWelderno());
				json.put("itemname", we.getIname());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}

	@RequestMapping("/addWelder")
	@ResponseBody
	public String addWelder(HttpServletRequest request,@ModelAttribute("we")Welder we){
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
			String companyurl = im.webserviceDto(request, we.getIid());
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(companyurl);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"welderWebServiceImpl\",\"METHOD\":\"addWelder\"}";
			String obj2 = "{\"NAME\":\""+we.getName()+"\",\"WELDERNO\":\""+we.getWelderno()+"\",\"INSFID\":\""+we.getIid()+"\","
					+ "\"CREATOR\":\""+myuser.getId()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
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
	
	@RequestMapping("/editWelder")
	@ResponseBody
	public String editWelder(HttpServletRequest request,@ModelAttribute("we")Welder we){
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
			String companyurl = im.webserviceDto(request, we.getIid());
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(companyurl);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"welderWebServiceImpl\",\"METHOD\":\"editWelder\"}";
			String obj2 = "{\"ID\":\""+we.getId()+"\",\"NAME\":\""+we.getName()+"\",\"WELDERNO\":\""+we.getWelderno()+"\",\"INSFID\":\""+we.getIid()+"\","
					+"\"MODIFIER\":\""+myuser.getId()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
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
	
	@RequestMapping("/removeWelder")
	@ResponseBody
	public String removeWelder(HttpServletRequest request){
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
			String obj1 = "{\"CLASSNAME\":\"welderWebServiceImpl\",\"METHOD\":\"removeWelder\"}";
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
			obj.put("success", true);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
	}
	
	/**
	 * 校验焊工编号是否存在
	 * @param wno
	 * @return
	 */
	@RequestMapping("/wnoValidate")
	@ResponseBody
	public String wnoValidate(@RequestParam String wno){
		boolean flag = true;
		int count = wm.getWeldernoCount(wno);
		if(count > 0){
			flag = false;
		}
		return flag + "";
	}
}
