package com.greatway.manager.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greatway.dao.WelcomeMapper;
import com.greatway.dto.WeldDto;
import com.greatway.manager.WelcomeManager;
import com.greatway.model.Welcome;

@Transactional
@Service
public class WelcomeManagerImpl implements WelcomeManager {
	@Autowired
	private WelcomeMapper wm;
	
	@Override
	public List<Welcome> getItemMachineCount(WeldDto dto) {
		return wm.getItemMachineCount(dto);
	}

	@Override
	public List<Welcome> getWorkRank(WeldDto dto) {
		return wm.getWorkRank(dto);
	}

	@Override
	public Welcome getWorkMachineCount(BigInteger itemid, WeldDto dto) {
		return wm.getWorkMachineCount(itemid, dto);
	}

	@Override
	public List<Welcome> getItemWeldTime(WeldDto dto) {
		return wm.getItemWeldTime(dto);
	}

	@Override
	public List<Welcome> getItemOverProofTime(WeldDto dto) {
		return wm.getItemOverProofTime(dto);
	}

}
