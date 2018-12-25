package com.sapit.springcloud.web.common.thymeleaf;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.sapit.springcloud.common.util.DateUtils;
import com.sapit.springcloud.common.util.Encodes;
import com.sapit.springcloud.common.util.FileUtils;
import com.sapit.springcloud.common.util.MathUtil;
import com.sapit.springcloud.common.util.StringUtils;
import com.sapit.springcloud.common.util.TimestampTool;
import com.sapit.springcloud.common.util.mapper.JsonMapper;
import com.sapit.springcloud.moudle.sys.Area;
import com.sapit.springcloud.moudle.sys.Dict;
import com.sapit.springcloud.moudle.sys.Menu;
import com.sapit.springcloud.moudle.sys.Office;
import com.sapit.springcloud.moudle.sys.User;
import com.sapit.springcloud.web.common.shiro.SystemAuthorizingRealm.Principal;
import com.sapit.springcloud.web.common.utils.DictUtils;
import com.sapit.springcloud.web.common.utils.UserUtils;

public class CommonExpressionObject {
	private final Locale locale;

	public CommonExpressionObject(final Locale locale) {
		super();
		this.locale = locale;
	}

	/**
	 * 获取当前用户对象
	 * 
	 * @return
	 */
	public User getCurrentUser() {
		return UserUtils.getUser();
	}

	/**
	 * 根据编码获取用户对象
	 * 
	 * @param id
	 * @return
	 */
	public User getUserById(String id) {
		return UserUtils.get(id);
	}

	/**
	 * 获取授权用户信息
	 * 
	 * @return
	 */
	public Principal getPrincipal() {
		return UserUtils.getPrincipal();
	}

	/**
	 * 获取当前用户的菜单对象列表
	 * 
	 * @return
	 */
	public List<Menu> getMenuList() {
		return UserUtils.getMenuList();
	}

	/**
	 * 获取当前用户的区域对象列表
	 * 
	 * @return
	 */
	public List<Area> getAreaList() {
		return UserUtils.getAreaList();
	}

	/**
	 * 获取当前用户的部门对象列表
	 * 
	 * @return
	 */
	public List<Office> getOfficeList() {
		return UserUtils.getOfficeList();
	}

	/**
	 * 获取字典标签
	 * 
	 * @param value
	 * @param type
	 * @param defaultValue
	 * @return
	 */
	public String getDictLabel(String value, String type, String defaultValue) {
		return DictUtils.getDictLabel(value, type, defaultValue);
	}

	/**
	 * 获取字典标签(多个)
	 * 
	 * @param values
	 * @param type
	 * @param defaultValue
	 * @return
	 */
	public String getDictLabels(String values, String type, String defaultValue) {
		return DictUtils.getDictLabels(values, type, defaultValue);
	}

	/**
	 * 获取字典值
	 * 
	 * @param label
	 * @param type
	 * @param defaultLabel
	 * @return
	 */
	public String getDictValue(String label, String type, String defaultLabel) {
		return DictUtils.getDictValue(label, type, defaultLabel);
	}

	/**
	 * 获取字典对象列表
	 * 
	 * @param labels
	 * @param type
	 * @param defaultLabel
	 * @return
	 */
	public String getDictValues(String labels, String type, String defaultLabel) {
		return DictUtils.getDictValues(labels, type, defaultLabel);
	}

	/**
	 * 获取字典对象列表
	 * 
	 * @param type
	 * @return
	 */
	public String getDictListJson(String type) {
		return DictUtils.getDictListJson(type);
	}
	
	
	public List<Dict> getDictList(String type) {
		return DictUtils.getDictList(type);
	}

	/**
	 * URL编码
	 * 
	 * @param part
	 * @return
	 */
	public String urlEncode(String part) {
		return Encodes.urlEncode(part);
	}

	/**
	 * URL解码
	 */
	public String urlDecode(String part) {
		return Encodes.urlDecode(part);
	}

	/**
	 * Html 转码.
	 */
	public String escapeHtml(String html) {
		return Encodes.escapeHtml(html);
	}

