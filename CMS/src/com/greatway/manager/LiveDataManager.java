package com.greatway.manager;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.greatway.dto.ModelDto;
import com.greatway.dto.WeldDto;
import com.greatway.model.LiveData;
import com.greatway.model.WeldedJunction;
import com.greatway.page.Page;

public interface LiveDataManager {
	/**
	 * 鏌ヨ浜嬩笟閮ㄧ剨鎺ュ伐鏃�
	 * @param dto鎵╁睍鍙傛暟绫�
	 * @param parent 鐖秈d
	 * @return
	 */
	List<ModelDto> getCausehour(Page page,WeldDto dto,BigInteger parent);
	
	/**
	 * 鏌ヨ鍏徃鐒婃帴宸ユ椂
	 * @param dto鎵╁睍鍙傛暟绫�
	 * @param parent鐖秈d
	 * @return
	 */
	List<ModelDto> getCompanyhour(Page page,WeldDto dto,BigInteger parent);
	
	/**
	 * 椤圭洰閮ㄧ剨鎺ュ伐鏃�
	 * @param dto鎵╁睍鍙傛暟绫�
	 * @return
	 */
	List<ModelDto> getItemhour(Page page,WeldDto dto);
	
	/**
	 * 鐒婂彛鐒婃帴宸ユ椂
	 * @param dto鎵╁睍鍙傛暟绫�
	 * @return
	 */
	List<ModelDto> getJunctionHous(Page page,WeldDto dto);
	
	/**
	 * 浜嬩笟閮ㄥ伐鑹鸿秴鏍囩粺璁�
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @param parent 鐖秈d
	 * @return
	 */
	List<ModelDto> getCauseOverproof(WeldDto dto,BigInteger parent);
	
	/**
	 * 椤圭洰閮ㄥ伐鑹鸿秴鏍囩粺璁�
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @param id 椤圭洰id
	 * @return
	 */
	List<ModelDto> getItemOverproof(WeldDto dto,BigInteger id);
	
	/**
	 * 鑾峰彇褰撳墠鎵�鍖呭惈鐨勯」鐩�
	 * @param parent 涓婄骇椤圭洰id
	 * @return
	 */
	List<LiveData> getAllInsf(BigInteger parent,int type);
	
	/**
	 * 鑾峰彇褰撳墠璺ㄥ害鎵�鍖呭惈鐨勬椂闂�
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @return
	 */
	List<ModelDto> getAllTime(Page page,WeldDto dto);
	
	/**
	 * 鍏徃宸ヨ壓瓒呮爣缁熻
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @return
	 */
	List<ModelDto> getCompanyOverproof(WeldDto dto,BigInteger parent);
	
	/**
	 * 瓒呮爣鏄庣粏
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @return
	 */
	List<ModelDto> getDatailOverproof(Page page,WeldDto dto,BigInteger parent);
	
	/**
	 * 鑾峰彇鏌愮剨宸ュ湪鏌愪釜鏃堕棿/鐒婃満/鐒婂彛鐨勬�诲伐鏃�
	 * @param welderno鐒婂伐缂栧彿
	 * @param machineno鐒婃満缂栧彿
	 * @param junctionno鐒婂彛缂栧彿
	 * @param time鏃堕棿
	 * @return
	 */
	int getCountTime(String welderno,String machineno,String junctionno,String time,BigInteger id);
	
	/**
	 * 鑾峰彇鐒婃満瓒呮爣
	 * @param welderno鐒婂伐缂栧彿
	 * @param machineno鐒婃満缂栧彿
	 * @param junctionno鐒婂彛缂栧彿
	 * @param time鏃堕棿
	 * @return
	 */
	List<ModelDto> getjunctionoverproof(String welderno,String machineno,String junctionno,String time,BigInteger itemid);
	
	/**
	 * 鑾峰彇鍏徃瓒呮椂寰呮満缁熻
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @param num 瓒呮椂鐐�
	 * @return
	 */
	List<ModelDto> getcompanyOvertime(WeldDto dto , String num,BigInteger parent);
	
	/**
	 * 鑾峰彇浜嬩笟閮ㄨ秴鏃跺緟鏈虹粺璁�
	 * @param dto鎵╁睍鍙傛暟绫�
	 * @param num瓒呮椂鐐�
	 * @param parent涓婄骇id
	 * @return
	 */
	List<ModelDto> getCaustOvertime(WeldDto dto , String num,BigInteger parent);
	
