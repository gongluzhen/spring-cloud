package com.sapit.springcloud.moudle.util.excel.fieldtype;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.sapit.springcloud.common.util.Reflections;
import com.sapit.springcloud.common.util.SpringContextHolder;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.moudle.sys.Area;

/**
 * 字段类型转换
 * 
 * @author sapit
 * @version 2013-03-10
 */
public class AreaType {
	private static Object areaClient = SpringContextHolder.getBeanByClass("com.sapit.springcloud.client.sys.AreaClient");

	/**
	 * 获取对象值（导入）
	 */
	@SuppressWarnings("unchecked")
	public static Object getValue(String val) {
		if (StringUtils.isBlank(val)) {
			return null;
		}

		Area area = new Area();
		area.setName(val);
		List<Area> list = (List<Area>) Reflections.invokeMethod(areaClient, "findAllList", new Class[] { Area.class }, new Object[] { area });
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((Area) val).getName() != null) {
			return ((Area) val).getName();
		}
		return "";
	}
}
