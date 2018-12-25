package com.sapit.springcloud.client.sys;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sapit.springcloud.common.client.FeignConfig;
import com.sapit.springcloud.moudle.sys.Role;
import com.sapit.springcloud.moudle.sys.User;

@Component
@FeignClient(value = "server-sys", path = "role", configuration = FeignConfig.class)
public interface RoleClient {
	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public Role getById(@RequestBody String id);

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public Role get(@RequestBody Role entity);

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Role save(@RequestBody Role entity);

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody Role entity);

	@RequestMapping(value = "getByName", method = RequestMethod.POST)
	public Role getByName(@RequestBody String name);

	@RequestMapping(value = "getByEnname", method = RequestMethod.POST)
	public Role getByEnname(@RequestBody String enname);

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<Role> findList(@RequestBody Role role);

	@RequestMapping(value = "findAllList", method = RequestMethod.POST)
	public List<Role> findAllList(@RequestBody Role role);

	@RequestMapping(value = "findAll", method = RequestMethod.POST)
	public List<Role> findAll(@RequestBody User currentLoginUser);

	@RequestMapping(value = "outUserInRole", method = RequestMethod.POST)
	public Boolean outUserInRole(@RequestBody Role role, @RequestParam("userId") String userId);

	@RequestMapping(value = "assignUserToRole", method = RequestMethod.POST)
	public User assignUserToRole(@RequestBody Role role, @RequestParam("userId") String userId);

}