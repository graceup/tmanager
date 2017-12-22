package com.igustudio.tmanager.utils;

/**
 * 时间转换工具类
 * 
 * @author gu
 * 
 * @date  2017年12月21日
 */
public class TimeUtils {
	
	/**
	 * 
	 * @param value
	 * @param lang 语言格式  zh为中文
	 * @return
	 */
	public static String duration(long value,String lang) {
		long millis = value % 1000;
		long sec = value / 1000;
		long mins = sec / 60;
		long hours = mins / 60;
		long day = hours / 24;
		if(day > 0){
			hours = hours % 24;
		}

		sec = sec % 60;
		mins = mins % 60;

		if (lang != null && "zh".equalsIgnoreCase(lang)) {
			return day + "天 " + hours + "小时 " + long2Str(mins) + "分钟 "
					+ long2Str(sec) + "秒";
		} else {
			return hours + ":" + long2Str(mins) + ":" + long2Str(sec) + "."
					+ long3Str(millis);
		}
	}
	
	private static String long2Str(long l) {
		return l < 10 ? "0" + l : Long.toString(l);
	}

	private static String long3Str(long l) {
		return l < 10 ? "00" + l : l < 100 ? "0" + l : Long.toString(l);
	}

}
