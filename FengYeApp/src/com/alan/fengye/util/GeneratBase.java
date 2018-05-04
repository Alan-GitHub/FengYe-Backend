package com.alan.fengye.util;

import java.lang.reflect.Field;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GeneratBase  implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7357920140785181469L;
	
	@Override  
    public String toString() {  
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);  
    }
//	@Override
//	public String toString() {
//
//	    return (new ReflectionToStringBuilder(this) {
//
//	        protected boolean accept(Field f) {
//
//	        return super.accept(f) && !f.getName().equals("password");
//
//	    }}).toString();
//	}

}
