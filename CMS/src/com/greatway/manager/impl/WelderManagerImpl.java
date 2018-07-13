package com.greatway.manager.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.greatway.dao.WelderMapper;
import com.greatway.manager.WelderManager;
import com.greatway.model.Welder;
import com.greatway.page.Page;

@Service
@Transactional
public class WelderManagerImpl implements WelderManager {
	@Autowired
	private WelderMapper wm;
	
	@Override
	public List<Welder> getWelderAll(Page page, String str) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return wm.getWelderAll(str);
	}

	@Override
	public void addWelder(Welder we) {
		wm.addWelder(we);
	}

	@Override
	public void editWelder(Welder we) {
		wm.editWelder(we);
	}

	@Override
	public void removeWelder(BigInteger id) {
		wm.removeWelder(id);
	}

	@Override
	public int getWeldernoCount(String wno) {
		return wm.getWeldernoCount(wno);
	}

	@Override
	public Welder getWelderById(BigInteger id) {
		return wm.getWelderById(id);
	}

}
