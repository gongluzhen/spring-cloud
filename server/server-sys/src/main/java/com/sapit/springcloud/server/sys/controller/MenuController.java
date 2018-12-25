package com.sapit.springcloud.server.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapit.springcloud.moudle.sys.Menu;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.server.sys.service.MenuService;

@RestController
@RefreshScope
@RequestMapping(value = "menu")
public class MenuController {
	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public Menu getById(@RequestBody String id) {
		return menuService.get(id);
	}

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public Menu get(@RequestBody Menu entity) {
		return menuService.get(entity);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody Menu entity) {
		menuService.delete(entity);
	}

	@RequestMapping(value = "findAll", method = RequestMethod.POST)
	public List<Menu> findAll(@RequestBody User currentLoginUser) {
		return menuService.findAll(currentLoginUser);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Menu save(@RequestBody Menu menu) {
		return menuService.save(menu);
	}

	@RequestMapping(value = "updateMenuSort", method = RequestMethod.POST)
	public void updateMenuSort(@RequestBody Menu menu) {
		menuService.updateMenuSort(menu);
	}

	@RequestMapping(value = "findAllList", method = RequestMethod.POST)
	public List<Menu> findAllList(@RequestBody Menu menu) {
		return menuService.findAllList(menu);
	}

	@RequestMapping(value = "findByUserId", method = RequestMethod.POST)
	public List<Menu> findByUserId(@RequestBody Menu menu) {
		return menuService.findByUserId(menu);
	}
}