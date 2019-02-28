package com.greatway.controller;

import java.io.File;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.greatway.dto.ModelDto;
import com.greatway.dto.WeldDto;
import com.greatway.manager.InsframeworkManager;
import com.greatway.manager.LiveDataManager;
import com.greatway.manager.MaintainManager;
import com.greatway.manager.WeldingMachineManager;
import com.greatway.manager.WpsService;
import com.greatway.model.Gather;
import com.greatway.model.WeldingMachine;
import com.greatway.model.WeldingMaintenance;
import com.greatway.model.Wps;
import com.greatway.page.Page;
import com.greatway.util.CommonExcelUtil;
import com.greatway.util.IsnullUtil;
import com.spring.model.MyUser;

@Controller
@RequestMapping(value = "/export", produces = { "text/json;charset=UTF-8" })
public class ExportExcelController {
	
	private static final long serialVersionUID = -4171187629012625142L;
	
	@Autowired
	private WeldingMachineManager wmm;
	@Autowired
	private MaintainManager mm;
	@Autowired
	private LiveDataManager ldm;
	@Autowired
	private InsframeworkManager insm;
	@Autowired
	private WpsService wpss;
	private String filename;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");
	IsnullUtil iutil = new IsnullUtil();
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@RequestMapping("/exporWeldingMachine")
	@ResponseBody
	public ResponseEntity<byte[]> exporWeldingMachine(HttpServletRequest request,HttpServletResponse response){
		File file = null;
		try {
			String str=(String) request.getSession().getAttribute("searchStr");
			String parentId = request.getParameter("parent");
			BigInteger parent = null;
			if(iutil.isNull(parentId)){
				parent = new BigInteger(parentId);
			}else{
				MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
					    .getAuthentication()  
					    .getPrincipal();
				long uid = myuser.getId();
				parent = insm.getUserInsfId(BigInteger.valueOf(uid));
			}
			List<WeldingMachine> list = wmm.getWeldingMachine(parent,str);
			
			String[] titles = new String[]{"设备编码","设备类型","入厂时间","所属项目","状态","厂家","厂家类型","是否在网","采集序号","位置","价值"};
			Object[][] data = new Object[list.size()][11];
			for(int i =0; i<list.size();i++){
				data[i][0] = list.get(i).getEquipmentNo();
				data[i][1] = list.get(i).getTypename();
				data[i][2] = list.get(i).getJoinTime();
				data[i][3] = list.get(i).getInsframeworkId().getName();
				data[i][4] = list.get(i).getStatusname();
				data[i][5] = list.get(i).getManufacturerId().getName();
				data[i][6] = list.get(i).getManufacturerId().getType();
				if(list.get(i).getIsnetworking()==0){
					data[i][7] = "是";
				}else{
					data[i][7] = "否";
				}
				Gather gather = list.get(i).getGatherId();
				if(gather!=null){
					data[i][8] = gather.getGatherNo();
				}else{
					data[i][8] = null;
				}
				data[i][9] = list.get(i).getPosition();
				data[i][10] = list.get(i).getMoney();
			}
			filename = "焊机设备" + sdf.format(new Date()) + ".xls";

			ServletContext scontext=request.getSession().getServletContext();
			//获取绝对路径
			String abpath=scontext.getRealPath("");
			//String contextpath=scontext. getContextPath() ; 获取虚拟路径
			
			String path = abpath+"excelfiles/" + filename;
			new CommonExcelUtil(titles, data, path, "焊机设备数据");
			
			file = new File(path);
			HttpHeaders headers = new HttpHeaders();
			String fileName = "";
			
			fileName = new String(filename.getBytes("gb2312"),"iso-8859-1");
			
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			
			//处理ie无法下载的问题
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setHeader( "Content-Disposition", 
					"attachment;filename=\""+ fileName); 
			ServletOutputStream o = response.getOutputStream();
			o.flush();
			
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		}catch (Exception e) {
			e.printStackTrace();
	    	return null;
		}  finally {
			file.delete();
		}
	}
	
