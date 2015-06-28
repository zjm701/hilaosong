package com.hi.control;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.hi.common.HIConstants;
import com.hi.common.SnsProvider;
import com.hi.json.GetUserInfoReq;
import com.hi.json.GetUserInfoResp;
import com.hi.json.LoginForm;
import com.hi.json.ReqForm;
import com.hi.json.TerminalUserLoginReq;
import com.hi.json.TerminalUserLoginResp;
import com.hi.tools.MD5;
import com.hi.tools.StringTools;

@Path("/")
public class UserAction extends BaseAction {

	/**
	 * "/wap/login" (Mobile version)
	 * 
	 * 用户登录
	 */
	@POST
	@Path(value = "/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String login(String content) {
		getSession().removeAttribute(HIConstants.LOGIN_ID);
		getSession().removeAttribute(HIConstants.USER);
		System.out.println("==> content:" + content);
		
		Gson gson = new Gson();
		LoginForm form = gson.fromJson(content, LoginForm.class);
		String username = form.getUsername();
		String password = form.getPassword();
		String message = "";
		if (StringUtils.isEmpty(username)) {
			message = "\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a"; //用户名不能为空
			return getMessageJsonResult(message);
		} else if (StringUtils.isEmpty(password)) {
			message = "\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a"; //密码不能为空
			return getMessageJsonResult(message);
		} else {
			password = MD5.getMd5(form.getPassword().getBytes());
			System.out.println("==> username:" + username + ", password:" + password);
			try {
				// 调用SNS接口验证用户名
				TerminalUserLoginReq loginReq = new TerminalUserLoginReq();
				loginReq.setReqInfo(new ReqForm("terminalUserLoginReq"));
				loginReq.setAccountName(username);
				loginReq.setPwd(password);
				loginReq.setStatus("1");

				String loginReqString = gson.toJson(loginReq);

				String respString = SnsProvider.getSNSJsonCxfClient().terminalUserLogin(loginReqString);
				System.out.println("<== login response:" + respString);

				TerminalUserLoginResp resp = gson.fromJson(respString, TerminalUserLoginResp.class);
				if(StringTools.isNotEmpty(resp.getLoginID())){
					getSession().setAttribute(HIConstants.LOGIN_ID, resp.getLoginID());
				}

				return respString;
			} catch (Exception ex) {
				return getMessageJsonResult("fail");
			}
		}
	}

	/**
	 * function getCustomerInfo() (Mobile version)
	 * 
	 * 获取用户信息
	 */
	@GET
	@Path(value = "/getuserinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserInfo(@FormParam("userId") String userId) {
		Gson gson = new Gson();

		// 调用sns查询用户信息
		GetUserInfoReq userInfoReq = new GetUserInfoReq();
		userInfoReq.setReqInfo(new ReqForm("getUserInfoReq"));
		userInfoReq.setFromUid(Long.valueOf(userId));
		userInfoReq.setToUid(Long.valueOf(userId));

		String userInfoReqString = gson.toJson(userInfoReq);

		String respString = SnsProvider.getSNSJsonCxfClient().getUserInfo(userInfoReqString);
		System.out.println("<== getuserinfo response:" + respString);

		GetUserInfoResp resp = gson.fromJson(respString, GetUserInfoResp.class);
		getSession().setAttribute(HIConstants.USER, resp.getUser());

		return respString;
	}
}
