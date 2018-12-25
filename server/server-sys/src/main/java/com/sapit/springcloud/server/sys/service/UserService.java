package com.sapit.springcloud.server.sys.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sapit.springcloud.common.server.service.CrudService;
import com.sapit.springcloud.common.util.BeanUtils;
import com.sapit.springcloud.common.util.Constants;
import com.sapit.springcloud.common.util.Digests;
import com.sapit.springcloud.common.util.Encodes;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.Office;
import com.sapit.springcloud.moudle.sys.Role;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.moudle.util.DataScopeFilter;
import com.sapit.springcloud.server.sys.dao.UserDao;

@Service
@Transactional(readOnly = true)
public class UserService extends CrudService<UserDao, User> {
	@Autowired
	private RoleService roleService;
	@Autowired
	private SysFileService sysFileService;

	/**
	 * 获取用户
	 *
	 * @param id
	 * @return
	 */
	public User get(String id) {
		User user = super.dao.get(id);
		user.setRoleList(roleService.findList(new Role(user)));
		return user;
	}

	/**
	 * 根据登录名获取用户
	 *
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(String loginName) {
		User user = super.dao.getByLoginName(new User(null, loginName));
		if (user == null) {
			return null;
		}
		user.setRoleList(roleService.findList(new Role(user)));
		return user;
	}

	public List<User> findAllList(User user) {
		List<User> userList = super.dao.findAllList(user);
		if (null == userList || userList.size() == 0) {
			userList = null;
		}
		return userList;
	}

	public Page<User> findPage(User user) {
		user.getSqlMap().put("dsf", DataScopeFilter.dataScopeFilter(user.getCurrentLoginUser(), "o", "a"));
		user.getPage().setList(super.dao.findList(user));
		return user.getPage();
	}

	/**
	 * 无分页查询人员列表
	 *
	 * @param user
	 * @return
	 */
	public List<User> find(User user) {
		user.getSqlMap().put("dsf", DataScopeFilter.dataScopeFilter(user.getCurrentLoginUser(), "o", "a"));
		List<User> list = super.dao.findList(user);
		return list;
	}

	/**
	 * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
	 *
	 * @param officeId
	 * @return
	 */
	public List<User> findByOfficeId(String officeId) {
		User user = new User();
		user.setOffice(new Office(officeId));
		List<User> list = super.dao.findUserByOfficeId(user);
		return list;
	}

	@Transactional(readOnly = false)
	public User save(User user) {
		if (StringUtils.isBlank(user.getId())) {
			user.preInsert();

			super.dao.insert(user);
		} else {
			User userOld = super.dao.get(user.getId());
			BeanUtils.copyPropertiesIgnoreNull(user, userOld);
			userOld.preUpdate();

			super.dao.update(userOld);
		}

		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();

		if (!CollectionUtils.isEmpty(roleIdList)) {
			for (Role r : roleService.findAll(user.getCurrentLoginUser())) {
				if (roleIdList.contains(r.getId())) {
					roleList.add(r);
				}
			}
			user.setRoleList(roleList);
		}

		if (!CollectionUtils.isEmpty(user.getRoleList())) {
			super.dao.deleteUserRole(user);
			super.dao.insertUserRole(user);
		}
		
		return user;
	}

	@Transactional(readOnly = false)
	public void updateUserInfo(User user) {
		user.preUpdate();
		super.dao.updateUserInfo(user);
		sysFileService.updateBusinessKeyByIds(user.getId(), user.getFileIds());
	}

	@Transactional(readOnly = false)
	public void updatePasswordById(String id, String loginName, String newPassword) {
		User user = new User(id);
		user.setPassword(entryptPassword(newPassword));
		user.setUpdatePasswdDate(new Date());
		super.dao.updatePasswordById(user);
	}

	@Transactional(readOnly = false)
	public void updateUserLoginInfo(User user, String loginIp) {
		// 保存上次登录信息
		user.setOldLoginIp(user.getLoginIp());
		user.setOldLoginDate(user.getLoginDate());
		// 更新本次登录信息
		user.setLoginIp(loginIp);
		user.setLoginDate(new Date());
		super.dao.updateLoginInfo(user);
	}

	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public String entryptPassword(String plainPassword) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Digests.generateSalt(Constants.SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, Constants.HASH_INTERATIONS);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}

	/**
	 * 验证密码
	 *
	 * @param plainPassword
	 *            明文密码
	 * @param password
	 *            密文密码
	 * @return 验证成功返回true
	 */
	public boolean validatePassword(String plainPassword, String password) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Encodes.decodeHex(password.substring(0, 16));
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, Constants.HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
	}

	public void deleteUserRoleByRoleId(String roleId) {
		super.dao.deleteUserRoleByRoleId(roleId);
	}

	public void deleteUserRoleByRoleIdUserId(String roleId, String userId) {
		super.dao.deleteUserRoleByRoleIdUserId(roleId, userId);
	}

	public void insertUserRoleByRoleIdUserId(String roleId, String userId) {
		super.dao.insertUserRoleByRoleIdUserId(roleId, userId);
	}

	public List<User> findUserByLoginNameAndEmail(User user) {
		return super.dao.findUserByLoginNameAndEmail(user);
	}

	@Transactional(readOnly = false)
	public void deleteAllUserRoleByRoleId(Role role) {
		super.dao.deleteUserRoleByRoleId(role.getId());
	}
}