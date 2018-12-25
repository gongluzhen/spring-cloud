package com.sapit.springcloud.server.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysUserFpwd;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.server.sys.service.SysUserFpwdService;

@RestController
@RequestMapping(value = "userFpwd")
public class SysUserFpwdController {
	@Autowired
	private SysUserFpwdService sysUserFpwdService;

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public SysUserFpwd getById(@RequestBody String id) {
		return sysUserFpwdService.get(id);
	}

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<SysUserFpwd> findList(@RequestBody SysUserFpwd sysUserFpwd) {
		return sysUserFpwdService.findList(sysUserFpwd);
	}

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	public Page<SysUserFpwd> findPage(@RequestBody SysUserFpwd sysUserFpwd) {
		return sysUserFpwdService.findPage(sysUserFpwd);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public SysUserFpwd save(@RequestBody SysUserFpwd sysUserFpwd) {
		return sysUserFpwdService.save(sysUserFpwd);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody SysUserFpwd sysUserFpwd) {
		sysUserFpwdService.delete(sysUserFpwd);
	}

	@RequestMapping(value = "checkToken", method = RequestMethod.POST)
	public SysUserFpwd checkToken(@RequestBody String tokenId) {
		return sysUserFpwdService.checkToken(tokenId);
	}

	@RequestMapping(value = "saveByUserAndTokenId", method = RequestMethod.POST)
	public void saveByUserAndTokenId(@RequestBody User user, @RequestParam("tokenId") String tokenId) {
		sysUserFpwdService.save(user, tokenId);
	}

	@RequestMapping(value = "deleteByUserId", method = RequestMethod.POST)
	public void deleteByUserId(@RequestBody User user) {
		sysUserFpwdService.deleteByUserId(user);
	}
}