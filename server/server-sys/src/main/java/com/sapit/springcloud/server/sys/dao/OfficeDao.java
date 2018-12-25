package com.sapit.springcloud.server.sys.dao;

import com.sapit.springcloud.common.server.mybatis.TreeDao;
import com.sapit.springcloud.common.server.mybatis.annotation.MyBatisDao;
import com.sapit.springcloud.moudle.sys.Office;

/**
 * 机构DAO接口
 * @author sapit
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
}
