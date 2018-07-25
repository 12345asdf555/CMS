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
import com.greatway.manager.FaultManager;
import com.greatway.manager.InsframeworkManager;
import com.greatway.model.Dictionarys;
import com.greatway.model.Fault;
import com.greatway.model.Insframework;
import com.greatway.page.Page;
import com.greatway.util.IsnullUtil;
import com.spring.model.MyUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/fault", produces = { "text/json;charset=UTF-8" })
public class FaultController {
  private Page page;
  private int pageIndex = 1;
  private int pageSize = 10;
  private int total = 0;
  
  @Autowired
  private FaultManager fm;

  @Autowired
  private DictionaryManager dm;

  @Autowired
  private InsframeworkManager im;
  private IsnullUtil iutil = new IsnullUtil();
  
  @RequestMapping("/goFault")
  public String goFault(){
    return "fault/fault";
  }
  
  @RequestMapping("/goaddFault")
  public String goaddFault(){
    return "fault/addfault";
  }
  
  @RequestMapping("/goeditFault")
  public String goeditFault(HttpServletRequest request,@RequestParam String id){
    request.setAttribute("f", fm.getFaultById(new BigInteger(id)));
    return "fault/editfault";
  }
  
  @RequestMapping("/goremoveFault")
  public String goremoveFault(HttpServletRequest request,@RequestParam String id){
    request.setAttribute("f", fm.getFaultById(new BigInteger(id)));
    return "fault/removefault";
  }
  
  @RequestMapping("/getFaultList")
  @ResponseBody
  public String getFaultList(HttpServletRequest request){
    pageIndex = Integer.parseInt(request.getParameter("page"));
    pageSize = Integer.parseInt(request.getParameter("rows"));
    String searchStr = request.getParameter("searchStr");
    page = new Page(pageIndex, pageSize, total);
    List<Fault> list = fm.getFaultAll(page, searchStr);
    long total = 0;
    if(list!=null){
      PageInfo<Fault> pageinfo = new PageInfo<Fault>(list);
      total = pageinfo.getTotal();
    }
    JSONObject obj = new JSONObject();
    JSONArray ary = new JSONArray();
    JSONObject json = new JSONObject();
    try{
      for(int i=0;i<list.size();i++){
        json.put("id", list.get(i).getId());
        json.put("code", list.get(i).getCode());
        json.put("type", list.get(i).getType());
        json.put("codeid", list.get(i).getCodeid());
        json.put("typeid", list.get(i).getTypeid());
        json.put("machineid", list.get(i).getMachineid());
        json.put("machineno", list.get(i).getMachineno());
        json.put("itemid", list.get(i).getItemid());
        json.put("time", list.get(i).getTime());
        ary.add(json);
      }
    }catch(Exception e){
      e.printStackTrace();
    }
    obj.put("total", total);
    obj.put("rows", ary);
    return obj.toString();
  }
@RequestMapping("/addFault")
  @ResponseBody
  public String addFault(HttpServletRequest request,@ModelAttribute("fault") Fault fault){
    JSONObject obj = new JSONObject();
    try{
    	//当前层级
		String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
		//获取项目层url
		String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
		//获取公司发布地址
		String companyurl = im.webserviceDto(request, fault.getItemid());
		//客户端执行操作
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(companyurl);
		iutil.Authority(client);
		String obj1 = "{\"CLASSNAME\":\"faultServiceImpl\",\"METHOD\":\"addFault\"}";
		String obj2 = "{\"MACHINEID\":\""+fault.getMachineid()+"\",\"TYPEID\":\""+fault.getTypeid()+"\",\"CODEID\":\""+fault.getCodeid()+"\","
				+ "\"TIME\":\""+fault.getTime()+"\",\"INSFID\":\""+fault.getItemid()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
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

  @RequestMapping("/editFault")
  @ResponseBody
  public String editFault(HttpServletRequest request,@ModelAttribute("fault") Fault fault){
    JSONObject obj = new JSONObject();
    try{
		//当前层级
		String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
		//获取项目层url
		String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
		//获取公司发布地址
		String companyurl = im.webserviceDto(request, fault.getItemid());
		//客户端执行操作
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(companyurl);
		iutil.Authority(client);
		String obj1 = "{\"CLASSNAME\":\"faultServiceImpl\",\"METHOD\":\"editFault\"}";
		String obj2 = "{\"ID\":\""+fault.getId()+"\",\"MACHINEID\":\""+fault.getMachineid()+"\",\"TYPEID\":\""+fault.getTypeid()+"\",\"CODEID\":\""+fault.getCodeid()+"\","
				+ "\"TIME\":\""+fault.getTime()+"\",\"INSFID\":\""+fault.getItemid()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
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
  
  @RequestMapping("/removeFault")
  @ResponseBody
  public String removeFault(HttpServletRequest request,@RequestParam String id,@RequestParam String insfid){
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
	    String obj1 = "{\"CLASSNAME\":\"faultServiceImpl\",\"METHOD\":\"deleteFault\"}";
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
      obj.put("success", true);
      obj.put("errorMsg", e.getMessage());
    }
    return obj.toString();
  }
  

  /**
   * 获取故障类型
   * @return
   */
  @RequestMapping("/getTypeAll")
  @ResponseBody
  public String getTypeAll(int num){
    JSONObject json = new JSONObject();
    JSONArray ary = new JSONArray();
    JSONObject obj = new JSONObject();
    try{
      List<Dictionarys> dictionary = dm.getDictionaryValue(num);
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
}