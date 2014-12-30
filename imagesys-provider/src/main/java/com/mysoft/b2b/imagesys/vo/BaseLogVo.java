package com.mysoft.b2b.imagesys.vo;

import com.google.code.morphia.annotations.Id;

public class BaseLogVo{

	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
