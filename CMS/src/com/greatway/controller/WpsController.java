package com.greatway.controller;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.greatway.manager.WpsService;
import com.greatway.model.Wps;
import com.greatway.page.Page;
import com.greatway.util.IsnullUtil;
import com.spring.model.MyUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/wps", produces = { "text/json;charset=UTF-8" })
public class WpsController {
	private Page page;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int total = 0;

	@Autowired
	private WpsService wpsService;

	IsnullUtil iutil = new IsnullUtil();

	@RequestMapping("/goWps")
	public String goWelder() {
		return "weldwps/allWps";
	}

	@RequestMapping("/getAllWps")
	@ResponseBody
	public String getAllWps(HttpServletRequest request) {
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String search = request.getParameter("searchStr");
		String parentId = request.getParameter("parent");
		BigInteger parent = null;
		if (iutil.isNull(parentId)) {
			parent = new BigInteger(parentId);
		}
		page = new Page(pageIndex, pageSize, total);
		List<Wps> findAll = wpsService.findAll(page, parent, search);
		long total = 0;

		if (findAll != null) {
			PageInfo<Wps> pageinfo = new PageInfo<Wps>(findAll);
			total = pageinfo.getTotal();
		}

		request.setAttribute("wpsList", findAll);
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try {
			for (Wps wps : findAll) {
				json.put("fid", wps.getFid());
				json.put("fwpsnum", wps.getFwpsnum());
				json.put("fweld_i", wps.getFweld_i());
				json.put("fweld_v", wps.getFweld_v());
				json.put("fweld_i_max", wps.getFweld_i_max());
				json.put("fweld_i_min", wps.getFweld_i_min());
				json.put("fweld_v_max", wps.getFweld_v_max());
				json.put("fweld_v_min", wps.getFweld_v_min());
				json.put("fweld_alter_i", wps.getFweld_alter_i());
				json.put("fweld_alter_v", wps.getFweld_alter_v());
				json.put("fweld_prechannel", wps.getFweld_prechannel());
				json.put("insname", wps.getInsname());
				json.put("insid", wps.getInsid());
				json.put("fback", wps.getFback());
				json.put("fname", wps.getFname());
				json.put("fdiameter", wps.getFdiameter());
				ary.add(json);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}


	@RequestMapping("/addWps")
	@ResponseBody
	public String addUser(HttpServletRequest request,Wps wps) {
		MyUser myuser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		JSONObject obj = new JSONObject();
		try {
			wps.setFcreater(myuser.getId());
			wpsService.save(wps);
			obj.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("success", false);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
	}

	@RequestMapping("/updateWps")
	@ResponseBody
	public String updateWps(Wps wps, HttpServletRequest request) {
		MyUser myuser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		JSONObject obj = new JSONObject();
		try {
			wps.setFupdater(myuser.getId());
			wpsService.update(wps);
			obj.put("success", true);
		} catch (Exception e) {
			obj.put("success", false);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();

	}

	@RequestMapping("/destroyWps")
	@ResponseBody
	public String destroyWps(@RequestParam BigInteger fid) {

		JSONObject obj = new JSONObject();
		try {
			wpsService.delete(fid);
			obj.put("success", true);
		} catch (Exception e) {
			obj.put("success", false);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
	}

	@RequestMapping("/wpsvalidate")
	@ResponseBody
	private String wpsvalidate(@RequestParam String fwpsnum) {
		boolean data = true;
		int count = wpsService.getUsernameCount(fwpsnum);
		if (count > 0) {
			data = false;
		}
		return data + "";
	}

	
}
