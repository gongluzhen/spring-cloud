package com.sapit.springcloud.client.generate.code;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sapit.springcloud.common.client.FeignConfig;
import com.sapit.springcloud.moudle.generate.code.GenConfig;

@Component
@FeignClient(value = "server-generate-code", path = "genConfig", configuration = FeignConfig.class)
public interface GenConfigClient {
	@RequestMapping(value = "get", method = RequestMethod.POST)
	public GenConfig get();
}
