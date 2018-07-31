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
import com.greatway.manager.GatherManager;
import com.greatway.manager.InsframeworkManager;
import com.greatway.model.Gather;
import com.greatway.page.Page;
import com.greatway.util.IsnullUtil;
import com.spring.model.MyUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/gather", produces = { "text/json;charset=UTF-8" })
public class GatherController {
	private Page page;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int total = 0;
	
	@Autowired
	private GatherManager gm;
	
	@Autowired
	private InsframeworkManager im;
	
	IsnullUtil iutil = new IsnullUtil();
	
	/**
	 * 跳转采集页面
	 * @return
	 */
	@RequestMapping("/goGather")
	public String goGather(){
		return "gather/gather";
	}
	
	/**
	 * 跳转新增页面
	 * @return
	 */
	@RequestMapping("/goaddGather")
	public String goaddGather(){
		return "gather/addgather";
	}
	
	/**
	 * 跳转修改页面
	 * @return
	 */
	@RequestMapping("/goeditGather")
	public String goeditGather(HttpServletRequest request,@RequestParam String id){
		Gather gather = gm.getGatherById(new BigInteger(id));
		request.setAttribute("g", gather);
		return "gather/editgather";
	}
	
	/**
	 * 跳转删除页面
	 * @return
	 */
	@RequestMapping("/goremoveGather")
	public String goremoveGather(HttpServletRequest request,@RequestParam String id,@RequestParam String itemid){
		Gather gather = gm.getGatherById(new BigInteger(id));
		request.setAttribute("g", gather);
		request.setAttribute("itemid", itemid);
		return "gather/removegather";
	}
	
	@RequestMapping("/getGatherList")
	@ResponseBody
	public String getGatherList(HttpServletRequest request){
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String searchStr = request.getParameter("searchStr");
		String parentid = request.getParameter("parent");
		request.getSession().setAttribute("searchStr", searchStr);
		BigInteger parent = null;
		if(iutil.isNull(parentid)){
			parent = new BigInteger(parentid);
		}else{
			MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
				    .getAuthentication()  
				    .getPrincipal();
			long uid = myuser.getId();
			parent = im.getUserInsfId(BigInteger.valueOf(uid));
		}
		page = new Page(pageIndex,pageSize,total);
		
		List<Gather> list = gm.getGatherPageAll(page, searchStr, parent);
		long total = 0;
		
		if(list != null){
			PageInfo<Gather> pageinfo = new PageInfo<Gather>(list);
			total = pageinfo.getTotal();
		}
		
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			for(Gather g:list){
				json.put("id", g.getId());
				json.put("gatherNo", g.getGatherNo());
				json.put("itemid",g.getItemid());
				json.put("itemname",g.getItemname());
				json.put("status",g.getStatus());
				json.put("protocol", g.getProtocol());
				json.put("ipurl", g.getIpurl());
				json.put("macurl", g.getMacurl());
				json.put("leavetime", g.getLeavetime());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/addGather")
	@ResponseBody
	public String addGather(HttpServletRequest request,@ModelAttribute("gether")Gather gather){
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
			String companyurl = im.webserviceDto(request, gather.getItemid());
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(companyurl);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"gatherWebServiceImpl\",\"METHOD\":\"addGather\"}";
			String obj2 = "{\"GATHERNO\":\""+gather.getGatherNo()+"\",\"IPURL\":\""+gather.getIpurl()+"\",\"INSFID\":\""+gather.getItemid()+"\",\"LEAVETIME\":\""+gather.getLeavetime()+"\",\"MACURL\":\""+gather.getMacurl()+"\",\"PROTOCOL\":\""+
					gather.getProtocol()+"\",\"STATUS\":\""+gather.getStatus()+"\",\"CREATOR\":\""+myuser.getId()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
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
	
	@RequestMapping("/editGather")
	@ResponseBody
	public String editGather(HttpServletRequest request,@ModelAttribute("gether")Gather gather, @RequestParam String id){
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
			String companyurl = im.webserviceDto(request, gather.getItemid());
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(companyurl);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"gatherWebServiceImpl\",\"METHOD\":\"editGather\"}";
			String obj2 = "{\"ID\":\""+gather.getId()+"\",\"GATHERNO\":\""+gather.getGatherNo()+"\",\"IPURL\":\""+gather.getIpurl()+"\",\"INSFID\":\""+gather.getItemid()+"\",\"LEAVETIME\":\""+gather.getLeavetime()+"\",\"MACURL\":\""+gather.getMacurl()+"\",\"PROTOCOL\":\""+
					gather.getProtocol()+"\",\"STATUS\":\""+gather.getStatus()+"\",\"MODIFIER\":\""+myuser.getId()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
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
	
	@RequestMapping("/removeGather")
	@ResponseBody
	public String removeGather(HttpServletRequest request,@RequestParam String id,@RequestParam String insfid){
		JSONObject obj = new JSONObject();
		try{
			//当前层级
			String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
			//获取项目层url
			String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
			//获取公司发布地址
			String companyurl = im.webserviceDto(request, new BigInteger(insfid));
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(companyurl);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"gatherWebServiceImpl\",\"METHOD\":\"deleteGather\"}";
			String obj2 = "{\"ID\":\""+id+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\",\"INSFID\":\""+insfid+"\"}";
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
	
	/**
	 * 校验采集编号是否存在
	 * @param name
	 * @return
	 */
	@RequestMapping("/gathernoValidate")
	@ResponseBody
	public String gathernoValidate(HttpServletRequest request,@RequestParam String gatherno){
		boolean flag = true;
		String itemid = request.getParameter("itemid");
		BigInteger item = null;
		if(iutil.isNull(itemid)){
			item = new BigInteger(itemid);
		}
		int count = gm.getGatherNoCount(gatherno,item);
		if(count > 0){
			flag = false;
		}
		return flag + "";
	}
}
