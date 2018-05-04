package com.alan.fengye.enums;

public enum ServiceNameEnum {
	//Register & login
	REGISTER("REGISTER"),
	LOGIN("LOGIN"),
	
	//Get recommend data
	RECOMMEND("RECOMMEND"),
	//Get discover data
	DISCOVER("DISCOVER"),
	
	//Select interest group
	INTERESTGROUPSELECTED("INTERESTGROUPSELECTED"),
	
	//Personal Center
	PERSONALCENTER("PERSONALCENTER"),
	
	//Personal Center -- fans data
	PERSONALCENTER_FANS("PERSONALCENTER_FANS"),
		
	//Personal Center -- drawboard
	PERSONALCENTER_DRAWBOARD("PERSONALCENTER_DRAWBOARD"),
	
	//Personal Center -- detail drawboard 
	PERSONALCENTER_DETAILDRAWBOARD("PERSONALCENTER_DETAILDRAWBOARD"),
	
	//Personal Center -- Add Drawboard
	PERSONALCENTER_ADDDRAWBOARD("PERSONALCENTER_ADDDRAWBOARD"),
	
	//Personal Center -- Collection
	PERSONALCENTER_COLLECTION("PERSONALCENTER_COLLECTION"),
	
	//Personal Center -- Like
	PERSONALCENTER_LIKE("PERSONALCENTER_LIKE"),
	
	//Personal Center -- Attention
	PERSONALCENTER_ATTENTION("PERSONALCENTER_ATTENTION"),

	//作品详情-- 喜欢或者不喜欢
	OPERATION_WORKS_LIKEORNOT("OPERATION_WORKS_LIKEORNOT"),
	
	//喜欢某作品的所有用户
	OPERATION_WORKS_ALLLIKE("OPERATION_WORKS_ALLLIKE"),
	
	//采集某个作品到自己画板 -- 选择画板名字
	OPERATION_WORKS_COLLECTION_SELECT_DRAWNAME("OPERATION_WORKS_COLLECTION_SELECT_DRAWNAME"),
	
	//采集某个作品到自己画板 -- 执行动作
	OPERATION_WORKS_COLLECTION_ACTION("OPERATION_WORKS_COLLECTION_ACTION"),
	
	//转发某作品的所有画板
	OPERATION_WORKS_ALLFORWARD("OPERATION_WORKS_ALLFORWARD"),
	
	//转发某作品的所有画板
	OPERATION_WORKS_ALLCOMMENT("OPERATION_WORKS_ALLCOMMENT"),
	
	//消息 -- 动态
	MESSAGE_DYNAMIC("MESSAGE_DYNAMIC"),
	
	//消息 -- 动态
	MESSAGE_PRIVATE("MESSAGE_PRIVATE"),
	
	//消息 -- 详细私信内容
	MESSAGE_PRIVATE_DETAIL("MESSAGE_PRIVATE_DETAIL");
		
	private String code;
	private ServiceNameEnum(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

}
