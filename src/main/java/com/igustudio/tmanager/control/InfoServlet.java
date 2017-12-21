package com.igustudio.tmanager.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.igustudio.tmanager.model.MonitorModel;
import com.igustudio.tmanager.util.Byteutils;
import com.igustudio.tmanager.util.RuntimeInfoAccessorBean;
import com.igustudio.tmanager.util.RuntimeInformation;
import com.igustudio.tmanager.util.TimeUtils;
import com.jezhumble.javasysmon.CpuTimes;
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
			
			 
			String msg = "获取成功！";
			
			logger.info(msg);
			
			json = new JSONObject();
			
			JSONObject baseInfo = new JSONObject();
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			//获取当前系统时间
			baseInfo.put("currentTime", df.format(new Date()));

			
			//系统运行时间
			baseInfo.put("osRunTime",TimeUtils.duration( javaMon.uptimeInSeconds()*1000, "zh"));

			 // Get JVM's thread system bean
	        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();

	        // Get start time
	        long startTime = bean.getStartTime();
	        
	        long collapseTime = System.currentTimeMillis()-startTime;
	        
	        //Tomcat运行时间
			baseInfo.put("tomcatRunTime",TimeUtils.duration(collapseTime, "zh"));

			//tomcat目录
			baseInfo.put("tomcatPath",System.getProperty("catalina.home"));
			
			json.put("baseInfo", baseInfo);

			
			JSONObject menInfo = new JSONObject();
			
			
			long totalMemory=Runtime.getRuntime().totalMemory();
			long freeMemory=Runtime.getRuntime().totalMemory();
			long maxMemory=Runtime.getRuntime().maxMemory();
			
			long memUsed=(totalMemory - freeMemory) * 100 /maxMemory;
			
			int cpuCount=Runtime.getRuntime().availableProcessors();
		
			
			RuntimeInformation  runtimeInformation=RuntimeInfoAccessorBean.getRuntimeInformation();
			
			long totalPhysicalMemorySize=runtimeInformation.getTotalPhysicalMemorySize();
			long freePhysicalMemorySize =runtimeInformation.getFreePhysicalMemorySize();
			long committedVirtualMemorySize= runtimeInformation.getCommittedVirtualMemorySize();
			long totalSwapSpaceSize =runtimeInformation.getTotalPhysicalMemorySize();
			long freeSwapSpaceSize =runtimeInformation.getFreeSwapSpaceSize();
			
			
			menInfo.put("freeMemory", Byteutils.formatByte(javaMon.physical().getTotalBytes(), 2));
			menInfo.put("memUsed", Byteutils.formatByte(javaMon.physical().getTotalBytes(), 2));
			menInfo.put("freeSwapSpaceSize", Byteutils.formatByte(freeSwapSpaceSize, 2));
			
			
			
			MonitorModel model = new MonitorModel();
			model.setOsName(javaMon.osName());
			model.setOsUptime(javaMon.uptimeInSeconds());
			model.setRamTotal(javaMon.physical().getTotalBytes());
			model.setRamFree(javaMon.physical().getFreeBytes());
			model.setSwapTotal(javaMon.physical().getTotalBytes());
			model.setSwapFree(javaMon.physical().getFreeBytes());
			model.setCpuNum(javaMon.numCpus());
			model.setCpuFrequency(javaMon.cpuFrequencyInHz());
			
			
			
			CpuTimes initialTimes = javaMon.cpuTimes();
			float useTime=javaMon.cpuTimes().getCpuUsage(initialTimes);
		
			json.put("currentTime1", System.getProperties());
//			json.put("data", model);
			
			
			
			
			json.put("menInfo", menInfo);
			json.put("code", 1);
			json.put("msg", msg);
			 
		} catch (Exception e) {
			logger.info(e);
		}

		writer.write(json.toString());
	}

	
	

}
