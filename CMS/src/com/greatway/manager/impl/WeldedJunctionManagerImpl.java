package com.greatway.manager.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.greatway.dao.WeldedJunctionMapper;
import com.greatway.manager.WeldedJunctionManager;
import com.greatway.model.WeldedJunction;
import com.greatway.page.Page;

@Service
@Transactional
public class WeldedJunctionManagerImpl implements WeldedJunctionManager{
	@Autowired
	private WeldedJunctionMapper wjm;

	@Override
	public List<WeldedJunction> getWeldedJunctionAll(Page page, String str, BigInteger parent) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return wjm.getWeldedJunctionAll(str,parent);
	}

	@Override
	public int getWeldedjunctionByNo(String wjno, BigInteger parent) {
		return wjm.getWeldedjunctionByNo(wjno, parent);
	}

	@Override
	public boolean addJunction(WeldedJunction wj) {
		return wjm.addJunction(wj);
	}

	@Override
	public boolean updateJunction(WeldedJunction wj) {
		return wjm.updateJunction(wj);
	}

	@Override
	public boolean deleteJunction(BigInteger id) {
		return wjm.deleteJunction(id);
	}

	@Override
	public WeldedJunction getWeldedJunctionById(BigInteger id) {
		return wjm.getWeldedJunctionById(id);
	}

	@Override
	public List<WeldedJunction> getLiveJunction(Page page,BigInteger parent) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return wjm.getLiveJunction(parent);
	}

}
