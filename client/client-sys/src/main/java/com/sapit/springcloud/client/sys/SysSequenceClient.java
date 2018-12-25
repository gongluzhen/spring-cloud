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
import com.sapit.springcloud.moudle.sys.SysSequence;

@Component
@FeignClient(value = "server-sys", path = "sequence", configuration = FeignConfig.class)
public interface SysSequenceClient {
	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public SysSequence getById(@RequestBody String id);

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public SysSequence get(@RequestBody SysSequence entity);

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public SysSequence save(@RequestBody SysSequence entity);

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody SysSequence entity);

	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public List<SysSequence> findList(@RequestBody SysSequence sysSequence);

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	public Page<SysSequence> findPage(@RequestBody SysSequence sysSequence);

	@RequestMapping(value = "getSequenceNextValue", method = RequestMethod.POST)
	public String getSequenceNextValue(@RequestBody String seqName, @RequestParam("prefix") String prefix, @RequestParam("suffixNum") int suffixNum);
}