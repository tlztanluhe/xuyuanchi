package rugal.sample.interceptor;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import rugal.sample.common.Const;
import rugal.sample.common.PropertyUtil;
import rugal.sample.core.vo.user.LoginUser;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	
	private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class.getName());
	

	public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o)
			throws Exception {
		
		String visitPath = request.getServletPath();
		List<String> encludeUrls = (List<String>) PropertyUtil.propertiesMap.get("encludeUrl");
		Boolean isCanVisit = false;
		
		log.info("当前访问路径--->"+visitPath);
		
		HttpSession session = request.getSession();
		LoginUser loginUser = (LoginUser) session.getAttribute(Const.user_login_key);
		
		if(loginUser == null){
			visitPath = visitPath.replaceAll("/+", "/");
			for(String url:encludeUrls){
				if(visitPath.startsWith(url)){
					isCanVisit = true;
				}
				
			}
			
			//未登录用户访问未经允许路径,跳转到未授权页面
			if(!isCanVisit){
				request.getRequestDispatcher("/403.jsp").forward(request, httpServletResponse);
				return false;
			}
		}else{
			isCanVisit = true;
		}
		
		
		//未登录
		if(!isCanVisit){
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, httpServletResponse);
		
		}
		return isCanVisit;
		

	}

	public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o,
			Exception e) throws Exception {
//		log.info("后面的路径");
	}

}
