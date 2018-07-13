package com.sshome.ssmcxf.webservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.service.ChartService;
import com.sshome.ssmcxf.webservice.ChartWebService;

@Service
@Transactional
public class ChartWebServiceImpl implements ChartWebService {
	@Autowired
	private ChartService cs;
	
	@Override
	public Object getEfficiency(String object) {
		return cs.getEfficiency(object);
	}

	@Override
	public Object getHour(String object) {
		return cs.getHour(object);
	}

	@Override
	public Object getOverproof(String object) {
		return cs.getOverproof(object);
	}

	@Override
	public Object getOvertime(String object) {
		return cs.getOvertime(object);
	}

	@Override
	public Object getLoads(String object) {
		return cs.getLoads(object);
	}

	@Override
	public Object getNoLoads(String object) {
		return cs.getNoLoads(object);
	}

	@Override
	public Object getIdle(String object) {
		return cs.getIdle(object);
	}

	@Override
	public Object getUse(String object) {
		return cs.getUse(object);
	}

}
