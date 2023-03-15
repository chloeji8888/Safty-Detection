package com.jszy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class DateFormatInfo {

	private static SimpleDateFormat bigLongSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSS");
	private static SimpleDateFormat hourSdf = new SimpleDateFormat("HH");
	private static SimpleDateFormat minutesSdf = new SimpleDateFormat("mm");
	private static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");
	private static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 获得本周的第一天，即周日
	 * 
	 * @return
	 */
	public static String getCurrentWeekDayStartTime(String sDate) {
		Calendar c = Calendar.getInstance();
		try {
			if (DateUtil.isDate(sDate)) {
				c.setTime(shortSdf.parse(sDate));
			}
			int weekday = c.get(Calendar.DAY_OF_WEEK) - 1;
			c.add(Calendar.DATE, -weekday);
			c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DateUtil.getDateString(c, "YY-MM-DD HH24:MI:SS");
	}

	/**
	 * 获得当前日期周的最后一天，即本周天
	 * 
	 * @return
	 */
	public static String getCurrentWeekDayEndTime(String sDate) {
		Calendar c = Calendar.getInstance();

		try {
			if (DateUtil.isDate(sDate)) {
				c.setTime(shortSdf.parse(sDate));
			}
			int weekday = c.get(Calendar.DAY_OF_WEEK);
			c.add(Calendar.DATE, 8 - weekday);// 周天改为7-weekday
			c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DateUtil.getDateString(c, "YY-MM-DD HH24:MI:SS");
	}

	/**
	 * 获得本天的开始时间，即2012-01-01 00:00:00
	 * 
	 * @return
	 */
	public static Date getCurrentDayStartTime() {
		Date now = new Date();
		try {
			now = shortSdf.parse(shortSdf.format(now));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获得本天的结束时间，即2012-01-01 23:59:59
	 * 
	 * @return
	 */
	public static Date getCurrentDayEndTime() {
		Date now = new Date();
		try {
			now = longSdf.parse(shortSdf.format(now) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获得本小时的开始时间，即2012-01-01 23:59:59
	 * 
	 * @return
	 */
	public static Date getCurrentHourStartTime() {
		Date now = new Date();
		try {
			now = longHourSdf.parse(longHourSdf.format(now));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获得本小时的结束时间，即2012-01-01 23:59:59
	 * 
	 * @return
	 */
	public static Date getCurrentHourEndTime() {
		Date now = new Date();
		try {
			now = longSdf.parse(longHourSdf.format(now) + ":59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 获得本月的开始时间，即2012-01-01 00:00:00
	 * 
	 * @return
	 */
	public static String getCurrentMonthStartTime(String sDate) {
		Calendar c = Calendar.getInstance();
		try {
			if (DateUtil.isDate(sDate)) {
				c.setTime(shortSdf.parse(sDate));
			}
			c.set(Calendar.DATE, 1);
			c.setTime(shortSdf.parse(shortSdf.format(c.getTime())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DateUtil.getDateString(c, "YY-MM-DD HH24:MI:SS");
	}

	/**
	 * 当前月的结束时间，即2012-01-31 23:59:59
	 * 
	 * @return
	 */
	public static String getCurrentMonthEndTime(String sDate) {
		Calendar c = Calendar.getInstance();
		try {
			if (DateUtil.isDate(sDate)) {
				c.setTime(shortSdf.parse(sDate));
			}
			c.set(Calendar.DATE, 1);
			c.add(Calendar.MONTH, 1);
			c.add(Calendar.DATE, -1);
			c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DateUtil.getDateString(c, "YY-MM-DD HH24:MI:SS");
	}

	/**
	 * 当前年的开始时间，即2012-01-01 00:00:00
	 * 
	 * @return
	 */
	public static Date getCurrentYearStartTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			c.set(Calendar.MONTH, 0);
			c.set(Calendar.DATE, 1);
			now = shortSdf.parse(shortSdf.format(c.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前年
	 * 
	 * @return
	 */

	public static String getCurrentYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		return sdf.format(date);
	}

	/**
	 * 当前年的结束时间，即2012-12-31 23:59:59
	 * 
	 * @return
	 */
	public static Date getCurrentYearEndTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			c.set(Calendar.MONTH, 11);
			c.set(Calendar.DATE, 31);
			now = shortSdf.parse(shortSdf.format(c.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前季度的开始时间，即2012-01-1 00:00:00
	 * 
	 * @return
	 */
	public static Date getCurrentQuarterStartTime() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3)
				c.set(Calendar.MONTH, 0);
			else if (currentMonth >= 4 && currentMonth <= 6)
				c.set(Calendar.MONTH, 3);
			else if (currentMonth >= 7 && currentMonth <= 9)
				c.set(Calendar.MONTH, 4);
			else if (currentMonth >= 10 && currentMonth <= 12)
				c.set(Calendar.MONTH, 9);
			c.set(Calendar.DATE, 1);
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前季度的结束时间，即2012-03-31 23:59:59
	 * 
	 * @return
	 */
	public static Date getCurrentQuarterEndTime() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3) {
				c.set(Calendar.MONTH, 2);
				c.set(Calendar.DATE, 31);
			} else if (currentMonth >= 4 && currentMonth <= 6) {
				c.set(Calendar.MONTH, 5);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 7 && currentMonth <= 9) {
				c.set(Calendar.MONTH, 8);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 10 && currentMonth <= 12) {
				c.set(Calendar.MONTH, 11);
				c.set(Calendar.DATE, 31);
			}
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return now;
	}

	/**
	 * 
	 * 方法描述
	 * 
	 * @return 当前半年的结束日期
	 * 
	 * @变更记录 2013-3-12 上午10:45:30 Administrator 创建
	 * 
	 */
	public static Date getCurrentHalfYearEndTime() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 6) {
				c.set(Calendar.MONTH, 6);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 7 && currentMonth <= 12) {
				c.set(Calendar.MONTH, 12);
				c.set(Calendar.DATE, 31);
			}
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return now;
	}

	public static String getWorkCount(String sFirstDate, String sLastDate) throws Exception {

		int sWorDayCount = 0;
		String sContrachRegisterDate = StringUtil.nullValue(sFirstDate);
		String sContractSignDate = StringUtil.nullValue(sLastDate);
		if (!DateUtil.isDate(sContrachRegisterDate) || !DateUtil.isDate(sContractSignDate)) {
			return "";
		}
		int[] nDiff = dateDiff(sContractSignDate, sContrachRegisterDate, "yyyy-MM-dd");
		if (nDiff != null && nDiff.length > 0) {
			sWorDayCount = nDiff[0];
		}
		return sWorDayCount > 5 ? ("<font color=red>" + sWorDayCount + "</font>") : (sWorDayCount + "");
	}

	/**
	 * 
	 * @param birthDay 生日
	 * @return 年龄
	 * @throws Exception
	 */
	public static String getAge(String sbirthDay) throws Exception {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date birthDay = sdf.parse(sbirthDay);
		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}

		return age + "";
	}

	/**
	 * 
	 * 方法描述
	 * 
	 * @param s 日期
	 * @param n 天数
	 * @return
	 * @变更记录 2013-5-3 下午8:53:42 Administrator 创建
	 * 
	 */
	public String addDay(String s, int n) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(s));
			cd.add(Calendar.DATE, n);// 增加一天
			// cd.add(Calendar.MONTH, n);//增加一个月

			return sdf.format(cd.getTime());

		} catch (Exception e) {
			return null;
		}
	}

	// 输入日期和月增量，返回变化后的日期
	public String addMonth(String s, int n) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(s));
			cd.add(Calendar.MONTH, n);// 增加一个月

			return sdf.format(cd.getTime());

		} catch (Exception e) {
			return null;
		}
	}

	// 该日期是星期几 1~7
	public static int dayForWeek(String pTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(pTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	public static String dayForWeekZh(String pTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(pTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		String zhDayForWeek = "";
		switch (dayForWeek) {
		case 1:
			zhDayForWeek = "一";
			break;
		case 2:
			zhDayForWeek = "二";
			break;
		case 3:
			zhDayForWeek = "三";
			break;
		case 4:
			zhDayForWeek = "四";
			break;
		case 5:
			zhDayForWeek = "五";
			break;
		case 6:
			zhDayForWeek = "六";
			break;
		case 7:
			zhDayForWeek = "日";
			break;
		}
		return zhDayForWeek;
	}

	/***
	 * 获取当月的第一天
	 * 
	 * @return
	 */
	public static String getMonthFirstDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String firstDay = format.format(cal_1.getTime());
		return firstDay;
	}

	public static String getMonthLastDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		return last;
	}

	// 根据开始时间，周次，星期几 得到日期
	public String getDateByWeek(String sStartDate, int nWeekCount, int nDayCount) {
		int nStartWeekDay = dayForWeek(sStartDate);
		/*
		 * if(nWeekCount==1) { return addDay(sStartDate, nDayCount); } else {
		 */
		return addDay(sStartDate, (nWeekCount - 2) * 7 + (7 - nStartWeekDay) + nDayCount);
		// }
	}

	// 输入：开始时间，当前时间 return 周次，星期几
	public int[] getWeekDayByDate(String sStartDate, String sEndDate) {
		int nWeekCount = 1;
		int[] aWeekDay = new int[2];
		aWeekDay[0] = aWeekDay[1] = -1;
		String sNowDate = sStartDate;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			do {
				if (sdf.parse(sStartDate).getTime() > sdf.parse(sEndDate).getTime())
					break;
				for (int nDayCount = 1; nDayCount <= 7; nDayCount++) {
					sNowDate = getDateByWeek(sStartDate, nWeekCount, nDayCount);
					if (sdf.parse(sNowDate).getTime() != sdf.parse(sEndDate).getTime()) {
						continue;
					} else {
						aWeekDay[0] = nWeekCount;
						aWeekDay[1] = nDayCount;
						break;
					}
				}
				nWeekCount++;
			} while (sdf.parse(sNowDate).getTime() != sdf.parse(sEndDate).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return aWeekDay;
	}

	// 输入： 年份，月份 返回：某个月的天数
	public int getMonthDays(int nYear, int nMonth) {
		switch (nMonth) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		case 2:
			if ((nYear % 4 == 0 && nYear % 100 != 0) || nYear % 400 == 0)
				return 29;
			else
				return 28;
		}
		return 0;

	}

	// 输入： 周期天数，周期开始时间，当前时间 返回：当前周期的第一天和最后一天
	public String[] getFirstAndLastDayOfCycle(int days, String sStartDate, String sNowDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] sDates = new String[2];
		String sFirstDate = "";
		String sLastDate = "";
		sFirstDate = sStartDate;
		sLastDate = addDay(sFirstDate, days - 1);

		try {
			while ((sdf.parse(sNowDate).getTime() > sdf.parse(sLastDate).getTime())
					|| (sdf.parse(sNowDate).getTime() < sdf.parse(sFirstDate).getTime())) {
				sFirstDate = addDay(sLastDate, 1);
				sLastDate = addDay(sLastDate, days);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sDates[0] = sFirstDate;
		sDates[1] = sLastDate;
		return sDates;

	}

	// 输入两个日期（如2008-8-8）,格式化方式；输出：相差天、小时、分钟、秒
	public static int[] dateDiff(String startTime, String endTime, String format) {
		int[] aDiff = new int[4];
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff = 0;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {
			// 获得两个时间的毫秒时间差异
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			day = diff / nd;// 计算差多少天
			hour = diff % nd / nh;// 计算差多少小时
			min = diff % nd % nh / nm;// 计算差多少分钟
			sec = diff % nd % nh % nm / ns;// 计算差多少秒
		} catch (ParseException e) {
			e.printStackTrace();
		}
		aDiff[0] = (int) day;
		aDiff[1] = (int) hour;
		aDiff[2] = (int) min;
		aDiff[3] = (int) sec;

		return aDiff;

	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate 较小的时间
	 * @param bdate 较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算2个时间相隔天数
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算2个时间相隔几周
	 */
	public static int weeksBetween(String smdate, String bdate) throws ParseException {
		int days = daysBetween(smdate, bdate);
		int day = dayForWeek(smdate);
		// 加上本周已过天数
		days += day;
		if (days % 7 != 0) {
			return (days / 7) + 1;
		} else {
			return days / 7;
		}
	}

	// 判断一个是否比某个时间早
	public static boolean dayBefore(String mdate, String ndate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date compDay = sdf.parse(mdate);
		Date date = sdf.parse(ndate);
		return compDay.before(date);
	}

	/**
	 * 格式化时间为日期
	 * 
	 * @param time
	 * @return
	 */
	public static String formatTimeToDate(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date compDay;
		try {
			compDay = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		return format.format(compDay);
	}

	/**
	 * 格式化时间为日期
	 * 
	 * @param time
	 * @return
	 */
	public static Date formatTime(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date compDay;
		try {
			compDay = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return compDay;
	}

	/**
	 * 判断是否为当天
	 * 
	 * @param time
	 * @return
	 */
	public static boolean checkToday(Date time) {
		long create = time.getTime();
		Date now = new Date();
		long ms = 86400000;// 毫秒数
		long ms_now = now.getTime();
		if (ms_now - create < ms) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取当天
	 * 
	 * @param format 时间格式
	 * @return
	 */
	public static String getToday(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	public static String getNum() throws ParseException {
		// 获取今天是周几
		String week = getTodayWeek();
		List<Map<String, Object>> list = getSecTime(week);
		// 获取当前时间
		String format = "HH:mm:ss";
		String nowDate = DateFormatInfo.getToday(format);
		for (int i = 0; i < list.size(); i++) {
			String beginTime = (String) list.get(i).get("beginTime");
			if (compTime(nowDate, beginTime)) {
				return list.get(i).get("name") + "";
			}
		}
		return "";
	}

	/**
	 * 比较时间 HH:mm:ss
	 * 
	 * @param format 时间格式
	 * @return
	 */
	private static boolean compTime(String mdate, String ndate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date compDay = sdf.parse(mdate);
		Date date = sdf.parse(ndate);
		return compDay.before(date);
	}

	// 获取今天是周几
	public static String getTodayWeek() {
		Calendar c = Calendar.getInstance();
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek + "";
	}

	public static List<Map<String, Object>> getSecTime(String week) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 8; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", i + 1);
			if (week.equals("5")) {
				switch (i + 1) {
				case 1:
					map.put("beginTime", "7:50");
					map.put("endTime", "8:35");
					break;
				case 2:
					map.put("beginTime", "8:45");
					map.put("endTime", "9:30");
					break;
				case 3:
					map.put("beginTime", "10:05");
					map.put("endTime", "10:50");
					break;
				case 4:
					map.put("beginTime", "11:00");
					map.put("endTime", "11:45");
					break;
				case 5:
					map.put("beginTime", "12:45");
					map.put("endTime", "13:30");
					break;
				case 6:
					map.put("beginTime", "13:40");
					map.put("endTime", "14:25");
					break;
				case 7:
					map.put("beginTime", "14:55");
					map.put("endTime", "15:40");
					break;
				default:
					map.put("beginTime", "15:50");
					map.put("endTime", "16:20");
					break;
				}
			} else {
				switch (i + 1) {
				case 1:
					map.put("beginTime", "7:50");
					map.put("endTime", "8:35");
					break;
				case 2:
					map.put("beginTime", "8:45");
					map.put("endTime", "9:30");
					break;
				case 3:
					map.put("beginTime", "10:05");
					map.put("endTime", "10:50");
					break;
				case 4:
					map.put("beginTime", "11:00");
					map.put("endTime", "11:45");
					break;
				case 5:
					map.put("beginTime", "13:00");
					map.put("endTime", "13:45");
					break;
				case 6:
					map.put("beginTime", "13:55");
					map.put("endTime", "14:45");
					break;
				case 7:
					map.put("beginTime", "14:55");
					map.put("endTime", "15:40");
					break;
				default:
					map.put("beginTime", "15:50");
					map.put("endTime", "16:20");
					break;
				}
			}
			list.add(map);
		}
		return list;
	}

	public static String formatDate(Date date, String format) {
		SimpleDateFormat time = new SimpleDateFormat(format);
		return time.format(date);
	}

	/**
	 * 格式化时间为日期
	 * 
	 * @param time
	 * @return
	 */
	public static Date formatTime(String time, String format) {
		SimpleDateFormat f = new SimpleDateFormat(format);
		Date compDay;
		try {
			compDay = f.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return compDay;
	}

	public static boolean isValidDate(String str) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (!convertSuccess) {
			try {
				format2.setLenient(false);
				format2.parse(str);
			} catch (ParseException e) {
				convertSuccess = false;
			}
		}
		return convertSuccess;
	}
}
