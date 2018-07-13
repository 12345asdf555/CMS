package com.spring.service.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.spring.dao.WeldedJunctionMapper;
import com.spring.dto.JudgeUtil;
import com.spring.model.Insframework;
import com.spring.model.WeldedJunction;
import com.spring.service.WeldedJunctionService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Transactional
@Service
public class WeldedJunctionServiceImpl implements WeldedJunctionService {
	@Autowired
	private WeldedJunctionMapper wjm;
	
	private JudgeUtil jutil = new JudgeUtil();
	
	@Override
	public Object getWeldedJunctionAll(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			List<WeldedJunction> list = wjm.getWeldedJunctionAll(json.getString("STR"));
			for(int i=0;i<list.size();i++){
				obj.put("ID", jutil.setValue(list.get(i).getId()));
				obj.put("JUNCTIONNO",jutil.setValue(list.get(i).getWeldedJunctionno()));
				obj.put("SERIALNO",jutil.setValue(list.get(i).getSerialNo()));
				obj.put("PIPELINENO",jutil.setValue(list.get(i).getPipelineNo()));
				obj.put("ROOMNO",jutil.setValue(list.get(i).getRoomNo()));
				obj.put("UNIT",jutil.setValue(list.get(i).getUnit()));
				obj.put("AREA",jutil.setValue(list.get(i).getArea()));
				obj.put("SYSTEMS",jutil.setValue(list.get(i).getSystems()));
				obj.put("CHILDREN",jutil.setValue(list.get(i).getChildren()));
				obj.put("DYNE",jutil.setValue(list.get(i).getDyne()));
				obj.put("SPECIFICATION",jutil.setValue(list.get(i).getSpecification()));
				obj.put("MAXELECTRICITY",jutil.setValue(list.get(i).getMaxElectricity()));
				obj.put("MINELECTRICITY",jutil.setValue(list.get(i).getMinElectricity()));
				obj.put("MAXVALTAGE",jutil.setValue(list.get(i).getMaxValtage()));
				obj.put("MINVALTAGE",jutil.setValue(list.get(i).getMinValtage()));
				obj.put("MATERIAL",jutil.setValue(list.get(i).getMaterial()));
				obj.put("NEXTMATERIAL",jutil.setValue(list.get(i).getNext_material()));
				obj.put("EXTERNALDIAMETER",jutil.setValue(list.get(i).getExternalDiameter()));
				obj.put("NEXTEXTERNALDIAMETER",jutil.setValue(list.get(i).getNextexternaldiameter()));
				obj.put("WALLTHICKNESS",jutil.setValue(list.get(i).getWallThickness()));
				obj.put("NEXTWALLTHICKNESS",jutil.setValue(list.get(i).getNextwall_thickness()));
				obj.put("ELECTRICITYUNIT",jutil.setValue(list.get(i).getElectricity_unit()));
				obj.put("VALTAGEUNIT",jutil.setValue(list.get(i).getValtage_unit()));
				obj.put("STARTTIME",jutil.setValue(list.get(i).getStartTime()));
				obj.put("ENDTIME",jutil.setValue(list.get(i).getEndTime()));
				obj.put("CREATETIME",jutil.setValue(list.get(i).getCreatTime()));
				obj.put("UPDATETIME",jutil.setValue(list.get(i).getUpdateTime()));
				obj.put("UPDATECOUNT",jutil.setValue(list.get(i).getUpdatecount()));
				obj.put("ITEMID",jutil.setValue(list.get(i).getItemid().getId()));
				obj.put("ITEMNAME",jutil.setValue(list.get(i).getItemid().getName()));
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object getWeldedJunctionById(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			WeldedJunction list = wjm.getWeldedJunctionById(new BigInteger(json.getString("ID")));
			if(list!=null){
				obj.put("ID", jutil.setValue(list.getId()));
				obj.put("JUNCTIONNO",jutil.setValue(list.getWeldedJunctionno()));
				obj.put("SERIALNO",jutil.setValue(list.getSerialNo()));
				obj.put("PIPELINENO",jutil.setValue(list.getPipelineNo()));
				obj.put("ROOMNO",jutil.setValue(list.getRoomNo()));
				obj.put("UNIT",jutil.setValue(list.getUnit()));
				obj.put("AREA",jutil.setValue(list.getArea()));
				obj.put("SYSTEMS",jutil.setValue(list.getSystems()));
				obj.put("CHILDREN",jutil.setValue(list.getChildren()));
				obj.put("DYNE",jutil.setValue(list.getDyne()));
				obj.put("SPECIFICATION",jutil.setValue(list.getSpecification()));
				obj.put("MAXELECTRICITY",jutil.setValue(list.getMaxElectricity()));
				obj.put("MINELECTRICITY",jutil.setValue(list.getMinElectricity()));
				obj.put("MAXVALTAGE",jutil.setValue(list.getMaxValtage()));
				obj.put("MINVALTAGE",jutil.setValue(list.getMinValtage()));
				obj.put("MATERIAL",jutil.setValue(list.getMaterial()));
				obj.put("NEXTMATERIAL",jutil.setValue(list.getNext_material()));
				obj.put("EXTERNALDIAMETER",jutil.setValue(list.getExternalDiameter()));
				obj.put("NEXTEXTERNALDIAMETER",jutil.setValue(list.getNextexternaldiameter()));
				obj.put("WALLTHICKNESS",jutil.setValue(list.getWallThickness()));
				obj.put("NEXTWALLTHICKNESS",jutil.setValue(list.getNextwall_thickness()));
				obj.put("ELECTRICITYUNIT",jutil.setValue(list.getElectricity_unit()));
				obj.put("VALTAGEUNIT",jutil.setValue(list.getValtage_unit()));
				obj.put("STARTTIME",jutil.setValue(list.getStartTime()));
				obj.put("ENDTIME",jutil.setValue(list.getEndTime()));
				obj.put("CREATETIME",jutil.setValue(list.getCreatTime()));
				obj.put("UPDATETIME",jutil.setValue(list.getUpdateTime()));
				obj.put("UPDATECOUNT",jutil.setValue(list.getUpdatecount()));
				obj.put("ITEMID",jutil.setValue(list.getItemid().getId()));
				obj.put("ITEMNAME",jutil.setValue(list.getItemid().getName()));
			}
			return JSON.toJSONString(obj);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getWeldedjunctionByNo(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return wjm.getWeldedjunctionByNo(json.getString("JUNCTIONNO"));
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public BigInteger addJunction(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			WeldedJunction wj = new WeldedJunction();
			wj.setWeldedJunctionno(json.getString("JUNCTIONNO"));
			wj.setSerialNo(json.getString("SERIALNO"));
			wj.setPipelineNo(json.getString("PIPELINENO"));
			wj.setRoomNo(json.getString("ROOMNO"));
			wj.setUnit(json.getString("UNIT"));
			wj.setArea(json.getString("AREA"));
			wj.setSystems(json.getString("SYSTEMS"));
			wj.setChildren(json.getString("CHILDREN"));
			wj.setDyne(json.getInt("DYNE"));
			wj.setSpecification(json.getString("SPECIFICATION"));
			wj.setMaxElectricity(json.getDouble("MAXELECTRICITY"));
			wj.setMinElectricity(json.getDouble("MINELECTRICITY"));
			wj.setMaxValtage(json.getDouble("MAXVALTAGE"));
			wj.setMinValtage(json.getDouble("MINVALTAGE"));
			wj.setMaterial(json.getString("MATERIAL"));
			wj.setNext_material(json.getString("NEXTMATERIAL"));
			wj.setExternalDiameter(json.getString("EXTERNALDIAMETER"));
			wj.setNextexternaldiameter(json.getString("NEXTEXTERNALDIAMETER"));
			wj.setWallThickness(json.getString("WALLTHICKNESS"));
			wj.setNextwall_thickness(json.getString("NEXTWALLTHICKNESS"));
			wj.setElectricity_unit(json.getString("ELECTRICITYUNIT"));
			wj.setValtage_unit(json.getString("VALTAGEUNIT"));
			wj.setStartTime(json.getString("STARTTIME"));
			String starttime =json.getString("STARTTIME");
			if(starttime!=null && !"".equals(starttime)){
				wj.setStartTime(starttime);
			}
			String endtime =json.getString("ENDTIME");
			if(endtime!=null && !"".equals(endtime)){
				wj.setEndTime(endtime);
			}
			wj.setCreator(json.getString("CREATOR"));
			Insframework itemid = new Insframework();
			itemid.setId(new BigInteger(json.getString("INSFID")));
			wj.setItemid(itemid);
			if(wjm.addJunction(wj)){
				return wj.getId();
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateJunction(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			WeldedJunction wj = new WeldedJunction();
			wj.setId(new BigInteger(json.getString("ID")));
			wj.setWeldedJunctionno(json.getString("JUNCTIONNO"));
			wj.setSerialNo(json.getString("SERIALNO"));
			wj.setPipelineNo(json.getString("PIPELINENO"));
			wj.setRoomNo(json.getString("ROOMNO"));
			wj.setUnit(json.getString("UNIT"));
			wj.setArea(json.getString("AREA"));
			wj.setSystems(json.getString("SYSTEMS"));
			wj.setChildren(json.getString("CHILDREN"));
			wj.setDyne(json.getInt("DYNE"));
			wj.setSpecification(json.getString("SPECIFICATION"));
			wj.setMaxElectricity(json.getDouble("MAXELECTRICITY"));
			wj.setMinElectricity(json.getDouble("MINELECTRICITY"));
			wj.setMaxValtage(json.getDouble("MAXVALTAGE"));
			wj.setMinValtage(json.getDouble("MINVALTAGE"));
			wj.setMaterial(json.getString("MATERIAL"));
			wj.setNext_material(json.getString("NEXTMATERIAL"));
			wj.setExternalDiameter(json.getString("EXTERNALDIAMETER"));
			wj.setNextexternaldiameter(json.getString("NEXTEXTERNALDIAMETER"));
			wj.setWallThickness(json.getString("WALLTHICKNESS"));
			wj.setNextwall_thickness(json.getString("NEXTWALLTHICKNESS"));
			wj.setElectricity_unit(json.getString("ELECTRICITYUNIT"));
			wj.setValtage_unit(json.getString("VALTAGEUNIT"));
			String starttime =json.getString("STARTTIME");
			if(starttime!=null && !"".equals(starttime)){
				wj.setStartTime(starttime);
			}
			String endtime =json.getString("ENDTIME");
			if(endtime!=null && !"".equals(endtime)){
				wj.setEndTime(endtime);
			}
			wj.setModifier(json.getString("MODIFITER"));
			Insframework itemid = new Insframework();
			itemid.setId(new BigInteger(json.getString("INSFID")));
			wj.setItemid(itemid);
			return wjm.updateJunction(wj);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteJunction(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return wjm.deleteJunction(new BigInteger(json.getString("ID")));
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

}
