package com.mysoft.b2b.imagesys.util;

public enum ImageOperStatusEnum {
	
	SUCCESS("成功"),FAILURE("失败");
	
	private String value;
	
	private ImageOperStatusEnum(String type){
		this.value = type;
	}
	public String value(){
		return this.value;
	}
}
