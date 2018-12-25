package com.sapit.springcloud.server.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysUserToken;
import com.sapit.springcloud.server.sys.service.SysUserTokenService;

@RestController
@RequestMapping(value = "userToken")
public class SysUserTokenController {
	@Autowired
	private SysUserTokenService sysUserTokenService;

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public SysUserToken getById(@RequestBody String id) {
		return sysUserTokenService.get(id);
	}

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<SysUserToken> findList(@RequestBody SysUserToken sysUserToken) {
		return sysUserTokenService.findList(sysUserToken);
	}

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	public Page<SysUserToken> findPage(@RequestBody SysUserToken sysUserToken) {
		return sysUserTokenService.findPage(sysUserToken);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public SysUserToken save(@RequestBody SysUserToken sysUserToken) {
		return sysUserTokenService.save(sysUserToken);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody SysUserToken sysUserToken) {
		sysUserTokenService.delete(sysUserToken);
	}

	@RequestMapping(value = "findAllList", method = RequestMethod.POST)
	public List<SysUserToken> findAllList(@RequestBody SysUserToken sysUserToken) {
		return sysUserTokenService.findAllList(sysUserToken);
	}
}