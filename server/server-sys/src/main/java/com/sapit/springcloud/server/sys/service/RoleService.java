package com.sapit.springcloud.server.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapit.springcloud.common.redis.CacheUtils;
import com.sapit.springcloud.common.server.service.CrudService;
import com.sapit.springcloud.common.util.Constants;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.sys.Role;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.moudle.util.DataScopeFilter;
import com.sapit.springcloud.server.sys.dao.RoleDao;

@Service
@Transactional(readOnly = true)
public class RoleService extends CrudService<RoleDao, Role> {
	@Autowired
	private UserService userService;

	public Role get(String id) {
		return super.dao.get(id);
	}

	public Role getByName(String name) {
		Role r = new Role();
		r.setName(name);
		return super.dao.getByName(r);
	}

	public Role getByEnname(String enname) {
		Role r = new Role();
		r.setEnname(enname);
		return super.dao.getByEnname(r);
	}

	public List<Role> findList(Role role) {
		return super.dao.findList(role);
	}

	public List<Role> findAllList(Role role) {
		return super.dao.findAllList(role);
	}

	@Transactional(readOnly = false)
	public Role save(Role role) {
		if (StringUtils.isBlank(role.getId())) {
			role.preInsert();
			super.dao.insert(role);
		} else {
			role.preUpdate();
			super.dao.update(role);
		}

		// 更新角色与用户关联
		userService.deleteUserRoleByRoleIdUserId(role.getId(), role.getCurrentLoginUser().getId());
		userService.insertUserRoleByRoleIdUserId(role.getId(), role.getCurrentLoginUser().getId());

		// 更新角色与菜单关联
		super.dao.deleteRoleMenu(role);
		if (role.getMenuList().size() > 0) {
			super.dao.insertRoleMenu(role);
		}
		// 更新角色与部门关联
		super.dao.deleteRoleOffice(role);
		if (role.getOfficeList().size() > 0) {
			super.dao.insertRoleOffice(role);
		}
		// 清除用户角色缓存
		CacheUtils.remove(Constants.CACHE_ROLE_LIST);
		
		return role;
	}

	@Transactional(readOnly = false)
	public void delete(Role role) {
		super.dao.delete(role);
		CacheUtils.remove(Constants.CACHE_ROLE_LIST);

	}

	@Transactional(readOnly = false)
	public Boolean outUserInRole(Role role, String userId) {
		User user = userService.get(userId);
		user.setCurrentLoginUser(role.getCurrentLoginUser());
		
		List<Role> roles = user.getRoleList();
		for (Role e : roles) {
			if (e.getId().equals(role.getId())) {
				roles.remove(e);
				userService.save(user);
				return true;
			}
		}
		return false;
	}

	@Transactional(readOnly = false)
	public User assignUserToRole(Role role, String userId) {
		User user = userService.get(userId);
		user.setCurrentLoginUser(role.getCurrentLoginUser());
		
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getRoleList().add(role);
		userService.save(user);
		return user;
	}

	public List<Role> findAll(User currentLoginUser) {
		@SuppressWarnings("unchecked")
		List<Role> roleList = (List<Role>) CacheUtils.get(Constants.CACHE_ROLE_LIST);
		if (roleList == null) {
			if (currentLoginUser.isAdmin()) {
				roleList = super.dao.findAllList(new Role());
			} else {
				Role role = new Role();
				role.getSqlMap().put("dsf", DataScopeFilter.dataScopeFilter(currentLoginUser, "o", "u"));
				roleList = super.dao.findList(role);
			}
			CacheUtils.put(Constants.CACHE_ROLE_LIST, roleList);
		}
		return roleList;
	}
}