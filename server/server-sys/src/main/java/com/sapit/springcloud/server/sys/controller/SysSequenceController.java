package com.sapit.springcloud.server.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.SysSequence;
import com.sapit.springcloud.server.sys.service.SysSequenceService;

@RestController
@RequestMapping(value = "sequence")
public class SysSequenceController {
	@Autowired
	private SysSequenceService sysSequenceService;

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public SysSequence getById(@RequestBody String id) {
		return sysSequenceService.get(id);
	}

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public SysSequence get(@RequestBody SysSequence entity) {
		return sysSequenceService.get(entity);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public SysSequence save(@RequestBody SysSequence entity) {
		return sysSequenceService.save(entity);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody SysSequence entity) {
		sysSequenceService.delete(entity);
	}

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<SysSequence> findList(@RequestBody SysSequence sysSequence) {
		return sysSequenceService.findList(sysSequence);
	}

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	public Page<SysSequence> findPage(@RequestBody SysSequence sysSequence) {
		return sysSequenceService.findPage(sysSequence);
	}

	@RequestMapping(value = "getSequenceNextValue", method = RequestMethod.POST)
	public String getSequenceNextValue(@RequestBody String seqName, @RequestParam("prefix") String prefix, @RequestParam("suffixNum") int suffixNum) {
		return sysSequenceService.getSequenceNextValue(seqName, prefix, suffixNum);
	}
}