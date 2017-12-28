package com.igustudio.tmanager.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
	
	/**
	 * 参数为要修改的文件路径  以及要修改的属性名和属性值  
	 * 
	 * @param path
	 * @param key
	 * @param value
	 * @return
	 */
    public static Boolean updatePro(String path,String key,String value){  
       
        properties.setProperty(key, value);   
        // 文件输出流   
        try {  
            FileOutputStream fos = new FileOutputStream(path);   
            // 将Properties集合保存到流中   
            properties.store(fos, "Copyright (c)");   
            fos.close();// 关闭流   
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
            return false;  
        } catch (IOException e) {  
            e.printStackTrace();  
            return false;  
        }  
        return true;  
    }  

}
