package com.drlm.backend.entities;

import lombok.Data;

@Data
public class SubjectAttr {
	
	private Long id;
	private String value;
	private String name;
	
	
	public SubjectAttr(Long id, String value, String name) {
		super();
		this.id = id;
		this.value = value;
		this.name = name;
	}
}
