package com.sapit.springcloud.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("deprecation")
public final class TimestampTool {
	static SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");

	// 当前时间
	public static Timestamp crunttime() {

		return new Timestamp(System.currentTimeMillis());
	}

	// 获取当前时间的字符串 2006-07-07
	public static String getCurrentDate() {

		Timestamp d = crunttime();
		return d.toString().substring(0, 10);
	}

	// 获取当前时间的字符串 2006-07-07 22:10:10
	public static String getCurrentDateTime() {

		Timestamp d = crunttime();
		return d.toString().substring(0, 19);
	}

	// 获取给定时间的字符串,只有日期 2006-07-07
	public static String getStrDate(Timestamp t) {
		return t.toString().substring(0, 10);
	}

	// 获取给定时间的字符串,只有日期 20060707
	public static String getStringDate() {
		Timestamp t = crunttime();
		return t.toString().substring(0, 10).replaceAll("-", "");
	}

	// 获取给定时间的字符串 2006-07-07 22:10:10
	public static String getStrDateTime(Timestamp t) {
		return t.toString().substring(0, 19);
	}

	// 返回日期 格式:2006-07-05
	
	public static Timestamp date(String str) {
		Timestamp tp = null;
		if (str.length() <= 10) {
			String[] string = str.trim().split("-");
			int one = Integer.parseInt(string[0]) - 1900;
			int two = Integer.parseInt(string[1]) - 1;
			int three = Integer.parseInt(string[2]);
			tp = new Timestamp(one, two, three, 0, 0, 0, 0);
		}
		return tp;
	}

	// 返回时间和日期 格式:2006-07-05 22:10:10
	public static Timestamp datetime(String str) {
		Timestamp tp = null;
		if (str.length() > 10) {
			String[] string = str.trim().split(" ");
			String[] date = string[0].split("-");
			String[] time = string[1].split(":");
			int date1 = Integer.parseInt(date[0]) - 1900;
			int date2 = Integer.parseInt(date[1]) - 1;
			int date3 = Integer.parseInt(date[2]);
			int time1 = Integer.parseInt(time[0]);
			int time2 = Integer.parseInt(time[1]);
			if (time.length > 2) {
				int time3 = Integer.parseInt(time[2]);
				tp = new Timestamp(date1, date2, date3, time1, time2, time3, 0);
			} else {
				tp = new Timestamp(date1, date2, date3, time1, time2, 0, 0);
			}

		}
		return tp;
	}

	// 返回日期和时间(没有秒) 格式:2006-07-05 22:10
	public static Timestamp datetimeHm(String str) {
		Timestamp tp = null;
		if (str.length() > 10) {
			String[] string = str.trim().split(" ");
			String[] date = string[0].split("-");
			String[] time = string[1].split(":");
			int date1 = Integer.parseInt(date[0]) - 1900;
			int date2 = Integer.parseInt(date[1]) - 1;
			int date3 = Integer.parseInt(date[2]);
			int time1 = Integer.parseInt(time[0]);
			int time2 = Integer.parseInt(time[1]);
			tp = new Timestamp(date1, date2, date3, time1, time2, 0, 0);
		}
		return tp;
	}

