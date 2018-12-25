package com.sapit.springcloud.server.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.Log;
import com.sapit.springcloud.server.sys.service.LogService;

@RestController
@RequestMapping(value = "log")
public class LogController {
	@Autowired
	private LogService logService;

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public Log getById(@RequestBody String id) {
		return logService.get(id);
	}

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public Log get(@RequestBody Log entity) {
		return logService.get(entity);
	}

	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public Log insert(@RequestBody Log entity) {
		return logService.insert(entity);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody Log entity) {
		logService.delete(entity);
	}

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	public Page<Log> findPage(@RequestBody Log log) {
		return logService.findPage(log);
	}

}
