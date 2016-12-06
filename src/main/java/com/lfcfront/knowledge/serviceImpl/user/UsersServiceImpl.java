package com.lfcfront.knowledge.serviceImpl.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lfcfront.knowledge.dao.user.UserMapper;
import com.lfcfront.knowledge.model.user.Role;
import com.lfcfront.knowledge.model.user.User;
import com.lfcfront.knowledge.service.user.IUserService;

@Service
public class UsersServiceImpl implements IUserService {

	Logger log = Logger.getLogger(UsersServiceImpl.class);

	@Autowired
	private UserMapper userMapper;

	@Override
	public User queryUserByUsername(String username) {
		return userMapper.queryUserByUsername(username);
	}

	@Override
	public List<User> queryUserList(int pageIndex, int pageCapacity, User user,
			Integer roleId) {
		Map<String, Object> condition = new HashMap<String, Object>();
		// 每页的容量，跟在limit字句后面
		condition.put("limit", pageCapacity);
		// 偏移量，跟在offset字句后面
		condition.put("offset", pageIndex * pageCapacity);
		if (user.getActualName() == "") {
			user.setActualName(null);
		}
		if (user.getUsername() == "") {
			user.setUsername(null);
		}
		if (roleId != null && roleId != 0) {
			List<Integer> roles = new ArrayList<Integer>();
			roles.add(roleId);
			condition.put("roles", roles);
		}
		condition.put("user", user);
		return userMapper.queryAllUser(condition);
	}

	@Transactional
	@Override
	public void deleteUserById(int id) {
		userMapper.deleteUserLogin(id);
		userMapper.deleteUserProfile(id);
		userMapper.deleteUserRole(id);
	}

	@Transactional
	@Override
	public void saveUser(HttpServletRequest request) {
		String actualName = request.getParameter("actual-name");
		String sex = request.getParameter("sex");
		String address = request.getParameter("address");
		String remark = request.getParameter("remark");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String username = request.getParameter("u-username");
		String password = request.getParameter("u-password");
		String[] roles = request.getParameterValues("role");
		String userId = request.getParameter("userId");
		// 如果用户ID不为空做更新操作，先删后插入
		User user = new User();
		if (userId != null) {
			this.deleteUserById(Integer.parseInt(userId));
			user.setId(Integer.parseInt(userId));
		}
		user.setActualName(actualName);
		user.setAddress(address);
		user.setEmail(email);
		user.setMobile(phone);
		user.setPassword(password);
		user.setRemark(remark);
		user.setUsername(username);
		user.setSex(sex);
		// 保存用户账号
		userMapper.saveUserAccount(user);
		// 取出用户ID，组装成用户角色map
		List<Role> roleList = convertRoleList(roles);
		user.setRoleList(roleList);
		// 插入用户角色
		userMapper.saveUserRole(user);
		// 保存用户资料
		userMapper.saveUserProfile(user);
	}

	private List<Role> convertRoleList(String[] roles) {
		List<Role> list = new ArrayList<Role>();
		Role role = null;
		if (roles != null) {
			for (String roleId : roles) {
				role = new Role();
				role.setId(Integer.parseInt(roleId));
				list.add(role);
			}
		}
		return list;
	}

	@Override
	public Integer queryUserCount(User user, Integer roleId) {
		Map<String, Object> condition = new HashMap<String, Object>();
		if (user.getActualName() == "") {
			user.setActualName(null);
		}
		if (user.getUsername() == "") {
			user.setUsername(null);
		}
		if (roleId != null && roleId != 0) {
			List<Integer> roles = new ArrayList<Integer>();
			roles.add(roleId);
			condition.put("roles", roles);
		}
		condition.put("user", user);
		return userMapper.queryUserCount(condition);
	}

	@Override
	public int saveUser(User newuser) {
		return userMapper.saveUser(newuser);
	}
}
