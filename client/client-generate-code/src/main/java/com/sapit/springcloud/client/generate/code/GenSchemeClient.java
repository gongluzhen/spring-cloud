package com.sapit.springcloud.client.generate.code;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sapit.springcloud.common.client.FeignConfig;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.generate.code.GenScheme;

@Component
@FeignClient(value = "server-generate-code", path = "genScheme", configuration = FeignConfig.class)
public interface GenSchemeClient {
	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public GenScheme getById(@RequestBody String id);

	@RequestMapping(value = "find", method = RequestMethod.POST)
	public Page<GenScheme> find(@RequestBody GenScheme genScheme);

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public GenScheme save(@RequestBody GenScheme genScheme);

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody GenScheme genScheme);
}
