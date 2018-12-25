package com.sapit.springcloud.web.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.util.CollectionUtils;

import com.sapit.springcloud.client.sys.AreaClient;
import com.sapit.springcloud.client.sys.MenuClient;
import com.sapit.springcloud.client.sys.OfficeClient;
import com.sapit.springcloud.client.sys.RoleClient;
import com.sapit.springcloud.client.sys.SysUserTokenClient;
import com.sapit.springcloud.client.sys.UserClient;
import com.sapit.springcloud.common.util.SpringContextHolder;
import com.sapit.springcloud.moudle.Page;
import com.sapit.springcloud.moudle.sys.Area;
import com.sapit.springcloud.moudle.sys.Menu;
import com.sapit.springcloud.moudle.sys.Office;
import com.sapit.springcloud.moudle.sys.Role;
import com.sapit.springcloud.moudle.sys.SysUserToken;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.moudle.util.DataScopeFilter;
import com.sapit.springcloud.web.common.shiro.SystemAuthorizingRealm.Principal;

/**
 * 用户工具类
 * 
 * @author sapit
 * @version 2013-12-05
 */
public class UserUtils {

	private static UserClient userClient = SpringContextHolder.getBean(UserClient.class);
	private static RoleClient roleClient = SpringContextHolder.getBean(RoleClient.class);
	private static MenuClient menuClient = SpringContextHolder.getBean(MenuClient.class);
	private static AreaClient areaClient = SpringContextHolder.getBean(AreaClient.class);
	private static OfficeClient officeClient = SpringContextHolder.getBean(OfficeClient.class);
	private static SysUserTokenClient sysUserTokenClient = SpringContextHolder.getBean(SysUserTokenClient.class);

	public static final String CACHE_AUTH_INFO = "authInfo";

	/**
	 * 根据ID获取用户
	 * 
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id) {
		User user = userClient.getById(id);
		if (user == null) {
			return null;
		}
		user.setRoleList(roleClient.findList(new Role(user)));
		return user;
	}

	/**
	 * 根据登录名获取用户
	 * 
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getByLoginName(String loginName) {
		User user = userClient.getByLoginName(loginName);
		if (user == null) {
			return null;
		}
		user.setRoleList(roleClient.findList(new Role(user)));
		return user;
	}

	/**
	 * 获取当前用户
	 * 
	 * @return 取不到返回 new User()
	 */
	public static User getUser() {
		Principal principal = getPrincipal();
		if (principal != null) {
			User user = get(principal.getId());
			if (user != null) {
				return user;
			}
			return new User();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new User();
	}

	public static User getUserByTokenId(String tokenId) {
		List<SysUserToken> list = sysUserTokenClient.findAllList(new SysUserToken(null, tokenId));
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		SysUserToken userToken = list.get(0);
		User user = get(userToken.getUser().getId());
		return user;
	}

	/**
	 * 获取当前用户角色列表
	 * 
	 * @return
	 */
	public static List<Role> getRoleList() {
		List<Role> roleList = null;
		User user = getUser();
		if (user.isAdmin()) {
			roleList = roleClient.findAllList(new Role());
		} else {
			Role role = new Role();
			role.getSqlMap().put("dsf", DataScopeFilter.dataScopeFilter(getUser(), "o", "u"));
			roleList = roleClient.findList(role);
		}

		return roleList;
	}

	/**
	 * 获取当前用户授权菜单
	 * 
	 * @return
	 */
	public static List<Menu> getMenuList() {
		List<Menu> menuList = null;
		User user = getUser();
		if (user.isAdmin()) {
			menuList = menuClient.findAllList(new Menu());
		} else {
			Menu m = new Menu();
			m.setUserId(user.getId());
			menuList = menuClient.findByUserId(m);
		}
		return menuList;
	}

	/**
	 * 获取当前用户授权的区域
	 * 
	 * @return
	 */
	public static List<Area> getAreaList() {
		return areaClient.findAllList(new Area());
	}

	/**
	 * 获取当前用户有权限访问的部门
	 * 
	 * @return
	 */
	public static List<Office> getOfficeList() {
		List<Office> officeList = null;
		User user = getUser();
		if (user.isAdmin()) {
			officeList = officeClient.findAllList(new Office());
		} else {
			Office office = new Office();
			office.getSqlMap().put("dsf", DataScopeFilter.dataScopeFilter(user, "a", ""));
			officeList = officeClient.findAllList(office);
		}
	
		return officeList;
	}

	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return principal;
			}
			// subject.logout();
		} catch (UnavailableSecurityManagerException e) {

		} catch (InvalidSessionException e) {

		}
		return null;
	}

	public static Session getSession() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null) {
				session = subject.getSession();
			}
			if (session != null) {
				return session;
			}
			// subject.logout();
		} catch (InvalidSessionException e) {

		}
		return null;
	}

	// ============== User Cache ==============

	public static Object getCache(String key) {
		return getCache(key, null);
	}

	public static Object getCache(String key, Object defaultValue) {
		// Object obj = getCacheMap().get(key);
		Object obj = getSession().getAttribute(key);
		return obj == null ? defaultValue : obj;
	}

	public static void putCache(String key, Object value) {
		// getCacheMap().put(key, value);
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
		// getCacheMap().remove(key);
		getSession().removeAttribute(key);
	}

	public static List<User> findUser(User user) {
		user.setCurrentLoginUser(UserUtils.getUser());
		user.getSqlMap().put("dsf", DataScopeFilter.dataScopeFilter(getUser(), "o", "a"));
		List<User> list = userClient.findList(user);
		return list;
	}

	public static Page<User> findUser(Page<User> page, User user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", DataScopeFilter.dataScopeFilter(getUser(), "o", "a"));
		user.setPage(page);
		user.setCurrentLoginUser(UserUtils.getUser());
		return userClient.findPage(user);
	}

	public static Map<String, Object> getCheckPasswdModDate(String loginName) {
		User checkedUser = userClient.getByLoginName(loginName);
		Date checkedDate = checkedUser.getUpdatePasswdDate();

		Map<String, Object> info = new HashMap<String, Object>();
		if (checkedDate != null) {
			Date now = new Date();
			long len = now.getTime() - checkedDate.getTime();
			int valDes = (int) (len / (1000 * 60 * 60 * 24));
			if (valDes >= 30) {
				info.put("warning", "您的密码已经超过修改间隔期限了，请尽快修改!");
			}
		}

		return info;
	}
}
