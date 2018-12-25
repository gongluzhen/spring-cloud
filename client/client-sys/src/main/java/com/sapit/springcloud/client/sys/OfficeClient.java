package com.sapit.springcloud.client.sys;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sapit.springcloud.common.client.FeignConfig;
import com.sapit.springcloud.moudle.sys.Office;
import com.sapit.springcloud.moudle.sys.User;

@Component
@FeignClient(value = "server-sys", path = "office", configuration = FeignConfig.class)
public interface OfficeClient {
	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public Office getById(@RequestBody String id);

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public Office get(@RequestBody Office entity);

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Office save(@RequestBody Office entity);

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody Office entity);

	@RequestMapping(value = "findAll", method = RequestMethod.POST)
	public List<Office> findAll(@RequestBody User currentLoginUser);

	@RequestMapping(value = "findAllList", method = RequestMethod.POST)
	public List<Office> findAllList(@RequestBody Office office);

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<Office> findList(@RequestBody User currentLoginUser, @RequestParam(value = "isAll", required = false) Boolean isAll);

	@RequestMapping(value = "findByParentIdsLike", method = RequestMethod.POST)
	public List<Office> findByParentIdsLike(@RequestBody Office office);
}