	/**
	 * 鑾峰彇椤圭洰閮ㄨ秴鏃跺緟鏈虹粺璁�
	 * @param dto鎵╁睍鍙傛暟绫�
	 * @param num瓒呮椂鐐�
	 * @param parent涓婄骇id
	 * @return
	 */
	List<ModelDto> getItemOvertime(WeldDto dto , String num);
	
	/**
	 * 鑾峰彇鎵�鏈夌剨鍙�
	 * @param parent 鎵�灞為」鐩甶d
	 * @return
	 */
	List<LiveData> getJunction(BigInteger parent);
	
	/**
	 * 寰呮満鏄庣粏
	 * @param dto鎵╁睍鍙傛暟绫�
	 * @param num瓒呮椂鐐�
	 * @param parent 椤圭洰id
	 * @return
	 */
	List<ModelDto> getDetailovertime(Page page,WeldDto dto , String num,String parent);
	
	/**
	 * 鍏徃璐熻嵎鐜�
	 * @param dto鎵╁睍鍙傛暟绫�
	 * @return
	 */
	List<ModelDto> getCompanyLoads(WeldDto dto,BigInteger parent);
	
	
	/**
	 * 浜嬩笟閮ㄨ礋鑽风巼
	 * @param dto鎵╁睍鍙傛暟绫�
	 * @param parent涓婄骇id
	 * @return
	 */
	List<ModelDto> getCaustLoads(WeldDto dto,BigInteger parent);
	
	/**
	 * 椤圭洰閮ㄨ礋鑽风巼
	 * @param dto鎵╁睍鍙傛暟绫�
	 * @param parent涓婄骇id
	 * @return
	 */
	List<ModelDto> getItemLoads(WeldDto dto,BigInteger parent);
	
	/**
	 * 鑾峰彇鎵�鏈夌剨鏈�
	 * @param parent 椤圭洰id
	 * @param dto鎵╁睍鍙傛暟绫�
	 * @return
	 */
	List<LiveData> getMachine(BigInteger parent);
	
	/**
	 * 鑾峰彇璐熻嵎鐜囨槑缁�
	 * @param dto鎵╁睍鍙傛暟绫�
	 * @param machineno鐒婃満缂栧彿
	 * @return
	 */
	List<ModelDto> getDetailLoads(Page page,WeldDto dto,String machineno);
	
	/**
	 * 鑾峰彇鍏徃绌鸿浇鐜�
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @return
	 */
	List<ModelDto> getCompanyNoLoads(WeldDto dto,BigInteger parent);

	/**
	 * 鑾峰彇浜嬩笟閮ㄧ┖杞界巼
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @param parent 鐖秈d
	 * @return
	 */
	List<ModelDto> getCaustNOLoads(WeldDto dto,BigInteger parent);
	
	/**
	 * 鑾峰彇椤圭洰閮ㄧ┖杞界巼
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @param parent 鐖秈d
	 * @return
	 */
	List<ModelDto> getItemNOLoads(WeldDto dto,BigInteger parent,String equipmentno);
	
	/**
	 * 鍏徃闂茬疆鐜�
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @return
	 */
	List<ModelDto> getCompanyIdle(WeldDto dto,BigInteger parent);
	
	/**
	 * 浜嬩笟閮ㄩ棽缃巼
	 * @param dto鎵╁睍鍙傛暟绫�
	 * @param parent涓婄骇id
	 * @return
	 */
	List<ModelDto> getCaustIdle(WeldDto dto,BigInteger parent);
	
	/**
	 * 椤圭洰閮ㄩ棽缃巼
	 * @param dto鎵╁睍鍙傛暟绫�
	 * @param itemid椤圭洰id
	 * @return
	 */
	List<ModelDto> getItemIdle(WeldDto dto,BigInteger itemid);
	
	/**
	 * 鑾峰彇椤圭洰鎵�鏈夌剨鏈烘暟閲�
	 * @param id 椤圭洰id
	 * @return
	 */
	int getMachineCount(BigInteger id);
	
	/**
	 * 鍏徃鍗曞彴璁惧杩愯鏁版嵁缁熻
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @param parent 涓婄骇id
	 * @return
	 */
	List<ModelDto> getCompanyUse(Page page,WeldDto dto,BigInteger parent);
	
	/**
	 * 浜嬩笟閮ㄥ崟鍙拌澶囪繍琛屾暟鎹粺璁�
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @param insid 椤圭洰id
	 * @return
	 */
	List<ModelDto> getCaustUse(Page page,WeldDto dto,BigInteger insid);
	

