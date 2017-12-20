package com.igustudio.tmanager.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.igustudio.tmanager.model.MonitorModel;
import com.jezhumble.javasysmon.JavaSysMon;

/**
 * 
 * 系统相关信息 控制类
 * 
 * @author gu<br/>
 * @version 1.0<br/>
 */
public class InfoServlet extends HttpServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2243668400971698202L;

	private static final Logger logger = Logger.getLogger(InfoServlet.class);

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject json = null;

		try {

			JavaSysMon javaMon = new JavaSysMon();
			
			MonitorModel model = new MonitorModel();
			model.setOsName(javaMon.osName());
			model.setOsUptime(javaMon.uptimeInSeconds());
			model.setRamTotal(javaMon.physical().getTotalBytes());
			model.setRamFree(javaMon.physical().getFreeBytes());
			model.setSwapTotal(javaMon.physical().getTotalBytes());
			model.setSwapFree(javaMon.physical().getFreeBytes());
			model.setCpuNum(javaMon.numCpus());
			model.setCpuFrequency(javaMon.cpuFrequencyInHz());
			
			
			System.out.println(model.toReadableString());
			 
			String msg = "获取成功！";
			
			logger.info(msg);
			
			json = new JSONObject();
			
			json.put("data", model);
			json.put("code", 1);
			json.put("msg", msg);
			 
		} catch (Exception e) {
			logger.info(e);
		}

		writer.write(json.toString());
	}


}