	@RequestMapping("/exporMaintain")
	@ResponseBody
	public ResponseEntity<byte[]> exporMaintain(HttpServletRequest request,HttpServletResponse response){
		File file = null;
		try{
			String weldingmachineId = request.getParameter("wid");
			String parent = request.getParameter("parent");
			String str = "";
			String searchStr = request.getParameter("searchStr");
			BigInteger parentid = null;
			if(iutil.isNull(parent)){
				parentid = new BigInteger(parent);
			}else{
				MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
					    .getAuthentication()  
					    .getPrincipal();
				long uid = myuser.getId();
				parentid = insm.getUserInsfId(BigInteger.valueOf(uid));
				str = request.getParameter("str");
			}
			if(iutil.isNull(searchStr)){
				if(iutil.isNull(str)){
					searchStr += " and "+ str;
				}else{
					searchStr += " and (i.fid="+parentid+" or ins.fid="+parentid+" or insf.fid="+parentid+" or insf.fparent="+parentid+")";
				}
			} else{
				searchStr = "(i.fid="+parentid+" or ins.fid="+parentid+" or insf.fid="+parentid+" or insf.fparent="+parentid+")";
				if(!iutil.isNull(str)){
					searchStr = str;
				}
			}
			BigInteger wid = null;
			if(iutil.isNull(weldingmachineId)){
				wid = new BigInteger(weldingmachineId);
			}
			List<WeldingMaintenance> list = mm.getWeldingMaintenanceAll(wid, str);
			
			String[] titles = new String[]{"设备编码","维修人员","维修起始时间","维修价格","维修结束时间","维修类型","维修说明"};
			Object[][] data = new Object[list.size()][7];
			for(int i =0; i<list.size();i++){
				data[i][0] = list.get(i).getWelding().getEquipmentNo();
				data[i][1] = list.get(i).getMaintenance().getViceman();
				data[i][2] = list.get(i).getMaintenance().getStartTime();
				data[i][3] = list.get(i).getMaintenance().getMoney();
				data[i][4] = list.get(i).getMaintenance().getEndTime();
				data[i][5] = list.get(i).getMaintenance().getTypename();
				data[i][6] = list.get(i).getMaintenance().getDesc();
			}
			filename = "焊机维修" + sdf.format(new Date())+".xls";

			ServletContext scontext=request.getSession().getServletContext();
			//获取绝对路径
			String abpath=scontext.getRealPath("");
			//String contextpath=scontext. getContextPath() ; 获取虚拟路径
			
			String path = abpath+"excelfiles/" + filename;
			
			new CommonExcelUtil(titles, data, path, "焊机维修数据");
			file = new File(path);
			HttpHeaders headers = new HttpHeaders();
			String fileName = "";
			fileName = new String(filename.getBytes("gb2312"),"iso-8859-1");
		
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			
			//处理ie无法下载的问题
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setHeader( "Content-Disposition", 
					"attachment;filename=\""+ fileName); 
			ServletOutputStream o = response.getOutputStream();
			o.flush();
			
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			return null;
		} finally {
			file.delete();
		}
	}
	