	// 获取当前日期之前的日期字符串 如 2007-04-15 前5月 就是 2006-11-15
	public static String getPreviousMonth(int month) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		cal1.add(Calendar.MONTH, -month);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(cal1.getTime());
	}

	public static String getPreviousMonth(int month, String format) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		cal1.add(Calendar.MONTH, -month);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(cal1.getTime());
	}

	public static String getPreviousMonth(Date date, int month) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		cal1.add(Calendar.MONTH, -month);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(cal1.getTime());
	}

	/**
	 * 日期月份加减
	 *
	 * @param date	Date
	 * @param month	正数：向前推xxx个月，负数：向后推xxx个月
	 * @return Date
	 * @author YJH
	 */
	public static Date getPreviousMonthReturnDate(Date date, int month) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		cal1.add(Calendar.MONTH, -month);
		return cal1.getTime();
	}

	public static String getPreviousMonth(Date date, int month, String format) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		cal1.add(Calendar.MONTH, -month);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(cal1.getTime());
	}

	// 获取当前日期之后的日期字符串 如 2007-04-15 后一天 就是 2007-04-16
	public static String getNextDay(int day) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		cal1.add(Calendar.DAY_OF_MONTH, day);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(cal1.getTime());
	}

	public static Date getNextHour(int hour) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		cal1.add(Calendar.HOUR_OF_DAY, hour);
		return cal1.getTime();
	}

	public static Date getNextHour(Date date, int hour) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		cal1.add(Calendar.HOUR_OF_DAY, hour);
		return cal1.getTime();
	}

	public static Date getNextMinute(int minute) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		cal1.add(Calendar.MINUTE, minute);
		return cal1.getTime();
	}

	// 获取指定日期之后的日期字符串 如 2007-04-15 后一天 就是 2007-04-16
	public static String getNextDay(String strDate, int day) {
		Calendar cal1 = Calendar.getInstance();
		String[] string = strDate.trim().split("-");
		int one = Integer.parseInt(string[0]) - 1900;
		int two = Integer.parseInt(string[1]) - 1;
		int three = Integer.parseInt(string[2]);
		cal1.setTime(new Date(one, two, three));
		cal1.add(Calendar.DAY_OF_MONTH, day);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(cal1.getTime());
	}

	public static Date getNextDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	/** 得到当前的毫秒日期20120201165044734，17位字符串 */
	public static String getMillisecond() {
		String str = crunttime().toString().replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "").replaceAll("\\.", "");
		return str;
	}

	public static String getDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	public static String getDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	public static String formatDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dateStr = sdf.format(date);
		return dateStr;
	}

	public static String getStringDate(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		return dateStr.replaceAll("-", "");
	}

	public static String dateStr(String date) {

		String dateStr = "";
		if (StringUtils.isNotBlank(date)) {
			dateStr = " to_date('" + date + " 00:00:00','yyyy-MM-dd HH24:mi:ss') ";
		}

		return dateStr;
	}

	/**
	 * subtractDate:(得到两个日期相减的结果)
	 * 
	 * @param @param
	 *            minuend（被减数）
	 * @param @param
	 *            subtrahend（减数）
	 * @param @param
	 *            returnType（返回类型：1-毫秒，2-秒，3-分，4-小时，5-天，6-月，7-年）
	 * @param @return
	 * @return long DOM对象
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	public static long subtractDate(Date minuend, Date subtrahend, int returnType) {
		long result = 0;
		long millionSecondsForMinuend = minuend.getTime();
		long millionSecondsForSubtrahend = subtrahend.getTime();
		long subtract = millionSecondsForMinuend - millionSecondsForSubtrahend;
		switch (returnType) {
		case 1:
			result = subtract;
			break;
		case 2:
			result = subtract / 1000;
			break;
		case 3:
			result = subtract / 1000 / 60;
			break;
		case 4:
			result = subtract / 1000 / 60 / 60;
			break;
		case 5:
			result = subtract / 1000 / 60 / 60 / 24;
			break;
		case 6:
			result = subtract / 1000 / 60 / 60 / 24 / 30;
			break;
		case 7:
			result = subtract / 1000 / 60 / 60 / 24 / 30 / 12;
			break;
		}
		return result;
	}

	/**
	 * 取得当前日期是多少周
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		/**
		 * 设置一年中第一个星期所需的最少天数，例如，如果定义第一个星期包含一年第一个月的第一天，则使用值 1 调用此方法。
		 * 如果最少天数必须是一整个星期，则使用值 7 调用此方法。
		 **/
		c.setMinimalDaysInFirstWeek(1);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 得到某一年周的总数
	 * 
	 * @param year
	 * @return
	 */
	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = Calendar.getInstance();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

		return getWeekOfYear(c.getTime());
	}

	/**
	 * 得到某年某周的第一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.WEEK_OF_YEAR, week);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 设置周一
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}

	/**
	 * 得到某年某周的最后一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.WEEK_OF_YEAR, week);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 得到某年某月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}

	/**
	 * 得到某年某月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}

	/**
	 * 得到某年某季度第一天
	 * 
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getFirstDayOfQuarter(int year, int quarter) {
		int month = 0;
		if (quarter > 4) {
			return null;
		} else {
			month = (quarter - 1) * 3 + 1;
		}
		return getFirstDayOfMonth(year, month);
	}

	/**
	 * 得到某年某季度最后一天
	 * 
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getLastDayOfQuarter(int year, int quarter) {
		int month = 0;
		if (quarter > 4) {
			return null;
		} else {
			month = quarter * 3;
		}
		return getLastDayOfMonth(year, month);
	}

	/**
	 * 得到某年第一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getFirstDayOfYear(int year) {
		return getFirstDayOfQuarter(year, 1);
	}

	/**
	 * 得到某年最后一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getLastDayOfYear(int year) {
		return getLastDayOfQuarter(year, 4);
	}

	/**
	 * 获取当月的 天数
	 */
	public static int getCurrentMonthDay() {

		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 根据年 月 获取对应的月份 天数
	 */
	public static int getDaysByYearMonth(int year, int month) {

		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 根据日期 找到对应日期的 星期
	 */
	public static String getDayOfWeekByDate(String date) {
		String dayOfweek = "-1";
		try {
			SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
			Date myDate = myFormatter.parse(date);
			SimpleDateFormat formatter = new SimpleDateFormat("E");
			String str = formatter.format(myDate);
			dayOfweek = str;

		} catch (Exception e) {
			System.out.println("错误!");
		}
		return dayOfweek;
	}

	// 获取给定时间字符串对应的日期类型 2006-07-07 22:10:10
	public static Date getDateFromStr(String t) throws ParseException {
		if (StringUtils.isNotBlank(t)) {
			return dff.parse(t);
		}
		return crunttime();
	}

	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	// 获取yf=‘2014-04’前1个月的月份，返回2014-03
	public static String getPreviousMonth(String date) {
		int nd = Integer.valueOf(date.substring(0, 4));
		int yf = Integer.valueOf(date.substring(5, 7));
		if (yf == 1) {
			nd = nd - 1;
			yf = 12;
		} else {
			yf = yf - 1;
		}
		return getDate(getFirstDayOfMonth(nd, yf)).substring(0, 7);
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(new Date());
	}

}
