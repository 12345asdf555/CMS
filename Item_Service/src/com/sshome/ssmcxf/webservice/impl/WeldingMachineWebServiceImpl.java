package com.sshome.ssmcxf.webservice.impl;

import java.math.BigInteger;
import java.util.List;

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
	public boolean addWeldingMachine(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			WeldingMachine wm = new WeldingMachine();
			wm.setId(new BigInteger(json.getString("ID")));
			wm.setEquipmentNo(json.getString("EQUIPMENTNO"));
			wm.setPosition(json.getString("POSITION"));
			wm.setIsnetworking(json.getInt("ISNETWORKING"));
			String jointime = json.getString("JOINTIME");
			if(jointime!=null && !"".equals(jointime)){
				wm.setJoinTime(jointime);
			}
			wm.setMoney(json.getInt("MONEY"));
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
			return wms.addWeldingMachine(wm);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean editWeldingMachine(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			WeldingMachine wm = new WeldingMachine();
			wm.setId(new BigInteger(json.getString("ID")));
			wm.setEquipmentNo(json.getString("EQUIPMENTNO"));
			wm.setPosition(json.getString("POSITION"));
			wm.setIsnetworking(json.getInt("ISNETWORKING"));
			String jointime = json.getString("JOINTIME");
			if(jointime!=null && !"".equals(jointime)){
				wm.setJoinTime(jointime);
			}
			wm.setTypeId(json.getInt("TYPEID"));
			wm.setMoney(json.getInt("MONEY"));
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
			if(flag){
				//修改焊机状态为启用时，结束所有维修任务
				int sid = wm.getStatusId();
				if(sid == 31){
					List<WeldingMaintenance> list = ms.getEndtime(wm.getId());
					for(WeldingMaintenance w : list){
							ms.updateEndtime(w.getId());
					}
				}
			}
			return flag;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteWeldingChine(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			if(wms.deleteWeldingChine(new BigInteger(json.getString("WID")))){
				List<WeldingMaintenance> list = ms.getMaintainByWeldingMachinId(new BigInteger(json.getString("WID")));
				for(WeldingMaintenance wm : list){
					//删除维修记录
					ms.deleteWeldingMaintenance(wm.getId());
					ms.deleteMaintenanceRecord(wm.getMaintenance().getId());
				}
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
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
	public String getGatheridMachine(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			BigInteger itemid = new BigInteger(json.getString("INSFID"));
			String gather = json.getString("GATHERNO");
			return wms.getGatheridMachine(itemid, gather);
		}catch(Exception e){
			return null;
		}
	}

}