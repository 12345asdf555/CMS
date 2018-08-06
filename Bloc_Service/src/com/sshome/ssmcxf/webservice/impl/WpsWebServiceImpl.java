package com.sshome.ssmcxf.webservice.impl;

import java.math.BigInteger;

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
	public BigInteger save(String object) {
		return ws.save(object);
	}

	@Override
	public boolean update(String object) {
		return ws.update(object);
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
	public boolean delete(String object) {
		return ws.delete(object);
	}

}
