package com.mio.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mio.ecache.MySession;
import com.mio.util.RandomNumStrUtil;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class MySeesionFilter implements Filter {

	private CacheManager cacheManager = CacheManager.getInstance();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {
		

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("=====================MySeesionFilter=====================");
		HttpServletRequest hrequest = (HttpServletRequest) request;
		HttpServletResponse hresponse =(HttpServletResponse) response;
		String sessionId = "";
		boolean isHave =  false;
		Cookie[] cookies = hrequest.getCookies();
		 for (int i = 0; i < cookies.length; i++) {
             if ("JSESSIONID".equals(cookies[i].getName())) {
                     sessionId = cookies[i].getValue();
                     isHave = true;
                     System.out.println("sessionId:"+sessionId);
                 break;
             }
        }
		 if(!isHave){
			 sessionId =(new Date()).getTime()+ RandomNumStrUtil.createRandomAndStr(RandomNumStrUtil.createRandomNum(6, 10));
			 Cookie c = new Cookie("JSESSIONID", sessionId);
			 System.out.println("create sessionId:"+sessionId);
			 hresponse.addCookie(c);
		 }
		
		this.addSession(sessionId);
		chain.doFilter(request, response);
	}

	
		public MySession addSession(String sessionId) {
			Cache cache = cacheManager.getCache("sessions");
			Element element = cache.get(sessionId);

			if (element == null) {
				System.out.println("add session:"+sessionId);
				MySession s = new MySession();
				s.setSessionId(sessionId);
				s.setSessionName("hha");
				Element element2 = new Element(s.getSessionId(), s);
				cache.put(element2);
				return s;
			}
			return null;
		}

}
