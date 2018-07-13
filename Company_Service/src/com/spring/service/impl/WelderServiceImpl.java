package com.spring.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.context.WebServiceContextImpl;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.spring.dao.WelderMapper;
import com.spring.dto.JudgeUtil;
import com.spring.model.Welder;
import com.spring.service.WelderService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Transactional
@Service
public class WelderServiceImpl implements WelderService {
	@Autowired
	private WelderMapper wm;
	
	private JudgeUtil jutil = new JudgeUtil();
	
	@Override
	public Object getWelderAll(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			List<Welder> list = wm.getWelderAll(json.getString("STR"));
			for(int i=0;i<list.size();i++){
				obj.put("ID", jutil.setValue(list.get(i).getId()));
				obj.put("NAME",jutil.setValue(list.get(i).getName()));
				obj.put("WELDERNO",jutil.setValue(list.get(i).getWelderno()));
				obj.put("ITEMID",jutil.setValue(list.get(i).getIid()));
				obj.put("ITEMNAME",jutil.setValue(list.get(i).getIname()));
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object addWelder(String obj1, String obj2) {
		try{
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			//向集团层执行插入
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client blocclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("blocurl"));
			jutil.Authority(blocclient);
			Object[] blocobj = blocclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});
			BigInteger id = new BigInteger(blocobj[0].toString());
			JSONObject json = JSONObject.fromObject(obj2);
			//获取层级id
			String hierarchy = json.getString("HIERARCHY");
			String itemurl = "";
			if(hierarchy.equals("4")){
				itemurl = json.getString("ITEMURL");
			}else{
				BigInteger insfid = new BigInteger(json.getString("INSFID"));
				itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
			}
			Welder we = new Welder();
			we.setId(id);
			we.setName(json.getString("NAME"));
			we.setWelderno(json.getString("WELDERNO"));
			we.setIid(new BigInteger(json.getString("INSFID")));
			we.setCreator(json.getString("CREATOR"));
			boolean flag = wm.addWelder(we);
			String result = "false";
			if(flag){
				//向项目执行插入
				if(itemurl!=null && !"".equals(itemurl)){
					Client itemclient = dcf.createClient(itemurl);
					jutil.Authority(itemclient);
					obj2 = obj2.substring(0,obj2.length()-1)+",\"ID\":\""+id+"\"}";
					Object[] itemobj = itemclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});
					result = itemobj[0].toString();
				}else{
					return "未找到该项目部，请检查网络连接情况或是否部署服务";
				}
				return result;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Object editWelder(String obj1, String obj2) {
		try{
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			//向集团层执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client blocclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("blocurl"));
			jutil.Authority(blocclient);
			Object[] blocobj = blocclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});  
			String blocResult = blocobj[0].toString();
			JSONObject json = JSONObject.fromObject(obj2);
			//获取层级id
			String hierarchy = json.getString("HIERARCHY");
			String itemurl = "";
			if(hierarchy.equals("4")){
				itemurl = json.getString("ITEMURL");
			}else{
				BigInteger insfid = new BigInteger(json.getString("INSFID"));
				itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
			}
			Welder we = new Welder();
			we.setId(new BigInteger(json.getString("ID")));
			we.setName(json.getString("NAME"));
			we.setWelderno(json.getString("WELDERNO"));
			we.setIid(new BigInteger(json.getString("INSFID")));
			we.setModifier(json.getString("MODIFIER"));
			boolean flag = wm.editWelder(we);
			String result = "false";
			if(flag && blocResult.equals("true")){
				if(itemurl!=null && !"".equals(itemurl)){//向项目执行操作
					Client itemclient = dcf.createClient(itemurl);
					jutil.Authority(itemclient);
					Object[] itemobj = itemclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});
					result = itemobj[0].toString();
				}else{
					return "未找到该项目部，请检查网络连接情况或是否部署服务";
				}
				return result;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Object removeWelder(String obj1, String obj2) {
		try{
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			//向集团层执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client blocclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("blocurl"));
			jutil.Authority(blocclient);
			Object[] blocobj = blocclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});  
			String blocResult = blocobj[0].toString();
			JSONObject json = JSONObject.fromObject(obj2);
			//获取层级id
			String hierarchy = json.getString("HIERARCHY");
			String itemurl = "";
			if(hierarchy.equals("4")){
				itemurl = json.getString("ITEMURL");
			}else{
				BigInteger insfid = new BigInteger(json.getString("INSFID"));
				itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
			}
			boolean flag = wm.removeWelder(new BigInteger(json.getString("ID")));
			String result = "false";
			if(flag &&  blocResult.equals("true")){
				if(itemurl!=null && !"".equals(itemurl)){
					//向项目执行操作
					Client itemclient = dcf.createClient(itemurl);
					jutil.Authority(itemclient);
					Object[] itemobj = itemclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});
					result = itemobj[0].toString();
				}else{
					return "未找到该项目部，请检查网络连接情况或是否部署服务";
				}
				return result;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int getWeldernoCount(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return wm.getWeldernoCount(json.getString("WELDERNO"));
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Object getWelderById(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			Welder list = wm.getWelderById(new BigInteger(json.getString("ID")));
			if(list!=null){
				obj.put("ID", jutil.setValue(list.getId()));
				obj.put("NAME",jutil.setValue(list.getName()));
				obj.put("WELDERNO",jutil.setValue(list.getWelderno()));
				obj.put("ITEMNAME",jutil.setValue(list.getIname()));
			}
			return JSON.toJSONString(obj);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
