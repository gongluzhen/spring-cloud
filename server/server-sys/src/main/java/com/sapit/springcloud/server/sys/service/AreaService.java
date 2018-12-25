package com.sapit.springcloud.server.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.sapit.springcloud.common.redis.CacheUtils;
import com.sapit.springcloud.common.server.service.TreeService;
import com.sapit.springcloud.common.util.Constants;
import com.sapit.springcloud.moudle.sys.Area;
import com.sapit.springcloud.server.sys.dao.AreaDao;

@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {

	public List<Area> findAll() {
		@SuppressWarnings("unchecked")
		List<Area> areaList = (List<Area>) CacheUtils.get(Constants.CACHE_AREA_LIST);
		if (areaList == null) {
			areaList = super.dao.findAllList(new Area());
			CacheUtils.put(Constants.CACHE_AREA_LIST, areaList);
		}
		return areaList;
	}

	public List<Area> findAllList(Area area) {
		List<Area> list = dao.findAllList(area);
		for (Area a : list) {
			a.setHasChild(isHasChild(a));
		}
		return list;
	}

	private String isHasChild(Area area) {
		Area areaParam = new Area();
		areaParam.setParent(area);

		List<Area> list = dao.findAllList(areaParam);
		if (CollectionUtils.isEmpty(list)) {
			return "";
		}
		return "hasChild='true'";
	}

	@Transactional(readOnly = false)
	public Area save(Area area) {
		CacheUtils.remove(Constants.CACHE_AREA_LIST);
		return super.save(area);
	}

	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		CacheUtils.remove(Constants.CACHE_AREA_LIST);
	}

	/**
	 * 查找全国和大区
	 *
	 * @return List<Area>
	 * @author YJH
	 */
	public List<Area> findWholeCountryAndArea() {
		return dao.findWholeCountryAndArea();
	}
}
