package com.igustudio.tmanager.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author gu<br/>
 * @version 1.0<br/>
 */
public class ConfigUtil {

	public static Properties properties = null;

	/**
	 * 
	 */
	public static void init(String configFilePath) {
		InputStream in = null;
		try {
			if (properties == null) {
				properties = new Properties();
				in = new BufferedInputStream(new FileInputStream(configFilePath));
				properties.load(in);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		if (properties != null) {
			return properties.getProperty(key);
		}

		return null;
	}

}
