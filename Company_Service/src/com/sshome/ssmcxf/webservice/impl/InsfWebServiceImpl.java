package com.sshome.ssmcxf.webservice.impl;

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
import com.spring.dto.JudgeUtil;
import com.spring.model.Insframework;
import com.spring.service.InsframeworkService;
import com.sshome.ssmcxf.webservice.InsfWebService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Transactional
@Service
public class InsfWebServiceImpl implements InsfWebService {
	@Autowired
	private InsframeworkService is;
	
	private JudgeUtil jutil = new JudgeUtil();
	
	@Override
	public Object getInsframeworkAll(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			List<Insframework> list = is.getInsframeworkAll(new BigInteger(json.getString("INSFID")), json.getString("STR"));
			for(int i=0;i<list.size();i++){
				obj.put("ID", list.get(i).getId());
				obj.put("NAME",list.get(i).getName());
				obj.put("LOGOGRAM",jutil.setValue(list.get(i).getLogogram()));
				obj.put("CODE",jutil.setValue(list.get(i).getCode()));
				obj.put("PARENTID",jutil.setValue(list.get(i).getParent()));
				obj.put("PARENTNAME",jutil.setValue(list.get(i).getParentname()));
				obj.put("TYPEID",jutil.setValue(list.get(i).getType()));
				obj.put("TYPENAME",jutil.setValue(list.get(i).getTypename()));
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean addInsframework(String obj1,String obj2) {
		try{
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			//向集团层执行插入
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client blocclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("blocurl"));
			jutil.Authority(blocclient);
			Object[] blocobj = blocclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{obj1,obj2});  
			BigInteger id = new BigInteger(blocobj[0].toString());
			JSONObject json = JSONObject.fromObject(obj2);
			Insframework i = new Insframework();
			i.setId(id);
			i.setName(json.getString("NAME"));
			i.setLogogram(json.getString("LOGOGRAM"));
			i.setCode(json.getString("CODE"));
			i.setParent(new BigInteger(json.getString("PARENT")));
			i.setType(json.getInt("TYPEID"));
			i.setCreator(json.getString("CREATOR"));
			boolean flag = is.addInsframework(i);
			if(flag){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public Object editInsframework(String obj1,String obj2) {
		try{
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			//向集团层执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client blocclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("blocurl"));
			jutil.Authority(blocclient);
			Object[] blocobj = blocclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{obj1,obj2});  
			String blocResult = blocobj[0].toString();
			JSONObject json = JSONObject.fromObject(obj2);
			boolean resultflag = true;
			//获取层级id
			String hierarchy = json.getString("HIERARCHY");
			String itemurl = "";
			if(hierarchy.equals("4")){
				itemurl = json.getString("ITEMURL");
			}else{
				int typeid = json.getInt("TYPEID");
				Insframework i = new Insframework();
				i.setId(new BigInteger(json.getString("INSFID")));
				i.setName(json.getString("NAME"));
				i.setLogogram(json.getString("LOGOGRAM"));
				i.setCode(json.getString("CODE"));
				i.setParent(new BigInteger(json.getString("PARENT")));
				i.setType(json.getInt("TYPEID"));
				i.setModifier(json.getString("MODIFIER"));
				boolean flag = is.editInsframework(i);
				String result = "false";
				if(typeid==23){
					BigInteger insfid = new BigInteger(json.getString("INSFID"));
					itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
					if(flag  && blocResult.equals("true")){
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
						resultflag =  false;
					}
				}
			}
			return resultflag;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Object deleteInsframework(String obj1,String obj2) {
		try{
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			//向集团层执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client blocclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("blocurl"));
			jutil.Authority(blocclient);
			Object[] blocobj = blocclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{obj1,obj2});  
			String blocResult = blocobj[0].toString();
			JSONObject json = JSONObject.fromObject(obj2);
			boolean resultflag = true;
			//获取层级id
			String hierarchy = json.getString("HIERARCHY");
			String itemurl = "";
			if(hierarchy.equals("4")){
				itemurl = json.getString("ITEMURL");
			}else{
				int type = json.getInt("TYPE");
				boolean flag = is.deleteInsframework(new BigInteger(json.getString("INSFID")));
				String result = "false";
				if(type==23){
					BigInteger insfid = new BigInteger(json.getString("INSFID"));
					itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
					if(flag && blocResult.equals("true")){
						if(itemurl != null && !"".equals(itemurl)){
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
						resultflag = false;
					}
				}
			}
			return resultflag;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int getInsframeworkNameCount(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return is.getInsframeworkNameCount(json.getString("NAME"));
		}catch(Exception e){
			return -1;
		}
	}

	@Override
	public String getInsframeworkById(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return is.getInsframeworkById(new BigInteger(json.getString("INSFID")));
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getInsfAllById(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			Insframework list = is.getInsfAllById(new BigInteger(json.getString("INSFID")));
			if(list!=null){
				obj.put("ID", list.getId());
				obj.put("NAME",list.getName());
				obj.put("LOGOGRAM",jutil.setValue(list.getLogogram()));
				obj.put("CODE",jutil.setValue(list.getCode()));
				obj.put("PARENTID",jutil.setValue(list.getParent()));
				obj.put("TYPEID",jutil.setValue(list.getType()));
				obj.put("TYPENAME",jutil.setValue(list.getTypename()));
			}
			return JSON.toJSONString(obj);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getBloc() {
		try{
			JSONObject obj = new JSONObject();
			Insframework list = is.getBloc();
			if(list!=null){
				obj.put("ID", list.getId());
				obj.put("NAME",list.getName());
			}
			return JSON.toJSONString(obj);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getCompany(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			String id = json.getString("INSFID");
			BigInteger insfid = null;
			if(id!=null && !"".equals(id)){
				insfid = new BigInteger(id);
			}
			List<Insframework> list =  is.getConmpany(insfid);
			for(int i=0;i<list.size();i++){
				obj.put("ID", list.get(i).getId());
				obj.put("NAME",list.get(i).getName());
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getCause(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			String id = json.getString("INSFID");
			BigInteger insfid = null;
			if(id!=null && !"".equals(id)){
				insfid = new BigInteger(id);
			}
			List<Insframework> list =  is.getCause(new BigInteger(json.getString("COMPANYID")),insfid);
			for(int i=0;i<list.size();i++){
				obj.put("ID", list.get(i).getId());
				obj.put("NAME",list.get(i).getName());
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object getWeldingMachineInsf(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			String id = json.getString("INSFID");
			BigInteger insfid = null;
			if(id!=null && !"".equals(id)){
				insfid = new BigInteger(id);
			}
			List<Insframework> list =  is.getWeldingMachineInsf(insfid);
			for(int i=0;i<list.size();i++){
				obj.put("ID", list.get(i).getId());
				obj.put("NAME",list.get(i).getName());
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getParent(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			String id = json.getString("INSFID");
			BigInteger insfid = null;
			if(id!=null && !"".equals(id)){
				insfid = new BigInteger(id);
			}
			Insframework list = is.getParent(insfid);
			if(list!=null){
				obj.put("ID", list.getId());
				obj.put("NAME",list.getName());
			}
			return JSON.toJSONString(obj);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getInsByType(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			String id = json.getString("INSFID");
			BigInteger insfid = null;
			if(id!=null && !"".equals(id)){
				insfid = new BigInteger(id);
			}
			List<Insframework> list =  is.getInsByType(json.getInt("TYPEID"), insfid);
			for(int i=0;i<list.size();i++){
				obj.put("ID", list.get(i).getId());
				obj.put("NAME",list.get(i).getName());
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public int getUserInsfType(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return is.getUserInsfType(new BigInteger(json.getString("USERID")));
		}catch(Exception e){
			return -1;
		}
	}

	@Override
	public BigInteger getUserInsfId(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return is.getUserInsfId(new BigInteger(json.getString("USERID")));
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public int getTypeById(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return is.getTypeById(new BigInteger(json.getString("INSFID")));
		}catch(Exception e){
			return -1;
		}
	}

	@Override
	public BigInteger getParentById(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return is.getParentById(new BigInteger(json.getString("INSFID")));
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getInsByUserid(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			List<Insframework> list =   is.getInsByUserid(new BigInteger(json.getString("USERID")));
			if(list!=null){
				obj.put("ID", jutil.setValue(list.get(0).getId()));
				obj.put("TYPEID",jutil.setValue(list.get(0).getType()));
			}
			return JSON.toJSONString(obj);
		}catch(Exception e){
			return null;
		}
	}
}
