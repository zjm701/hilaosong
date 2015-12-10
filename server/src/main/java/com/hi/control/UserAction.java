package com.hi.control;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

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
import com.hi.service.HistoryUserInfoService;
import com.hi.thread.PlaceOrderPhoneCodeSmsThread;
import com.hi.tools.StringTools;

@Path("/")
public class UserAction extends BaseAction {
	
	
	private Logger logger = LoggerFactory.getLogger(UserAction.class);
	
	@RequestMapping("/index")
	public String index(String content) {
		logger.debug("index..................");
		return "index";
	}

	@POST
	@Path(value = "/loginout")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String loginOut(String content) {
		clearUser();
		return getJsonString(MessageCode.LOGING_OUT);
	}

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
		clearUser();
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
					return getUserInfo( resp.getLoginID());
				}
				return respString;
			} catch (Exception e) {
				return getJsonString(MessageCode.ERROR_USER);
			}
		}
	}

	@POST
	@Path(value = "/login2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String login2(@FormParam("loginId") String loginId) {
		//如果已经登陆
		String sloginId = (String)getSession().getAttribute(HIConstants.LOGIN_ID);
		if(StringUtils.isNotEmpty(sloginId)){
			String respString =  (String) getSession().getAttribute(HIConstants.LOGIN_INFO);
			if (StringTools.isNotEmpty(respString)) {
				return respString;
			}else{
				loginId = sloginId;
			}
		}
		System.out.println("loginId:"+loginId);
		if(StringUtils.isNotEmpty(loginId)){
			//如果是0退出登录
			if(HIConstants.LOGIN_OUT_ID.equals(loginId)){
				clearUser();
				return getJsonString(MessageCode.ERROR_USER);
			}else{
				saveUserInfoIntoSession(loginId);
				String respString =  (String) getSession().getAttribute(HIConstants.LOGIN_INFO);
				if (StringTools.isNotEmpty(respString)) {
					return respString;
				} else {
					clearUser();
					return getJsonString(MessageCode.ERROR_USER);
				}
			}
		}
		clearUser();
		return getJsonString(MessageCode.VERIFICATION_EMPTY_LOGINID);
	}

	@GET
	@Path(value = "/getuserinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserInfo() {
		return getUserInfo((String)getSession().getAttribute(HIConstants.LOGIN_ID));
	}
	/**
	 * function getCustomerInfo() (Mobile version)
	 * 
	 * 获取用户信息
	 */
	//@GET
	//@Path(value = "/getuserinfo")
	//@Produces(MediaType.APPLICATION_JSON)
	public String getUserInfo(@FormParam("loginId") String loginId) {
		logger.info(loginId);
		if (StringUtils.isEmpty(loginId)) {
			clearUser();
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
					clearUser();
					return getJsonString(MessageCode.ERROR_USER);
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
				return getJsonString(MessageCode.ERROR_USER);
			}
		} else {
			return "[]";
		}
	}

	private void saveUserInfoIntoSession(String loginId) {
		System.out.println("==> loginId:" + loginId);
		//用户测试
		if(loginId.equals("21517111111")||loginId.equals("22646111111")){
			tetlogin(loginId);
		}else{
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
				if(resp.getUser() != null ){
					getSession().setAttribute(HIConstants.LOGIN_ID, loginId);
					getSession().setAttribute(HIConstants.LOGIN_INFO, respString);
					getSession().setAttribute(HIConstants.USER, resp.getUser());
	
				}
			} catch (Exception e) {
				clearUser();
				e.printStackTrace();
			}
		}
	}
	
	private void tetlogin(String loginId){
		Gson gson = new Gson();
		String realPath=getSession().getServletContext().getRealPath("/test/"+loginId+".json");
		String respString = "";
		try {
			respString = FileUtils.readFileToString(new File(realPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GetUserInfoResp resp = gson.fromJson(respString, GetUserInfoResp.class);
		getSession().setAttribute(HIConstants.LOGIN_ID, loginId);
		getSession().setAttribute(HIConstants.LOGIN_INFO, respString);
		getSession().setAttribute(HIConstants.USER, resp.getUser());
	}
	
	private void clearUser(){
		getSession().removeAttribute(HIConstants.LOGIN_ID);
		getSession().removeAttribute(HIConstants.CUSTOMER_KEY);
		getSession().removeAttribute(HIConstants.LOGIN_INFO);
		getSession().removeAttribute(HIConstants.USER);
	}
	@Autowired
	private HistoryUserInfoService historyUserInfoService;
	
	/**
	 * 获取用户历史信息
	 * @return
	 */
	@GET
	@Path(value = "/getHistoryUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHistoryUser(@FormParam("type")String type) {
		User user = (User) getSession().getAttribute(HIConstants.USER);
		if(type==null){
			return getSuccessJsonResponse(historyUserInfoService.list(user.getUser_entity_id()+""));
		}else{
			return getSuccessJsonResponse(historyUserInfoService.list(user.getUser_entity_id()+"",Integer.parseInt(type)));
		}
	}

	/**
	 * 保存用户历史信息
	 * @param name
	 * @param phone
	 * @param address
	 * @return
	 */
	
	@POST
	@Path(value = "/saveHistoryUser")
	@Produces(MediaType.APPLICATION_JSON)
	public String saveHistoryUser(@FormParam("name") String name,
			@FormParam("phone") String phone,@FormParam("address") String address,
			@FormParam("code") String code){
		//校验验证码
		//if("".equals(code) || checkCode(code)){
			User user = (User) getSession().getAttribute(HIConstants.USER);
			try{
				//保存
				historyUserInfoService.save(name,phone,address,user.getUser_entity_id()+"");
				return this.getJsonString(MessageCode.SAVE_SUCCESS);
			}catch(Exception e){
				logger.error("保存用户失败:"+e.getMessage());
				return this.getJsonString(MessageCode.SAVE_FAILED);
			}
		//}else{
		//	return this.getJsonString(MessageCode.FAILED);
		//}
	}
	/**
	 * 保存发票
	 * @param name
	 * @param phone
	 * @param address
	 * @return
	 */
	
	@POST
	@Path(value = "/saveInvoice")
	@Produces(MediaType.APPLICATION_JSON)
	public String saveInvoice(@FormParam("invoice") String invoice){
		//校验验证码
		//if("".equals(code) || checkCode(code)){
			User user = (User) getSession().getAttribute(HIConstants.USER);
			try{
				//保存
				historyUserInfoService.saveInvoice(invoice,user.getUser_entity_id()+"");
				return this.getJsonString(MessageCode.SAVE_SUCCESS);
			}catch(Exception e){
				logger.error("保存用户失败:"+e.getMessage());
				return this.getJsonString(MessageCode.SAVE_FAILED);
			}
		//}else{
		//	return this.getJsonString(MessageCode.FAILED);
		//}
	}
	/**
	 * 删除用户历史信息
	 * @param id
	 * @return
	 */
	@POST
	@Path(value = "/deleteHistoryUser")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteHistoryUser(@FormParam("id") String id) {
		try{
			historyUserInfoService.delete(id);
			return this.getJsonString(MessageCode.DELETE_SUCCESS);
		}catch(Exception e){
			logger.error("保存用户失败:"+e.getMessage());
			return this.getJsonString(MessageCode.DELETE_FAILED);
		}
	}

	/**
	 * 获取手机验证码
	 * @param phone
	 * @return
	 */
	@POST
	@Path(value = "/getVerificationCode")
	@Produces(MediaType.APPLICATION_JSON)
	public String getVerificationCode(@FormParam("phone") String phone){
		try{
			//起线程获取手机验证码
			new Thread(new PlaceOrderPhoneCodeSmsThread(phone, getSession())).start();
			return this.getJsonString(MessageCode.SUCCESS);
		}catch(Exception e){
			return this.getJsonString(MessageCode.FAILED);
		}
	}


	@POST
	@Path(value = "/checkVerificationCode")
	@Produces(MediaType.APPLICATION_JSON)
	public String checkVerificationCode(@FormParam("code") String code,@FormParam("phone") String phone){
		if(checkCode(code,phone)){
			return this.getJsonString(MessageCode.SUCCESS);
		}else{
			return this.getJsonString(MessageCode.FAILED);
		}
	}

	/*
	 * 校验手机验证码
	 */
	private boolean checkCode(String code,String phone){
		if(code.equals(getSession().getAttribute("phoneVerificationCode_"+phone))){
			getSession().removeAttribute("phoneVerificationCode_"+phone);
			return true;
		}
		return false;
	}
}
