package com.lfcfront.knowledge.service.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lfcfront.knowledge.model.user.User;

public interface IUserService {

	/**
	 * 根据用户名查询用户
	 * 
	 * @param username
	 * @return
	 */
	User queryUserByUsername(String username);

	/**
	 * 查询所有用户列表
	 * @param pageCapacity 
	 * @param pageIndex 
	 * @param user 
	 * @param roleId 
	 */
	List<User> queryUserList(int pageIndex, int pageCapacity, User user, Integer roleId);

	/**
	 * 根据用户ID删除一个用户
	 * 
	 * @param id
	 */
	void deleteUserById(int id);

	void saveUser(HttpServletRequest request);

	/**
	 * 查询用户数量
	 * @param user
	 * @param roleId
	 * @return
	 */
	Integer queryUserCount(User user, Integer roleId);
	/**
	 * 注册新用户
	 * @param newuser
	 * @return
	 */
	int saveUser(User newuser);
}
