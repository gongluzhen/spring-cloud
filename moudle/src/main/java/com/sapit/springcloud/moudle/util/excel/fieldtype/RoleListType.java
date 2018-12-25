package com.sapit.springcloud.moudle.util.excel.fieldtype;

import java.util.List;

import com.google.common.collect.Lists;
import com.sapit.springcloud.common.util.Collections3;
import com.sapit.springcloud.common.util.Reflections;
import com.sapit.springcloud.common.util.SpringContextHolder;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.sys.Role;

/**
 * 字段类型转换
 * 
 * @author sapit
 * @version 2013-5-29
 */
public class RoleListType {
	private static Object roleClient = SpringContextHolder.getBeanByClass("com.sapit.springcloud.client.sys.RoleClient");

	/**
	 * 获取对象值（导入）
	 */
	@SuppressWarnings("unchecked")
	public static Object getValue(String val) {
		if (StringUtils.isBlank(val)) {
			return null;
		}

		List<Role> roleList = Lists.newArrayList();
		List<Role> allRoleList = (List<Role>) Reflections.invokeMethod(roleClient, "findList", new Class[] { Role.class }, new Object[] { new Role() });
		for (String s : StringUtils.split(val, ",")) {
			for (Role e : allRoleList) {
				if (StringUtils.trimToEmpty(s).equals(e.getName())) {
					roleList.add(e);
				}
			}
		}
		return roleList.size() > 0 ? roleList : null;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null) {
			@SuppressWarnings("unchecked")
			List<Role> roleList = (List<Role>) val;
			return Collections3.extractToString(roleList, "name", ", ");
		}
		return "";
	}

}
