package com.greatway.manager.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.greatway.dao.EquipmentManufacturerMapper;
import com.greatway.manager.ManufacturerManager;
import com.greatway.model.EquipmentManufacturer;
import com.greatway.page.Page;

@Service
@Transactional
public class ManufacturerManagerImpl implements ManufacturerManager {
	@Autowired
	private  EquipmentManufacturerMapper em;
	
	@Override
	public List<EquipmentManufacturer> getmanuAll(Page page, String str) {
		PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		return em.getmanuAll(str);
	}

	@Override
	public BigInteger getManuidByValue(String name, String type) {
		return em.getManuidByValue(name, type);
	}

	@Override
	public int getManuCount(String name, int type) {
		return em.getManuCount(name, type);
	}

	@Override
	public EquipmentManufacturer getManuById(BigInteger id) {
		return em.getManuById(id);
	}

	@Override
	public boolean addManu(EquipmentManufacturer manu) {
		return em.addManu(manu);
	}

	@Override
	public boolean editManu(EquipmentManufacturer manu) {
		return em.editManu(manu);
	}

	@Override
	public boolean deleteManu(BigInteger id) {
		return em.deleteManu(id);
	}

}
