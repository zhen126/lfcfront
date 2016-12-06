package com.lfcfront.knowledge.controller.user;

import java.awt.Menu;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lfcfront.knowledge.dao.user.UserMapper;
import com.lfcfront.knowledge.model.user.Role;
import com.lfcfront.knowledge.model.user.User;
import com.lfcfront.knowledge.service.user.IUserService;

@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	private IUserService userService;

	@Autowired
	private UserMapper userMapper;

	Logger log = Logger.getLogger(UserController.class);
/**
 * 登录接口
 * @param request
 * @param username
 * @param password
 * @param session
 * @return
 */
	@ResponseBody
	@RequestMapping(value = "login")
	public Map<String, Object> login(HttpServletRequest request,
			String username, String password, HttpSession session) {
		User user = userService.queryUserByUsername(username);
		Map<String, Object> result = new HashMap<String, Object>();
		String msg = null;
		int state = 10;
		if (user != null) {
			if (user.getUsername().equals(username)
					&& user.getPassword().equals(password)) {
				state = 0;
				msg = "登录成功！";
				session.setAttribute("user", user);
			} else{
				state = 1;
				msg = "用户名或者密码错误 ！";
			}
		} else{
			state = 1;
			msg = "用户名或者密码错误 ！";
		}
				/*state = -1;
				msg = "系统错误！";*/
		result.put("state", state);
		result.put("msg", msg);
		return result;
	}
	/**
	 * 注册-校验用户名是否存在接口
	 * @param request
	 * @param username
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkUser")
	public Map<String, Object> checkUser(HttpServletRequest request,
			String username,HttpSession session) {
		User user = userService.queryUserByUsername(username);
		Map<String, Object> result = new HashMap<String, Object>();
		String msg = null;
		int state = 10;
		if (user != null) {
				state = 0;
				msg = "用户名已存在！";
		} else {
			state = 1;
			msg = "用户名不存在！";
		}
		/*state = -1;
		msg = "系统错误！";*/
		result.put("state", state);
		result.put("msg", msg);
		return result;
	}
	/**
	 * 注册动作
	 * @param request
	 * @param username
	 * @param password
	 * @param code
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "regist")
	public Map<String, Object> regist(HttpServletRequest request,
			String username, String password,String code,HttpSession session) {
		User user = userService.queryUserByUsername(username);
		Map<String, Object> result = new HashMap<String, Object>();
		String msg = null;
		int state = 10;
		if (username != null && password != null && code != null) {
			if (user == null && "3321".equals(code)) {
				state = 0;
				msg = "注册成功！";
				User newuser = new User();
				newuser.setUsername(username);
				newuser.setPassword(password);
				userService.saveUser(newuser);
			} else if (user == null && !"3321".equals(code)) {
				state = 2;
				msg = "验证码错误！";
			} else if (user != null) {
				state = 1;
				msg = "用户名已存在！";
			}
		} else if (username == null || password == null || code == null) {
			state = 3;
			msg = "缺少必输项！";
		} else {
			state = -1;
			msg = "系统错误！";
		}
		result.put("state", state);
		result.put("msg", msg);
		return result;
	}
	
	
	/**
	 * 用户退出
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout.do")
	public String loginOut(HttpSession session) {
		Object o = session.getAttribute("user");
		if (o != null) {
			session.removeAttribute("user");
		}
		return "login";
	}

	/**
	 * 查询用户列表
	 * 
	 * @param map
	 * @param user
	 * @return
	 */
	@RequestMapping("/userList.do")
	public String queryUser(ModelMap map, User user) {
		return "user/userList";
	}

	/**
	 * 查询用户列表
	 * 
	 * @param map
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryUserList.do")
	public Map<String, Object> queryUserList(User user, Integer roleId,
			Integer pageIndex, Integer pageCapacity) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> list = null;
		try {
			list = userService.queryUserList(pageIndex, pageCapacity, user,
					roleId);
		} catch (Exception e) {
			log.error(e);
		}
		map.put("userList", list);
		return map;
	}

	/**
	 * 查询用户总数量
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryUserCount.do")
	public Map<String, Object> queryUserCount(User user, Integer roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer total = userService.queryUserCount(user, roleId);
		map.put("total", total);
		return map;
	}

	/**
	 * 跳转到添加用户页面
	 * 
	 * @return
	 */
	@RequestMapping("toAddUserPage.do")
	public String addUserPage() {
		return "/user/addUser";
	}

	/**
	 * 删除一个用户
	 * 
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteUser.do")
	public Map<String, String> deleteUser(int userId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "0");
		try {
			userService.deleteUserById(userId);
		} catch (Exception e) {
			log.error(e);
			map.put("state", "1");
		}
		return map;
	}

	/**
	 * 添加一个用户
	 * 
	 * @param user
	 * @return
	 */
	@Transactional
	@ResponseBody
	@RequestMapping("/saveUser.do")
	public Map<String, String> saveUser(HttpServletRequest request) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			userService.saveUser(request);
			result.put("state", "0");
		} catch (Exception e) {
			log.error(e);
			result.put("state", "1");
		}
		return result;
	}

	@RequestMapping("/nologin.do")
	public String noLogin() {
		return "login";
	}

	/**
	 * 查询所有角色列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryRoleList.do")
	public Map<String, List<Role>> queryRoleList() {
		Map<String, List<Role>> roles = new HashMap<String, List<Role>>();
		roles.put("roles", userMapper.queryRoleList());
		return roles;
	}

	/**
	 * 校验一个用户名（工号）是否存在
	 * 
	 * @param username
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/validateUserExists.do")
	public Map<String, String> validateUserExists(String username) {
		Map<String, String> map = new HashMap<String, String>();
		User user = userMapper.queryUserByUsername(username);
		if (user != null) {
			// 用户名存在
			map.put("state", "0");
		} else
			map.put("state", "1");
		return map;
	}

	/**
	 * 根绝ID查询一个用户的资料
	 * 
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryUserInfoById.do")
	public User queryUserInfoById(Integer userId) {
		User user = userMapper.queryUserById(userId);
		return user;
	}

	/**
	 * 跳转到查看用户页面
	 * 
	 * @return
	 */
	@RequestMapping("/home.do")
	public String toViewUserPage() {
		return "home";
	}
}