	/**
	 * 椤圭洰閮ㄥ崟鍙拌澶囪繍琛屾暟鎹粺璁�
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @param insid 椤圭洰id
	 * @return
	 */
	List<ModelDto> getItemUse(Page page,WeldDto dto,BigInteger insid);
	
	/**
	 * 鑾峰彇鐢ㄦ埛id
	 * @return
	 */
	BigInteger getUserId(HttpServletRequest request);
	
	/**
	 * 鑾峰彇鎵�鏈夋椂闂�
	 * @param dto
	 * @return
	 */
	List<ModelDto> getAllTimes(WeldDto dto);
	
	/**
	 * 闆嗗洟鐒婃帴宸ユ椂
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @return
	 */
	List<ModelDto> getBlochour(Page page,WeldDto dto);
	
	/**
	 * 闆嗗洟瓒呮爣缁熻
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @return
	 */
	List<ModelDto> getBlocOverproof(WeldDto dto);
	
	/**
	 * 闆嗗洟瓒呮椂寰呮満缁熻
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @param num 瓒呮椂鐐�
	 * @return
	 */
	List<ModelDto> getBlocOvertime(WeldDto dto,String num);
	
	/**
	 * 闆嗗洟璐熻浇鐜�
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @return
	 */
	List<ModelDto> getBlocLoads(WeldDto dto);
	
	/**
	 * 闆嗗洟绌鸿浇鐜�
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @return
	 */
	List<ModelDto> getBlocNoLoads(WeldDto dto);
	
	/**
	 * 闆嗗洟闂茬疆鐜�
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @return
	 */
	List<ModelDto> getBlocIdle(WeldDto dto);
	
	/**
	 * 闆嗗洟鍗曞彴璁惧杩愯鏁版嵁缁熻
	 * @param dto  鎵╁睍鍙傛暟绫�
	 * @param parent 涓婄骇鐨勭埗id
	 * @return
	 */
	List<ModelDto> getBlocUse(Page page,WeldDto dto,BigInteger parent);
	
	/**
	 * 鑾峰彇闆嗗洟涓嬬殑鍏徃
	 * @return
	 */
	List<LiveData> getBlocChildren();
	
	/**
	 * 浜嬩笟閮ㄥ伐鏁�
	 * @param page 鍒嗛〉
	 * @param parent 鐖秈d
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @return
	 */
	List<ModelDto> caustEfficiency(Page page,BigInteger parent,WeldDto dto,int min,int max);
	
	/**
	 * 鍏徃宸ユ晥
	 * @param page 鍒嗛〉
	 * @param parent 鐖秈d
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @return
	 */
	List<ModelDto> companyEfficiency(Page page ,BigInteger parent,WeldDto dto,int min,int max);
	
	/**
	 * 闆嗗洟宸ユ晥
	 * @param page 鍒嗛〉
	 * @param parent 鐖秈d
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @return
	 */
	List<ModelDto> blocEfficiency(Page page,WeldDto dto,BigInteger parent,int min,int max);
	
	/**
	 * 鑾峰彇宸ユ晥鏈�澶у�兼渶灏忓�间互鍙婂钩鍧囪法搴�
	 */
	List<ModelDto> getEfficiencyChartNum(WeldDto dto,BigInteger parent);
	
	/**
	 * 鑾峰彇宸ユ晥鍥捐〃鏁版嵁
	 */
	List<ModelDto> getEfficiencyChart(WeldDto dto,BigInteger parent,int minnum,int avgnum);
	
	/**
	 * 鏍规嵁id鑾峰彇鐒婂彛淇℃伅
	 * @param id
	 * @return
	 */
	WeldedJunction getWeldedJunctionById(BigInteger id);
	
	/**
	 * 鑾峰彇鐒婂彛鍒嗙被
	 * @param page 鍒嗛〉
	 * @param parent 鐖秈d
	 * @param material 鏉愯川
	 * @param external_diameter 澶栧緞
	 * @param wall_thickness 鐠у帤
	 * @param nextExternal_diameter 涓嬫父澶栧緞
	 * @return
	 */
	List<ModelDto> getHousClassify(Page page,BigInteger parent,String searchStr);
	
	List<ModelDto> getDetailNoLoads(Page page,WeldDto dto);
	
	/**
	 * 鑾峰彇鐒婃満id鎬昏揪鍥犲��
	 * @param str 鎷兼帴鐨勭剨鏈篿d鏉′欢
	 * @return
	 */
	BigInteger getDyneByJunctionno(String str);
	
