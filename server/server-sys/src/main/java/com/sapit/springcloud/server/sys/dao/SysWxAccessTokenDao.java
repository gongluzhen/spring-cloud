package com.sapit.springcloud.server.sys.dao;

import com.sapit.springcloud.common.server.mybatis.CrudDao;
import com.sapit.springcloud.common.server.mybatis.annotation.MyBatisDao;
import com.sapit.springcloud.moudle.sys.SysWxAccessToken;

@MyBatisDao
public interface SysWxAccessTokenDao extends CrudDao<SysWxAccessToken> {
	public SysWxAccessToken getByAppId(String appId);
}