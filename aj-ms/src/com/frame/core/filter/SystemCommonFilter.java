package com.frame.core.filter;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.frame.system.service.SystemResourceManager;

//import com.spark.base.common.system.dic.SparkDictionaryManager;

public class SystemCommonFilter implements Filter {

	private static String contextRealPath;

	public static String getContextRealPath() {
		return contextRealPath;
	}

	private static void setContextRealPath(String contextRealPath) {
		SystemCommonFilter.contextRealPath = contextRealPath;
	}

	@Override
	public void destroy() {

	}

	@SuppressWarnings("deprecation")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		String fileName = req.getRealPath("") + "/index.jsp";
		File file = new File(fileName);
		if ("managerStatic".equals(req.getParameter("openType")) && file != null && file.exists()) {
			file.delete();
		}
		if (this.needStaticHtml(uri, req) && (null == file || !file.exists())) {
			response.setCharacterEncoding("GBK");
			FileCaptureResponseWrapper responseWrapper = new FileCaptureResponseWrapper((HttpServletResponse) response);
			chain.doFilter(request, responseWrapper);
			// String html = responseWrapper.toString();
			// System.out.println(html);
			responseWrapper.writeFile(fileName);
			PrintWriter out = response.getWriter();
			responseWrapper.writeResponse(out);
			res.setHeader("REFRESH", "0;URL=" + req.getContextPath() + "/index.jsp");
		} else {
			chain.doFilter(request, response);
		}
	}

	private boolean needStaticHtml(String uri, HttpServletRequest req) {
		if (uri.equals(req.getContextPath() + "/")) {
			return true;
		}
		if (uri.equals(req.getContextPath() + "/index.jsp")) {
			return true;
		}
		if (uri.equals(req.getContextPath() + "/pub/index.jsp") && "managerStatic".equals(req.getParameter("openType"))) {
			return true;
		}
		return false;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		setContextRealPath(arg0.getServletContext().getRealPath(""));
//		SparkDictionaryManager.init(arg0);
		SystemResourceManager.init(arg0);
	}

	public class FileCaptureResponseWrapper extends HttpServletResponseWrapper {
		private CharArrayWriter output;

		public String toString() {
			return output.toString();
		}

		public FileCaptureResponseWrapper(HttpServletResponse response) {
			super(response);
			output = new CharArrayWriter();
		}

		public PrintWriter getWriter() {
			return new PrintWriter(output);
		}

		public void writeFile(String fileName) {
			FileWriter fw = null;
			try {
				fw = new FileWriter(fileName);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				fw.write(new String(output.toString().getBytes(), "GBK"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void writeResponse(PrintWriter out) {
			out.print(output.toCharArray());
		}
	}
}
