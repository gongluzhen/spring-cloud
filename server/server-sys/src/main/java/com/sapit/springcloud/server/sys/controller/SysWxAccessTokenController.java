package com.sapit.springcloud.server.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysWxAccessToken;
import com.sapit.springcloud.server.sys.service.SysWxAccessTokenService;

@RestController
@RequestMapping(value = "wxAccessToken")
public class SysWxAccessTokenController {
	@Autowired
	private SysWxAccessTokenService sysWxAccessTokenService;

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public SysWxAccessToken getById(@RequestBody String id) {
		return sysWxAccessTokenService.get(id);
	}

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<SysWxAccessToken> findList(@RequestBody SysWxAccessToken sysWxAccessToken) {
		return sysWxAccessTokenService.findList(sysWxAccessToken);
	}

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	public Page<SysWxAccessToken> findPage(@RequestBody SysWxAccessToken sysWxAccessToken) {
		return sysWxAccessTokenService.findPage(sysWxAccessToken);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public SysWxAccessToken save(@RequestBody SysWxAccessToken sysWxAccessToken) {
		return sysWxAccessTokenService.save(sysWxAccessToken);
	}

	@RequestMapping(value = "getAccessTokenAndJSSDKTicket", method = RequestMethod.POST)
	public SysWxAccessToken getAccessTokenAndJSSDKTicket() {
		return sysWxAccessTokenService.getAccessTokenAndJSSDKTicket();
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody SysWxAccessToken sysWxAccessToken) {
		sysWxAccessTokenService.delete(sysWxAccessToken);
	}

}