	/**
	 * 鏌ヨ瀹炴椂鏁版嵁鍏徃鐒婃満鏁伴噺
	 * @param dto 鎵╁紶鍙傛暟绫�
	 * @param parent 鍏徃id
	 * @return
	 */
	List<ModelDto> getCompanyMachineCount(WeldDto dto,BigInteger parent);

	/**
	 * 鏌ヨ瀹炴椂鏁版嵁浜嬩笟閮�/椤圭洰閮ㄧ剨鏈烘暟閲�
	 * @param dto 鎵╁紶鍙傛暟绫�
	 * @param parent 浜嬩笟閮╥d
	 * @return
	 */
	List<ModelDto> getCaustMachineCount(WeldDto dto,BigInteger parent);
	
	
	/**
	 * 鏌ヨ瀹炴椂鏁版嵁闆嗗洟鐒婃満鏁伴噺
	 * @param dto 鎵╁紶鍙傛暟绫�
	 * @param parent 浜嬩笟閮╥d
	 * @return
	 */
	List<ModelDto> getBlocMachineCount(WeldDto dto,BigInteger parent);
	
	/**
	 * 鏍规嵁缁勭粐鏈烘瀯鍙婃椂闂寸偣鑾峰彇宸ヤ綔鎬绘椂闀�
	 * @param parent 缁勭粐鏈烘瀯id
	 * @param time 鏃堕棿鐐�
	 * @param mid 鐒婃満id
	 * @return
	 */
	BigInteger getCountByTime(BigInteger parent,String time,BigInteger mid);
	
	/**
	 * 鏍规嵁鐒婂伐鑾峰彇鐒婂彛
	 * @param dto 鎵╁睍鍙傛暟绫�
	 * @param welder 鐒婂伐缂栧彿
	 * @return
	 */
	List<ModelDto> getJunctionByWelder(Page page,WeldDto dto ,String welder);
	
	/**
	 * 鑾峰彇瓒呮爣鍥炴函
	 * @param time 瓒呮爣鏃堕棿
	 * @param welder 鐒婂伐
	 * @param jucntion 鐒婂彛
	 * @return
	 */
	List<ModelDto> getExcessiveBack(String time,String welder,String junction);
	
	/**
	 * 鑾峰彇闆嗗洟璁惧杩愯鏃堕暱
	 * @param page 鍒嗛〉
	 * @param parent 缁勭粐鏈烘瀯id
	 * @param dto dto.dtoTime1 璧峰鏃堕棿 dto.dtoTime2 缁撴潫鏃堕棿 
	 * @param startindex (limit鍚庣殑鍙傛暟) 杩斿洖璁板綍琛岀殑鍋忕Щ閲忥紝浠�0寮�濮�
	 * @param endindex  (limit鍚庣殑鍙傛暟) 杩斿洖璁板綍琛岀殑鏈�澶ф暟鐩�
	 * @return
	 */
	List<ModelDto> getBlocRunTime(Page page, BigInteger parent, WeldDto dto, int startindex, int endindex);
	
	/**
	 * 鑾峰彇璁惧浣跨敤鐜�
	 * @param time1 璧峰鏃堕棿(蹇呭～)
	 * @param time2 缁撴潫鏃堕棿(蹇呭～)
	 * @return
	 */
	List<ModelDto> getUseratio(String time1,String time2);
	
	/**
	 * 鑾峰彇璁惧缁翠慨鐜�
	 * @param page 鍒嗛〉
	 * @param dto dtoTime1 缁翠慨璧峰鏃堕棿 dtoTime2 缁翠慨缁撴潫鏃堕棿
	 * @return
	 */
	List<ModelDto> getMaintenanceratio(Page page, WeldDto dto);
	List<ModelDto> getMaintenanceratio(WeldDto dto);
	

	/**
	 * 鑾峰彇缁翠慨鎬绘鏁�/鎬荤淮淇垂鐢�/鎬昏澶囪垂鐢�
	 * @param dto dtoTime1 缁翠慨璧峰鏃堕棿 dtoTime2 缁翠慨缁撴潫鏃堕棿
	 * @return
	 */
	ModelDto getSumMaintenance(WeldDto dto);
	
	/**
	 * 得到焊机待机时间和总时间
	 * @param dto 截止当前时间为止的一个小时内
	 * @return
	 */
	List<ModelDto> getStandbytimeout(WeldDto dto);
}
