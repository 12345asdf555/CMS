package com.spring.service.impl;

import java.math.BigInteger;
import java.util.List;

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
				obj.put("TYPE",jutil.setValue(list.get(i).getType()));
				obj.put("CODEID",jutil.setValue(list.get(i).getCodeid()));
				obj.put("TYPEID",jutil.setValue(list.get(i).getTypeid()));
				obj.put("MACHINEID",jutil.setValue(list.get(i).getMachineid()));
				obj.put("MACHINENO",jutil.setValue(list.get(i).getMachineno()));
				obj.put("ITEMID",jutil.setValue(list.get(i).getItemid()));
				obj.put("TIME",jutil.setValue(list.get(i).getTime()));
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
				obj.put("TYPE",jutil.setValue(list.getType()));
				obj.put("CODEID",jutil.setValue(list.getCodeid()));
				obj.put("TYPEID",jutil.setValue(list.getTypeid()));
				obj.put("MACHINEID",jutil.setValue(list.getMachineid()));
				obj.put("MACHINENO",jutil.setValue(list.getMachineno()));
				obj.put("ITEMID",jutil.setValue(list.getItemid()));
				obj.put("TIME",jutil.setValue(list.getTime()));
			}
			return JSON.toJSONString(obj);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public BigInteger addFault(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			Fault f = new Fault();
			f.setCodeid(json.getInt("CODEID"));
			f.setTime(json.getString("TIME"));
			f.setTypeid(json.getInt("TYPEID"));
			f.setMachineid(new BigInteger(json.getString("MACHINEID")));
			if(fm.addFault(f)){
				return f.getId();
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean editFault(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			Fault f = new Fault();
			f.setId(new BigInteger(json.getString("ID")));
			f.setCodeid(json.getInt("CODEID"));
			f.setTime(json.getString("TIME"));
			f.setTypeid(json.getInt("TYPEID"));
			f.setMachineid(new BigInteger(json.getString("MACHINEID")));
			return fm.editFault(f);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteFault(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return fm.deleteFault(new BigInteger(json.getString("ID")));
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
