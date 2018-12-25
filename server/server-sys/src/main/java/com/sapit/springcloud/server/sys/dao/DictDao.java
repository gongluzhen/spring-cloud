package com.sapit.springcloud.server.sys.dao;

import java.util.List;

import com.sapit.springcloud.common.server.mybatis.CrudDao;
import com.sapit.springcloud.common.server.mybatis.annotation.MyBatisDao;
import com.sapit.springcloud.moudle.sys.Dict;

@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);

	public Dict getByTypeVal(Dict dict);

	public List<Dict> getByType(Dict dict);

	/**
	 * 从字典表中获取XXX类型中的排序的最大值
	 *
	 * @param dictType
	 *            类型
	 * @return 排序最大值
	 * @author YJH
	 */
	Integer findMaxSort(String dictType);
}
