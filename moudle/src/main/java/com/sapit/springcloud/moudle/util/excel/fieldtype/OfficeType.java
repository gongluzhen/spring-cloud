package com.sapit.springcloud.moudle.util.excel.fieldtype;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.sapit.springcloud.common.util.Reflections;
import com.sapit.springcloud.common.util.SpringContextHolder;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.sys.Office;

/**
 * 字段类型转换
 * 
 * @author sapit
 * @version 2013-03-10
 */
public class OfficeType {
	private static Object officeClient = SpringContextHolder.getBeanByClass("com.sapit.springcloud.client.sys.OfficeClient");

	/**
	 * 获取对象值（导入）
	 */
	@SuppressWarnings("unchecked")
	public static Object getValue(String val) {
		if (StringUtils.isBlank(val)) {
			return null;
		}

		List<Office> list = (List<Office>) Reflections.invokeMethod(officeClient, "findAllList", new Class[] { Office.class }, new Object[] { new Office() });
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((Office) val).getName() != null) {
			return ((Office) val).getName();
		}
		return "";
	}
}
