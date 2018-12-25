package com.sapit.springcloud.server.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapit.springcloud.moudle.sys.Area;
import com.sapit.springcloud.server.sys.service.AreaService;

@RestController
@RequestMapping(value = "area")
public class AreaController {
	@Autowired
	private AreaService areaService;

	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public Area getById(@RequestBody String id) {
		return areaService.get(id);
	}

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public Area get(@RequestBody Area entity) {
		return areaService.get(entity);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Area save(@RequestBody Area entity) {
		return areaService.save(entity);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody Area entity) {
		areaService.delete(entity);
	}

	@RequestMapping(value = "findAll", method = RequestMethod.POST)
	public List<Area> findAll() {
		return areaService.findAll();
	}

	@RequestMapping(value = "findAllList", method = RequestMethod.POST)
	public List<Area> findAllList(@RequestBody Area area) {
		return areaService.findAllList(area);
	}

	@RequestMapping(value = "findWholeCountryAndArea", method = RequestMethod.POST)
	public List<Area> findWholeCountryAndArea() {
		return areaService.findWholeCountryAndArea();
	}
}
