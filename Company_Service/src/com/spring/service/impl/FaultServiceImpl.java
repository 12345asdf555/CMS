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
import com.spring.dao.FaultMapper;
import com.spring.dto.JudgeUtil;
import com.spring.model.Fault;
import com.spring.service.FaultService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class FaultServiceImpl implements FaultService {
	@Autowired
	private FaultMapper fm;
	
	private JudgeUtil jutil = new JudgeUtil();

	@Override
	public Object getFaultAll(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			List<Fault> list = fm.getFaultAll(json.getString("STR"));
			for(int i=0;i<list.size();i++){
				obj.put("ID", jutil.setValue(list.get(i).getId()));
				obj.put("CODE",jutil.setValue(list.get(i).getCode()));
				obj.put("TYPEID",jutil.setValue(list.get(i).getType()));
				obj.put("TYPENAME",jutil.setValue(list.get(i).getValuename()));
				obj.put("DESC",jutil.setValue(list.get(i).getDesc()));
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object getFaultById(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			Fault list = fm.getFaultById(new BigInteger(json.getString("ID")));
			if(list!=null){
				obj.put("ID", jutil.setValue(list.getId()));
				obj.put("CODE",jutil.setValue(list.getCode()));
				obj.put("TYPEID",jutil.setValue(list.getType()));
				obj.put("TYPENAME",jutil.setValue(list.getValuename()));
				obj.put("CREATOR",jutil.setValue(list.getCreator()));
				obj.put("DESC",jutil.setValue(list.getDesc()));
			}
			return JSON.toJSONString(obj);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object addFault(String obj1, String obj2) {
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
			String itemurl = "";
			boolean exists = false;
			if(json.getString("INSFID")!=null && !"".equals(json.getString("INSFID"))){
				BigInteger insfid = new BigInteger(json.getString("INSFID"));
				itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
				exists = true;
			}
			Fault f = new Fault();
			f.setId(id);
			f.setCode(json.getString("CODE"));
			f.setDesc(json.getString("DESC"));
			f.setType(json.getInt("TYPEID"));
			f.setCreator(json.getString("CREATOR"));
			boolean flag = fm.addFault(f);
			String result = "false";
			if(flag){
				//为项目时向项目执行插入
				if(exists){
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
					return true;
				}
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Object editFault(String obj1, String obj2) {
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
			String itemurl = "";
			boolean exists = false;
			if(json.getString("INSFID")!=null && !"".equals(json.getString("INSFID"))){
				BigInteger insfid = new BigInteger(json.getString("INSFID"));
				itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
				exists = true;
			}
			Fault f = new Fault();
			f.setId(new BigInteger(json.getString("ID")));
			f.setCode(json.getString("CODE"));
			f.setDesc(json.getString("DESC"));
			f.setType(json.getInt("TYPEID"));
			f.setModifier(json.getString("MODIFIER"));
			boolean flag = fm.editFault(f);
			String result = "false";
			if(flag && blocResult.equals("true")){
				if(exists){
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
					return true;
				}
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Object deleteFault(String obj1, String obj2) {
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
			String itemurl = "";
			boolean exists = false;
			if(json.getString("INSFID")!=null && !"".equals(json.getString("INSFID"))){
				BigInteger insfid = new BigInteger(json.getString("INSFID"));
				itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
				exists = true;
			}
			boolean flag = fm.deleteFault(new BigInteger(json.getString("ID")));
			String result = "false";
			if(flag &&  blocResult.equals("true")){
				if(exists){
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
					return true;
				}
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
