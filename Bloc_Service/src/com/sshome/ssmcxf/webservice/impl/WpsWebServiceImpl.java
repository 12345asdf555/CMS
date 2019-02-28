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
	public String save(String object) {
		return ws.save(object);
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

	@Override
	public Object findWpsAll(String object) {
		return ws.findWpsAll(object);
	}

	@Override
	public Object findWpsByid(String object) {
		return ws.findWpsByid(object);
	}

	@Override
	public BigInteger addWps(String object) {
		return ws.addWps(object);
	}

	@Override
	public boolean updateWps(String object) {
		return ws.updateWps(object);
	}

	@Override
	public boolean deleteWps(String object) {
		return ws.deleteWps(object);
	}

	@Override
	public BigInteger saveChildrenWPS(String object) {
		return ws.saveChiledrenWps(object);
	}
	
}
