package com.wsc.qa.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import com.wsc.qa.service.UserService;

@Order(1)
@WebFilter(filterName = "myfilter", urlPatterns = "/*")
public class MyFilter implements Filter {

	@Autowired
	private UserService userServiceImpl;
    @Override
    public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) srequest;
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		if(isInclude(request.getRequestURI())) {
			System.out.println("this is MyFilter,url :"+request.getRequestURI());
			if (null != userName &&null != userServiceImpl.getUserInfo(userName)) {
				filterChain.doFilter(srequest, sresponse);
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
	            dispatcher.forward(srequest, sresponse);
			}
		}else {
			filterChain.doFilter(srequest, sresponse);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	 /**
     * 是否需要过滤
     * @param url
     * @return
     */
    private boolean isInclude(String url) {
    	 /**
         * 封装，不需要过滤的list列表
         */
        List<Pattern> patterns = new ArrayList<Pattern>();
        patterns.add(Pattern.compile(".*login$"));
        patterns.add(Pattern.compile(".*register$"));
        patterns.add(Pattern.compile(".*error$"));
        patterns.add(Pattern.compile(".*ops$"));
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                return false;
            }
        }
        return true;
    }
}