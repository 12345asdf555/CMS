package com.greatway.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

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

import com.github.pagehelper.PageInfo;
import com.greatway.dto.WeldDto;
import com.greatway.manager.DictionaryManager;
import com.greatway.manager.InsframeworkManager;
import com.greatway.model.Dictionarys;
import com.greatway.model.Insframework;
import com.greatway.page.Page;
import com.greatway.util.IsnullUtil;
import com.spring.model.MyUser;
import com.spring.service.TdService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/insframework", produces = { "text/json;charset=UTF-8" })
public class InsframeworkController {
	private Page page;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int total = 0;
	private BigInteger value3;
	
	@Autowired
	private TdService tdService;
	
	@Autowired
	private InsframeworkManager im;
	
	@Autowired
	private DictionaryManager dm;
	
	IsnullUtil iutil = new IsnullUtil();
	
	/**
	 * 跳转组织机构页面
	 * @return
	 */
	@RequestMapping("/goInsframework")
	public String goInsframework(){
		return "insframework/insframework";
	}
	
	/**
	 * 跳转新增页面
	 * @return
	 */
	@RequestMapping("/goaddInsframework")
	public String goaddInsframework(){
		return "insframework/addinsframework";
	}
	
	/**
	 * 跳转修改页面
	 * @return
	 */
	@RequestMapping("/goeditInsframework")
	public String goeditInsframework(HttpServletRequest request,@RequestParam String id){
		Insframework insf = im.getInsfAllById(new BigInteger(id));
		request.setAttribute("parent", im.getInsframeworkById(insf.getParent()));
		request.setAttribute("insf", insf);
		return "insframework/editinsframework";
	}
	
	/**
	 * 跳转删除页面
	 * @return
	 */
	@RequestMapping("/goremoveInsframework")
	public String goremoveInsframework(HttpServletRequest request,@RequestParam String id){
		Insframework insf = im.getInsfAllById(new BigInteger(id));
		request.setAttribute("parent", im.getInsframeworkById(insf.getParent()));
		request.setAttribute("insf", insf);
		request.setAttribute("type", request.getParameter("type"));
		return "insframework/removeinsframework";
	}
	
	@RequestMapping("/getInsframeworkList")
	@ResponseBody
	public String getWeldingMachine(HttpServletRequest request){
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String searchStr = request.getParameter("searchStr");
		String parentId = request.getParameter("parent");
		request.getSession().setAttribute("searchStr", searchStr);
		BigInteger parent = null;
		WeldDto dto = new WeldDto();
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
			int type = im.getTypeById(parent);
			if(type==20){
				dto.setBloc("bloc");
			}else if(type==21){
				dto.setCompany("company");
			}else if(type==22){
				dto.setCaust("caust");
			}else if(type==23){
				dto.setItem("item");
			}
		}
		page = new Page(pageIndex,pageSize,total);
		
		List<Insframework> list = im.getInsframeworkAll(page, parent,searchStr,dto);
		long total = 0;
		
