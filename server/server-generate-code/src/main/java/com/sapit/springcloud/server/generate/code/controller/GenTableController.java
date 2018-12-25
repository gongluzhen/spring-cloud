package com.sapit.springcloud.server.generate.code.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.generate.code.GenTable;
import com.sapit.springcloud.server.generate.code.service.GenTableService;

@RestController
@RequestMapping(value = "genTable")
public class GenTableController {
	@Autowired
	private GenTableService genTableService;

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public GenTable getById(@RequestBody String id) {
		return genTableService.get(id);
	}

	@RequestMapping(value = "find", method = RequestMethod.POST)
	public Page<GenTable> find(@RequestBody GenTable genTable) {
		return genTableService.find(genTable);
	}

	@RequestMapping(value = "findAll", method = RequestMethod.POST)
	public List<GenTable> findAll() {
		return genTableService.findAll();
	}

	@RequestMapping(value = "findTableListFormDb", method = RequestMethod.POST)
	public List<GenTable> findTableListFormDb(@RequestBody GenTable genTable) {
		return genTableService.findTableListFormDb(genTable);
	}

	@RequestMapping(value = "checkTableName", method = RequestMethod.POST)
	public boolean checkTableName(@RequestParam(value = "tableName", required = false) String tableName) {
		return genTableService.checkTableName(tableName);
	}

	@RequestMapping(value = "getTableFormDb", method = RequestMethod.POST)
	public GenTable getTableFormDb(@RequestBody GenTable genTable) {
		return genTableService.getTableFormDb(genTable);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public GenTable save(@RequestBody GenTable genTable) {
		return genTableService.save(genTable);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody GenTable genTable) {
		genTableService.delete(genTable);
	}

}
