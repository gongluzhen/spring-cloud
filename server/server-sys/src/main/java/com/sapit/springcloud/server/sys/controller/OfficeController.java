package com.sapit.springcloud.server.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapit.springcloud.moudle.sys.Office;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.server.sys.service.OfficeService;

@RestController
@RequestMapping(value = "office")
public class OfficeController {
	@Autowired
	private OfficeService officeService;

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public Office getById(@RequestBody String id) {
		return officeService.get(id);
	}

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public Office get(@RequestBody Office entity) {
		return officeService.get(entity);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Office save(@RequestBody Office entity) {
		return officeService.save(entity);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody Office entity) {
		officeService.delete(entity);
	}

	@RequestMapping(value = "findAll", method = RequestMethod.POST)
	public List<Office> findAll(@RequestBody User currentLoginUser) {
		return officeService.findAll(currentLoginUser);
	}

	@RequestMapping(value = "findAllList", method = RequestMethod.POST)
	public List<Office> findAllList(@RequestBody Office office) {
		return officeService.findAllList(office);
	}

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<Office> findList(@RequestBody User currentLoginUser, @RequestParam(value = "isAll", required = false) Boolean isAll) {
		return officeService.findList(currentLoginUser, isAll);
	}

	@RequestMapping(value = "findByParentIdsLike", method = RequestMethod.POST)
	public List<Office> findByParentIdsLike(@RequestBody Office office) {
		return officeService.findByParentIdsLike(office);
	}
}
