package com.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dao.WeldingMachineMapper;
import com.spring.service.DemoService;

@Service
@Transactional
public class DemoServiceImpl implements DemoService {
	@Autowired
	private WeldingMachineMapper wmm;

	@Override
	public int getEquipmentnoCount(String name) {
		try{
			return wmm.getEquipmentnoCount(name);
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}
	
}
