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
import com.spring.model.EquipmentManufacturer;
import com.spring.model.Gather;
import com.spring.model.Insframework;
import com.spring.model.WeldingMachine;
import com.spring.model.WeldingMaintenance;
import com.spring.service.MaintainService;
import com.spring.service.WeldingMachineService;
import com.sshome.ssmcxf.webservice.WeldingMachineWebService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Transactional
@Service
public class WeldingMachineWebServiceImpl implements WeldingMachineWebService {
	@Autowired
	private WeldingMachineService wms;
	
	@Autowired
	private MaintainService ms;
	
	private JudgeUtil jutil = new JudgeUtil();
	
	@Override
	public Object getWeldingMachineAll(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			String id = json.getString("INSFID");
			BigInteger insfid = null;
			if(id!=null && !"".equals(id)){
				insfid = new BigInteger(id);
			}
			List<WeldingMachine> list =  wms.getWeldingMachineAll(insfid, json.getString("STR"));
			for(int i=0;i<list.size();i++){
				obj.put("ID", jutil.setValue(list.get(i).getId()));
				obj.put("MACHINENO",jutil.setValue(list.get(i).getEquipmentNo()));
				if(list.get(i).getGatherId()!=null){
					obj.put("GATHERID",jutil.setValue(list.get(i).getGatherId().getId()));
					obj.put("GATHERNO",jutil.setValue(list.get(i).getGatherId().getGatherNo()));
				}else{
					obj.put("GATHERID","");
					obj.put("GATHERNO","");
				}
				if(list.get(i).getManufacturerId()!=null){
					obj.put("MANUFACTURERID",jutil.setValue(list.get(i).getManufacturerId().getId()));
					obj.put("MANUFACTURERNAME",jutil.setValue(list.get(i).getManufacturerId().getName()));
				}else{
					obj.put("MANUFACTURERID", "");
					obj.put("MANUFACTURERNAME", "");
				}
				if(list.get(i).getInsframeworkId()!=null){
					obj.put("INSFRAMEWORKID",jutil.setValue(list.get(i).getInsframeworkId().getId()));
					obj.put("INSFRAMEWORKNAME",jutil.setValue(list.get(i).getInsframeworkId().getName()));
				}else{
					obj.put("INSFRAMEWORKID", "");
					obj.put("INSFRAMEWORKNAME", "");
				}
				obj.put("MONEY", jutil.setValue(list.get(i).getMoney()));
				obj.put("JOINTIME",jutil.setValue(list.get(i).getJoinTime()));
				obj.put("POSITION",jutil.setValue(list.get(i).getPosition()));
				obj.put("ISNETWORKING",jutil.setValue(list.get(i).getIsnetworking()));
				obj.put("STATUSID",jutil.setValue(list.get(i).getStatusId()));
				obj.put("STATUSNAME",jutil.setValue(list.get(i).getStatusname()));
				obj.put("TYPEID",jutil.setValue(list.get(i).getTypeId()));
				obj.put("TYPENAME",jutil.setValue(list.get(i).getTypename()));
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getManuAll() {
		try{
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			List<EquipmentManufacturer> list =  wms.getManuAll();
			for(int i=0;i<list.size();i++){
				obj.put("ID", jutil.setValue(list.get(i).getId()));
				obj.put("NAME",jutil.setValue(list.get(i).getName()));
				obj.put("TYPE",jutil.setValue(list.get(i).getType()));
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
	public Object addWeldingMachine(String obj1,String obj2) {
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
				BigInteger insfid = new BigInteger(json.getString("INSFRAMEWORKID"));
				itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
			}
			WeldingMachine wm = new WeldingMachine();
			wm.setId(id);
			wm.setMoney(json.getInt("MONEY"));
			wm.setEquipmentNo(json.getString("EQUIPMENTNO"));
			wm.setPosition(json.getString("POSITION"));
			wm.setIsnetworking(json.getInt("ISNETWORKING"));
			String jointime = json.getString("JOINTIME");
			if(jointime!=null && !"".equals(jointime)){
				wm.setJoinTime(jointime);
			}
			wm.setTypeId(json.getInt("TYPEID"));
			wm.setStatusId(json.getInt("STATUSID"));
			wm.setCreator(json.getString("CREATOR"));
			Gather gather = new Gather();
			String gatherid = json.getString("GATHERID");
			if(gatherid!=null && !"".equals(gatherid)){
				gather.setId(new BigInteger(gatherid));
			}
			wm.setGatherId(gather);
			EquipmentManufacturer e = new EquipmentManufacturer();
			e.setId(new BigInteger(json.getString("MANUFACTURERID")));
			wm.setManufacturerId(e);
			Insframework ins = new Insframework();
			ins.setId(new BigInteger(json.getString("INSFRAMEWORKID")));
			wm.setInsframeworkId(ins);
			boolean flag = wms.addWeldingMachine(wm);
			String result = "false";
			if(flag){
				if(itemurl!=null && !"".equals(itemurl)){
					//向项目执行插入
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
	public Object editWeldingMachine(String obj1,String obj2) {
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
				BigInteger insfid = new BigInteger(json.getString("INSFRAMEWORKID"));
				itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
			}
			WeldingMachine wm = new WeldingMachine();
			wm.setId(new BigInteger(json.getString("ID")));
			wm.setMoney(json.getInt("MONEY"));
			wm.setEquipmentNo(json.getString("EQUIPMENTNO"));
			wm.setPosition(json.getString("POSITION"));
			wm.setIsnetworking(json.getInt("ISNETWORKING"));
			String jointime = json.getString("JOINTIME");
			if(jointime!=null && !"".equals(jointime)){
				wm.setJoinTime(jointime);
			}
			wm.setTypeId(json.getInt("TYPEID"));
			wm.setStatusId(json.getInt("STATUSID"));
			wm.setModifier(json.getString("MODIFIER"));
			Gather gather = new Gather();
			String gatherid = json.getString("GATHERID");
			if(gatherid!=null && !"".equals(gatherid)){
				gather.setId(new BigInteger(gatherid));
			}
			wm.setGatherId(gather);
			EquipmentManufacturer e = new EquipmentManufacturer();
			e.setId(new BigInteger(json.getString("MANUFACTURERID")));
			wm.setManufacturerId(e);
			Insframework ins = new Insframework();
			ins.setId(new BigInteger(json.getString("INSFRAMEWORKID")));
			wm.setInsframeworkId(ins);
			boolean flag = wms.editWeldingMachine(wm);
			String result = "false";
			if(flag && blocResult.equals("true")){
				if(itemurl !=null && !"".equals(itemurl)){
					//向项目执行操作
					Client itemclient = dcf.createClient(itemurl);
					jutil.Authority(itemclient);
					Object[] itemobj = itemclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});
					result = itemobj[0].toString();
					if(result.equals("true")){
						//修改焊机状态为启用时，结束所有维修任务
						int sid = wm.getStatusId();
						if(sid == 31){
							List<WeldingMaintenance> list = ms.getEndtime(wm.getId());
							for(WeldingMaintenance w : list){
									ms.updateEndtime(w.getId());
							}
						}
					}
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
	public Object deleteWeldingChine(String obj1,String obj2) {
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
			boolean flag = wms.deleteWeldingChine(new BigInteger(json.getString("WID")));
			String result = "false";
			if(flag && blocResult.equals("true")){
				if(itemurl !=null && !"".equals(itemurl)){
					//向项目执行操作
					Client itemclient = dcf.createClient(itemurl);
					jutil.Authority(itemclient);
					Object[] itemobj = itemclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});
					result = itemobj[0].toString();
					if(result.equals("true")){
						List<WeldingMaintenance> list = ms.getMaintainByWeldingMachinId(new BigInteger(json.getString("WID")));
						for(WeldingMaintenance wm : list){
							//删除维修记录
							ms.deleteWeldingMaintenance(wm.getId());
							ms.deleteMaintenanceRecord(wm.getMaintenance().getId());
						}
					}
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
	public BigInteger getWeldingMachineByEno(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return wms.getWeldingMachineByEno(json.getString("ENO"));
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public int getEquipmentnoCount(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return wms.getEquipmentnoCount(json.getString("ENO"));
		}catch(Exception e){
			return -1;
		}
	}

	@Override
	public int getGatheridCount(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			BigInteger itemid = new BigInteger(json.getString("INSFID"));
			String gather = json.getString("GATHERNO");
			return wms.getGatheridCount(itemid, gather);
		}catch(Exception e){
			return -1;
		}
	}

	@Override
	public BigInteger getManuidByValue(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return wms.getManuidByValue(json.getString("MANUNAME"), json.getString("MANUTYPE"));
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getWeldingMachineById(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			WeldingMachine list = wms.getWeldingMachineById(new BigInteger(json.getString("WID")));
			if(list!=null){
				obj.put("ID", jutil.setValue(list.getId()));
				obj.put("MACHINENO",jutil.setValue(list.getEquipmentNo()));
				if(list.getGatherId()!=null){
					obj.put("GATHERID",jutil.setValue(list.getGatherId().getId()));
					obj.put("GATHERNO",jutil.setValue(list.getGatherId().getGatherNo()));
				}else{
					obj.put("GATHERID","");
					obj.put("GATHERNO","");
				}
				if(list.getManufacturerId()!=null){
					obj.put("MANUFACTURERID",jutil.setValue(list.getManufacturerId().getId()));
					obj.put("MANUFACTURERNAME",jutil.setValue(list.getManufacturerId().getName()));
				}else{
					obj.put("MANUFACTURERID", "");
					obj.put("MANUFACTURERNAME", "");
				}
				if(list.getInsframeworkId()!=null){
					obj.put("INSFRAMEWORKID",jutil.setValue(list.getInsframeworkId().getId()));
					obj.put("INSFRAMEWORKNAME",jutil.setValue(list.getInsframeworkId().getName()));
				}else{
					obj.put("INSFRAMEWORKID", "");
					obj.put("INSFRAMEWORKNAME", "");
				}
				obj.put("MONEY",jutil.setValue(list.getMoney()));
				obj.put("JOINTIME",jutil.setValue(list.getJoinTime()));
				obj.put("POSITION",jutil.setValue(list.getPosition()));
				obj.put("ISNETWORKING",jutil.setValue(list.getIsnetworking()));
				obj.put("STATUSID",jutil.setValue(list.getStatusId()));
				obj.put("STATUSNAME",jutil.setValue(list.getStatusname()));
				obj.put("TYPEID",jutil.setValue(list.getTypeId()));
				obj.put("TYPENAME",jutil.setValue(list.getTypename()));
			}
			return JSON.toJSONString(obj);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public BigInteger getInsframeworkByName(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return wms.getInsframeworkByName(json.getString("INSFNAME"));
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public BigInteger getMachineCountByManu(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			BigInteger mid = new BigInteger(json.getString("MANUID"));
			BigInteger insid = new BigInteger(json.getString("INSFID"));
			return wms.getMachineCountByManu(mid, insid);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object addMachineToItem(String obj1, String obj2) {
		try{
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			JSONObject json = JSONObject.fromObject(obj2);
			BigInteger insfid = new BigInteger(json.getString("INSFRAMEWORKID"));
			String itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
			if(itemurl !=null && !"".equals(itemurl)){
				JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
				//向项目执行插入
				Client itemclient = dcf.createClient(itemurl);
				jutil.Authority(itemclient);
				String newobj = "{\"CLASSNAME\":\"weldingMachineWebServiceImpl\",\"METHOD\":\"addWeldingMachine\"}";
				Object[] itemobj = itemclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{newobj,obj2});
				return itemobj[0].toString();
			}else{
				return "未找到该项目部，请检查网络连接情况或是否部署服务";
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Object editMachineToBlocCompany(String obj1, String obj2) {
		try{
			String newobj = "{\"CLASSNAME\":\"weldingMachineWebServiceImpl\",\"METHOD\":\"editWeldingMachine\"}";
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			//向集团层执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client blocclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("blocurl"));
			jutil.Authority(blocclient);
			Object[] blocobj = blocclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{newobj,obj2});  
			String blocResult = blocobj[0].toString();
			JSONObject json = JSONObject.fromObject(obj2);
			WeldingMachine wm = new WeldingMachine();
			wm.setId(new BigInteger(json.getString("ID")));
			wm.setMoney(json.getInt("MONEY"));
			wm.setEquipmentNo(json.getString("EQUIPMENTNO"));
			wm.setPosition(json.getString("POSITION"));
			wm.setIsnetworking(json.getInt("ISNETWORKING"));
			String jointime = json.getString("JOINTIME");
			if(jointime!=null && !"".equals(jointime)){
				wm.setJoinTime(jointime);
			}
			wm.setTypeId(json.getInt("TYPEID"));
			wm.setStatusId(json.getInt("STATUSID"));
			wm.setModifier(json.getString("MODIFIER"));
			Gather gather = new Gather();
			String gatherid = json.getString("GATHERID");
			if(gatherid!=null && !"".equals(gatherid)){
				gather.setId(new BigInteger(gatherid));
			}
			wm.setGatherId(gather);
			EquipmentManufacturer e = new EquipmentManufacturer();
			e.setId(new BigInteger(json.getString("MANUFACTURERID")));
			wm.setManufacturerId(e);
			Insframework ins = new Insframework();
			ins.setId(new BigInteger(json.getString("INSFRAMEWORKID")));
			wm.setInsframeworkId(ins);
			boolean flag = wms.editWeldingMachine(wm);
			if(flag && blocResult.equals("true")){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int getMachineCountToItem(String obj1, String obj2) {
		try{
			String newobj = "{\"CLASSNAME\":\"weldingMachineWebServiceImpl\",\"METHOD\":\"getEquipmentnoCount\"}";
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			JSONObject json = JSONObject.fromObject(obj2);
			BigInteger insfid = new BigInteger(json.getString("INSFID"));
			String itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
			int num = 0;
			if(itemurl !=null && !"".equals(itemurl)){
				JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
				//向项目执行插入
				Client itemclient = dcf.createClient(itemurl);
				jutil.Authority(itemclient);
				Object[] itemobj = itemclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{newobj,obj2});
				num =  Integer.parseInt(itemobj[0].toString());
			}
			return num;
		}catch(Exception e){
			return -1;
		}
	}

	@Override
	public String getGatheridMachine(String obj1, String obj2) {
		try{
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			JSONObject json = JSONObject.fromObject(obj2);
			BigInteger insfid = new BigInteger(json.getString("INSFID"));
			String itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
			String num = "";
			if(itemurl !=null && !"".equals(itemurl)){
				JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
				//向项目执行插入
				Client itemclient = dcf.createClient(itemurl);
				jutil.Authority(itemclient);
				Object[] itemobj = itemclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});
				num =  itemobj[0].toString();
			}
			return num;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object editMachineToItem(String obj1, String obj2) {
		try{
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			JSONObject json = JSONObject.fromObject(obj2);
			BigInteger insfid = new BigInteger(json.getString("INSFRAMEWORKID"));
			String itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
			if(itemurl !=null && !"".equals(itemurl)){
				JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
				//向项目执行插入
				Client itemclient = dcf.createClient(itemurl);
				jutil.Authority(itemclient);
				String newobj = "{\"CLASSNAME\":\"weldingMachineWebServiceImpl\",\"METHOD\":\"editWeldingMachine\"}";
				Object[] itemobj = itemclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{newobj,obj2});
				return itemobj[0].toString();
			}else{
				return "未找到该项目部，请检查网络连接情况或是否部署服务";
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String getMachineByGather(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return wms.getMachineByGather(new BigInteger(json.getString("INSFID")), json.getString("GATHERNO"));
		}catch(Exception e){
			return null;
		}
	}

}
