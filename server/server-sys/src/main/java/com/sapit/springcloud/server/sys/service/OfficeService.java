package com.sapit.springcloud.server.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapit.springcloud.common.redis.CacheUtils;
import com.sapit.springcloud.common.server.service.TreeService;
import com.sapit.springcloud.common.util.Constants;
import com.sapit.springcloud.moudle.sys.Office;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.moudle.util.DataScopeFilter;
import com.sapit.springcloud.server.sys.dao.OfficeDao;

@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	public List<Office> findAll(User currentLoginUser) {
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>) CacheUtils.get(Constants.CACHE_OFFICE_LIST);
		if (officeList == null) {
			if (currentLoginUser.isAdmin()) {
				officeList = super.dao.findAllList(new Office());
			} else {
				Office office = new Office();
				office.getSqlMap().put("dsf", DataScopeFilter.dataScopeFilter(currentLoginUser, "a", ""));
				officeList = super.dao.findList(office);
			}
			CacheUtils.put(Constants.CACHE_OFFICE_LIST, officeList);
		}
		return officeList;
	}

	public List<Office> findAllList(Office office) {
		return dao.findAllList(office);

	}

	public List<Office> findList(User currentLoginUser, Boolean isAll) {
		if (isAll != null && isAll) {
			@SuppressWarnings("unchecked")
			List<Office> officeList = (List<Office>) CacheUtils.get(Constants.CACHE_OFFICE_ALL_LIST);
			if (officeList == null) {
				officeList = super.dao.findAllList(new Office());
			}
			return officeList;
		} else {
			return this.findAll(currentLoginUser);
		}
	}

	@Transactional(readOnly = true)
	public List<Office> findByParentIdsLike(Office office) {
		if (office != null) {
			return dao.findByParentIdsLike(office);
		}
		return new ArrayList<Office>();
	}

	@Transactional(readOnly = false)
	public Office save(Office office) {
		CacheUtils.remove(Constants.CACHE_OFFICE_LIST);
		return super.save(office);
	}

	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		CacheUtils.remove(Constants.CACHE_OFFICE_LIST);
	}

}
