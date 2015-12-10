package com.cn;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hi.model.HistoryUserInfo;
import com.hi.service.HistoryUserInfoService;

public class HistortyTest {

	private static BeanFactory factory = new ClassPathXmlApplicationContext("spring-*.xml");
	public static void main(String[] args) {
		//delete();
		//save();
		list();
	}

	public static void list(){
		HistoryUserInfoService ds = (HistoryUserInfoService) factory.getBean("historyUserInfoService");
		try {
			//List<HistoryUserInfo> infolists = ds.list("21517");
			List<HistoryUserInfo> infolists = ds.list("21517",1);
			for (HistoryUserInfo historyUserInfo : infolists) {
				System.out.println(historyUserInfo.getInfo());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void delete(){
		HistoryUserInfoService ds = (HistoryUserInfoService) factory.getBean("historyUserInfoService");
		try {
			ds.delete("1");
			ds.delete("2");
			ds.delete("3");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void save(){
		HistoryUserInfoService ds = (HistoryUserInfoService) factory.getBean("historyUserInfoService");
		try {

			ds.save("测试3", "13161695955", "测试地址", "21517");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
