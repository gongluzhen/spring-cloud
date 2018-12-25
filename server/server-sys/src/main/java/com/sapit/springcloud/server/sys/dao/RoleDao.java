package com.sapit.springcloud.server.sys.dao;

import com.sapit.springcloud.common.server.mybatis.CrudDao;
import com.sapit.springcloud.common.server.mybatis.annotation.MyBatisDao;
import com.sapit.springcloud.moudle.sys.Role;

@MyBatisDao
public interface RoleDao extends CrudDao<Role> {

	public Role getByName(Role role);

	public Role getByEnname(Role role);

	/**
	 * 维护角色与菜单权限关系
	 * 
	 * @param role
	 * @return
	 */
	public int deleteRoleMenu(Role role);

	public int insertRoleMenu(Role role);

	/**
	 * 维护角色与公司部门关系
	 * 
	 * @param role
	 * @return
	 */
	public int deleteRoleOffice(Role role);

	public int insertRoleOffice(Role role);

}
