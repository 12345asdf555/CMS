package com.sshome.ssmcxf.webservice.impl;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.context.WebServiceContextImpl;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dto.JudgeUtil;
import com.spring.service.WeldService;
import com.sshome.ssmcxf.webservice.SsWebService;

import net.sf.json.JSONObject;

@Transactional
@Service
public class SsWebServiceImpl implements SsWebService {

	@Autowired
	private WeldService userService;
	
	private JudgeUtil jutil = new JudgeUtil();
	
	@Override
	public Boolean AddWeld(String obj1,String obj2) {
		try{
			JSONObject json = JSONObject.fromObject(obj2);
			BigInteger insid = userService.FindIns_Id("{\"FNAME\":\""+json.getString("FITEMID")+"\"}");
			obj2 = obj2.substring(0,obj2.length()-1)+",\"ITEM\":\""+insid+"\"}";
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			//向集团层执行插入
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client blocclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("blocurl"));
			//webservice权限认证
			jutil.Authority(blocclient);
			Object[] blocobj = blocclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});
			BigInteger id = new BigInteger(blocobj[0].toString());
			obj2 = obj2.substring(0,obj2.length()-1)+",\"ID\":\""+id+"\"}";
			//向公司层执行插入
			Client companyclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("companyurl"));
			//webservice权限认证
			jutil.Authority(companyclient);
			Object[] companyobj = companyclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});
			String result = companyobj[0].toString();
			//向项目执行插入
			boolean flag = userService.AddWeld(obj2);
			if(flag && result.equals("true")){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean UpdateWeld(String obj1,String obj2) {
		try{
			JSONObject json = JSONObject.fromObject(obj2);
			BigInteger insid = userService.FindIns_Id("{\"FNAME\":\""+json.getString("FITEMID")+"\"}");
			obj2 = obj2.substring(0,obj2.length()-1)+",\"ITEM\":\""+insid+"\"}";
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			//向集团层执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client blocclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("blocurl"));
			//webservice权限认证
			jutil.Authority(blocclient);
			Object[] blocobj = blocclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});  
			String blocResult = blocobj[0].toString();
			//向公司层执行插入
			Client companyclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("companyurl"));
			//webservice权限认证
			jutil.Authority(companyclient);
			Object[] companyobj = companyclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});
			String result = companyobj[0].toString();
			boolean flag = userService.UpdateWeld(obj2);
			if(flag && result.equals("true") && blocResult.equals("true")){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean AddJunction(String obj1,String obj2) {
		try{
			JSONObject json = JSONObject.fromObject(obj2);
			BigInteger insid = userService.FindIns_Id("{\"FNAME\":\""+json.getString("FITEMID")+"\"}");
			obj2 = obj2.substring(0,obj2.length()-1)+",\"ITEM\":\""+insid+"\"}";
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			//向集团层执行插入
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client blocclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("blocurl"));
			//webservice权限认证
			jutil.Authority(blocclient);
			Object[] blocobj = blocclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});  
			BigInteger id = new BigInteger(blocobj[0].toString());
			obj2 = obj2.substring(0,obj2.length()-1)+",\"ID\":\""+id+"\"}";
			//向公司层执行插入
			Client companyclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("companyurl"));
			//webservice权限认证
			jutil.Authority(companyclient);
			Object[] companyobj = companyclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});
			String result = companyobj[0].toString();
			boolean flag = userService.AddJunction(obj2);
			if(flag && result.equals("true")){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean UpdateJunction(String obj1,String obj2) {
		try{
			JSONObject json = JSONObject.fromObject(obj2);
			BigInteger insid = userService.FindIns_Id("{\"FNAME\":\""+json.getString("FITEMID")+"\"}");
			obj2 = obj2.substring(0,obj2.length()-1)+",\"ITEM\":\""+insid+"\"}";
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			//向集团层执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client blocclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("blocurl"));
			//webservice权限认证
			jutil.Authority(blocclient);
			Object[] blocobj = blocclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});  
			String blocResult = blocobj[0].toString();
			//向公司层执行插入
			Client companyclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("companyurl"));
			//webservice权限认证
			jutil.Authority(companyclient);
			Object[] companyobj = companyclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});
			String result = companyobj[0].toString();
			boolean flag = userService.UpdateJunction(obj2);
			if(flag && result.equals("true") && blocResult.equals("true")){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean DeleteJunction(String obj1,String obj2) {
		try{
			JSONObject json = JSONObject.fromObject(obj2);
			BigInteger insid = userService.FindIns_Id("{\"FNAME\":\""+json.getString("FITEMID")+"\"}");
			obj2 = obj2.substring(0,obj2.length()-1)+",\"ITEM\":\""+insid+"\"}";
			//webservice获取request
			MessageContext ctx = new WebServiceContextImpl().getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			//向集团层执行操作
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client blocclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("blocurl"));
			//webservice权限认证
			jutil.Authority(blocclient);
			Object[] blocobj = blocclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});  
			String blocResult = blocobj[0].toString();
			//向公司层执行插入
			Client companyclient = dcf.createClient(request.getSession().getServletContext().getInitParameter("companyurl"));
			//webservice权限认证
			jutil.Authority(companyclient);
			Object[] companyobj = companyclient.invoke(new QName("http://webservice.ssmcxf.sshome.com/", "enterTheWS"), new Object[]{obj1,obj2});
			String result = companyobj[0].toString();
			boolean flag = userService.DeleteJunction(obj2);
			if(flag && result.equals("true") && blocResult.equals("true")){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public BigInteger FindIns_Id(String insname) {
		return userService.FindIns_Id(insname);
	}

}
