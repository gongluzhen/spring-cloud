package com.sapit.springcloud.server.sys.dao;

import java.util.Map;

import com.sapit.springcloud.common.server.mybatis.CrudDao;
import com.sapit.springcloud.common.server.mybatis.annotation.MyBatisDao;
import com.sapit.springcloud.moudle.sys.SysSequence;

@MyBatisDao
public interface SysSequenceDao extends CrudDao<SysSequence> {
	public void getSequenceNextValue(Map<String, Object> param);
}