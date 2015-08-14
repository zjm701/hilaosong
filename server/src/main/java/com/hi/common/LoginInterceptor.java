package com.hi.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hi.model.User;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	//@Autowired
	//private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");
		if (user == null) {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();

			//user = userService.getUserByName(auth.getName());
			user = new User();
			user.setNickname(auth.getName());
			session.setAttribute("user", user);
		}

		return super.preHandle(request, response, handler);
	}
}