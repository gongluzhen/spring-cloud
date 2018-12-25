package com.sapit.springcloud.client.sys;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sapit.springcloud.common.client.FeignConfig;
import com.sapit.springcloud.moudle.sys.Menu;
import com.sapit.springcloud.moudle.sys.User;

@Component
@FeignClient(value = "server-sys", path = "menu", configuration = FeignConfig.class)
public interface MenuClient {
	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public Menu getById(@RequestBody String id);

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public Menu get(@RequestBody Menu entity);

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody Menu entity);

	@RequestMapping(value = "findAll", method = RequestMethod.POST)
	public List<Menu> findAll(@RequestBody User currentLoginUser);

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Menu save(@RequestBody Menu menu);

	@RequestMapping(value = "updateMenuSort", method = RequestMethod.POST)
	public void updateMenuSort(@RequestBody Menu menu);

	@RequestMapping(value = "findAllList", method = RequestMethod.POST)
	public List<Menu> findAllList(@RequestBody Menu menu);

	@RequestMapping(value = "findByUserId", method = RequestMethod.POST)
	public List<Menu> findByUserId(@RequestBody Menu menu);
}