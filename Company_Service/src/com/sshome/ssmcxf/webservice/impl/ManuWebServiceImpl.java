package com.sshome.ssmcxf.webservice.impl;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.service.ManufacturerService;
import com.sshome.ssmcxf.webservice.ManuWebService;

@Service
@Transactional
public class ManuWebServiceImpl implements ManuWebService {
	@Autowired
	private ManufacturerService ms;
	
	@Override
	public Object getmanuAll(String object) {
		return ms.getmanuAll(object);
	}

	@Override
	public BigInteger getManuidByValue(String object) {
		return ms.getManuidByValue(object);
	}

	@Override
	public int getManuCount(String object) {
		return ms.getManuCount(object);
	}

	@Override
	public Object getManuById(String object) {
		return ms.getManuById(object);
	}

	@Override
	public Object addManu(String obj1, String obj2) {
		return ms.addManu(obj1, obj2);
	}

	@Override
	public Object editManu(String obj1, String obj2) {
		return ms.editManu(obj1, obj2);
	}

	@Override
	public Object deleteManu(String obj1, String obj2) {
		return ms.deleteManu(obj1, obj2);
	}

}
