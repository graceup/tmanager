package com.igustudio.tmanager.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取磁盘信息
 * 
 * @author gu
 * @date  2017年12月22日
 */
public class DiskFreeUtils {
	
	private static DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");

	public static List<Map<String, String>> getInfo() {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		File[] roots = File.listRoots();// 获取磁盘分区列表
		for (File file : roots) {
			Map<String, String> map = new HashMap<String, String>();
			
			long freeSpace=file.getFreeSpace();
			long totalSpace=file.getTotalSpace();
			long usableSpace=totalSpace-freeSpace;
			
			map.put("path", file.getPath());
			map.put("freeSpace", freeSpace / 1024 / 1024 / 1024 + "G");// 空闲空间
			map.put("usableSpace", usableSpace / 1024 / 1024 / 1024 + "G");// 可用空间
			map.put("totalSpace",totalSpace / 1024 / 1024 / 1024 + "G");// 总空间
			map.put("percent", DECIMALFORMAT.format(((double)usableSpace/(double)totalSpace)*100)+"%");// 总空间
			
			list.add(map);
		}

		return list;
	}

	public static void main(String[] args) {
		
		System.out.println(getInfo());
		
	}

}
