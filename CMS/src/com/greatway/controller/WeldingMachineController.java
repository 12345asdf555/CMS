package com.greatway.controller;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.greatway.manager.DictionaryManager;
import com.greatway.manager.GatherManager;
import com.greatway.manager.InsframeworkManager;
import com.greatway.manager.WeldingMachineManager;
import com.greatway.model.Dictionarys;
import com.greatway.model.EquipmentManufacturer;
import com.greatway.model.Gather;
import com.greatway.model.Insframework;
import com.greatway.model.WeldingMachine;
import com.greatway.page.Page;
import com.greatway.util.IsnullUtil;
import com.spring.model.MyUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/weldingMachine", produces = { "text/json;charset=UTF-8" })
public class WeldingMachineController {
	private Page page;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int total = 0;
	private String primaryKey = "";
	
	@Autowired
	private WeldingMachineManager wmm;
	
	@Autowired
	private InsframeworkManager im;
	
	@Autowired
	private GatherManager gm;
	
	@Autowired
	private DictionaryManager dm;
	
	IsnullUtil iutil = new IsnullUtil();
	
	/**
	 * 焊机设备管理
	 * @return
	 */
	@RequestMapping("/goWeldingMachine")
	public String goWeldingMahine(){
		return "weldingMachine/weldingmachine";
	}
	
	/**
	 * 焊机设备迁移
	 * @return
	 */
	@RequestMapping("/goMachineMigrate")
	public String goMachineMigrate(){
		return "weldingMachine/machinemigrate";
	}
	
	/**
	 * 维修记录
	 * @param request
	 * @param wid
	 * @return
	 */
	@RequestMapping("/goMaintain")
	public String goMaintain(HttpServletRequest request, @RequestParam String wid){
		WeldingMachine weld = wmm.getWeldingMachineById(new BigInteger(wid));
		if(weld.getIsnetworking()==0){
			request.setAttribute("isnetworking", "是");
		}else{
			request.setAttribute("isnetworking", "否");
		}
		request.setAttribute("w", weld);
		return "maintain/weldingmaintenance";
	}
	
	/**
	 * 新增焊机设备
	 * @return
	 */
	@RequestMapping("/goAddWeldingMachine")
	public String goAddWeldingMachine(){
		return "weldingMachine/addweldingmachine";
	}
	
	/**
	 * 修改焊机设备
	 * @param request
	 * @param wid
	 * @return
	 */
	@RequestMapping("/goEditWeldingMachine")
	public String goEditWeldingMachine(HttpServletRequest request, @RequestParam String wid){
		WeldingMachine weld = wmm.getWeldingMachineById(new BigInteger(wid));
		request.setAttribute("w", weld);
		return "weldingMachine/editweldingmachine";
	}
	
	/**
	 * 删除焊机信息
	 * @param request
	 * @param wid
	 * @return
	 */
	@RequestMapping("/goremoveWeldingMachine")
	public String goremoveWeldingMahine(HttpServletRequest request, @RequestParam String wid,@RequestParam String insfid){
		WeldingMachine weld = wmm.getWeldingMachineById(new BigInteger(wid));
		if(weld.getIsnetworking()==0){
			request.setAttribute("isnetworking", "是");
		}else{
			request.setAttribute("isnetworking", "否");
		}
		request.setAttribute("w", weld);
		request.setAttribute("insfid", insfid);
		return "weldingMachine/removeweldingmachine";
	}
	
	/**
	 * 显示焊机列表
	 * @return
	 */
	@RequestMapping("/getWedlingMachineList")
	@ResponseBody
	public String getWeldingMachine(HttpServletRequest request){
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String searchStr = request.getParameter("searchStr");
		String parentId = request.getParameter("parent");
		BigInteger parent = null;
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}else{
			MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
				    .getAuthentication()  
				    .getPrincipal();
			long uid = myuser.getId();
			parent = im.getUserInsfId(BigInteger.valueOf(uid));
		}
		request.getSession().setAttribute("searchStr", searchStr);
		page = new Page(pageIndex,pageSize,total);
		List<WeldingMachine> list = wmm.getWeldingMachineAll(page,parent,searchStr);
		long total = 0;
		
