package com.cha.transcoder;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggerInterceptor extends HandlerInterceptorAdapter {

	protected Log log = LogFactory.getLog(LoggerInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (log.isDebugEnabled()) {
			//log.debug("=======================          START         =======================");
			//log.debug("Request URI=" + request.getRequestURI()); 
			//printParameters(request);

		}
		return super.preHandle(request, response, handler);
	}

	private void printParameters(HttpServletRequest request) {
		StringBuffer buffer = new StringBuffer();

		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String[] value = request.getParameterValues(name);

			buffer.append(name + "=");
			for (int i = 0; i < value.length; i++) {
				if (i != 0) {
					buffer.append(",");
				}
				buffer.append(value[i]);
			}
			buffer.append("|");
		}

		log.debug("parameters=" + buffer.toString());
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (log.isDebugEnabled()) {
			//log.debug("=======================           END          =======================\n");
		}
	}
}
