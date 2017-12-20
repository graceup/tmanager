package com.igustudio.tmanager.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 * 
 * 用户 登录、登出以及注册servlet
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

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String oper = request.getParameter("oper");
		if ("login".equals(oper)) {
			login(request, response);
		} else if ("leave".equals(oper)) {
			leave(request, response);
		} else if ("register".equals(oper)) {
			register(request, response);
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
		String digest = null;
		if (null != password) {
			digest = DigestUtils.md5Hex(password);
		}
		logger.info("登陆系统，用户名：" + username + "，密码：******");
		JSONObject json = null;

		try {

			if ("admin".equals(username)) {
				session.setAttribute("user", username);
				String msg = "用户" + username + "登陆成功";
				logger.info(msg);
				json = new JSONObject();
				json.put("retval", "ok");
				json.put("errormsg", msg);
			} else {
				String msg = "用户名或者密码错误！";
				logger.info(msg);
				json = new JSONObject();
				json.put("retval", "error");
				json.put("errormsg", msg);
			}
		} catch (Exception e) {
			logger.info(e);
		}

		writer.write(json.toString());
	}

	/**
	 * 注册
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String digest = null;
		if (null != password) {
			digest = DigestUtils.md5Hex(password);
		}
		String userId = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");

		JSONObject json = null;
		try {

			// String msg = "用户" + username + "注册成功";
			// logger.info(msg);
			// json = new JSONObject();
			// json.put("retval", "ok");
			// json.put("errormsg", msg);

			String msg = "注册不成功,账号名称已存在！";
			logger.info(msg);
			json = new JSONObject();
			json.put("retval", "error");
			json.put("errormsg", msg);

		} catch (Exception e) {
			logger.info(e);
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
		response.sendRedirect("login.html");
	}

}
