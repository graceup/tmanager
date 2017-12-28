package com.igustudio.tmanager.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;


/**
 * 
 * url路径过滤器
 * 
 */
public class UrlFilter implements Filter {
	
	 private String[] excludes;

	public void init(FilterConfig config) throws ServletException {
		
		//获取不拦截url
		 String excludedPage = config.getInitParameter("excludes");
	        if (excludedPage != null && excludedPage.length() > 0){
	            excludes = excludedPage.split(",");
	        }

	}



	/**
	 * 拦截过滤url
	 */
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		// 获取 resquest、response、session
        HttpSession session = request.getSession();
        
        
        String path=request.getServletPath();

        // 定义表示变量 并验证用户请求URL 是否包含不过滤路径
        boolean flag = false;
        for (String excudePath:excludes) {
        	
        	if(excudePath.endsWith("/*")){
        		String subPath=excudePath.substring(0, excudePath.length()-1);
        		if(path.startsWith(subPath)){
        			 flag = true;
                     break;
        		}
        	}else if (path.equals(excudePath)){
                flag = true;
                break;
            }
        }
        
        if (flag){
        	filterChain.doFilter(request, response);
        }else{
        	//验证用户登录
			Object Object=session.getAttribute("user");
			if(null==Object){
				response.setCharacterEncoding("UTF-8");
				String type = request.getMethod();
				
				if("POST".equals(type)){
					PrintWriter writer = response.getWriter();
					JSONObject json = new JSONObject();
					json.put("code", 1);
					json.put("msg", "请重新登录");
					writer.write(json.toString());
				}else{
					 String context = request.getContextPath();
					 response.getWriter()
						.println(
								"<HTML><HEAD><TITLE></TITLE><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>"
										+ "</HEAD><BODY><script>alert('请重新登录');top.document.location='"
										+context+ "/login.html'</script></BODY></HTML>");
				
//					 request.getRequestDispatcher("/login.html").forward(request, response);
				
				}
				return;
				
			}else{
				filterChain.doFilter(request, response);
			}
		 
        }

	}

	public void destroy() {

	}

}
