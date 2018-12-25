package com.sapit.springcloud.server.sys.dao;

import java.util.List;

import com.sapit.springcloud.common.server.mybatis.TreeDao;
import com.sapit.springcloud.common.server.mybatis.annotation.MyBatisDao;
import com.sapit.springcloud.moudle.sys.Area;

@MyBatisDao
public interface AreaDao extends TreeDao<Area> {

	/**
	 * 查找全国和大区
	 *
	 * @return List<Area>
	 * @author YJH
	 */
	List<Area> findWholeCountryAndArea();
}
