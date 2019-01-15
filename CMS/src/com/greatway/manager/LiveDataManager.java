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
	 * 查询事业部焊接工时
	 * @param dto扩展参数类
	 * @param parent 父id
	 * @return
	 */
	List<ModelDto> getCausehour(Page page,WeldDto dto,BigInteger parent);
	
	/**
	 * 查询公司焊接工时
	 * @param dto扩展参数类
	 * @param parent父id
	 * @return
	 */
	List<ModelDto> getCompanyhour(Page page,WeldDto dto,BigInteger parent);
	
	/**
	 * 项目部焊接工时
	 * @param dto扩展参数类
	 * @return
	 */
	List<ModelDto> getItemhour(Page page,WeldDto dto);
	
	/**
	 * 焊口焊接工时
	 * @param dto扩展参数类
	 * @return
	 */
	List<ModelDto> getJunctionHous(Page page,WeldDto dto);
	
	/**
	 * 事业部工艺超标统计
	 * @param dto 扩展参数类
	 * @param parent 父id
	 * @return
	 */
	List<ModelDto> getCauseOverproof(WeldDto dto,BigInteger parent);
	
	/**
	 * 项目部工艺超标统计
	 * @param dto 扩展参数类
	 * @param id 项目id
	 * @return
	 */
	List<ModelDto> getItemOverproof(WeldDto dto,BigInteger id);
	
	/**
	 * 获取当前所包含的项目
	 * @param parent 上级项目id
	 * @return
	 */
	List<LiveData> getAllInsf(BigInteger parent,int type);
	
	/**
	 * 获取当前跨度所包含的时间
	 * @param dto 扩展参数类
	 * @return
	 */
	List<ModelDto> getAllTime(Page page,WeldDto dto);
	
	/**
	 * 公司工艺超标统计
	 * @param dto 扩展参数类
	 * @return
	 */
	List<ModelDto> getCompanyOverproof(WeldDto dto,BigInteger parent);
	
	/**
	 * 超标明细
	 * @param dto 扩展参数类
	 * @return
	 */
	List<ModelDto> getDatailOverproof(Page page,WeldDto dto);
	
	/**
	 * 获取某焊工在某个时间/焊机/焊口的总工时
	 * @param welderno焊工编号
	 * @param machineno焊机编号
	 * @param junctionno焊口编号
	 * @param time时间
	 * @return
	 */
	int getCountTime(String welderno,String machineno,String junctionno,String time,BigInteger id);
	
	/**
	 * 获取焊机超标
	 * @param welderno焊工编号
	 * @param machineno焊机编号
	 * @param junctionno焊口编号
	 * @param time时间
	 * @return
	 */
	List<ModelDto> getjunctionoverproof(String welderno,String machineno,String junctionno,String time,BigInteger itemid);
	
	/**
	 * 获取公司超时待机统计
	 * @param dto 扩展参数类
	 * @param num 超时点
	 * @return
	 */
	List<ModelDto> getcompanyOvertime(WeldDto dto , String num,BigInteger parent);
	
	/**
	 * 获取事业部超时待机统计
	 * @param dto扩展参数类
	 * @param num超时点
	 * @param parent上级id
	 * @return
	 */
	List<ModelDto> getCaustOvertime(WeldDto dto , String num,BigInteger parent);
	
	/**
	 * 获取项目部超时待机统计
	 * @param dto扩展参数类
	 * @param num超时点
	 * @param parent上级id
	 * @return
	 */
	List<ModelDto> getItemOvertime(WeldDto dto , String num);
	
	/**
	 * 获取所有焊口
	 * @param parent 所属项目id
	 * @return
	 */
	List<LiveData> getJunction(BigInteger parent);
	
	/**
	 * 待机明细
	 * @param dto扩展参数类
	 * @param num超时点
	 * @param parent 项目id
	 * @return
	 */
	List<ModelDto> getDetailovertime(Page page,WeldDto dto , String num,String parent);
	
	/**
	 * 公司负荷率
	 * @param dto扩展参数类
	 * @return
	 */
	List<ModelDto> getCompanyLoads(WeldDto dto,BigInteger parent);
	
	
	/**
	 * 事业部负荷率
	 * @param dto扩展参数类
	 * @param parent上级id
	 * @return
	 */
	List<ModelDto> getCaustLoads(WeldDto dto,BigInteger parent);
	
	/**
	 * 项目部负荷率
	 * @param dto扩展参数类
	 * @param parent上级id
	 * @return
	 */
	List<ModelDto> getItemLoads(WeldDto dto,BigInteger parent);
	
	/**
	 * 获取所有焊机
	 * @param parent 项目id
	 * @param dto扩展参数类
	 * @return
	 */
	List<LiveData> getMachine(BigInteger parent);
	
	/**
	 * 获取负荷率明细
	 * @param dto扩展参数类
	 * @param machineno焊机编号
	 * @return
	 */
	List<ModelDto> getDetailLoads(Page page,WeldDto dto,String machineno);
	
	/**
	 * 获取公司空载率
	 * @param dto 扩展参数类
	 * @return
	 */
	List<ModelDto> getCompanyNoLoads(WeldDto dto,BigInteger parent);

	/**
	 * 获取事业部空载率
	 * @param dto 扩展参数类
	 * @param parent 父id
	 * @return
	 */
	List<ModelDto> getCaustNOLoads(WeldDto dto,BigInteger parent);
	
	/**
	 * 获取项目部空载率
	 * @param dto 扩展参数类
	 * @param parent 父id
	 * @return
	 */
	List<ModelDto> getItemNOLoads(WeldDto dto,BigInteger parent,String equipmentno);
	
	/**
	 * 公司闲置率
	 * @param dto 扩展参数类
	 * @return
	 */
	List<ModelDto> getCompanyIdle(WeldDto dto,BigInteger parent);
	
	/**
	 * 事业部闲置率
	 * @param dto扩展参数类
	 * @param parent上级id
	 * @return
	 */
	List<ModelDto> getCaustIdle(WeldDto dto,BigInteger parent);
	
	/**
	 * 项目部闲置率
	 * @param dto扩展参数类
	 * @param itemid项目id
	 * @return
	 */
	List<ModelDto> getItemIdle(WeldDto dto,BigInteger itemid);
	
	/**
	 * 获取项目所有焊机数量
	 * @param id 项目id
	 * @return
	 */
	int getMachineCount(BigInteger id);
	
	/**
	 * 公司单台设备运行数据统计
	 * @param dto 扩展参数类
	 * @param parent 上级id
	 * @return
	 */
	List<ModelDto> getCompanyUse(Page page,WeldDto dto,BigInteger parent);
	
	/**
	 * 事业部单台设备运行数据统计
	 * @param dto 扩展参数类
	 * @param insid 项目id
	 * @return
	 */
	List<ModelDto> getCaustUse(Page page,WeldDto dto,BigInteger insid);
	

	/**
	 * 项目部单台设备运行数据统计
	 * @param dto 扩展参数类
	 * @param insid 项目id
	 * @return
	 */
	List<ModelDto> getItemUse(Page page,WeldDto dto,BigInteger insid);
	
	/**
	 * 获取用户id
	 * @return
	 */
	BigInteger getUserId(HttpServletRequest request);
	
	/**
	 * 获取所有时间
	 * @param dto
	 * @return
	 */
	List<ModelDto> getAllTimes(WeldDto dto);
	
	/**
	 * 集团焊接工时
	 * @param dto 扩展参数类
	 * @return
	 */
	List<ModelDto> getBlochour(Page page,WeldDto dto);
	
	/**
	 * 集团超标统计
	 * @param dto 扩展参数类
	 * @return
	 */
	List<ModelDto> getBlocOverproof(WeldDto dto);
	
	/**
	 * 集团超时待机统计
	 * @param dto 扩展参数类
	 * @param num 超时点
	 * @return
	 */
	List<ModelDto> getBlocOvertime(WeldDto dto,String num);
	
	/**
	 * 集团负载率
	 * @param dto 扩展参数类
	 * @return
	 */
	List<ModelDto> getBlocLoads(WeldDto dto);
	
	/**
	 * 集团空载率
	 * @param dto 扩展参数类
	 * @return
	 */
	List<ModelDto> getBlocNoLoads(WeldDto dto);
	
	/**
	 * 集团闲置率
	 * @param dto 扩展参数类
	 * @return
	 */
	List<ModelDto> getBlocIdle(WeldDto dto);
	
	/**
	 * 集团单台设备运行数据统计
	 * @param dto  扩展参数类
	 * @param parent 上级的父id
	 * @return
	 */
	List<ModelDto> getBlocUse(Page page,WeldDto dto,BigInteger parent);
	
	/**
	 * 获取集团下的公司
	 * @return
	 */
	List<LiveData> getBlocChildren();
	
	/**
	 * 事业部工效
	 * @param page 分页
	 * @param parent 父id
	 * @param dto 扩展参数类
	 * @return
	 */
	List<ModelDto> caustEfficiency(Page page,BigInteger parent,WeldDto dto,int min,int max);
	
	/**
	 * 公司工效
	 * @param page 分页
	 * @param parent 父id
	 * @param dto 扩展参数类
	 * @return
	 */
	List<ModelDto> companyEfficiency(Page page ,BigInteger parent,WeldDto dto,int min,int max);
	
	/**
	 * 集团工效
	 * @param page 分页
	 * @param parent 父id
	 * @param dto 扩展参数类
	 * @return
	 */
	List<ModelDto> blocEfficiency(Page page,WeldDto dto,BigInteger parent,int min,int max);
	
	/**
	 * 获取工效最大值最小值以及平均跨度
	 */
	List<ModelDto> getEfficiencyChartNum(WeldDto dto,BigInteger parent);
	
	/**
	 * 获取工效图表数据
	 */
	List<ModelDto> getEfficiencyChart(WeldDto dto,BigInteger parent,int minnum,int avgnum);
	
	/**
	 * 根据id获取焊口信息
	 * @param id
	 * @return
	 */
	WeldedJunction getWeldedJunctionById(BigInteger id);
	
	/**
	 * 获取焊口分类
	 * @param page 分页
	 * @param parent 父id
	 * @param material 材质
	 * @param external_diameter 外径
	 * @param wall_thickness 璧厚
	 * @param nextExternal_diameter 下游外径
	 * @return
	 */
	List<ModelDto> getHousClassify(Page page,BigInteger parent,String searchStr);
	
	List<ModelDto> getDetailNoLoads(Page page,WeldDto dto);
	
	/**
	 * 获取焊机id总达因值
	 * @param str 拼接的焊机id条件
	 * @return
	 */
	BigInteger getDyneByJunctionno(String str);
	
	/**
	 * 查询实时数据公司焊机数量
	 * @param dto 扩张参数类
	 * @param parent 公司id
	 * @return
	 */
	List<ModelDto> getCompanyMachineCount(WeldDto dto,BigInteger parent);

	/**
	 * 查询实时数据事业部/项目部焊机数量
	 * @param dto 扩张参数类
	 * @param parent 事业部id
	 * @return
	 */
	List<ModelDto> getCaustMachineCount(WeldDto dto,BigInteger parent);
	
	
	/**
	 * 查询实时数据集团焊机数量
	 * @param dto 扩张参数类
	 * @param parent 事业部id
	 * @return
	 */
	List<ModelDto> getBlocMachineCount(WeldDto dto,BigInteger parent);
	
	/**
	 * 根据组织机构及时间点获取工作总时长
	 * @param parent 组织机构id
	 * @param time 时间点
	 * @param mid 焊机id
	 * @return
	 */
	double getCountByTime(BigInteger parent,String time1,String time2,BigInteger mid, int type);
	
	/**
	 * 获取超标回溯
	 * @param time 超标时间
	 * @param welder 焊工
	 * @param jucntion 焊口
	 * @return
	 */
	List<ModelDto> getExcessiveBack(Page page,WeldDto dto);

	/**
	 * 根据id获取连续超标明细
	 * @param id
	 * @return
	 */
	List<ModelDto> getExcessiveBackDetail(BigInteger id);
	
	/**
	 * 获取设备运行时长
	 * @param page 分页
	 * @param parent 上级id
	 * @param dto dto.dtoTime1 起始时间 dto.dtoTime2 结束时间
	 * @param startindex (limit参数) 开始下标
	 * @param endindex  (limit参数) 结束下标
	 * @return
	 */
	List<ModelDto> getBlocRunTime(Page page, BigInteger parent, WeldDto dto, int startindex, int endindex);
	List<ModelDto> getBlocRunTime(BigInteger parent, WeldDto dto, int startindex, int endindex);
	
	/**
	 * 设备利用率
	 * @param time1 起始时间
	 * @param time2 结束时间
	 * @return
	 */
	List<ModelDto> getUseratio(String time1,String time2,String insftype);
	
	/**
	 * 设备维修率
	 * @param dto dto.parent组织机构id dtoTime1 起始时间 dtoTime2 结束时间
	 * @return
	 */
	List<ModelDto> getMaintenanceratio(WeldDto dto);
	

	/**
	 * 获取设备维修总次数，总金额以及设备价值
	 * @param dto dtoTime1 起始时间 dtoTime2 结束时间
	 * @return
	 */
	ModelDto getSumMaintenance(WeldDto dto);
	
	/**
	 * 得到焊机待机时间和总时间
	 * @param dto 截止当前时间为止的一个小时内
	 * @return
	 */
	List<ModelDto> getStandbytimeout(WeldDto dto,int str);
	
	/**
	 * 不同厂商不同类型的焊机的维修费用
	 * @param dto
	 * @param itemid
	 * @return
	 */
	List<ModelDto> getItemTypeMaintain(WeldDto dto,BigInteger itemid);
	
	/**
	 * 根据不同厂商不同类型获取总焊机费用及厂商信息
	 * @return
	 */
	List<ModelDto> getItemMachineSumMoneyByType(BigInteger itemid);
	List<ModelDto> getItemMachineSumMoneyByType(Page page,BigInteger itemid);
	
	/**
	 * 获取各组织机构焊机价格
	 * @return
	 */
	List<ModelDto> getMachineMoney();
	
	/**
	 * 设备故障率
	 * @param dto dto.parent组织机构id dto.dtoTime1起始时间 dto.dtoTime2结束时间
	 * @return
	 */
	List<ModelDto> getFaultRatio(WeldDto dto);

	/**
	 * 不同厂商不同类型的焊机维修次数
	 * @param dto dto.parent项目部id dto.dtoTime1起始时间 dto.dtoTime2结束时间
	 * @return
	 */
	List<ModelDto> getMaintenanceNum(WeldDto dto);

	/**
	 * 不同厂商不同类型的焊机故障次数
	 * @param dto dto.parent项目部id dto.dtoTime1起始时间 dto.dtoTime2结束时间
	 * @return
	 */
	List<ModelDto> getFaultNum(WeldDto dto);
	
	/**
	 * 根据故障类别求故障率
	 * @param dto dto.parent组织机构id dto.dtoTime1起始时间 dto.dtoTime2结束时间
	 * @return
	 */
	List<ModelDto> getFaultRatioByType(WeldDto dto);
	
	/**
	 * 获取故障明细
	 * @param dto dto dto.parent组织机构id dto.dtoTime1起始时间 dto.dtoTime2结束时间
	 * @return
	 */
	List<ModelDto> getFaultDetail(Page page,WeldDto dto);
	
	/**
	 * 获取在线焊工人数总时长
	 * @param dto dto.dtoTime1起始时间 dto.dtoTime2结束时间
	 * @return
	 */
	List<ModelDto> getOnlineNumber(WeldDto dto,double time);
	
	/**
	 * 获取开机时长,焊接时长，待机时长
	 * @param dto dto.dtoTime1起始时间 dto.dtoTime2结束时间
	 * @return
	 */
	List<ModelDto> getOperatoreTime(WeldDto dto);
	
	/**
	 * 获取项目部正常开机时长,焊接时长，待机时长，焊工编号，姓名
	 * @param dto dto.dtoTime1起始时间 dto.dtoTime2结束时间
	 * @return
	 */
	List<ModelDto> getItemWorkTime(Page page,WeldDto dto);
	
	/**
	 * 获取项目部正常开机时长,焊接时长，待机时长，焊工编号，姓名
	 * @param dto dto.dtoTime1起始时间 dto.dtoTime2结束时间
	 * @return
	 */
	List<ModelDto> getItemWorkTime(WeldDto dto);
	
	
	/**
	 * 获取项目部待机开机时长,焊接时长，待机时长，焊工编号，姓名
	 * @param dto dto.dtoTime1起始时间 dto.dtoTime2结束时间
	 * @return
	 */
	List<ModelDto> getItemStandbyTime(WeldDto dto);
	
	/**
	 * 获取各部门及焊机数量
	 * @param parent 组织机构id
	 * @return
	 */
	List<ModelDto> getInsfandMachinenum(BigInteger parent);
	List<ModelDto> getInsfandMachinenum(Page page,BigInteger parent);
	
	/**
	 * 获取持续时间
	 * @param sql 获取持续时间sql
	 * @return
	 */
	List<ModelDto> getDurationTime(String time1,String time2,int timetype);
	/**
	 * 获取持续时间
	 * @param sql 获取持续时间sql
	 * @return
	 */
	List<ModelDto> getDurationTime(Page page,String time1,String time2,int timetype);
	
	/**
	 * 获取利用率明细
	 * @param page 分页
	 * @param fid 厂商id
	 * @param type 厂商类型
	 * @param dto dto.dtoTime1起始时间dto.dtoTime2结束时间
	 * @return
	 */
	List<ModelDto> getUseDetail(Page page,BigInteger fid,int type,WeldDto dto);
	
	/**
	 * 焊机前十最高最低排行
	 * @param dto
	 * @return
	 */
	List<ModelDto> getWeldingmachineList(WeldDto dto);

	/**
	 * 焊工前十最高最低排行
	 * @param dto
	 * @return
	 */
	List<ModelDto> getWelderList(WeldDto dto);
	
	/**
	 * 获取连续超时待机
	 * @param dto
	 * @param num
	 * @param type
	 * @return
	 */
	List<ModelDto> getNewOvertime(WeldDto dto,int num,String type);
	
	/**
	 * 获取连续超标明细
	 * @param dto
	 * @param num
	 * @return
	 */
	List<ModelDto> getNewOvertimeDetail(Page page,WeldDto dto,int num);
	
	/**
	 * 根据焊机类型获取工作焊机数量
	 * @param dto 
	 * @return
	 */
	List<ModelDto> getNewIdle(WeldDto dto);
	
	/**
	 * 根据类型获取焊机数量
	 * @param parent 组织机构id
	 * @return
	 */
	List<ModelDto> getMachineTypeTotal(BigInteger parent);
	
	/**
	 * 获取焊工焊接时长，工作时长（指按分统计的焊接时长）
	 * @param page
	 * @param dto
	 * @param insftype
	 * @return
	 */
	List<ModelDto> getWelderWorkTime(Page page,WeldDto dto,String insftype);
	List<ModelDto> getWelderWorkTime(WeldDto dto,String insftype);
	
	/**
	 * 获取平均焊接时长，工作时长
	 * @param dto
	 * @param insftype
	 * @return
	 */
	ModelDto getWelderAvgWorkTime(WeldDto dto);
}
