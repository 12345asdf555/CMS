package com.spring.service.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dao.ChartMapper;
import com.spring.dao.WeldingMachineMapper;
import com.spring.dto.JudgeUtil;
import com.spring.dto.ModelDto;
import com.spring.service.ChartService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class ChartServiceImpl implements ChartService {
	@Autowired
	private ChartMapper cm;
	@Autowired
	private WeldingMachineMapper wm;
	
	private JudgeUtil jutil = new JudgeUtil();
	
	@Override
	public Object getEfficiency(String object) {
		JSONArray ary = new JSONArray();
		JSONObject json = new JSONObject();
		try{
			JSONObject obj = JSONObject.fromObject(object);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf.format(new Date(obj.getString("TIME")));
			List<ModelDto> list = cm.getEfficiency(time);
			for(ModelDto m : list){
				json.put("CAUSTNAME",jutil.setValue(m.getFname()));
				json.put("ITEMNAME",jutil.setValue(m.getIname()));
				json.put("WELDERNAME",jutil.setValue(m.getWname()));
				json.put("WELDERNO",jutil.setValue(m.getFwelder_id()));
				String[] str = m.getJidgather().split(",");
				String search = "and (";
				for(int i=0;i<str.length;i++){
					search += " fid = "+str[i];
					if(i<str.length-1){
						search += " or";
					}
				}
				search += " )";
				BigInteger dyne = cm.getDyneByJunctionno(search);
				json.put("DYNE",jutil.setValue(dyne));
				double weldtime = (double)Math.round(Double.valueOf(m.getWeldTime())*100)/100;
				json.put("WELDTIME",jutil.setValue(weldtime));
				json.put("NUM",jutil.setValue(str.length));
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(ary.isEmpty()){
			ary.add("{\"CAUSTNAME\":\"\",\"ITEMNAME\":\"\",\"WELDERNAME\":\"\",\"WELDERNO\":\"\",\"DYNE\":\"\",\"WELDTIME\":\"\",\"NUM\":\"\"}");
		}
		return ary.toString();
	}

	@Override
	public Object getHour(String object) {
		JSONArray ary = new JSONArray();
		JSONObject json = new JSONObject();
		try{
			JSONObject obj = JSONObject.fromObject(object);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf.format(new Date(obj.getString("TIME")));
			List<ModelDto> list = cm.getHour(time);
			for(ModelDto l:list){
				json.put("CAUSTNAME",jutil.setValue(l.getIname()));
				json.put("ITEMNAME",jutil.setValue(l.getFname()));
				String[] str = l.getJidgather().split(",");
				if(l.getJidgather().equals("0")){
					json.put("JUNCTIONNUM", "0");
					json.put("AVGTIME", 0);
					json.put("DYNE",0);
				}else{
					json.put("JUNCTIONNUM", str.length);
					double hour = (double)Math.round(l.getHous().doubleValue()/str.length*100)/100;
					json.put("AVGTIME", hour);
					String strsql = "and (";
					for(int i=0;i<str.length;i++){
						strsql += " fid = "+str[i];
						if(i<str.length-1){
							strsql += " or";
						}
					}
					strsql += " )";
					BigInteger dyne = cm.getDyneByJunctionno(strsql);
					json.put("DYNE",dyne);
				}
				json.put("MATERIAL",jutil.setValue(l.getMaterial()));
				json.put("NEXTMATERIAL",jutil.setValue(l.getNextmaterial()));
				json.put("WALLTHICKNESS",jutil.setValue(l.getWallThickness()));
				json.put("NEXTWALLTHICKNESS",jutil.setValue(l.getNextwallThickness()));
				json.put("EXTERNALDIAMETER",jutil.setValue(l.getExternalDiameter()));
				json.put("NEXTEXTERNALDIAMETER",jutil.setValue(l.getNextexternaldiameter()));
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(ary.isEmpty()){
			ary.add("{\"BLOCNAME\":\"\",\"ITEMNAME\":\"\",\"JUNCTIONNUM\":\"\",\"AVGTIME\":\"\",\"DYNE\":\"\","
					+ "\"MATERIAL\":\"\",\"NEXTMATERIAL\":\"\",\"WALLTHICKNESS\":\"\",\"NEXTWALLTHICKNESS\":\"\",\"EXTERNALDIAMETER\":\"\",\"NEXTEXTERNALDIAMETER\":\"\"}");
		}
		return ary.toString();
	}

	@Override
	public Object getOvertime(String object) {
		JSONArray ary = new JSONArray();
		JSONObject json = new JSONObject();
		try{
			JSONObject obj = JSONObject.fromObject(object);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf.format(new Date(obj.getString("TIME")));
			List<ModelDto> insf = cm.getAllInsf();
			List<ModelDto> list = cm.getOvertime(time, obj.getInt("OVERTIME"));
			for(ModelDto ins: insf){
				int num = 0;
				for(ModelDto l:list){
					if(ins.getFid().equals(l.getFid())){
						num = Integer.parseInt(l.getOvertime());
					}
				}
				json.put("CAUSTNAME", jutil.setValue(ins.getFname()));
				json.put("ITEMNAME", jutil.setValue(ins.getIname()));
				json.put("WELDTIME",time);
				json.put("OVERTIME", num);
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(ary.isEmpty()){
			ary.add("{\"CAUSTNAME\":\"\",\"ITEMNAME\":\"\",\"WELDTIME\":\"\",\"OVERTIME\":\"\"}");
		}
		return ary.toString();
	}

	@Override
	public Object getOverproof(String object) {
		JSONArray ary = new JSONArray();
		JSONObject json = new JSONObject();
		try{
			JSONObject obj = JSONObject.fromObject(object);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf.format(new Date(obj.getString("TIME")));
			List<ModelDto> insf = cm.getAllInsf();
			List<ModelDto> list = cm.getOverproof(time);
			for(ModelDto ins: insf){
				BigInteger num = new BigInteger("0");
				for(ModelDto l:list){
					if(ins.getFid().equals(l.getFid())){
						num = l.getOverproof();
					}
				}
				json.put("CAUSTNAME", jutil.setValue(ins.getFname()));
				json.put("ITEMNAME", jutil.setValue(ins.getIname()));
				json.put("WELDTIME",time);
				json.put("OVERPROOF", num);
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(ary.isEmpty()){
			ary.add("{\"CAUSTNAME\":\"\",\"ITEMNAME\":\"\",\"WELDTIME\":\"\",\"OVERPROOF\":\"\"}");
		}
		return ary.toString();
	}

	@Override
	public Object getLoads(String object) {
		JSONArray ary = new JSONArray();
		JSONObject json = new JSONObject();
		try{
			JSONObject obj = JSONObject.fromObject(object);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf.format(new Date(obj.getString("TIME")));
			List<ModelDto> insf = cm.getAllInsf();
			List<ModelDto> list = cm.getLoads(time);
			List<ModelDto> machine = cm.getMachineCount(time,1);
			for(ModelDto ins: insf){
				double num = 0,worktime = 0,workmachine = 0;
				for(ModelDto l:list){
					for(ModelDto m:machine){
						if(m.getFid().equals(l.getIid()) && l.getIid().equals(ins.getFid())){
							num = (double)Math.round(l.getLoads()/m.getLoads()*10000)/10000;
							workmachine = m.getLoads();
							worktime = l.getLoads();
						}
					}
				}
				json.put("CAUSTNAME", jutil.setValue(ins.getFname()));
				json.put("ITEMNAME", jutil.setValue(ins.getIname()));
				json.put("WELDTIME",time);
				json.put("LOADS", num);
				json.put("WORKTIME", (double) Math.round(Double.valueOf(worktime)*1000)/1000);
				json.put("WORKMACHINE", workmachine);
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(ary.isEmpty()){
			ary.add("{\"CAUSTNAME\":\"\",\"ITEMNAME\":\"\",\"WELDTIME\":\"\",\"LOADS\":\"\",\"WORKTIME\":\"\",\"WORKMACHINE\":\"\"}");
		}
		return ary.toString();
	}

	@Override
	public Object getNoLoads(String object) {
		JSONArray ary = new JSONArray();
		JSONObject json = new JSONObject();
		try{
			JSONObject obj = JSONObject.fromObject(object);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf.format(new Date(obj.getString("TIME")));
			List<ModelDto> insf = cm.getAllInsf();
			List<ModelDto> list = cm.getNoLoads(time);
			List<ModelDto> machine = cm.getMachineCount(time,0);
			for(ModelDto ins: insf){
				double num = 0,worktime = 0,workmachine = 0,sumtime = 0;
				for(ModelDto l:list){
					for(ModelDto m:machine){
						if(m.getFid().equals(l.getIid()) && l.getIid().equals(ins.getFid())){
							sumtime = cm.getCountByTime(time, l.getIid()).doubleValue();
							num = (double)Math.round(l.getLoads()/sumtime/m.getLoads()*10000)/10000;
							workmachine = m.getLoads();
							worktime = l.getLoads();
						}
					}
				}
				json.put("CAUSTNAME", jutil.setValue(ins.getFname()));
				json.put("ITEMNAME", jutil.setValue(ins.getIname()));
				json.put("WELDTIME",time);
				json.put("NOLOADS", num);
				json.put("STANDBYTIME", (double) Math.round(Double.valueOf(worktime)*1000)/1000);
				json.put("STANDBYMACHINE", workmachine);
				json.put("TOTALTIME", (double) Math.round(Double.valueOf(sumtime)*1000)/1000);
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(ary.isEmpty()){
			ary.add("{\"CAUSTNAME\":\"\",\"ITEMNAME\":\"\",\"WELDTIME\":\"\",\"NOLOADS\":\"\",\"STANDBYTIME\":\"\",\"STANDBYMACHINE\":\"\",\"TOTALTIME\":\"\"}");
		}
		return ary.toString();
	}

	@Override
	public Object getIdle(String object) {
		JSONArray ary = new JSONArray();
		JSONObject json = new JSONObject();
		try{
			JSONObject obj = JSONObject.fromObject(object);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf.format(new Date(obj.getString("TIME")));
			List<ModelDto> insf = cm.getAllInsf();
			List<ModelDto> list = cm.getIdle(time);
			for(ModelDto ins: insf){
				double num = 0;
				int count = cm.getMachineCountByIns(ins.getFid());
				for(ModelDto l:list){
					if(ins.getFid().equals(l.getFid())){
						num = count - l.getNum().doubleValue();
					}
				}
				json.put("CAUSTNAME", jutil.setValue(ins.getFname()));
				json.put("ITEMNAME", jutil.setValue(ins.getIname()));
				json.put("WELDTIME",time);
				json.put("IDLE", num);
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(ary.isEmpty()){
			ary.add("{\"CAUSTNAME\":\"\",\"ITEMNAME\":\"\",\"WELDTIME\":\"\",\"IDLE\":\"\"}");
		}
		return ary.toString();
	}

	@Override
	public Object getUse(String object) {
		JSONArray ary = new JSONArray();
		JSONObject json = new JSONObject();
		try{
			JSONObject obj = JSONObject.fromObject(object);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf.format(new Date(obj.getString("TIME")));
			List<ModelDto> list = cm.getUse(time);
			for(ModelDto i: list){
				double num = wm.getMachineCountByManu(i.getEid(),i.getIid()).doubleValue();
				json.put("CAUSTNAME", jutil.setValue(i.getFname()));
				json.put("ITEMNAME", jutil.setValue(i.getIname()));
				json.put("WELDTIME",time);
				json.put("AVGTIME",  (double)Math.round(i.getTime()/num*100)/100);
				json.put("MANUFACTURERSNAME", i.getWname());
				json.put("MANUFACTURERSTYPE", i.getType());
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(ary.isEmpty()){
			ary.add("{\"CAUSTNAME\":\"\",\"ITEMNAME\":\"\",\"WELDTIME\":\"\",\"AVGTIME\":\"\",\"MANUFACTURERSNAME\":\"\",\"MANUFACTURERSTYPE\":\"\"}");
		}
		return ary.toString();
	}

}