		if(list != null){
			PageInfo<Insframework> pageinfo = new PageInfo<Insframework>(list);
			total = pageinfo.getTotal();
		}
		
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			for(Insframework i:list){
				json.put("id", i.getId());
				json.put("name", i.getName());
				json.put("logogram", i.getLogogram());
				json.put("code", i.getCode());
				json.put("parent", i.getParentname());
				json.put("type", i.getTypename());
				json.put("typeid", i.getType());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/addInsframework")
	@ResponseBody
	public String addInsframework(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		try{
			//当前层级
			String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
			//获取当前用户
			Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyUser myuser = (MyUser)object;
			String webserviceurl = "";
			String type = request.getParameter("type");
			String parent = request.getParameter("parent");
			if(hierarchy.equals("1")){
				if(type.equals("21")){
					//集团层插入公司无须同步下去
					webserviceurl = request.getSession().getServletContext().getInitParameter("blocurl");
				}else if(type.equals("22")){
					webserviceurl = request.getSession().getServletContext().getInitParameter(parent);
				}else if(type.equals("23")){
					Insframework companyid = im.getParent(new BigInteger(parent));
					webserviceurl = request.getSession().getServletContext().getInitParameter(companyid.getId().toString());
				} 
			}else if(hierarchy.equals("2") || hierarchy.equals("3")){
				webserviceurl = request.getSession().getServletContext().getInitParameter("companyurl");
			}else{
				obj.put("success", false);
				obj.put("errorMsg", "您没有新增组织机构的权限！");
				return obj.toString();
			}
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(webserviceurl);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"insfWebServiceImpl\",\"METHOD\":\"addInsframework\"}";
			String obj2 = "{\"NAME\":\""+request.getParameter("name")+"\",\"LOGOGRAM\":\""+request.getParameter("logogram")+"\",\"CODE\":\""+request.getParameter("code")+"\","
					+ "\"PARENT\":\""+parent+"\",\"TYPEID\":\""+type+"\",\"CREATOR\":\""+myuser.getId()+"\"}";
			Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{obj1,obj2});
			if(hierarchy.equals("1")){
				if(objects[0].toString()!=null && !"".equals(objects[0].toString())){
					obj.put("success", true);
				}else{
					obj.put("success", false);
					obj.put("errorMsg", "操作失败！");
				}
			}else{
				if(objects[0].toString().equals("true")){
					obj.put("success", true);
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
	
	@RequestMapping("/editInsframework")
	@ResponseBody
	public String editInsframework(HttpServletRequest request, @RequestParam String id){
		JSONObject obj = new JSONObject();
		try{
			//当前层级
			String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
			//获取当前用户
			Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyUser myuser = (MyUser)object;
			//获取项目层url
			String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
			String webserviceurl = "";
			String type = request.getParameter("type");
			String parent = request.getParameter("parent");
			if(hierarchy.equals("1")){
				if(type.equals("20")){//集团层编辑自己
					webserviceurl = request.getSession().getServletContext().getInitParameter("blocurl");
				}else if(type.equals("21")){//集团层编辑公司
					webserviceurl = request.getSession().getServletContext().getInitParameter(id);
				}else if(type.equals("22")){
					webserviceurl = request.getSession().getServletContext().getInitParameter(parent);
				}else if(type.equals("23")){
					Insframework companyid = im.getParent(new BigInteger(parent));
					webserviceurl = request.getSession().getServletContext().getInitParameter(companyid.getId().toString());
				} 
			}else{
				webserviceurl = request.getSession().getServletContext().getInitParameter("companyurl");
			}
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(webserviceurl);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"insfWebServiceImpl\",\"METHOD\":\"editInsframework\"}";
			String obj2 = "{\"INSFID\":\""+id+"\",\"NAME\":\""+request.getParameter("name")+"\",\"LOGOGRAM\":\""+request.getParameter("logogram")+"\",\"CODE\":\""+request.getParameter("code")+"\","
					+ "\"PARENT\":\""+request.getParameter("parent")+"\",\"TYPEID\":\""+request.getParameter("type")+"\",\"MODIFIER\":\""+myuser.getId()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
			Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{obj1,obj2});  
			if(hierarchy.equals("1")){
				if(objects[0].toString()!=null && !"".equals(objects[0].toString())){
					obj.put("success", true);
				}else{
					obj.put("success", false);
					obj.put("errorMsg", "操作失败！");
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
	
	@RequestMapping("/removeInsframework")
	@ResponseBody
	public String removeInsframework(HttpServletRequest request,@RequestParam String id,@RequestParam String type){
		JSONObject obj = new JSONObject();
		try{
			//当前层级
			String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
			//获取项目层url
			String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
			String webserviceurl = "";
			String parent = request.getParameter("parent");
			if(hierarchy.equals("1")){
				if(type.equals("20")){//集团层编辑自己
					webserviceurl = request.getSession().getServletContext().getInitParameter("blocurl");
				}else if(type.equals("21")){//集团层编辑公司
					webserviceurl = request.getSession().getServletContext().getInitParameter(id);
				}else if(type.equals("22")){
					webserviceurl = request.getSession().getServletContext().getInitParameter(parent);
				}else if(type.equals("23")){
					Insframework companyid = im.getParent(new BigInteger(parent));
					webserviceurl = request.getSession().getServletContext().getInitParameter(companyid.getId().toString());
				} 
			}else{
				webserviceurl = request.getSession().getServletContext().getInitParameter("companyurl");
			}
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(webserviceurl);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"insfWebServiceImpl\",\"METHOD\":\"deleteInsframework\"}";
			String obj2 = "{\"INSFID\":\""+id+"\",\"TYPE\":\""+type+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
			Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{obj1,obj2});  
			if(hierarchy.equals("1")){
				if(objects[0].toString()!=null && !"".equals(objects[0].toString())){
					obj.put("success", true);
				}else{
					obj.put("success", false);
					obj.put("errorMsg", "操作失败！");
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
			e.printStackTrace();
			obj.put("success", false);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
	}
	
	/**
	 * 获取父节点
	 * @return
	 */
	@RequestMapping("/getParent")
	@ResponseBody
	public String getParent(HttpServletRequest request){
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONArray arys = new JSONArray();
		JSONObject jsons = new JSONObject();
		try{
			String id = request.getParameter("id");
			int type = Integer.parseInt(request.getParameter("type"));
			//获取用户id
			Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyUser myuser = (MyUser)object;
			List<Insframework> insparent = im.getInsByUserid(new BigInteger(myuser.getId()+""));
			List<Insframework> instype = im.getInsByUserid(new BigInteger(myuser.getId()+""));
			List<Insframework> ins = null;
			for(Insframework i:insparent){
				if(i.getType()==20){
					ins = im.getInsAll(23);
				}else if(i.getType()==21){
					ins = im.getInsIdByParent(i.getId(),23);
					Insframework insf = im.getInsById(i.getId());
					ins.add(ins.size(),insf);
					if(type==21){
						Insframework insframework = im.getBloc();
						ins.add(ins.size(),insframework);
					}
				}else if(i.getType()==22){
					ins = im.getInsIdByParent(i.getId(),23);
					if(type==22){
						Insframework insf = new Insframework();
						BigInteger parent = im.getParentById(instype.get(0).getId());
						insf.setId(parent);
						insf.setName(im.getInsframeworkById(parent));
						ins.add(ins.size(),insf);
					}
				}else{
					ins = im.getInsIdByParent(i.getId(),23);
					if(type==23){
						Insframework insf = new Insframework();
						BigInteger parent = im.getParentById(instype.get(0).getId());
						insf.setId(parent);
						insf.setName(im.getInsframeworkById(parent));
						ins.add(ins.size(),insf);
					}
				}
			}

			if(type==20){
				json.put("id", 0);
				json.put("name", "无");
				ary.add(json);
			}else{
				for(Insframework in:ins){
					if(!in.getId().toString().equals(id)){
						json.put("id", in.getId());
						json.put("name", in.getName());
						ary.add(json);
					}
				}
			}
			List<Dictionarys> dictionary = null;
			//获取枚举值
			for(Insframework i:instype){
				if(i.getType()==20){
					dictionary = dm.getDicValueByValue(2, 20);
				}else if(i.getType()==21){
					if(i.getType()==type){
						dictionary = dm.getDicValueByValue(2, 20);
					}else{
						dictionary = dm.getDicValueByValue(2, 21);
					}
				}else{
					if(type==22){
						dictionary = dm.getDicValueByValue(2, 21);
					}else{
						dictionary = dm.getDicValueByValue(2, 22);
					}
				}
			}
			if(type==20){
				String valuename = dm.getDicValueByType(type);
				jsons.put("id", type);
				jsons.put("name", valuename);
				arys.add(jsons);
			}else{
				for(Dictionarys d:dictionary){
					jsons.put("id", d.getValue());
					jsons.put("name", d.getValueName());
					arys.add(jsons);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("ary", ary);
		obj.put("arys", arys);
		return obj.toString();
	}
	
	/**
	 * 获取登录者组织机构id
	 * @return
	 */
	@RequestMapping("/getUserInsfid")
	@ResponseBody
	public String getType(){
		JSONObject obj = new JSONObject();
		try{
			//获取用户id
			Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyUser myuser = (MyUser)object;
			List<Insframework> ins = im.getInsByUserid(new BigInteger(myuser.getId()+""));
			if(ins.get(0).getType()==23){
				obj.put("flag",false);
			}else{
				obj.put("flag",true);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj.toString();
	}
	
	/**
	 * 校验项目名称是否存在
	 * @param name
	 * @return
	 */
	@RequestMapping("/insfdValidate")
	@ResponseBody
	public String insfidValidate(@RequestParam String name){
		boolean flag = true;
		int count = im.getInsframeworkNameCount(name);
		if(count > 0){
			flag = false;
		}
		return flag + "";
	}
	
	/**
	 * 组织机构树形菜单
	 * @param name
	 * @return
	 */
	@RequestMapping("/getConmpany")
	@ResponseBody
	public void getConmpany(HttpServletResponse response){
		MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
			    .getAuthentication()  
			    .getPrincipal();
		long uid = myuser.getId();
		int dic=tdService.findDic(uid);
		BigInteger value1;
		BigInteger value2;
		
		if(dic==20){
			value1=null;
			value2=null;
			value3=null;
		}else if(dic==21){
			value1=im.getUserInsfId(BigInteger.valueOf(uid));
			value2=null;
			value3=null;
		}else if(dic==22){
			value1=im.getParent(im.getUserInsfId(BigInteger.valueOf(uid))).getId();
			value2=im.getUserInsfId(BigInteger.valueOf(uid));
			value3=null;
		}else{
			value1=im.getParent(im.getParent(im.getUserInsfId(BigInteger.valueOf(uid))).getId()).getId();
			value2=im.getParent(im.getUserInsfId(BigInteger.valueOf(uid))).getId();
			value3=im.getUserInsfId(BigInteger.valueOf(uid));
		}
        String str ="";  
        StringBuilder json = new StringBuilder();  
        // 拼接根节点  
        Insframework b = im.getBloc();
        if(b!=null){  
	        json.append("[");  
	        json.append("{\"id\":" +b.getId());
	        json.append(",\"text\":\"" +b.getName()+ "\"");
	        json.append(",\"state\":\"open\"");  
	        // 获取根节点下的所有子节点  
	        List<Insframework> treeList = im.getConmpany(value1);
	        // 遍历子节点下的子节点  
	        if(treeList!=null && treeList.size()!=0){  
	            json.append(",\"children\":[");  
	            for (Insframework t : treeList) {  
	                  
	                json.append("{\"id\":" +String.valueOf(t.getId()));   
	                json.append(",\"text\":\"" +t.getName() + "\"");   
	                json.append(",\"state\":\"open\"");   
	                  
	                // 该节点有子节点  
	                // 设置为关闭状态,而从构造异步加载tree  
	              
	                List<Insframework> tList = im.getCause(t.getId(),value2);  
	                if(tList!=null && tList.size()!=0){// 存在子节点  
	                     json.append(",\"children\":[");  
	                     json.append(dealJsonFormat(tList));// 存在子节点的都放在一个工具类里面处理了
	                     json.append("]");  
	                }  
	                json.append("},");  
	            }  
	            str = json.toString();  
	            str = str.substring(0, str.length()-1);  
	            str+="]}]";
	        }
              
        }  
        try {
            response.getWriter().print(str);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	public String dealJsonFormat(List<Insframework> tList){  
        StringBuilder json = new StringBuilder();  
        for (Insframework tree : tList) {  
            json.append("{\"id\":" +String.valueOf(tree.getId()));   
            json.append(",\"text\":\"" +tree.getName() + "\"");   
            json.append(",\"state\":\"open\"");
            
            // 获取根节点下的所有子节点  
            List<Insframework> treeLists = im.getCause(tree.getId(),value3);
            // 遍历子节点下的子节点  
            if(treeLists!=null && treeLists.size()!=0){  
                json.append(",\"children\":["); 
                json.append(dealJsonFormat2(treeLists));// 存在子节点的都放在一个工具类里面处理
                json.append("]");  
            }  
            json.append("},");  
        }  
        String str = json.toString();  
        str = str.substring(0, str.length()-1);
        return str;  
    } 
	
	public String dealJsonFormat2(List<Insframework> treeLists){  
        StringBuilder json = new StringBuilder();  
        for (Insframework tree : treeLists) {  
            json.append("{\"id\":" +String.valueOf(tree.getId()));   
            json.append(",\"text\":\"" +tree.getName() + "\"");   
            json.append(",\"state\":\"open\"");
            json.append("},");  
        }  
        String str = json.toString();  
        str = str.substring(0, str.length()-1); 
        return str;  
    } 
	
	/**
	 * 判断用户操作权限
	 * @param request
	 * @param id 操作节点id
	 * @return
	 */
	@RequestMapping("/getUserAuthority")
	@ResponseBody
	public String getUserAuthority(HttpServletRequest request,BigInteger id){
		JSONObject obj = new JSONObject();
		obj.put("flag", false);
		obj.put("afreshLogin", "");
		try{
			//获取用户id
			Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyUser myuser = (MyUser)object;
			List<Insframework> ins = im.getInsByUserid(new BigInteger(myuser.getId()+""));
			for(Insframework i:ins){
				//如果用户为集团层或点击的是自己的层级时不做判断
				if(i.getType()==20 || id.equals(i.getId())){
					obj.put("flag", true);
				}else{
					List<Insframework> list = im.getInsIdByParent(i.getId(),0);
					for(Insframework insf:list){
						if(id.equals(insf.getId())){
							obj.put("flag",true);
							break;
						}
					}
				}
			}
			return obj.toString();
		}catch(Exception e){
			obj.put("afreshLogin", "您的Session已过期，请重新登录！");
			return obj.toString();
		}
	}
	
	@RequestMapping("/getUserAdd")
	@ResponseBody
	public String getUserAdd(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		obj.put("flag", false);
		obj.put("afreshLogin", "");
		try{
			BigInteger nodeId = new BigInteger(request.getParameter("nodeId"));
			//获取用户id
			Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyUser myuser = (MyUser)object;
			List<Insframework> ins = im.getInsByUserid(new BigInteger(myuser.getId()+""));
			for(Insframework i:ins){
				//如果用户为集团层或点击的是自己的层级时不做判断
				if(i.getType()==20 || nodeId.equals(i.getId())){
					obj.put("flag", true);
				}else{
					List<Insframework> list = im.getInsIdByParent(i.getId(),0);
					for(Insframework insf:list){
						if(nodeId.equals(insf.getId())){
							obj.put("flag",true);
							break;
						}
					}
				}
			}
			return obj.toString();
		}catch(Exception e){
			obj.put("afreshLogin", "您的Session已过期，请重新登录！");
			return obj.toString();
		}
	}
}
