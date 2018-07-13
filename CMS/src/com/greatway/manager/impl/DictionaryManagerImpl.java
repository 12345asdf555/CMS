package com.greatway.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.greatway.dao.DictionaryMapper;
import com.greatway.manager.DictionaryManager;
import com.greatway.model.Dictionarys;
import com.greatway.page.Page;


@Service
@Transactional
public class DictionaryManagerImpl implements DictionaryManager {

	@Autowired
	private DictionaryMapper dictionaryMapper;
	@Override
	public List<Dictionarys> getAllDictionary(Page page, String str) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		return dictionaryMapper.getDictionaryAll(str);
	}
	@Override
	public void addDictionary(Dictionarys d) {
		int value= dictionaryMapper.getDictionaryMaxValue(d.getTypeid());
		d.setValue(value+1);
		dictionaryMapper.addDictionary(d);
	}
	@Override
	public void editDictionary(Dictionarys d) {
		dictionaryMapper.editDictionary(d);
		
	}
	@Override
	public int getDictionaryMaxValue(int typeid) {
	    int id=dictionaryMapper.getDictionaryMaxValue(typeid);
		return id;
	}
	@Override
	public Dictionarys getDictionaryByFid(int id) {
		Dictionarys dic=dictionaryMapper.getDictionaryByFid(id);
		return dic;
	}
	@Override
	public void deleteDictionary(int id) {
		dictionaryMapper.deleteDictionary(id);
	}
	
	@Override
	public List<Dictionarys> getDictionaryValue(int typeid) {
		return dictionaryMapper.getDictionaryValue(typeid);
	}
	@Override
	public List<Dictionarys> getDicValueByValue(int typeid, int value) {
		return dictionaryMapper.getDicValueByValue(typeid, value);
	}
	@Override
	public int getvaluebyname(int typeid,String valuename) {
		return dictionaryMapper.getvaluebyname(typeid,valuename);
	}
	@Override
	public String getDicValueByType(int value) {
		return dictionaryMapper.getDicValueByType(value);
	}
	@Override
	public List<Dictionarys> getBack() {
		return dictionaryMapper.getBack();
	}
}
