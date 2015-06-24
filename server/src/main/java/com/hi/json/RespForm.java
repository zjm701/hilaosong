package com.hi.json;

import com.hi.tools.annotation.XMLMapping;

@XMLMapping("respInfo")
public class RespForm
{
    @XMLMapping("msgName")
    private String msgName;
    
    @XMLMapping("resp_time")
    private String resp_time;
    
    @XMLMapping("result_code")
    private String result_code;
    
    @XMLMapping("result_desc")
    private String result_desc;
    
    @XMLMapping("count")
    private Integer count;
    
    @XMLMapping("comment_num")
    private Integer comment_num;

    public String getMsgName()
    {
        return msgName;
    }

    public void setMsgName(String msgName)
    {
        this.msgName = msgName;
    }

    public String getResp_time()
    {
        return resp_time;
    }

    public void setResp_time(String respTime)
    {
        resp_time = respTime;
    }

    public String getResult_code()
    {
        return result_code;
    }

    public void setResult_code(String resultCode)
    {
        result_code = resultCode;
    }

    public String getResult_desc()
    {
        return result_desc;
    }

    public void setResult_desc(String resultDesc)
    {
        result_desc = resultDesc;
    }

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getComment_num() {
		return comment_num;
	}

	public void setComment_num(Integer comment_num) {
		this.comment_num = comment_num;
	}
  
}
