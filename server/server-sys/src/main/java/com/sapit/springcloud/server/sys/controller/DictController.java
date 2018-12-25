package com.sapit.springcloud.server.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.Dict;
import com.sapit.springcloud.server.sys.service.DictService;

@RestController
@RequestMapping(value = "dict")
public class DictController {
	@Autowired
	private DictService dictService;

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public Dict getById(@RequestBody String id) {
		return dictService.get(id);
	}

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public Dict get(@RequestBody Dict entity) {
		return dictService.get(entity);
	}

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<Dict> findList(@RequestBody Dict dict) {
		return dictService.findList(dict);
	}

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	public Page<Dict> findPage(@RequestBody Dict entity) {
		return dictService.findPage(entity);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Dict save(@RequestBody Dict entity) {
		return dictService.save(entity);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody Dict entity) {
		dictService.delete(entity);
	}

	@RequestMapping(value = "findTypeList", method = RequestMethod.POST)
	public List<String> findTypeList() {
		return dictService.findTypeList();
	}

	@RequestMapping(value = "findMaxSort", method = RequestMethod.POST)
	public Integer findMaxSort(@RequestBody String dictType) {
		return dictService.findMaxSort(dictType);
	}

	@RequestMapping(value = "findAllList", method = RequestMethod.POST)
	public List<Dict> findAllList(@RequestBody Dict dict) {
		return dictService.findAllList(dict);
	}

}
