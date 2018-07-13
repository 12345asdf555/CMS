package com.sshome.ssmcxf.webservice.impl;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.service.WeldService;
import com.sshome.ssmcxf.webservice.SsWebService;

@Transactional
@Service
public class SsWebServiceImpl implements SsWebService {

	@Autowired
	private WeldService userService;
	
	@Override
	public Boolean AddWeld(String obj) {
		return userService.AddWeld(obj);
	}

	@Override
	public Boolean UpdateWeld(String obj) {
		return userService.UpdateWeld(obj);
	}

	@Override
	public Boolean AddJunction(String obj) {
		return  userService.AddJunction(obj);
	}

	@Override
	public Boolean UpdateJunction(String obj) {
		return userService.UpdateJunction(obj);
	}

	@Override
	public Boolean DeleteJunction(String obj) {
		return  userService.DeleteJunction(obj);
	}

	@Override
	public BigInteger FindIns_Id(String insname) {
		return userService.FindIns_Id(insname);
	}

}
