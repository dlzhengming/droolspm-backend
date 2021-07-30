package com.drlm.backend.entities;

import lombok.Data;

@Data
public class Operator {
	
	private Long id;
	private String value;
	private String name;
	private String[] selectList;
	private boolean readOnly;
}
