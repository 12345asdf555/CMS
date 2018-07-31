package com.greatway.manager.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.greatway.dao.FaultMapper;
import com.greatway.manager.FaultManager;
import com.greatway.model.Fault;
import com.greatway.page.Page;

@Service
@Transactional
public class FaultManagerImpl implements FaultManager {
	@Autowired
	private FaultMapper fm;
	
	@Override
	public List<Fault> getFaultAll(Page page,String str) {
		PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		return fm.getFaultAll(str);
	}

	@Override
	public Fault getFaultById(BigInteger id) {
		return fm.getFaultById(id);
	}

	@Override
	public boolean addFault(Fault fault) {
		return fm.addFault(fault);
	}

	@Override
	public boolean editFault(Fault fault) {
		return fm.editFault(fault);
	}

	@Override
	public boolean deleteFault(BigInteger id) {
		return fm.deleteFault(id);
	}

}
