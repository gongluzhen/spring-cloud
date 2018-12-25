package com.sapit.springcloud.client.sys;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sapit.springcloud.common.client.FeignConfig;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysUserToken;

@Component
@FeignClient(value = "server-sys", path = "userToken", configuration = FeignConfig.class)
public interface SysUserTokenClient {
	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public SysUserToken getById(@RequestBody String id);

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<SysUserToken> findList(@RequestBody SysUserToken sysUserToken);

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	public Page<SysUserToken> findPage(@RequestBody SysUserToken sysUserToken);

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public SysUserToken save(@RequestBody SysUserToken sysUserToken);

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody SysUserToken sysUserToken);

	@RequestMapping(value = "findAllList", method = RequestMethod.POST)
	public List<SysUserToken> findAllList(@RequestBody SysUserToken sysUserToken);
}