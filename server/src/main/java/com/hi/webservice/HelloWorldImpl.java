package com.hi.webservice;

import javax.ws.rs.core.Response;

import com.hi.tools.ResponseTools;

public class HelloWorldImpl implements HelloWorld {
	@Override
	public Response sayHelloString(String name) {
		System.out.println("==> sayHelloString:" + name);
		return ResponseTools.getResponse("hello, " + name);
	}

	@Override
	public Response sayHelloJson(String name) {
		System.out.println("==> sayHelloJson:" + name);
		return ResponseTools.getResponse("{\"name\":\"" + name + "\",\"action\":\"hello\"}");
	}

	@Override
	public Response sayHelloXml(String name) {
		System.out.println("==> sayHelloXml:" + name);
		return ResponseTools.getResponse("<Response><Name>" + name + "</Name><Action>hello</Action></Response>");
	}
}