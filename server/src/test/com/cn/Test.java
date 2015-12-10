package com.cn;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
public class Test {
	  public static void main(String[] args) throws 
      		IOException {
		 // VirtualMachine vm = VirtualMachine.attach(args[0]);
		 // vm.loadAgent("/Users/jiangbo/Workspace/code/java/javaagent/loadagent.jar");
	
	}


	@SuppressWarnings("rawtypes")
	public static void agentmain(String args, Instrumentation inst){
	        Class[] classes = inst.getAllLoadedClasses();
	        for(Class cls :classes){
	            System.out.println(cls.getName());
	        }
	}
	
	public void chinaTo16(String s){
		try {
			byte[] b = s.getBytes();
			String str = " ";
			for (int i = 0; i < b.length; i++) {
				Integer I = new Integer(b[i]);
				String strTmp = I.toHexString(b[i]);
				if (strTmp.length() > 2)
					strTmp = strTmp.substring(strTmp.length() - 2);
				str = str + strTmp;
			}
			System.out.println(str.toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 public static String chinaToUnicode(String str){  
	        String result="";  
	        for (int i = 0; i < str.length(); i++){  
	            int chr1 = (char) str.charAt(i);  
	            if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)  
	                result+="\\u" + Integer.toHexString(chr1);  
	            }else{  
	                result+=str.charAt(i);  
	            }  
	        }  
	        return result;  
	    }  
}