	/**
	 * Html 解码.
	 */
	public String unescapeHtml(String htmlEscaped) {
		return Encodes.unescapeHtml(htmlEscaped);
	}

	/**
	 * 从后边开始截取字符串
	 * 
	 * @param str
	 * @param separator
	 * @return
	 */
	public String substringAfterLast(final String str, final String separator) {
		return StringUtils.substringAfterLast(str, separator);
	}

	/**
	 * 判断字符串是否以某某开头
	 * 
	 * @param str
	 * @param separator
	 * @return
	 */
	public boolean startsWith(final String str, final String separator) {
		return StringUtils.startsWith(str, separator);
	}

	/**
	 * 判断字符串是否以某某结尾
	 * 
	 * @param str
	 * @param suffix
	 * @return
	 */
	public boolean endsWith(final CharSequence str, final CharSequence suffix) {
		return StringUtils.endsWith(str, suffix);
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * 
	 * @param str
	 *            目标字符串
	 * @param length
	 *            截取长度
	 * @return
	 */
	public String abbr(String str, int length) {
		return StringUtils.abbr(str, length);
	}

	/**
	 * 替换掉HTML标签
	 * 
	 * @param html
	 * @return
	 */
	public String replaceHtml(String html) {
		return StringUtils.replaceHtml(html);
	}

	/**
	 * 转换为JS获取对象值，生成三目运算返回结果
	 * 
	 * @param objectString
	 *            对象串 例如：row.user.id
	 *            返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
	 */
	public String jsGetVal(String objectString) {
		return StringUtils.jsGetVal(objectString);
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public String getDate(String pattern) {
		return DateUtils.getDate(pattern);
	}

	/**
	 * 获取当前日期(long)
	 * 
	 * @return
	 */
	public long getLongTime() {
		return DateUtils.getLongTime();
	}

	/**
	 * 获取过去的天数
	 * 
	 * @param date
	 * @return
	 */
	public long pastDays(Date date) {
		return DateUtils.pastDays(date);
	}

	/**
	 * 对象转换JSON字符串
	 * 
	 * @param object
	 * @return
	 */
	public String toJsonString(Object object) {
		return JsonMapper.toJsonString(object);
	}
	
	public String formatDate(Date date, String format) {
		if(date == null){
			return "";
		}
		return TimestampTool.formatDate(date, format);
	}

	/**
	 * 小数转百分数
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public BigDecimal toPercent(BigDecimal bigDecimal) {
		return MathUtil.toPercent(bigDecimal);
	}

	/**
	 * 百分数转小数
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public BigDecimal toDecimal(BigDecimal bigDecimal) {
		return MathUtil.toDecimal(bigDecimal);
	}

	/**
	 * chocus添加，把10000转成1万
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public BigDecimal toMillion(BigDecimal bigDecimal) {
		return MathUtil.toMillion(bigDecimal);
	}

	/**
	 * chocus添加，去掉小数点
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public BigDecimal toInteger(BigDecimal bigDecimal) {
		return MathUtil.toInteger(bigDecimal);
	}

	/**
	 * chocus添加，保留两位小数
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public BigDecimal toDouble(BigDecimal bigDecimal) {
		return MathUtil.toDouble(bigDecimal);
	}

	/**
	 * chocus添加，保留一位小数，比例专用
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public BigDecimal toOne(BigDecimal bigDecimal) {
		return MathUtil.toOne(bigDecimal);
	}
	
	
	/**
	 * 判断checkbox是否选中
	 * @param values ex.1,1,2,2
	 * @param value ex.1
	 * @return
	 */
	public boolean isCheckboxChecked(String values, String value){
		return StringUtils.isCheckboxChecked(values, value);
	}
	
	/**
	 * 友好的显示文件大小
	 * @param size
	 * @return
	 */
	public String byteCountToDisplaySize(long size){
		return FileUtils.byteCountToDisplaySize(size);
	}

	public Locale getLocale() {
		return locale;
	}
}
