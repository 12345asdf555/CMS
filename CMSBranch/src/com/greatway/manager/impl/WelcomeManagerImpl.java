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
	public List<Welcome> getItemMachineCount(BigInteger parent) {
		return wm.getItemMachineCount(parent);
	}

	@Override
	public List<Welcome> getWorkRank(BigInteger parent, String time) {
		return wm.getWorkRank(parent, time);
	}

	@Override
	public Welcome getWorkMachineCount(BigInteger itemid, String time) {
		return wm.getWorkMachineCount(itemid, time);
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
