package com.sapit.springcloud.client.sys;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sapit.springcloud.common.client.FeignConfig;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.Role;
import com.sapit.springcloud.moudle.sys.User;

@Component
@FeignClient(value = "server-sys", path = "user", configuration = FeignConfig.class)
public interface UserClient {
	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public User getById(@RequestBody String id);

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public User get(@RequestBody User entity);

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public User save(@RequestBody User entity);

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody User entity);

	@RequestMapping(value = "getByLoginName", method = RequestMethod.POST)
	public User getByLoginName(@RequestBody String loginName);

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<User> findList(@RequestBody User user);

	@RequestMapping(value = "findAllList", method = RequestMethod.POST)
	public List<User> findAllList(@RequestBody User user);

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	public Page<User> findPage(@RequestBody User user);

	@RequestMapping(value = "findNoPage", method = RequestMethod.POST)
	public List<User> findNoPage(@RequestBody User user);

	@RequestMapping(value = "findByOfficeId", method = RequestMethod.POST)
	public List<User> findByOfficeId(@RequestBody String officeId);

	@RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
	public void updateUserInfo(@RequestBody User user);

	@RequestMapping(value = "updatePasswordById", method = RequestMethod.POST)
	public void updatePasswordById(@RequestBody String id, @RequestParam("loginName") String loginName, @RequestParam("newPassword") String newPassword);

	@RequestMapping(value = "updateUserLoginInfo", method = RequestMethod.POST)
	public void updateUserLoginInfo(@RequestBody User user, @RequestParam("loginIp") String loginIp);

	@RequestMapping(value = "deleteUserRoleByRoleId", method = RequestMethod.POST)
	public void deleteUserRoleByRoleId(@RequestBody String roleId);

	@RequestMapping(value = "deleteUserRoleByRoleIdUserId", method = RequestMethod.POST)
	public void deleteUserRoleByRoleIdUserId(@RequestBody String roleId, @RequestParam("userId") String userId);

	@RequestMapping(value = "insertUserRoleByRoleIdUserId", method = RequestMethod.POST)
	public void insertUserRoleByRoleIdUserId(@RequestBody String roleId, @RequestParam("userId") String userId);

	@RequestMapping(value = "findUserByLoginNameAndEmail", method = RequestMethod.POST)
	public List<User> findUserByLoginNameAndEmail(@RequestBody User user);

	@RequestMapping(value = "deleteAllUserRoleByRoleId", method = RequestMethod.POST)
	public void deleteAllUserRoleByRoleId(@RequestBody Role role);

	@RequestMapping(value = "entryptPassword", method = RequestMethod.POST)
	public String entryptPassword(@RequestBody String plainPassword);

	@RequestMapping(value = "validatePassword", method = RequestMethod.POST)
	public boolean validatePassword(@RequestBody String plainPassword, @RequestParam("password") String password);
}
