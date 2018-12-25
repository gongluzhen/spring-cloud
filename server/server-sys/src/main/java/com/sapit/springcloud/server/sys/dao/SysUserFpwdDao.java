package com.sapit.springcloud.server.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.sapit.springcloud.common.server.mybatis.CrudDao;
import com.sapit.springcloud.common.server.mybatis.annotation.MyBatisDao;
import com.sapit.springcloud.moudle.sys.SysUserFpwd;

@MyBatisDao
public interface SysUserFpwdDao extends CrudDao<SysUserFpwd> {

	public SysUserFpwd getByParams(@Param("userId") String userId, @Param("tokenId") String tokenId);

	public void deleteByUserId(@Param("userId") String userId);
}