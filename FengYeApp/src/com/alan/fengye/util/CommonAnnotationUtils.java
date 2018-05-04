package com.alan.fengye.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.alan.fengye.enums.ReturnCode;


public class CommonAnnotationUtils {
	
	 /**
     * 通过反射来获取javaBean上的注解信息，判断属性值信息，然后通过注解元数据
     * 来返回
     * @param t
	 * @throws ServiceException 
     */
    public static <T> void doValidator(T t) throws ServiceException{
        Class<?> clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            NotNull notNull = field.getDeclaredAnnotation(NotNull.class);
            if(null!=notNull){
                Object value = getValue(t,field.getName());
                if(!notNull(value)){
                    throwExcpetion(notNull.value());
                }
            }
        }
    }
     
    public static <T> Object getValue(T t,String fieldName){
        Object value = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass());
            PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor property:props){
                if(fieldName.equals(property.getName())){
                    Method method = property.getReadMethod();
                    value = method.invoke(t,new Object[]{});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
     
    public static boolean notNull(Object value){
        if(null==value){
            return false;
        }
        if(value instanceof String && isEmpty((String)value)){
            return false;
        }
        if(value instanceof List && isEmpty((List<?>)value)){
            return false;
        }
        return null!=value;
    }
     
    public static boolean isEmpty(String str){
        return null==str || str.isEmpty();
    }
     
    public static boolean isEmpty(List<?> list){
        return null==list || list.isEmpty();
    }
     
    public static void throwExcpetion(String msg) throws ServiceException{
        if(null!=msg){
            throw new ServiceException(ReturnCode.BASEPARAMNULL.getCode(),msg);
        }
    }
     
    public static void handlerExcpetion(Exception e){
        if(e instanceof ServiceException){
            System.out.println(((ServiceException)e).getDesc());
        }else{
            System.out.println("调用失败");
        }
    }
 

}
