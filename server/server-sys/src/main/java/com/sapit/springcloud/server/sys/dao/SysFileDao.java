package com.sapit.springcloud.server.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.sapit.springcloud.common.server.mybatis.CrudDao;
import com.sapit.springcloud.common.server.mybatis.annotation.MyBatisDao;
import com.sapit.springcloud.moudle.sys.SysFile;

/**
 * 系统-附件表DAO接口
 * @author gongluzhen
 * @version 2018-12-12
 */
@MyBatisDao
public interface SysFileDao extends CrudDao<SysFile> {
	public void updateBusinessKeyByIds(@Param("businessKey") String businessKey, @Param("fileIds") String[] fileIds);
	public void deleteByBusinessKey(String businessKey);
}