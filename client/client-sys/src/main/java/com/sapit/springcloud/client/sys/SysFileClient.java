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
import com.sapit.springcloud.moudle.sys.SysFile;

/**
 * 系统-附件表Client
 * 
 * @author gongluzhen
 * @version 2018-12-12
 */

@Component
@FeignClient(value = "server-sys", path = "sysFile", configuration = FeignConfig.class)
public interface SysFileClient {

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public SysFile getById(@RequestBody String id);

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public SysFile get(@RequestBody SysFile sysFile);

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<SysFile> findList(@RequestBody SysFile sysFile);

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	public Page<SysFile> findPage(@RequestBody SysFile sysFile);

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public SysFile save(@RequestBody SysFile sysFile);

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody SysFile sysFile);

	@RequestMapping(value = "updateBusinessKeyByIds", method = RequestMethod.POST)
	public void updateBusinessKeyByIds(@RequestParam(value = "businessKey", required = false) String businessKey,
			@RequestParam(value = "fileIds", required = false) String fileIds);
}