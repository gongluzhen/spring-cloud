package com.sapit.springcloud.server.generate.code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.generate.code.GenTemplate;
import com.sapit.springcloud.server.generate.code.service.GenTemplateService;

@RestController
@RequestMapping(value = "genTemplate")
public class GenTemplateController {
	@Autowired
	private GenTemplateService genTemplateService;

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public GenTemplate getById(@RequestBody String id) {
		return genTemplateService.get(id);
	}

	@RequestMapping(value = "find", method = RequestMethod.POST)
	public Page<GenTemplate> find(@RequestBody GenTemplate genTemplate) {
		return genTemplateService.find(genTemplate);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public GenTemplate save(@RequestBody GenTemplate genTemplate) {
		return genTemplateService.save(genTemplate);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody GenTemplate genTemplate) {
		genTemplateService.delete(genTemplate);
	}

}
