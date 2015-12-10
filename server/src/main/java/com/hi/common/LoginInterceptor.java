package com.hi.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import com.hi.common.provider.SnsProvider;
import com.hi.json.GetUserInfoReq;
import com.hi.json.GetUserInfoResp;
import com.hi.json.ReqForm;


public class LoginInterceptor extends HandlerInterceptorAdapter {
	public Log logger = LogFactory.getLog(getClass());

	//@Autowired
	//private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
//		HttpSession session = request.getSession();
//		String remoteAddress=request.getRemoteAddr();  
		String servletPath=request.getServletPath();  
		/*String realPath=request.getRealPath("/");  
		String remoteUser=request.getRemoteUser();  
		String requestURI=request.getRequestURI();
		*/
//		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
		System.out.println("LoginInterceptor..........."+servletPath);
		//查询用户ID
		if(servletPath.endsWith(".html")){
			String loginId = request.getParameter("loginId");
			System.out.println("loginId:"+loginId);
			if(StringUtils.isNotEmpty(loginId)){
				//如果是0退出登录
				if(HIConstants.LOGIN_OUT_ID.equals(loginId)){
					request.getSession().removeAttribute(HIConstants.LOGIN_ID);
					request.getSession().removeAttribute(HIConstants.CUSTOMER_KEY);
					request.getSession().removeAttribute(HIConstants.LOGIN_INFO);
					request.getSession().removeAttribute(HIConstants.USER);
				}else{
					//saveUserInfoIntoSession(loginId, request);
				}
			}
		}
		
		
		/*User user = (User) session.getAttribute("user");
		if (user == null) {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();

			//user = userService.getUserByName(auth.getName());
			user = new User();
			user.setNickname(auth.getName());
			session.setAttribute("user", user);
		}*/

		return super.preHandle(request, response, handler);
	}
/*
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String servletPath=request.getServletPath();  
		System.out.println("postHandle:"+servletPath);
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String servletPath=request.getServletPath();  
		System.out.println("afterCompletion:"+servletPath);
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String servletPath=request.getServletPath();  
		System.out.println("afterConcurrentHandlingStarted:"+servletPath);
		super.afterConcurrentHandlingStarted(request, response, handler);
	}
*/
	private void saveUserInfoIntoSession(String loginId,HttpServletRequest request) {
		System.out.println("==> loginId:" + loginId);
		Gson gson = new Gson();

		// 调用sns查询用户信息
		GetUserInfoReq userInfoReq = new GetUserInfoReq();
		userInfoReq.setReqInfo(new ReqForm("getUserInfoReq"));
		userInfoReq.setFromUid(StringUtils.isEmpty(loginId)?0:Long.valueOf(loginId));
		userInfoReq.setToUid(Long.valueOf(loginId));

		String userInfoReqString = gson.toJson(userInfoReq);

		try {
			String respString = SnsProvider.getSNSJsonCxfClient().getUserInfo(userInfoReqString);
			System.out.println("<== getuserinfo response:" + respString);

			GetUserInfoResp resp = gson.fromJson(respString, GetUserInfoResp.class);
			request.getSession().setAttribute(HIConstants.LOGIN_ID, loginId);
			request.getSession().setAttribute(HIConstants.LOGIN_INFO, respString);
			request.getSession().setAttribute(HIConstants.USER, resp.getUser());

		} catch (Exception e) {
			//clearUser();
			e.printStackTrace();
		}
	}
	
}