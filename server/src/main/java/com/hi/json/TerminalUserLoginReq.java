package com.hi.json;

public class TerminalUserLoginReq
{
    private ReqForm reqInfo;

    private String accountName;

    private String pwd;
    
    private String status;
    
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    /**
     * 返回reqInfo属性
     * 
     * @return reqInfo属性
     */
    public ReqForm getReqInfo()
    {
        return reqInfo;
    }

    /**
     * 设置reqInfo属性
     * 
     * @param reqInfo reqInfo属性
     */
    public void setReqInfo(ReqForm reqInfo)
    {
        this.reqInfo = reqInfo;
    }

    /**
     * 返回accountName属性
     * 
     * @return accountName属性
     */
    public String getAccountName()
    {
        return accountName;
    }

    /**
     * 设置accountName属性
     * 
     * @param accountName accountName属性
     */
    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    /**
     * 返回pwd属性
     * 
     * @return pwd属性
     */
    public String getPwd()
    {
        return pwd;
    }

    /**
     * 设置pwd属性
     * 
     * @param pwd pwd属性
     */
    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }

}
