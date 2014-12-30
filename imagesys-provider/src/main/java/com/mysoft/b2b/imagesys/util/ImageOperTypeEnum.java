package com.mysoft.b2b.imagesys.util;

public enum ImageOperTypeEnum {
	
	DELETE("删除"),UPLOAD("上传"),ADD("创建");
	
	private String type;
	
	private ImageOperTypeEnum(String type){
		this.type = type;
	}
	
	public String value(){
		return this.type;
	}
}
