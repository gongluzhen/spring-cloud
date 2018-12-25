package com.sapit.springcloud.server.sys.dao;

import com.sapit.springcloud.common.server.mybatis.CrudDao;
import com.sapit.springcloud.common.server.mybatis.annotation.MyBatisDao;
import com.sapit.springcloud.moudle.sys.Log;

@MyBatisDao
public interface LogDao extends CrudDao<Log> {

}
