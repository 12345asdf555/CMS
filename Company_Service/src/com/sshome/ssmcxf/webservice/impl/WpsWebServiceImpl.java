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
	public Object save(String obj1, String obj2) {
		return ws.save(obj1, obj2);
	}

	@Override
	public Object update(String obj1, String obj2) {
		return ws.update(obj1, obj2);
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

}
