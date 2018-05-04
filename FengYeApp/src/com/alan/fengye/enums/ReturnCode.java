package com.alan.fengye.enums;

public enum ReturnCode {
	SECCESS("000","�ɹ�"),
	FAIL("999","ʧ��"),
	SERVICEEXCEPTION("999999","�����������쳣"),
	//controller���Ʋ�������
	TIMEOUT("111","���ӳ�ʱ"),
	BASEPARAMNULL("112","��������Ϊ��"),
	SERVECPARAMNULL("112","ҵ�����Ϊ��"),
	
	//serviceҵ���������
	USER_EXSIST_ERROR("222","�û��Ѵ��ڴ���"),
	INTERFACE_EXSIST_ERROR("223","�ӿڲ�����");
	private String code;
	private String desc;
	

	private ReturnCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	

}
