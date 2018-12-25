package com.sapit.springcloud.client.generate.code;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sapit.springcloud.common.client.FeignConfig;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.generate.code.GenTable;

@Component
@FeignClient(value = "server-generate-code", path = "genTable", configuration = FeignConfig.class)
public interface GenTableClient {
	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public GenTable getById(@RequestBody String id);

	@RequestMapping(value = "find", method = RequestMethod.POST)
	public Page<GenTable> find(@RequestBody GenTable genTable);

	@RequestMapping(value = "findAll", method = RequestMethod.POST)
	public List<GenTable> findAll();

	@RequestMapping(value = "findTableListFormDb", method = RequestMethod.POST)
	public List<GenTable> findTableListFormDb(@RequestBody GenTable genTable);

	@RequestMapping(value = "checkTableName", method = RequestMethod.POST)
	public boolean checkTableName(@RequestParam(value = "tableName", required = false) String tableName);

	@RequestMapping(value = "getTableFormDb", method = RequestMethod.POST)
	public GenTable getTableFormDb(@RequestBody GenTable genTable);

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public GenTable save(@RequestBody GenTable genTable);

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody GenTable genTable);

}
