package com.sapit.springcloud.client.sys;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sapit.springcloud.common.client.FeignConfig;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysWxAccessToken;

@Component
@FeignClient(value = "server-sys", path = "wxAccessToken", configuration = FeignConfig.class)
public interface SysWxAccessTokenClient {
	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public SysWxAccessToken getById(@RequestBody String id);

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<SysWxAccessToken> findList(@RequestBody SysWxAccessToken sysWxAccessToken);

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	public Page<SysWxAccessToken> findPage(@RequestBody SysWxAccessToken sysWxAccessToken);

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public SysWxAccessToken save(@RequestBody SysWxAccessToken sysWxAccessToken);

	@RequestMapping(value = "getAccessTokenAndJSSDKTicket", method = RequestMethod.POST)
	public SysWxAccessToken getAccessTokenAndJSSDKTicket();

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody SysWxAccessToken sysWxAccessToken);

}