package com.alan.fengye.test;


import com.alan.fengye.util.CommonAnnotationUtils;
import com.alan.fengye.vo.UserVo;
public class Test {
	

	    public void testNotNull() {
	        try {
	        	UserVo student = new UserVo();
	        	//System.out.print(student);
	        	student.setRegisterName("Àî°×");
	        	//System.out.print(student);
	            CommonAnnotationUtils.doValidator(student);
	        } catch (Exception e) {
	        	CommonAnnotationUtils.handlerExcpetion(e);
	        }
	    }
	    
	    public static void main(String[] args) {
	    	new Test().testNotNull();
		}

}
