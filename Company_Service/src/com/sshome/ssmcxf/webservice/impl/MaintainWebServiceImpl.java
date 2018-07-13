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
import com.spring.model.MaintenanceRecord;
import com.spring.model.WeldingMachine;
import com.spring.model.WeldingMaintenance;
import com.spring.service.MaintainService;
import com.sshome.ssmcxf.webservice.MaintainWebService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Transactional
@Service
public class MaintainWebServiceImpl implements MaintainWebService {
	@Autowired
	private MaintainService ms;
	
	private JudgeUtil jutil = new JudgeUtil();
	
	@Override
	public Object getWeldingMaintenanceAll(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			List<WeldingMaintenance> list = ms.getWeldingMaintenanceAll(json.getString("STR"));
			for(int i=0;i<list.size();i++){
				obj.put("ID", jutil.setValue(list.get(i).getId()));
				obj.put("INSFID",jutil.setValue(list.get(i).getInsfid()));
				if(list.get(i).getMaintenance()!=null){
					obj.put("MONEY",jutil.setValue(list.get(i).getMaintenance().getMoney()));
					obj.put("MAINTENANCEID",jutil.setValue(list.get(i).getMaintenance().getId()));
					obj.put("VICEMAN",jutil.setValue(list.get(i).getMaintenance().getViceman()));
					obj.put("TYPEID",jutil.setValue(list.get(i).getMaintenance().getTypeId()));
					obj.put("TYPENAME",jutil.setValue(list.get(i).getMaintenance().getTypename()));
					obj.put("STARTTIME",jutil.setValue(list.get(i).getMaintenance().getStartTime()));
					obj.put("ENDTIME",jutil.setValue(list.get(i).getMaintenance().getEndTime()));
					obj.put("DESC",jutil.setValue(list.get(i).getMaintenance().getDesc()));
				}else{
					obj.put("MONEY","");
					obj.put("MAINTENANCEID", "");
					obj.put("VICEMAN", "");
					obj.put("TYPEID", "");
					obj.put("TYPENAME", "");
					obj.put("STARTTIME", "");
					obj.put("ENDTIME", "");
					obj.put("DESC", "");
				}
				if(list.get(i).getWelding()!=null){
					obj.put("MACHINEID",jutil.setValue(list.get(i).getWelding().getId()));
					obj.put("MACHINENO",jutil.setValue(list.get(i).getWelding().getEquipmentNo()));
				}else{
					obj.put("MACHINEID", "");
					obj.put("MACHINENO", "");
				}
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getEndtime(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			List<WeldingMaintenance> list = ms.getEndtime(new BigInteger(json.getString("WID")));
			for(int i=0;i<list.size();i++){
				obj.put("ID", jutil.setValue(list.get(i).getId()));
				if(list.get(i).getMaintenance()!=null){
					obj.put("VICEMAN",jutil.setValue(list.get(i).getMaintenance().getViceman()));
					obj.put("TYPEID",jutil.setValue(list.get(i).getMaintenance().getTypeId()));
					obj.put("STARTTIME",jutil.setValue(list.get(i).getMaintenance().getStartTime()));
					obj.put("DESC",jutil.setValue(list.get(i).getMaintenance().getDesc()));
				}else{
					obj.put("VICEMAN", "");
					obj.put("TYPEID", "");
					obj.put("STARTTIME", "");
					obj.put("DESC", "");
				}
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getWeldingMaintenanceById(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			WeldingMaintenance list = ms.getWeldingMaintenanceById(new BigInteger(json.getString("WID")));
			if(list!=null){
				obj.put("ID", jutil.setValue(list.getId()));
				if(list.getMaintenance()!=null){
					obj.put("MONEY",jutil.setValue(list.getMaintenance().getMoney()));
					obj.put("MAINTENANCEID",jutil.setValue(list.getMaintenance().getId()));
					obj.put("VICEMAN",jutil.setValue(list.getMaintenance().getViceman()));
					obj.put("TYPEID",jutil.setValue(list.getMaintenance().getTypeId()));
					obj.put("STARTTIME",jutil.setValue(list.getMaintenance().getStartTime()));
					obj.put("ENDTIME",jutil.setValue(list.getMaintenance().getEndTime()));
					obj.put("DESC",jutil.setValue(list.getMaintenance().getDesc()));
				}else{
					obj.put("MONEY", "");
					obj.put("MAINTENANCEID", "");
					obj.put("VICEMAN", "");
					obj.put("TYPEID", "");
					obj.put("STARTTIME", "");
					obj.put("ENDTIME", "");
					obj.put("DESC", "");
				}
				if(list.getWelding()!=null){
					obj.put("MACHINEID",jutil.setValue(list.getWelding().getId()));
					obj.put("MACHINENO",jutil.setValue(list.getWelding().getEquipmentNo()));
				}else{
					obj.put("MACHINEID", "");
					obj.put("MACHINENO", "");
				}
			}
			return JSON.toJSONString(obj);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getEquipmentno() {
		try{
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			List<WeldingMachine> list = ms.getEquipmentno();
			for(int i=0;i<list.size();i++){
				obj.put("ID", jutil.setValue(list.get(i).getId()));
				obj.put("MACHINENO", jutil.setValue(list.get(i).getEquipmentNo()));
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			return null;
		}
	}
	
	@Override
	public Object addMaintian(String obj1,String obj2) {
		try{
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			//向集团层执行插入
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client blocclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("blocurl"));
			jutil.Authority(blocclient);
			//执行明细表插入
			String str = "{\"CLASSNAME\":\"maintainWebServiceImpl\",\"METHOD\":\"addMaintenanceRecord\"}";
			Object[] blocobj2 = blocclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{str,obj2});  
			BigInteger rid = new BigInteger(blocobj2[0].toString());
			//执行关联表的插入
			obj2 = obj2.substring(0,obj2.length()-1)+",\"RID\":\""+rid+"\"}";
			Object[] blocobj1 = blocclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});  
			BigInteger mid = new BigInteger(blocobj1[0].toString());
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
			WeldingMaintenance wm = new WeldingMaintenance();
			MaintenanceRecord mr = new MaintenanceRecord();
			mr.setId(new BigInteger(json.getString("RID")));
			mr.setViceman(json.getString("VICEMAN"));
			mr.setStartTime(json.getString("STARTTIME"));
			mr.setMoney(json.getInt("MONEY"));
			String endTime = json.getString("ENDTIME");
			if(endTime!=null && !"".equals(endTime)){
				mr.setEndTime(json.getString("ENDTIME"));
			}
			mr.setDesc(json.getString("DESC"));
			mr.setTypeId(json.getInt("TYPEID"));
			mr.setCreator(json.getString("CREATOR"));
			wm.setMaintenance(mr);
			wm.setCreator(json.getString("CREATOR"));
			WeldingMachine w = new WeldingMachine();
			BigInteger wid = new BigInteger(json.getString("WELDID"));
			w.setId(wid);
			wm.setWelding(w);
			wm.setId(mid);
			obj2 = obj2.substring(0,obj2.length()-1)+",\"MID\":\""+mid+"\"}";
			boolean flag = ms.addMaintenanceRecord(mr);
			boolean flags = ms.addMaintian(wm,mr,wid);
			String result = "false";
			if(flag && flags){
				if(itemurl!=null && !"".equals(itemurl)){
					//向项目执行插入
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
	public Object updateEndtime(String obj1,String obj2) {
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
			boolean flag = ms.updateEndtime(new BigInteger(json.getString("MID")));
			BigInteger weldingid = new BigInteger(json.getString("WELDINGID"));
			List<WeldingMaintenance> list =  ms.getEndtime(weldingid);
			if(list.isEmpty()){
				//如果维修结束时间没有为空的则修改状态为启用
				ms.editstatus(weldingid, 31);
			}
			String result = "false";
			if(flag && blocResult.equals("true")){
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
			return false;
		}
	}

	@Override
	public Object updateMaintenanceRecord(String obj1,String obj2) {
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
			MaintenanceRecord mr = new MaintenanceRecord();
			mr.setId(new BigInteger(json.getString("MID")));
			mr.setViceman(json.getString("VICEMAN"));
			mr.setStartTime(json.getString("STARTTIME"));
			String endTime = json.getString("ENDTIME");
			mr.setMoney(json.getInt("MONEY"));
			if(endTime!=null && !"".equals(endTime)){
				mr.setEndTime(json.getString("ENDTIME"));
			}
			mr.setDesc(json.getString("DESC"));
			mr.setTypeId(json.getInt("TYPEID"));
			mr.setModifier(json.getString("MODIFIER"));
			boolean flag = ms.updateMaintenanceRecord(mr);
			BigInteger wid = new BigInteger(json.getString("WID"));
			List<WeldingMaintenance> list =  ms.getEndtime(wid);
			if(endTime==null || "".equals(endTime)){
				//修改焊机状态为维修中
				ms.editstatus(wid, 33);
			}
			if(list.isEmpty()){
				//如果维修结束时间没有为空的则修改状态为启用
				ms.editstatus(wid, 31);
			}
			String result = "false";
			if(flag && blocResult.equals("true")){
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
	public Object deleteMaintenanceRecord(String obj1,String obj2) {
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
			WeldingMaintenance wm = ms.getWeldingMaintenanceById(new BigInteger(json.getString("MID")));
			boolean flag = ms.deleteMaintenanceRecord(wm.getMaintenance().getId());
			boolean flags = ms.deleteWeldingMaintenance(wm.getId());
			String result = "false";
			if(flag && flags &&  blocResult.equals("true")){
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
	public boolean deleteWeldingMaintenance(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return ms.deleteWeldingMaintenance(new BigInteger(json.getString("WID")));
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public Object getMaintainByWeldingMachinId(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			List<WeldingMaintenance> list = ms.getMaintainByWeldingMachinId(new BigInteger(json.getString("WID")));
			for(int i=0;i<list.size();i++){
				obj.put("ID", jutil.setValue(list.get(i).getId()));
				obj.put("MAINTENANCEID", jutil.setValue(list.get(i).getMaintenance().getId()));
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public boolean editstatus(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return ms.editstatus(new BigInteger(json.getString("WID")), json.getInt("STATUSID"));
		}catch(Exception e){
			return false;
		}
	}
	
	@Override
	public BigInteger getInsfidByMachineid(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return ms.getInsfidByMachineid(new BigInteger(json.getString("WID")));
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public boolean editStatusToItem(String obj1, String obj2) {
		try{
			String newobj = "{\"CLASSNAME\":\"maintainWebServiceImpl\",\"METHOD\":\"editstatus\"}";
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			JSONObject json = JSONObject.fromObject(obj2);
			BigInteger insfid = new BigInteger(json.getString("INSFID"));
			String itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
			if(itemurl !=null && !"".equals(itemurl)){
				JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
				//向项目执行插入
				Client itemclient = dcf.createClient(itemurl);
				jutil.Authority(itemclient);
				Object[] itemobj = itemclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{newobj,obj2});
				if(itemobj[0].toString().equals("true")){
					return true;
				}else{
					return false;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
			return true;
	}

}
