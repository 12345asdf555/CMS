package com.spring.service;

import java.math.BigInteger;
import java.util.List;

import com.greatway.model.SmsUser;
import com.greatway.page.Page;
import com.spring.model.User;

public interface UserService {
	void save(User user);
	void saveRole(User user);
	boolean update(User user);
	boolean delete(int id);
	boolean deleteRole(int id);
	User findById(Integer id);
	String findByRoleId(Integer id);
	int findByName(String name);
	List<User> findAll(Page page, BigInteger parent,String str);
	List<User> findRole(Integer id);
	List<User> findAllRole();
	String updateUserRole(Integer findByRoleId);
	int getUsernameCount(String userName);
	User LoadUser(String userName);
	List<String> getAuthoritiesByUsername(String userName);
	List<User> getIns(BigInteger parent);
	User getUserInsframework(BigInteger id);
	List<User> getInsUser(int ins);

	/**
	 * 获取短信用户
	 * @param page 分页
	 * @param parent 归属id
	 * @param str 拼接查询条件
	 * @return
	 */
	List<SmsUser> getSMSUser(Page page,BigInteger parent,String str);
	
	/**
	 * 新增短信用户
	 * @param user
	 * @return
	 */
	boolean saveSMSUser(SmsUser user);
	
	/**
	 * 修改短信用户
	 * @param user
	 * @return
	 */
	boolean editSMSUser(SmsUser user);
	
	/**
	 * 删除短信用户
	 * @param id
	 * @return
	 */
	boolean removeSMSUser(BigInteger id);

	/**
	 * 选择用户
	 * @param parent 组织机构id
	 * @param name 用户名
	 * @return
	 */
	List<User> selectUser(Page page,BigInteger parent,String name);
	
	/**
	 * 判断该项目部下是否已存在短信用户
	 * @param id
	 * @return
	 */
	int getSelectCountByInsid(BigInteger id);
}