package com.sshome.ssmcxf.webservice.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dto.JudgeUtil;
import com.spring.dto.ModelDto;
import com.spring.dto.WeldDto;
import com.spring.model.Insframework;
import com.spring.model.LiveData;
import com.spring.service.InsframeworkService;
import com.spring.service.LiveDataService;
import com.sshome.ssmcxf.webservice.LiveDataWebService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Transactional
@Service
public class LiveDataWebServiceImpl implements LiveDataWebService {
	@Autowired
	private LiveDataService live;
	@Autowired
	private InsframeworkService insf;
	
	private JudgeUtil jutil = new JudgeUtil();
	
	@Override
	public Object getCausehour(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setSearch(json.getString("STR"));
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getCausehour(dto, new BigInteger(json.getString("INSFID")));
			for(ModelDto d:list){
				obj.put("HOUS", jutil.setValue(d.getHous()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				obj.put("JUNCTIONIDTEAM", jutil.setValue(d.getJidgather()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object getCompanyhour(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setSearch(json.getString("STR"));
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list =  live.getCompanyhour(dto, new BigInteger(json.getString("COMPANYINSFID")));
			for(ModelDto d:list){
				obj.put("HOUS", jutil.setValue(d.getHous()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				obj.put("JUNCTIONIDTEAM", jutil.setValue(d.getJidgather()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getItemhour(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setSearch(json.getString("STR"));
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			dto.setParent(new BigInteger(json.getString("INSFID")));
			List<ModelDto>list = live.getItemhour(dto);
			for(ModelDto d:list){
				obj.put("HOUS", jutil.setValue(d.getHous()));
				obj.put("MATERIAL", jutil.setValue(d.getMaterial()));
				obj.put("NEXTMATERIAL", jutil.setValue(d.getNextmaterial()));
				obj.put("EXTERNALDIAMETER", jutil.setValue(d.getExternalDiameter()));
				obj.put("NEXTEXTERNALDIAMETER", jutil.setValue(d.getNextexternaldiameter()));
				obj.put("WALLTHICKNESS", jutil.setValue(d.getWallThickness()));
				obj.put("NEXTWALLTHICKNESS", jutil.setValue(d.getNextwallThickness()));
				obj.put("INSFID", jutil.setValue(d.getItemid()));
				obj.put("JUNCTIONIDTEAM", jutil.setValue(d.getJidgather()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getJunctionHous(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			dto.setDtoItem(new BigInteger(json.getString("ITEMINSFID")));
			dto.setDtoMaterial(json.getString("MATERIAL"));
			dto.setDtoExternalDiameter(json.getString("EXTERNALDIAMETER"));
			dto.setDtoWallThickness(json.getString("WALLTHICKNESS"));
			dto.setDtoNextExternalDiameter(json.getString("NEXTEXTERNALDIAMETER"));
			dto.setNextmaterial(json.getString("NEXTMATERIAL"));
			dto.setNextwallthickness(json.getString("NEXTWALLTHICKNESS"));
			List<ModelDto> list = live.getJunctionHous(dto);
			for(ModelDto d:list){
				obj.put("HOUS", jutil.setValue(d.getHous()));
				obj.put("DYNE", jutil.setValue(d.getDyne()));
				obj.put("JUNCTIONNO", jutil.setValue(d.getFname()));
				obj.put("JUNCTIONID", jutil.setValue(d.getFid()));
				obj.put("STARTTIME", jutil.setValue(d.getStarttime()));
				obj.put("ENDTIME", jutil.setValue(d.getEndtime()));
				obj.put("INSFNAME", jutil.setValue(d.getIname()));
				obj.put("WELDERTEAM", jutil.setValue(d.getFwelder_id()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getCauseOverproof(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getCauseOverproof(dto, new BigInteger(json.getString("INSFID")));
			for(ModelDto d:list){
				obj.put("OVERPROOF", jutil.setValue(d.getOverproof()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object getItemOverproof(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getItemOverproof(dto, new BigInteger(json.getString("ITEMINSFID")));
			for(ModelDto d:list){
				obj.put("OVERPROOF", jutil.setValue(d.getOverproof()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getAllInsf(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			List<LiveData> list = live.getAllInsf(new BigInteger(json.getString("INSFID")), json.getInt("TYPEID"));
			for(LiveData d:list){
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getAllTime(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getAllTime(dto);
			for(ModelDto d:list){
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getCompanyOverproof(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getCompanyOverproof(dto, new BigInteger(json.getString("COMPANYINSFID")));
			for(ModelDto d:list){
				obj.put("OVERPROOF", jutil.setValue(d.getOverproof()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}
	
	@Override
	public Object getcompanyOvertime(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getcompanyOvertime(dto, json.getString("NUMBER"), new BigInteger(json.getString("COMPANYINSFID")));
			for(ModelDto d:list){
				obj.put("OVERTIME", jutil.setValue(d.getOvertime()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object getCaustOvertime(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getCaustOvertime(dto, json.getString("NUMBER"), new BigInteger(json.getString("INSFID")));
			for(ModelDto d:list){
				obj.put("OVERTIME", jutil.setValue(d.getOvertime()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getItemOvertime(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			dto.setParent(new BigInteger(json.getString("ITEMINSFID")));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getItemOvertime(dto, json.getString("NUMBER"));
			for(ModelDto d:list){
				obj.put("OVERTIME", jutil.setValue(d.getOvertime()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getJunction(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			List<LiveData> list = live.getJunction(new BigInteger(json.getString("INSFID")));
			for(LiveData d:list){
				obj.put("ID", jutil.setValue(d.getFid()));
				obj.put("JUNCTIONNO", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getItemid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getDetailovertime(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getDetailovertime(dto, json.getString("NUMBER"),json.getString("ITEMINSFID"));
			for(ModelDto d:list){
				obj.put("OVERTIME", jutil.setValue(d.getOvertime()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				obj.put("WELDERNAME", jutil.setValue(d.getWname()));
				obj.put("WELDERNO", jutil.setValue(d.getFwelder_id()));
				obj.put("MACHINENO", jutil.setValue(d.getFmachine_id()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getCompanyLoads(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getCompanyLoads(dto, new BigInteger(json.getString("COMPANYINSFID")));
			for(ModelDto d:list){
				obj.put("LOAD", jutil.setValue(d.getLoads()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getIid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getCaustLoads(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getCaustLoads(dto, new BigInteger(json.getString("INSFID")));
			for(ModelDto d:list){
				obj.put("LOAD", jutil.setValue(d.getLoads()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getIid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getItemLoads(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getItemLoads(dto, new BigInteger(json.getString("ITEMINSFID")));
			for(ModelDto d:list){
				obj.put("LOAD", jutil.setValue(d.getLoads()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getIid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getMachine(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			List<LiveData> list = live.getMachine(new BigInteger(json.getString("INSFID")));
			for(LiveData d:list){
				obj.put("MACHINEID", jutil.setValue(d.getFid()));
				obj.put("MACHINENO", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getItemid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getDetailLoads(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			dto.setTime( json.getString("WELDTIME").replace("/", "-")+"%");
			dto.setParent(new BigInteger(json.getString("ITEMINSFID")));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getDetailLoads(dto, null);
			for(ModelDto d:list){
				obj.put("LOAD", jutil.setValue(d.getLoads()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				obj.put("MACHINENO", jutil.setValue(d.getFmachine_id()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getCompanyNoLoads(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getCompanyNoLoads(dto, new BigInteger(json.getString("COMPANYINSFID")));
			for(ModelDto d:list){
				obj.put("NOLOAD", jutil.setValue(d.getLoads()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getIid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getCaustNOLoads(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getCaustNOLoads(dto, new BigInteger(json.getString("INSFID")));
			for(ModelDto d:list){
				obj.put("NOLOAD", jutil.setValue(d.getLoads()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getIid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getItemNOLoads(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getItemNOLoads(dto, new BigInteger(json.getString("ITEMINSFID")), null);
			for(ModelDto d:list){
				obj.put("NOLOAD", jutil.setValue(d.getLoads()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getCompanyIdle(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getCompanyIdle(dto, new BigInteger(json.getString("COMPANYINSFID")));
			for(ModelDto d:list){
				obj.put("NUM", jutil.setValue(d.getNum()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getCaustIdle(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getCaustIdle(dto, new BigInteger(json.getString("INSFID")));
			for(ModelDto d:list){
				obj.put("NUM", jutil.setValue(d.getNum()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getItemIdle(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getItemIdle(dto, new BigInteger(json.getString("ITEMINSFID")));
			for(ModelDto d:list){
				obj.put("NUM", jutil.setValue(d.getNum()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public int getMachineCount(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			return live.getMachineCount(new BigInteger(json.getString("INSFID")));
		}catch(Exception e){
			return -1;
		}
	}

	@Override
	public Object getCompanyUse(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getCompanyUse(dto, new BigInteger(json.getString("CAUSTINSFID")));
			for(ModelDto d:list){
				obj.put("ID", jutil.setValue(d.getFid()));
				obj.put("NAME", jutil.setValue(d.getFname()));
				obj.put("TYPE", jutil.setValue(d.getType()));
				obj.put("TIME", jutil.setValue(d.getTime()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getCaustUse(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getCaustUse(dto, new BigInteger(json.getString("ITEMINSFID")));
			for(ModelDto d:list){
				obj.put("ID", jutil.setValue(d.getFid()));
				obj.put("NAME", jutil.setValue(d.getFname()));
				obj.put("TYPE", jutil.setValue(d.getType()));
				obj.put("TIME", jutil.setValue(d.getTime()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getItemUse(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getItemUse(dto, new BigInteger(json.getString("ITEMINSFID")));
			for(ModelDto d:list){
				obj.put("ID", jutil.setValue(d.getFid()));
				obj.put("NAME", jutil.setValue(d.getFname()));
				obj.put("TYPE", jutil.setValue(d.getType()));
				obj.put("TIME", jutil.setValue(d.getTime()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getBlochour(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setSearch(json.getString("STR"));
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getBlochour(dto);
			for(ModelDto d:list){
				obj.put("HOUS", jutil.setValue(d.getHous()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				obj.put("JUNCTIONIDTEAM", jutil.setValue(d.getJidgather()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getBlocOverproof(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getBlocOverproof(dto);
			for(ModelDto d:list){
				obj.put("OVERPROOF", jutil.setValue(d.getOverproof()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getBlocOvertime(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getBlocOvertime(dto, json.getString("NUMBER"));
			for(ModelDto d:list){
				obj.put("OVERTIME", jutil.setValue(d.getOvertime()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getBlocLoads(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getBlocLoads(dto);
			for(ModelDto d:list){
				obj.put("LOAD", jutil.setValue(d.getLoads()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getIid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getBlocNoLoads(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getBlocNoLoads(dto);
			for(ModelDto d:list){
				obj.put("NOLOAD", jutil.setValue(d.getLoads()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getIid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getBlocIdle(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getBlocIdle(dto);
			for(ModelDto d:list){
				obj.put("NUM", jutil.setValue(d.getNum()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getBlocUse(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getBlocUse(dto, new BigInteger(json.getString("COMPANYINSFID")));
			for(ModelDto d:list){
				obj.put("ID", jutil.setValue(d.getFid()));
				obj.put("NAME", jutil.setValue(d.getFname()));
				obj.put("TYPE", jutil.setValue(d.getType()));
				obj.put("TIME", jutil.setValue(d.getTime()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getBlocChildren() {
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			List<LiveData> list = live.getBlocChildren();
			for(LiveData d:list){
				obj.put("INSFID", jutil.setValue(d.getFid()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object caustEfficiency(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
		    int min = json.getInt("MIN");
		    int max = json.getInt("MAX");
			List<ModelDto> list = live.caustEfficiency(new BigInteger(json.getString("ITEMINSFID")), dto,min,max);
			for(ModelDto d:list){
				obj.put("INSFID", jutil.setValue(d.getFid()));
				obj.put("INSFNAME", jutil.setValue(d.getIname()));
				obj.put("WELDERNAME", jutil.setValue(d.getWname()));
				obj.put("WELDERNO", jutil.setValue(d.getFwelder_id()));
				obj.put("WORKTIME", jutil.setValue(d.getWeldTime()));
				obj.put("JUNCTIONIDTEAM", jutil.setValue(d.getJidgather()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object companyEfficiency(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
		    int min = json.getInt("MIN");
		    int max = json.getInt("MAX");
			List<ModelDto> list = live.companyEfficiency(new BigInteger(json.getString("CAUSTINSFID")), dto,min,max);
			for(ModelDto d:list){
				obj.put("INSFID", jutil.setValue(d.getFid()));
				obj.put("INSFNAME", jutil.setValue(d.getIname()));
				obj.put("WELDERNAME", jutil.setValue(d.getWname()));
				obj.put("WELDERNO", jutil.setValue(d.getFwelder_id()));
				obj.put("WORKTIME", jutil.setValue(d.getWeldTime()));
				obj.put("JUNCTIONIDTEAM", jutil.setValue(d.getJidgather()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object blocEfficiency(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
		    int min = json.getInt("MIN");
		    int max = json.getInt("MAX");
			List<ModelDto> list = live.blocEfficiency(dto, new BigInteger(json.getString("COMPANYINSFID")),min,max);
			for(ModelDto d:list){
				obj.put("INSFID", jutil.setValue(d.getFid()));
				obj.put("INSFNAME", jutil.setValue(d.getIname()));
				obj.put("WELDERNAME", jutil.setValue(d.getWname()));
				obj.put("WELDERNO", jutil.setValue(d.getFwelder_id()));
				obj.put("WORKTIME", jutil.setValue(d.getWeldTime()));
				obj.put("JUNCTIONIDTEAM", jutil.setValue(d.getJidgather()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getEfficiencyChart(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			BigInteger id = new BigInteger(json.getString("INSFID"));
			List<ModelDto> list = live.getEfficiencyChartNum(dto, id);
			List<ModelDto> efficiency = null;
			for(ModelDto m:list){
				efficiency = live.getEfficiencyChart(dto, id, m.getMinnum(), m.getAvgnum());
			}
			for(ModelDto d:efficiency){
				obj.put("TIME1", jutil.setValue(d.getSum1()));
				obj.put("TIME2", jutil.setValue(d.getSum2()));
				obj.put("TIME3", jutil.setValue(d.getSum3()));
				obj.put("TIME4", jutil.setValue(d.getSum4()));
				obj.put("TIME5", jutil.setValue(d.getSum5()));
				obj.put("TIME6", jutil.setValue(d.getSum6()));
				obj.put("TIME7", jutil.setValue(d.getSum7()));
				obj.put("TIME8", jutil.setValue(d.getSum8()));
				obj.put("TIME9", jutil.setValue(d.getSum9()));
				obj.put("TIME10", jutil.setValue(d.getSum10()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getHousClassify(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			List<ModelDto> list = live.getHousClassify(new BigInteger(json.getString("INSFID")), json.getString("STR"));
			for(ModelDto d:list){
				obj.put("ID", jutil.setValue(d.getFid()));
				obj.put("MATERIAL", jutil.setValue(d.getMaterial()));
				obj.put("NEXTMATERIAL", jutil.setValue(d.getNextmaterial()));
				obj.put("EXTERNALDIAMETER", jutil.setValue(d.getExternalDiameter()));
				obj.put("NEXTEXTERNALDIAMETER", jutil.setValue(d.getNextexternaldiameter()));
				obj.put("WALLTHICKNESS", jutil.setValue(d.getWallThickness()));
				obj.put("NEXTWALLTHICKNESS", jutil.setValue(d.getNextwallThickness()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getDetailNoLoads(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			dto.setTime( json.getString("WELDTIME").replace("/", "-")+"%");
			dto.setParent(new BigInteger(json.getString("ITEMINSFID")));
			List<ModelDto> list = live.getDetailNoLoads(dto);
			for(ModelDto d:list){
				obj.put("NOLOAD", jutil.setValue(d.getLoads()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				obj.put("MACHINEID", jutil.setValue(d.getFmachine_id()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getBlocMachineCount(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			dto.setDtoStatus(json.getInt("STATUS"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getCaustMachineCount(dto, new BigInteger(json.getString("INSFID")));
			for(ModelDto d:list){
				obj.put("NUM", jutil.setValue(d.getLoads()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getCompanyMachineCount(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			dto.setDtoStatus(json.getInt("STATUS"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getCaustMachineCount(dto, new BigInteger(json.getString("INSFID")));
			for(ModelDto d:list){
				obj.put("NUM", jutil.setValue(d.getLoads()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getCaustMachineCount(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			dto.setDtoStatus(json.getInt("STATUS"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getCaustMachineCount(dto, new BigInteger(json.getString("INSFID")));
			for(ModelDto d:list){
				obj.put("NUM", jutil.setValue(d.getLoads()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("INSFID", jutil.setValue(d.getFid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public double getCountByTime(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			BigInteger parent = null,mid = null;
			int type = json.getInt("TYPE");
			String parentid = json.getString("INSFID");
			String time1 = json.getString("TIME1").replace("/", "-");
			String time2 = json.getString("TIME2").replace("/", "-");
			String machineid = json.getString("MACHINEID");
			if(parentid!=null && !"".equals(parentid)){
				parent = new BigInteger(parentid);
			}
			if(machineid!=null && !"".equals(machineid)){
				mid = new BigInteger(machineid);
			}
			return live.getCountByTime(parent, time1, time2, mid,type);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	/*@Override
	public Object getJunctionByWelder(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			String time = json.getString("TIME").replace("/", "-");
			String welderno = json.getString("WELDERNO");
			if(time!=null && !"".equals(time)){
				dto.setDtoTime1(time);
			}
			List<ModelDto> list  = live.getJunctionByWelder(dto, welderno);
			for(ModelDto d:list){
				obj.put("JUNCTIONNO", jutil.setValue(d.getFname()));
				obj.put("INSFNAME", jutil.setValue(d.getIname()));
				obj.put("MAXELECTRICITY", jutil.setValue(d.getFmax_electricity()));
				obj.put("MINELECTRICITY", jutil.setValue(d.getFmin_electricity()));
				obj.put("MAXVALTAGE", jutil.setValue(d.getFmax_valtage()));
				obj.put("MINVALTAGE", jutil.setValue(d.getFmin_valtage()));
				obj.put("MATERIAL", jutil.setValue(d.getMaterial()));
				obj.put("NEXTMATERIAL", jutil.setValue(d.getNextmaterial()));
				obj.put("EXTERNALDIAMETER", jutil.setValue(d.getExternalDiameter()));
				obj.put("NEXTEXTERNALDIAMETER", jutil.setValue(d.getNextexternaldiameter()));
				obj.put("WALLTHICKNESS", jutil.setValue(d.getWallThickness()));
				obj.put("NEXTWALLTHICKNESS", jutil.setValue(d.getNextwallThickness()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}*/

	@Override
	public Object getExcessiveBack(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("TIME1").replace("/", "-"));
			dto.setDtoTime2(json.getString("TIME2").replace("/", "-"));
			String parentid = json.getString("INSFID");
			if(parentid!=null && !"".equals(parentid)){
				dto.setParent(new BigInteger(parentid));
			}
			List<ModelDto> list = live.getExcessiveBack(dto);
			for(ModelDto d:list){
				obj.put("FID", jutil.setValue(d.getFid()));
				obj.put("WELDERNO", jutil.setValue(d.getFwelder_id()));
				obj.put("WELDNAME", jutil.setValue(d.getWname()));
				obj.put("JUNCTIONNO", jutil.setValue(d.getFjunction_id()));
				obj.put("MACHINENO", jutil.setValue(d.getFmachine_id()));
				obj.put("INSFNAME", jutil.setValue(d.getIname()));
				obj.put("STARTTIME", jutil.setValue(d.getStarttime()));
				obj.put("ENDTIME", jutil.setValue(d.getEndtime()));
				obj.put("MAXELECTRICITY", jutil.setValue(d.getFmax_electricity()));
				obj.put("MINELECTRICITY", jutil.setValue(d.getFmin_electricity()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}
	
	@Override
	public Object getStandbytimeout(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int num = json.getInt("NUM");
			List<ModelDto> list = live.getStandbytimeout(dto, num);
			for(ModelDto d:list){
				obj.put("FNAME", jutil.setValue(d.getFname()));
				obj.put("HOUS", jutil.setValue(d.getHous()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getSMSMessage(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			String parentid = json.getString("PARENT");
			if(parentid!=null && !"".equals(parentid)){
				dto.setParent(new BigInteger(parentid));
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String starttime = json.getString("STARTTIME");
			String endtime = json.getString("ENDTIME");
			Calendar cal = Calendar.getInstance();
			Date time1 = sdf.parse(starttime);
			Date time2 = sdf.parse(endtime);
			if(endtime!=null && !"".equals(endtime)){
				cal.setTime(time2);
			}else{
				cal.setTime(time1);
			}
			cal.add(Calendar.DATE, 1);//增加一天,即：结束时间刚好到第二点零点整
			dto.setDtoTime2(sdf.format(cal.getTime())+" 00:00:00");
			cal.setTime(time1);
			dto.setDtoTime1(sdf.format(cal.getTime())+" 00:00:00");
			//组织机构
			Insframework item = insf.getInsfAllById(dto.getParent());
			obj.put("ITEMNAME",item.getName());
			//获取平均焊接及工作时长
			ModelDto avgmsg = live.getWelderAvgWorkTime(dto);
			int weldertotal = live.getWelderTotal(dto);//在线人数
			if(avgmsg!=null){
				obj.put("AVGWORKTIME", (double)Math.round(avgmsg.getTime()/weldertotal*100)/100);//平均工作时长
			}else{
				obj.put("AVGWORKTIME", 0);
			}
			List<ModelDto> front = live.getWelderRank(dto, 1);
			List<ModelDto> back = live.getWelderRank(dto, 0);
			String frontwelder = "", backwelder = "";
			for(int i=0;i<front.size();i++){
				String name = front.get(i).getFname();
				if(name==null || "".equals(name)){
					name = front.get(i).getFwelder_id();
				}
				frontwelder += name+"("+(double)Math.round(front.get(i).getWorktime()*100)/100+"h)";
				if(i != front.size()-1){
					frontwelder += "、";
				}
			}
			for(int i=0;i<back.size();i++){
				String name = back.get(i).getFname();
				if(name==null || "".equals(name)){
					name = back.get(i).getFwelder_id();
				}
				backwelder += name+"("+(double)Math.round(back.get(i).getWorktime()*100)/100+"h)";
				if(i != front.size()-1){
					backwelder += "、";
				}
			}
			obj.put("FRONTWELDER",frontwelder);
			obj.put("BACKWELDER",backwelder);
			obj.put("WELDERTOTAL",weldertotal);
			return obj.toString();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object getExcessiveBackDetail(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			List<ModelDto> list = live.getExcessiveBackDetail(new BigInteger(json.getString("ID")));
			for(ModelDto d:list){
				obj.put("ELECTRICITY", jutil.setValue(d.getFelectricity()));
				obj.put("MAXELECTRICITY", jutil.setValue(d.getFmax_electricity()));
				obj.put("MINELECTRICITY", jutil.setValue(d.getFmin_electricity()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getBlocRunTime(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			BigInteger parent = null;
			String parentid = json.getString("INSFID");
			if(parentid!=null && !"".equals(parentid)){
				parent = new BigInteger(parentid);
			}
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getBlocRunTime(parent, dto, 0, 0);
			for(ModelDto d:list){
				obj.put("TIME", jutil.setValue(d.getTime()));
				obj.put("MACHINENO", jutil.setValue(d.getFmachine_id()));
				obj.put("INSFNAME", jutil.setValue(d.getFname()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getUseratio(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			String time1 = json.getString("TIME1");
			String time2 = json.getString("TIME2");
			String insftype = json.getString("INSFTYPE");
			List<ModelDto> list = live.getUseratio(time1, time2, insftype);
			for(ModelDto d:list){
				obj.put("COMPANYID", jutil.setValue(d.getFid()));
				obj.put("CAUSTID", jutil.setValue(d.getCaustid()));
				obj.put("ITEMID", jutil.setValue(d.getItemid()));
				obj.put("ITEMNAME", jutil.setValue(d.getFname()));
				obj.put("TOTAL", jutil.setValue(d.getTotal()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("WORKTIME", jutil.setValue(d.getWorktime()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getMaintenanceratio(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			String parentid = json.getString("INSFID");
			if(parentid!=null && !"".equals(parentid)){
				dto.setParent(new BigInteger(parentid));
			}
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getMaintenanceratio(dto);
			for(ModelDto d:list){
				obj.put("COMPANYID", jutil.setValue(d.getFid()));
				obj.put("COMPANYNAME", jutil.setValue(d.getFname()));
				obj.put("CAUSTID", jutil.setValue(d.getCaustid()));
				obj.put("CAUSTNAME", jutil.setValue(d.getIname()));
				obj.put("ITEMID", jutil.setValue(d.getItemid()));
				obj.put("ITEMNAME", jutil.setValue(d.getWname()));
				obj.put("MONEY", jutil.setValue(d.getRmoney()));
				obj.put("TOTAL", jutil.setValue(d.getTotal()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getSumMaintenance(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			ModelDto list = live.getSumMaintenance(dto);
			if(list!=null){
				obj.put("TOTAL", jutil.setValue(list.getTotal()));
				obj.put("MONEY", jutil.setValue(list.getRmoney()));
				obj.put("MACHINEMONEY", jutil.setValue(list.getMmoney()));
			}
			return obj.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getItemTypeMaintain(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			BigInteger itemid = null;
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			String item = json.getString("ITEMID");
			if(item!=null && !"".equals(item)){
				itemid = new BigInteger(item);
			}
			List<ModelDto> list = live.getItemTypeMaintain(dto, itemid);
			for(ModelDto d:list){
				obj.put("ID", jutil.setValue(d.getFid()));
				obj.put("MONEY", jutil.setValue(d.getRmoney()));
				obj.put("TYPEID", jutil.setValue(d.getTypeid()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getItemMachineSumMoneyByType(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			BigInteger itemid = null;
			String item = json.getString("ITEMID");
			if(item!=null && !"".equals(item)){
				itemid = new BigInteger(item);
			}
			List<ModelDto> list = live.getItemMachineSumMoneyByType(itemid);
			for(ModelDto d:list){
				obj.put("ID", jutil.setValue(d.getFid()));
				obj.put("TYPEID", jutil.setValue(d.getTypeid()));
				obj.put("TYPENAME", jutil.setValue(d.getType()));
				obj.put("NAME", jutil.setValue(d.getFname()));
				obj.put("MONEY", jutil.setValue(d.getMmoney()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getMachineMoney() {
		try{
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			List<ModelDto> list = live.getMachineMoney();
			for(ModelDto d:list){
				obj.put("COMPANYID", jutil.setValue(d.getFid()));
				obj.put("COMPANYNAME", jutil.setValue(d.getFname()));
				obj.put("CAUSTID", jutil.setValue(d.getCaustid()));
				obj.put("CAUSTNAME", jutil.setValue(d.getIname()));
				obj.put("ITEMID", jutil.setValue(d.getItemid()));
				obj.put("ITEMNAME", jutil.setValue(d.getWname()));
				obj.put("MONEY", jutil.setValue(d.getMmoney()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getFaultRatio(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			String parentid = json.getString("INSFID");
			if(parentid!=null && !"".equals(parentid)){
				dto.setParent(new BigInteger(parentid));
			}
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getFaultRatio(dto);
			for(ModelDto d:list){
				obj.put("COMPANYID", jutil.setValue(d.getFid()));
				obj.put("COMPANYNAME", jutil.setValue(d.getFname()));
				obj.put("CAUSTID", jutil.setValue(d.getCaustid()));
				obj.put("CAUSTNAME", jutil.setValue(d.getIname()));
				obj.put("ITEMID", jutil.setValue(d.getItemid()));
				obj.put("ITEMNAME", jutil.setValue(d.getWname()));
				obj.put("TOTAL", jutil.setValue(d.getTotal()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getMaintenanceNum(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			String parentid = json.getString("INSFID");
			if(parentid!=null && !"".equals(parentid)){
				dto.setParent(new BigInteger(parentid));
			}
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getMaintenanceNum(dto);
			for(ModelDto d:list){
				obj.put("ID", jutil.setValue(d.getFid()));
				obj.put("TYPEID", jutil.setValue(d.getTypeid()));
				obj.put("NAME", jutil.setValue(d.getFname()));
				obj.put("TYPENAME", jutil.setValue(d.getType()));
				obj.put("TOTAL", jutil.setValue(d.getTotal()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getFaultNum(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			String parentid = json.getString("INSFID");
			if(parentid!=null && !"".equals(parentid)){
				dto.setParent(new BigInteger(parentid));
			}
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getFaultNum(dto);
			for(ModelDto d:list){
				obj.put("ID", jutil.setValue(d.getFid()));
				obj.put("TYPEID", jutil.setValue(d.getTypeid()));
				obj.put("NAME", jutil.setValue(d.getFname()));
				obj.put("TYPENAME", jutil.setValue(d.getType()));
				obj.put("TOTAL", jutil.setValue(d.getTotal()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getFaultRatioByType(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			String parentid = json.getString("INSFID");
			if(parentid!=null && !"".equals(parentid)){
				dto.setParent(new BigInteger(parentid));
			}
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getFaultRatioByType(dto);
			for(ModelDto d:list){
				obj.put("TYPEID", jutil.setValue(d.getTypeid()));
				obj.put("TYPENAME", jutil.setValue(d.getType()));
				obj.put("TOTAL", jutil.setValue(d.getTotal()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getFaultDetail(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			String parentid = json.getString("INSFID");
			if(parentid!=null && !"".equals(parentid)){
				dto.setParent(new BigInteger(parentid));
			}
			dto.setDtoStatus(json.getInt("TYPEID"));
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getFaultDetail(dto);
			for(ModelDto d:list){
				obj.put("TYPENAME", jutil.setValue(d.getType()));
				obj.put("MACHINENO", jutil.setValue(d.getFmachine_id()));
				obj.put("TOTAL", jutil.setValue(d.getTotal()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				obj.put("NAME", jutil.setValue(d.getFname()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getOnlineNumber(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			String parentid = json.getString("INSFID");
			if(parentid!=null && !"".equals(parentid)){
				dto.setParent(new BigInteger(parentid));
			}
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			double time = 0;
			String times = json.getString("TIME");
			if(times!=null && !"".equals(times)){
				time = Double.valueOf(times);
			}
			List<ModelDto> list = live.getOnlineNumber(dto, time);
			for(ModelDto d:list){
				obj.put("COMPANYID", jutil.setValue(d.getFid()));
				obj.put("COMPANYNAME", jutil.setValue(d.getFname()));
				obj.put("CAUSTID", jutil.setValue(d.getCaustid()));
				obj.put("CAUSTNAME", jutil.setValue(d.getIname()));
				obj.put("ITEMID", jutil.setValue(d.getItemid()));
				obj.put("ITEMNAME", jutil.setValue(d.getWname()));
				obj.put("TOTAL", jutil.setValue(d.getTotal()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getOperatoreTime(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getOperatoreTime(dto);
			for(ModelDto d:list){
				obj.put("COMPANYID", jutil.setValue(d.getFid()));
				obj.put("COMPANYNAME", jutil.setValue(d.getFname()));
				obj.put("CAUSTID", jutil.setValue(d.getCaustid()));
				obj.put("CAUSTNAME", jutil.setValue(d.getIname()));
				obj.put("ITEMID", jutil.setValue(d.getItemid()));
				obj.put("ITEMNAME", jutil.setValue(d.getWname()));
				obj.put("WORKTIME", jutil.setValue(d.getWorktime()));
				obj.put("LOADTIME", jutil.setValue(d.getLoads()));
				obj.put("STANDBYTIME", jutil.setValue(d.getTime()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getItemWorkTime(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			String parentid = json.getString("INSFID");
			if(parentid!=null && !"".equals(parentid)){
				dto.setParent(new BigInteger(parentid));
			}
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getItemWorkTime(dto);
			for(ModelDto d:list){
				obj.put("WORKTIME", jutil.setValue(d.getWorktime()));
				obj.put("WELDERNO", jutil.setValue(d.getFwelder_id()));
				obj.put("WELDNAME", jutil.setValue(d.getFname()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getItemStandbyTime(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			String parentid = json.getString("INSFID");
			if(parentid!=null && !"".equals(parentid)){
				dto.setParent(new BigInteger(parentid));
			}
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getItemStandbyTime(dto);
			for(ModelDto d:list){
				obj.put("WORKTIME", jutil.setValue(d.getWorktime()));
				obj.put("WELDERID", jutil.setValue(d.getFwelder_id()));
				obj.put("WELDERNAME", jutil.setValue(d.getFname()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getInsfandMachinenum(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			BigInteger parent = null;
			String parentid = json.getString("INSFID");
			if(parentid!=null && !"".equals(parentid)){
				parent = new BigInteger(parentid);
			}
			List<ModelDto> list = live.getInsfandMachinenum(parent);
			for(ModelDto d:list){

				obj.put("COMPANYID", jutil.setValue(d.getFid()));
				obj.put("COMPANYNAME", jutil.setValue(d.getFname()));
				obj.put("CAUSTID", jutil.setValue(d.getCaustid()));
				obj.put("CAUSTNAME", jutil.setValue(d.getIname()));
				obj.put("ITEMID", jutil.setValue(d.getItemid()));
				obj.put("ITEMNAME", jutil.setValue(d.getWname()));
				obj.put("TOTAL", jutil.setValue(d.getTotal()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getUseDetail(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getUseDetail(new BigInteger(json.getString("ID")), json.getInt("TYPEID"), dto);
			for(ModelDto d:list){
				obj.put("MACHINENO", jutil.setValue(d.getWname()));
				obj.put("NAME", jutil.setValue(d.getFname()));
				obj.put("TYPENAME", jutil.setValue(d.getType()));
				obj.put("TIME", jutil.setValue(d.getTime()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object getDurationTime(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			List<ModelDto> list = live.getDurationTime(json.getString("SQL"));
			for(ModelDto d:list){
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getWeldingmachineList(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			String parentid = json.getString("INSFID");
			if(parentid!=null && !"".equals(parentid)){
				dto.setParent(new BigInteger(parentid));
			}
			int status = 0;
			String statusid = json.getString("STATUS");
			if(statusid!=null && !"".equals(statusid)){
				status = Integer.parseInt(statusid);
			}
			dto.setDtoStatus(status);
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getWeldingmachineList(dto);
			for(ModelDto d:list){
				obj.put("LOADS", jutil.setValue(d.getLoads()));
				obj.put("MACHINENO", jutil.setValue(d.getFname()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getWelderList(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			String parentid = json.getString("INSFID");
			if(parentid!=null && !"".equals(parentid)){
				dto.setParent(new BigInteger(parentid));
			}
			int status = 0;
			String statusid = json.getString("STATUS");
			if(statusid!=null && !"".equals(statusid)){
				status = Integer.parseInt(statusid);
			}
			dto.setDtoStatus(status);
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getWelderList(dto);
			for(ModelDto d:list){
				obj.put("LOADS", jutil.setValue(d.getLoads()));
				obj.put("WELDERNAME", jutil.setValue(d.getFname()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getNewOvertime(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			int num = json.getInt("NUM");
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			List<ModelDto> list = live.getNewOvertime(dto, num, json.getString("INSFTYPE"));
			for(ModelDto d:list){
				obj.put("CAOMPANYID", jutil.setValue(d.getFid()));
				obj.put("CAUSTID", jutil.setValue(d.getCaustid()));
				obj.put("ITEMID", jutil.setValue(d.getItemid()));
				obj.put("TOTAL", jutil.setValue(d.getTotal()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getNewOvertimeDetail(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			dto.setTime(json.getString("TIME"));
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			int num = json.getInt("NUM");
			List<ModelDto> list = live.getNewOvertimeDetail(dto, num);
			for(ModelDto d:list){
				obj.put("WELDERNO", jutil.setValue(d.getFwelder_id()));
				obj.put("WELDERNAME", jutil.setValue(d.getWname()));
				obj.put("MACHINENO", jutil.setValue(d.getFmachine_id()));
				obj.put("JUNCTIONNO", jutil.setValue(d.getFjunction_id()));
				obj.put("STATRTIME", jutil.setValue(d.getStarttime()));
				obj.put("ENDTIME", jutil.setValue(d.getEndtime()));
				obj.put("TIME", jutil.setValue(d.getTime()));
				obj.put("ITEMNAME", jutil.setValue(d.getIname()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getNewIdle(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			WeldDto dto = new WeldDto();
			String parentid = json.getString("INSFID");
			if(parentid!=null && !"".equals(parentid)){
				dto.setParent(new BigInteger(parentid));
			}
			int type = json.getInt("TYPEID");
			if(type==1){
				dto.setYear("year");
			}else if(type==2){
				dto.setMonth("month");
			}else if(type==3){
				dto.setDay("day");
			}else if(type==4){
				dto.setWeek("week");
			}
			dto.setDtoTime1(json.getString("STARTTIME"));
			dto.setDtoTime2(json.getString("ENDTIME"));
			List<ModelDto> list = live.getNewIdle(dto);
			for(ModelDto d:list){
				obj.put("TYPEID", jutil.setValue(d.getTypeid()));
				obj.put("TOTAL", jutil.setValue(d.getTotal()));
				obj.put("WELDTIME", jutil.setValue(d.getWeldTime()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object getMachineTypeTotal(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			JSONArray ary = new JSONArray();
			JSONObject obj = new JSONObject();
			BigInteger parent = null;
			String parentid = json.getString("INSFID");
			if(parentid!=null && !"".equals(parentid)){
				parent = new BigInteger(parentid);
			}
			List<ModelDto> list = live.getMachineTypeTotal(parent);
			for(ModelDto d:list){
				obj.put("TYPEID", jutil.setValue(d.getTypeid()));
				obj.put("TYPENAME", jutil.setValue(d.getFname()));
				obj.put("TOTAL", jutil.setValue(d.getTotal()));
				ary.add(obj);
			}
			return ary.toString();
		}catch(Exception e){
			return null;
		}
	}
}
