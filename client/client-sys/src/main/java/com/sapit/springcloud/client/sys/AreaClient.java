package com.sapit.springcloud.client.sys;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sapit.springcloud.common.client.FeignConfig;
import com.sapit.springcloud.moudle.sys.Area;

@Component
@FeignClient(value = "server-sys", path = "area", configuration = FeignConfig.class)
public interface AreaClient {
	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public Area getById(@RequestBody String id);

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public Area get(@RequestBody Area entity);

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Area save(@RequestBody Area entity);

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody Area entity);

	@RequestMapping(value = "findAll", method = RequestMethod.POST)
	public List<Area> findAll();

	@RequestMapping(value = "findAllList", method = RequestMethod.POST)
	public List<Area> findAllList(@RequestBody Area area);

	@RequestMapping(value = "findWholeCountryAndArea", method = RequestMethod.POST)
	public List<Area> findWholeCountryAndArea();
}
