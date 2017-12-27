package com.igustudio.tmanager.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.igustudio.tmanager.utils.Byteutils;
import com.igustudio.tmanager.utils.DiskFreeUtils;
import com.igustudio.tmanager.utils.JmxUtils;
import com.igustudio.tmanager.utils.TimeUtils;
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

	/**
	 * 日志
	 */
	private static final Logger logger = Logger.getLogger(InfoServlet.class);

	/**
	 * 格式化小数
	 */
	private static DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject json = null;

		try {

			json = new JSONObject();

			JSONObject baseInfo = new JSONObject();

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			// 获取当前系统时间
			baseInfo.put("currentTime", df.format(new Date()));

			JavaSysMon javaMon = new JavaSysMon();

			// 系统运行时间
			baseInfo.put("osRunTime", TimeUtils.duration(javaMon.uptimeInSeconds() * 1000, "zh"));

			// Get JVM's thread system bean
			RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();

			// Get start time
			long startTime = bean.getStartTime();

			long collapseTime = System.currentTimeMillis() - startTime;

			// Tomcat运行时间
			baseInfo.put("tomcatRunTime", TimeUtils.duration(collapseTime, "zh"));

			// tomcat目录
			baseInfo.put("tomcatPath", System.getProperty("catalina.home"));

			json.put("baseInfo", baseInfo);

			JSONObject menInfo = new JSONObject();
			JSONObject cpuInfo = new JSONObject();

			try {

				long totalMemory = Runtime.getRuntime().totalMemory();
				long freeMemory = Runtime.getRuntime().freeMemory();
				long maxMemory = Runtime.getRuntime().maxMemory();

				double memUsed = (((double) totalMemory - (double) freeMemory) / (double) maxMemory) * 100;

				menInfo.put("memUsed", DECIMALFORMAT.format(memUsed) + "%");
				menInfo.put("freeMemory", Byteutils.formatByte(freeMemory, 2));
				menInfo.put("totalMemory", Byteutils.formatByte(totalMemory, 2));
				menInfo.put("maxMemory", Byteutils.formatByte(maxMemory, 2));

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			try {

				MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

				ObjectName osOName = new ObjectName("java.lang:type=OperatingSystem");

				ObjectName runtimeOName = new ObjectName("java.lang:type=Runtime");

				if (!JmxUtils.getStringAttr(mBeanServer, runtimeOName, "VmVendor").startsWith("IBM Corporation")) {

					long freePhysicalMemorySize = JmxUtils.getLongAttr(mBeanServer, osOName, "FreePhysicalMemorySize");
					long committedVirtualMemorySize = JmxUtils.getLongAttr(mBeanServer, osOName,
							"CommittedVirtualMemorySize");

					long totalSwapSpaceSize = JmxUtils.getLongAttr(mBeanServer, osOName, "TotalSwapSpaceSize");
					long freeSwapSpaceSize = JmxUtils.getLongAttr(mBeanServer, osOName, "FreeSwapSpaceSize");

					menInfo.put("freePhysicalMemorySize", Byteutils.formatByte(freePhysicalMemorySize, 2));
					menInfo.put("committedVirtualMemorySize", Byteutils.formatByte(committedVirtualMemorySize, 2));
					menInfo.put("totalSwapSpaceSize", Byteutils.formatByte(totalSwapSpaceSize, 2));
					menInfo.put("freeSwapSpaceSize", Byteutils.formatByte(freeSwapSpaceSize, 2));

					long totalPhysicalMemorySize = JmxUtils.getLongAttr(mBeanServer, osOName,
							"TotalPhysicalMemorySize");
					menInfo.put("totalPhysicalMemorySize", Byteutils.formatByte(totalPhysicalMemorySize, 2));
				} else {
					long totalPhysicalMemorySize = JmxUtils.getLongAttr(mBeanServer, osOName, "TotalPhysicalMemory");
					menInfo.put("totalPhysicalMemorySize", Byteutils.formatByte(totalPhysicalMemorySize, 2));
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			try {

				CpuTimes initialTimes = javaMon.cpuTimes();
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				float cpuUsage = javaMon.cpuTimes().getCpuUsage(initialTimes) * 100;

				cpuInfo.put("cpuUsage", DECIMALFORMAT.format(cpuUsage) + "%");
				cpuInfo.put("cpuNum", javaMon.numCpus() + "核");
				cpuInfo.put("cpuFrequency", javaMon.cpuFrequencyInHz() / (1000 * 1000 * 1000) + " GHz");

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			try {
				json.put("diskInfo", DiskFreeUtils.getInfo());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			// json.put("properties", System.getProperties());

			String msg = "系统信息获取成功！";

			logger.info(msg);

			json.put("cpuInfo", cpuInfo);
			json.put("menInfo", menInfo);
			json.put("code", 1);
			json.put("msg", msg);

		} catch (Exception e) {
			logger.error("info error:" + e.getMessage(), e);
		}

		writer.write(json.toString());

	}

}
