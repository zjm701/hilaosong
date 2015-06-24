package com.hi.json;

public class TerminalUserLoginResp
{
    private RespForm respInfo;

    private String ssoid;

    private String loginID;

    private String customerKey;
    
    private String tabCount;
    
    private String mailCount;
    
    private String friendsCount;
    
    private String photoCount;

    public String getTabCount() {
		return tabCount;
	}

	public void setTabCount(String tabCount) {
		this.tabCount = tabCount;
	}

	public String getMailCount() {
		return mailCount;
	}

	public void setMailCount(String mailCount) {
		this.mailCount = mailCount;
	}

	public String getFriendsCount() {
		return friendsCount;
	}

	public void setFriendsCount(String friendsCount) {
		this.friendsCount = friendsCount;
	}

	public String getPhotoCount() {
		return photoCount;
	}

	public void setPhotoCount(String photoCount) {
		this.photoCount = photoCount;
	}

	/**
     * 返回respInfo属性
     * 
     * @return respInfo属性
     */
    public RespForm getRespInfo()
    {
        return respInfo;
    }

    /**
     * 设置respInfo属性
     * 
     * @param respInfo respInfo属性
     */
    public void setRespInfo(RespForm respInfo)
    {
        this.respInfo = respInfo;
    }

    /**
     * 返回ssoid属性
     * 
     * @return ssoid属性
     */
    public String getSsoid()
    {
        return ssoid;
    }

    /**
     * 设置ssoid属性
     * 
     * @param ssoid ssoid属性
     */
    public void setSsoid(String ssoid)
    {
        this.ssoid = ssoid;
    }

    /**
     * 返回loginID属性
     * 
     * @return loginID属性
     */
    public String getLoginID()
    {
        return loginID;
    }

    /**
     * 设置loginID属性
     * 
     * @param loginID loginID属性
     */
    public void setLoginID(String loginID)
    {
        this.loginID = loginID;
    }

    /**
     * 设置customerKey属性
     * @param customerKey customerKey属性
     */
    public void setCustomerKey(String customerKey)
    {
        this.customerKey = customerKey;
    }

    /**
     * 返回customerKey属性
     * @return customerKey属性
     */
    public String getCustomerKey()
    {
        return customerKey;
    }

}
