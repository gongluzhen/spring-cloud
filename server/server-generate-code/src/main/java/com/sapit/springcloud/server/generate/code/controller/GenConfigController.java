package com.sapit.springcloud.server.generate.code.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapit.springcloud.moudle.generate.code.GenConfig;
import com.sapit.springcloud.server.generate.code.util.GenUtils;

@RestController
@RequestMapping(value = "genConfig")
public class GenConfigController {
	
	@RequestMapping(value = "get", method = RequestMethod.POST)
	public GenConfig get() {
		return GenUtils.getConfig();
	}
}
