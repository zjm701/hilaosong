package com.hi.json;

import com.hi.tools.annotation.XMLMapping;


/**
 * [简要描述]:<br />
 * 
 * @author wupei
 * @version 1.0, 2011-12-6
 */
@XMLMapping("reqInfo")
public class ReqForm
{
    @XMLMapping("msgName")
    private String msgName;

    @XMLMapping("license")
    private String license = "123123123123";

    @XMLMapping("req_time")
    private String req_time;

    /**
     * 1:web 2:iphone 3:ipad
     */
    @XMLMapping("req_device")
    private String req_device;

    /**
     * 请求语言 1中文 2 英文
     */
    @XMLMapping("req_language")
    private String req_language;

    public String getMsgName()
    {
        return msgName;
    }

    public void setMsgName(String msgName)
    {
        this.msgName = msgName;
    }

    public String getLicense()
    {
        return license;
    }

    public void setLicense(String license)
    {
        this.license = license;
    }

    public String getReq_time()
    {
        return req_time;
    }

    public void setReq_time(String reqTime)
    {
        req_time = reqTime;
    }

    public String getReq_device()
    {
        return req_device;
    }

    public void setReq_device(String reqDevice)
    {
        req_device = reqDevice;
    }

    public String getReq_language()
    {
        return req_language;
    }

    public void setReq_language(String reqLanguage)
    {
        req_language = reqLanguage;
    }
}
