package com.hi.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hi.model.User;

public class TestInterceptor extends HandlerInterceptorAdapter {

	//@Autowired
	//private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String req_uri=request.getRequestURI(); 
		System.out.println("request.getRequestURI():"+req_uri);
		String ctxPath = request.getContextPath(); 
		String param=request.getQueryString();
		System.out.println("request.getQueryString():"+param);
		if(req_uri.contains("/rest")){
			Enumeration enu=request.getParameterNames(); 
			req_uri += "?";
	        while(enu.hasMoreElements()) 
	        { 
	            String name=(String)enu.nextElement();
	            String value = request.getParameter(name);
	            req_uri += name+"="+value+"&";
	        }
            String result = HttpClientUtil.HttpClientByGet("http://103.240.244.31/hilaosong/rest/"+req_uri);
            ajaxResponse(response, result);
	        return false;
		}
		return super.preHandle(request, response, handler);
	}

    /**
     * [简要描述]:记录谁做了哪个动作
     * 
     * @author zhangxurong
     * @param datas <br/>
     */
    public void ajaxResponse(HttpServletResponse response,String datas)
    {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        try
        {
            if (null != datas)
            {
                response.getWriter().print(datas);
            }
            else
            {
                response.getWriter().print(datas);
            }
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }
    }

}