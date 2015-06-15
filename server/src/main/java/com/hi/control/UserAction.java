package com.hi.control;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.google.gson.Gson;
import com.hi.json.LoginForm;
import com.hi.json.ReqForm;
import com.hi.json.TerminalUserLoginReq;
import com.hi.tools.MD5;
import com.hi.tools.StringTools;
import com.hi.webservice.SnsTerminalInterface;

@Path("/")
public class UserAction extends BaseAction {
	@POST
	@Path(value = "/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String login(String content) {
		System.out.println("==>content:" + content);
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
			System.out.println("==>username:" + username + ", password:" + password);
			try {
				TerminalUserLoginReq terminalUserLoginReq = new TerminalUserLoginReq();
				ReqForm reqForm = new ReqForm();

				// 调用sns进行登录
				String status = "1";

				// 调用SNS接口验证用户名
				reqForm.setMsgName("terminalUserLoginReq");
				reqForm.setReq_device("1");// web user
				reqForm.setReq_language("1");// 中文
				reqForm.setReq_time(StringTools.date2TimeStr(new Date(), StringTools.DATETIME_PATTERN));

				terminalUserLoginReq.setReqInfo(reqForm);
				terminalUserLoginReq.setAccountName(username);
				terminalUserLoginReq.setPwd(password);
				terminalUserLoginReq.setStatus(status);

				String loginReqString = gson.toJson(terminalUserLoginReq);

				// 调用SNS接口验证用户名
				SnsTerminalInterface interService = new SnsProvider().getSNSJsonCxfClient();
				String response = interService.terminalUserLogin(loginReqString);
				System.out.println("<==response:" + response);
				return response;
				// object = JSONObject.fromObject(response);
				//
				// TerminalUserLoginResp resp = (TerminalUserLoginResp)
				// JSONObject
				// .toBean(object, TerminalUserLoginResp.class);
				// Map<String, Object> classMap = new HashMap<String, Object>();
				// classMap.put("user", UserForm.class);
				// GetUserInfoResp resp1 = (GetUserInfoResp)
				// JSONObject.toBean(object,
				// GetUserInfoResp.class, classMap);
				// session.setAttribute("userInfo", resp1.getUser());
				// "sns-login-resp");
				// if (resp.getRespInfo().getResult_code() != null
				// && resp.getRespInfo().getResult_code()
				// .equals(Common.RESULTCODE_300)) {
				// session.setAttribute("customerId", resp.getLoginID());
				// session.setAttribute("customerkey", resp.getCustomerKey());
				// session.setAttribute("mailCount",resp.getMailCount());
				// session.setAttribute("friendsCount",resp.getFriendsCount());
				// session.setAttribute("photoCount",resp.getPhotoCount());
				// // 个人信息
				// IphoneUserForm iphoneUserForm = getCustomerInfo(resp
				// .getLoginID());
				// UserBaseInfo userBaseInfo = new UserBaseInfo();
				// userBaseInfo.setRealName(iphoneUserForm.getRealname());
				// userBaseInfo.setNickName(iphoneUserForm.getNickname());
				// userBaseInfo.setMobileNo(iphoneUserForm.getMobile());
				// userBaseInfo.setGender(iphoneUserForm.getSex());
				// customerInfo.setUserBaseInfo(userBaseInfo);
				// session.setAttribute("customerInfo", customerInfo);
				// session.setAttribute("userPhone",iphoneUserForm.getMobile());
				//
				// message = "success";
				//
				// } else {
				// message = resp.getRespInfo().getResult_desc();
				// }
				//
			} catch (Exception ex) {
				message = "fail";
			}
		}
		return null;
	}

	private class SnsProvider {

		private static final String SNS_JSON_ADDRESS = "http://114.247.120.164:7070/haidilao/services/SnsTerminalInterface";

		public SnsProvider() {
		}

		public synchronized SnsTerminalInterface getSNSJsonCxfClient() {
			// 定义客户端代理
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

			// 设置服务端地址和服务类
			factory.setAddress(SNS_JSON_ADDRESS);
			factory.setServiceClass(SnsTerminalInterface.class);
			SnsTerminalInterface service = (SnsTerminalInterface) factory.create();

			return service;
		}
	}
}
