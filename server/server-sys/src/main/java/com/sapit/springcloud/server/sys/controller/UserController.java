package com.sapit.springcloud.server.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.Role;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.server.sys.service.UserService;

@RestController
@RequestMapping(value = "user")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public User getById(@RequestBody String id) {
		return userService.get(id);
	}

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public User get(@RequestBody User entity) {
		return userService.get(entity);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public User save(@RequestBody User entity) {
		return userService.save(entity);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody User entity) {
		userService.delete(entity);
	}

	@RequestMapping(value = "getByLoginName", method = RequestMethod.POST)
	public User getByLoginName(@RequestBody String loginName) {
		return userService.getByLoginName(loginName);
	}

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<User> findList(@RequestBody User user) {
		return userService.findList(user);
	}

	@RequestMapping(value = "findAllList", method = RequestMethod.POST)
	public List<User> findAllList(@RequestBody User user) {
		return userService.findAllList(user);
	}

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	public Page<User> findPage(@RequestBody User user) {
		return userService.findPage(user);
	}

	@RequestMapping(value = "findNoPage", method = RequestMethod.POST)
	public List<User> findNoPage(@RequestBody User user) {
		return userService.find(user);
	}

	@RequestMapping(value = "findByOfficeId", method = RequestMethod.POST)
	public List<User> findByOfficeId(@RequestBody String officeId) {
		return userService.findByOfficeId(officeId);
	}

	@RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
	public void updateUserInfo(@RequestBody User user) {
		userService.updateUserInfo(user);
	}

	@RequestMapping(value = "updatePasswordById", method = RequestMethod.POST)
	public void updatePasswordById(@RequestBody String id, @RequestParam("loginName") String loginName, @RequestParam("newPassword") String newPassword) {
		userService.updatePasswordById(id, loginName, newPassword);
	}

	@RequestMapping(value = "updateUserLoginInfo", method = RequestMethod.POST)
	public void updateUserLoginInfo(@RequestBody User user, @RequestParam("loginIp") String loginIp) {
		userService.updateUserLoginInfo(user, loginIp);
	}

	@RequestMapping(value = "deleteUserRoleByRoleId", method = RequestMethod.POST)
	public void deleteUserRoleByRoleId(@RequestBody String roleId) {
		userService.deleteUserRoleByRoleId(roleId);
	}

	@RequestMapping(value = "deleteUserRoleByRoleIdUserId", method = RequestMethod.POST)
	public void deleteUserRoleByRoleIdUserId(@RequestBody String roleId, @RequestParam("userId") String userId) {
		userService.deleteUserRoleByRoleIdUserId(roleId, userId);
	}

	@RequestMapping(value = "insertUserRoleByRoleIdUserId", method = RequestMethod.POST)
	public void insertUserRoleByRoleIdUserId(@RequestBody String roleId, @RequestParam("userId") String userId) {
		userService.insertUserRoleByRoleIdUserId(roleId, userId);
	}

	@RequestMapping(value = "findUserByLoginNameAndEmail", method = RequestMethod.POST)
	public List<User> findUserByLoginNameAndEmail(@RequestBody User user) {
		return userService.findUserByLoginNameAndEmail(user);
	}

	@RequestMapping(value = "deleteAllUserRoleByRoleId", method = RequestMethod.POST)
	public void deleteAllUserRoleByRoleId(@RequestBody Role role) {
		userService.deleteAllUserRoleByRoleId(role);
	}

	@RequestMapping(value = "entryptPassword", method = RequestMethod.POST)
	public String entryptPassword(@RequestBody String plainPassword) {
		return userService.entryptPassword(plainPassword);
	}

	@RequestMapping(value = "validatePassword", method = RequestMethod.POST)
	public boolean validatePassword(@RequestBody String plainPassword, @RequestParam("password") String password) {
		return userService.validatePassword(plainPassword, password);
	}
}
