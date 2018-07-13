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
import com.greatway.manager.MaintainManager;
import com.greatway.model.Dictionarys;
import com.greatway.model.WeldingMachine;
import com.greatway.model.WeldingMaintenance;
import com.greatway.page.Page;
import com.greatway.util.IsnullUtil;
import com.spring.model.MyUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/maintain", produces = { "text/json;charset=UTF-8" })
public class MaintainController {
	private Page page;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int total = 0;

	@Autowired
	private MaintainManager mm;
	IsnullUtil iutil = new IsnullUtil();

	@Autowired
	private DictionaryManager dm;
	
	@Autowired
	private InsframeworkManager im;
	
	/**
	 * 跳转维修记录页面
	 * @return
	 */
	@RequestMapping("/goMaintain")
	public String goWeldingMahine(){
		return "maintain/maintain";
	}
	
	/**
	 * 跳转删除维修记录页面
	 * @param request
	 * @param wid
	 * @param tname
	 * @return
	 */
	@RequestMapping("/goremoveMaintain")
	public String goremoveWeldingMahine(HttpServletRequest request, @RequestParam String wid,@RequestParam String tname,@RequestParam String insfid){
		WeldingMaintenance maibtain = mm.getWeldingMaintenanceById(new BigInteger(wid));
		request.setAttribute("m", maibtain);
		request.setAttribute("tname", tname);
		request.setAttribute("insfid", insfid);
		return "maintain/removemaintain";
	}
	
	/**
	 * 跳转新增维修记录页面
	 * @return
	 */
	@RequestMapping("/goAddMaintain")
	public String goAddMaintain(){
		return "maintain/addmaintain";
	}
	
	/**
	 * 跳转修改维修记录页面
	 * @param request
	 * @param wid
	 * @return
	 */
	@RequestMapping("/goEditMaintain")
	public String goEditMaintain(HttpServletRequest request, @RequestParam String wid,@RequestParam String insfid){
		WeldingMaintenance wm = mm.getWeldingMaintenanceById(new BigInteger(wid));
		request.setAttribute("wm", wm);
		request.setAttribute("insfid", insfid);
		return "maintain/editmaintain";
	}
	