		if(list != null){
			PageInfo<WeldingMachine> pageinfo = new PageInfo<WeldingMachine>(list);
			total = pageinfo.getTotal();
		}
		
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			for(WeldingMachine wm:list){
				json.put("id", wm.getId());
				json.put("equipmentNo", wm.getEquipmentNo());
				json.put("position", wm.getPosition());
				if(wm.getIsnetworking()==0){
					json.put("isnetworking", "是");
				}else{
					json.put("isnetworking", "否");
				}
				json.put("isnetworkingId", wm.getIsnetworking());
				json.put("jointime", wm.getJoinTime());
				json.put("money", wm.getMoney());
				json.put("typeName",wm.getTypename());
				json.put("typeId", wm.getTypeId());
				json.put("statusName", wm.getStatusname());
				json.put("statusId", wm.getStatusId());
				json.put("insframeworkName", wm.getInsframeworkId().getName());
				json.put("insframeworkId", wm.getInsframeworkId().getId());
				json.put("manufacturerName", wm.getManufacturerId().getName()+" - "+wm.getManufacturerId().getType());
				json.put("manufacturerId", wm.getManufacturerId().getId());
				if(wm.getGatherId()!=null ||("").equals(wm.getGatherId())){
					json.put("gatherNo", wm.getGatherId().getGatherNo());
					json.put("gatherId", wm.getGatherId().getId());
				}else{
					json.put("gatherNo", null);
					json.put("gatherId", null);
				}
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}
	
