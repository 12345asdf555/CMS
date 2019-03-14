package com.greatway.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greatway.manager.DictionaryManager;
import com.greatway.manager.GatherManager;
import com.greatway.manager.InsframeworkManager;
import com.greatway.manager.MaintainManager;
import com.greatway.manager.WeldingMachineManager;
import com.greatway.manager.WpsService;
import com.greatway.model.EquipmentManufacturer;
import com.greatway.model.Gather;
import com.greatway.model.Insframework;
import com.greatway.model.MaintenanceRecord;
import com.greatway.model.WeldingMachine;
import com.greatway.model.WeldingMaintenance;
import com.greatway.model.Wps;
import com.greatway.util.IsnullUtil;
import com.greatway.util.UploadUtil;
import com.greatway.util.UploadUtil;
import com.spring.model.MyUser;

import net.sf.json.JSONObject;

/**
 * excel导入数据库
 * @author gpyf16
 *
 */
@Controller
@RequestMapping(value = "/import", produces = { "text/json;charset=UTF-8" })
public class ImportExcelController {
	@Autowired
	private WeldingMachineManager wmm;
	@Autowired
	private MaintainManager mm;
	@Autowired
	private InsframeworkManager im;
	@Autowired
	private DictionaryManager dm;
	@Autowired
	private GatherManager gm;
	@Autowired
	private WpsService wpss;
	IsnullUtil iutil = new IsnullUtil();
	
