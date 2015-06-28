package com.hi.json;

import com.hi.model.User;
import com.hi.tools.annotation.XMLMapping;

@XMLMapping("Message")
public class GetUserInfoResp {
	private RespForm respInfo;

	private User user;

	public RespForm getRespInfo() {
		return respInfo;
	}

	public void setRespInfo(RespForm respInfo) {
		this.respInfo = respInfo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
