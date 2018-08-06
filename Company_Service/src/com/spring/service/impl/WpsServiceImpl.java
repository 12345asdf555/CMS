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
import com.spring.dao.WpsMapper;
import com.spring.dto.JudgeUtil;
import com.spring.model.Wps;
import com.spring.service.WpsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
@Transactional  
public class WpsServiceImpl implements WpsService {
	@Autowired
	private WpsMapper wm;

	private JudgeUtil jutil = new JudgeUtil();
	
	@Override
	public Object findAll(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			String parentid = json.getString("ID");
			BigInteger parent = null;
			if(parentid!=null && parentid!=""){
				parent = new BigInteger(parentid);
			}
			List<Wps> list = wm.findAll(parent, json.getString("STR"));
			for(int i=0;i<list.size();i++){
				obj.put("ID", jutil.setValue(list.get(i).getFid()));
				obj.put("WPSNO",jutil.setValue(list.get(i).getFwpsnum()));
				obj.put("ELECTRICITY",jutil.setValue(list.get(i).getFweld_i()));
				obj.put("VALTAGE",jutil.setValue(list.get(i).getFweld_v()));
				obj.put("MAXELECTRICITY",jutil.setValue(list.get(i).getFweld_i_max()));
				obj.put("MINELECTRICITY",jutil.setValue(list.get(i).getFweld_i_min()));
				obj.put("MAXVALTAGE",jutil.setValue(list.get(i).getFweld_v_max()));
				obj.put("MINVALTAGE",jutil.setValue(list.get(i).getFweld_v_min()));
				obj.put("ALERTELECTRICITY",jutil.setValue(list.get(i).getFweld_alter_i()));
				obj.put("ALERTVALTAGE",jutil.setValue(list.get(i).getFweld_alter_v()));
				obj.put("PRECHANNEL",jutil.setValue(list.get(i).getFweld_prechannel()));
				obj.put("INSFNAME",jutil.setValue(list.get(i).getInsname()));
				obj.put("INSFID",jutil.setValue(list.get(i).getInsid()));
				obj.put("BACK",jutil.setValue(list.get(i).getFback()));
				obj.put("NAME",jutil.setValue(list.get(i).getFname()));
				obj.put("DIAMETER",jutil.setValue(list.get(i).getFdiameter()));
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object save(String obj1, String obj2) {
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
			Wps w = new Wps();
			w.setFid(id);
			w.setFwpsnum(json.getString("WPSNO"));
			w.setFweld_prechannel(json.getInt("PRECHANNEL"));
			w.setFweld_alter_i(json.getInt("ALERTELECTRICITY"));
			w.setFweld_alter_v(json.getInt("ALERTVALTAGE"));
			w.setFweld_i(json.getInt("ELECTRICITY"));
			w.setFweld_v(json.getInt("VALTAGE"));
			w.setFweld_i_max(json.getInt("MAXELECTRICITY"));
			w.setFweld_i_min(json.getInt("MINELECTRICITY"));
			w.setFweld_v_max(json.getInt("MAXVALTAGE"));
			w.setFweld_v_min(json.getInt("MINVALTAGE"));
			w.setFname(json.getString("NAME"));
			w.setFdiameter(json.getInt("DIAMETER"));
			w.setInsid(new BigInteger(json.getString("INSFID")));
			w.setFback(json.getString("BACK"));
			w.setFcreater(json.getLong("CREATOR"));
			boolean flag = wm.save(w);
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
	public Object update(String obj1, String obj2) {
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
			Wps w = new Wps();
			w.setFid(new BigInteger(json.getString("ID")));
			w.setFwpsnum(json.getString("WPSNO"));
			w.setFweld_prechannel(json.getInt("PRECHANNEL"));
			w.setFweld_alter_i(json.getInt("ALERTELECTRICITY"));
			w.setFweld_alter_v(json.getInt("ALERTVALTAGE"));
			w.setFweld_i(json.getInt("ELECTRICITY"));
			w.setFweld_v(json.getInt("VALTAGE"));
			w.setFweld_i_max(json.getInt("MAXELECTRICITY"));
			w.setFweld_i_min(json.getInt("MINELECTRICITY"));
			w.setFweld_v_max(json.getInt("MAXVALTAGE"));
			w.setFweld_v_min(json.getInt("MINVALTAGE"));
			w.setFname(json.getString("NAME"));
			w.setFdiameter(json.getInt("DIAMETER"));
			w.setFupdater(json.getLong("MODIFIER"));
			w.setInsid(new BigInteger(json.getString("INSFID")));
			w.setFback(json.getString("BACK"));
			boolean flag = wm.update(w);
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
	public int getUsernameCount(String object) {
		try {
			JSONObject json = JSONObject.fromObject(object);
			return wm.getUsernameCount(json.getString("WPSNO"));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Object findById(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			Wps list = wm.findById(new BigInteger(json.getString("ID")));
			if(list!=null){
				obj.put("ID", jutil.setValue(list.getFid()));
				obj.put("WPSNO",jutil.setValue(list.getFwpsnum()));
				obj.put("ELECTRICITY",jutil.setValue(list.getFweld_i()));
				obj.put("VALTAGE",jutil.setValue(list.getFweld_v()));
				obj.put("MAXELECTRICITY",jutil.setValue(list.getFweld_i_max()));
				obj.put("MINELECTRICITY",jutil.setValue(list.getFweld_i_min()));
				obj.put("MAXVALTAGE",jutil.setValue(list.getFweld_v_max()));
				obj.put("MINVALTAGE",jutil.setValue(list.getFweld_v_min()));
				obj.put("ALERTELECTRICITY",jutil.setValue(list.getFweld_alter_i()));
				obj.put("ALERTVALTAGE",jutil.setValue(list.getFweld_alter_v()));
				obj.put("PRECHANNEL",jutil.setValue(list.getFweld_prechannel()));
				obj.put("INSFNAME",jutil.setValue(list.getInsname()));
				obj.put("INSFID",jutil.setValue(list.getInsid()));
				obj.put("BACK",jutil.setValue(list.getFback()));
				obj.put("NAME",jutil.setValue(list.getFname()));
				obj.put("DIAMETER",jutil.setValue(list.getFdiameter()));
			}
			return JSON.toJSONString(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object delete(String obj1, String obj2) {
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
			boolean flag = wm.delete(new BigInteger(json.getString("ID")));
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
