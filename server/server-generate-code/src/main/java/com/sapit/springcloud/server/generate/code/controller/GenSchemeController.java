package com.sapit.springcloud.server.generate.code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.generate.code.GenScheme;
import com.sapit.springcloud.server.generate.code.service.GenSchemeService;

@RestController
@RequestMapping(value = "genScheme")
public class GenSchemeController {
	@Autowired
	private GenSchemeService genSchemeService;

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public GenScheme getById(@RequestBody String id) {
		return genSchemeService.get(id);
	}

	@RequestMapping(value = "find", method = RequestMethod.POST)
	public Page<GenScheme> find(@RequestBody GenScheme genScheme) {
		return genSchemeService.find(genScheme);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public GenScheme save(@RequestBody GenScheme genScheme) {
		return genSchemeService.save(genScheme);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody GenScheme genScheme) {
		genSchemeService.delete(genScheme);
	}
}
