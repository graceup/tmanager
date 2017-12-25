package com.igustudio.tmanager.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.igustudio.tmanager.utils.Byteutils;
import com.igustudio.tmanager.utils.TomcatUtils;

/**
 * 
 * 日志相关信息 控制类
 * 
 * @author gu<br/>
 * @version 1.0<br/>
 */
public class LogServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4197039584252963499L;

	private static final Logger logger = Logger.getLogger(LogServlet.class);

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/plain;charset=UTF-8");
		

		String oper = request.getParameter("oper");
		if ("list".equals(oper)) {
			list(request, response);
		} else if ("delete".equals(oper)) {
			delete(request, response);
		} else if ("trace".equals(oper)) {
//			trace(request, response);
		} else if ("download".equals(oper)) {
			download(request, response);
		}
		
		
	
		
	}
	
	
	/**
	 * 下载日志
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void download(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String filName = request.getParameter("filName");
		
		try {
			
			String  logPath=System.getProperty("catalina.home")+"/logs/"+filName;
			
			File logFile = new File(logPath);
			
			
			if (!logFile.exists()) {
				PrintWriter writer = response.getWriter();
				writer.write("文件[" + logPath + "]不存在！");
			} else {
				TomcatUtils.sendFile(request, response, logFile);
			}
			
		} catch (Exception e) {
			PrintWriter writer = response.getWriter();
			writer.write("出现异常："+e.getMessage());
			logger.error("info error:" + e.getMessage(), e);
		}
		
		
		
	}

	/**
	 * 删除日志
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		JSONObject json = new JSONObject();
		
		String filName = request.getParameter("filName");
		
		try {
			
			String  logPath=System.getProperty("catalina.home")+"/logs/"+filName;
			
			File logFile = new File(logPath);
			 
			
			if(logFile.delete()){
				json.put("code", 0);
			}else{
				json.put("code", 1);
			}
			
			
		} catch (Exception e) {
			json.put("code", 1);
			logger.error("info error:" + e.getMessage(), e);
		}

		String msg = "获取成功！";
		logger.info(msg);
		json.put("msg", msg);
		
		writer.write(json.toString());
		
	}

	
	/**
	 * 显示日志列表
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		JSONObject json = null;

		try {

			json = new JSONObject();

			
			String  logPath=System.getProperty("catalina.home")+"/logs";
			
			File logDir = new File(logPath);
			File[] files = logDir.listFiles();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for (File f : files) {
				JSONObject lf = new JSONObject();
				lf.put("fileName",f.getName());
//				lf.put("filePath",f.getAbsolutePath().replaceAll("\\\\", "/"));
				lf.put("fileSize",Byteutils.formatByte(f.length(), 2));
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				lf.put("lastModified",sdf.format(new Date(f.lastModified())));
				 
				list.add(lf);
			}
			

			String msg = "获取成功！";

			logger.info(msg);

			json.put("filePath", logPath);
			
			json.put("list", list);
			
			json.put("code", 1);
			json.put("msg", msg);

		} catch (Exception e) {
			logger.info("info error:" + e.getMessage(), e);
		}

		writer.write(json.toString());

	}

}
