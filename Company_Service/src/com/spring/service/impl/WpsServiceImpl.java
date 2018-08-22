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
import com.spring.model.Gather;
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
			List<Wps> list = wm.findAll(json.getString("STR"));
			for(int i=0;i<list.size();i++){
				obj.put("ID", jutil.setValue(list.get(i).getChildrenid()));
				obj.put("WPSNO",jutil.setValue(list.get(i).getFwpsnum()));
				obj.put("ELECTRICITY",jutil.setValue(list.get(i).getFweld_i()));
				obj.put("VALTAGE",jutil.setValue(list.get(i).getFweld_v()));
				obj.put("MAXELECTRICITY",jutil.setValue(list.get(i).getFweld_i_max()));
				obj.put("MINELECTRICITY",jutil.setValue(list.get(i).getFweld_i_min()));
				obj.put("MAXVALTAGE",jutil.setValue(list.get(i).getFweld_v_max()));
				obj.put("MINVALTAGE",jutil.setValue(list.get(i).getFweld_v_min()));
				obj.put("PRECHANNEL",jutil.setValue(list.get(i).getFweld_prechannel()));
				obj.put("INSFID",jutil.setValue(list.get(i).getInsid()));
				obj.put("BACK",jutil.setValue(list.get(i).getFback()));
				obj.put("NAME",jutil.setValue(list.get(i).getFname()));
				obj.put("WELDINGMETHOD",jutil.setValue(list.get(i).getFwelding_method()));
				obj.put("TYPE",jutil.setValue(list.get(i).getFtype()));
				obj.put("GETFPOLARITY",jutil.setValue(list.get(i).getFpolarity()));
				obj.put("WELDINGSPEED",jutil.setValue(list.get(i).getFwelding_speed()));
				obj.put("SPECIFICATION",jutil.setValue(list.get(i).getFspecification()));
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			e.printStackTrace();
			return null;
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
				obj.put("ID", jutil.setValue(list.getChildrenid()));
				obj.put("WPSNO",jutil.setValue(list.getFwpsnum()));
				obj.put("ELECTRICITY",jutil.setValue(list.getFweld_i()));
				obj.put("VALTAGE",jutil.setValue(list.getFweld_v()));
				obj.put("MAXELECTRICITY",jutil.setValue(list.getFweld_i_max()));
				obj.put("MINELECTRICITY",jutil.setValue(list.getFweld_i_min()));
				obj.put("MAXVALTAGE",jutil.setValue(list.getFweld_v_max()));
				obj.put("MINVALTAGE",jutil.setValue(list.getFweld_v_min()));
				obj.put("PRECHANNEL",jutil.setValue(list.getFweld_prechannel()));
				obj.put("INSFID",jutil.setValue(list.getInsid()));
				obj.put("BACK",jutil.setValue(list.getFback()));
				obj.put("NAME",jutil.setValue(list.getFname()));
				obj.put("WELDINGMETHOD",jutil.setValue(list.getFwelding_method()));
				obj.put("TYPE",jutil.setValue(list.getFtype()));
				obj.put("GETFPOLARITY",jutil.setValue(list.getFpolarity()));
				obj.put("WELDINGSPEED",jutil.setValue(list.getFwelding_speed()));
				obj.put("SPECIFICATION",jutil.setValue(list.getFspecification()));
			}
			return JSON.toJSONString(obj);
		}catch(Exception e){
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
			//获取层级id
			//获取层级id
			String hierarchy = json.getString("HIERARCHY");
			String itemurl = "";
			boolean itemflag = true;
			if(hierarchy.equals("4")){
				itemurl = json.getString("ITEMURL");
			}else{
				if(json.getInt("INSFTYPE")==23){
					BigInteger insfid = new BigInteger(json.getString("INSFID"));
					itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
				}else{
					itemflag = false;
				}
			}
			boolean flag = wm.delete(new BigInteger(json.getString("ID")));
			String result = "false";
			if(flag &&  blocResult.equals("true")){
				if(!itemflag){
					return true;
				}
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
	public Object findWpsAll(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			BigInteger insid = null;
			String iid = json.getString("insfid");
			if(iid!=null && !"".equals(iid)){
				insid = new BigInteger(iid);
			}
			List<Wps> list = wm.findWpsAll(insid, json.getString("STR"));
			for(int i=0;i<list.size();i++){
				obj.put("ID", jutil.setValue(list.get(i).getFid()));
				obj.put("WPSNO",jutil.setValue(list.get(i).getFwpsnum()));
				obj.put("VERSIONS",jutil.setValue(list.get(i).getFversions()));
				obj.put("PROJECT_CODE",jutil.setValue(list.get(i).getFproject_code()));
				obj.put("REPORT_NUMBER",jutil.setValue(list.get(i).getFreport_number()));
				obj.put("DEGREE",jutil.setValue(list.get(i).getFdegree()));
				obj.put("EVALUATION_STANDARD",jutil.setValue(list.get(i).getFevaluation_standard()));
				obj.put("VALIDITY",jutil.setValue(list.get(i).getFvalidity())); 
				obj.put("AUTOMATIC",jutil.setValue(list.get(i).getFautomatic()));
				obj.put("GROOVE_TYPE",jutil.setValue(list.get(i).getFgroove_type()));
				obj.put("STABILIVOLT_SYSTEM",jutil.setValue(list.get(i).getFstabilivolt_system()));
				obj.put("MATERIALS",jutil.setValue(list.get(i).getFmaterials()));
				obj.put("THICKNESS1",jutil.setValue(list.get(i).getFthickness1()));
				obj.put("THICKNESS2",jutil.setValue(list.get(i).getFthickness2()));
				obj.put("DIAMETER",jutil.setValue(list.get(i).getFdiameter()));
				obj.put("ELES1",jutil.setValue(list.get(i).getFeles1()));
				obj.put("IMAGES_DESC",jutil.setValue(list.get(i).getFimages_desc()));
				obj.put("IMAGES_URL", jutil.setValue(list.get(i).getFimages_url()));
				obj.put("CATEGORY", jutil.setValue(list.get(i).getFcategory()));
				obj.put("SHOP_SIGN", jutil.setValue(list.get(i).getFshop_sign()));
				obj.put("SPECIFICATION", jutil.setValue(list.get(i).getFspecification()));
				obj.put("MATERIALS_TYPE", jutil.setValue(list.get(i).getFmaterials_type()));
				obj.put("MATERIALS_NUMBER1", jutil.setValue(list.get(i).getFmaterials_number1()));
				obj.put("MATERIALS_SPECIFICATION1", jutil.setValue(list.get(i).getFmaterials_specification1()));
				obj.put("MATERIALS_NUMBER2", jutil.setValue(list.get(i).getFmaterials_number2()));
				obj.put("MATERIALS_SPECIFICATION2()", jutil.setValue(list.get(i).getFmaterials_specification2()));
				obj.put("SOLDERING_NUMBER", jutil.setValue(list.get(i).getFsoldering_number()));
				obj.put("POSITION", jutil.setValue(list.get(i).getFposition()));
				obj.put("DIRECTION", jutil.setValue(list.get(i).getFdirection()));
				obj.put("ELSE2", jutil.setValue(list.get(i).getFelse2()));
				obj.put("FRONT1", jutil.setValue(list.get(i).getFfront1()));
				obj.put("FRONT2", jutil.setValue(list.get(i).getFfront2()));
				obj.put("REVERSE1", jutil.setValue(list.get(i).getFreverse1()));
				obj.put("REVERSE2", jutil.setValue(list.get(i).getFreverse2()));
				obj.put("REVERSE3", jutil.setValue(list.get(i).getFreverse3()));
				obj.put("TAIL", jutil.setValue(list.get(i).getFtail()));
				obj.put("PREHEATING_TEMPERATURE", jutil.setValue(list.get(i).getFpreheating_temperature()));
				obj.put("PREHEAT_WAY", jutil.setValue(list.get(i).getfPreheat_way()));
				obj.put("TEMPERATURE_RANGE", jutil.setValue(list.get(i).getFtemperature_range()));
				obj.put("TEMPERATURE", jutil.setValue(list.get(i).getFtemperature()));
				obj.put("SOAKING_TIME", jutil.setValue(list.get(i).getFsoaking_time()));
				obj.put("ELES3", jutil.setValue(list.get(i).getFeles3()));
				obj.put("SCOPE", jutil.setValue(list.get(i).getFscope()));
				obj.put("NOZZLE", jutil.setValue(list.get(i).getFnozzle()));
				obj.put("DISTANCE", jutil.setValue(list.get(i).getFdistance()));
				obj.put("BACK_CHIPPING", jutil.setValue(list.get(i).getFback_chipping()));
				obj.put("LAYER_SCOPE1", jutil.setValue(list.get(i).getFlayer_scope1()));
				obj.put("LAYER_SCOPE2", jutil.setValue(list.get(i).getFlayer_scope2()));
				obj.put("TUNGSTEN_ELECTRODE", jutil.setValue(list.get(i).getFtungsten_electrode()));
				obj.put("TRANSIENT_MODE", jutil.setValue(list.get(i).getFtransient_mode()));
				obj.put("METHOD1", jutil.setValue(list.get(i).getFmethod1()));
				obj.put("METHOD2", jutil.setValue(list.get(i).getFmethod2()));
				obj.put("ITEMID", jutil.setValue(list.get(i).getFitemid()));
				obj.put("ITEMNAME",jutil.setValue(list.get(i).getFitemname()));
				ary.add(obj);
			}
			return JSON.toJSONString(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object findWpsByid(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			Wps list = wm.findById(new BigInteger(json.getString("ID")));
			if(list!=null){
				obj.put("ID", jutil.setValue(list.getFid()));
				obj.put("WPSNO",jutil.setValue(list.getFwpsnum()));
				obj.put("VERSIONS",jutil.setValue(list.getFversions()));
				obj.put("PROJECT_CODE",jutil.setValue(list.getFproject_code()));
				obj.put("REPORT_NUMBER",jutil.setValue(list.getFreport_number()));
				obj.put("DEGREE",jutil.setValue(list.getFdegree()));
				obj.put("EVALUATION_STANDARD",jutil.setValue(list.getFevaluation_standard()));
				obj.put("VALIDITY",jutil.setValue(list.getFvalidity())); 
				obj.put("AUTOMATIC",jutil.setValue(list.getFautomatic()));
				obj.put("GROOVE_TYPE",jutil.setValue(list.getFgroove_type()));
				obj.put("STABILIVOLT_SYSTEM",jutil.setValue(list.getFstabilivolt_system()));
				obj.put("MATERIALS",jutil.setValue(list.getFmaterials()));
				obj.put("THICKNESS1",jutil.setValue(list.getFthickness1()));
				obj.put("THICKNESS2",jutil.setValue(list.getFthickness2()));
				obj.put("DIAMETER",jutil.setValue(list.getFdiameter()));
				obj.put("ELES1",jutil.setValue(list.getFeles1()));
				obj.put("IMAGES_DESC",jutil.setValue(list.getFimages_desc()));
				obj.put("IMAGES_URL", jutil.setValue(list.getFimages_url()));
				obj.put("CATEGORY", jutil.setValue(list.getFcategory()));
				obj.put("SHOP_SIGN", jutil.setValue(list.getFshop_sign()));
				obj.put("SPECIFICATION", jutil.setValue(list.getFspecification()));
				obj.put("MATERIALS_TYPE", jutil.setValue(list.getFmaterials_type()));
				obj.put("MATERIALS_NUMBER1", jutil.setValue(list.getFmaterials_number1()));
				obj.put("MATERIALS_SPECIFICATION1", jutil.setValue(list.getFmaterials_specification1()));
				obj.put("MATERIALS_NUMBER2", jutil.setValue(list.getFmaterials_number2()));
				obj.put("MATERIALS_SPECIFICATION2()", jutil.setValue(list.getFmaterials_specification2()));
				obj.put("SOLDERING_NUMBER", jutil.setValue(list.getFsoldering_number()));
				obj.put("POSITION", jutil.setValue(list.getFposition()));
				obj.put("DIRECTION", jutil.setValue(list.getFdirection()));
				obj.put("ELSE2", jutil.setValue(list.getFelse2()));
				obj.put("FRONT1", jutil.setValue(list.getFfront1()));
				obj.put("FRONT2", jutil.setValue(list.getFfront2()));
				obj.put("REVERSE1", jutil.setValue(list.getFreverse1()));
				obj.put("REVERSE2", jutil.setValue(list.getFreverse2()));
				obj.put("REVERSE3", jutil.setValue(list.getFreverse3()));
				obj.put("TAIL", jutil.setValue(list.getFtail()));
				obj.put("PREHEATING_TEMPERATURE", jutil.setValue(list.getFpreheating_temperature()));
				obj.put("PREHEAT_WAY", jutil.setValue(list.getfPreheat_way()));
				obj.put("TEMPERATURE_RANGE", jutil.setValue(list.getFtemperature_range()));
				obj.put("TEMPERATURE", jutil.setValue(list.getFtemperature()));
				obj.put("SOAKING_TIME", jutil.setValue(list.getFsoaking_time()));
				obj.put("ELES3", jutil.setValue(list.getFeles3()));
				obj.put("SCOPE", jutil.setValue(list.getFscope()));
				obj.put("NOZZLE", jutil.setValue(list.getFnozzle()));
				obj.put("DISTANCE", jutil.setValue(list.getFdistance()));
				obj.put("BACK_CHIPPING", jutil.setValue(list.getFback_chipping()));
				obj.put("LAYER_SCOPE1", jutil.setValue(list.getFlayer_scope1()));
				obj.put("LAYER_SCOPE2", jutil.setValue(list.getFlayer_scope2()));
				obj.put("TUNGSTEN_ELECTRODE", jutil.setValue(list.getFtungsten_electrode()));
				obj.put("TRANSIENT_MODE", jutil.setValue(list.getFtransient_mode()));
				obj.put("METHOD1", jutil.setValue(list.getFmethod1()));
				obj.put("METHOD2", jutil.setValue(list.getFmethod2()));
				obj.put("ITEMID", jutil.setValue(list.getFitemid()));
				obj.put("ITEMNAME",jutil.setValue(list.getFitemname()));
			}
			return JSON.toJSONString(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object addWps(String obj1, String obj2) {
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
			boolean itemflag = true;
			if(hierarchy.equals("4")){
				itemurl = json.getString("ITEMURL");
			}else{
				if(json.getInt("INSFTYPE")==23){
					BigInteger insfid = new BigInteger(json.getString("INSFID"));
					itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
				}else{
					itemflag = false;
				}
			}
			boolean flag = true;
			Wps wps = new Wps();
	       	wps.setFid(id);
			wps.setFwpsnum(json.getString("WPSNUM"));
			wps.setFversions(json.getInt("VERSIONS"));
			wps.setFproject_code(json.getString("PROJECT_CODE"));
			wps.setFreport_number(json.getString("REPORT_NUMBER"));
			wps.setFdegree(json.getString("DEGREE"));
			wps.setFevaluation_standard(json.getString("EVALUATION_STANDARD"));
			wps.setFvalidity(json.getString("VALIDITY"));
			wps.setFautomatic(json.getString("AUTOMATIC"));
			wps.setFgroove_type(json.getString("GROOVE_TYPE"));
			wps.setFstabilivolt_system(json.getString("STABILIVOLT_SYSTEM"));
			wps.setFmaterials(json.getString("MATERIALS"));
			wps.setFthickness1(json.getString("THICKNESS1"));
			wps.setFthickness2(json.getString("THICKNESS2"));
			wps.setFdiameter(json.getString("DIAMETER"));
			wps.setFeles1(json.getString("ELES1"));
			wps.setFimages_url(json.getString("IMAGES_URL"));
			wps.setFimages_desc(json.getString("IMAGES_DESC"));
			wps.setFcategory(json.getString("CATEGORY"));
			wps.setFshop_sign(json.getString("SHOP_SIGN"));
			wps.setFspecification(json.getString("SPECIFICATION"));
			wps.setFmaterials_type(json.getString("materials_type"));
			wps.setFmaterials_number1(json.getString("MATERIALS_NUMBER1"));
			wps.setFmaterials_specification1(json.getString("MATERIALS_SPECIFICATION1"));
			wps.setFmaterials_number2(json.getString("MATERIALS_NUMBER2"));
			wps.setFmaterials_specification2(json.getString("MATERIALS_SPECIFICATION2"));
			wps.setFsoldering_number(json.getString("SOLDERING_NUMBER"));
			wps.setFposition(json.getString("POSITION"));
			wps.setFdirection(json.getString("DIRECTION"));
			wps.setFelse2(json.getString("ELSE2"));
			wps.setFfront1(json.getString("FRONT1"));
			wps.setFfront2(json.getString("FRONT2"));
			wps.setFreverse1(json.getString("REVERSE1"));
			wps.setFreverse2(json.getString("REVERSE2"));
			wps.setFreverse3(json.getString("REVERSE3"));
			wps.setFtail(json.getString("TAIL"));
			wps.setFpreheating_temperature(json.getString("PREHEATING_TEMPERATURE"));
			wps.setFtemperature(json.getString("TEMPERATURE"));
			wps.setfPreheat_way(json.getString("PREHEAT_WAY"));
			wps.setFtemperature_range(json.getString("TEMPERATURE_RANGE"));
			wps.setFsoaking_time(json.getString("SOAKING_TIME"));
			wps.setFeles3(json.getString("ELES3"));
			wps.setFscope(json.getString("SCOPE"));
			wps.setFnozzle(json.getString("NOZZLE"));
			wps.setFdistance(json.getString("DISTANCE"));
			wps.setFback_chipping(json.getString("BACK_CHIPPING"));
			wps.setFlayer_scope1(json.getString("LAYER_SCOPE1"));
			wps.setFlayer_scope2(json.getString("LAYER_SCOPE2"));
			wps.setFtungsten_electrode(json.getString("TUNGSTEN_ELECTRODE"));
			wps.setFtransient_mode(json.getString("TRANSIENT_MODE"));
			wps.setFmethod1(json.getString("METHOD1"));
			wps.setFmethod2(json.getString("METHOD2"));
			wps.setFitemid(new BigInteger(json.getString("INSFID")));
			wps.setFcreator(new BigInteger(json.getString("CREATOR")));
			flag = wm.addWps(wps);
			String childrenstr = "";
			if(flag && json.getString("WELDWPS")!=null && !"".equals(json.getString("WELDWPS"))){
				String obj3 = "{\"CLASSNAME\":\"wpsWebServiceImpl\",\"METHOD\":\"save\"}";
				Object[] childrenobj = blocclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj3,obj2});
				childrenstr = childrenobj[0].toString();
				String[] childrenid = childrenstr.split(",");
				com.alibaba.fastjson.JSONArray ary = com.alibaba.fastjson.JSONArray.parseArray(json.getString("WELDWPS"));
		       	for(int i=0;i<ary.size();i++){
					Wps w = new Wps();
			       	String str = ary.getString(i);
			       	JSONObject js = JSONObject.fromObject(str);
			       	w.setChildrenid(new BigInteger(childrenid[i]));
					w.setFwpsnum(js.getString("WPSNUM"));
					w.setFweld_prechannel(js.getInt("WELDPRECHANNEL"));
					w.setFwelding_method(js.getString("WELDINGMETHOD"));
					w.setFtype(js.getString("TYPE"));
					w.setFchildren_specification(js.getString("SPECIFICATION"));
					w.setFpolarity(js.getString("POLARITY"));
					w.setFweld_i_min(js.getInt("MINELECTRICITY"));
					w.setFweld_i_max(js.getInt("MAXELECTRICITY"));
					w.setFweld_v_min(js.getInt("MINVALTAGE"));
					w.setFweld_v_max(js.getInt("MAXVALTAGE"));
					w.setFweld_i((js.getInt("MINELECTRICITY")+js.getInt("MAXELECTRICITY"))/2);
					w.setFweld_v((js.getInt("MINVALTAGE")+js.getInt("MAXVALTAGE"))/2);
					w.setFweld_alter_i(0);
					w.setFweld_alter_v(0);
					w.setFname(js.getString("WPSNUM"));
					w.setFwelding_speed(js.getString("WELDINGSPEED"));
					w.setFcreater(js.getLong("CREATOR"));
					w.setInsid(new BigInteger(js.getString("INSFID")));
					flag = wm.save(w);
		       	}
			}
			String result = "false";
			if(flag){
				if(!itemflag){
					return true;
				}
				//向项目执行插入
				if(itemurl!=null && !"".equals(itemurl)){
					Client itemclient = dcf.createClient(itemurl);
					jutil.Authority(itemclient);
					obj2 = obj2.substring(0,obj2.length()-1)+",\"ID\":\""+id+"\",\"CHILDRENSTR\":\""+childrenstr+"\"}";
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
	public Object updateWps(String obj1, String obj2) {
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
			boolean itemflag = true;
			if(hierarchy.equals("4")){
				itemurl = json.getString("ITEMURL");
			}else{
				if(json.getInt("INSFTYPE")==23){
					BigInteger insfid = new BigInteger(json.getString("INSFID"));
					itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
				}else{
					itemflag = false;
				}
			}
			Wps wps = new Wps();
			boolean flag = true;
			wps.setFid(new BigInteger(json.getString("ID")));
			wps.setFwpsnum(json.getString("WPSNUM"));
			wps.setFversions(json.getInt("VERSIONS"));
			wps.setFproject_code(json.getString("PROJECT_CODE"));
			wps.setFreport_number(json.getString("REPORT_NUMBER"));
			wps.setFdegree(json.getString("DEGREE"));
			wps.setFevaluation_standard(json.getString("EVALUATION_STANDARD"));
			wps.setFvalidity(json.getString("VALIDITY"));
			wps.setFautomatic(json.getString("AUTOMATIC"));
			wps.setFgroove_type(json.getString("GROOVE_TYPE"));
			wps.setFstabilivolt_system(json.getString("STABILIVOLT_SYSTEM"));
			wps.setFmaterials(json.getString("MATERIALS"));
			wps.setFthickness1(json.getString("THICKNESS1"));
			wps.setFthickness2(json.getString("THICKNESS2"));
			wps.setFdiameter(json.getString("DIAMETER"));
			wps.setFeles1(json.getString("ELES1"));
			wps.setFimages_url(json.getString("IMAGES_URL"));
			wps.setFimages_desc(json.getString("IMAGES_DESC"));
			wps.setFcategory(json.getString("CATEGORY"));
			wps.setFshop_sign(json.getString("SHOP_SIGN"));
			wps.setFspecification(json.getString("SPECIFICATION"));
			wps.setFmaterials_type(json.getString("materials_type"));
			wps.setFmaterials_number1(json.getString("MATERIALS_NUMBER1"));
			wps.setFmaterials_specification1(json.getString("MATERIALS_SPECIFICATION1"));
			wps.setFmaterials_number2(json.getString("MATERIALS_NUMBER2"));
			wps.setFmaterials_specification2(json.getString("MATERIALS_SPECIFICATION2"));
			wps.setFsoldering_number(json.getString("SOLDERING_NUMBER"));
			wps.setFposition(json.getString("POSITION"));
			wps.setFdirection(json.getString("DIRECTION"));
			wps.setFelse2(json.getString("ELSE2"));
			wps.setFfront1(json.getString("FRONT1"));
			wps.setFfront2(json.getString("FRONT2"));
			wps.setFreverse1(json.getString("REVERSE1"));
			wps.setFreverse2(json.getString("REVERSE2"));
			wps.setFreverse3(json.getString("REVERSE3"));
			wps.setFtail(json.getString("TAIL"));
			wps.setFpreheating_temperature(json.getString("PREHEATING_TEMPERATURE"));
			wps.setFtemperature(json.getString("TEMPERATURE"));
			wps.setfPreheat_way(json.getString("PREHEAT_WAY"));
			wps.setFtemperature_range(json.getString("TEMPERATURE_RANGE"));
			wps.setFsoaking_time(json.getString("SOAKING_TIME"));
			wps.setFeles3(json.getString("ELES3"));
			wps.setFscope(json.getString("SCOPE"));
			wps.setFnozzle(json.getString("NOZZLE"));
			wps.setFdistance(json.getString("DISTANCE"));
			wps.setFback_chipping(json.getString("BACK_CHIPPING"));
			wps.setFlayer_scope1(json.getString("LAYER_SCOPE1"));
			wps.setFlayer_scope2(json.getString("LAYER_SCOPE2"));
			wps.setFtungsten_electrode(json.getString("TUNGSTEN_ELECTRODE"));
			wps.setFtransient_mode(json.getString("TRANSIENT_MODE"));
			wps.setFmethod1(json.getString("METHOD1"));
			wps.setFmethod2(json.getString("METHOD2"));
			wps.setFitemid(new BigInteger(json.getString("INSFID")));
			wps.setFmodifier(new BigInteger(json.getString("MODIFIER")));
			flag = wm.updateWps(wps);
			String childrenstr = "";
			if(flag){
				if(json.getString("OLDWELDWPS")!=null && !"".equals(json.getString("OLDWELDWPS"))){
					com.alibaba.fastjson.JSONArray ary = com.alibaba.fastjson.JSONArray.parseArray(json.getString("OLDWELDWPS"));
			       	for(int i=0;i<ary.size();i++){
						Wps w = new Wps();
				       	String str = ary.getString(i);
				       	JSONObject js = JSONObject.fromObject(str);
				       	w.setChildrenid(new BigInteger(js.getString("ID")));
						w.setFwpsnum(js.getString("WPSNUM"));
						w.setFweld_prechannel(js.getInt("WELDPRECHANNEL"));
						w.setFwelding_method(js.getString("WELDINGMETHOD"));
						w.setFtype(js.getString("TYPE"));
						w.setFchildren_specification(js.getString("SPECIFICATION"));
						w.setFpolarity(js.getString("POLARITY"));
						w.setFweld_i_min(js.getInt("MINELECTRICITY"));
						w.setFweld_i_max(js.getInt("MAXELECTRICITY"));
						w.setFweld_v_min(js.getInt("MINVALTAGE"));
						w.setFweld_v_max(js.getInt("MAXVALTAGE"));
						w.setFweld_i((js.getInt("MINELECTRICITY")+js.getInt("MAXELECTRICITY"))/2);
						w.setFweld_v((js.getInt("MINVALTAGE")+js.getInt("MAXVALTAGE"))/2);
						w.setFweld_alter_i(0);
						w.setFweld_alter_v(0);
						w.setFname(js.getString("WPSNUM"));
						w.setFwelding_speed(js.getString("WELDINGSPEED"));
						w.setFupdater(json.getLong("MODIFIER"));
						w.setInsid(new BigInteger(js.getString("INSFID")));
						wm.update(w);
			       	}
				}
				if(json.getString("WELDWPS")!=null && !"".equals(json.getString("WELDWPS"))){
					String obj3 = "{\"CLASSNAME\":\"wpsWebServiceImpl\",\"METHOD\":\"save\"}";
					Object[] childrenobj = blocclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj3,obj2});
					childrenstr = childrenobj[0].toString();
					String[] childrenid = childrenstr.split(",");
					com.alibaba.fastjson.JSONArray ary = com.alibaba.fastjson.JSONArray.parseArray(json.getString("WELDWPS"));
			       	for(int i=0;i<ary.size();i++){
						Wps w = new Wps();
				       	String str = ary.getString(i);
				       	JSONObject js = JSONObject.fromObject(str);
				       	w.setChildrenid(new BigInteger(childrenid[i]));
						w.setFwpsnum(js.getString("WPSNUM"));
						w.setFweld_prechannel(js.getInt("WELDPRECHANNEL"));
						w.setFwelding_method(js.getString("WELDINGMETHOD"));
						w.setFtype(js.getString("TYPE"));
						w.setFchildren_specification(js.getString("SPECIFICATION"));
						w.setFpolarity(js.getString("POLARITY"));
						w.setFweld_i_min(js.getInt("MINELECTRICITY"));
						w.setFweld_i_max(js.getInt("MAXELECTRICITY"));
						w.setFweld_v_min(js.getInt("MINVALTAGE"));
						w.setFweld_v_max(js.getInt("MAXVALTAGE"));
						w.setFweld_i((js.getInt("MINELECTRICITY")+js.getInt("MAXELECTRICITY"))/2);
						w.setFweld_v((js.getInt("MINVALTAGE")+js.getInt("MAXVALTAGE"))/2);
						w.setFweld_alter_i(0);
						w.setFweld_alter_v(0);
						w.setFname(js.getString("WPSNUM"));
						w.setFwelding_speed(js.getString("WELDINGSPEED"));
						w.setFcreater(js.getLong("CREATOR"));
						w.setInsid(new BigInteger(js.getString("INSFID")));
						flag = wm.save(w);
			       	}
				}
			}
			String result = "false";
			if(flag && blocResult.equals("true")){
				if(!itemflag){
					return true;
				}
				if(itemurl!=null && !"".equals(itemurl)){//向项目执行操作
					Client itemclient = dcf.createClient(itemurl);
					jutil.Authority(itemclient);
					obj2 = obj2.substring(0,obj2.length()-1)+",\"CHILDRENSTR\":\""+childrenstr+"\"}";
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
	public Object deleteWps(String obj1, String obj2) {
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
			boolean itemflag = true;
			if(hierarchy.equals("4")){
				itemurl = json.getString("ITEMURL");
			}else{
				if(json.getInt("INSFTYPE")==23){
					BigInteger insfid = new BigInteger(json.getString("INSFID"));
					itemurl = request.getSession().getServletContext().getInitParameter(insfid.toString());
				}else{
					itemflag = false;
				}
			}
			wm.deleteByWpsno(json.getString("WPSNUM"));
			boolean flag = wm.deleteWps(new BigInteger(json.getString("ID")));
			String result = "false";
			if(flag &&  blocResult.equals("true")){
				if(!itemflag){
					return true;
				}
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

}
