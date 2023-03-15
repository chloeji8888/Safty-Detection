package com.jszy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {
	public static final String YYMMDD = "YY-MM-DD";
	public static final String YYMMDD_CHS = "YY年MM月DD日";
	public static final String HHMISS = "HH24:MI:SS";
	public static final String HHMISS_CHS = "HH24点MI分SS秒";
	public static final String YYMMDD_HHMISS = "YY-MM-DD HH24:MI:SS";
	public static final String YYMMDD_HHMISS_CHS = "YY年MM月DD日HH24点MI分SS秒";

	public static String getYear(String paramString) {
		if (StringUtil.nullValue(paramString).length() == 0) {
			return "";
		}
		int i = paramString.indexOf('-');
		if (i == -1) {
			return "";
		}
		return paramString.substring(0, i);
	}

	public static String getMonth(String paramString) {
		int i = paramString.indexOf('-');
		if (i == -1) {
			return "";
		}
		int j = paramString.indexOf('-', i + 1);
		if (j == -1) {
			return "";
		}
		int k = Convertor.toInt(paramString.substring(i + 1, j), 0);
		if ((k < 1) || (k > 12)) {
			return "";
		}
		return "" + k;
	}

	public static String getDate(String paramString) {
		int i = paramString.indexOf('-');
		if (i == -1) {
			return "";
		}
		i = paramString.indexOf('-', i + 1);
		if (i == -1) {
			return "";
		}
		int j = paramString.indexOf(' ', i);
		if (j == -1) {
			j = paramString.length();
		}
		int k = Convertor.toInt(paramString.substring(i + 1, j), 0);
		if ((k < 1) || (k > 31)) {
			return "";
		}
		return "" + k;
	}

	public static String getHour(String paramString) {
		int i = paramString.indexOf(' ');
		if (i == -1) {
			return "0";
		}
		int j = paramString.indexOf(':', i + 1);
		if (j == -1) {
			return "0";
		}
		int k = Convertor.toInt(paramString.substring(i + 1, j), -1);
		if ((k < 0) || (k > 59)) {
			return "";
		}
		return "" + k;
	}

	public static Date toDate(String paramString) {
		if (!isDate(paramString))
			return null;
		try {
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			return localSimpleDateFormat.parse(getDateString(paramString,
					"YY-MM-DD HH24:MI:SS"));
		} catch (ParseException localParseException) {
		}
		return null;
	}

	public static long dateDiff(String paramString1, String paramString2,
			int paramInt) {
		Date localDate1 = toDate(paramString1);
		Date localDate2 = toDate(paramString2);
		if ((localDate1 == null) || (localDate2 == null))
			return 0L;

		long l = localDate2.getTime() - localDate1.getTime();

		if (paramInt == 1) {
			return Convertor.toLong(l / 1000L + "");
		}
		if (paramInt == 2) {
			return Convertor.toLong(l / 1000L / 60L + "");
		}
		if (paramInt == 3) {
			return Convertor.toLong(l / 1000L / 3600L + "");
		}
		if (paramInt == 4) {
			return Convertor.toLong(l / 1000L / 86400L + "");
		}
		return l;
	}

	public static String dateAdd(String paramString, long paramLong,
			int paramInt) {
		Date localDate = toDate(paramString);
		if (localDate == null)
			return null;
		if (paramInt == 1) {
			paramLong *= 1000L;
		} else if (paramInt == 2) {
			paramLong = paramLong * 1000L * 60L;
		} else if (paramInt == 3) {
			paramLong = paramLong * 1000L * 3600L;
		} else if (paramInt == 4) {
			paramLong = paramLong * 1000L * 86400L;
		}

		long l = localDate.getTime() + paramLong;
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTimeInMillis(l);
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return localSimpleDateFormat.format(localCalendar.getTime());
	}

	public static String getMinute(String paramString) {
		paramString = paramString.trim();
		int i = paramString.indexOf(" ");
		if (i == -1) {
			return "0";
		}

		i = paramString.indexOf(":", i);
		if (i == -1) {
			return "0";
		}
		int j = paramString.indexOf(":", i + 1);
		if (j == -1) {
			j = paramString.length();
		}
		return paramString.substring(i + 1, j);
	}

	public static String getSecond(String paramString) {
		paramString = paramString.trim();
		int i = paramString.indexOf(" ");
		if (i == -1) {
			return "0";
		}

		i = paramString.indexOf(":", i);
		if (i == -1) {
			return "0";
		}
		i = paramString.indexOf(":", i + 1);
		if (i == -1) {
			return "0";
		}
		int j = paramString.indexOf(".", i + 1);
		if (j == -1) {
			j = paramString.length();
		}

		return paramString.substring(i + 1, j);
	}

	public static boolean isDate(String paramString) {
		if (paramString == null) {
			return false;
		}
		if (paramString.length() == 0) {
			return false;
		}
		int i = paramString.indexOf(' ');
		String str1 = "";
		String str2 = "";
		if (i == -1) {
			str1 = paramString;
		} else {
			str1 = paramString.substring(0, i);
			str2 = paramString.substring(i + 1);
		}
		String[] arrayOfString = StringUtil.split(str1, "-");
		if (arrayOfString.length != 3) {
			return false;
		}
		if ((Convertor.toInt(arrayOfString[0], -1) < 0)
				|| (Convertor.toInt(arrayOfString[1], 0) < 1)
				|| (Convertor.toInt(arrayOfString[1], 0) > 12)
				|| (Convertor.toInt(arrayOfString[1], 0) < 1)
				|| (Convertor.toInt(arrayOfString[1], 0) > 31)) {
			return false;
		}
		if (str2.length() > 0) {
			i = str2.indexOf(".");
			if (i != -1) {
				Object localObject = str2.substring(i + 1);
				if (!Convertor.isInt((String) localObject)) {
					return false;
				}
				str2 = str2.substring(0, i);
			}
			String[] localObject = StringUtil.split(str2, ":");
			if (localObject.length != 3) {
				return false;
			}
			for (int j = 0; j < 3; j++) {
				if ((Convertor.toInt(localObject[j], -1) < 0)
						|| (Convertor.toInt(localObject[j], -1) > 59)) {
					return false;
				}
			}
		}
		return true;
	}

	public static String getDateString(String paramString1,
			String paramString2, String paramString3) {
		return getDateString(paramString1, paramString2, paramString3, "~");
	}

	public static String getDateString(String paramString1,
			String paramString2, String paramString3, String paramString4) {
		if ((paramString1.length() == 0) && (paramString2.length() == 0)) {
			return "";
		}
		if (paramString1.length() == 0) {
			return getDateString(paramString2, paramString3);
		}
		if (paramString2.length() == 0) {
			return getDateString(paramString1, paramString3);
		}
		String str = paramString3;

		if ((str.indexOf("YY") != -1)
				&& (str.indexOf("MM") != -1)
				&& (getYear(paramString2)
						.equalsIgnoreCase(getYear(paramString1)))) {
			str = str.substring(str.indexOf("MM"));
			if ((paramString3.indexOf("DD") != -1)
					&& (getMonth(paramString2)
							.equalsIgnoreCase(getMonth(paramString1)))) {
				str = str.substring(str.indexOf("DD"));
				if ((paramString3.indexOf("HH") != -1)
						&& (getDate(paramString2)
								.equalsIgnoreCase(getDate(paramString1)))) {
					str = str.substring(str.indexOf("HH"));
				}

			}

		}

		return getDateString(paramString1, paramString3) + paramString4
				+ getDateString(paramString2, str);
	}

	public static String getDateString(String paramString1, String paramString2) {
		if (paramString1.length() == 0) {
			return "";
		}
		String str1 = paramString2;
		str1 = StringUtil.replace(str1, "YY", getYear(paramString1));
		str1 = StringUtil.replace(str1, "0MM",
				StringUtil.lpad(getMonth(paramString1), '0', 2));
		str1 = StringUtil.replace(str1, "MM", getMonth(paramString1));
		str1 = StringUtil.replace(str1, "0DD",
				StringUtil.lpad(getDate(paramString1), '0', 2));
		str1 = StringUtil.replace(str1, "DD", getDate(paramString1));
		String str2 = getHour(paramString1);
		str1 = StringUtil.replace(str1, "0HH24", StringUtil.lpad(str2, '0', 2));
		str1 = StringUtil.replace(str1, "HH24", str2);
		str1 = StringUtil.replace(str1, "0HH", StringUtil.lpad(str2, '0', 2));
		str1 = StringUtil.replace(str1, "HH", str2);
		str1 = StringUtil.replace(str1, "0MI",
				StringUtil.lpad(getMinute(paramString1), '0', 2));
		str1 = StringUtil.replace(str1, "MI", getMinute(paramString1));
		str1 = StringUtil.replace(str1, "0SS",
				StringUtil.lpad(getSecond(paramString1), '0', 2));
		str1 = StringUtil.replace(str1, "SS", getSecond(paramString1));
		return str1;
	}

	public static String getDateString(Calendar paramCalendar,
			String paramString) {
		String str = paramCalendar.get(1) + "-" + (paramCalendar.get(2) + 1)
				+ "-" + paramCalendar.get(5) + " " + paramCalendar.get(11)
				+ ":" + paramCalendar.get(12) + ":" + paramCalendar.get(13);
		return getDateString(str, paramString);
	}

	public static int compareDate(String paramString1, String paramString2) {
		if (paramString1 == null) {
			paramString1 = "";
		}
		if (paramString2 == null) {
			paramString2 = "";
		}
		paramString1 = toEngDateString(paramString1);
		paramString2 = toEngDateString(paramString2);
		if (paramString1.indexOf(' ') == -1) {
			paramString1 = paramString1 + " 00:00:00";
		}
		if (paramString2.indexOf(' ') == -1) {
			paramString2 = paramString2 + " 00:00:00";
		}
		return paramString1.compareTo(paramString2);
	}

	public static String now() {
		return now("YY-MM-DD HH24:MI:SS");
	}

	public static String now(String paramString) {
		Calendar localCalendar = Calendar.getInstance();
		String str = localCalendar.get(1) + "-" + (localCalendar.get(2) + 1)
				+ "-" + localCalendar.get(5) + " " + localCalendar.get(11)
				+ ":" + localCalendar.get(12) + ":" + localCalendar.get(13);
		return getDateString(str, paramString);
	}

	public static String relativeDay(int paramInt) {
		return relativeDay(paramInt, "YY-MM-DD HH24:MI:SS");
	}

	public static String relativeDay(int paramInt, String paramString) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.add(5, paramInt);
		String str = localCalendar.get(1) + "-" + (localCalendar.get(2) + 1)
				+ "-" + localCalendar.get(5) + " " + localCalendar.get(11)
				+ ":" + localCalendar.get(12) + ":" + localCalendar.get(13);
		return getDateString(str, paramString);
	}

	public static String relativeDay(String paramString, int paramInt) {
		return relativeDay(paramString, paramInt, "YY-MM-DD HH24:MI:SS");
	}

	public static String relativeDay(String paramString1, int paramInt,
			String paramString2) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.set(1, Convertor.toInt(getYear(paramString1)));
		localCalendar.set(2, Convertor.toInt(getMonth(paramString1)) - 1);
		localCalendar.set(5, Convertor.toInt(getDate(paramString1)) - 1);
		localCalendar.set(11, Convertor.toInt(getHour(paramString1)));
		localCalendar.set(12, Convertor.toInt(getMinute(paramString1)));
		localCalendar.set(13, Convertor.toInt(getSecond(paramString1)));
		localCalendar.add(5, paramInt);
		String str = localCalendar.get(1) + "-" + (localCalendar.get(2) + 1)
				+ "-" + localCalendar.get(5) + " " + localCalendar.get(11)
				+ ":" + localCalendar.get(12) + ":" + localCalendar.get(13);
		return getDateString(str, paramString2);
	}

	public static String getTimeRemark(String paramString) {
		return getTimeRemark(Convertor.toInt(paramString));
	}

	public static String getTimeRemark(int paramInt) {
		return getTimeRemark(paramInt, "");
	}

	public static String getTimeRemark(int paramInt, String paramString) {
		int i = 0;
		int j = 0;
		int k = 0;
		if (paramInt > 86400) {
			i = (paramInt - paramInt % 86400) / 86400;
			paramInt -= i * 86400;
		}
		if (paramInt > 3600) {
			j = (paramInt - paramInt % 3600) / 3600;
			paramInt -= j * 3600;
		}
		if (paramInt > 60) {
			k = (paramInt - paramInt % 60) / 60;
			paramInt -= k * 60;
		}
		if (paramString.length() == 0) {
			return (i > 0 ? i + "天" : "")
					+ (j > 0 ? j + "小时" : "")
					+ (k > 0 ? k + "分" : "")
					+ (((j == 0) && (k == 0) && (paramInt == 0))
							|| (paramInt != 0) ? paramInt + "秒" : "");
		}

		String str = paramString;
		str = StringUtil.replace(str, "0DD", StringUtil.lpad("" + i, '0', 2));
		str = StringUtil.replace(str, "DD", "" + i);
		str = StringUtil.replace(str, "0HH", StringUtil.lpad("" + j, '0', 2));
		str = StringUtil.replace(str, "HH", "" + j);
		str = StringUtil.replace(str, "0MI", StringUtil.lpad("" + k, '0', 2));
		str = StringUtil.replace(str, "MI", "" + k);
		str = StringUtil.replace(str, "0SS",
				StringUtil.lpad("" + paramInt, '0', 2));
		str = StringUtil.replace(str, "SS", "" + paramInt);
		return str;
	}

	private static String toEngDateString(String paramString) {
		paramString = paramString.replace('年', '-');
		paramString = paramString.replace('月', '-');
		paramString = paramString.replace('日', '-');
		paramString = paramString.replace(' ', '-');
		paramString = paramString.replace(':', '-');

		String[] arrayOfString = StringUtil.split(paramString, "-");

		String str = "";
		int i = 0;
		for (int j = 0; j < arrayOfString.length; j++) {
			if (arrayOfString[j].length() == 0) {
				continue;
			}
			if (i != 0) {
				if (i < 3) {
					str = str + "-";
				} else if (i == 3) {
					str = str + " ";
				} else
					str = str + ":";
			}
			if ((i == 0) && (arrayOfString[j].length() == 2)) {
				str = str + "20";
			}
			if (arrayOfString[j].length() == 1) {
				str = str + "0";
			}
			str = str + arrayOfString[j];
			i++;
		}
		if (str.indexOf('.') != -1) {
			str = str.substring(0, str.indexOf('.'));
		}
		return str;
	}
}