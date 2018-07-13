package com.sshome.ssmcxf.webservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.service.FaultService;
import com.sshome.ssmcxf.webservice.FaultWebService;
@Transactional
@Service
public class FaultWebServiceImpl implements FaultWebService {
	@Autowired
	private FaultService fs;
	@Override
	public Object getFaultAll(String object) {
		return fs.getFaultAll(object);
	}

	@Override
	public Object getFaultById(String object) {
		return fs.getFaultById(object);
	}

	@Override
	public Object addFault(String obj1, String obj2) {
		return fs.addFault(obj1, obj2);
	}

	@Override
	public Object editFault(String obj1, String obj2) {
		return fs.editFault(obj1, obj2);
	}

	@Override
	public Object deleteFault(String obj1, String obj2) {
		return fs.deleteFault(obj1, obj2);
	}

}
