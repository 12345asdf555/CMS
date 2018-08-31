package com.greatway.manager.impl;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.greatway.dao.WpsMapper;
import com.greatway.manager.WpsService;
import com.greatway.model.Wps;
import com.greatway.page.Page;

@Service
@Transactional
public class WpsServiceImpl implements WpsService{

	@Resource
	private WpsMapper mapper;
	
	@Override
	public Wps findById(BigInteger fid) {
		return mapper.findById(fid);
	}

	@Override
	public int getUsernameCount(String fwpsnum) {
		return mapper.getUsernameCount(fwpsnum);
	}

	@Override
	public List<Wps> findAll(BigInteger parent,String wpsnum) {
		return mapper.findAll(parent,wpsnum,null);
	}

	@Override
	public boolean save(Wps wps) {
		return mapper.save(wps);
	}

	@Override
	public boolean update(Wps wps) {
		return mapper.update(wps);
	}

	@Override
	public boolean delete(BigInteger fid) {
		return mapper.delete(fid);
	}

	@Override
	public List<Wps> findWpsAll(Page page,BigInteger parent, String str) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return mapper.findWpsAll(parent, str);
	}

	@Override
	public Wps findWpsByid(BigInteger id) {
		return mapper.findWpsByid(id);
	}

	@Override
	public boolean addWps(Wps wps) {
		return mapper.addWps(wps);
	}

	@Override
	public boolean updateWps(Wps wps) {
		return mapper.updateWps(wps);
	}

	@Override
	public boolean deleteWps(String wpsnum, BigInteger fid) {
		mapper.deleteByWpsno(wpsnum);
		return mapper.deleteWps(fid);
	}

	@Override
	public List<Wps> findAll(Page page,BigInteger parent, String wpsnum,String search) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return mapper.findAll(parent,wpsnum,search);
	}

	
}
