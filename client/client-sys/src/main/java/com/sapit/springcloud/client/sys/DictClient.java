package com.sapit.springcloud.client.sys;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sapit.springcloud.common.client.FeignConfig;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.Dict;

@Component
@FeignClient(value = "server-sys", path = "dict", configuration = FeignConfig.class)
public interface DictClient {

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public Dict getById(@RequestBody String id);

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public Dict get(@RequestBody Dict entity);

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<Dict> findList(@RequestBody Dict dict);

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	public Page<Dict> findPage(@RequestBody Dict entity);

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Dict save(@RequestBody Dict entity);

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody Dict entity);

	@RequestMapping(value = "findTypeList", method = RequestMethod.POST)
	public List<String> findTypeList();

	@RequestMapping(value = "findMaxSort", method = RequestMethod.POST)
	public Integer findMaxSort(@RequestBody String dictType);

	@RequestMapping(value = "findAllList", method = RequestMethod.POST)
	public List<Dict> findAllList(@RequestBody Dict dict);
}
