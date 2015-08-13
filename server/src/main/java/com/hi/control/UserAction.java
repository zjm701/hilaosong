package com.hi.control;

import java.util.HashMap;
import java.util.Map;

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
import com.hi.common.MessageCode;
import com.hi.common.provider.SnsProvider;
import com.hi.json.GetUserInfoReq;
import com.hi.json.GetUserInfoResp;
import com.hi.json.LoginForm;
import com.hi.json.ReqForm;
import com.hi.json.TerminalUserLoginReq;
import com.hi.json.TerminalUserLoginResp;
import com.hi.model.User;
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
		getSession().removeAttribute(HIConstants.CUSTOMER_KEY);
		getSession().removeAttribute(HIConstants.LOGIN_INFO);
		getSession().removeAttribute(HIConstants.USER);
		System.out.println("==> content:" + content);

		Gson gson = new Gson();
		LoginForm form = gson.fromJson(content, LoginForm.class);
		String username = form.getUsername();
		String password = form.getPassword();
		if (StringUtils.isEmpty(username)) {
			return getJsonString(MessageCode.VERIFICATION_EMPTY_USERNAME);
		} else if (StringUtils.isEmpty(password)) {
			return getJsonString(MessageCode.VERIFICATION_EMPTY_PASSWORD);
		} else {
			System.out.println("==> username:" + username + ", password:" + password);

			// 调用SNS接口验证用户名
			TerminalUserLoginReq loginReq = new TerminalUserLoginReq();
			loginReq.setReqInfo(new ReqForm("terminalUserLoginReq"));
			loginReq.setAccountName(username);
			loginReq.setPwd(password);
			loginReq.setStatus("1");

			String loginReqString = gson.toJson(loginReq);

			try {
				String respString = SnsProvider.getSNSJsonCxfClient().terminalUserLogin(loginReqString);
				System.out.println("<== login response:" + respString);

				TerminalUserLoginResp resp = gson.fromJson(respString, TerminalUserLoginResp.class);
				if (StringTools.isNotEmpty(resp.getLoginID())) {
					getSession().setAttribute(HIConstants.LOGIN_ID, resp.getLoginID());
					getSession().setAttribute(HIConstants.CUSTOMER_KEY, resp.getCustomerKey());
				}

				return respString;
			} catch (Exception e) {
				return getJsonString(MessageCode.ERROR_USER_SYSTEM);
			}
		}
	}

	@POST
	@Path(value = "/login2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String login2(String content) {
		String step1 = login(content);
		String loginId = (String) getSession().getAttribute(HIConstants.LOGIN_ID);
		if (StringTools.isNotEmpty(loginId)) {
			return getCurrentUser();
		} else {
			return step1;
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
	public String getUserInfo(@FormParam("loginId") String loginId) {
		if (StringUtils.isEmpty(loginId)) {
			return getJsonString(MessageCode.VERIFICATION_EMPTY_LOGINID);
		} else {
			User user = (User) getSession().getAttribute(HIConstants.USER);
			if (user != null && loginId.equals(user.getUser_entity_id() + "")) {
				return (String) getSession().getAttribute(HIConstants.LOGIN_INFO);
			} else {
				saveUserInfoIntoSession(loginId);
				String respString =  (String) getSession().getAttribute(HIConstants.LOGIN_INFO);
				if (StringTools.isNotEmpty(respString)) {
					return respString;
				} else {
					return getJsonString(MessageCode.ERROR_USER_SYSTEM);
				}
			}
		}
	}

	@GET
	@Path(value = "/getcurrentuser")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCurrentUser() {
		String loginId = (String) getSession().getAttribute(HIConstants.LOGIN_ID);
		if (StringTools.isNotEmpty(loginId)) {
			User user = (User) getSession().getAttribute(HIConstants.USER);
			if (user == null) {
				saveUserInfoIntoSession(loginId);
				user = (User) getSession().getAttribute(HIConstants.USER);
			}
			if (user != null) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("userId", user.getUser_entity_id() + "");
				m.put("nickname", user.getNickname());
				return getJsonString(m);
			} else {
				return getJsonString(MessageCode.ERROR_USER_SYSTEM);
			}
		} else {
			return "[]";
		}
	}

	private void saveUserInfoIntoSession(String loginId) {
		System.out.println("==> loginId:" + loginId);
		Gson gson = new Gson();

		// 调用sns查询用户信息
		GetUserInfoReq userInfoReq = new GetUserInfoReq();
		userInfoReq.setReqInfo(new ReqForm("getUserInfoReq"));
		userInfoReq.setFromUid(Long.valueOf(loginId));
		userInfoReq.setToUid(Long.valueOf(loginId));

		String userInfoReqString = gson.toJson(userInfoReq);

		try {
			String respString = SnsProvider.getSNSJsonCxfClient().getUserInfo(userInfoReqString);
			System.out.println("<== getuserinfo response:" + respString);

			GetUserInfoResp resp = gson.fromJson(respString, GetUserInfoResp.class);
			getSession().setAttribute(HIConstants.LOGIN_ID, loginId);
			getSession().setAttribute(HIConstants.LOGIN_INFO, respString);
			getSession().setAttribute(HIConstants.USER, resp.getUser());

		} catch (Exception e) {
			getSession().removeAttribute(HIConstants.LOGIN_ID);
			getSession().removeAttribute(HIConstants.CUSTOMER_KEY);
			getSession().removeAttribute(HIConstants.LOGIN_INFO);
			getSession().removeAttribute(HIConstants.USER);
			e.printStackTrace();
		}
	}
}
