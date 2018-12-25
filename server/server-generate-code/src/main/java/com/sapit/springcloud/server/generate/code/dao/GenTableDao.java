package com.sapit.springcloud.server.generate.code.dao;

import com.sapit.springcloud.common.server.mybatis.CrudDao;
import com.sapit.springcloud.common.server.mybatis.annotation.MyBatisDao;
import com.sapit.springcloud.moudle.generate.code.GenTable;

@MyBatisDao
public interface GenTableDao extends CrudDao<GenTable> {
	
}
