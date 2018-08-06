package com.spring.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.jws.WebService;

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
@WebService
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
	public boolean save(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
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
			w.setInsid(new BigInteger(json.getString("INSFID")));
			w.setFback(json.getString("BACK"));
			w.setFcreater(json.getLong("CREATOR"));
			return wm.save(w);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
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
			w.setInsid(new BigInteger(json.getString("INSFID")));
			w.setFback(json.getString("BACK"));
			w.setFupdater(json.getLong("MODIFIER"));
			return wm.update(w);
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
	public boolean delete(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return wm.delete(new BigInteger(json.getString("ID")));
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

}
