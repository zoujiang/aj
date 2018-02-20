package com.frame.core.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.frame.core.constant.Constant;
import com.frame.system.vo.UserExtForm;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public class SessionFilter extends OncePerRequestFilter  {
	private static final String FILETERED_REQUEST="@@session_context_filtered_request";
	private static final String[] INHERENT_URLS={"/login","/index","/api","/changeLocale","/photoShare","/shopShare","/ftp"};
	
	
	private boolean isURILogin(String requestURI){
		for(String uri:INHERENT_URLS){
			if(requestURI!=null && requestURI.indexOf(uri)>=0){
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request!=null && request.getAttribute(FILETERED_REQUEST)!=null){
			filterChain.doFilter(request, response);
		}else{
			request.setAttribute(FILETERED_REQUEST, Boolean.TRUE);
			UserExtForm userExtForm =(UserExtForm) request.getSession().getAttribute(Constant.LoginAdminUser);
			String loginUrl=request.getContextPath()+"/";
			if(request.getSession().getAttribute("sessionId")==null && userExtForm==null && !isURILogin(request.getRequestURI())){
				if(request.getHeader("x-requested-with")!=null
						&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
					PrintWriter printWriter = response.getWriter();
					//必须给easyui返回rows属性，easyui框架的bug
					printWriter.print("{\"sessionState\":\"0\",\"loginUrl\":\""+loginUrl+"\",\"rows\":[]}");
					printWriter.flush();
					printWriter.close();
					return ;
				} else{
					request.getRequestDispatcher("/").forward(request, response);
					return ;
				}
				//return ;
			}
			if(userExtForm==null && !isURILogin(request.getRequestURI())){
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				if(request.getHeader("x-requested-with")!=null
						&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
					PrintWriter printWriter = response.getWriter();
					StringBuilder builder=new StringBuilder();
					builder.append("<script type=\"text/javascript\">");
					builder.append("window.top.location.href='");
					builder.append(loginUrl);
					builder.append("';");
					builder.append("</script>");
					printWriter.println(builder.toString());
					printWriter.flush();
					printWriter.close();
					return;
				} 
				String servletPath=request.getServletPath();
				request.getRequestDispatcher(servletPath+"/").forward(request, response);
				return;
			}
			if(!response.isCommitted()){
				filterChain.doFilter(request, response);
			}
		}

		
	}

}
