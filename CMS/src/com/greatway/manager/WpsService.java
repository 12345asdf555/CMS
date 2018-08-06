package com.greatway.manager;

import java.math.BigInteger;
import java.util.List;

import com.greatway.model.Wps;
import com.greatway.page.Page;

public interface WpsService {
	List<Wps> findAll(Page page, BigInteger parent,String str);
	void save(Wps wps);
	void update(Wps wps);
	int getUsernameCount(String fwpsnum);
	Wps findById(BigInteger fid);
	void delete(BigInteger fid);
	
}
