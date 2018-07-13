package com.sshome.ssmcxf.webservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.service.WelderService;
import com.sshome.ssmcxf.webservice.WelderWebService;

@Transactional
@Service
public class WelderWebServiceImpl implements WelderWebService {
	@Autowired
	private WelderService ws;
	
	@Override
	public Object getWelderAll(String object) {
		return ws.getWelderAll(object);
	}

	@Override
	public Object addWelder(String obj1, String obj2) {
		return ws.addWelder(obj1, obj2);
	}

	@Override
	public Object editWelder(String obj1, String obj2) {
		return ws.editWelder(obj1, obj2);
	}

	@Override
	public Object removeWelder(String obj1, String obj2) {
		return ws.removeWelder(obj1, obj2);
	}

	@Override
	public int getWeldernoCount(String object) {
		return ws.getWeldernoCount(object);
	}

	@Override
	public Object getWelderById(String object) {
		return ws.getWelderById(object);
	}

}
