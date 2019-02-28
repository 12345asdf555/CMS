package com.sshome.ssmcxf.webservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.service.WpsService;
import com.sshome.ssmcxf.webservice.WpsWebService;
@Transactional
@Service
public class WpsWebServiceImpl implements WpsWebService {
	@Autowired
	private WpsService ws;

	@Override
	public Object findAll(String object) {
		return ws.findAll(object);
	}

	@Override
	public int getUsernameCount(String object) {
		return ws.getUsernameCount(object);
	}

	@Override
	public Object findById(String object) {
		return ws.findById(object);
	}

	@Override
	public Object delete(String obj1, String obj2) {
		return ws.delete(obj1, obj2);
	}

	@Override
	public Object findWpsAll(String object) {
		return ws.findWpsAll(object);
	}

	@Override
	public Object findWpsByid(String object) {
		return ws.findWpsByid(object);
	}

	@Override
	public Object addWps(String obj1, String obj2) {
		return ws.addWps(obj1, obj2);
	}

	@Override
	public Object updateWps(String obj1, String obj2) {
		return ws.updateWps(obj1, obj2);
	}

	@Override
	public Object deleteWps(String obj1, String obj2) {
		return ws.deleteWps(obj1, obj2);
	}

	@Override
	public Object saveChildrenWPS(String obj1, String obj2) {
		return ws.saveChiledrenWps(obj1, obj2);
	}
}
