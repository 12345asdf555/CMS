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
	public List<Wps> findAll(Page page, BigInteger parent, String str) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return mapper.findAll(parent,str);
	}

	public void save(Wps wps) {
		mapper.save(wps);
	}

	public Wps findById(BigInteger fid) {
		return mapper.findById(fid);
	}

	public void update(Wps wps) {
		mapper.update(wps);
		
	}

	public void delete(BigInteger fid) {
		mapper.delete(fid);
	}

	@Override
	public int getUsernameCount(String fwpsnum) {
		return mapper.getUsernameCount(fwpsnum);
	}

	
}
