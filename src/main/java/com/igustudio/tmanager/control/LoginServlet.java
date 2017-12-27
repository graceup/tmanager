package com.igustudio.tmanager.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.igustudio.tmanager.utils.ConfigUtil;

/**
 * 
 * 用户 登录、登出servlet
 * 
 * @author gu<br/>
 * @version 1.0<br/>
 */
public class LoginServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(LoginServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 4748429688545698854L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		String configFilePath=config.getServletContext().getRealPath("")+"/WEB-INF/classes/config.properties"; 
		ConfigUtil.init(configFilePath);
		
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String oper = request.getParameter("oper");
		if ("login".equals(oper)) {
			login(request, response);
		} else if ("leave".equals(oper)) {
			leave(request, response);
		}
	}

	/**
	 * 登陆
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
	
		logger.info("登陆系统，用户名：" + username + "，密码：******");
		JSONObject json = new JSONObject();
		try {
			
			String configUsername=ConfigUtil.getProperty("username");
			String configPassword=ConfigUtil.getProperty("password");

			if (configUsername.equals(username)&&configPassword.equals(password)) {
				session.setAttribute("user", username);
				String msg = "用户" + username + "登陆成功";
				logger.info(msg);
				json.put("code", 0);
				json.put("msg", msg);
			} else {
				String msg = "用户名或者密码错误！";
				logger.info(username+msg);
				json.put("code", 1);
				json.put("msg", msg);
			}
		} catch (Exception e) {
			json.put("code", 1);
			json.put("msg", "配置不正确");
			logger.error(e);
		}

		writer.write(json.toString());
	}

	 

	/**
	 * 注销
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void leave(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Object object = session.getAttribute("user");
		if (object != null) {
			logger.info("用户" + object.toString() + "退出系统！");

			session.removeAttribute("user");
			session.invalidate();
		}
		
		JSONObject json = new JSONObject();
		json.put("code", 0);
		json.put("msg", "logout success.");
		PrintWriter writer = response.getWriter();
		writer.write(json.toString());
	}

}
