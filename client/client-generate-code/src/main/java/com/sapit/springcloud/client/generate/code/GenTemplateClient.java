package com.sapit.springcloud.client.generate.code;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sapit.springcloud.common.client.FeignConfig;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.generate.code.GenTemplate;

@Component
@FeignClient(value = "server-generate-code", path = "genTemplate", configuration = FeignConfig.class)
public interface GenTemplateClient {
	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public GenTemplate getById(@RequestBody String id);

	@RequestMapping(value = "find", method = RequestMethod.POST)
	public Page<GenTemplate> find(@RequestBody GenTemplate genTemplate);

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public GenTemplate save(@RequestBody GenTemplate genTemplate);

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody GenTemplate genTemplate);

}
