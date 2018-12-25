package com.sapit.springcloud.server.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapit.springcloud.common.redis.CacheUtils;
import com.sapit.springcloud.common.server.service.CrudService;
import com.sapit.springcloud.common.util.Constants;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.Dict;
import com.sapit.springcloud.server.sys.dao.DictDao;

@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {
	public Dict get(String id) {
		return super.get(id);
	}

	public Dict get(Dict entity) {
		return dao.get(entity);
	}

	public List<Dict> findList(Dict dict) {
		return super.findList(dict);
	}

	public Page<Dict> findPage(Dict dict) {
		return super.findPage(dict);
	}

	@Transactional(readOnly = false)
	public Dict save(Dict dict) {
		CacheUtils.remove(Constants.CACHE_DICT_MAP);
		return super.save(dict);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		CacheUtils.remove(Constants.CACHE_DICT_MAP);
		super.delete(dict);
	}
	

	/**
	 * 查询字段类型列表
	 *
	 * @return
	 */
	public List<String> findTypeList() {
		return dao.findTypeList(new Dict());
	}

	/**
	 * 从字典表中获取XXX类型中的排序的最大值
	 *
	 * @param dictType
	 *            类型
	 * @return 排序最大值
	 * @author YJH
	 */
	public Integer findMaxSort(String dictType) {
		return dao.findMaxSort(dictType);
	}

	public List<Dict> findAllList(Dict dict) {
		return dao.findAllList(dict);
	}

}