	/**
	 * 显示维修列表
	 * @return
	 */
	@RequestMapping("/getMaintainList")
	@ResponseBody
	public String getWeldingMachine(HttpServletRequest request){
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String weldingmachineId = request.getParameter("wid");
		String parent = request.getParameter("parent");
		String searchStr = request.getParameter("searchStr");
		BigInteger parentid = null;
		if(iutil.isNull(parent)){
			parentid = new BigInteger(parent);
		}else{
			MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
				    .getAuthentication()  
				    .getPrincipal();
			long uid = myuser.getId();
			parentid = im.getUserInsfId(BigInteger.valueOf(uid));
		}
		if(iutil.isNull(searchStr)){
			searchStr += " and (i.fid="+parentid+" or ins.fid="+parentid+" or insf.fid="+parentid+" or insf.fparent="+parentid+")";
		} else{
			searchStr = "(i.fid="+parentid+" or ins.fid="+parentid+" or insf.fid="+parentid+" or insf.fparent="+parentid+")";
		}
		request.getSession().setAttribute("searchStr", searchStr);
		BigInteger wid = null;
		if(iutil.isNull(weldingmachineId)){
			wid = new BigInteger(weldingmachineId);
		}
		page = new Page(pageIndex,pageSize,total);
		
		List<WeldingMaintenance> list = mm.getWeldingMaintenanceAllPage(page,wid,searchStr);
		long total = 0;
		
		if(list != null){
			PageInfo<WeldingMaintenance> pageinfo = new PageInfo<WeldingMaintenance>(list);
			total = pageinfo.getTotal();
		}
		
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			for(WeldingMaintenance wm:list){
				json.put("id", wm.getId());
				json.put("equipmentNo", wm.getWelding().getEquipmentNo());
				json.put("wid", wm.getWelding().getId());
				json.put("mid", wm.getMaintenance().getId());
				json.put("viceman", wm.getMaintenance().getViceman());
				json.put("money", wm.getMaintenance().getMoney());
				json.put("starttime",wm.getMaintenance().getStartTime());
				json.put("endtime", wm.getMaintenance().getEndTime());
				json.put("typeid", wm.getMaintenance().getTypeId());
				json.put("typename", wm.getMaintenance().getTypename());
				json.put("desc", wm.getMaintenance().getDesc());
				json.put("insfid", wm.getInsfid());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getComboboxValue")
	@ResponseBody
	public String getComboboxValue(){
		JSONObject json = new JSONObject();
		JSONArray ary1 = new JSONArray();
		JSONArray ary2 = new JSONArray();
		JSONObject obj = new JSONObject();
		
		try{
			List<WeldingMachine> list1 = mm.getEquipmentno();
			for(WeldingMachine wm:list1){
				json.put("equipmentNo", wm.getEquipmentNo());
				json.put("mid", wm.getId());
				ary1.add(json);
			}
			List<Dictionarys> dictionary = dm.getDictionaryValue(5);
			for(Dictionarys d:dictionary){
				json.put("typeid", d.getValue());
				json.put("typename", d.getValueName());
				ary2.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("ary1", ary1);
		obj.put("ary2", ary2);
		return obj.toString();
	}
	
	@RequestMapping("/addMaintain")
	@ResponseBody
	public String addMaintain(HttpServletRequest request,@ModelAttribute("wm")WeldingMaintenance wm) {
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
			BigInteger insfid = mm.getInsfidByMachineid(new BigInteger(request.getParameter("wId")));
			String companyurl = im.webserviceDto(request, insfid);
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(companyurl);
			iutil.Authority(client);
			int money = 0;
			if(iutil.isNull(request.getParameter("money"))){
				money = Integer.parseInt(request.getParameter("money"));
			}
			String obj1 = "{\"CLASSNAME\":\"maintainWebServiceImpl\",\"METHOD\":\"addMaintian\"}";
			String obj2 = "{\"VICEMAN\":\""+request.getParameter("viceman")+"\",\"INSFID\":\""+insfid+"\",\"STARTTIME\":\""+request.getParameter("startTime")+
					"\",\"ENDTIME\":\""+request.getParameter("endTime")+"\",\"DESC\":\""+request.getParameter("desc")+"\",\"MONEY\":\""+money+"\",\"TYPEID\":\""+request.getParameter("tId")+
					"\",\"WELDID\":\""+request.getParameter("wId")+"\",\"CREATOR\":\""+myuser.getId()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
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
	
	@RequestMapping("/editMaintain")
	@ResponseBody
	public String editMaintain(HttpServletRequest request,@RequestParam String mid,@RequestParam String wid,@RequestParam String insfid) {
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
			String companyurl = im.webserviceDto(request, new BigInteger(insfid));
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(companyurl);
			iutil.Authority(client);
			int money = 0;
			if(iutil.isNull(request.getParameter("money"))){
				money = Integer.parseInt(request.getParameter("money"));
			}
			String obj1 = "{\"CLASSNAME\":\"maintainWebServiceImpl\",\"METHOD\":\"updateMaintenanceRecord\"}";
			String obj2 = "{\"MID\":\""+new BigInteger(mid)+"\",\"WID\":\""+new BigInteger(wid)+"\",\"INSFID\":\""+insfid+"\",\"VICEMAN\":\""+request.getParameter("viceman")+"\",\"STARTTIME\":\""+request.getParameter("startTime")+"\","
					+ "\"ENDTIME\":\""+request.getParameter("endTime")+"\",\"DESC\":\""+request.getParameter("desc")+"\",\"MONEY\":\""+money+"\",\"TYPEID\":\""+request.getParameter("tId")+"\","
					+ "\"MODIFIER\":\""+myuser.getId()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
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
	
	/**
	 * 完成维修
	 * @param wid
	 * @return
	 */
	@RequestMapping("/updateEndtime")
	@ResponseBody
	public String updateEndtime(HttpServletRequest request,@RequestParam String wid,@RequestParam String weldingid,@RequestParam String insfid){
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
			String obj1 = "{\"CLASSNAME\":\"maintainWebServiceImpl\",\"METHOD\":\"updateEndtime\"}";
			String obj2 = "{\"MID\":\""+wid+"\",\"WELDINGID\":\""+weldingid+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\",\"INSFID\":\""+insfid+"\"}";
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
	
	@RequestMapping("/removeMaintain")
	@ResponseBody
	public String removeMaintain(HttpServletRequest request,@RequestParam String wid,@RequestParam String insfid){
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
			String obj1 = "{\"CLASSNAME\":\"maintainWebServiceImpl\",\"METHOD\":\"deleteMaintenanceRecord\"}";
			String obj2 = "{\"MID\":\""+wid+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\",\"INSFID\":\""+insfid+"\"}";
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
}
