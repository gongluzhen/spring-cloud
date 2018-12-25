package com.sapit.springcloud.server.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysFile;
import com.sapit.springcloud.server.sys.service.SysFileService;

/**
 * 系统-附件表Controller
 * 
 * @author gongluzhen
 * @version 2018-12-12
 */
@RestController
@RequestMapping(value = "sysFile")
public class SysFileController {
	@Autowired
	private SysFileService sysFileService;

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public SysFile getById(@RequestBody String id) {
		return sysFileService.get(id);
	}

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public SysFile get(@RequestBody SysFile sysFile) {
		return sysFileService.get(sysFile);
	}

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<SysFile> findList(@RequestBody SysFile SysFile) {
		return sysFileService.findList(SysFile);
	}

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	public Page<SysFile> findPage(@RequestBody SysFile sysFile) {
		return sysFileService.findPage(sysFile);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public SysFile save(@RequestBody SysFile sysFile) {
		return sysFileService.save(sysFile);
	}

	@RequestMapping(value = "updateBusinessKeyByIds", method = RequestMethod.POST)
	public void updateBusinessKeyByIds(@RequestParam(value = "businessKey", required = false) String businessKey,
			@RequestParam(value = "fileIds", required = false) String fileIds) {
		sysFileService.updateBusinessKeyByIds(businessKey, fileIds);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody SysFile sysFile) {
		sysFileService.delete(sysFile);
	}
}