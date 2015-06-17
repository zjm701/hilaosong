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
import com.hi.common.SnsProvider;
import com.hi.json.GetUserInfoReq;
import com.hi.json.LoginForm;
import com.hi.json.ReqForm;
import com.hi.json.TerminalUserLoginReq;
import com.hi.tools.MD5;

@Path("/")
public class UserAction extends BaseAction {

	/**
	 * 用户登录
	 */
	@POST
	@Path(value = "/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String login(String content) {
		System.out.println("==> content:" + content);
		Gson gson = new Gson();
		LoginForm form = gson.fromJson(content, LoginForm.class);
		String username = form.getUsername();
		String password = form.getPassword();
		String message = "";
		if (StringUtils.isEmpty(username)) {
			message = "用户名不能为空";
			return getFailedJsonResult(message);
		} else if (StringUtils.isEmpty(password)) {
			message = "密码不能为空";
			return getFailedJsonResult(message);
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

				return respString;
			} catch (Exception ex) {
				return getFailedJsonResult("fail");
			}
		}
	}

	/**
	 * 用户登录
	 */
	@GET
	@Path(value = "/getuserinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserInfo(@FormParam("userId") String userId) {
		System.out.println("==> userId:" + userId);
		Gson gson = new Gson();

		// 调用sns查询用户信息
		GetUserInfoReq userInfoReq = new GetUserInfoReq();
		userInfoReq.setReqInfo(new ReqForm("getUserInfoReq"));
		userInfoReq.setFromUid(Long.valueOf(userId));
		userInfoReq.setToUid(Long.valueOf(userId));

		String userInfoReqString = gson.toJson(userInfoReq);

		String respString = SnsProvider.getSNSJsonCxfClient().getUserInfo(userInfoReqString);
		System.out.println("<== getuserinfo response:" + respString);
		return respString;
	}
}
