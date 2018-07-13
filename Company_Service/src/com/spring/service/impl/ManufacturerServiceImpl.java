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
import com.spring.dao.EquipmentManufacturerMapper;
import com.spring.dto.JudgeUtil;
import com.spring.model.EquipmentManufacturer;
import com.spring.service.ManufacturerService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class ManufacturerServiceImpl implements ManufacturerService {
	@Autowired
	private  EquipmentManufacturerMapper em;
	
	private JudgeUtil jutil = new JudgeUtil();

	@Override
	public Object getmanuAll(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			List<EquipmentManufacturer> list = em.getmanuAll(json.getString("STR"));
			for(int i=0;i<list.size();i++){
				obj.put("ID", jutil.setValue(list.get(i).getId()));
				obj.put("NAME",jutil.setValue(list.get(i).getName()));
				obj.put("TYPEID",jutil.setValue(list.get(i).getTypeid()));
				obj.put("TYPENAME",jutil.setValue(list.get(i).getType()));
				obj.put("TYPEVALUE",jutil.setValue(list.get(i).getTypeValue()));
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public BigInteger getManuidByValue(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return em.getManuidByValue(json.getString("NAME"), json.getString("TYPENAME"));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getManuCount(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return em.getManuCount(json.getString("NAME"), json.getInt("TYPEID"));
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Object getManuById(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			EquipmentManufacturer list = em.getManuById(new BigInteger(json.getString("ID")));
			if(list!=null){
				obj.put("ID", jutil.setValue(list.getId()));
				obj.put("NAME",jutil.setValue(list.getName()));
				obj.put("TYPEID",jutil.setValue(list.getTypeid()));
				obj.put("TYPENAME",jutil.setValue(list.getType()));
				obj.put("TYPEVALUE",jutil.setValue(list.getTypeValue()));
				obj.put("CREATOR",jutil.setValue(list.getCreator()));
			}
			return JSON.toJSONString(obj);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object addManu(String obj1, String obj2) {
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
			EquipmentManufacturer e = new EquipmentManufacturer();
			e.setId(id);
			e.setName(json.getString("NAME"));
			e.setType(json.getString("TYPEID"));
			e.setTypeValue(json.getString("TYPEVALUE"));
			e.setCreator(json.getString("CREATOR"));
			boolean flag = em.addManu(e);
			String result = "false";
			if(flag){
				if(exists){
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
	public Object editManu(String obj1, String obj2) {
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
			EquipmentManufacturer e = new EquipmentManufacturer();
			e.setId(new BigInteger(json.getString("ID")));
			e.setName(json.getString("NAME"));
			e.setType(json.getString("TYPEID"));
			e.setTypeValue(json.getString("TYPEVALUE"));
			e.setModifier(json.getString("MODIFIER"));
			boolean flag = em.editManu(e);
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
	public Object deleteManu(String obj1, String obj2) {
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
			boolean flag = em.deleteManu(new BigInteger(json.getString("ID")));
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
