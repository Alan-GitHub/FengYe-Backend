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
	
	//��ע��ȡ����Ȥ��
	INTEREST_GROUP_REGARD_OR_NOT("INTEREST_GROUP_REGARD_OR_NOT"),
	
	//Personal Center
	PERSONALCENTER("PERSONALCENTER"),
	
	//Personal Center -- fans data
	PERSONALCENTER_FANS("PERSONALCENTER_FANS"),
		
	//Personal Center -- drawboard
	PERSONALCENTER_DRAWBOARD("PERSONALCENTER_DRAWBOARD"),
	
	//Personal Center -- detail drawboard 
	PERSONALCENTER_DETAILDRAWBOARD("PERSONALCENTER_DETAILDRAWBOARD"),
	
	//Personal Center -- DRAWBOARD_REGARD_OR_NOT 
	DRAWBOARD_REGARD_OR_NOT("DRAWBOARD_REGARD_OR_NOT"),
	
	//Personal Center -- detail drawboard 
	WORKS_ADD_INFO("WORKS_ADD_INFO"),
	
	//Personal Center -- Add Drawboard
	PERSONALCENTER_ADDDRAWBOARD("PERSONALCENTER_ADDDRAWBOARD"),
	
	//Personal Center -- Collection
	PERSONALCENTER_COLLECTION("PERSONALCENTER_COLLECTION"),
	
	//Personal Center -- Like
	PERSONALCENTER_LIKE("PERSONALCENTER_LIKE"),
	
	//Personal Center -- Attention
	PERSONALCENTER_ATTENTION("PERSONALCENTER_ATTENTION"),
	
	//��ע�û� ��ȡ����ע
	USER_REGARD_OR_NOT("USER_REGARD_OR_NOT"),

	//��Ʒ����-- ϲ�����߲�ϲ��
	OPERATION_WORKS_LIKEORNOT("OPERATION_WORKS_LIKEORNOT"),
	
	//ϲ��ĳ��Ʒ�������û�
	OPERATION_WORKS_ALLLIKE("OPERATION_WORKS_ALLLIKE"),
	
	//�ɼ�ĳ����Ʒ���Լ����� -- ѡ�񻭰�����
	OPERATION_WORKS_COLLECTION_SELECT_DRAWNAME("OPERATION_WORKS_COLLECTION_SELECT_DRAWNAME"),
	
	//�ɼ�ĳ����Ʒ���Լ����� -- ִ�ж���
	OPERATION_WORKS_COLLECTION_ACTION("OPERATION_WORKS_COLLECTION_ACTION"),
	
	//ת��ĳ��Ʒ�����л���
	OPERATION_WORKS_ALLFORWARD("OPERATION_WORKS_ALLFORWARD"),
	
	//ĳ����Ʒ����������
	OPERATION_WORKS_ALLCOMMENT("OPERATION_WORKS_ALLCOMMENT"),
	
	//��ĳ����Ʒ�������
	OPERATION_WORKS_ADDCOMMENT("OPERATION_WORKS_ADDCOMMENT"), 
	
	//��Ϣ -- ��̬
	MESSAGE_DYNAMIC("MESSAGE_DYNAMIC"),
	
	//��Ϣ -- ˽��
	MESSAGE_PRIVATE("MESSAGE_PRIVATE"),
	
	//��Ϣ -- ��ϸ˽������
	MESSAGE_PRIVATE_DETAIL("MESSAGE_PRIVATE_DETAIL"),
	
	//��Ϣ -- �ظ�˽������
	MESSAGE_PRIVATE_DETAIL_REPLY("MESSAGE_PRIVATE_DETAIL_REPLY");
		
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
