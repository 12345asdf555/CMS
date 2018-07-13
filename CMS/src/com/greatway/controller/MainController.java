package com.greatway.controller;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greatway.util.IsnullUtil;
import com.spring.model.MyUser;
import com.spring.model.User;
import com.spring.service.UserService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/hierarchy", produces = { "text/json;charset=UTF-8" })
public class MainController {
	@Autowired
	private UserService user;
	
	IsnullUtil iutil = new IsnullUtil();
	
	/**
	 * 跳转index页面进行分层
	 * @return
	 */
	@RequestMapping("/goIndex")
	@ResponseBody
	public String goGather(HttpServletRequest request){
		String hierarchy = request.getSession().getServletContext().getInitParameter("hierarchy");
		request.setAttribute("hierarchy", hierarchy);
		JSONObject obj = new JSONObject();
		obj.put("hierarchy", hierarchy);
		return obj.toString();
	}
	
	@RequestMapping("/getUserInsframework")
	@ResponseBody
	public String getUserInsframework(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(object==null){
			obj.put("uname", "请登录");
			obj.put("insframework", "无");
			return obj.toString();
		}
		MyUser u = (MyUser)object;
		User list = user.getUserInsframework(new BigInteger(u.getId()+""));
		obj.put("id", u.getId());
		obj.put("uname", list.getUserName());
		obj.put("type", list.getType());
		obj.put("insframework", list.getInsname());
		return obj.toString();
	}
}
