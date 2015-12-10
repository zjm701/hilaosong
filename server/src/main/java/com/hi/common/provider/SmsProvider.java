package com.hi.common.provider;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.hi.common.SystemSetting;
import com.huawei.mproxy.message.iface.service.MproxyWService;
import com.huawei.mproxy.message.iface.service.SmsInfo;

/**
 * sms短信工具类
 * @author 蒋先彪
 *
 */
public class SmsProvider{
	
	private static Logger log = LoggerFactory.getLogger(SmsProvider.class);
	
    /**
     * 接口调用对象Map
     */
    private static final ConcurrentMap<String, Object> OBJMAP = new ConcurrentHashMap<String, Object>();

    public SmsProvider()
    {

    }

   
   
    /**
     * 
     * [简要描述]:获取短信代理的客户端
     * 
     * @return
     */
    public static synchronized MproxyWService getMproxyClientExt()
    {
        Object object = null;
        MproxyWService service = null;
        if (OBJMAP.containsKey("MproxyWService"))
        {
            service = (MproxyWService) OBJMAP.get("MproxyWService");
        }
        else
        {
        	//得到sms短信服务地址
            String addr = SystemSetting.getSmsServiceURL();
            log.info("sms URl==>["+addr+"]");
            // 定义客户端代理
            JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
            // 设置服务端地址和服务类
            factory.setAddress(addr);
            factory.setServiceClass(MproxyWService.class);
            object = factory.create();

            if (null != object)
            {
                service = (MproxyWService) object;
                OBJMAP.put("MproxyWService", service);
            }
            Client client = ClientProxy.getClient(service);
            HTTPConduit http = (HTTPConduit) client.getConduit();
            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
            httpClientPolicy.setConnectionTimeout(300000);
            httpClientPolicy.setAllowChunking(false);
            httpClientPolicy.setReceiveTimeout(300000);
            http.setClient(httpClientPolicy);
        }
        return service;
    }

    /**
     * 发短信
     * @param msg 短信内容
     * @param phone 手机号
     */
    public static void sendSms(String msg,String phone){
    	if(!StringUtils.hasLength(msg)||!StringUtils.hasLength(phone)){
    		return;
    	}
    	MproxyWService mproxyService = SmsProvider.getMproxyClientExt();
		SmsInfo sms = new SmsInfo();
		sms.setContent(msg);
		List<String> phones = new ArrayList<String>();
	    phones.add(phone);
	    sms.setDestAddr(phones);
	    mproxyService.sendSms("CATER", "", sms);
    }
    

    
}