	/**
	 * 导入焊机设备
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/importWeldingMachine")
	@ResponseBody
	public String importWeldingMachine(HttpServletRequest request,
			HttpServletResponse response){
		UploadUtil u = new UploadUtil();
		JSONObject obj = new JSONObject();
		String path = "";
		try{
			path = u.uploadFile(request, response);
			List<WeldingMachine> list = xlsxWm(path);
			//删除已保存的excel文件
			File file  = new File(path);
			file.delete();
			for(WeldingMachine wm : list){
				wm.setTypeId(dm.getvaluebyname(4,wm.getTypename()));
				wm.setStatusId(dm.getvaluebyname(3,wm.getStatusname()));
				wm.getManufacturerId().setId(wmm.getManuidByValue(wm.getManufacturerId().getName(),wm.getManufacturerId().getType()));
				String name = wm.getInsframeworkId().getName();
				wm.getInsframeworkId().setId(wmm.getInsframeworkByName(name));
				Gather gather = wm.getGatherId();
				BigInteger gatherid = new BigInteger("0");
				int count2 = 0;
				if(gather!=null){
					count2 = wmm.getGatheridCount(wm.getInsframeworkId().getId(),gather.getGatherNo());
					gatherid = gm.getGatherByNo(gather.getGatherNo());
				}
				if(wm.getJoinTime()==null){
					wm.setJoinTime("");
				}
				wm.setGatherId(gather);
				//编码唯一
				int count1 = wmm.getEquipmentnoCount(wm.getEquipmentNo());
				if(count1>0 || count2>0){
					obj.put("msg","导入失败，请检查您的设备编码、采集序号是否已存在！");
					obj.put("success",false);
					return obj.toString();
				}
				//当前层级
				String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
				//获取当前用户
				Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				MyUser myuser = (MyUser)object;
				//获取项目层url
				String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
				//获取公司发布地址
				String companyurl = im.webserviceDto(request, wm.getInsframeworkId().getId());
				//客户端执行操作
				JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
				Client client = dcf.createClient(companyurl);
				iutil.Authority(client);
				if(wm.getMoney()<0){
					wm.setMoney(0);
				}
				String obj1 = "{\"CLASSNAME\":\"weldingMachineWebServiceImpl\",\"METHOD\":\"addWeldingMachine\"}";
				String obj2 = "{\"EQUIPMENTNO\":\""+wm.getEquipmentNo()+"\",\"POSITION\":\""+wm.getPosition()+"\",\"ISNETWORKING\":\""+wm.getIsnetworking()+"\","
						+ "\"JOINTIME\":\""+wm.getJoinTime()+"\",\"TYPEID\":\""+wm.getTypeId()+"\",\"STATUSID\":\""+wm.getStatusId()+"\","
						+ "\"GATHERID\":\""+gatherid+"\",\"MONEY\":\""+wm.getMoney()+"\"MANUFACTURERID\":\""+wm.getManufacturerId().getId()+"\","
						+ "\"INSFRAMEWORKID\":\""+wm.getInsframeworkId().getId()+"\",\"CREATOR\":\""+myuser.getUsername()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
				Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{obj1,obj2});  
				if(objects[0].toString().equals("true")){
					obj.put("success", true);
				}else{
					obj.put("success", false);
					obj.put("msg", "导入失败！");
					return obj.toString();
				}
			};
			obj.put("success",true);
			obj.put("msg","导入成功！");
		}catch(Exception e){
			e.printStackTrace();
			obj.put("msg","导入失败，请检查您的文件格式以及数据是否符合要求！");
			obj.put("success",false);
		}
		return obj.toString();
	}
	
	/**
	 * 导入维修记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/importMaintain")
	@ResponseBody
	public String importMaintain(HttpServletRequest request,
			HttpServletResponse response){
		UploadUtil u = new UploadUtil();
		JSONObject obj = new JSONObject();
		try{
			String path = u.uploadFile(request, response);
			List<WeldingMaintenance> wt = xlsxMaintain(path);
			//删除已保存的excel文件
			File file  = new File(path);
			file.delete();
			for(int i=0;i<wt.size();i++){
				wt.get(i).getMaintenance().setTypeId(dm.getvaluebyname(5,wt.get(i).getMaintenance().getTypename()));
				BigInteger wmid = wmm.getWeldingMachineByEno(wt.get(i).getWelding().getEquipmentNo());
				wt.get(i).getWelding().setId(wmid);
				if(wt.get(i).getMaintenance().getStartTime()==null){
					wt.get(i).getMaintenance().setStartTime("");
				}
				if(wt.get(i).getMaintenance().getEndTime()==null){
					wt.get(i).getMaintenance().setEndTime("");
				}
				//当前层级
				String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
				//获取当前用户
				Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				MyUser myuser = (MyUser)object;
				//获取项目层url
				String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
				//获取公司发布地址
				BigInteger insfid = mm.getInsfidByMachineid(wt.get(i).getWelding().getId());
				String companyurl = im.webserviceDto(request, insfid);
				//客户端执行操作
				JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
				Client client = dcf.createClient(companyurl);
				iutil.Authority(client);
				if(wt.get(i).getMaintenance().getMoney()<0){
					wt.get(i).getMaintenance().setMoney(0);
				}
				String obj1 = "{\"CLASSNAME\":\"maintainWebServiceImpl\",\"METHOD\":\"addMaintian\"}";
				String obj2 = "{\"VICEMAN\":\""+wt.get(i).getMaintenance().getViceman()+"\",\"INSFID\":\""+insfid+"\",\"STARTTIME\":\""+wt.get(i).getMaintenance().getStartTime()+
						"\",\"ENDTIME\":\""+wt.get(i).getMaintenance().getEndTime()+"\",\"DESC\":\""+wt.get(i).getMaintenance().getDesc()+"\",\"TYPEID\":\""+wt.get(i).getMaintenance().getTypeId()+
						"\",\"WELDID\":\""+wt.get(i).getWelding().getId()+"\",\"MONEY\":\""+wt.get(i).getMaintenance().getMoney()+"\",\"CREATOR\":\""+myuser.getUsername()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
				Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{obj1,obj2}); 
				if(objects[0].toString().equals("true")){
					obj.put("success", true);
				}else if(!objects[0].toString().equals("false")){
					obj.put("success", true);
					obj.put("msg", objects[0].toString());
				}else{
					obj.put("success", false);
					obj.put("errorMsg", "操作失败！");
				}
			};
			obj.put("success",true);
			obj.put("msg","导入成功！");
		}catch(Exception e){
			e.printStackTrace();
			obj.put("success",false);
			obj.put("msg","导入失败，请检查您的文件格式以及数据是否符合要求！");
		}
		return obj.toString();
	}
	
	/**
	 * 导入WeldingMaintenance表数据
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static List<WeldingMaintenance> xlsxMaintain(String path) throws IOException, InvalidFormatException{
		List<WeldingMaintenance> wm = new ArrayList<WeldingMaintenance>();
		InputStream stream = new FileInputStream(path);
		Workbook workbook = create(stream);
		Sheet sheet = workbook.getSheetAt(0);
		
		int rowstart = sheet.getFirstRowNum()+1;
		int rowEnd = sheet.getLastRowNum();
	    
		for(int i=rowstart;i<=rowEnd;i++){
			Row row = sheet.getRow(i);
			if(null == row){
				continue;
			}
			int cellStart = row.getFirstCellNum();
			int cellEnd = row.getLastCellNum();
			WeldingMaintenance dit = new WeldingMaintenance();
			MaintenanceRecord mr = new MaintenanceRecord();
			for(int k = cellStart; k<= cellEnd;k++){
				Cell cell = row.getCell(k);
				if(null == cell){
					continue;
				}
				
				String cellValue = "";
				
				switch (cell.getCellType()){
				case HSSFCell.CELL_TYPE_NUMERIC://数字
					if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
		                SimpleDateFormat sdf = null;  
		                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat  
		                        .getBuiltinFormat("h:mm")) {  
		                    sdf = new SimpleDateFormat("HH:mm");  
		                } else {// 日期  
		                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                }  
		                Date date = cell.getDateCellValue();  
		                cellValue = sdf.format(date);  
		            } else if (cell.getCellStyle().getDataFormat() == 58) {  
		                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
		                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                double value = cell.getNumericCellValue();  
		                Date date = org.apache.poi.ss.usermodel.DateUtil  
		                        .getJavaDate(value);  
		                cellValue = sdf.format(date);  
		            } else {
                        double value = cell.getNumericCellValue();
                        int intValue = (int) value;
                        cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
                    }
					if(k == 2){
						mr.setStartTime(cellValue);//维修起始时间
						break;
					}
					if(k == 3){
						mr.setMoney(Integer.parseInt(cellValue));//维修价格
						break;
					}
					if(k == 4){
						mr.setEndTime(cellValue);//维修结束时间
						break;
	    			}
					break;
				case HSSFCell.CELL_TYPE_STRING://字符串
					cellValue = cell.getStringCellValue();
					if(k == 0){
						WeldingMachine welding = new WeldingMachine();
						welding.setEquipmentNo(cellValue);
						dit.setWelding(welding);//设备编码
						break;
					}
					if(k == 1){
						mr.setViceman(cellValue);//维修人员
						break;
					}
					if(k == 2){
						mr.setStartTime(cellValue);//维修起始时间
						break;
					}
					if(k == 3){
						mr.setMoney(Integer.parseInt(cellValue));//维修价格
						break;
					}
					if(k == 4){
						mr.setEndTime(cellValue);//维修结束时间
						break;
	    			}
					if(k == 5){
						mr.setTypename(cellValue);
						break;
 					}
 					if(k == 6){
 						mr.setDesc(cellValue);//维修说明
						break;
 					}
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
					cellValue = String.valueOf(cell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_FORMULA: // 公式
					cellValue = String.valueOf(cell.getCellFormula());
					break;
				case HSSFCell.CELL_TYPE_BLANK: // 空值
					cellValue = "";
					break;
				case HSSFCell.CELL_TYPE_ERROR: // 故障
					cellValue = "";
					break;
				default:
					cellValue = cell.toString().trim();
					break;
				}
			}
			dit.setMaintenance(mr);
			wm.add(dit);
		}
		
		return wm;
	}
	
	/**
	 * 导入Wedlingmachine表数据
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static List<WeldingMachine> xlsxWm(String path) throws IOException, InvalidFormatException{
		List<WeldingMachine> wm = new ArrayList<WeldingMachine>();
		InputStream stream = new FileInputStream(path);
		Workbook workbook = create(stream);
		Sheet sheet = workbook.getSheetAt(0);
		
		int rowstart = sheet.getFirstRowNum()+1;
		int rowEnd = sheet.getLastRowNum();
	    
		for(int i=rowstart;i<=rowEnd;i++){
			Row row = sheet.getRow(i);
			if(null == row){
				continue;
			}
			int cellStart = row.getFirstCellNum();
			int cellEnd = row.getLastCellNum();
			WeldingMachine dit = new WeldingMachine();
			EquipmentManufacturer manu = new EquipmentManufacturer();
			for(int k = cellStart; k<= cellEnd;k++){
				Cell cell = row.getCell(k);
				if(null == cell){
					continue;
				}
				
				String cellValue = "";
				
				switch (cell.getCellType()){
				case HSSFCell.CELL_TYPE_NUMERIC://数字
					if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
		                SimpleDateFormat sdf = null;  
		                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat  
		                        .getBuiltinFormat("h:mm")) {  
		                    sdf = new SimpleDateFormat("HH:mm");  
		                } else {// 日期  
		                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                }  
		                Date date = cell.getDateCellValue();  
		                cellValue = sdf.format(date);  
		            } else if (cell.getCellStyle().getDataFormat() == 58) {  
		                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
		                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                double value = cell.getNumericCellValue();  
		                Date date = org.apache.poi.ss.usermodel.DateUtil  
		                        .getJavaDate(value);  
		                cellValue = sdf.format(date);  
		            } else {
		            	 //处理数字过长时出现x.xxxE9
		            	 BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
		            	 cellValue = big.toString();
                    }
					if(k == 0){
						dit.setEquipmentNo(cellValue);//设备编码
						break;
					}
					if(k == 2){
						dit.setJoinTime(cellValue);//入厂时间
						break;
					}
					//采集序号机设备序号只能是数字
					if(k == 8){
						Gather g = new Gather();
						g.setGatherNo(cellValue);
						dit.setGatherId(g);//采集序号
						break;
					}
					if(k == 10){
						dit.setMoney(Integer.parseInt(cellValue));//价值
						break;
					}
					break;
				case HSSFCell.CELL_TYPE_STRING://字符串
					cellValue = cell.getStringCellValue();
					if(k == 0){
						dit.setEquipmentNo(cellValue);//设备编码
						break;
					}
					if(k == 1){
						dit.setTypename(cellValue);//设备类型
						break;
					}
					if(k == 2){
						dit.setJoinTime(cellValue);//入厂时间
						break;
					}
					if(k == 3){
 						Insframework ins = new Insframework();
 						ins.setName(cellValue);
 						dit.setInsframeworkId(ins);//所属项目
						break;
	    			}
			        if(k == 4){
			        	dit.setStatusname(cellValue);//状态
						break;
 					}
 					if(k == 5){
 						manu.setName(cellValue);
 						dit.setManufacturerId(manu);//厂家
						break;
 					}
 					if(k == 6){
 						manu.setType(cellValue);
 						dit.setManufacturerId(manu);//厂家类型
						break;
 					}
					if(k == 7){
						if(cellValue.equals("是")){
	 						dit.setIsnetworking(0);//是否在网
						}else{
	 						dit.setIsnetworking(1);
						}
						break;
 					}
					if(k == 9){
						dit.setPosition(cellValue);//位置
						break;
					}
					if(k == 10){
						dit.setMoney(Integer.parseInt(cellValue));//价值
						break;
					}
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
					cellValue = String.valueOf(cell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_FORMULA: // 公式
					cellValue = String.valueOf(cell.getCellFormula());
					break;
				case HSSFCell.CELL_TYPE_BLANK: // 空值
					cellValue = "";
					break;
				case HSSFCell.CELL_TYPE_ERROR: // 故障
					cellValue = "";
					break;
				default:
					cellValue = cell.toString().trim();
					break;
				}
			}
			wm.add(dit);
		}
		
		return wm;
	}
	
	/**
	 * 导入工艺
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/importWps")
	@ResponseBody
	public String importWPS(HttpServletRequest request,
			HttpServletResponse response){
		UploadUtil u = new UploadUtil();
		JSONObject obj = new JSONObject();
		String path = "";
		try{
			path = u.uploadFile(request, response);
			List<Wps> list = xlsxWPS(path);
			//删除已保存的excel文件
			File file  = new File(path);
			file.delete();
			for(Wps wps : list){
				wps.setFitemid(wmm.getInsframeworkByName(wps.getFitemname()));
				//编码唯一
				int count = wpss.getUsernameCount(wps.getFwpsnum());
				if(count>0){
					obj.put("msg","导入失败，请检查您的工艺编号是否已存在！");
					obj.put("success",false);
					return obj.toString();
				}
				//当前层级
				String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
				//获取当前用户
				Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				MyUser myuser = (MyUser)object;
				//获取项目层url
				String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
				//获取公司发布地址
				int type = im.getTypeById(wps.getFitemid());
				String webserviceurl = "",method = "enterTheIDU";
				if(type==20){
					webserviceurl = request.getSession().getServletContext().getInitParameter("blocurl");
					method="enterTheWS";
				}else if(type==21){
					webserviceurl = request.getSession().getServletContext().getInitParameter("companyurl");
				}else if(type==22){
					Insframework ins = im.getParent(wps.getFitemid());
					webserviceurl = request.getSession().getServletContext().getInitParameter(ins.getId().toString());
				}else{
					Insframework ins = im.getParent(wps.getFitemid());
					Insframework companyid = im.getParent(ins.getId());
					webserviceurl = request.getSession().getServletContext().getInitParameter(companyid.getId().toString());
				}
				//客户端执行操作
				JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
				Client client = dcf.createClient(webserviceurl);
				iutil.Authority(client);
				String childrenstr = "[]";
				String obj1 = "{\"CLASSNAME\":\"wpsWebServiceImpl\",\"METHOD\":\"addWps\"}";
				String obj2 = "{\"WPSNUM\":\""+wps.getFwpsnum()+"\",\"VERSIONS\":\""+wps.getFversions()+"\",\"INSFID\":\""+wps.getFitemid()+"\",\"PROJECT_CODE\":\""+wps.getFproject_code()+"\",\"REPORT_NUMBER\":\""+wps.getFreport_number()+"\",\"DEGREE\":\""+
						wps.getFdegree()+"\",\"EVALUATION_STANDARD\":\""+wps.getFevaluation_standard()+"\",\"VALIDITY\":\""+wps.getFvalidity()+"\",\"AUTOMATIC\":\""+wps.getFautomatic()+"\",\"GROOVE_TYPE\":\""+wps.getFgroove_type()+"\""+
						",\"STABILIVOLT_SYSTEM\":\""+wps.getFstabilivolt_system()+"\",\"MATERIALS\":\""+wps.getFmaterials()+"\",\"THICKNESS1\":\""+wps.getFthickness1()+"\",\"THICKNESS2\":\""+wps.getFthickness2()+"\""+
						",\"DIAMETER\":\""+wps.getFdiameter()+"\",\"ELES1\":\""+wps.getFeles1()+"\",\"IMAGES_URL\":\""+request.getParameter("imageurl")+"\",\"IMAGES_DESC\":\""+wps.getFimages_desc()+"\",\"CATEGORY\":\""+wps.getFcategory()+"\""+
						",\"SHOP_SIGN\":\""+wps.getFshop_sign()+"\",\"SPECIFICATION\":\""+wps.getFspecification()+"\",\"materials_type\":\""+wps.getFmaterials_type()+"\",\"MATERIALS_NUMBER1\":\""+wps.getFmaterials_number1()+"\""+
						",\"MATERIALS_SPECIFICATION1\":\""+wps.getFmaterials_specification1()+"\",\"MATERIALS_NUMBER2\":\""+wps.getFmaterials_number2()+"\",\"MATERIALS_SPECIFICATION2\":\""+wps.getFmaterials_specification2()+"\",\"SOLDERING_NUMBER\":\""+wps.getFsoldering_number()+"\""+
						",\"POSITION\":\""+wps.getFposition()+"\",\"DIRECTION\":\""+wps.getFdirection()+"\",\"ELSE2\":\""+wps.getFelse2()+"\",\"FRONT1\":\""+wps.getFfront1()+"\",\"FRONT2\":\""+wps.getFfront2()+"\""+
						",\"REVERSE1\":\""+wps.getFreverse1()+"\",\"REVERSE2\":\""+wps.getFreverse2()+"\",\"REVERSE3\":\""+wps.getFreverse3()+"\",\"TAIL\":\""+wps.getFtail()+"\",\"PREHEATING_TEMPERATURE\":\""+wps.getFpreheating_temperature()+"\""+
						",\"TEMPERATURE\":\""+wps.getFtemperature()+"\",\"PREHEAT_WAY\":\""+wps.getfPreheat_way()+"\",\"TEMPERATURE_RANGE\":\""+wps.getFtemperature_range()+"\",\"SOAKING_TIME\":\""+wps.getFsoaking_time()+"\",\"ELES3\":\""+wps.getFeles3()+"\""+
						",\"SCOPE\":\""+wps.getFscope()+"\",\"NOZZLE\":\""+wps.getFnozzle()+"\",\"DISTANCE\":\""+wps.getFdistance()+"\",\"BACK_CHIPPING\":\""+wps.getFback_chipping()+"\",\"LAYER_SCOPE1\":\""+wps.getFlayer_scope1()+"\""+
						",\"LAYER_SCOPE2\":\""+wps.getFlayer_scope2()+"\",\"TUNGSTEN_ELECTRODE\":\""+wps.getFtungsten_electrode()+"\",\"TRANSIENT_MODE\":\""+wps.getFtransient_mode()+"\",\"METHOD1\":\""+wps.getFmethod1()+"\",\"METHOD2\":\""+wps.getFmethod2()+"\""+
						",\"CREATOR\":\""+myuser.getId()+"\",\"WELDWPS\":"+childrenstr+",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\",\"INSFTYPE\":\""+type+"\"}";
				Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", method), new Object[]{obj1,obj2});  
				if(objects[0].toString().equals("true")){
					obj.put("success", true);
				}else if(!objects[0].toString().equals("false")){
					obj.put("success", true);
					obj.put("msg", objects[0].toString());
				}else{
					obj.put("success", false);
					obj.put("msg", "导入失败！");
				}
			};
			obj.put("success",true);
			obj.put("msg","导入成功！");
		}catch(Exception e){
			e.printStackTrace();
			obj.put("msg","导入失败，请检查您的文件格式以及数据是否符合要求！");
			obj.put("success",false);
		}
		return obj.toString();
	}
	
	
	/**
	 * 导入工艺焊接参数
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/importChildrenWPS")
	@ResponseBody
	public String importChildrenWPS(HttpServletRequest request,
			HttpServletResponse response){
		UploadUtil u = new UploadUtil();
		JSONObject obj = new JSONObject();
		String path = "";
		try{
			path = u.uploadFile(request, response);
			List<Wps> list = xlsxChiledremWps(path);
			//删除已保存的excel文件
			File file  = new File(path);
			file.delete();
			for(Wps wps : list){
				if(!iutil.isNull(wps.getFwpsnum())){
					return obj.toString();
				}
				wps.setFitemid(wmm.getInsframeworkByName(wps.getFitemname()));
				int count = wpss.getUsernameCount(wps.getFwpsnum());
				if(count<=0){
					obj.put("msg","导入失败，请检查您的工艺编号是否存在！");
					obj.put("success",false);
					return obj.toString();
				}
				if(wps.getFback().equals("打底")){
					wps.setFweld_prechannel(1);
				}else if(wps.getFback().equals("填充")){
					wps.setFweld_prechannel(2);
				}else if(wps.getFback().equals("盖面")){
					wps.setFweld_prechannel(3);
				}else if(wps.getFback().equals("背面封底")){
					wps.setFweld_prechannel(4);
				}else{
					wps.setFweld_prechannel(5);
				}
				//当前层级
				String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
				//获取当前用户
				Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				MyUser myuser = (MyUser)object;
				//获取项目层url
				String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
				//获取公司发布地址
				int type = im.getTypeById(wps.getFitemid());
				String webserviceurl = "",method = "enterTheIDU";
				if(type==20){
					webserviceurl = request.getSession().getServletContext().getInitParameter("blocurl");
					method="enterTheWS";
				}else if(type==21){
					webserviceurl = request.getSession().getServletContext().getInitParameter("companyurl");
				}else if(type==22){
					Insframework ins = im.getParent(wps.getFitemid());
					webserviceurl = request.getSession().getServletContext().getInitParameter(ins.getId().toString());
				}else{
					Insframework ins = im.getParent(wps.getFitemid());
					Insframework companyid = im.getParent(ins.getId());
					webserviceurl = request.getSession().getServletContext().getInitParameter(companyid.getId().toString());
				}
				//客户端执行操作
				JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
				Client client = dcf.createClient(webserviceurl);
				iutil.Authority(client);
				String obj1 = "{\"CLASSNAME\":\"wpsWebServiceImpl\",\"METHOD\":\"saveChildrenWPS\"}";
				String obj2 = "{\"WPSNUM\":\""+wps.getFwpsnum()+"\",\"WELDPRECHANNEL\":\""+wps.getFweld_prechannel()+"\",\"INSFID\":\""+wps.getFitemid()+"\",\"WELDINGMETHOD\":\""+wps.getFwelding_method()+"\",\"TYPE\":\""+wps.getFtype()+"\",\"SPECIFICATION\":\""+
						wps.getFchildren_specification()+"\",\"POLARITY\":\""+wps.getFpolarity()+"\",\"MINELECTRICITY\":\""+wps.getFweld_i_min()+"\",\"MAXELECTRICITY\":\""+wps.getFweld_i_max()+"\",\"MINVALTAGE\":\""+wps.getFweld_v_min()+"\""+
						",\"MAXVALTAGE\":\""+wps.getFweld_v_max()+"\",\"WELDINGSPEED\":\""+wps.getFwelding_speed()+"\",\"CREATOR\":\""+myuser.getId()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\",\"INSFTYPE\":\""+type+"\"}";
				Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", method), new Object[]{obj1,obj2});  
				if(objects[0].toString().equals("true")){
					obj.put("success", true);
				}else if(!objects[0].toString().equals("false")){
					obj.put("success", true);
					obj.put("msg", objects[0].toString());
				}else{
					obj.put("success", false);
					obj.put("msg", "操作失败！");
				}
			};
			obj.put("success",true);
			obj.put("msg","导入成功！");
		}catch(Exception e){
			e.printStackTrace();
			obj.put("msg","导入失败，请检查您的文件格式以及数据是否符合要求！");
			obj.put("success",false);
		}
		return obj.toString();
	}
	
	/**
	 * 导入WPS表数据
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static List<Wps> xlsxWPS(String path) throws IOException, InvalidFormatException{
		List<Wps> wps = new ArrayList<Wps>();
		InputStream stream = new FileInputStream(path);
		Workbook workbook = create(stream);
		Sheet sheet = workbook.getSheetAt(0);
		
		int rowstart = sheet.getFirstRowNum()+1;
		int rowEnd = sheet.getLastRowNum();
	    
		for(int i=rowstart;i<=rowEnd;i++){
			Row row = sheet.getRow(i);
			if(null == row){
				continue;
			}
			int cellStart = row.getFirstCellNum();
			int cellEnd = row.getLastCellNum();
			Wps w = new Wps();
			for(int k = cellStart; k<= cellEnd;k++){
				Cell cell = row.getCell(k);
				if(null == cell){
					continue;
				}
				
				String cellValue = "";
				
				switch (cell.getCellType()){
				case HSSFCell.CELL_TYPE_NUMERIC://数字
					if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
		                SimpleDateFormat sdf = null;  
		                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat  
		                        .getBuiltinFormat("h:mm")) {  
		                    sdf = new SimpleDateFormat("HH:mm");  
		                } else {// 日期  
		                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                }  
		                Date date = cell.getDateCellValue();  
		                cellValue = sdf.format(date);  
		            } else if (cell.getCellStyle().getDataFormat() == 58) {  
		                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
		                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                double value = cell.getNumericCellValue();  
		                Date date = org.apache.poi.ss.usermodel.DateUtil  
		                        .getJavaDate(value);  
		                cellValue = sdf.format(date);  
		            } else {
		            	 //处理数字过长时出现x.xxxE9
		            	 BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
		            	 cellValue = big.toString();
                    }
					if(k == 0){
						w.setFwpsnum(cellValue);
						break;
					}
					if(k == 1){
						w.setFversions(Integer.parseInt(cellValue));
						break;
					}
					if(k == 11){
						w.setFthickness1(cellValue);
						break;
					}
					if(k == 12){
						w.setFthickness2(cellValue);
						break;
					}
					break;
				case HSSFCell.CELL_TYPE_STRING://字符串
					cellValue = cell.getStringCellValue();
					if(k == 0){
						w.setFwpsnum(cellValue);
						break;
					}
					if(k == 1){
						w.setFversions(Integer.parseInt(cellValue));
						break;
					}
					if(k == 2){
						w.setFproject_code(cellValue);
						break;
					}
					if(k == 3){
						w.setFreport_number(cellValue);
						break;
					}
					if(k == 3){
						w.setFreport_number(cellValue);
						break;
					}
					if(k == 4){
						w.setFdegree(cellValue);
						break;
					}
					if(k == 5){
						w.setFevaluation_standard(cellValue);
						break;
					}
					if(k == 6){
						w.setFstabilivolt_system(cellValue);
						break;
					}
					if(k == 7){
						w.setFvalidity(cellValue);
						break;
					}
					if(k == 8){
						w.setFautomatic(cellValue);
						break;
					}
					if(k == 9){
						w.setFgroove_type(cellValue);
						break;
					}
					if(k == 10){
						w.setFmaterials(cellValue);
						break;
					}
					if(k == 11){
						w.setFthickness1(cellValue);
						break;
					}
					if(k == 12){
						w.setFthickness2(cellValue);
						break;
					}
					if(k == 13){
						w.setFdiameter(cellValue);
						break;
					}
					if(k == 14){
						w.setFeles1(cellValue);
						break;
					}
					if(k == 15){
						w.setFcategory(cellValue);
						break;
					}
					if(k == 16){
						w.setFshop_sign(cellValue);
						break;
					}
					if(k == 17){
						w.setFspecification(cellValue);
						break;
					}
					if(k == 18){
						w.setFmaterials_type(cellValue);
						break;
					}
					if(k == 19){
						w.setFmaterials_number1(cellValue);
						break;
					}
					if(k == 20){
						w.setFmaterials_specification1(cellValue);
						break;
					}
					if(k == 21){
						w.setFmaterials_number2(cellValue);
						break;
					}
					if(k == 22){
						w.setFmaterials_specification2(cellValue);
						break;
					}
					if(k == 23){
						w.setFsoldering_number(cellValue);
						break;
					}
					if(k == 24){
						w.setFposition(cellValue);
						break;
					}
					if(k == 25){
						w.setFdirection(cellValue);
						break;
					}
					if(k == 26){
						w.setFelse2(cellValue);
						break;
					}
					if(k == 27){
						w.setFfront1(cellValue);
						break;
					}
					if(k == 28){
						w.setFfront2(cellValue);
						break;
					}
					if(k == 29){
						w.setFreverse1(cellValue);
						break;
					}
					if(k == 30){
						w.setFreverse2(cellValue);
						break;
					}
					if(k == 31){
						w.setFreverse3(cellValue);
						break;
					}
					if(k == 32){
						w.setFtail(cellValue);
						break;
					}
					if(k == 33){
						w.setFpreheating_temperature(cellValue);
						break;
					}
					if(k == 34){
						w.setFtemperature(cellValue);
						break;
					}
					if(k == 35){
						w.setfPreheat_way(cellValue);
						break;
					}
					if(k == 36){
						w.setFtemperature_range(cellValue);
						break;
					}
					if(k == 37){
						w.setFsoaking_time(cellValue);
						break;
					}
					if(k == 38){
						w.setFeles3(cellValue);
						break;
					}
					if(k == 39){
						w.setFscope(cellValue);
						break;
					}
					if(k == 40){
						w.setFnozzle(cellValue);
						break;
					}
					if(k == 41){
						w.setFdistance(cellValue);
						break;
					}
					if(k == 42){
						w.setFback_chipping(cellValue);
						break;
					}
					if(k == 43){
						w.setFlayer_scope1(cellValue);
						break;
					}
					if(k == 44){
						w.setFlayer_scope2(cellValue);
						break;
					}
					if(k == 45){
						w.setFtungsten_electrode(cellValue);
						break;
					}
					if(k == 46){
						w.setFtransient_mode(cellValue);
						break;
					}
					if(k == 47){
						w.setFmethod1(cellValue);
						break;
					}
					if(k == 48){
						w.setFmethod2(cellValue);
						break;
					}
					if(k == 49){
						w.setFitemname(cellValue);
						break;
					}
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
					cellValue = String.valueOf(cell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_FORMULA: // 公式
					cellValue = String.valueOf(cell.getCellFormula());
					break;
				case HSSFCell.CELL_TYPE_BLANK: // 空值
					cellValue = "";
					break;
				case HSSFCell.CELL_TYPE_ERROR: // 故障
					cellValue = "";
					break;
				default:
					cellValue = cell.toString().trim();
					break;
				}
			}
			wps.add(w);
		}
		
		return wps;
	}
	
	/**
	 * 导入Wps焊接参数表数据
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static List<Wps> xlsxChiledremWps(String path) throws IOException, InvalidFormatException{
		List<Wps> wps = new ArrayList<Wps>();
		InputStream stream = new FileInputStream(path);
		Workbook workbook = create(stream);
		Sheet sheet = workbook.getSheetAt(0);
		
		int rowstart = sheet.getFirstRowNum()+1;
		int rowEnd = sheet.getLastRowNum();
	    
		for(int i=rowstart;i<=rowEnd;i++){
			Row row = sheet.getRow(i);
			if(null == row){
				continue;
			}
			int cellStart = row.getFirstCellNum();
			int cellEnd = row.getLastCellNum();
			Wps w = new Wps();
			for(int k = cellStart; k<= cellEnd;k++){
				Cell cell = row.getCell(k);
				if(null == cell){
					continue;
				}
				
				String cellValue = "";
				
				switch (cell.getCellType()){
				case HSSFCell.CELL_TYPE_NUMERIC://数字
					if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
		                SimpleDateFormat sdf = null;  
		                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat  
		                        .getBuiltinFormat("h:mm")) {  
		                    sdf = new SimpleDateFormat("HH:mm");  
		                } else {// 日期  
		                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                }  
		                Date date = cell.getDateCellValue();  
		                cellValue = sdf.format(date);  
		            } else if (cell.getCellStyle().getDataFormat() == 58) {  
		                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
		                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                double value = cell.getNumericCellValue();  
		                Date date = org.apache.poi.ss.usermodel.DateUtil  
		                        .getJavaDate(value);  
		                cellValue = sdf.format(date);  
		            } else {
		            	 //处理数字过长时出现x.xxxE9
		            	 BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
		            	 cellValue = big.toString();
                    }
					if(k == 0){
						w.setFwpsnum(cellValue);
						break;
					}
					if(k == 6){
						w.setFweld_i_min(Integer.parseInt(cellValue));
						break;
					}
					if(k == 7){
						w.setFweld_i_max(Integer.parseInt(cellValue));
						break;
					}
					if(k == 8){
						w.setFweld_v_min(Integer.parseInt(cellValue));
						break;
					}
					if(k == 9){
						w.setFweld_v_max(Integer.parseInt(cellValue));
						break;
					}
					break;
				case HSSFCell.CELL_TYPE_STRING://字符串
					cellValue = cell.getStringCellValue();
					if(k == 0){
						w.setFwpsnum(cellValue);
						break;
					}
					if(k == 1){
						w.setFback(cellValue);
						break;
					}
					if(k == 2){
						w.setFwelding_method(cellValue);
						break;
					}
					if(k == 3){
						w.setFtype(cellValue);
						break;
					}
					if(k == 4){
						w.setFchildren_specification(cellValue);
						break;
					}
					if(k == 5){
						w.setFpolarity(cellValue);
						break;
					}
					if(k == 6){
						w.setFweld_i_min(Integer.parseInt(cellValue));
						break;
					}
					if(k == 7){
						w.setFweld_i_max(Integer.parseInt(cellValue));
						break;
					}
					if(k == 8){
						w.setFweld_v_min(Integer.parseInt(cellValue));
						break;
					}
					if(k == 9){
						w.setFweld_v_max(Integer.parseInt(cellValue));
						break;
					}
					if(k == 10){
						w.setFwelding_speed(cellValue);
						break;
					}
					if(k == 11){
						w.setFitemname(cellValue);
						break;
					}
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
					cellValue = String.valueOf(cell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_FORMULA: // 公式
					cellValue = String.valueOf(cell.getCellFormula());
					break;
				case HSSFCell.CELL_TYPE_BLANK: // 空值
					cellValue = "";
					break;
				case HSSFCell.CELL_TYPE_ERROR: // 故障
					cellValue = "";
					break;
				default:
					cellValue = cell.toString().trim();
					break;
				}
			}
			wps.add(w);
		}
		
		return wps;
	}
	
	
	public static Workbook create(InputStream in) throws IOException,InvalidFormatException {
		if (!in.markSupported()) {
            in = new PushbackInputStream(in, 8);
        }
        if (POIFSFileSystem.hasPOIFSHeader(in)) {
            return new HSSFWorkbook(in);
        }
        if (POIXMLDocument.hasOOXMLHeader(in)) {
            return new XSSFWorkbook(OPCPackage.open(in));
        }
        throw new IllegalArgumentException("你的excel版本目前poi解析不了");
    }
}
