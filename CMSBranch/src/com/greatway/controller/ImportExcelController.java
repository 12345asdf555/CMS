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
import com.greatway.model.EquipmentManufacturer;
import com.greatway.model.Gather;
import com.greatway.model.Insframework;
import com.greatway.model.MaintenanceRecord;
import com.greatway.model.WeldingMachine;
import com.greatway.model.WeldingMaintenance;
import com.greatway.util.IsnullUtil;
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
				String gatherid = "";
				int count2 = 0;
				if(gather!=null){
					count2 = wmm.getGatheridCount(wm.getInsframeworkId().getId(),gather.getGatherNo());
					gatherid = gm.getGatherByNo(gather.getGatherNo()).toString();
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
				String obj1 = "{\"CLASSNAME\":\"weldingMachineWebServiceImpl\",\"METHOD\":\"addWeldingMachine\"}";
				String obj2 = "{\"EQUIPMENTNO\":\""+wm.getEquipmentNo()+"\",\"POSITION\":\""+wm.getPosition()+"\",\"ISNETWORKING\":\""+wm.getIsnetworking()+"\","
						+ "\"JOINTIME\":\""+wm.getJoinTime()+"\",\"TYPEID\":\""+wm.getTypeId()+"\",\"STATUSID\":\""+wm.getStatusId()+"\","
						+ "\"GATHERID\":\""+gatherid+"\",\"MANUFACTURERID\":\""+wm.getManufacturerId().getId()+"\","
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
				String obj1 = "{\"CLASSNAME\":\"maintainWebServiceImpl\",\"METHOD\":\"addMaintian\"}";
				String obj2 = "{\"VICEMAN\":\""+wt.get(i).getMaintenance().getViceman()+"\",\"INSFID\":\""+insfid+"\",\"STARTTIME\":\""+wt.get(i).getMaintenance().getStartTime()+
						"\",\"ENDTIME\":\""+wt.get(i).getMaintenance().getEndTime()+"\",\"DESC\":\""+wt.get(i).getMaintenance().getDesc()+"\",\"TYPEID\":\""+wt.get(i).getMaintenance().getTypeId()+
						"\",\"WELDID\":\""+wt.get(i).getWelding().getId()+"\",\"CREATOR\":\""+myuser.getUsername()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
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
						mr.setEndTime(cellValue);//维修结束时间
						break;
	    			}
					if(k == 4){
						mr.setTypename(cellValue);
						break;
 					}
 					if(k == 5){
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
