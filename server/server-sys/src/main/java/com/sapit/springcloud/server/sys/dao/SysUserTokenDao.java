package com.sapit.springcloud.server.sys.dao;

import com.sapit.springcloud.common.server.mybatis.CrudDao;
import com.sapit.springcloud.common.server.mybatis.annotation.MyBatisDao;
import com.sapit.springcloud.moudle.sys.SysUserToken;

@MyBatisDao
public interface SysUserTokenDao extends CrudDao<SysUserToken> {

	/**
	 * 通过tokenId进行物理删除
	 *
	 * @param sysUserToken
	 *            SysUserToken
	 * @author YJH
	 */
	void realDelete(SysUserToken sysUserToken);

}