	@RequestMapping("/exporWelderWorkTime")
	@ResponseBody
	public ResponseEntity<byte[]> exporWelderWorkTime(HttpServletRequest request,HttpServletResponse response){
		File file = null;
		try {
			int pageIndex = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("rows"));
			Page page = new Page(pageIndex,pageSize,0);
			String parentid = request.getParameter("parent");
			String time1 = request.getParameter("time1");
			String time2 = request.getParameter("time2");
			int status = Integer.parseInt(request.getParameter("status"));
			WeldDto dto = new WeldDto();
			if(iutil.isNull(time1)){
				dto.setDtoTime1(time1);
			}
			if(iutil.isNull(time2)){
				dto.setDtoTime2(time2);
			}
			if(iutil.isNull(parentid)){
				dto.setParent(new BigInteger(parentid));
			}
			int usertype = insm.getTypeById(dto.getParent());
			String insftype = "itemid";
			if(usertype==20){
				insftype = "fid";
			}else if(usertype==21){
				insftype = "caustid";
			}
			List<ModelDto> list = null;
			if(status == 1){
				list = ldm.getWelderWorkTime(dto, insftype);
			}else{
				list = ldm.getWelderWorkTime(page, dto, insftype);
				if(list != null){
					PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(list);
				}
			}
			
			String[] titles = new String[]{"姓名","编号","焊接时长","工作时长"};
			Object[][] data = new Object[list.size()][4];
			for(int i =0; i<list.size();i++){
				data[i][0] = list.get(i).getFname();
				data[i][1] = list.get(i).getFwelder_id();
				data[i][2] = (double)Math.round(list.get(i).getWorktime()*100)/100;
				data[i][3] = (double)Math.round(list.get(i).getTime()*100)/100;
			}
			filename = "焊工焊接工时" + sdf.format(new Date()) + ".xls";

			ServletContext scontext=request.getSession().getServletContext();
			//获取绝对路径
			String abpath=scontext.getRealPath("");
			//String contextpath=scontext. getContextPath() ; 获取虚拟路径
			
			String path = abpath+"excelfiles/" + filename;
			new CommonExcelUtil(titles, data, path, "焊机设备数据");
			
			file = new File(path);
			HttpHeaders headers = new HttpHeaders();
			String fileName = "";
			
			fileName = new String(filename.getBytes("gb2312"),"iso-8859-1");
			
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			
			//处理ie无法下载的问题
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setHeader( "Content-Disposition", 
					"attachment;filename=\""+ fileName); 
			ServletOutputStream o = response.getOutputStream();
			o.flush();
			
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		}catch (Exception e) {
	    	return null;
		}  finally {
			file.delete();
		}
	}
	
	@RequestMapping("/exporWPS")
	@ResponseBody
	public ResponseEntity<byte[]> exporWPS(HttpServletRequest request,HttpServletResponse response){
		File file = null;
		try {
			String str=(String) request.getSession().getAttribute("searchStr");
			String parentId = request.getParameter("parent");
			BigInteger parent = null;
			if(iutil.isNull(parentId)){
				parent = new BigInteger(parentId);
			}else{
				MyUser myuser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				long uid = myuser.getId();
				parent = insm.getUserInsfId(BigInteger.valueOf(uid));
			}
			List<Wps> list = wpss.findWpsAll(parent, str);
			
			String[] titles = new String[]{"编号","版本","焊工考试项目代号","报告编号","自动化程度","评定标准","稳压系统","有效期","自动跟踪系统","坡口形式",
					"衬垫（材料）","焊缝金属厚度(mm)","焊缝金属厚度(mm)","管子直径(mm)","其他（焊接接头）","类别号","牌号","规格","焊材类型","焊材型（牌）号1",
					"焊材规格1","焊材型（牌）号2","焊材规格2","焊剂型（牌）号","焊接位置","焊接方向","其他（焊接位置）","正面1(气种类型)","正面2(气流量)",
					"背面1(气种类型)","背面2","背面3(气流量)","尾部","预热温度","层间温度","预热方式","温度范围","保温时间","其他（焊后热处理）","线能量范围",
					"喷嘴尺寸","导电嘴与工件距离","清根方式","焊缝层数范围","焊缝层数范围","钨极类型/尺寸","熔滴过渡方式","直向焊、摆动焊及摆动方法","背面、打底及中间焊道清理方法","编制单位名称"};
			Object[][] data = new Object[list.size()][50];
			for(int i =0; i<list.size();i++){
				data[i][0] = list.get(i).getFwpsnum();
				data[i][1] = list.get(i).getFversions();
				data[i][2] = list.get(i).getFproject_code();
				data[i][3] = list.get(i).getFreport_number();
				data[i][4] = list.get(i).getFdegree();
				data[i][5] = list.get(i).getFevaluation_standard();
				data[i][6] = list.get(i).getFstabilivolt_system();
				data[i][7] = list.get(i).getFvalidity();
				data[i][8] = list.get(i).getFautomatic();
				data[i][9] = list.get(i).getFgroove_type();
				data[i][10] = list.get(i).getFmaterials();
				data[i][11] = list.get(i).getFthickness1();
				data[i][12] = list.get(i).getFthickness2();
				data[i][13] = list.get(i).getFdiameter();
				data[i][14] = list.get(i).getFeles1();
				data[i][15] = list.get(i).getFcategory();
				data[i][16] = list.get(i).getFshop_sign();
				data[i][17] = list.get(i).getFspecification();
				data[i][18] = list.get(i).getFmaterials_type();
				data[i][19] = list.get(i).getFmaterials_number1();
				data[i][20] = list.get(i).getFmaterials_specification1();
				data[i][21] = list.get(i).getFmaterials_number2();
				data[i][22] = list.get(i).getFmaterials_specification2();
				data[i][23] = list.get(i).getFsoldering_number();
				data[i][24] = list.get(i).getFposition();
				data[i][25] = list.get(i).getFdirection();
				data[i][26] = list.get(i).getFelse2();
				data[i][27] = list.get(i).getFfront1();
				data[i][28] = list.get(i).getFfront2();
				data[i][29] = list.get(i).getFreverse1();
				data[i][30] = list.get(i).getFreverse2();
				data[i][31] = list.get(i).getFreverse3();
				data[i][32] = list.get(i).getFtail();
				data[i][33] = list.get(i).getFpreheating_temperature();
				data[i][34] = list.get(i).getFtemperature();
				data[i][35] = list.get(i).getfPreheat_way();
				data[i][36] = list.get(i).getFtemperature_range();
				data[i][37] = list.get(i).getFsoaking_time();
				data[i][38] = list.get(i).getFeles3();
				data[i][39] = list.get(i).getFscope();
				data[i][40] = list.get(i).getFnozzle();
				data[i][41] = list.get(i).getFdistance();
				data[i][42] = list.get(i).getFback_chipping();
				data[i][43] = list.get(i).getFlayer_scope1();
				data[i][44] = list.get(i).getFlayer_scope2();
				data[i][45] = list.get(i).getFtungsten_electrode();
				data[i][46] = list.get(i).getFtransient_mode();
				data[i][47] = list.get(i).getFmethod1();
				data[i][48] = list.get(i).getFmethod2();
				data[i][49] = list.get(i).getFitemname();
			}
			filename = "工艺管理" + sdf.format(new Date()) + ".xls";

			ServletContext scontext=request.getSession().getServletContext();
			//获取绝对路径
			String abpath=scontext.getRealPath("");
			//String contextpath=scontext. getContextPath() ; 获取虚拟路径
			
			String path = abpath+"excelfiles/" + filename;
			new CommonExcelUtil(titles, data, path, "工艺管理数据");
			
			file = new File(path);
			HttpHeaders headers = new HttpHeaders();
			String fileName = "";
			
			fileName = new String(filename.getBytes("gb2312"),"iso-8859-1");
			
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			
			//处理ie无法下载的问题
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setHeader( "Content-Disposition", 
					"attachment;filename=\""+ fileName); 
			ServletOutputStream o = response.getOutputStream();
			o.flush();
			
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		}catch (Exception e) {
	    	return null;
		}  finally {
			file.delete();
		}
	}
	
	@RequestMapping("/exporChiledrenWPS")
	@ResponseBody
	public ResponseEntity<byte[]> exporChiledrenWPS(HttpServletRequest request,HttpServletResponse response){
		File file = null;
		try {
			String str=(String) request.getSession().getAttribute("searchStr");
			String parentId = request.getParameter("parent");
			BigInteger parent = null;
			if(iutil.isNull(parentId)){
				parent = new BigInteger(parentId);
			}else{
				MyUser myuser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				long uid = myuser.getId();
				parent = insm.getUserInsfId(BigInteger.valueOf(uid));
			}
			List<Wps> list = wpss.findAll(parent, null,str);
			
			String[] titles = new String[]{"工艺编号","焊层","焊接方法","焊材型（牌）号","焊材规格（mm）","焊接电流极性","焊接电流最小范围（>A）","焊接电流最大范围（>A）","电压最小范围 （V）","电压最大范围 （V）","焊接速度（mm/min）","编制单位名称"};
			Object[][] data = new Object[list.size()][12];
			for(int i =0; i<list.size();i++){
				data[i][0] = list.get(i).getFwpsnum();
				if(list.get(i).getFweld_prechannel()==1){
					data[i][1] = "打底";
				}else if(list.get(i).getFweld_prechannel()==2){
					data[i][1] = "填充";
				}else if(list.get(i).getFweld_prechannel()==3){
					data[i][1] = "盖面";
				}else if(list.get(i).getFweld_prechannel()==4){
					data[i][1] = "背面封底";
				}else{
					data[i][1] = "第一层";
				}
				data[i][2] = list.get(i).getFwelding_method();
				data[i][3] = list.get(i).getFtype();
				data[i][4] = list.get(i).getFchildren_specification();
				data[i][5] = list.get(i).getFpolarity();
				data[i][6] = list.get(i).getFweld_i_min();
				data[i][7] = list.get(i).getFweld_i_max();
				data[i][8] = list.get(i).getFweld_v_min();
				data[i][9] = list.get(i).getFweld_v_max();
				data[i][10] = list.get(i).getFwelding_speed();
				data[i][11] = list.get(i).getFitemname();
			}
			filename = "焊接参数" + sdf.format(new Date()) + ".xls";

			ServletContext scontext=request.getSession().getServletContext();
			//获取绝对路径
			String abpath=scontext.getRealPath("");
			//String contextpath=scontext. getContextPath() ; 获取虚拟路径
			
			String path = abpath+"excelfiles/" + filename;
			new CommonExcelUtil(titles, data, path, "焊接参数数据");
			
			file = new File(path);
			HttpHeaders headers = new HttpHeaders();
			String fileName = "";
			
			fileName = new String(filename.getBytes("gb2312"),"iso-8859-1");
			
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			
			//处理ie无法下载的问题
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setHeader( "Content-Disposition", 
					"attachment;filename=\""+ fileName); 
			ServletOutputStream o = response.getOutputStream();
			o.flush();
			
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		}catch (Exception e) {
	    	return null;
		}  finally {
			file.delete();
		}
	}
}
