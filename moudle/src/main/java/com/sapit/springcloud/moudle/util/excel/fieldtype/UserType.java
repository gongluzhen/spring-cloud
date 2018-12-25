package com.sapit.springcloud.moudle.util.excel.fieldtype;

import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.sys.User;

public class UserType {

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		User user = null;
		if(!StringUtils.isBlank(val)){
			user = new User();
			user.setName(val);
		}
		return user;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((User) val).getName() != null) {
			return ((User) val).getName();
		}
		return "";
	}
}
