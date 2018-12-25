package com.sapit.springcloud.client.sys;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sapit.springcloud.common.client.FeignConfig;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysUserFpwd;
import com.sapit.springcloud.moudle.sys.User;

@Component
@FeignClient(value = "server-sys", path = "userFpwd", configuration = FeignConfig.class)
public interface SysUserFpwdClient {
	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public SysUserFpwd getById(@RequestBody String id);

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<SysUserFpwd> findList(@RequestBody SysUserFpwd sysUserFpwd);

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	public Page<SysUserFpwd> findPage(@RequestBody SysUserFpwd sysUserFpwd);

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public SysUserFpwd save(@RequestBody SysUserFpwd sysUserFpwd);

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody SysUserFpwd sysUserFpwd);

	@RequestMapping(value = "checkToken", method = RequestMethod.POST)
	public SysUserFpwd checkToken(@RequestBody String tokenId);

	@RequestMapping(value = "saveByUserAndTokenId", method = RequestMethod.POST)
	public void saveByUserAndTokenId(@RequestBody User user, @RequestParam("tokenId") String tokenId);

	@RequestMapping(value = "deleteByUserId", method = RequestMethod.POST)
	public void deleteByUserId(@RequestBody User user);
}