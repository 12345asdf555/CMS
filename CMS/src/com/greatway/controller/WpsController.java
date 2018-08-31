package com.greatway.controller;

import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.greatway.manager.InsframeworkManager;
import com.greatway.manager.WpsService;
import com.greatway.model.Insframework;
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
	
	@RequestMapping("/goShowWps")
	public String goShowWps(HttpServletRequest request) {
		Wps wps = wpsService.findWpsByid(new BigInteger(request.getParameter("id")));
		List<Wps> list = wpsService.findAll(null,wps.getFwpsnum());
		MyUser myuser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		request.setAttribute("username", myuser.getUsername());
		request.setAttribute("wps", wps);
		request.setAttribute("list", list);
		return "weldwps/showWps";
	}
	
	@RequestMapping("/goAddWps")
	public String goAddWps() {
		return "weldwps/addWps";
	}
	
	@RequestMapping("/goEditWps")
	public String goEditWps(HttpServletRequest request) {
		Wps wps = wpsService.findWpsByid(new BigInteger(request.getParameter("id")));
		List<Wps> list = wpsService.findAll(null,wps.getFwpsnum());
		MyUser myuser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		request.setAttribute("username", myuser.getUsername());
		request.setAttribute("wps", wps);
		request.setAttribute("list", list);
		request.setAttribute("listcount", list.size());
		return "weldwps/editWps";
	}
	

	@RequestMapping("/goRemoveWps")
	public String goRemoveWps(HttpServletRequest request) {
		Wps wps = wpsService.findWpsByid(new BigInteger(request.getParameter("id")));
		List<Wps> list = wpsService.findAll(null,wps.getFwpsnum());
		MyUser myuser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		request.setAttribute("username", myuser.getUsername());
		request.setAttribute("wps", wps);
		request.setAttribute("list", list);
		return "weldwps/removeWps";
	}

	@RequestMapping("/getAllWps")
	@ResponseBody
	public String getAllWps(HttpServletRequest request) {
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String search = request.getParameter("searchStr");
		String parentId = request.getParameter("parent");
		BigInteger parent = null;
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}else{
			MyUser myuser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			long uid = myuser.getId();
			parent = im.getUserInsfId(BigInteger.valueOf(uid));
		}
		page = new Page(pageIndex, pageSize, total);
		List<Wps> findAll = wpsService.findWpsAll(page, parent, search);
		long total = 0;

		if (findAll != null) {
			PageInfo<Wps> pageinfo = new PageInfo<Wps>(findAll);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try {
			for (Wps wps : findAll) {
				json.put("fid", wps.getFid());
				json.put("fwpsnum", wps.getFwpsnum());
				json.put("fversions", wps.getFversions());
				json.put("fproject_code", wps.getFproject_code());
				ary.add(json);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}

	@RequestMapping("/getAllWpsChildren")
	@ResponseBody
	public String getAllWpsChildren(HttpServletRequest request) {
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String search = request.getParameter("searchStr");
		String parentId = request.getParameter("parent");
		BigInteger parent = null;
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}else{
			MyUser myuser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			long uid = myuser.getId();
			parent = im.getUserInsfId(BigInteger.valueOf(uid));
		}
		page = new Page(pageIndex, pageSize, total);
		List<Wps> findAll = wpsService.findAll(page,parent, null,search);
		long total = 0;

		if (findAll != null) {
			PageInfo<Wps> pageinfo = new PageInfo<Wps>(findAll);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try {
			for (Wps wps : findAll) {
				json.put("fid", wps.getFid());
				json.put("fwpsnum", wps.getFwpsnum());
				json.put("fweld_i_max", wps.getFweld_i_max());
				json.put("fweld_i_min", wps.getFweld_i_min());
				json.put("fweld_v_max", wps.getFweld_v_max());
				json.put("fweld_v_min", wps.getFweld_v_min());
				json.put("insname", wps.getFitemname());
				json.put("insid", wps.getInsid());
				ary.add(json);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}
	
	/**
	 * 新增wps
	 * @param request
	 * @param wps
	 * @return
	 */
	@RequestMapping("/addWps")
	@ResponseBody
	public String addWps(HttpServletRequest request,Wps wps) {
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
			int type = im.getTypeById(wps.getFitemid());
			String webserviceurl = "",method = "enterTheIDU";
			if(type==20){
				webserviceurl = request.getSession().getServletContext().getInitParameter("blocurl");
				method="enterTheWS";
			}else if(type==21){
				webserviceurl = request.getSession().getServletContext().getInitParameter("companyurl");
			}else if(type==22){
				Insframework ins = im.getParent(wps.getFitemid());
				webserviceurl = request.getSession().getServletContext().getInitParameter(ins.getId().toString());
			}else{
				Insframework ins = im.getParent(wps.getFitemid());
				Insframework companyid = im.getParent(ins.getId());
				webserviceurl = request.getSession().getServletContext().getInitParameter(companyid.getId().toString());
			}
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(webserviceurl);
			iutil.Authority(client);
			String childrenstr = addUserChildren(request, wps.getFwpsnum(), myuser.getId(), wps.getFitemid());
			if(childrenstr==null || "".equals(childrenstr)){
				childrenstr = "[]";
			}
			String obj1 = "{\"CLASSNAME\":\"wpsWebServiceImpl\",\"METHOD\":\"addWps\"}";
			String obj2 = "{\"WPSNUM\":\""+wps.getFwpsnum()+"\",\"VERSIONS\":\""+wps.getFversions()+"\",\"INSFID\":\""+wps.getFitemid()+"\",\"PROJECT_CODE\":\""+wps.getFproject_code()+"\",\"REPORT_NUMBER\":\""+wps.getFreport_number()+"\",\"DEGREE\":\""+
					wps.getFdegree()+"\",\"EVALUATION_STANDARD\":\""+wps.getFevaluation_standard()+"\",\"VALIDITY\":\""+wps.getFvalidity()+"\",\"AUTOMATIC\":\""+wps.getFautomatic()+"\",\"GROOVE_TYPE\":\""+wps.getFgroove_type()+"\""+
					",\"STABILIVOLT_SYSTEM\":\""+wps.getFstabilivolt_system()+"\",\"MATERIALS\":\""+wps.getFmaterials()+"\",\"THICKNESS1\":\""+wps.getFthickness1()+"\",\"THICKNESS2\":\""+wps.getFthickness2()+"\""+
					",\"DIAMETER\":\""+wps.getFdiameter()+"\",\"ELES1\":\""+wps.getFeles1()+"\",\"IMAGES_URL\":\""+request.getParameter("imageurl")+"\",\"IMAGES_DESC\":\""+wps.getFimages_desc()+"\",\"CATEGORY\":\""+wps.getFcategory()+"\""+
					",\"SHOP_SIGN\":\""+wps.getFshop_sign()+"\",\"SPECIFICATION\":\""+wps.getFspecification()+"\",\"materials_type\":\""+wps.getFmaterials_type()+"\",\"MATERIALS_NUMBER1\":\""+wps.getFmaterials_number1()+"\""+
					",\"MATERIALS_SPECIFICATION1\":\""+wps.getFmaterials_specification1()+"\",\"MATERIALS_NUMBER2\":\""+wps.getFmaterials_number2()+"\",\"MATERIALS_SPECIFICATION2\":\""+wps.getFmaterials_specification2()+"\",\"SOLDERING_NUMBER\":\""+wps.getFsoldering_number()+"\""+
					",\"POSITION\":\""+wps.getFposition()+"\",\"DIRECTION\":\""+wps.getFdirection()+"\",\"ELSE2\":\""+wps.getFelse2()+"\",\"FRONT1\":\""+wps.getFfront1()+"\",\"FRONT2\":\""+wps.getFfront2()+"\""+
					",\"REVERSE1\":\""+wps.getFreverse1()+"\",\"REVERSE2\":\""+wps.getFreverse2()+"\",\"REVERSE3\":\""+wps.getFreverse3()+"\",\"TAIL\":\""+wps.getFtail()+"\",\"PREHEATING_TEMPERATURE\":\""+wps.getFpreheating_temperature()+"\""+
					",\"TEMPERATURE\":\""+wps.getFtemperature()+"\",\"PREHEAT_WAY\":\""+wps.getfPreheat_way()+"\",\"TEMPERATURE_RANGE\":\""+wps.getFtemperature_range()+"\",\"SOAKING_TIME\":\""+wps.getFsoaking_time()+"\",\"ELES3\":\""+wps.getFeles3()+"\""+
					",\"SCOPE\":\""+wps.getFscope()+"\",\"NOZZLE\":\""+wps.getFnozzle()+"\",\"DISTANCE\":\""+wps.getFdistance()+"\",\"BACK_CHIPPING\":\""+wps.getFback_chipping()+"\",\"LAYER_SCOPE1\":\""+wps.getFlayer_scope1()+"\""+
					",\"LAYER_SCOPE2\":\""+wps.getFlayer_scope2()+"\",\"TUNGSTEN_ELECTRODE\":\""+wps.getFtungsten_electrode()+"\",\"TRANSIENT_MODE\":\""+wps.getFtransient_mode()+"\",\"METHOD1\":\""+wps.getFmethod1()+"\",\"METHOD2\":\""+wps.getFmethod2()+"\""+
					",\"CREATOR\":\""+myuser.getId()+"\",\"WELDWPS\":"+childrenstr+",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\",\"INSFTYPE\":\""+type+"\"}";
			Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", method), new Object[]{obj1,obj2});  
			if(objects[0].toString().equals("true")){
				if(method.equals("enterTheWS")){
					obj.put("success", addBlocChildren(client,childrenstr,obj2));
				}else{
					obj.put("success", true);
				}
			}else if(!objects[0].toString().equals("false")){
				if(method.equals("enterTheWS")){
					obj.put("success", addBlocChildren(client,childrenstr,obj2));
				}else{
					obj.put("success", true);
				}
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
	
	public boolean addBlocChildren(Client client,String childrenstr,String obj2){
		boolean flag = true;
		try{
			if(!childrenstr.equals("[]")){
				String obj1 = "{\"CLASSNAME\":\"wpsWebServiceImpl\",\"METHOD\":\"save\"}";
				Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});  
				if(objects[0].toString()!=null && !"".equals(objects[0].toString())){
					flag = true;
				}else{
					flag = false;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	public String addUserChildren(HttpServletRequest request,String wpsnum,Long userid,BigInteger insfid){
		String flag = "";
		JSONArray ary = new JSONArray();
		JSONObject json = new JSONObject();
		try{
			String weldwps = request.getParameter("weldwps");
			if(iutil.isNull(weldwps)){
				String[] str = weldwps.split(",");
				for(int i=0;i<str.length;i++){
					String[] s = str[i].split(";");
					String[] weld_i = s[5].split("~");
					String[] weld_v = s[6].split("~");
					json.put("WPSNUM", wpsnum);
					json.put("WELDPRECHANNEL", s[0]);
					json.put("INSFID", insfid);
					json.put("WELDINGMETHOD", s[1]);
					json.put("TYPE", s[2]);
					json.put("SPECIFICATION", s[3]);
					json.put("POLARITY", s[4]);
					json.put("MINELECTRICITY", weld_i[0]);
					json.put("MAXELECTRICITY", weld_i[1]);
					json.put("MINVALTAGE", weld_v[0]);
					json.put("MAXVALTAGE", weld_v[1]);
					json.put("WELDINGSPEED", s[7]);
					json.put("CREATOR", userid);
					ary.add(json);
				}
				flag = JSON.toJSONString(ary);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return flag;
	}
	
	public String updateUserChildren(HttpServletRequest request,String wpsnum,Long userid,BigInteger insfid){
		String flag = "";
		JSONArray ary = new JSONArray();
		JSONObject json = new JSONObject();
		try{
			String weldwps = request.getParameter("oldweldwps");
			if(iutil.isNull(weldwps)){
				String[] str = weldwps.split(",");
				for(int i=0;i<str.length;i++){
					String[] s = str[i].split(";");
					String[] weld_i = s[5].split("~");
					String[] weld_v = s[6].split("~");
					json.put("WPSNUM", wpsnum);
					json.put("WELDPRECHANNEL", s[0]);
					json.put("INSFID", insfid);
					json.put("WELDINGMETHOD", s[1]);
					json.put("TYPE", s[2]);
					json.put("SPECIFICATION", s[3]);
					json.put("POLARITY", s[4]);
					json.put("MINELECTRICITY", weld_i[0]);
					json.put("MAXELECTRICITY", weld_i[1]);
					json.put("MINVALTAGE", weld_v[0]);
					json.put("MAXVALTAGE", weld_v[1]);
					json.put("WELDINGSPEED", s[7]);
					json.put("ID", s[8]);
					json.put("MODIFIER", userid);
					ary.add(json);
				}
				flag = JSON.toJSONString(ary);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return flag;
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
			//获取发布地址
			int type = im.getTypeById(wps.getFitemid());
			String webserviceurl = "",method = "enterTheIDU";
			if(type==20){
				webserviceurl = request.getSession().getServletContext().getInitParameter("blocurl");
				method="enterTheWS";
			}else if(type==21){
				webserviceurl = request.getSession().getServletContext().getInitParameter("companyurl");
			}else if(type==22){
				Insframework ins = im.getParent(wps.getFitemid());
				webserviceurl = request.getSession().getServletContext().getInitParameter(ins.getId().toString());
			}else{
				Insframework ins = im.getParent(wps.getFitemid());
				Insframework companyid = im.getParent(ins.getId());
				webserviceurl = request.getSession().getServletContext().getInitParameter(companyid.getId().toString());
			}
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(webserviceurl);
			iutil.Authority(client);
			String childrenstr1 = addUserChildren(request, wps.getFwpsnum(), myuser.getId(), wps.getFitemid());
			String childrenstr2 = updateUserChildren(request, wps.getFwpsnum(), myuser.getId(), wps.getFitemid());
			if(childrenstr1==null || "".equals(childrenstr1)){
				childrenstr1 = "[]";
			}
			if(childrenstr2==null || "".equals(childrenstr2)){
				childrenstr2 = "[]";
			}
			String obj1 = "{\"CLASSNAME\":\"wpsWebServiceImpl\",\"METHOD\":\"updateWps\"}";
			String obj2 = "{\"ID\":\""+wps.getFid()+"\",\"WPSNUM\":\""+wps.getFwpsnum()+"\",\"VERSIONS\":\""+wps.getFversions()+"\",\"INSFID\":\""+wps.getFitemid()+"\",\"PROJECT_CODE\":\""+wps.getFproject_code()+"\",\"REPORT_NUMBER\":\""+wps.getFreport_number()+"\",\"DEGREE\":\""+
					wps.getFdegree()+"\",\"EVALUATION_STANDARD\":\""+wps.getFevaluation_standard()+"\",\"VALIDITY\":\""+wps.getFvalidity()+"\",\"AUTOMATIC\":\""+wps.getFautomatic()+"\",\"GROOVE_TYPE\":\""+wps.getFgroove_type()+"\""+
					",\"STABILIVOLT_SYSTEM\":\""+wps.getFstabilivolt_system()+"\",\"MATERIALS\":\""+wps.getFmaterials()+"\",\"THICKNESS1\":\""+wps.getFthickness1()+"\",\"THICKNESS2\":\""+wps.getFthickness2()+"\""+
					",\"DIAMETER\":\""+wps.getFdiameter()+"\",\"ELES1\":\""+wps.getFeles1()+"\",\"IMAGES_URL\":\""+request.getParameter("imageurl")+"\",\"IMAGES_DESC\":\""+wps.getFimages_desc()+"\",\"CATEGORY\":\""+wps.getFcategory()+"\""+
					",\"SHOP_SIGN\":\""+wps.getFshop_sign()+"\",\"SPECIFICATION\":\""+wps.getFspecification()+"\",\"materials_type\":\""+wps.getFmaterials_type()+"\",\"MATERIALS_NUMBER1\":\""+wps.getFmaterials_number1()+"\""+
					",\"MATERIALS_SPECIFICATION1\":\""+wps.getFmaterials_specification1()+"\",\"MATERIALS_NUMBER2\":\""+wps.getFmaterials_number2()+"\",\"MATERIALS_SPECIFICATION2\":\""+wps.getFmaterials_specification2()+"\",\"SOLDERING_NUMBER\":\""+wps.getFsoldering_number()+"\""+
					",\"POSITION\":\""+wps.getFposition()+"\",\"DIRECTION\":\""+wps.getFdirection()+"\",\"ELSE2\":\""+wps.getFelse2()+"\",\"FRONT1\":\""+wps.getFfront1()+"\",\"FRONT2\":\""+wps.getFfront2()+"\""+
					",\"REVERSE1\":\""+wps.getFreverse1()+"\",\"REVERSE2\":\""+wps.getFreverse2()+"\",\"REVERSE3\":\""+wps.getFreverse3()+"\",\"TAIL\":\""+wps.getFtail()+"\",\"PREHEATING_TEMPERATURE\":\""+wps.getFpreheating_temperature()+"\""+
					",\"TEMPERATURE\":\""+wps.getFtemperature()+"\",\"PREHEAT_WAY\":\""+wps.getfPreheat_way()+"\",\"TEMPERATURE_RANGE\":\""+wps.getFtemperature_range()+"\",\"SOAKING_TIME\":\""+wps.getFsoaking_time()+"\",\"ELES3\":\""+wps.getFeles3()+"\""+
					",\"SCOPE\":\""+wps.getFscope()+"\",\"NOZZLE\":\""+wps.getFnozzle()+"\",\"DISTANCE\":\""+wps.getFdistance()+"\",\"BACK_CHIPPING\":\""+wps.getFback_chipping()+"\",\"LAYER_SCOPE1\":\""+wps.getFlayer_scope1()+"\""+
					",\"LAYER_SCOPE2\":\""+wps.getFlayer_scope2()+"\",\"TUNGSTEN_ELECTRODE\":\""+wps.getFtungsten_electrode()+"\",\"TRANSIENT_MODE\":\""+wps.getFtransient_mode()+"\",\"METHOD1\":\""+wps.getFmethod1()+"\",\"METHOD2\":\""+wps.getFmethod2()+"\""+
					",\"MODIFIER\":\""+myuser.getId()+"\",\"WELDWPS\":"+childrenstr1+",\"OLDWELDWPS\":"+childrenstr2+",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\",\"INSFTYPE\":\""+type+"\"}";
			Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", method), new Object[]{obj1,obj2});  
			if(objects[0].toString().equals("true")){
				if(method.equals("enterTheWS")){
					obj.put("success", addBlocChildren(client,childrenstr1,obj2));
				}else{
					obj.put("success", true);
				}
			}else if(!objects[0].toString().equals("false")){
				if(method.equals("enterTheWS")){
					obj.put("success", addBlocChildren(client,childrenstr1,obj2));
				}else{
					obj.put("success", true);
				}
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
	
	@RequestMapping("/destroyWps")
	@ResponseBody
	public String destroyWps(HttpServletRequest request, @RequestParam BigInteger fid, @RequestParam BigInteger insfid) {
		JSONObject obj = new JSONObject();
		try{
			//当前层级
			String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
			//获取项目层url
			String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
			//获取发布地址
			int type = im.getTypeById(insfid);
			String webserviceurl = "",method = "enterTheIDU";
			if(type==20){
				webserviceurl = request.getSession().getServletContext().getInitParameter("blocurl");
				method="enterTheWS";
			}else if(type==21){
				webserviceurl = request.getSession().getServletContext().getInitParameter("companyurl");
			}else if(type==22){
				Insframework ins = im.getParent(insfid);
				webserviceurl = request.getSession().getServletContext().getInitParameter(ins.getId().toString());
			}else{
				Insframework ins = im.getParent(insfid);
				Insframework companyid = im.getParent(ins.getId());
				webserviceurl = request.getSession().getServletContext().getInitParameter(companyid.getId().toString());
			}
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(webserviceurl);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"wpsWebServiceImpl\",\"METHOD\":\"delete\"}";
			String obj2 = "{\"ID\":\""+fid+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\",\"INSFID\":\""+insfid+"\",\"INSFTYPE\":\""+type+"\"}";
			Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", method), new Object[]{obj1,obj2});  
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
	
	@RequestMapping("/destroyWpsAll")
	@ResponseBody
	public String destroyWpsAll(HttpServletRequest request, @RequestParam BigInteger id, @RequestParam String wpsnum, @RequestParam BigInteger insfid) {
		JSONObject obj = new JSONObject();
		try{
			//当前层级
			String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
			//获取项目层url
			String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
			//获取发布地址
			int type = im.getTypeById(insfid);
			String webserviceurl = "",method = "enterTheIDU";
			if(type==20){
				webserviceurl = request.getSession().getServletContext().getInitParameter("blocurl");
				method="enterTheWS";
			}else if(type==21){
				webserviceurl = request.getSession().getServletContext().getInitParameter("companyurl");
			}else if(type==22){
				Insframework ins = im.getParent(insfid);
				webserviceurl = request.getSession().getServletContext().getInitParameter(ins.getId().toString());
			}else{
				Insframework ins = im.getParent(insfid);
				Insframework companyid = im.getParent(ins.getId());
				webserviceurl = request.getSession().getServletContext().getInitParameter(companyid.getId().toString());
			}
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(webserviceurl);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"wpsWebServiceImpl\",\"METHOD\":\"deleteWps\"}";
			String obj2 = "{\"ID\":\""+id+"\",\"WPSNUM\":\""+wpsnum+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\",\"INSFID\":\""+insfid+"\",\"INSFTYPE\":\""+type+"\"}";
			Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", method), new Object[]{obj1,obj2});  
			if(objects[0].toString().equals("true")){
				obj.put("success", true);
				obj.put("msg", null);
				removeImager(request,request.getParameter("imager"));
			}else if(!objects[0].toString().equals("false")){
				obj.put("success", true);
				obj.put("msg", objects[0].toString());
				removeImager(request,request.getParameter("imager"));
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

	//删除上传图片
	public void removeImager(HttpServletRequest request,String imageurl){
		if(iutil.isNull(imageurl)){
			//文件存储位置
			ServletContext scontext = request.getSession().getServletContext();
			// 获取绝对路径
			String path = scontext.getRealPath("") + imageurl;
			File file = new File(path);
			file.delete();
		}
	}

	@RequestMapping("/wpsvalidate")
	@ResponseBody
	public String wpsvalidate(@RequestParam String fwpsnum) {
		boolean data = true;
		int count = wpsService.getUsernameCount(fwpsnum);
		if (count > 0) {
			data = false;
		}
		return data + "";
	}
	
	@RequestMapping("/getInsAll")
	@ResponseBody
	public String getInsAll(){
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			List<Insframework> list = im.getInsAll(0);
			for(int i=0;i<list.size();i++){
				json.put("id", list.get(i).getId());
				json.put("name", list.get(i).getName());
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("ary", ary);
		return obj.toString();
	}

	@RequestMapping("/uploadimage")
	@ResponseBody
	public String uploadPicture(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		JSONObject obj = new JSONObject();
		File targetFile = null;
		String url = "";// 返回存储路径
		String fileName = file.getOriginalFilename();// 获取文件名加后缀
		if (fileName != null && fileName != "") {
			//文件存储位置
			ServletContext scontext = request.getSession().getServletContext();
			// 获取绝对路径
			String path = scontext.getRealPath("") + "excelfiles";
			String lastname = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
			fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + lastname;//当前时间+随机数=新的文件名
			// 如果文件夹不存在则创建
			File pathfile = new File(path);
			if (!pathfile.exists()) {
				pathfile.mkdirs();
			}
			// 将图片存入文件夹
			targetFile = new File(path, fileName);
			try {
				// 将上传的文件写到服务器上指定的文件。
				file.transferTo(targetFile);
				obj.put("success", true);
				url = "excelfiles/" + fileName;
			} catch (Exception e) {
				e.printStackTrace();
				obj.put("success", false);
				obj.put("errorMsg", e.getMessage());
			}
		}
		obj.put("imgurl", url);
		return obj.toString();
	}
}
