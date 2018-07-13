package com.sshome.ssmcxf.webservice.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.service.WeldedJunctionService;
import com.sshome.ssmcxf.webservice.JunctionWebService;
@Transactional
@Service
public class JunctionWebServiceImpl implements JunctionWebService {
	@Autowired
	private WeldedJunctionService jws;
	
	@Override
	public Object getWeldedJunctionAll(String object) {
		return jws.getWeldedJunctionAll(object);
	}

	@Override
	public Object getWeldedJunctionById(String object) {
		return jws.getWeldedJunctionById(object);
	}

	@Override
	public int getWeldedjunctionByNo(String object) {
		return jws.getWeldedjunctionByNo(object);
	}

	@Override
	public Object addJunction(String obj1, String obj2) {
		return jws.addJunction(obj1, obj2);
	}

	@Override
	public Object updateJunction(String obj1, String obj2) {
		return jws.updateJunction(obj1, obj2);
	}

	@Override
	public Object deleteJunction(String obj1, String obj2) {
		return jws.deleteJunction(obj1, obj2);
	}

}
