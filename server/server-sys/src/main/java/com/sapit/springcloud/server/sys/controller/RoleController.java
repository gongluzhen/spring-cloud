package com.sapit.springcloud.server.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapit.springcloud.moudle.sys.Role;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.server.sys.service.RoleService;

@RestController
@RequestMapping(value = "role")
public class RoleController {
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public Role getById(@RequestBody String id) {
		return roleService.get(id);
	}

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public Role get(@RequestBody Role entity) {
		return roleService.get(entity);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Role save(@RequestBody Role entity) {
		return roleService.save(entity);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody Role entity) {
		roleService.delete(entity);
	}

	@RequestMapping(value = "getByName", method = RequestMethod.POST)
	public Role getByName(@RequestBody String name) {
		return roleService.getByName(name);
	}

	@RequestMapping(value = "getByEnname", method = RequestMethod.POST)
	public Role getByEnname(@RequestBody String enname) {
		return roleService.getByEnname(enname);
	}

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<Role> findList(@RequestBody Role role) {
		return roleService.findList(role);
	}

	@RequestMapping(value = "findAllList", method = RequestMethod.POST)
	public List<Role> findAllList(@RequestBody Role role) {
		return roleService.findAllList(role);
	}

	@RequestMapping(value = "findAll", method = RequestMethod.POST)
	public List<Role> findAll(@RequestBody User currentLoginUser) {
		return roleService.findAll(currentLoginUser);
	}

	@RequestMapping(value = "outUserInRole", method = RequestMethod.POST)
	public Boolean outUserInRole(@RequestBody Role role, @RequestParam("userId") String userId) {
		return roleService.outUserInRole(role, userId);
	}

	@RequestMapping(value = "assignUserToRole", method = RequestMethod.POST)
	public User assignUserToRole(@RequestBody Role role, @RequestParam("userId") String userId) {
		return roleService.assignUserToRole(role, userId);
	}

}