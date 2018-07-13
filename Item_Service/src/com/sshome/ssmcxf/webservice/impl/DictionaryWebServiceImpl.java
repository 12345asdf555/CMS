package com.sshome.ssmcxf.webservice.impl;

import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.spring.dto.JudgeUtil;
import com.spring.model.Dictionarys;
import com.spring.service.DictionaryService;
import com.sshome.ssmcxf.webservice.DictionaryWebService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Transactional
@Service
public class DictionaryWebServiceImpl implements DictionaryWebService{

	@Autowired
	private DictionaryService ds;
	
	private JudgeUtil jutil = new JudgeUtil();
	
	@Override
	public Object getAllDictionary(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			List<Dictionarys> list = ds.getAllDictionary(json.getString("STR"));
			for(int i=0;i<list.size();i++){
				obj.put("ID", list.get(i).getId());
				obj.put("TYPEID",jutil.setValue(list.get(i).getTypeid()));
				obj.put("VALUE",jutil.setValue(list.get(i).getValue()));
				obj.put("VALUENAME", jutil.setValue(list.get(i).getValueName()));
				obj.put("BACK",jutil.setValue(list.get(i).getBack()));
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public boolean addDictionary(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			Dictionarys d = new Dictionarys();
			d.setId(new BigInteger(json.getString("ID")));
			d.setBack(json.getString("BACK"));
			d.setTypeid(json.getInt("TYPEID"));
			d.setValueName(json.getString("VALUENAME"));
			d.setCreator(json.getString("CREATOR"));
			return ds.addDictionary(d);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean editDictionary(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			Dictionarys d = new Dictionarys();
			d.setId(new BigInteger(json.getString("ID")));
			d.setBack(json.getString("BACK"));
			d.setTypeid(json.getInt("TYPEID"));
			d.setValueName(json.getString("VALUENAME"));
			d.setModifier(json.getString("MODIFIER"));
			return ds.editDictionary(d);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Object getDictionaryByFid(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			Dictionarys list = ds.getDictionaryByFid(json.getInt("ID"));
			if(list!=null){
				obj.put("ID", list.getId());
				obj.put("TYPEID",jutil.setValue(list.getTypeid()));
				obj.put("VALUE",jutil.setValue(list.getValue()));
				obj.put("VALUENAME", jutil.setValue(list.getValueName()));
				obj.put("BACK",jutil.setValue(list.getBack()));
			}
			return JSON.toJSONString(obj);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public boolean deleteDictionary(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return ds.deleteDictionary(json.getInt("ID"));
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Object getDictionaryValue(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			List<Dictionarys> list = ds.getDictionaryValue(json.getInt("TYPEID"));
			for(int i=0;i<list.size();i++){
				obj.put("VALUE",jutil.setValue(list.get(i).getValue()));
				obj.put("VALUENAME", jutil.setValue(list.get(i).getValueName()));
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getDicValueByValue(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			List<Dictionarys> list = ds.getDicValueByValue(json.getInt("TYPEID"), json.getInt("VALUE"));
			for(int i=0;i<list.size();i++){
				obj.put("VALUE",jutil.setValue(list.get(i).getValue()));
				obj.put("VALUENAME", jutil.setValue(list.get(i).getValueName()));
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public int getvaluebyname(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return ds.getvaluebyname(json.getInt("TYPEID"), json.getString("VALUENAME"));
		}catch(Exception e){
			return -1;
		}
	}

	@Override
	public Object getDicValueByType(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return ds.getDicValueByType(json.getInt("VALUEID"));
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getBack() {
		try{
			JSONObject obj = new JSONObject();
			JSONArray ary = new JSONArray();
			List<Dictionarys> list = ds.getBack();
			for(int i=0;i<list.size();i++){
				obj.put("TYPEID",jutil.setValue(list.get(i).getTypeid()));
				obj.put("BACK",jutil.setValue(list.get(i).getBack()));
				ary.add(obj);
			}
			return JSON.toJSONString(ary);
		}catch(Exception e){
			return null;
		}
	}

}
