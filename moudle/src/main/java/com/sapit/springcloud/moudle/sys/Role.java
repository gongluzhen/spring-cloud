package com.sapit.springcloud.moudle.sys;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.sapit.springcloud.common.util.Constants;
import com.sapit.springcloud.moudle.DataEntity;
import com.sapit.springcloud.moudle.util.excel.annotation.ExcelField;

public class Role extends DataEntity<Role> {

	private static final long serialVersionUID = 1L;
	private Office office; // 归属机构
	private String name; // 角色名称
	private String enname; // 英文名称
	private String noEnname;
	private String[] noEnnames;
	private String roleType;// 权限类型
	private String dataScope;// 数据范围

	private String oldName; // 原角色名称
	private String oldEnname; // 原英文名称
	private String sysData; // 是否是系统数据
	private String useable; // 是否是可用

	private User user; // 根据用户ID查询角色列表

	// private List<User> userList = Lists.newArrayList(); // 拥有用户列表
	private List<Menu> menuList = Lists.newArrayList(); // 拥有菜单列表
	private List<Office> officeList = Lists.newArrayList(); // 按明细设置数据范围

	// 数据范围（1：所有数据；2：所在机构及以下数据；3：所在机构数据；4：仅本人数据；9：按明细设置）
	public static final String DATA_SCOPE_ALL = "1";
	public static final String DATA_SCOPE_OFFICE_AND_CHILD = "2";
	public static final String DATA_SCOPE_OFFICE = "3";
	public static final String DATA_SCOPE_SELF = "4";
	public static final String DATA_SCOPE_CUSTOM = "9";

	public Role() {
		super();
		this.dataScope = DATA_SCOPE_SELF;
		this.useable = Constants.YES;
	}

	public Role(String id) {
		super(id);
	}

	public Role(User user) {
		this();
		this.user = user;
	}

	@ExcelField(title = "是否是可用", align = 2, sort = 5, dictType = "yes_no")
	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	@ExcelField(title = "是否是系统数据", align = 2, sort = 4)
	public String getSysData() {
		return sysData;
	}

	public void setSysData(String sysData) {
		this.sysData = sysData;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@Length(min = 1, max = 100)
	@ExcelField(title = "角色名称", align = 2, sort = 1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 1, max = 100)
	@ExcelField(title = "英文名称", align = 2, sort = 2)
	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	@Length(min = 1, max = 100)
	@ExcelField(title = "权限类型", align = 2, sort = 3)
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	@ExcelField(title = "数据范围", align = 2, sort = 6, dictType = "sys_data_scope")
	public String getDataScope() {
		return dataScope;
	}

	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getOldEnname() {
		return oldEnname;
	}

	public void setOldEnname(String oldEnname) {
		this.oldEnname = oldEnname;
	}

	// public List<User> getUserList() {
	// return userList;
	// }
	//
	// public void setUserList(List<User> userList) {
	// this.userList = userList;
	// }
	//
	// public List<String> getUserIdList() {
	// List<String> nameIdList = Lists.newArrayList();
	// for (User user : userList) {
	// nameIdList.add(user.getId());
	// }
	// return nameIdList;
	// }
	//
	// public String getUserIds() {
	// return StringUtils.join(getUserIdList(), ",");
	// }

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public List<String> getMenuIdList() {
		List<String> menuIdList = Lists.newArrayList();
		for (Menu menu : menuList) {
			menuIdList.add(menu.getId());
		}
		return menuIdList;
	}

	public void setMenuIdList(List<String> menuIdList) {
		menuList = Lists.newArrayList();
		for (String menuId : menuIdList) {
			Menu menu = new Menu();
			menu.setId(menuId);
			menuList.add(menu);
		}
	}

	public String getMenuIds() {
		return StringUtils.join(getMenuIdList(), ",");
	}

	public void setMenuIds(String menuIds) {
		menuList = Lists.newArrayList();
		if (menuIds != null) {
			String[] ids = StringUtils.split(menuIds, ",");
			setMenuIdList(Lists.newArrayList(ids));
		}
	}

	public List<Office> getOfficeList() {
		return officeList;
	}

	public void setOfficeList(List<Office> officeList) {
		this.officeList = officeList;
	}

	public List<String> getOfficeIdList() {
		List<String> officeIdList = Lists.newArrayList();
		for (Office office : officeList) {
			officeIdList.add(office.getId());
		}
		return officeIdList;
	}

	public void setOfficeIdList(List<String> officeIdList) {
		officeList = Lists.newArrayList();
		for (String officeId : officeIdList) {
			Office office = new Office();
			office.setId(officeId);
			officeList.add(office);
		}
	}

	public String getOfficeIds() {
		return StringUtils.join(getOfficeIdList(), ",");
	}

	public void setOfficeIds(String officeIds) {
		officeList = Lists.newArrayList();
		if (officeIds != null) {
			String[] ids = StringUtils.split(officeIds, ",");
			setOfficeIdList(Lists.newArrayList(ids));
		}
	}

	/**
	 * 获取权限字符串列表
	 */
	public List<String> getPermissions() {
		List<String> permissions = Lists.newArrayList();
		for (Menu menu : menuList) {
			if (menu.getPermission() != null && !"".equals(menu.getPermission())) {
				permissions.add(menu.getPermission());
			}
		}
		return permissions;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNoEnname() {
		return noEnname;
	}

	public void setNoEnname(String noEnname) {
		this.noEnname = noEnname;
	}

	@Length(min = 0, max = 255)
	@ExcelField(title = "备注", align = 2, sort = 7)
	public String getRemarks() {
		return remarks;
	}

	public String[] getNoEnnames() {
		return noEnnames;
	}

	public void setNoEnnames(String[] noEnnames) {
		this.noEnnames = noEnnames;
	}
	
}
