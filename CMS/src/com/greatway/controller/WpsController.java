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
		List<Wps> list = wpsService.findAll(wps.getFwpsnum());
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
		List<Wps> list = wpsService.findAll(wps.getFwpsnum());
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
		List<Wps> list = wpsService.findAll(wps.getFwpsnum());
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
			//获取当前用户
			Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyUser myuser = (MyUser)object;
			String imageurl = request.getParameter("imageurl");
			wps.setFimages_url(imageurl);
			wps.setFcreator(new BigInteger(myuser.getId()+""));
			String weldwps = request.getParameter("weldwps");
			if(iutil.isNull(weldwps)){
				String[] str = weldwps.split(",");
				for(int i=0;i<str.length;i++){
					Wps w = new Wps();
					String[] s = str[i].split(";");
					w.setFwpsnum(wps.getFwpsnum());
					w.setFweld_prechannel(Integer.parseInt(s[0]));
					w.setFwelding_method(s[1]);
					w.setFtype(s[2]);
					w.setFchildren_specification(s[3]);
					w.setFpolarity(s[4]);
					String[] weld_i = s[5].split("~");
					String[] weld_v = s[6].split("~");
					w.setFweld_i_min(Integer.parseInt(weld_i[0]));
					w.setFweld_i_max(Integer.parseInt(weld_i[1]));
					w.setFweld_v_min(Integer.parseInt(weld_v[0]));
					w.setFweld_v_max(Integer.parseInt(weld_v[1]));
					w.setFweld_i((Integer.parseInt(weld_i[0])+Integer.parseInt(weld_i[1]))/2);
					w.setFweld_v((Integer.parseInt(weld_v[0])+Integer.parseInt(weld_v[1]))/2);
					w.setFweld_alter_i(0);
					w.setFweld_alter_v(0);
					w.setFwelding_speed(s[7]);
					w.setFcreater((Long)myuser.getId());
					w.setInsid(wps.getFitemid());
					w.setFname(wps.getFwpsnum());
					boolean flag = wpsService.save(w);
					if(!flag){
						obj.put("success", false);
						obj.put("errorMsg", "操作失败！");
						return obj.toString();
					}
				}
			}
			boolean result = wpsService.addWps(wps);
			if(result){
				obj.put("success", true);
			}else{
				obj.put("success", false);
				obj.put("errorMsg", "操作失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
	}

	@RequestMapping("/addWpsXXX")
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
			//获取当前用户
			Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyUser myuser = (MyUser)object;
			String imageurl = request.getParameter("imageurl");
			wps.setFimages_url(imageurl);
			wps.setFmodifier(new BigInteger(myuser.getId()+""));
			String weldwps = request.getParameter("weldwps");
			String oldweldwps = request.getParameter("oldweldwps");
			String[] editstr = oldweldwps.split(",");
			if(iutil.isNull(oldweldwps)){
				for(int i=0;i<editstr.length;i++){
					Wps w = new Wps();
					String[] s = editstr[i].split(";");
					w.setFwpsnum(wps.getFwpsnum());
					w.setFweld_prechannel(Integer.parseInt(s[0]));
					w.setFwelding_method(s[1]);
					w.setFtype(s[2]);
					w.setFchildren_specification(s[3]);
					w.setFpolarity(s[4]);
					String[] weld_i = s[5].split("~");
					String[] weld_v = s[6].split("~");
					w.setFweld_i_min(Integer.parseInt(weld_i[0]));
					w.setFweld_i_max(Integer.parseInt(weld_i[1]));
					w.setFweld_v_min(Integer.parseInt(weld_v[0]));
					w.setFweld_v_max(Integer.parseInt(weld_v[1]));
					w.setFweld_i((Integer.parseInt(weld_i[0])+Integer.parseInt(weld_i[1]))/2);
					w.setFweld_v((Integer.parseInt(weld_v[0])+Integer.parseInt(weld_v[1]))/2);
					w.setFweld_alter_i(0);
					w.setFweld_alter_v(0);
					w.setFwelding_speed(s[7]);
					w.setChildrenid(new BigInteger(s[8]));
					w.setFupdater((Long)myuser.getId());
					w.setInsid(wps.getFitemid());
					w.setFname(wps.getFwpsnum());
					boolean flag = wpsService.update(w);
					if(!flag){
						obj.put("success", false);
						obj.put("errorMsg", "操作失败！");
						return obj.toString();
					}
				}
			}

			if(iutil.isNull(weldwps)){
				String[] str = weldwps.split(",");
				for(int i=0;i<str.length;i++){
					Wps w = new Wps();
					String[] s = str[i].split(";");
					w.setFwpsnum(wps.getFwpsnum());
					w.setFweld_prechannel(Integer.parseInt(s[0]));
					w.setFwelding_method(s[1]);
					w.setFtype(s[2]);
					w.setFchildren_specification(s[3]);
					w.setFpolarity(s[4]);
					String[] weld_i = s[5].split("~");
					String[] weld_v = s[6].split("~");
					w.setFweld_i_min(Integer.parseInt(weld_i[0]));
					w.setFweld_i_max(Integer.parseInt(weld_i[1]));
					w.setFweld_v_min(Integer.parseInt(weld_v[0]));
					w.setFweld_v_max(Integer.parseInt(weld_v[1]));
					w.setFweld_i((Integer.parseInt(weld_i[0])+Integer.parseInt(weld_i[1]))/2);
					w.setFweld_v((Integer.parseInt(weld_v[0])+Integer.parseInt(weld_v[1]))/2);
					w.setFweld_alter_i(0);
					w.setFweld_alter_v(0);
					w.setFwelding_speed(s[7]);
					w.setFcreater((Long)myuser.getId());
					w.setInsid(wps.getFitemid());
					w.setFname(wps.getFwpsnum());
					boolean flag = wpsService.save(w);
					if(!flag){
						obj.put("success", false);
						obj.put("errorMsg", "操作失败！");
						return obj.toString();
					}
				}
			}
			boolean result = wpsService.updateWps(wps);
			if(result){
				obj.put("success", true);
			}else{
				obj.put("success", false);
				obj.put("errorMsg", "操作失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
	}
	
	@RequestMapping("/updateWpsXX")
	@ResponseBody
	public String updateWpsXX(Wps wps, HttpServletRequest request) {
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
			boolean flag = wpsService.delete(fid);
			obj.put("success", flag);
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
			boolean flags = wpsService.deleteWps(wpsnum, id);
			obj.put("success", flags);
		}catch(Exception e){
			e.printStackTrace();
			obj.put("success", false);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
	}

	@RequestMapping("/destroyWpsXXX")
	@ResponseBody
	public String destroyWpsXXX(HttpServletRequest request, @RequestParam BigInteger fid, @RequestParam BigInteger insfid) {
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
