package com.igustudio.tmanager.utils;

import java.text.NumberFormat;

public class Byteutils {

	public static final double KB = 1024;
	public static final double MB = KB * 1024;
	public static final double GB = MB * 1024;
	public static final double TB = GB * 1024;

	/**
	 * 格式化字节数，以KB,MB等结尾，并指定精度
	 * 
	 * @param value
	 * @param fractions 保留小数点位数
	 * @return
	 */
	public static String formatByte(long value, int fractions) {
		double doubleResult;
		String suffix;

		if (value < KB) {
			doubleResult = value;
			suffix = "B";
		} else if (value >= KB && value < MB) {
			doubleResult = round(value / KB, fractions);
			suffix = "KB";
		} else if (value >= MB && value < GB) {
			doubleResult = round(value / MB, fractions);
			suffix = "MB";
		} else if (value >= GB && value < TB) {
			doubleResult = round(value / GB, fractions);
			suffix = "GB";
		} else {
			doubleResult = round(value / TB, fractions);
			suffix = "TB";
		}

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(fractions);
		return nf.format(doubleResult) + " " + suffix;
	}

	private static double round(double value, int fractions) {
		return Math.round(value * Math.pow(10, fractions))
				/ Math.pow(10, fractions);
	}

}