	/**
	 * 获取设备类型
	 * @return
	 */
	@RequestMapping("/getTypeAll")
	@ResponseBody
	public String getTypeAll(){
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			List<Dictionarys> dictionary = dm.getDictionaryValue(4);
			for(Dictionarys d:dictionary){
				json.put("id", d.getValue());
				json.put("name", d.getValueName());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("ary", ary);
		return obj.toString();
	}
	
	/**
	 * 获取项目类型
	 * @return
	 */
	@RequestMapping("/getInsframeworkAll")
	@ResponseBody
	public String getInsframeworkAll(){
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			//获取用户id
			Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyUser myuser = (MyUser)object;
			List<Insframework> instype = im.getInsByUserid(new BigInteger(myuser.getId()+""));
			List<Insframework> list = null;
			for(Insframework i:instype){
				if(i.getType()==20){
					list = im.getWeldingMachineInsf(null);
				}else{
					list = im.getWeldingMachineInsf(i.getId());
				}
			}
			for(Insframework i:list){
				json.put("id", i.getId());
				json.put("name", i.getName());
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("ary", ary);
		return obj.toString();
	}
	
	/**
	 * 获取采集编号
	 * @return
	 */
	@RequestMapping("/getGatherAll")
	@ResponseBody
	public String getGatherAll(HttpServletRequest request){
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		String itemid = request.getParameter("itemid");
		BigInteger item = null;
		if(iutil.isNull(itemid)){
			item = new BigInteger(itemid);
		}
		try{
			List<Gather> list = gm.getGatherAll(null,item);
			for(Gather g:list){
				json.put("id", g.getId());
				json.put("name", g.getGatherNo());
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("ary", ary);
		return obj.toString();
	}
	
	/**
	 * 获取焊机状态
	 * @return
	 */
	@RequestMapping("/getStatusAll")
	@ResponseBody
	public String getStatusAll(){
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			List<Dictionarys> dictionary = dm.getDictionaryValue(3);
			for(Dictionarys d:dictionary){
				json.put("id", d.getValue());
				json.put("name", d.getValueName());
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("ary", ary);
		return obj.toString();
	}
	
	/**
	 * 获取厂商
	 * @return
	 */
	@RequestMapping("/getManuAll")
	@ResponseBody
	public String getManuAll(){
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			List<EquipmentManufacturer> list = wmm.getManuAll();
			for(EquipmentManufacturer es:list){
				json.put("id", es.getId());
				json.put("name", es.getName());
				json.put("type", es.getType());
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("ary", ary);
		return obj.toString();
	}
	
	/**
	 * 新增
	 * @return
	 */
	@RequestMapping("/addWeldingMachine")
	@ResponseBody
	public String addWeldingMachine(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		try{
			//当前层级
			String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
			//获取当前用户
			Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyUser myuser = (MyUser)object;
			//获取项目层url
			String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
			//获取公司发布地址
			String companyurl = im.webserviceDto(request, new BigInteger(request.getParameter("iId")));
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(companyurl);
			iutil.Authority(client);
			int money = 0;
			if(iutil.isNull(request.getParameter("money"))){
				money = Integer.parseInt(request.getParameter("money"));
			}
			String obj1 = "{\"CLASSNAME\":\"weldingMachineWebServiceImpl\",\"METHOD\":\"addWeldingMachine\"}";
			String obj2 = "{\"EQUIPMENTNO\":\""+request.getParameter("equipmentNo")+"\",\"POSITION\":\""+request.getParameter("position")+"\",\"ISNETWORKING\":\""+request.getParameter("isnetworking")+"\","
					+ "\"JOINTIME\":\""+request.getParameter("joinTime")+"\",\"TYPEID\":\""+request.getParameter("tId")+"\",\"STATUSID\":\""+request.getParameter("sId")+"\","
					+ "\"GATHERID\":\""+request.getParameter("gatherId")+"\",\"MONEY\":\""+money+"\",\"MANUFACTURERID\":\""+request.getParameter("manuno")+"\","
					+ "\"INSFRAMEWORKID\":\""+request.getParameter("iId")+"\",\"CREATOR\":\""+myuser.getId()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
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
		}catch(Exception e){
			obj.put("success", false);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
	}
	
	@RequestMapping("/editWeldingMachine")
	@ResponseBody
	public String editWeldingMachine(HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		try {
			// 当前层级
			String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
			// 获取当前用户
			Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyUser myuser = (MyUser) object;
			// 获取项目层url
			String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
			// 获取公司发布地址
			String companyurl = im.webserviceDto(request, new BigInteger(request.getParameter("iId")));
			// 客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(companyurl);
			iutil.Authority(client);
			int money = 0;
			if (iutil.isNull(request.getParameter("money"))) {
				money = Integer.parseInt(request.getParameter("money"));
			}
			String obj1 = "{\"CLASSNAME\":\"weldingMachineWebServiceImpl\",\"METHOD\":\"editWeldingMachine\"}";
			String obj2 = "{\"ID\":\"" + request.getParameter("wid") + "\",\"EQUIPMENTNO\":\""
					+ request.getParameter("equipmentNo") + "\",\"POSITION\":\"" + request.getParameter("position")
					+ "\"," + "\"ISNETWORKING\":\"" + request.getParameter("isnetworking") + "\",\"JOINTIME\":\""
					+ request.getParameter("joinTime") + "\",\"TYPEID\":\"" + request.getParameter("tId") + "\""
					+ ",\"STATUSID\":\"" + request.getParameter("sId") + "\",\"GATHERID\":\""
					+ request.getParameter("gatherId") + "\",\"MONEY\":\"" + money + "\",\"MANUFACTURERID\":\""
					+ request.getParameter("manuno") + "\"," + "\"INSFRAMEWORKID\":\"" + request.getParameter("iId")
					+ "\",\"MODIFIER\":\"" + myuser.getId() + "\",\"ITEMURL\":\"" + itemurl + "\",\"HIERARCHY\":\""
					+ hierarchy + "\"}";
			Object[] objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"),
					new Object[] { obj1, obj2 });
			if (objects[0].toString().equals("true")) {
				obj.put("success", true);
			} else if (!objects[0].toString().equals("false")) {
				obj.put("success", true);
				obj.put("msg", objects[0].toString());
			} else {
				obj.put("success", false);
				obj.put("errorMsg", "操作失败！ ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("success", false);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
	}
	
	/**
	 * 迁移
	 * @return
	 */
	@RequestMapping("/migrateWeldingMachine")
	@ResponseBody
	public String migrateWeldingMachine(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		try{
			//当前层级
			String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
			//获取当前用户
			Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MyUser myuser = (MyUser)object;
			//获取项目层url
			String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
			//获取公司发布地址
			String companyurl = im.webserviceDto(request, new BigInteger(request.getParameter("insframework")));
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(companyurl);
			iutil.Authority(client);
			int money = 0;
			if(iutil.isNull(request.getParameter("money"))){
				money = Integer.parseInt(request.getParameter("money"));
			}
			String obj2 = "{\"ID\":\""+request.getParameter("wid")+"\",\"EQUIPMENTNO\":\""+request.getParameter("equipmentNo")+"\",\"POSITION\":\""+request.getParameter("position")+"\","
					+ "\"ISNETWORKING\":\""+request.getParameter("isnetworkingId")+"\",\"JOINTIME\":\""+request.getParameter("jointime")+"\",\"TYPEID\":\""+request.getParameter("typeId")+"\""
					+ ",\"STATUSID\":\""+request.getParameter("statusId")+"\",\"GATHERID\":\""+request.getParameter("gatherId")+"\",\"MONEY\":\""+money+"\",\"MANUFACTURERID\":\""+request.getParameter("manufacturerId")+"\","
					+ "\"INSFRAMEWORKID\":\""+request.getParameter("insframework")+"\",\"MODIFIER\":\""+myuser.getId()+"\",\"CREATOR\":\""+myuser.getId()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
			
			if(request.getParameter("insframework").equals(request.getParameter("insframeworkId"))){
				obj.put("success", true);
				//归属未发生改变，正常执行操作editWeldingMachine (由于该页面只有归属可以编辑故归属未发生变化时无需操作)
				/*String obj1 = "{\"CLASSNAME\":\"weldingMachineWebServiceImpl\",\"METHOD\":\"editWeldingMachine\"}";
				Object[] editobj = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{obj1, obj2});
				if(editobj[0].toString().equals("true")){
					obj.put("success", true);
				}else if(!editobj[0].toString().equals("false")){
					obj.put("success", true);
					obj.put("msg", editobj[0].toString());
				}else{
					obj.put("success", false);
					obj.put("errorMsg", "操作失败！");
				}*/
			}else{
				//1.归属发生改变
				int flag = checkGather(request, client, myuser,hierarchy);
				obj2 = "{\"ID\":\""+request.getParameter("wid")+"\",\"EQUIPMENTNO\":\""+request.getParameter("equipmentNo")+"\",\"POSITION\":\""+request.getParameter("position")+"\","
						+ "\"ISNETWORKING\":\""+request.getParameter("isnetworkingId")+"\",\"JOINTIME\":\""+request.getParameter("jointime")+"\",\"TYPEID\":\""+request.getParameter("typeId")+"\""
						+ ",\"STATUSID\":\""+request.getParameter("statusId")+"\",\"GATHERID\":\""+primaryKey+"\",\"MONEY\":\""+money+"\",\"MANUFACTURERID\":\""+request.getParameter("manufacturerId")+"\","
						+ "\"INSFRAMEWORKID\":\""+request.getParameter("insframework")+"\",\"MODIFIER\":\""+myuser.getId()+"\",\"CREATOR\":\""+myuser.getId()+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
				
				if(flag == 0){
					//1_1.判断新项目部焊机是否存在
					String countobj1 = "{\"CLASSNAME\":\"weldingMachineWebServiceImpl\",\"METHOD\":\"getMachineCountToItem\"}";
					String countobj2 = "{\"ENO\":\""+request.getParameter("equipmentNo")+"\",\"INSFID\":\""+request.getParameter("insframework")+"\"}";
					Object[] countobj = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{countobj1, countobj2});
					Object[] objects = null;
					if(Integer.parseInt(countobj[0].toString())>0){//已存在，修改目标项目部焊机状态为启用
						String existsobj1 = "{\"CLASSNAME\":\"weldingMachineWebServiceImpl\",\"METHOD\":\"editMachineToItem\"}";
						String existsobj2 = "{\"ID\":\""+request.getParameter("wid")+"\",\"EQUIPMENTNO\":\""+request.getParameter("equipmentNo")+"\",\"POSITION\":\""+request.getParameter("position")+"\","
								+ "\"ISNETWORKING\":\""+request.getParameter("isnetworkingId")+"\",\"JOINTIME\":\""+request.getParameter("jointime")+"\",\"TYPEID\":\""+request.getParameter("typeId")+"\""
								+ ",\"STATUSID\":\"31\",\"GATHERID\":\""+primaryKey+"\",\"MONEY\":\""+money+"\",\"MANUFACTURERID\":\""+request.getParameter("manufacturerId")+"\","
								+ "\"INSFRAMEWORKID\":\""+request.getParameter("insframework")+"\",\"MODIFIER\":\""+myuser.getId()+"\"}";
						objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{existsobj1, existsobj2});
					}else{//不存在则将该焊机新增到新项目部
						String obj1 = "{\"CLASSNAME\":\"weldingMachineWebServiceImpl\",\"METHOD\":\"addMachineToItem\"}";
						objects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{obj1,obj2}); 
					}
					if(!objects[0].toString().equals("false")){
						//1_2.修改原项目部焊机采集序号及状态为迁移
						String statusobj1 = "{\"CLASSNAME\":\"maintainWebServiceImpl\",\"METHOD\":\"editStatusToItem\"}";
						String statusobj2 = "{\"WID\":\""+request.getParameter("wid")+"\",\"STATUSID\":\"35\",\"INSFID\":\""+request.getParameter("insframeworkId")+"\"}";
						Object[] statusobjects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{statusobj1, statusobj2});
						
						//1_3.向公司和集团修改焊机归属
						String obj3 = "{\"CLASSNAME\":\"weldingMachineWebServiceImpl\",\"METHOD\":\"editMachineToBlocCompany\"}";
						Object[] updateobjects = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{obj3,obj2});
						if(!updateobjects[0].toString().equals("false") && !statusobjects[0].toString().equals("false")){
							obj.put("success", true);
							if(!objects[0].toString().equals("true")){
								obj.put("msg", objects[0].toString());
							}
						}else{
							obj.put("success", false);
							obj.put("errorMsg", "操作失败！");
						}
					}else{
						obj.put("success", false);
						obj.put("errorMsg", "操作失败！");
					}
				}else if(flag == 1){
					obj.put("success", false);
					obj.put("errorMsg", "操作失败 ,采集模块已经被绑定！");
				}else{
					obj.put("success", false);
					obj.put("errorMsg", "操作失败！");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			obj.put("success", false);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
	}
	
	/**
	 * 项目部发生改变，根据需求处理采集
	 * @param request request对象
	 * @param client Client客户端
	 * @param myuser 当前用户对象
	 * @param hierarchy 当前层级
	 * @return 0表示执行正确，1表示采集被绑定，2表示执行错误
	 */
	public int checkGather(HttpServletRequest request,Client client,MyUser myuser,String hierarchy){
		int flag = 0;
		try{
			//2.该焊机是否绑定采集
			if(iutil.isNull(request.getParameter("gatherId"))){//绑定采集模块
				Gather g = gm.getGatherById(new BigInteger(request.getParameter("gatherId")));
				if(!iutil.isNull(g.getLeavetime())){
					g.setLeavetime("");
				}
				//2_1.判断目标项目部采集模块是否存在
				String gatherobj1 = "{\"CLASSNAME\":\"gatherWebServiceImpl\",\"METHOD\":\"getGatherNoCountToItem\"}";
				String gatherobj2 = "{\"GATHERNO\":\""+request.getParameter("gatherNo")+"\",\"INSFID\":\""+request.getParameter("insframework")+"\"}";
				Object[] gatherobj = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{gatherobj1, gatherobj2});
				if(!gatherobj[0].toString().equals("0")){//已存在
					//判断采集是否被焊机绑定
					String bindingobj1 = "{\"CLASSNAME\":\"weldingMachineWebServiceImpl\",\"METHOD\":\"getGatheridMachine\"}";
					String bindingobj2 = "{\"GATHERNO\":\""+request.getParameter("gatherNo")+"\",\"INSFID\":\""+request.getParameter("insframework")+"\"}";
					Object[] bindingobj = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{bindingobj1, bindingobj2});
					if(bindingobj[0]!=null && !"".equals(bindingobj[0])){//被绑定
						if(!bindingobj[0].toString().equals(request.getParameter("equipmentNo"))){//被绑定且不是与当前焊机绑定
							flag = 1;
							return flag;
						}
					}
					//修改采集状态（三层同步）为正常
					String statusobj1 = "{\"CLASSNAME\":\"gatherWebServiceImpl\",\"METHOD\":\"editGather\"}";
					String statusobj2 = "{\"ID\":\""+gatherobj[0].toString()+"\",\"GATHERNO\":\""+g.getGatherNo()+"\",\"IPURL\":\""+g.getIpurl()+"\",\"INSFID\":\""+request.getParameter("insframework")+"\",\"LEAVETIME\":\""+g.getLeavetime()+"\",\"MACURL\":\""+g.getMacurl()+"\",\"PROTOCOL\":\""+g.getProtocol()+"\",\"STATUS\":\"正常\",\"MODIFIER\":\""+myuser.getId()+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
					Object[] statusobj = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{statusobj1, statusobj2});
					if(statusobj[0].toString().equals("false")){
						flag = 2;
					}else{
						primaryKey = gatherobj[0].toString();
					}
					
				}else{//不存在则新增采集（三层同步）
					String addgatherobj1 = "{\"CLASSNAME\":\"gatherWebServiceImpl\",\"METHOD\":\"addMachineGather\"}";
					String addgatherobj2 = "{\"GATHERNO\":\""+g.getGatherNo()+"\",\"IPURL\":\""+g.getIpurl()+"\",\"INSFID\":\""+request.getParameter("insframework")+"\",\"LEAVETIME\":\""+g.getLeavetime()+"\",\"MACURL\":\""+g.getMacurl()+"\",\"PROTOCOL\":\""+g.getProtocol()+"\",\"STATUS\":\"正常\",\"CREATOR\":\""+myuser.getId()+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
					Object[] addgetherobj = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{addgatherobj1, addgatherobj2});
					if(addgetherobj[0].toString()!=null && !"".equals(addgetherobj[0].toString())){
						primaryKey = addgetherobj[0].toString();
					}else{
						flag = 2;
					}
				}
				//2_2.修改原采集模块状态为迁移（三层同步）
				String oldstatusobj1 = "{\"CLASSNAME\":\"gatherWebServiceImpl\",\"METHOD\":\"editGather\"}";
				String oldstatusobj2 = "{\"ID\":\""+g.getId()+"\",\"GATHERNO\":\""+g.getGatherNo()+"\",\"IPURL\":\""+g.getIpurl()+"\",\"INSFID\":\""+request.getParameter("insframeworkId")+"\",\"LEAVETIME\":\""+g.getLeavetime()+"\",\"MACURL\":\""+g.getMacurl()+"\",\"PROTOCOL\":\""+g.getProtocol()+"\",\"STATUS\":\"迁移\",\"MODIFIER\":\""+myuser.getId()+"\",\"HIERARCHY\":\""+hierarchy+"\"}";
				Object[] oldstatusobj = client.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheIDU"), new Object[]{oldstatusobj1, oldstatusobj2});
				if(oldstatusobj[0].toString().equals("false")){
					flag = 2;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			flag = 2;
		}
		return flag;
	}
	
	/**
	 * 删除焊机设备
	 * @param wid
	 * @return
	 */
	@RequestMapping("/removeWeldingMachine")
	@ResponseBody
	private String removeWeldingMachine(HttpServletRequest request,@RequestParam String wid,@RequestParam String insfid){
		JSONObject obj = new JSONObject();
		try{
			//当前层级
			String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
			//获取项目层url
			String itemurl = request.getSession().getServletContext().getInitParameter("itemurl");
			//获取公司发布地址
			String companyurl = im.webserviceDto(request, new BigInteger(insfid));
			//客户端执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(companyurl);
			iutil.Authority(client);
			String obj1 = "{\"CLASSNAME\":\"weldingMachineWebServiceImpl\",\"METHOD\":\"deleteWeldingChine\"}";
			String obj2 = "{\"WID\":\""+wid+"\",\"ITEMURL\":\""+itemurl+"\",\"HIERARCHY\":\""+hierarchy+"\",\"INSFID\":\""+insfid+"\"}";
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
		}catch(Exception e){
			obj.put("success", true);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
	}
	
	@RequestMapping("/enovalidate")
	@ResponseBody
	private String enovalidate(@RequestParam String eno){
		boolean data = true;
		int count = wmm.getEquipmentnoCount(eno);
		if(count>0){
			data = false;
		}
		return data + "";
	}
	
	@RequestMapping("/gidvalidate")
	@ResponseBody
	private String gidvalidate(HttpServletRequest request){
		String gather = request.getParameter("gather");
		String itemid = request.getParameter("itemid");
		BigInteger item = null;
		if(iutil.isNull(itemid)){
			item = new BigInteger(itemid);
		}
		boolean data = true;
		int count = wmm.getGatheridCount(item,gather);
		if(count>0){
			data = false;
		}
		return data + "";
	}
	
}
