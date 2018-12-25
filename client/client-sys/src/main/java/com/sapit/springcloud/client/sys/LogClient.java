package com.sapit.springcloud.client.sys;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sapit.springcloud.common.client.FeignConfig;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.Log;

@Component
@FeignClient(value = "server-sys", path = "log", configuration = FeignConfig.class)
public interface LogClient {
	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public Log getById(@RequestBody String id);

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public Log get(@RequestBody Log entity);

	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public Log insert(@RequestBody Log entity);

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody Log entity);

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	public Page<Log> findPage(@RequestBody Log log);

}
