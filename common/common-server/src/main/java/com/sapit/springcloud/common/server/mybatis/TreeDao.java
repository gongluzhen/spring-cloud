/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/sapit/demo">demo</a> All rights reserved.
 */
package com.sapit.springcloud.common.server.mybatis;

import java.util.List;

import com.sapit.springcloud.moudle.TreeEntity;

/**
 * DAO支持类实现
 * @author sapit
 * @version 2014-05-16
 * @param <T>
 */
public interface TreeDao<T extends TreeEntity<T>> extends CrudDao<T> {

	/**
	 * 找到所有子节点
	 * @param entity
	 * @return
	 */
	public List<T> findByParentIdsLike(T entity);

	/**
	 * 更新所有父节点字段
	 * @param entity
	 * @return
	 */
	public int updateParentIds(T entity);
	
}