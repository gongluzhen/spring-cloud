package com.sapit.springcloud.server.generate.code.dao;

import java.util.List;

import com.sapit.springcloud.common.server.mybatis.CrudDao;
import com.sapit.springcloud.common.server.mybatis.annotation.MyBatisDao;
import com.sapit.springcloud.moudle.generate.code.GenTable;
import com.sapit.springcloud.moudle.generate.code.GenTableColumn;

@MyBatisDao
public interface GenDataBaseDictDao extends CrudDao<GenTableColumn> {

	/**
	 * 查询表列表
	 * @param genTable
	 * @return
	 */
	List<GenTable> findTableList(GenTable genTable);

	/**
	 * 获取数据表字段
	 * @param genTable
	 * @return
	 */
	List<GenTableColumn> findTableColumnList(GenTable genTable);
	
	/**
	 * 获取数据表主键
	 * @param genTable
	 * @return
	 */
	List<String> findTablePK(GenTable genTable);
	
}
