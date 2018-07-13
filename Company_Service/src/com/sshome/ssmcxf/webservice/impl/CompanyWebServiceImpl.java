package com.sshome.ssmcxf.webservice.impl;

import java.lang.reflect.Method;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sshome.ssmcxf.webservice.ChartWebService;
import com.sshome.ssmcxf.webservice.CompanyWebService;

import net.sf.json.JSONObject;

@Transactional
@Service
@WebService(endpointInterface = "com.sshome.ssmcxf.webservice.CompanyWebService", serviceName = "CompanyWebService")
public class CompanyWebServiceImpl implements CompanyWebService {
	@Autowired
	private ChartWebService cws;
	@Override
	public Object enterTheWS(String obj1, String obj2) {
		try{
			JSONObject json1 = JSONObject.fromObject(obj1);
			ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"config/spring-common.xml"});
			Class<?>  cls = context.getBean(json1.getString("CLASSNAME")).getClass();
			Method m = cls.getDeclaredMethod(json1.getString("METHOD"),new Class[]{String.class});
			Object obj = m.invoke(context.getBean(json1.getString("CLASSNAME")),new Object[]{obj2});
			return obj;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public Object enterTheIDU(String obj1, String obj2) {
		try{
			JSONObject json1 = JSONObject.fromObject(obj1);
			ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"config/spring-common.xml"});
			Class<?>  cls = context.getBean(json1.getString("CLASSNAME")).getClass();
			Method m = cls.getDeclaredMethod(json1.getString("METHOD"),new Class[]{String.class,String.class});
			Object obj = m.invoke(context.getBean(json1.getString("CLASSNAME")),new Object[]{obj1,obj2});
			return obj;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Object enterNoParamWs(String obj1) {
		try{
			JSONObject json1 = JSONObject.fromObject(obj1);
			ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"config/spring-common.xml"});
			Class<?>  cls = context.getBean(json1.getString("CLASSNAME")).getClass();
			Method m = cls.getDeclaredMethod(json1.getString("METHOD"));
			Object obj = m.invoke(context.getBean(json1.getString("CLASSNAME")));
			return obj;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public Object enterChart(String object) {
		try{
			JSONObject json = JSONObject.fromObject(object);
			int num = json.getInt("NUM");
			if(num==1){
				return cws.getEfficiency(object);
			}else if(num==2){
				return cws.getHour(object);
			}else if(num==3){
				return cws.getOvertime(object);
			}else if(num==4){
				return cws.getOverproof(object);
			}else if(num==5){
				return cws.getLoads(object);
			}else if(num==6){
				return cws.getNoLoads(object);
			}else if(num==7){
				return cws.getIdle(object);
			}else if(num==8){
				return cws.getUse(object);
			}
			return "抱歉，未找到您想要的报表信息！";
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
