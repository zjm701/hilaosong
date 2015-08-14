package com.hi.control;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class PayAction extends BaseAction {

	/**
	 * \ 支付渠道列表
	 * 
	 * <pre>
	 *         输入参数： 
	 *         输出参数：
	 *         respCode 
	 *         respMsg 
	 *         channelNo 渠道编号
	 *         channelName 渠道名称
	 *         
	 *         输出样例
	 *         
	 *     		{
	 * 			"respCode":"1000",
	 * 			"respMsg":"查询成功",
	 * 			"payChannelList":[
	 * 				{
	 * 					"channelNo":"tenPay",
	 * 					"channelName":"caifutong"
	 * 				}
	 * 		}
	 * </pre>
	 * 
	 * @param wapPayForm
	 * @return
	 */
	@GET
	@Path(value = "/getpaychannels")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPayChannels(@FormParam("loginId") String loginId) {
		return null;
	}
}
